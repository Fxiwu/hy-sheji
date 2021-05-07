package com.hy.Sheji.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.hy.Sheji.bean.Cart;
import com.hy.Sheji.bean.Result;
import com.hy.Sheji.bean.User;
import com.hy.Sheji.biz.BizException;
import com.hy.Sheji.biz.CartBiz;
import com.hy.Sheji.dao.CartMapper;
 

@RestController
public class CartAction {

	@Resource 
	private CartBiz cb;
	@Resource 
	private CartMapper cm;
	
	@GetMapping("cart")
	public Model cart(@RequestParam(value="cId" ,defaultValue = "0") int cId,Model m, String uName,HttpSession session) {
		//判断是否有删除的 
		if(cId!=0) {
			 int i=cb.deleteByUid(cId);
			 System.out.println("cId===="+cId);
		  }
		uName=(String) session.getAttribute("LoginUser");
		m.addAttribute("se",uName);
		m.addAttribute("loginImg",session.getAttribute("loginImg"));
		try {  //若用户已登陆，则通过uName来获取uId
			if(uName!=null) {
				int uId=cm.selectUer(uName).getuId();
				List<Cart> clist=cb.selectCart(uId);
				//System.out.println("======"+clist.toString());
				m.addAttribute("clist", clist);
				double sum=0.0;
				 for (Cart str : clist) {   //算出购物车总金额         
 			        System.out.println(str.getcCount()*str.getProduct().getPrice());
 			        sum+=str.getcCount()*str.getProduct().getPrice();
 			    }
				 m.addAttribute("sum", sum);
		     	
			}else {
				Result res=new Result(0,"请先用户登录");
				System.out.println("konhkojoio");
				m.addAttribute("res", res);
			}
			
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}
	
	
	 //向购物车中添加商品
	  @GetMapping ("addcart") 
		public Result addcart(@RequestParam(value="pId" ,defaultValue ="0") int pId,
				              @RequestParam(value="cCount" ,defaultValue ="0") int cCount,HttpSession session ) {
		 String uName=(String) session.getAttribute("LoginUser");
		 int uId = 0;
		 if(uName!=null) {
			    uId=cm.selectUer(uName).getuId();
		   }else {
			   return new Result(0,"请先登录");
		   }
		  if(pId>0) {
			  if(cb.selectupId(uId, pId)>0) {  //判断购物车中产品是否已经添加
				 int i= cb.addupdate(uId, pId,cCount);
				  if(i>0) {
						return new Result(1,"添加购物车成功");
					}
			  }else {
				  Cart cart=new Cart();
				cart.setcCount(cCount);
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
	
//	@GetMapping("carts")
//	public Result carts(String uName) {
//		int uId=cm.selectUer(uName).getuId();
//		if(cb.deleteBycUid(uId)>0) {
//			Result res=new Result(1,"购物车清除成功，前往结算页面");
//			return res;
//		};
//		return new Result(0,"购物车清除失败");
//		
//	}
	
	
}
