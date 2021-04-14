package com.hy.Sheji.web;

 

import java.io.File;
import java.util.List;
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
	 
	 @Resource   //来获取登录的用户
		private CartMapper cm;
	 
	 @GetMapping ("pro_detail") //产品详情界面
		public Model pro_detail(Model m,HttpSession session) {
		 List<Product> like=pm.selectLike();
	        m.addAttribute("se",session.getAttribute("LoginUser"));
		         m.addAttribute("like", like);  //产品详情界面喜欢模块数据渲染
				return m;
			}	
	 
	
	      //产品详情界面数据传递
	  @GetMapping ("pro_details") 
		public Product Product(@RequestParam(value="pId" ,defaultValue ="1") int pId ) {
			
		  Product p=pm.selectPro(pId);
			 
				return p;
			}	
	 
	  //向购物车中添加商品
	  @GetMapping ("addcart") 
		public Result addcart(@RequestParam(value="pId" ,defaultValue ="0") int pId,HttpSession session ) {
		 String uName=(String) session.getAttribute("LoginUser");
		 int uId = 0;
		 if(uName!=null) {
			    uId=cm.selectUer(uName).getuId();
		   }else {
			   return new Result(0,"请先登录");
		   }
		  if(pId>0) {
			  if(cb.selectupId(uId, pId)>0) {  //判断购物车中产品是否已经添加
				 int i= cb.addupdate(uId, pId);
				  if(i>0) {
						return new Result(1,"添加购物车成功");
					}
			  }else {
				  Cart cart=new Cart();
				cart.setcCount(1);
				cart.setcPid(pId);
				cart.setcUid(uId);
				int i=cb.addCart(cart);
				if(i>0) {
					return new Result(1,"添加购物车成功");
				}
				return new Result(0,"添加失败");
			  }
				
			}
		return new Result(0,"失败");
				
			}
		   
	//后台商品管理 product.html全部商品展示
	 @GetMapping("productquery")
	 public List<Product> productquery(@RequestParam(value="pCid" ,defaultValue ="0") int pCid,
			       @RequestParam(value="aId" ,defaultValue ="0") int aId){
		 Product product=new Product();
		 if(pCid!=0) {
			 product.setpCid(pCid);
		 }
		 if(aId!=0) {
			 product.setpAid(aId);
		 }
		 return pm.productquery(product);
		 
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
									String	 pHot , 
					           @RequestParam(value="pImg" ,required = false)String	pImg ){
			int phot=0;
			int c=0;
			int a=0;
			Product pro=new Product();
			System.out.println("=======");
			 if(pHot.equals("是") ) {
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
			System.out.println(pro.getpCid());
			System.out.println(pro.getpAid());
			System.out.println(pro.getpHot().getClass());
			pro.setpImg(pImg);
			pro.setPrice(price);
			
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
		 //@ResponseBody
		// @RequestMapping(value = "uploadImg", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
		   @PostMapping("uploadImg")	 
		 public Result uploadImg(@RequestParam(value="file",required = false)MultipartFile file){
			       
			        //获取文件名
			 
			        String fileName = file.getOriginalFilename();
			       System.out.println("fileName"+fileName);
			        //获取文件后缀名
			        String suffixName = fileName.substring(fileName.lastIndexOf("."));
			        //重新生成文件名
			        fileName =fileName+suffixName;
			        System.out.println("fileName:"+fileName);
			        //指定本地文件夹存储图片
			        String filePath = "E:/github/hy-sheji/hy-sheji/Sheji/src/main/resources/static/upload/";
			        try {
			            //将图片保存到static文件夹里
			        	file.transferTo(new File(filePath+fileName));
			        	System.out.println("file"+filePath+fileName);
			        	//JSON.toJSONString(fileName);
			            return new Result(1,"上传成功","upload/"+fileName);
			        } catch (Exception e) {
			            e.printStackTrace();
			            return new Result(0,"上传失败");
			        } 
			    }

	 
		
}
