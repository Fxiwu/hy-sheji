package com.hy.Sheji.biz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.Sheji.bean.Address;
import com.hy.Sheji.bean.AdminOrder;
import com.hy.Sheji.bean.Comment;
import com.hy.Sheji.bean.Order;
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

	public void addaddress(Address address) {
		 um.addaddress(address);
	}

	//修改原本的默认地址为不默认
	public int setAddressDft(int uId) {
		// TODO Auto-generated method stub
		return um.setAddressDft(uId);
	}
	
	 //use_order中个人order展示
		public List<Order> userorder(int ouid) {
			// TODO Auto-generated method stub
			  return um.userorder( ouid);
		}
		 //use_order中 进行评论
		public int addComment(Comment comm) {
			 
			return  um.addComment(comm);
		}

		//user中修改头像
		public int updatTou(String f,int uId) {
			 return um.updatTou(f,uId);
		}
}
