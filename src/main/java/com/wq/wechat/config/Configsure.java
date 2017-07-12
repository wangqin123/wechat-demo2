package com.wq.wechat.config;

import com.wq.wechat.holder.SpringContextHolder;

public class Configsure {
	
	private  String appid;
	
	private  String appsecret;
	
	private  String token;
	
	private  String domin;
	
	
	private  String key;
	
	
	public String getAppid() {
		return appid;
	}


	public void setAppid(String appid) {
		this.appid = appid;
	}


	public String getAppsecret() {
		return appsecret;
	}


	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getDomin() {
		return domin;
	}


	public void setDomin(String domin) {
		this.domin = domin;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public Configsure(String appid, String appsecret, String token, String domin, String key) {
		super();
		this.appid = appid;
		this.appsecret = appsecret;
		this.token = token;
		this.domin = domin;
		this.key = key;
	}


	public Configsure() {}

	/**
	 * 获取实例
	 * @return
	 */
	public static Configsure newInstance() {
		return SpringContextHolder.getBean(Configsure.class);
	}
	
	
	
	
	
	
	
	
	

}
