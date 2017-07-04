package com.wq.wechat.serviceImpl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import com.wq.wechat.bean.Page;
import com.wq.wechat.service.QRCodeService;
import com.wq.wechat.util.JsonUtils;
import com.wq.wechat.util.WchatHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Mifeng.He(bee) on 2015/11/17.
 */
@Service
@SuppressWarnings("unchecked")
public class QRCodeServiceImpl implements QRCodeService {
    private static Log log =  LogFactory.getLog(QRCodeService.class);

    private static final String QRCODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";

    /*该二维码有效时间，以秒为单位。 最大不超过604800（即7天）。*/
    public static final Integer EXPIRE_SECONDS_MAX = 604800;
    /*场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）*/
    public static final Integer temporary_SCENE_ID_LENGTH = 32;
    public static final Integer permanent_SCENE_ID_MAX = 100000;
    public static final Integer PERMANENT_SCENE_STR_LENGTH_MIN = 1;
    public static final Integer PERMANENT_SCENE_STR_LENGTH_MAX = 64;

    /*二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久,QR_LIMIT_STR_SCENE为永久的字符串参数值 */
    public static final String ACTION_NAME_QR_SCENE = "QR_SCENE";
    public static final String ACTION_NAME_QR_LIMIT_SCENE = "QR_LIMIT_SCENE";
    public static final String ACTION_NAME_QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";

    /*二维码传输参数数据*/
    public static final String TRANSFER_PARAM_QR_SCENE = "{\"expire_seconds\": EXPIRE_SECONDS, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": SCENE_ID}}}";
    public static final String TRANSFER_PARAM_QR_LIMIT_SCENE = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": SCENE_ID}}}";
    public static final String TRANSFER_PARAM_QR_LIMIT_STR_SCENE = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"SCENE_STR\"}}}";

    /*获取二维码ticket后，开发者可用ticket换取二维码图片*/
    public static final String SHOW_QR_CODE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";

    public static final String DATA_ERR_CODE = "errcode";
    public static final String DATA_ERR_MSG = "errmsg";
    public static final String DATA_TICKET = "ticket";
    public static final String DATA_EXPIRE_SECONDS = "expire_seconds ";
    public static final String DATA_URL = "url";

    public static final String QR_CODE_ROOT_ABSOLUTE = "/assets/wechat/qrcode/";


    public Page<Object> temporary(String sceneId) {
        return temporary(sceneId, EXPIRE_SECONDS_MAX);
    }

    public Page<Object> temporary(String sceneId, Integer expireSeconds) {
        Page<Object> page = new Page<Object>();

        if (expireSeconds == null || expireSeconds > EXPIRE_SECONDS_MAX) expireSeconds = EXPIRE_SECONDS_MAX;

        if (sceneId.equals("0") || sceneId.length() > temporary_SCENE_ID_LENGTH || !sceneId.matches("^\\d+$")) {
            page.setSuccess(false);
            page.setMsg("场景值ID，临时二维码时为32位非0整型");
            return page;
        }

        //String actionName = ACTION_NAME_QR_SCENE;
        String url = QRCODE_URL.replace("TOKEN",  WchatHelper.getWeChatProperties().getAccesstoken());

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        String jsonStr = TRANSFER_PARAM_QR_SCENE
                .replace("EXPIRE_SECONDS", expireSeconds.toString())
                .replace("SCENE_ID", sceneId);

        try {
            StringEntity reqEntity= new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
            post.setEntity(reqEntity);
            CloseableHttpResponse resp = httpclient.execute(post);
            StatusLine status = resp.getStatusLine();
            if(status.getStatusCode()==200){
                HttpEntity entity = resp.getEntity();
                String result = EntityUtils.toString(entity);
                Map<String, Object> jsonMap = JsonUtils.toObject(result, Map.class);
                Object obj = null;
                if (jsonMap.containsKey(DATA_ERR_CODE)) {
                    page.setSuccess(false);
                    obj = jsonMap.get(DATA_ERR_CODE);
                    if (obj != null) page.setCode(obj.toString());
                    if (jsonMap.containsKey(DATA_ERR_MSG)) {
                        obj =  jsonMap.get(DATA_ERR_MSG);
                        if (obj != null) page.setMsg(obj.toString());
                    }
                    return page;
                }

                if (jsonMap.containsKey(DATA_TICKET)) {
                    obj = jsonMap.get(DATA_TICKET);
                    if (obj != null) {
                        try {
                            obj = URLEncoder.encode(obj.toString(), "utf-8");
                        } catch (Exception e) {
                            page.setMsg("URLEncoder ticket失败");
                            page.setSuccess(false);
                            return page;
                        }
                        page.getMap().put(DATA_TICKET, obj);
                        page.setT(obj);
                    }
                } else {
                    page.setSuccess(false);
                    page.setMsg("没有获取到ticket");
                }

                if (jsonMap.containsKey(DATA_EXPIRE_SECONDS)) {
                    obj = jsonMap.get(DATA_EXPIRE_SECONDS);
                    if (obj != null) {
                        page.getMap().put(DATA_EXPIRE_SECONDS, obj);
                    }
                }

                if (jsonMap.containsKey(DATA_URL)) {
                    obj = jsonMap.get(DATA_URL);
                    if (obj != null) {
                        page.getMap().put(DATA_URL, obj);
                        page.setUrl(obj.toString());
                    }
                }else {
                    page.setSuccess(false);
                    page.setMsg("没有获取到url");
                }
            }
            if (page.isSuccess()) {
                log.debug("#############################################");
                log.debug("###临时二维码生成成功");
                log.debug("###SCENEID: " + sceneId);
                log.debug("###EXPIRESECONDS: " + expireSeconds);
                log.debug("###URL: " + page.getMap().get(DATA_URL));
                log.debug("###TICKET: " + page.getMap().get(DATA_TICKET));
                log.debug("#############################################");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }finally{
            try {
                if(httpclient!=null) httpclient.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return page;
    }


    public Page<Object> permanent(Integer sceneId) {
        Page<Object> page = new Page<Object>();

        if (sceneId > permanent_SCENE_ID_MAX || sceneId <= 0) {
            page.setSuccess(false);
            page.setMsg("最大值为100000(目前参数只支持1--100000)");
            return page;
        }

        //String actionName = TRANSFER_PARAM_QR_LIMIT_SCENE;
        String url = QRCODE_URL.replace("TOKEN", WchatHelper.getWeChatProperties().getAccesstoken());

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        String jsonStr = TRANSFER_PARAM_QR_LIMIT_SCENE
                .replace("SCENE_ID", sceneId.toString());
        try {
            StringEntity reqEntity= new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
            post.setEntity(reqEntity);
            CloseableHttpResponse resp = httpclient.execute(post);
            StatusLine status = resp.getStatusLine();
            if(status.getStatusCode()==200){
                HttpEntity entity = resp.getEntity();
                String result = EntityUtils.toString(entity);
				Map<String, Object> jsonMap = JsonUtils.toObject(result, Map.class);
                Object obj = null;
                if (jsonMap.containsKey(DATA_ERR_CODE)) {
                    page.setSuccess(false);
                    obj = jsonMap.get(DATA_ERR_CODE);
                    if (obj != null) page.setCode(obj.toString());
                    if (jsonMap.containsKey(DATA_ERR_MSG)) {
                        obj =  jsonMap.get(DATA_ERR_MSG);
                        if (obj != null) page.setMsg(obj.toString());
                    }
                    return page;
                }

                if (jsonMap.containsKey(DATA_TICKET)) {
                    obj = jsonMap.get(DATA_TICKET);
                    if (obj != null) {
                        try {
                            obj = URLEncoder.encode(obj.toString(), "utf-8");
                        } catch (Exception e) {
                            page.setMsg("URLEncoder ticket失败");
                            page.setSuccess(false);
                            return page;
                        }
                        page.getMap().put(DATA_TICKET, obj);
                        page.setT(obj);
                    }
                } else {
                    page.setSuccess(false);
                    page.setMsg("没有获取到ticket");
                }

                if (jsonMap.containsKey(DATA_EXPIRE_SECONDS)) {
                    obj = jsonMap.get(DATA_EXPIRE_SECONDS);
                    if (obj != null) {
                        page.getMap().put(DATA_EXPIRE_SECONDS, obj);
                    }
                }

                if (jsonMap.containsKey(DATA_URL)) {
                    obj = jsonMap.get(DATA_URL);
                    if (obj != null) {
                        page.getMap().put(DATA_URL, obj);
                        page.setUrl(obj.toString());
                    }
                }else {
                    page.setSuccess(false);
                    page.setMsg("没有获取到url");
                }
            }
            if (page.isSuccess()) {
                log.debug("#############################################");
                log.debug("###永久二维码生成成功");
                log.debug("###SCENEID: " + sceneId);
                log.debug("###URL: " + page.getMap().get(DATA_URL));
                log.debug("###TICKET: " + page.getMap().get(DATA_TICKET));
                log.debug("#############################################");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }finally{
            try {
                if(httpclient!=null) httpclient.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return page;
    }


    public Page<Object> permanent(String sceneStr) {
        Page<Object> page = new Page<Object>();

        if (sceneStr == null) sceneStr = "";
        sceneStr = sceneStr.trim();
        if (PERMANENT_SCENE_STR_LENGTH_MIN > sceneStr.length() || PERMANENT_SCENE_STR_LENGTH_MAX < sceneStr.length() ) {
            page.setSuccess(false);
            page.setMsg("长度限制为1到64");
            return page;
        }

        //String actionName = TRANSFER_PARAM_QR_LIMIT_SCENE;
        String url = QRCODE_URL.replace("TOKEN",  WchatHelper.getWeChatProperties().getAccesstoken());

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        String jsonStr = TRANSFER_PARAM_QR_LIMIT_STR_SCENE
                .replace("SCENE_STR", sceneStr);
        try {
            StringEntity reqEntity= new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
            post.setEntity(reqEntity);
            CloseableHttpResponse resp = httpclient.execute(post);
            StatusLine status = resp.getStatusLine();
            if(status.getStatusCode()==200){
                HttpEntity entity = resp.getEntity();
                String result = EntityUtils.toString(entity);
                Map<String, Object> jsonMap = JsonUtils.toObject(result, Map.class);
                Object obj = null;
                if (jsonMap.containsKey(DATA_ERR_CODE)) {
                    page.setSuccess(false);
                    obj = jsonMap.get(DATA_ERR_CODE);
                    if (obj != null) page.setCode(obj.toString());
                    if (jsonMap.containsKey(DATA_ERR_MSG)) {
                        obj =  jsonMap.get(DATA_ERR_MSG);
                        if (obj != null) page.setMsg(obj.toString());
                    }
                    return page;
                }

                if (jsonMap.containsKey(DATA_TICKET)) {
                    obj = jsonMap.get(DATA_TICKET);
                    if (obj != null) {
                        try {
                            obj = URLEncoder.encode(obj.toString(), "utf-8");
                        } catch (Exception e) {
                            page.setMsg("URLEncoder ticket失败");
                            page.setSuccess(false);
                            return page;
                        }
                        page.getMap().put(DATA_TICKET, obj);
                        page.setT(obj);
                    }
                } else {
                    page.setSuccess(false);
                    page.setMsg("没有获取到ticket");
                }

                if (jsonMap.containsKey(DATA_EXPIRE_SECONDS)) {
                    obj = jsonMap.get(DATA_EXPIRE_SECONDS);
                    if (obj != null) {
                        page.getMap().put(DATA_EXPIRE_SECONDS, obj);
                    }
                }

                if (jsonMap.containsKey(DATA_URL)) {
                    obj = jsonMap.get(DATA_URL);
                    if (obj != null) {
                        page.getMap().put(DATA_URL, obj);
                        page.setUrl(obj.toString());
                    }
                }else {
                    page.setSuccess(false);
                    page.setMsg("没有获取到url");
                }
            }
            if (page.isSuccess()) {
                log.debug("#############################################");
                log.debug("###永久二维码生成成功");
                log.debug("###SCENESTR: " + sceneStr);
                log.debug("###URL: " + page.getMap().get(DATA_URL));
                log.debug("###TICKET: " + page.getMap().get(DATA_TICKET));
                log.debug("#############################################");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }finally{
            try {
                if(httpclient!=null) httpclient.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return page;
    }


    public Page<Object> qrcode(String ticket) {
        Page<Object> page = new Page<Object>();

        if (StringUtils.isEmpty(ticket)) {
            page.setSuccess(false);
            page.setMsg("ticket不为空");
            return page;
        }
       /* try {
            ticket = URLEncoder.encode(ticket, "utf-8");
        } catch (Exception e) {
            page.setMsg("URLEncoder ticket失败");
            page.setSuccess(false);
            return page;
        }*/
        //String actionName = TRANSFER_PARAM_QR_LIMIT_SCENE;
        String url = SHOW_QR_CODE_URL.replace("TICKET", ticket);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);

        try {
            CloseableHttpResponse resp = httpclient.execute(get);
            StatusLine status = resp.getStatusLine();
            if(status.getStatusCode()==200){
                HttpEntity entity = resp.getEntity();
                if (entity.getContentType().getValue().contains("application/json")) {
                    String result = EntityUtils.toString(entity);
                    Map<String, Object> jsonMap = JsonUtils.toObject(result, Map.class);
                    page.setSuccess(false);
                    page.getMap().putAll(jsonMap);
                    return page;

                } else if (entity.getContentType().getValue().contains("image/jpg")) {
                    BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(entity);
                    InputStream is = bufferedEntity.getContent();

                    String dir = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
                    dir = dir + QR_CODE_ROOT_ABSOLUTE;
                    if (!new File(dir).exists()) new File(dir).mkdirs();
                    String file = dir + ticket + ".jpg";
                    FileOutputStream fos = new FileOutputStream(new File(file));
                    int len = 0;
                    byte[] bytes = new byte[1024];
                    while ((len = is.read(bytes)) != -1) {
                        fos.write(bytes, 0, len);
                    }
                    fos.close();
                    is.close();

                    page.setUrl(QR_CODE_ROOT_ABSOLUTE + ticket + ".jpg");
                    log.debug("#########二维码生成：" + file);

                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }finally{
            try {
                if(httpclient!=null) httpclient.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        return page;
    }

    public Page<Object> temporaryQrcode(String sceneId) {
        Page<Object> page = this.temporary(sceneId);
        if (!page.isSuccess() || page.getMap().containsKey(DATA_TICKET)) return page;
        String ticket = page.getMap().get(DATA_TICKET).toString();
        page = this.qrcode(ticket);
        return page;
    }

    public Page<Object> temporaryQrcode(String sceneId, Integer expireSeconds) {
        Page<Object> page = this.temporary(sceneId, expireSeconds);
        if (!page.isSuccess() && page.getMap().containsKey(DATA_TICKET)) return page;
        String ticket = page.getMap().get(DATA_TICKET).toString();
        page = this.qrcode(ticket);
        return page;
    }

    public Page<Object> permanentQrcode(Integer sceneId) {
        Page<Object> page = this.permanent(sceneId);
        if (!page.isSuccess() && page.getMap().containsKey(DATA_TICKET)) return page;
        String ticket = page.getMap().get(DATA_TICKET).toString();
        page = this.qrcode(ticket);
        return page;
    }

    public Page<Object> permanentQrcode(String sceneStr) {
        Page<Object> page = this.permanent(sceneStr);
        if (!page.isSuccess() && page.getMap().containsKey(DATA_TICKET)) return page;
        page.getMap().get(DATA_TICKET).toString();
        String ticket = page.getMap().get(DATA_TICKET).toString();
        page = this.qrcode(ticket);
        return page;
    }


}