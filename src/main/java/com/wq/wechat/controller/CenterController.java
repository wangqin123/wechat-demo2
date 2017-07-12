package com.wq.wechat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wq.wechat.bean.ShareInfo;
import com.wq.wechat.bean.userT;
import com.wq.wechat.util.ShareUtil;


@Controller
@RequestMapping("center")
public class CenterController {
	
	@ResponseBody
	@RequestMapping(value = "/addInfo/v1", method = RequestMethod.POST)// 前端
	public userT addCustDetailInfo(HttpServletRequest req) {
		userT user = new userT();
		user.setAge(25);
		String str =null;
		System.out.println("123"+str);
		System.out.println(req.getParameter("name"));
		return user;
	}
	
	@RequestMapping("medicalnsuranceDetails.html")
	public  ModelAndView medicalnsuranceDetails(){
		ModelAndView modelAndView = new ModelAndView("/userinfo/index");
		ShareInfo info = ShareUtil.healthindex();
		modelAndView.addObject("share",info);
		return modelAndView;
	}
	
	@RequestMapping("userinfo.html")
	public  ModelAndView userinfo(){
		ModelAndView modelAndView = new ModelAndView("/userinfo/index2");
		ShareInfo info = ShareUtil.healthindex();
		modelAndView.addObject("share",info);
		return modelAndView;
	}
	
	@RequestMapping("error")
	public @ResponseBody ModelAndView error(){
		return new ModelAndView("/error/illegalAccess");
	}
	
	
	

}
