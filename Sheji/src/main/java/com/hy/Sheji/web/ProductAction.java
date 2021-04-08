package com.hy.Sheji.web;

 

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hy.Sheji.bean.Cart;
import com.hy.Sheji.bean.Product;
import com.hy.Sheji.bean.Result;
import com.hy.Sheji.biz.CartBiz;
import com.hy.Sheji.biz.MallBiz;
import com.hy.Sheji.dao.CartMapper;
import com.hy.Sheji.dao.ProductMapper;

@RestController
 public class ProductAction {
	
	 @Resource 
	 private ProductMapper pm;
	
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
		   
				
	 
}
