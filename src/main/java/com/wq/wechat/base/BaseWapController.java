package com.wq.wechat.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wq.wechat.bean.WeixinOauth2Token;
import com.wq.wechat.bean.WeixinUserInfo;
import com.wq.wechat.util.WchatHelper;


public class BaseWapController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseWapController.class);

	public Boolean getCurrentLoginStatus() {
		return false;
	}

	public WeixinOauth2Token getCurrentOpenId() {
		HttpServletRequest request = super.getRequest();
		HttpSession session = request.getSession();
		
		WeixinOauth2Token  token = new WeixinOauth2Token();
		token.setOpenId((String) request.getAttribute("openId"));
		
		String openId = (String) request.getAttribute("openId");
		
		if (StringUtils.isBlank(openId)){
			token.setOpenId( (String) session.getAttribute("openId"));
			openId = (String) session.getAttribute("openId");
		}
		
		if (StringUtils.isBlank(openId)) {
			String code = request.getParameter("code");//获取授权code代码
			if (StringUtils.isNotBlank(code) && !code.equals(session.getAttribute("cacheSessionCode"))) {
				session.setAttribute("cacheSessionCode", code);
				logger.debug("# wechat > auth2 > code:" +  code);
				if (StringUtils.isNotBlank(code)) {
					//1.通过code换取网页授权access_token
					token = WchatHelper.getAccToken(code);
					 //2,拉取用户信息(需scope为 snsapi_userinfo)
					
					   WeixinUserInfo  wechatUserinfo =  WchatHelper.getwechatUserInfo(token.getAccessToken(),token.getOpenId());
					   
					   logger.debug("# wechat > auth2 > wechatUserinfo:" +  wechatUserinfo.toString());
					   
					request.setAttribute("openId", token.getOpenId());
					session.setAttribute("openId", token.getOpenId());
				} 
			}
		}
		logger.debug("# wechat > auth2 > openid:" +  openId);
		return token;
	}

}
