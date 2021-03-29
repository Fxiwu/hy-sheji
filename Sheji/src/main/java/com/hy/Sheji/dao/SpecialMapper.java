package com.hy.Sheji.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hy.Sheji.bean.Area;
import com.hy.Sheji.bean.Product;

public interface SpecialMapper {
	//Special界面展示
	
	@Select("select * from hy_area ")
	List<Area> selectArea();
	
	@Select("select * from hy_product where p_aid=#{pAid}")
	List<Product> selectProduct(int pAid);
	 
}
