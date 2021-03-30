package com.hy.Sheji.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.Sheji.bean.Area;
import com.hy.Sheji.biz.AreaBiz;

 
 

@Controller
public class AreaAction {
	   @Resource
	   AreaBiz ab;
       
       @GetMapping("area")
       public Model area(Model m) {
			//area页面展示
         List<Area> alist= ab.area();
         m.addAttribute("alist", alist);
       	return m;
       	
       }
       
       
       @GetMapping("area_detail")
       public String areadet() {
			//area_detail页面展示
        
       	return "area_detail";
       	
       }
       
       @ResponseBody
       @GetMapping("adetail")
       public Area  adetail(int aId) {
			//area页面展示
         Area ade=ab.areade(aId) ;
        
       	return ade;
       	
       }
        

}
