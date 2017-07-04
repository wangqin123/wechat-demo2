package com.wq.wechat.bean;

public class WeChatProperties {
	
	private int id;
	
	private String accesstoken;
	
	private String jsapiTicket;
	
	private String apiTicket;
	
	private String refreshtime;

	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	public String getJsapiTicket() {
		return jsapiTicket;
	}

	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}

	public String getApiTicket() {
		return apiTicket;
	}

	public void setApiTicket(String apiTicket) {
		this.apiTicket = apiTicket;
	}

	public String getRefreshtime() {
		return refreshtime;
	}

	public void setRefreshtime(String refreshtime) {
		this.refreshtime = refreshtime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	

}
