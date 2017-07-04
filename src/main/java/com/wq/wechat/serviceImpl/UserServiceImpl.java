package com.wq.wechat.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wq.wechat.bean.userT;
import com.wq.wechat.dao.userTMapper;
import com.wq.wechat.service.IUserService;


@Service("userService")  
public class UserServiceImpl implements IUserService {  
	@Autowired  
  private userTMapper mapper;

	public userT getUserById(int userId) {
		userT  t = mapper.selectByPrimaryKey(userId);
		System.out.println(t.getUserName());
		return t;
	}
}
    
