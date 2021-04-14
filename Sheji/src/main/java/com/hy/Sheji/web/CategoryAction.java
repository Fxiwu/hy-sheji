package com.hy.Sheji.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hy.Sheji.bean.Category;
import com.hy.Sheji.bean.Result;
import com.hy.Sheji.dao.CategoryMapper;

@RestController
public class CategoryAction {
	
	@Resource 
	private CategoryMapper cm;
	 //后台商品管理  product.html category.html类别选项
	 @PostMapping("category")
	 public List<Category> category() {
		return  cm.category();
	 }
	 
	 
	 //后台类别管理  category.html 删除类别 
	 @GetMapping("delcategory")
	 public Result delcategory(@RequestParam(value="cId")int cId) {
		  if( cm.delcategory(cId)>0) {
			  return new Result(1,"删除成功");
		  
		  }else {
			  return new Result(0,"删除失败");
		  }
	 }
	 //后台类别管理  category.html 添加类别 
	 @GetMapping("addcategory")
	 public Result addcategory( @RequestParam(value="cname")String cname) {
		 if(cname!= null&&cname.isEmpty()==false) {
			 if( cm.addcategory(cname)>0) {
		 
			  return new Result(1,"添加成功");
		  
		  }
			 }else {
			  return new Result(0,"添加失败");
		  }
		 return new Result(0,"添加失败");
	 }
}
