package com.hy.Sheji.dao;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hy.Sheji.bean.Area;
import com.hy.Sheji.bean.Product;

public interface SpecialMapper {
	//Special界面展示
	
	@Select("select * from hy_area ")
	 
	List<Area> selectArea();
	
	//Special_detail界面展示
	@Select("select * from hy_product where p_aid=#{pAid}")
	 @Results({
			 @Result(column="p_aid",property="pAid"),
			 @Result(column="p_aid",property="area",
		        one=@One(select="com.hy.Sheji.dao.SpecialMapper.selArea" ))
         })
	List<Product> selectProduct(int pAid);
	
	 @Select("select * from hy_area where a_id=#{pAid}")
	 Area selArea(int pAid);
	 //搜索查询
	 @Select("select * from hy_area where a_shi like '%${txt}%' or a_xian like '%${txt}%' ")
	List<Area> souaddr(String txt);
	 
}
