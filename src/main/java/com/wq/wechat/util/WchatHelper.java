package com.wq.wechat.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wq.wechat.bean.WeixinOauth2Token;
import com.wq.wechat.bean.WeixinUserInfo;
import com.wq.wechat.config.Configsure;

public class WchatHelper {
	
	
	private static final String ACCESS_TOKEN_URL = " https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	private static final String GET_USERINFO ="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	private static final String REF_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

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
		url = ACCESS_TOKEN_URL.replace("APPID", Configsure.getAppid()).replace("SECRET", Configsure.getAppsecret()).replace("CODE", code);
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
		url = REF_TOKEN.replace("APPID", Configsure.getAppid()).replace("REFRESH_TOKEN ", refreshToken);
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
			System.out.println("HttpClient invoke error:" + e);
		}finally{
			try {
				if(httpclient!=null)
					httpclient.close();
			} catch (Exception e) {
				System.out.println("HttpClient close error:"+e);
			}
		}
		return rs;
	}
	

}
