package com.wq.wechat.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wq.wechat.base.BaseWapController;
import com.wq.wechat.bean.WeChatProperties;
import com.wq.wechat.bean.WeixinOauth2Token;
import com.wq.wechat.bean.userT;
import com.wq.wechat.config.Configsure;
import com.wq.wechat.prop.PropertyPlaceholderConfigurer;
import com.wq.wechat.service.IUserService;
import com.wq.wechat.util.WchatHelper;


@Controller
@RequestMapping("/wechat/auth2")
public class AuthorizeController extends BaseWapController{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthorizeController.class);
	
	@Autowired
	private IUserService userService;
	/**
	 * 微信菜单用户授权控制
	 * @param unLoginURIKey 未登录跳转页面
	 * @param loginURIKey 登陆跳转页面
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String authorize(){
		
		/*WeixinOauth2Token token = super.getCurrentOpenId();
		
		if (StringUtils.isBlank(token.getOpenId())) {
			return "/error/illegalAccess";
		}
	   userT  user =  userService.getUserById(1);
	   
	   System.out.println(JSONObject.toJSON(user));
	   
	   WeChatProperties  e =  WchatHelper.getWeChatProperties();
	   
	   System.out.println(JSONObject.toJSON(e));
		*/
		String r = PropertyPlaceholderConfigurer.getProperty("wechat.appsecret");
		System.out.println(r);
		
		return "/userinfo/demo";
	}
	
	
	
	
	

}
