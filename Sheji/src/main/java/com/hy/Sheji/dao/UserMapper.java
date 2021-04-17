package com.hy.Sheji.dao;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hy.Sheji.bean.Address;
import com.hy.Sheji.bean.User;

public interface UserMapper {

	
	@Select("select * from hy_user where u_name=#{uName}")
	public User selectByuName(String uName);
	
	//从User中查询用户收货地址
	@Select("select * from hy_user where u_name=#{uName}")
	@Results({@Result(column="u_name",property="uName"),
		      @Result(column="u_id",property="uId"),
		      @Result(column="u_id",property="address",
		    		  one=@One(select="com.hy.Sheji.dao.UserMapper.seAddress"))
		
	})
	public User selectAddress(String uName);
	
	@Select("select * from hy_address where add_uid=#{addUid}")
	public List<Address> seAddress(int addUid);
	
	//从User中查询用户收货地址的默认地址
		@Select("select * from hy_user where u_name=#{uName}")
		@Results({@Result(column="u_name",property="uName"),
			      @Result(column="u_id",property="uId"),
			      @Result(column="u_id",property="address",
			    		  one=@One(select="com.hy.Sheji.dao.UserMapper.seAddressdft"))
			
		})
		public User selectAddressDef(String uName);
		
		@Select("select * from hy_address where add_uid=#{addUid} and add_dft=1")
		public  Address  seAddressdft(int addUid);
	
		 
	//
	@Select("select * from hy_user where u_id=#{uid}")
	public User selectByuId(String uid);
	
	
	//更改过List<Address>改为Address,orderAction zhifu中调用
	@Select("select * from hy_address where add_id=#{addId}")
	public  Address  selectAddressByaid(int addId);
}
