package com.wq.wechat.dao;

import java.util.List;

import com.wq.wechat.bean.userT;

public interface userTMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(userT record);

    userT selectByPrimaryKey(Integer id);

    List<userT> selectAll();

    int updateByPrimaryKey(userT record);
}