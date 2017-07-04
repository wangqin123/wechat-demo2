package com.wq.wechat.service;

import java.util.List;

import com.wq.wechat.bean.WeChatProperties;

public interface WeChatPropertiesService {
	
	/**
	 * 查询properties
	 * @return
	 */
	List<WeChatProperties> selectProperties();
	
	/**
	 * 更新properties
	 * @return
	 */
	Integer updateProperties(WeChatProperties weChatProperties);
	
	
	

}
