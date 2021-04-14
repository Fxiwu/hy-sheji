package com.hy.Sheji.biz;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.Sheji.bean.User;
import com.hy.Sheji.dao.UserMapper;

@Service
public class UserBiz {

	@Resource
	private UserMapper um;
	
	public User selectByuName(String uName)   {
		User user=um.selectByuName(uName);
		/*
		 * if(user==null) { throw new BizException("没有该用户！"); }
		 */
		return user;
		
	}
	
	public User selectAddress(String uName) {
	     
		return um.selectAddress(uName);
		
	}
}
