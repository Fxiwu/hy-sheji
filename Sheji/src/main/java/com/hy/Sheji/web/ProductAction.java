package com.hy.Sheji.web;

 

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.Sheji.bean.Product;
import com.hy.Sheji.biz.MallBiz;
import com.hy.Sheji.dao.ProductMapper;

@Controller
 public class ProductAction {
	
	 @Resource 
	 private ProductMapper pm;
	 
	 
	 @GetMapping ("pro_detail") //产品详情界面
		public String pro_detail() {
			
		  
				return "pro_detail";
			}	
	 
	
	 @ResponseBody      //产品详情界面数据传递
	  @GetMapping ("pro_details") 
		public Product Product(@RequestParam(value="pId" ,defaultValue ="1") int pId ) {
			
		  Product p=pm.selectPro(pId);
			 
				return p;
			}	
	 

}
