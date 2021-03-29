package com.hy.Sheji.web;

 
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hy.Sheji.bean.Product;
import com.hy.Sheji.biz.MallBiz;

@RestController
 public class MallAction {
	
	  @Resource private MallBiz mb;
	 
	  @GetMapping ("mall") 
		public ModelAndView Mall(ModelAndView mav) {
		  List<Product> mlist=mb.Mall();
		  
			mav.addObject("mall", mlist);
			mav.setViewName("mall");
			return mav;
		}	
		
	  @GetMapping ("category")
		public List<Product> Category(@RequestParam(value="pCid" ,defaultValue = "4") int pCid) {
	
		//商城mall商品界面分展示
		  System.out.println("+=============");
     	  System.out.println(pCid);
     	 System.out.println("=============");

			List<Product> mclist=mb.Category(pCid);
			for ( int  i =  0 ;i < mclist.size();i++){
			     System.out.println(mclist.get(i).getpCid());
			}
		   return mclist; 
		}
		
	 

}
