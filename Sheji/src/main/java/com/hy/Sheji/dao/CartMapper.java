package com.hy.Sheji.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hy.Sheji.bean.Cart;
import com.hy.Sheji.bean.User;


public interface CartMapper {
     
	//查询指定用户的购物车
	@Select("select * from hy_cart where c_uid=#{cUid}")
	@Results({
		@Result(column="c_uid",property="cUid"),
		@Result(column="c_pid",property="product",
		one=@One(select="com.hy.Sheji.dao.ProductMapper.selectPro"))
		})
	List<Cart> selectCart(int cUid);
	
	//查询登录用户的信息
		@Select("select * from hy_user where u_name=#{uName}")
		 
		User selectUer(String uName);
	
	  //查询登录用户是否有某件商品 
				@Select("select count(*) from hy_cart where c_uId=#{cUid} and c_pid=#{cPid}")
				 
				int selectupId(int cUid,int cPid);
	
	//商品到添加购物车
	@Insert("insert into hy_cart values(null,#{cUid},#{cPid},#{cCount})")
	int addCart(Cart cart);
	
	//添加购物车中商品的数量
	@Update("update hy_cart set c_count=c_count+#{cCount} where c_uid=#{cUid} and c_pid=#{cPid}")
     int addupdate(int cUid,int cPid,int cCount);
	
	//减少购物车中商品的数量
		@Update("update hy_cart set c_count=c_count-1 where c_uid=#{cUid} and c_pid=#{cPid}")
	     int deupdate(int cUid,int cPid);
	
	//删除购物车中的某商品类商品
	@Delete("delete from hy_cart where c_id=#{cId}")
	int deleteByCid(int cart);

	@Delete("delete from hy_cart where c_uid=#{cUid}")
	int deleteBycUid(int cUid);
	
}
