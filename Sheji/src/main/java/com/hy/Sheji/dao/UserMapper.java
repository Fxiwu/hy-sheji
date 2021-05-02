package com.hy.Sheji.dao;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hy.Sheji.bean.Address;
import com.hy.Sheji.bean.Order;
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
		
		//默认地址   order中调用  useraction中addaddress\ modfydft调用
		@Select("select * from hy_address where add_uid=#{addUid} and add_dft=1")
		public  Address  seAddressdft(int addUid);
	
		 
	//
	@Select("select * from hy_user where u_id=#{uid}")
	public User selectByuId(String uid);
	
	
	//更改过List<Address>改为Address,orderAction zhifu中调用
	@Select("select * from hy_address where add_id=#{addId}")
	public  Address  selectAddressByaid(int addId);

	//添加地址
	@Insert("insert into hy_address values("
			+ "null,#{addUid},#{addAddr},#{addPhone},#{addName},#{addDft})")
	public int addaddress(Address address);
	
	//删除地址 useraction 中delAddr
		@Delete("delete hy_address  where add_id=#{addId}")
		public int delAddr(int addId);
	
	//设为默认地址 useraction 中modfydft
	@Update("update hy_address set add_dft=1 where add_id=#{addId}")
	public int modfydft(int addId);

	//修改原本的默认地址为不默认 useraction中addaddress调用
	@Update("update hy_address set add_dft=0 where add_uid=#{uId}")
	public int setAddressDft(int uId);

	 //use_order中个人order展示
	 @Select("select * from  hy_order where o_uid=#{ouid} order by o_createtime desc ")
	 	@Results({@Result(column="o_uid",property="user",
	 			          one=@One(select="com.hy.Sheji.dao.UserMapper.selectByuId")),
		 		  @Result(column="o_id",property="oId"),
		 		 @Result(column="o_id",property="ordertail",
		                  one=@One(select="com.hy.Sheji.dao.OrderMapper.selectOrderdetail")),
	              @Result(column="o_addid",property="address",
	            		   one=@One(select="com.hy.Sheji.dao.UserMapper.selectAddressByaid"))
	            		  })
	public List<Order> userorder(int ouid);
}
