package com.wq.wechat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wq.wechat.bean.WeChatProperties;
import com.wq.wechat.bean.WeixinOauth2Token;
import com.wq.wechat.bean.WeixinUserInfo;
import com.wq.wechat.bean.userT;
import com.wq.wechat.service.IUserService;
import com.wq.wechat.util.WchatHelper;


@Controller
@RequestMapping("/wechat/auth2")
public class AuthorizeController {
	
	
	
	@Autowired
	private IUserService userService;
	/**
	 * 微信菜单用户授权控制
	 * @param unLoginURIKey 未登录跳转页面
	 * @param loginURIKey 登陆跳转页面
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String authorize(HttpServletRequest req){
		System.out.println(req.getParameter("code"));
		//1,根据code获取access_token
	   WeixinOauth2Token  weixinOauth2Token =  WchatHelper.getAccToken(req.getParameter("code"));
	   
	   //2,拉取用户信息(需scope为 snsapi_userinfo)
	   WeixinUserInfo  wechatUserinfo =  WchatHelper.getwechatUserInfo(weixinOauth2Token.getAccessToken(),weixinOauth2Token.getOpenId());
	   
	   System.out.println(wechatUserinfo.toString());
	   
	   userT  user =  userService.getUserById(1);
	   
	   System.out.println(user.toString());
	   
	   WeChatProperties  e =  WchatHelper.getWeChatProperties();
	   
	   System.out.println(JSONObject.toJSON(e));
		return "/index";
	}
	
	@ResponseBody
	@RequestMapping(value = "/addInfo/v1", method = RequestMethod.GET)// 前端
	public String addCustDetailInfo(HttpServletRequest req) {
		String str =null;
		System.out.println("123"+str);
		System.out.println(req.getParameter("name"));
		return "hello";
	}
	
	
	

}
