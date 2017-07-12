package com.wq.wechat.util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wq.wechat.bean.Button;
import com.wq.wechat.bean.ClickButton;
import com.wq.wechat.bean.Menu;
import com.wq.wechat.bean.ViewButton;
import com.wq.wechat.config.Configsure;



public class MenuManager{
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		boolean result = false;
		int shu = 0;
		String jsonString = "";
		
		// 调用接口获取access_token
		JSONObject jsonObj;//微信返回信息Json对象
		String url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx322330a266a36785&secret=bbd2ea7859c2dfadf82f162a53d01516";
		
		String jsonStr =get(url);
		
		String access_token = "";
		
		if(StringUtils.isNotEmpty(jsonStr)){
			jsonObj = JSON.parseObject(jsonStr);
			access_token = jsonObj.getString("access_token");
		}
		
		
			// 调用接口创建菜单
			result = MenuUtil.createMenu(getMenu(), access_token);
			
			WchatHelper.generateQrocde(); 
			
			shu++;
			// 判断菜单创建结果
			if (result) {
				System.out.println("成功");
			} else {
				System.out.println("菜单创建失败，错误码：" + result);
			}
		
		//查询菜单项
//		 if(result == 0 && shu > 0){
		// jsonString = MenuUtil.getMenu(access_token);
		 System.out.println("菜单查询结果:"+jsonString);
//		 }


	}
	
	
	
	/**
	 * http get
	 * @param url
	 * @return
	 */
	private static String get(String url){
		String rs = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet post = new HttpGet(url);
		try {
			CloseableHttpResponse resp = null;
			resp = httpclient.execute(post);
			StatusLine status = resp.getStatusLine();
			if(status.getStatusCode()==200){
				HttpEntity entity = resp.getEntity();
				if(entity!=null){
					BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(entity);
					rs = EntityUtils.toString(bufferedEntity, "UTF-8");
				}
			}
		} catch (Exception e) {
			System.out.println("HttpClient invoke error:" + e);
		}finally{
			try {
				if(httpclient!=null)
					httpclient.close();
			} catch (Exception e) {
				System.out.println("HttpClient close error:");
			}
		}
		return rs;
	}
	

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	
	private static Menu getMenu() throws UnsupportedEncodingException {
		
		ViewButton btn6 = new ViewButton();
		btn6.setType("view");
		btn6.setName("\u2600元活动");
		btn6.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx322330a266a36785&redirect_uri=http://1b5a938757.51mypc.cn/wechat/auth2/login&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");

		
		ClickButton btn4 = new ClickButton();
		btn4.setType("click");
		btn4.setName("透明与监督");
		btn4.setKey("contactUsEvent");
		
	
		Menu menu = new Menu();
		menu.setButton(new Button[]{btn6,btn4});
		return menu;
		
	}
		
}
