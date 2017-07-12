package com.wq.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wq.wechat.config.Configsure;
import com.wq.wechat.util.Sign1;
import com.wq.wechat.util.WchatHelper;


/**
 * @author hemf
 * 微信SDK配置
 */
@Controller("")
@RequestMapping("/js")
public class JSSDKController {
	
	/**
	 * 动态处理微信JSSDK 票据问题
	 * @param url
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/jssdk/config")
	public @ResponseBody Map<String, String> jssdConfig( String url) throws UnsupportedEncodingException{
		System.out.println("dfjdjkfjdkfjjfj-----------------------");
		url = url.split("\\#")[ 0 ];
		if(StringUtils.isNotEmpty(url)) url = URLDecoder.decode(url, "utf-8");
		//String jsapi_ticket = WchatHelper.fetchConfig(WchatHelper.getWeChatProperties().getAccesstoken());
		Map<String, String> sign = Sign1.sign(WchatHelper.getWeChatProperties().getJsapiTicket(), url);
		sign.put("appId", Configsure.newInstance().getAppid());
		System.out.println(JSONObject.toJSONString(sign));
		return sign;
	}
	
}
