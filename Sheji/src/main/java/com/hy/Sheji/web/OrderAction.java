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

import com.hy.Sheji.bean.Address;
import com.hy.Sheji.bean.AdminOrder;
import com.hy.Sheji.bean.Cart;
import com.hy.Sheji.bean.Order;
import com.hy.Sheji.bean.Orderdetail;
import com.hy.Sheji.bean.Product;
import com.hy.Sheji.bean.Result;
import com.hy.Sheji.bean.User;
import com.hy.Sheji.biz.BizException;
import com.hy.Sheji.biz.CartBiz;
import com.hy.Sheji.biz.OrderBiz;
import com.hy.Sheji.biz.UserBiz;
import com.hy.Sheji.dao.CartMapper;
import com.hy.Sheji.dao.OrderMapper;
import com.hy.Sheji.dao.ProductMapper;
import com.hy.Sheji.dao.UserMapper;

@RestController
public class OrderAction {

	@Resource 
	private OrderBiz ob;
	
	@Resource 
	private OrderMapper om;
	
	@Resource 
	private CartBiz cb;
	@Resource 
	private CartMapper cm;
	
	@Resource 
	private UserBiz ub;
	
	@Resource 
	private ProductMapper pm;
	
	@Resource 
	private UserMapper um;
	
	//jiesuan页面
	@GetMapping("jiesuan")
	public Model jiesuan(@RequestParam(value="cId" ,defaultValue = "0") int cId, 
						//@RequestParam(value="addId" ,defaultValue = "0") int addId,
			             Model m,HttpSession session) {
		String uName=(String) session.getAttribute("LoginUser");
		m.addAttribute("se",uName);
		m.addAttribute("loginImg",session.getAttribute("loginImg"));
		if(uName!=null) {   //登录用户的地址信息
			User user=ob.selectAddress(uName);
			  if(um.seAddressdft(user.getuId())!=null) {//默认地址
					m.addAttribute("addrid",um.seAddressdft(user.getuId()).getAddId());	 
			  };
			
			m.addAttribute("user", user);	 
		     
			//判断是否有删除的，进行商品种类删除
			if(cId!=0) {
				 int i=cb.deleteByUid(cId);
				 System.out.println("cId===="+cId);
			  }
			int uId=cm.selectUer(uName).getuId();
		     //关联order orderdetail product三表展示jiesuan中的商品展示
 			   int oid=(int) session.getAttribute("oid");
 			  Order  order=ob.selectOrder(oid);
 			//System.out.println("order::::"+order.getoTotal());
 			  m.addAttribute("order", order);
// 			 cm.deleteBycUid(uId);
        			
		}
	return m;
	}
	
	//cart页面进行去结算时,向order表中新增一条
	@GetMapping("order")
	public Result addOrder(@RequestParam(value="sum" ,defaultValue = "0.0")double sum,
			@RequestParam(value="n" ,defaultValue = "0") int n,//区分直接购买，为购物车界面而来
			           Model m,HttpSession session) {
	    
			String uName=(String) session.getAttribute("LoginUser");
			int uId=cm.selectUer(uName).getuId();
			Order order=new Order();
			/*
			 * //判断该用户是否有地址 
			 * 
			 */
			 

			if(um.seAddressdft(uId)!=null) //不为空则设置默认地址
			   { 
				 order.setoAddid(um.seAddressdft(uId).getAddId()); 
			   }
		//	System.out.println("sum:::::"+sum);
			order.setoTotal(sum);
			order.setoUid(uId);
			System.out.println("order.setoTotal(sum):::::"+order.getoTotal()); 
		    List<Cart> clist;
			try {
				
				clist = cb.selectCart(uId); //购物车中信息
				if(clist.size()>0) {
					int i=ob.insertOrder(order);//order中进行订单添加
					int oid=order.getoId();
					if(i>0) { //order表添加成功后向adminorder表中添加相应的订单
						  
						Order od=ob.OrderByOid(oid);
						 
						ob.insertadminorder(od);
					}else{
						return new Result(0,"添加失败");
					}
					
					
				    session.setAttribute("oid", oid);
				List<Orderdetail> odlist =new ArrayList<Orderdetail>();
				 for (Cart str : clist) {
					 Orderdetail ordt=new Orderdetail();
					 ordt.setdCount(str.getcCount());
					 ordt.setdOid(oid);
					 ordt.setdPid(str.getProduct().getpId());
					 ordt.setdTotal(str.getProduct().getPrice()*str.getcCount());
					  odlist.add(ordt);
				//	System.out.println("rrrrrr"+ordt.getdPid()+" "+ordt.getdCount()+"  "+ordt.getdOid()+"  "+ordt.getdTotal());
				 }
 				 ob.insertOrderdetail(odlist);
				 
				//删除购物车中相应的订单
	 			  if(n!=0) {
	 				  cm.deleteBycUid(uId);
	 			  }
				return new Result(1,"order表成功添加订单一条订单");
               }
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 
			} 
 	
			 return new Result(0,"order表添加订单一条订单失败");
	}
	
	@GetMapping("liji")
	
	public Result liji(@RequestParam(value="pId" ,required=false ,defaultValue = "0")int pId,
			           @RequestParam(value="count" ,required=false ,defaultValue = "1")int count,Model m,HttpSession session) {
	    String uName=(String) session.getAttribute("LoginUser");
				if(uName==null||uName.isEmpty()) {
					return  new Result(0,"请先登录");
				}
			
			int uId=cm.selectUer(uName).getuId();
			Product p=pm.selectPro(pId);
			Order order=new Order();
			/*
			 * //判断该用户是否有地址 
			 * 
			 */
			 

			if(um.seAddressdft(uId)!=null) //不为空则设置默认地址
			   { 
				 order.setoAddid(um.seAddressdft(uId).getAddId()); 
			   }
			order.setoTotal(p.getPrice()*count);
			//System.out.println("p.getPrice()*count:::"+p.getPrice()*count);
			order.setoUid(uId);
			   
					int i=ob.insertOrder(order);//order中进行订单添加
					int oid=order.getoId();
					if(i>0) { //order表添加成功后向adminorder表中添加相应的订单
						 
						Order od=ob.OrderByOid(oid);
						 
						ob.insertadminorder(od);
						 
					}else{
						 return  new Result(0,"订单添加错误");
					}
			        session.setAttribute("oid", oid);
				List<Orderdetail> odlist =new ArrayList<Orderdetail>();
				 
					 Orderdetail ordt=new Orderdetail();
					 ordt.setdCount(count);
					 ordt.setdOid(oid);
					 ordt.setdPid(p.getpId());
					 ordt.setdTotal(p.getPrice());
					   
					 
					  odlist.add(ordt);
					  if(ob.insertOrderdetail(odlist)>0) {
						  return  new Result(1,"Orderdetail添加成功");
					  }
					  
					  
					  return new Result(0,"立即购买失败");
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
			//当total减为0的时候删除adminorder中对应的记录
			if(ob.selectOrder(oid).getoTotal()==0) {
				 
				ob.deladminorder(oid);
			} 
			return new Result(1,"商品删除成功");
		}else {
			//回滚
			 TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
		
		}
		return new Result(0,"商品删除失败");
		
	}
	
	//jiesuan.html中支付
	@GetMapping("zhifu")
	public Result zhifu(@RequestParam(value="addId" ,defaultValue = "0")int addId,
			            @RequestParam(value="oid" ,defaultValue = "0")int oid ,
			            HttpSession session) {
		Order or=ob.selectOrder(oid);  //相应的订单
		int uid=or.getoUid();
	 
		if(um.seAddressdft(uid)==null) {
			 session.setAttribute("flag","kong");
			// System.out.println("flag"+session.getAttribute("flag"));
			return new Result(0,"请添加收货地址");
		}
		System.out.println("flag"+session.getAttribute("flag"));
		
		if(session.getAttribute("flag")=="kong") {//当添加地址后重新设置order中的地址
			if(um.seAddressdft(uid)!=null) {
				
				session.removeAttribute("flag");
				int addid=um.seAddressdft(uid).getAddId();
				//修改空地址为默认地址
				ob.updateOrderAddr(addid,oid);
				//adminorder中相应改变
				 AdminOrder ao=new AdminOrder();
				 ao.setAddAddr(om.selectAddressById(addId).getAddAddr());
				 ao.setAddName(um.selectAddressByaid(addId).getAddName());
				 ao.setAddPhone(um.selectAddressByaid(addId).getAddPhone());
				  ob.updateadminordeAddr(ao,oid) ;
				 
			}
			 
		}
		int addddft=um.seAddressdft(uid).getAddId();//用户的默认地址id
		if(addId!=uid) {
			 ob.updateOrderAddr(addId,oid);//更改Order表中的收货地址
			     //更改adminOrder表中的收货地址
			 
			 AdminOrder ao=new AdminOrder();
			 ao.setAddAddr(om.selectAddressById(addId).getAddAddr());
			 ao.setAddName(um.selectAddressByaid(addId).getAddName());
			 ao.setAddPhone(um.selectAddressByaid(addId).getAddPhone());
			  ob.updateadminordeAddr(ao,oid) ;
		} 
		   //修改order和adminorder为已支付状态
	 if(om.updateoState(oid,1)>0&&om.updateaoOstate(oid,1)>0) {
		 return new Result(1,"支付成功");
	 }else {
		 return new Result(0,"支付失败");
	 }
		
	}
	
	//fukuan.html中付款成功
	@GetMapping("fukuan")
	public Model fukuan(Model m, HttpSession session) {
		String uName=(String) session.getAttribute("LoginUser");
		m.addAttribute("se",uName);
		m.addAttribute("loginImg",session.getAttribute("loginImg"));
		return m;
	}
	
	//fukuan.html中付款成功数据渲染
	@GetMapping("fukuan1")
	public Order fukuan1( HttpSession session) {
		
		int oid=(int) session.getAttribute("oid");
		Order or=ob.selectOrder(oid);  //查询相应的订单
		 
		return or;
	}
	
	//back adminorder中所有的订单
	@GetMapping("allOrder")
	public List<AdminOrder> allOrder(@RequestParam(value="uname",required=false) String uname,
			                    @RequestParam(value="state",required=false) String state) throws BizException {
	   
	    AdminOrder or=new AdminOrder(); 
	     
	    System.out.println("uname:"+uname);
	    //查询判断
	    if(uname!=null&&uname.isEmpty()==false) {
	    	or.setuName(uname);
	    }
	    
	    //查询
	    if(state!=null&&state.isEmpty()==false) {
	    	int s=Integer.parseInt(state);
	    	or.setoState(s);
	    }
	    
		return ob.adminOrderquery(or);	 
	}
	
	//back order.html中修改订单信息后保存
	@GetMapping("updateadminorder")
	public Result updateadminorder( int oId,String addName,int oState,String addAddr,String addPhone){
		ob.updateadminorder( oId, addName,oState,addAddr,addPhone);
	if(ob.updateadminorder( oId, addName,oState,addAddr,addPhone)>0) {
		 ob.updateorderState(oId,oState);
		return new Result(1,"修改成功");
	} 
	return new Result(0,"修改失败");
	}
	
	 
		   
}
