package com.wq.wechat.config;

public class Configsure {
	
	private static String appid="wx322330a266a36785";
	
	private static String appsecret="bbd2ea7859c2dfadf82f162a53d01516";
	
	private static String token="wqwqwqwqwqwq";
	
	private static String domin = "http://1b5a938757.51mypc.cn";
	
	

	public static String getDomin() {
		return domin;
	}

	public static void setDomin(String domin) {
		Configsure.domin = domin;
	}

	public static String getAppid() {
		return appid;
	}

	public static void setAppid(String appid) {
		Configsure.appid = appid;
	}

	public static String getAppsecret() {
		return appsecret;
	}

	public static void setAppsecret(String appsecret) {
		Configsure.appsecret = appsecret;
	}

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		Configsure.token = token;
	}
	
	

}
