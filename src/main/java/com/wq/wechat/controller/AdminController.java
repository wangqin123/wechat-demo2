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
@RequestMapping("/admin")
public class AdminController {
	
	@ResponseBody
	@RequestMapping(value = "/admin/v1", method = RequestMethod.GET)// 前端
	public userT addCustDetailInfo(HttpServletRequest req) {
		userT user = new userT();
		user.setAge(25);
		String str =null;
		System.out.println("admin"+str);
		System.out.println(req.getParameter("name"));
		return user;
	}
	
	
	@RequestMapping("login")
	public  ModelAndView medicalnsuranceDetails(){
		ModelAndView modelAndView = new ModelAndView("/admin/login/demo");
		ShareInfo info = ShareUtil.healthindex();
		modelAndView.addObject("share",info);
		return modelAndView;
	}
	
	
	

}
