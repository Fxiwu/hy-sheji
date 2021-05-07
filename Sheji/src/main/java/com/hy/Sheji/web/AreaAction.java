package com.hy.Sheji.web;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
         m.addAttribute("loginImg",session.getAttribute("loginImg"));
         m.addAttribute("alist", alist);
       	return m;
       	
       }
       
       
       @GetMapping("area_detail")
       public String areadet(HttpSession session,Model m) {
			//area_detail页面展示
           m.addAttribute("se",session.getAttribute("LoginUser"));
           m.addAttribute("loginImg",session.getAttribute("loginImg"));
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
       @PostMapping("upload")	 
		 public Result uploadImg(@RequestParam(value="file1",required = false)MultipartFile file1){
			       
			        //获取文件名
			 
			        String fileName = file1.getOriginalFilename();
			       System.out.println("fileName"+fileName);
			        //获取文件后缀名
			        //String suffixName = fileName.substring(fileName.lastIndexOf("."));
			        //重新生成文件名
			       // fileName =fileName+suffixName;
			        System.out.println("fileName:"+fileName);
			        //指定本地文件夹存储图片
			        String filePath = "E:/github/hy-sheji/hy-sheji/Sheji/src/main/resources/static/areaimg/";
			        try {
			            //将图片保存到static文件夹里
			        	file1.transferTo(new File(filePath+fileName));
			        	System.out.println("file"+filePath+fileName);
			        	//JSON.toJSONString(fileName);
			            return new Result(1,"上传成功","areaimg/"+fileName);
			        } catch (Exception e) {
			            e.printStackTrace();
			            return new Result(0,"上传失败");
			        } 
			    }

       
       
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
       
       @ResponseBody
       @GetMapping("delarea")
        
       public Result  savearea(@RequestParam(value="aId" ,required = false,defaultValue="0") int aId) {
		
    	   if(aId!=0) {
    		  if( ab.delarea(aId)>0) { 
    			  return new Result(1,"删除成功");
    		  } 
    	   }
    	   return new Result(0,"删除失败");

         }

}
