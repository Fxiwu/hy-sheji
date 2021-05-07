package com.hy.Sheji.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hy.Sheji.bean.Product;
import com.hy.Sheji.dao.ProductMapper;

@Controller
public class IndexAction{
       
	    @Resource
        ProductMapper pm;
        
        @GetMapping("index" )
        public Model index(Model m,HttpSession session) {
			//首页商城热销
        	List<Product> list=pm.selectIndexhot();
        	m.addAttribute("list",list);
        	m.addAttribute("se",session.getAttribute("LoginUser"));
        	m.addAttribute("loginImg",session.getAttribute("loginImg"));
             
        	return m;
        	
        }
        
		  
		 
		 @GetMapping("sou")
		 @ResponseBody 
		 public List<Product> sou(@RequestParam(value="txt" ,defaultValue="null") String txt) {
		 System.out.println("txt"+txt); 
		 
		 if(txt!=null) {
			  List<Product> plist=pm.SuoProduct(txt);
			  return plist;
		 }
		 
		     return null;
		 }
		  
 }
         
        
 