package com.hy.Sheji.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.hy.Sheji.bean.User;

public interface SignMapper {

	//注册
	@Insert("insert into hy_user values(null,#{uName},#{uPassword},#{uPhone},#{uSex},now(),#{uStatus},#{uImg})")
	public int Register(User user);
	
	//注册时进行同名验证
		@Select("select count(*) from hy_user where u_name=#{uName}")
		public int ByName(String uName);
	
	//注册时进行手机号码验证，同一个手机号码只能注册一个用户
	@Select("select count(*) from hy_user where u_phone=#{uPhone}")
	public int ByPhone(String uPhone);
	
	//登录
	@Select("select * from hy_user where u_name=#{uName} and u_password=#{uPassword} and u_status=#{uStatus}")
	public  User selectSign(User user);
	
}
