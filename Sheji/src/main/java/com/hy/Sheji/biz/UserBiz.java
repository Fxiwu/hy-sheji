package com.hy.Sheji.biz;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.Sheji.bean.User;
import com.hy.Sheji.dao.UserMapper;

@Service
public class UserBiz {

	@Resource
	private UserMapper um;
	
	public User selectByuName(String uName) {
	     
		return um.selectByuName(uName);
		
	}
	
	public User selectAddress(String uName) {
	     
		return um.selectAddress(uName);
		
	}
}
