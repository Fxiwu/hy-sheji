package com.hy.Sheji.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.Sheji.bean.Area;
import com.hy.Sheji.bean.Result;
import com.hy.Sheji.biz.AreaBiz;

 
 

@Controller
public class AreaAction {
	   @Resource
	   AreaBiz ab;
       
       @GetMapping("area")
       public Model area(Model m,HttpSession session) {
			//area页面展示
         List<Area> alist= ab.area();
         m.addAttribute("se",session.getAttribute("LoginUser"));
         m.addAttribute("alist", alist);
       	return m;
       	
       }
       
       
       @GetMapping("area_detail")
       public String areadet(HttpSession session,Model m) {
			//area_detail页面展示
           m.addAttribute("se",session.getAttribute("LoginUser"));

       	return "area_detail";
       	
       }
       
       @ResponseBody
       @GetMapping("adetail")
       public Area  adetail(int aId) {
			//area页面展示
         Area ade=ab.areade(aId) ;
        
       	return ade;
       	
       }
        
       //back  area.html
       @ResponseBody
       @GetMapping("areaquery")
       public List<Area>  areaquery( ) {
			//area页面展示
         List<Area> ade=ab.area() ;
        
       	return ade;
       	
       }
       @ResponseBody
       @GetMapping("savearea")
        
       public Result  savearea(@RequestParam(value="aId" ,required = false,defaultValue="0") int aId,
    		                   String aShi,
    		                    String aXian,
    		                     String aWeizhi,
    		                     String aImg,
    		                  String aJieshao,
    		                    String aTese,
    		                    String aLvyou,
    		                    String aPhoto
    		                    
     		                   ) {
		 Area are=new Area();
		 if(aId!=0) {
			  are.setaId(aId);
		 }
		
		 are.setaImg(aImg);
		 are.setaJieshao(aJieshao);
		 are.setaLvyou(aLvyou);
		 are.setaPhoto(aPhoto);
		 are.setaShi(aShi);
		 are.setaTese(aTese);
		 are.setaWeizhi(aWeizhi);
		 are.setaXian(aXian);
    	   System.out.println("area"+are .getaJieshao());
         if(aId== 0) {
 			if( ab.insertarea(are )>0) {
 				return new Result(1,"地区添加成功!");
 			}else {
 				return new Result(0,"地区添加失败!");
 			}
 			
 		}else {
 			int i= ab.updatearea(are);
 			 if(i>0) {
 				 return	new Result(1,"地区修改成功!");
 			 }else{
 				 return	new Result(0,"地区修改失败!");
 			 }
 		   

 		}
        
       }

}
