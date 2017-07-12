package com.wq.wechat.inteceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wq.wechat.util.URLController;


/**
 * 系统后台拦截器
 * 
 * @author wangqin
 *
 * @date 2017年7月6日 上午11:47:16
 */
public class PrivilegeInteceptor implements HandlerInterceptor {

	private final static Logger log = LoggerFactory.getLogger(PrivilegeInteceptor.class);
 
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// System.out.println(response.getHeaderNames());
	}
	

// pao
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String url = request.getRequestURL().toString();//这个就是一个全路径  response.sendRedirect(url);  不影响
		String path = request.getScheme()+"://"+request.getServerName();
		String path1 = request.getServerName();
		String path3 = request.getRequestURI();
		HttpSession session = request.getSession();
		
		log.debug("收到请求：" + url);
		if (url.contains("center")) {
			
			String openId = (String) request.getAttribute("openId");
			if(StringUtils.isBlank(openId)){
				 //openId = (String) session.getAttribute("openId");
			}
			log.debug("请求opendId：" + openId);
			
			if (openId == null) {
				response.sendRedirect(path + "/center/error");
				return false;
			} else {
				if(URLController.isWxContainsUrl(path3)){
					return true;
				}
				
				
					
				}
		}else{
			response.sendRedirect(path+"/admin/login");
			return false;
		}
		
		return false;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
