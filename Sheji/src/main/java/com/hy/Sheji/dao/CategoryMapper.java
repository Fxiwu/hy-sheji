package com.hy.Sheji.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.hy.Sheji.bean.Category;

public interface CategoryMapper {

	//后台商品管理  product.html category.html类别选项
	@Select("select * from hy_category")
 	List<Category> category();

	@Delete("delete from hy_category where pc_id=#{id}")
	int delcategory(int id);

	 //后台类别管理  category.html 添加类别 
	@Insert("insert into hy_category values(null,#{cname})")
	int addcategory(String cname);

}
