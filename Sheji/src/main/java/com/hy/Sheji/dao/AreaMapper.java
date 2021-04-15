package com.hy.Sheji.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hy.Sheji.bean.Area;
import com.hy.Sheji.bean.Product;

public interface AreaMapper {
	//Area界面展示	
	@Select("select * from hy_area ")
	List<Area> selectArea();
	
	//Area_detail界面展示
 	@Select("select * from hy_area where a_id=#{aId}")
	Area selectAreaD(int aId);

	//后台商品管理  area.html 地区添加
 	@Insert("insert into hy_area values(null,#{aShi},"
 			+ "#{aXian},#{aImg},#{aJieshao},#{aWeizhi},#{aTese}"
 			+ ",#{aLvyou},#{aPhoto})")
	int insertarea(Area area);

 	//后台商品管理  area.html 地区修改
 		 String sql="<if  test='aShi!=null'>,a_shi = #{aShi} </if>" 
 		 			+" <if  test='aXian!=null'>,a_xian = #{aXian} </if> "
 		 			+ "<if  test='aImg!=null'>,a_img = #{aImg} </if>"
 		 			+ "<if  test='aJieshao!=null'>,a_jieshao = #{aJieshao} </if>"
 		 			+ "<if  test='aWeizhi!=null'>,a_weizhi = #{aWeizhi} </if>"
 		 			+ "<if  test='aTese!=null'>,a_tese = #{aTese} </if>"
 		 			+ "<if  test='aLvyou!=null'>,a_lvyou = #{aLvyou} </if>"
 		 			+ "<if  test='aPhoto!=null'>,a_photo = #{aPhoto} </if>" ;
 	  	@Update("<script> update hy_area "
 	  			+ "<set>  "
 	 			 
 	 			+ sql
 	 			+"</set>" 
 	 			+ " where a_id=#{aId} </script>")
	    int updatearea(Area area);

 	 //后台商品管理  area.html 地区删除
 	  	@Delete("delete from hy_area where a_id=#{aId}")
		int delarea(int aId);
	 
}
