package com.hy.Sheji.dao;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;

import com.hy.Sheji.bean.Product;

public interface ProductMapper {

	//首页商城精选商品展示
	@Select("select * from hy_product where p_hot=1")
	List<Product> selectIndexhot();
	
	//产品详情展示
		@Select("select * from hy_product where p_id=#{pId}")
		@Results({
			 @Result(column="p_id",property="pId"),
			 @Result(column="p_aid",property="pAid"),
			 @Result(column="p_aid",property="area",
					 one=@One(select="com.hy.Sheji.dao.SpecialMapper.selArea" ))
		 })
		Product selectPro(int pId);
	
	 }
