package com.wq.wechat.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 核心服务类
 */
public interface CoreService {
	
    /**
     * 处理微信发来的请求
     * 
     * @param request
     * @return
     */
    String processRequest(HttpServletRequest request);
}
