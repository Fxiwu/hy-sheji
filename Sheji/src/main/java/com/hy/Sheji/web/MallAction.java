package com.hy.Sheji.web;

 
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hy.Sheji.bean.Category;
import com.hy.Sheji.bean.Product;
import com.hy.Sheji.biz.MallBiz;
import com.hy.Sheji.dao.CategoryMapper;

@RestController
 public class MallAction {
	
	  @Resource
	  private MallBiz mb;
	  
	  @Resource
	  private CategoryMapper cm;
	 
	  //mall 
	  @GetMapping ("mall") 
		public ModelAndView Mall(ModelAndView mav ,HttpSession session) {
		 
		  mav.addObject("se",session.getAttribute("LoginUser"));
		  
		   List<Product> mlist=mb.Mall(); //展示cid为1的商品
		   List<Category> clist=cm.category();
			mav.addObject("mall", mlist);
			mav.addObject("clist", clist);
			mav.setViewName("mall");
			return mav;
		}	
	//mall界面中category方法
	  @GetMapping ("category")
		public List<Product> CategoryProduct(@RequestParam(value="pCid" ,defaultValue = "1") int pCid) {
	
		//商城mall商品界面分展示
		 

			List<Product> mclist=mb.Category(pCid);
			/*
			 * for ( int i = 0 ;i < mclist.size();i++){
			 * System.out.println(mclist.get(i).getpCid()); }
			 */
		   return mclist; 
		}
		
	 

}
