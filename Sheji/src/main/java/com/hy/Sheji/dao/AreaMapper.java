package com.hy.Sheji.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hy.Sheji.bean.Area;
import com.hy.Sheji.bean.Product;

public interface AreaMapper {
	//Area界面展示	
	@Select("select * from hy_area ")
	List<Area> selectArea();
	
	//Area_detail界面展示
 	@Select("select * from hy_area where a_id=#{aId}")
	Area selectAreaD(int aId);
	 
}
