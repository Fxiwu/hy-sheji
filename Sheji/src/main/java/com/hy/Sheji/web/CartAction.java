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
		try {
			if(uName!=null) {
				int uId=cm.selectUer(uName).getuId();
				List<Cart> clist=cb.selectCart(uId);
				//System.out.println("======"+clist.toString());
				m.addAttribute("clist", clist);
				double sum=0.0;
				 for (Cart str : clist) {            
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
}
