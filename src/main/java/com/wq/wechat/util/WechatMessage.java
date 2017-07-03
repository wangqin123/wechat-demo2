package com.wq.wechat.util;


import java.util.HashMap;
import java.util.Map;
/**
 * 这是哪个写的，gong
 * 
 * @author wangqin
 *
 * @date 2017年7月3日 上午9:56:36
 */
public class WechatMessage{
	private Map<String, String> map = new HashMap<String, String>();
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	public String get(String key) {
		return this.map.get(key);
	}
	
}
