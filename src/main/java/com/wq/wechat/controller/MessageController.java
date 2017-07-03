package com.wq.wechat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wq.wechat.config.Configsure;
import com.wq.wechat.service.CoreService;
import com.wq.wechat.util.SHA1;

@Controller()
@RequestMapping("/wechat")
public class MessageController {
	
	  @Autowired
	    private CoreService coreService;
    /**
     * 接入消息
     * @throws IOException 
     */
    @RequestMapping(method = RequestMethod.GET)
    public void access(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        String reSignature = null;
        try {
            String[] str = { Configsure.getToken(), timestamp, nonce };
            Arrays.sort(str);
            String bigStr = str[0] + str[1] + str[2];
            reSignature = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != reSignature && reSignature.equals(signature)) {
            // 请求来自微信
            out.print(echostr);
        } else {
            out.print("error request! the request is not from weixin server");
        }
        out.flush();
        out.close();
    }
    
    /**
     * 接入消息
     * @throws IOException 
     */
    @RequestMapping(method = RequestMethod.POST)
    public void process(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 调用核心业务类接收消息、处理消息
        String respMessage = coreService.processRequest(request);

        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
    }
	

}
