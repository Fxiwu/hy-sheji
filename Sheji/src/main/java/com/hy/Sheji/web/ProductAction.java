package com.hy.Sheji.web;

 

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hy.Sheji.bean.Area;
import com.hy.Sheji.bean.Cart;
import com.hy.Sheji.bean.Category;
import com.hy.Sheji.bean.Comment;
import com.hy.Sheji.bean.Product;
import com.hy.Sheji.bean.Result;
import com.hy.Sheji.biz.CartBiz;
import com.hy.Sheji.biz.MallBiz;
import com.hy.Sheji.dao.AreaMapper;
import com.hy.Sheji.dao.CartMapper;
import com.hy.Sheji.dao.ProductMapper;

@RestController
 
 public class ProductAction {
	
	 @Resource 
	 private ProductMapper pm;
	 
	 @Resource 
	 private AreaMapper am;
	
	 @Resource 
	 private CartBiz cb; 
	 
	 @Resource    
		private CartMapper cm;
	 
	 @GetMapping ("pro_detail") //产品详情界面
		public Model pro_detail(Model m,HttpSession session) {
		 List<Product> like=pm.selectLike();
	        m.addAttribute("se",session.getAttribute("LoginUser"));
	        m.addAttribute("loginImg",session.getAttribute("loginImg"));
		         m.addAttribute("like", like);  //产品详情界面喜欢模块数据渲染
				return m;
			}	
	 
	
	      //产品详情界面数据传递
	  @GetMapping ("pro_details") 
		public Product Product(@RequestParam(value="pId" ,defaultValue ="1") int pId ) {
			
		  Product p=pm.selectPro(pId);
			 
				return p;
			}	
	//产品详情界面数据传递
	  @GetMapping ("pro_detail_comment") 
		public List<Comment> SelectComm(@RequestParam(value="pId" ,defaultValue ="1") int pId ) {
			
		  List<Comment> clist=pm.SelectComm(pId);
			 
				return clist;
			}	
	  
		   
	//后台商品管理 product.html全部商品展示
	 @GetMapping("productquery")
	 public Map<String, Object> productquery(@RequestParam(value="pCid" ,defaultValue ="0") int pCid,
			       @RequestParam(value="aId" ,defaultValue ="0") int aId,
			       Integer page,Integer rows){
		 Product product=new Product();
		 if(pCid!=0) {
			 product.setpCid(pCid);
		 }
		 if(aId!=0) {
			 product.setpAid(aId);
		 }
 
		    //查询所有数据
		 List<Product> plist= pm.productquery(product);
		    //查询总记录数
		 int count=pm.countpro(product);
		 //展示分页集合
		    List<Product> p = new ArrayList<>();
		 
		    for (int i = 0; i < plist.size(); i++)
		    {
		        //当前页-1 * 记录数 并且 这条记录是第几页的数据 当前页乘以记录数
		        //第一次传过来的rows 是10 page是1
		        if (i >= (page - 1) * rows && i < page * rows)
		        {
		            //符合当前页的数据添加到展示分页集合中
		            p.add(plist.get(i));
		        }
		    }
		    Map<String,Object> result = new HashMap<String,Object>();
		    result.put("total", count);
		    result.put("rows", p);
		    return result;
		 
	 }
	//后台商品管理  product.html 商品删除
		 @GetMapping("delpro")
		 public Result delpro(@RequestParam(value="pId" ,defaultValue ="0") int pId){
			 int i= pm.delpro(pId);
			 System.out.println(pId);
			 if(i>0) {
				 return new Result(1,"删除成功");
			 }
			 return new Result(0,"删除失败");
			 
		 }
		 
		//后台商品管理  product.html 商品修改
		 @GetMapping("savepro")
		 public Result savepro(@RequestParam(value="pId" ,required = false,defaultValue="0")int pId,String pName, @RequestParam(value="cId" ,required = false)String cId,
									Double price ,
									String	pDesc , 
									@RequestParam(value="aId" ,required = false)String aId , 
									String	 pGuige,   
									int	 pKucun, 
									String	 pHot , 
									String	 pDanwei,
					           @RequestParam(value="pImg" ,required = false)String	pImg ){
		 
			 int phot=0;
			int c=0;
			int a=0;
			Product pro=new Product();
			 
			 if(pHot.equals("1") ) {
				 phot=1;
			 }
			 if(cId!=null) {
				  c=Integer.parseInt(cId);
				  pro.setpCid(c);
				
			 } 
			 if(aId!=null) {
				  a=Integer.parseInt(aId);
			     pro.setpAid(a);
			 } 
		   
			if(pId!=0) {
				pro.setpId(pId);
			}
			pro.setpName(pName);
			pro.setpDesc(pDesc);
			pro.setpGuige(pGuige);		
			pro.setpHot(phot);
			pro.setpImg(pImg);
			pro.setpKucun(pKucun);
			pro.setpImg(pImg);
			pro.setPrice(price);
			pro.setpDanwei(pDanwei);
			 System.out.println("pDanwei"+pro.getpDanwei());
			 if( pId == 0) {
		 			 pm.insertpro(pro);
		 			return new Result(1,"商品添加成功!");
		 		}else {
		 			int i= pm.updatepro(pro);
		 			 if(i>0) {
		 				 return	new Result(1,"商品修改成功!");
		 			 }else{
		 				 return	new Result(0,"商品修改失败!");
		 			 }
		 		   

		 		}
		 }
		 //后台商品管理  product.html 地区选项
		 @PostMapping("arequery")
		 public List<Area> arequery() {
			 
			return  am.selectArea();
		 }
		 
		 
		//后台商品管理  product.html  图片上传
		  @ResponseBody
		  @RequestMapping(value = "uploadImg", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
		  // @PostMapping("uploadImg")	 
		 public Result uploadImg(@RequestParam(value="file",required = false)MultipartFile file){
			       
			        //获取文件名
			 
			        String fileName = file.getOriginalFilename();
			       System.out.println("fileName"+fileName);
			        
			        String filePath = "E:/github/hy-sheji/hy-sheji/Sheji/src/main/resources/static/upload/";
			        try {
			            //将图片保存到static文件夹里
			        	file.transferTo(new File(filePath+fileName));
			        	System.out.println("file"+filePath+fileName);
			            return new Result(1,"上传成功","upload/"+fileName);
			        } catch (Exception e) {
			            e.printStackTrace();
			            return new Result(0,"上传失败");
			        } 
			    }

	 
		
}
