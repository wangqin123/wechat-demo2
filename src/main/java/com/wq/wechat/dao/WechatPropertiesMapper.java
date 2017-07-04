package com.wq.wechat.dao;

import java.util.List;

import com.wq.wechat.bean.WeChatProperties;

public interface WechatPropertiesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WeChatProperties record);

    WeChatProperties selectByPrimaryKey(Long id);

    List<WeChatProperties> selectAll();

    int updateByPrimaryKey(WeChatProperties record);
}