package com.hy.Sheji.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hy.Sheji.bean.Cart;
import com.hy.Sheji.bean.Order;
import com.hy.Sheji.bean.Orderdetail;
import com.hy.Sheji.bean.Result;
import com.hy.Sheji.bean.User;
import com.hy.Sheji.biz.BizException;
import com.hy.Sheji.biz.CartBiz;
import com.hy.Sheji.biz.OrderBiz;
import com.hy.Sheji.dao.CartMapper;

@RestController
public class OrderAction {

	@Resource 
	private OrderBiz ob;
	
	@Resource 
	private CartBiz cb;
	@Resource 
	private CartMapper cm;
	
	//jiesuan页面
	@GetMapping("jiesuan")
	public Model jiesuan(@RequestParam(value="cId" ,defaultValue = "0") int cId,@RequestParam(value="addId" ,defaultValue = "0") int addId,Model m,HttpSession session) {
		String uName=(String) session.getAttribute("LoginUser");
		m.addAttribute("se",uName);
		if(uName!=null) {   //登录用户的地址信息
			User user=ob.selectAddress(uName);
			m.addAttribute("user", user);	 
		 
			//判断是否有删除的 ，进行商品种类删除
			if(cId!=0) {
				 int i=cb.deleteByUid(cId);
				 System.out.println("cId===="+cId);
			  }
			int uId=cm.selectUer(uName).getuId();
		      //关联order orderdetail product三表展示jiesuan中的商品展示
 			   int oid=(int) session.getAttribute("oid");
 			  Order  order=ob.selectOrder(oid);
 			  m.addAttribute("order", order);
 			//删除购物车中相应的订单
        			cm.deleteBycUid(uId);
		}
	return m;
	}
	
	//cart页面进行去结算时,向order表中新增一条
	@GetMapping("order")
	public Result addOrder(@RequestParam(value="sum" ,defaultValue = "0.0")double sum,Model m,HttpSession session) {
	    
			String uName=(String) session.getAttribute("LoginUser");
			int uId=cm.selectUer(uName).getuId();
			Order order=new Order();
			order.setoTotal(sum);
			order.setoUid(uId);
			 
		    List<Cart> clist;
			try {
				
				clist = cb.selectCart(uId); //购物车中信息
				if(clist.size()>0) {
					ob.insertOrder(order);//order中进行订单添加
					int oid=order.getoId();
				    session.setAttribute("oid", oid);
				List<Orderdetail> odlist =new ArrayList<Orderdetail>();
				 for (Cart str : clist) {
					 Orderdetail ordt=new Orderdetail();
					 ordt.setdCount(str.getcCount());
					 ordt.setdOid(oid);
					 ordt.setdPid(str.getProduct().getpId());
					 ordt.setdTotal(str.getProduct().getPrice()*str.getcCount());
					  odlist.add(ordt);
					System.out.println("rrrrrr"+ordt.getdPid()+" "+ordt.getdCount()+"  "+ordt.getdOid()+"  "+ordt.getdTotal());
				 }
				 System.out.println("+++++"+odlist.getClass());
				 ob.insertOrderdetail(odlist);
				return new Result(1,"order表成功添加订单一条订单");
               }
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 
			} 
			
			
			
		
			 return new Result(0,"order表添加订单一条订单失败");
	}
	
	//删除订单中的商品
	@GetMapping("delorder")
	@Transactional(rollbackFor = Exception.class)
	public Result updae(@RequestParam(value="pId" ,defaultValue = "0")int pId,
			@RequestParam(value="price" ,defaultValue = "0.0")double price,
			HttpSession session) {
		Object savePoint = null;
		int oid=(int) session.getAttribute("oid");
		 //设置回滚点
        savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
		int i=ob.updateorder( oid,price);
		int j=ob.delod(pId,oid);
		if(i>0&&j>0) {
			return new Result(1,"商品删除成功");
		}else {
			//回滚
			 TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
		
		}
		return new Result(0,"商品删除失败");
		
	}
	
	@GetMapping("zhifu")
	public Result zhifu(@RequestParam(value="addId" ,defaultValue = "0")int addId,@RequestParam(value="oid" ,defaultValue = "0")int oid ) {
		 
		if(addId>0) {
			 ob.updateOrderAddr(addId,oid);
			return new Result(1,"成功");
		}
		
		return new Result(0,"失败");
	}
}
