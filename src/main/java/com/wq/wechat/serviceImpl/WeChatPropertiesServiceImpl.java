package com.wq.wechat.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wq.wechat.bean.WeChatProperties;
import com.wq.wechat.dao.WechatPropertiesMapper;
import com.wq.wechat.service.WeChatPropertiesService;



/**
 * 
 * 
 * @author wangqin
 *
 * @date 2017年7月4日 上午9:43:30
 */
@Service("weChatPropertiesService")
public class WeChatPropertiesServiceImpl implements WeChatPropertiesService {

	private @Autowired WechatPropertiesMapper weChatPropertiesMapper;

	public List<WeChatProperties> selectProperties() {
		return weChatPropertiesMapper.selectAll();
	}

	public Integer updateProperties(WeChatProperties dto) {
		return weChatPropertiesMapper.updateByPrimaryKey(dto);
	}
	

}
