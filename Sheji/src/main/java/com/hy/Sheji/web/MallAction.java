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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
		public Model Mall(Model m ,HttpSession session,
				@RequestParam(value="pCid" ,defaultValue = "1") int pCid ,
                @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
		 
		  m.addAttribute("se",session.getAttribute("LoginUser"));
		  m.addAttribute("loginImg",session.getAttribute("loginImg")); 
		   List<Category> clist=cm.category();////展示商品类别
		   
		   m.addAttribute("clist", clist);
		   PageHelper.startPage(pageNum,10);
			List<Product> mclist=mb.Fenlei(pCid);
			PageInfo<Product> pageInfo = new PageInfo<>(mclist);
			m.addAttribute("pageInfo", pageInfo);  
		   return m;
		}	
	//mall界面中category方法
	  @GetMapping ("Fenlei")
		public PageInfo<Product> Fenlei(@RequestParam(value="pCid" ,defaultValue = "1") int pCid ,
				                    @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
	
		//商城mall商品界面分展示
		 
			PageHelper.startPage(pageNum,10);
			List<Product> mclist=mb.Fenlei(pCid);
			PageInfo<Product> pageInfo = new PageInfo<>(mclist);
			  
		   return pageInfo; 
		}
		
	 

}
