package com.hy.Sheji.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hy.Sheji.bean.Product;

public interface ProductMapper {

	//首页商城精选商品展示
	@Select("select * from hy_product where p_hot=1")
	List<Product> selectIndexhot();
	
	 }
