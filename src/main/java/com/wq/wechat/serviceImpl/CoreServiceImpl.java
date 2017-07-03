package com.wq.wechat.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.wq.wechat.res.Article;
import com.wq.wechat.res.NewsMessage;
import com.wq.wechat.res.TextMessage;
import com.wq.wechat.service.CoreService;
import com.wq.wechat.util.MessageUtil;
import com.wq.wechat.util.WechatMessage;

/**
 * @author hemf
 * 微信服务处理类
 */
@Service("coreServiceImpl")
public class CoreServiceImpl implements CoreService {
	
	@Autowired private WechatMessage wechatMessage;
	
	private static String SITENAME="";

	public String processRequest(HttpServletRequest request) {
	     String respMessage = null;
	        try {
	        	
	            // 默认返回的文本消息内容
	            String respContent = "";

	            // xml请求解析
	            Map<String, String> requestMap = MessageUtil.parseXml(request);

	            // 发送方帐号（open_id）
	            String fromUserName = requestMap.get("FromUserName");
	            // 公众帐号
	            String toUserName = requestMap.get("ToUserName");
	            // 消息类型
	            String msgType = requestMap.get("MsgType");
	            // 回复文本消息
	            TextMessage textMessage = new TextMessage();
	            textMessage.setToUserName(fromUserName);
	            textMessage.setFromUserName(toUserName);
	            textMessage.setCreateTime(new Date().getTime());
	            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	            textMessage.setFuncFlag(0);
	            
	            // 文本消息
	            if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
	            	// 内容
	                String Content = requestMap.get("Content");
	                respContent = "额，这个问题有点难度...<a href='http://www.baidu.com/s?wd="+Content+"&rsv_spt=1&issp=1&rsv_bp=0&ie=utf-8&tn=baiduhome_pg&rsv_sug3=8&rsv_sug4=714&rsv_sug1=8&rsv_sug2=0&inputT=4403&rsv_sug=1'>点击这里便明了..</a>";
	            }
	            // 图片消息
	            else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
	                respContent = "这是一张图片";
	            }
	            // 地理位置消息
	            else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
	            	if (requestMap.containsKey("Latitude")) System.out.println("纬度 :" + requestMap.get("Latitude"));
	            	if (requestMap.containsKey("Longitude")) System.out.println("经度 :" + requestMap.get("Latitude"));
	            	if (requestMap.containsKey("Precision")) System.out.println("精度  :" + requestMap.get("Latitude"));
	                respContent = "欢迎使用"+SITENAME+"微信，谢谢您对我们的支持！";
	            }
	            // 链接消息
	            else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
	                respContent = "欢迎使用"+SITENAME+"微信，谢谢您对我们的支持！";
	            }
	            // 音频消息
	            else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
	                respContent = "欢迎使用"+SITENAME+"微信，谢谢您对我们的支持！";
	            }
	            // 事件推送
	            else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
	                // 事件类型
	                String eventType = requestMap.get("Event");
	                // 订阅
	                if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
	                    respContent = "欢迎使用"+SITENAME+"微信，谢谢您对我们的支持！";
	                }
	                // 取消订阅
	                else if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
	                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
	                }
	                // 自定义菜单点击事件
	                else if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_CLICK)) {
	                    // 事件KEY值
	                    String eventKey = requestMap.get("EventKey");
	                    respContent = null;
	                    
	                    
	                    String key = "contactUsEvent";
	                    if (StringUtils.isBlank(respContent) && key.equals(eventKey)) {
	                    	respContent = wechatMessage.get(key);
	                    }
	                    if (eventKey.startsWith("imageMessageEvent")) {

	                        NewsMessage newsMessage = new NewsMessage();
	    					newsMessage.setToUserName(fromUserName);
	    					newsMessage.setFromUserName(toUserName);
	    					newsMessage.setCreateTime(new Date().getTime());
	    					newsMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);

	    					List<Article> articleList = new ArrayList<Article>();
	    					Article article = new Article();
	    					article.setTitle(wechatMessage.get(eventKey+ "Title"));
	    					article.setDescription(wechatMessage.get(eventKey+ "Description"));	
	    					article.setPicUrl(wechatMessage.get(eventKey+ "PicUrl"));
	    					article.setUrl(wechatMessage.get(eventKey+ "Url"));
	    					
	    					articleList.add(article);
	    					// 设置图文消息个数
	    					newsMessage.setArticleCount(articleList.size());
	    					// 设置图文消息包含的图文集合
	    					newsMessage.setArticles(articleList);
	    					// 将图文消息对象转换成xml字符串
	    					respContent = MessageUtil.newsMessageToXml(newsMessage);
	                    }
	                    
	                    
	                    if (StringUtils.isBlank(respContent)) respContent = "该功能正在研发中,敬请关注...";
	                    
	                } else if (eventType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
	                    System.out.println(JSON.toJSONString(requestMap));
	                  /*  //{"FromUserName":"odsbDuB5eojCgXc0tLVNb5zhIUMM","Event":"LOCATION","Precision":"150.000000","CreateTime":"1449715207","Latitude":"30.631975","Longitude":"104.073975","ToUserName":"gh_5211400c28dd","MsgType":"event"}
	                    if (requestMap.containsKey("Latitude")) SessionUtil.put(SessionUtil.MAP_LOCATION_LATITUDE, requestMap.get("Latitude"));
	                	if (requestMap.containsKey("Longitude")) SessionUtil.put(SessionUtil.MAP_LOCATION_LONGITUDE, requestMap.get("Longitude"));
	                	if (requestMap.containsKey("Precision")) SessionUtil.put(SessionUtil.MAP_LOCATION_PRECISION, requestMap.get("Precision"));
	                	if (requestMap.containsKey("CreateTime")) SessionUtil.put(SessionUtil.MAP_LOCATION_TIME, requestMap.get("CreateTime"));
	                    System.out.println(SessionUtil.get(SessionUtil.MAP_LOCATION_LATITUDE));*/
	                }
	            }

	            if (StringUtils.isNotEmpty(respContent)) {
	                textMessage.setContent(respContent);
	                respMessage = MessageUtil.textMessageToXml(textMessage);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return respMessage;
	}

}



