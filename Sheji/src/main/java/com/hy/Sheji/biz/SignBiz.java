package com.hy.Sheji.biz;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.Sheji.bean.User;
import com.hy.Sheji.dao.SignMapper;
 
@Service
public class SignBiz {

	@Resource
	private SignMapper sm;
	
	//注册
	public void register(User user) throws BizException{
		if(sm.ByPhone(user.getuPhone())>0){
			
			throw new BizException("该手机号已注册账户");
		}
		sm.Register(user);
		
	}
	
	//登录
		public User login(User user) throws BizException {
			User dbuser=sm.selectSign(user);
			if(dbuser==null) {
				 
				throw new BizException("用户名或密码或身份错误");
			}
			return dbuser;
		}
}
