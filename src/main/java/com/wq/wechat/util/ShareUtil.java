package com.wq.wechat.util;

import com.wq.wechat.bean.ShareInfo;
import com.wq.wechat.config.Configsure;

public class ShareUtil{
	
	private static String APPID = Configsure.newInstance().getAppid();
	
	private static String DOMAIN = Configsure.newInstance().getDomin();
	
	private static String link = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx322330a266a36785&redirect_uri=http://1b5a938757.51mypc.cn/center/realURL&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
	
	private static String imgurl = DOMAIN + "/assets/images/btn_xianjindai.png";
	
	

	
	/**
	 * 健康宝主页
	 */
	public static ShareInfo healthindex(){
		String realurl = "userinfo/index";
		ShareInfo share = new ShareInfo();
		String url = link.replace("realURL", realurl);
		share.setLink(url);
		share.setImgurl(imgurl);
		share.setTitle("分享标题");
		share.setDesc("让世界更加美好");
		return share;
	}
	


	
	

}
