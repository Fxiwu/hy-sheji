package com.hy.Sheji.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hy.Sheji.bean.Product;

public interface MallMapper {
	//商城mall商品界面展示
	
	@Select("select * from hy_product where p_cid=1 order by p_createtime desc")
	List<Product> selectMall();
	
	
	//商城mall商品界面分类展示
	
			@Select("select * from hy_product where p_cid=#{pCid} order by p_createtime desc")
			List<Product> selectCategory(int pCid);

}
