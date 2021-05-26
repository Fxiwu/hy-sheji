package com.hy.Sheji.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hy.Sheji.bean.Area;
import com.hy.Sheji.bean.Product;
import com.hy.Sheji.biz.SpecialBiz;
import com.hy.Sheji.dao.SpecialMapper;
 

@Controller
public class specialAction {
	   @Resource
      SpecialBiz sb;
       
       @GetMapping("special")
       public  PageInfo<Area> special(Model m,HttpSession session,
    		   @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum) {
			//special页面展示
        
        m.addAttribute("se",session.getAttribute("LoginUser"));
        m.addAttribute("loginImg",session.getAttribute("loginImg"));
        PageHelper.startPage(pageNum,5);
    	List<Area> alist=sb.selspecial();
		PageInfo<Area> pageInfo =new PageInfo<>(alist);
		m.addAttribute("pageInfo", pageInfo); 
       	return pageInfo;
       	
       }
       
       
       @GetMapping("special_detail")
       public String sdetail(Model m,HttpSession session) {
           m.addAttribute("se",session.getAttribute("LoginUser"));
           m.addAttribute("loginImg",session.getAttribute("loginImg"));
       	return "special_detail";
       	
       }
       
       @ResponseBody
       @GetMapping("special_details")
       public PageInfo<Product>  sdetail1(@RequestParam(value="aId" ,defaultValue ="1") int aId,
    		                         @RequestParam(value="pageNum", defaultValue="1")int pageNum) {
		    PageHelper.startPage(pageNum,8);
    	   //special_detail页面展示
             List<Product> sdlist=sb.sdetail(aId);
             PageInfo<Product> pageInfo=new PageInfo<>(sdlist);
             
       	return pageInfo;
       	
       }
       
       //地区搜索
       @ResponseBody
       @GetMapping("souaddr")
       public List<Area> souaddr(@RequestParam(value="txt" ,defaultValue ="null") String txt) {
			//special_detail页面展示
    	   List<Area> salist=sb.souaddr(txt);
             
       	return salist;
       	
       }
       

}
