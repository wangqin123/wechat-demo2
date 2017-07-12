package com.wq.wechat.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

import org.apache.commons.lang3.StringUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wq.wechat.bean.Page;
import com.wq.wechat.bean.WeChatProperties;
import com.wq.wechat.bean.WeixinOauth2Token;
import com.wq.wechat.bean.WeixinUserInfo;
import com.wq.wechat.config.Configsure;
import com.wq.wechat.holder.SpringContextHolder;
import com.wq.wechat.service.QRCodeService;
import com.wq.wechat.service.WeChatPropertiesService;

public class WchatHelper {
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(WchatHelper.class);
	private static final String ACCESS_TOKEN_URL = " https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	private static final String GET_USERINFO ="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	private static final String REF_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

	private static final String GET_ACCESS_TOKEN= "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	
	private static final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	private static final WeChatPropertiesService weChatPropertiesService = (WeChatPropertiesService)SpringContextHolder.getBean("weChatPropertiesService");
	

	 
	/**
	 * 获取getAccEssToken
	 * @param code
	 * @return
	 */
	public static String getAccessToken(){
		String jsonStr;//微信返回信息
		String url;
		String access_token = null;
		JSONObject jsonObj;//微信返回信息Json对象
		url = GET_ACCESS_TOKEN.replace("APPID", Configsure.newInstance().getAppid()).replace("APPSECRET", Configsure.newInstance().getAppsecret());
		try {
			
			jsonStr = get(url);
			jsonObj = JSON.parseObject(jsonStr);
			
			 access_token = jsonObj.getString("access_token");
			//wat.setExpiresIn(jsonObj.getInteger("expires_in"));
			return access_token;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return access_token;
		
		
	}
	
	
	
	
	/**
	 * 获取网页授权凭证
	 * 
	 * @param code
	 * @return
	 */
	public static WeixinOauth2Token getAccToken(String code){
		WeixinOauth2Token wat= null;
		String jsonStr;//微信返回信息
		String url;
		JSONObject jsonObj;//微信返回信息Json对象
		url = ACCESS_TOKEN_URL.replace("APPID", Configsure.newInstance().getAppid()).replace("SECRET", Configsure.newInstance().getAppsecret()).replace("CODE", code);
		try {
			wat = new WeixinOauth2Token();
			
			jsonStr = get(url);
			jsonObj = JSON.parseObject(jsonStr);
			
			wat.setAccessToken(jsonObj.getString("access_token"));
			wat.setExpiresIn(jsonObj.getInteger("expires_in"));
			wat.setRefreshToken(jsonObj.getString("refresh_token"));
			wat.setOpenId(jsonObj.getString("openid"));
			wat.setScope(jsonObj.getString("scope"));
			System.out.println(jsonObj.getString("access_token"));
			return wat;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wat;
		
		
	}
	
	/**
	 * 刷新网页授权凭证
	 * @param refreshToken
	 * @return
	 */
	public static WeixinOauth2Token getRefreshToken(String refreshToken){
		WeixinOauth2Token wat= null;
		String jsonStr;//微信返回信息
		String url;
		JSONObject jsonObj;//微信返回信息Json对象
		url = REF_TOKEN.replace("APPID", Configsure.newInstance().getAppid()).replace("REFRESH_TOKEN ", refreshToken);
		try {
			wat = new WeixinOauth2Token();
			
			jsonStr = get(url);
			jsonObj = JSON.parseObject(jsonStr);
			
			wat.setAccessToken(jsonObj.getString("access_token"));
			wat.setExpiresIn(jsonObj.getInteger("expires_in"));
			wat.setRefreshToken(jsonObj.getString("refresh_token"));
			wat.setOpenId(jsonObj.getString("openid"));
			wat.setScope(jsonObj.getString("scope"));
			System.out.println(jsonObj.getString("access_token"));
			return wat;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wat;
		
		
	}
	
	
	
	public static WeixinUserInfo getwechatUserInfo(String accToken ,String openid){
		WeixinUserInfo weixinUserInfo= null;
		String jsonStr;//微信返回信息
		String url;
		JSONObject jsonObject;//微信返回信息Json对象
		url = GET_USERINFO.replace("ACCESS_TOKEN",accToken).replace("OPENID", openid);
		try {
			weixinUserInfo = new WeixinUserInfo();
			
			jsonStr = get(url);
			jsonObject = JSON.parseObject(jsonStr);
			
			// 用户的标识
			weixinUserInfo.setOpenId(jsonObject.getString("openid"));
			// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				// 昵称
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				// 用户的性别（1是男性，2是女性，0是未知）
				weixinUserInfo.setSex(jsonObject.getInteger("sex"));
				// 用户所在国家
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				weixinUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				weixinUserInfo.setCity(jsonObject.getString("city"));
				// 用户头像
				weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				
			
			  return weixinUserInfo;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return weixinUserInfo;
		
		
	}
	
	
	
	/**
	 * http get
	 * @param url
	 * @return
	 * @throws URISyntaxException 
	 * @throws MalformedURLException 
	 */
	private static String get(String url) throws URISyntaxException, MalformedURLException{
		String rs = null;
		
		URL url1 = new URL(url);
		URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet post = new HttpGet(uri);
		try {
			CloseableHttpResponse resp = null;
			resp = httpclient.execute(post);
			StatusLine status = resp.getStatusLine();
			if(status.getStatusCode()==200){
				HttpEntity entity = resp.getEntity();
				if(entity!=null){
					BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(entity);
					rs = EntityUtils.toString(bufferedEntity, "UTF-8");
				}
			}
		} catch (Exception e) {
			LOGGER.debug("HttpClient invoke error:" + e);
		}finally{
			try {
				if(httpclient!=null)
					httpclient.close();
			} catch (Exception e) {
				LOGGER.debug("HttpClient close error:"+e);
			}
		}
		return rs;
	}
	
	
	/**
	 * http post 提交
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, Map<String, Object> params){
		String rs = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		String jsonParam = JSONObject.toJSONString(params);
		try {
			StringEntity reqEntity= new StringEntity(jsonParam, ContentType.APPLICATION_JSON);
			post.setEntity(reqEntity);
			CloseableHttpResponse resp = null;
			try {
				resp = httpclient.execute(post);
				StatusLine status = resp.getStatusLine();
				if(status.getStatusCode()==200){
					HttpEntity entity = resp.getEntity();
					if(entity!=null){
						BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(entity);
						rs = EntityUtils.toString(bufferedEntity, "UTF-8");
					}
				}
			} catch (Exception e) {
			} finally {
				if(resp!=null){
					resp.close();
				}
			}
		}catch (Exception e) {
		}finally{
			try {
				if(httpclient!=null)
					httpclient.close();
			} catch (IOException e) {
			}
		}
		return rs;
	}
	
	
	/**
	 * 抓取access_token jsapi_ticket
	 */
	public synchronized static String fetchConfig(String access_token){
	
		
		Long invokeStartTime = new Date().getTime();
		
		
		String jsonStr;//微信返回信息
		JSONObject jsonObj;//微信返回信息Json对象
		String url;//调用url地址
	
		//step2 fetch api_ticket
		String jsapi_ticket = null;
		url = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", access_token);
		try {
			jsonStr = get(url);
			if(StringUtils.isNotEmpty(jsonStr)){
				jsonObj = JSON.parseObject(jsonStr);
				jsapi_ticket = jsonObj.getString("ticket");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.debug("###Duration: " + jsapi_ticket + (new Date().getTime() - invokeStartTime));
	
		return jsapi_ticket;
		
		
		
	}
	
	
	
	/**
	 * 获取微信接口参数 -7200后过期  重新获取
	 * @return
	 */
	public static WeChatProperties getWeChatProperties() {
		WeChatProperties properties = new WeChatProperties();
		properties = weChatPropertiesService.selectProperties().get(0);
	   Long now  = Long.parseLong(String.valueOf(System.currentTimeMillis()).substring(0, 10))-Long.parseLong(properties.getRefreshtime());
		if(now > 100 ){
			String accessToken = getAccessToken();
			String jsapi_ticket = fetchConfig(accessToken);
			//String api_ticket = AdvancedUtil.getCardTickect(accessToken);
			properties.setAccesstoken(accessToken);
			properties.setJsapiTicket(jsapi_ticket);
			//properties.setApiTicket(api_ticket);
			properties.setRefreshtime(String.valueOf(System.currentTimeMillis()).substring(0, 10));
			weChatPropertiesService.updateProperties(properties);
		}
		return properties; 
	}
	
	
    /**
	 * @Description：sign签名
	 * @param params 请求参数
	 * @param signType 签名类型 
	 * @return
	 */
	public static String sign(SortedMap<String, String> params,String signType) {
		StringBuffer sb = new StringBuffer();
		for(Map.Entry<String, String> entry : params.entrySet()){
			String key 	 = entry.getKey();
			String value = entry.getValue();
			if (null != value && !"".equals(value) && !"sign".equals(key) && !"key".equals(key)) {
				sb.append(key + "=" + value + "&");
			}
		}
		sb.append("key=" + Configsure.newInstance().getKey());
		
		String sign = "";
		System.out.println("签名前:" + params);
		if(signType.equalsIgnoreCase("MD5")){
			sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
		}else if(signType.equalsIgnoreCase("SHA1")){
			sign = Sha1Util.getSha1(sb.toString());
		}
		System.out.println("签名后：" + sign);
		return sign;
	}
	
	/**
	 * 生成二维码
	 */
	public static void generateQrocde() {
		QRCodeService qrCodeService = SpringContextHolder.getBean(QRCodeService.class);
		Page<Object> temporary = qrCodeService.temporary(String.valueOf(new Date().getTime()), 2592000);
		String qrcodeTicket = (String) temporary.getMap().get("ticket");
		if (StringUtils.isBlank(qrcodeTicket)) return;
		//LOGGER.debug("# 扫描二维码请关注:" + WechatConfig.newInstance().getNetworkPath() + "wap/qrcode");
		qrcodeTicket = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + qrcodeTicket;
		LOGGER.debug("# 创建临时二维码:" + qrcodeTicket);
	}
	
	

	

}
