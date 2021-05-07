package com.hy.Sheji.biz;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import com.hy.Sheji.bean.Address;
import com.hy.Sheji.bean.AdminOrder;
import com.hy.Sheji.bean.Order;
import com.hy.Sheji.bean.Orderdetail;
import com.hy.Sheji.bean.User;
import com.hy.Sheji.dao.OrderMapper;
import com.hy.Sheji.dao.UserMapper;

@Service
public class OrderBiz {
	
	//UserMapper 的用户地址查询
	@Resource 
	private UserMapper um;
	
	@Resource
	private OrderMapper om;
	
	//查询当前登录用户的地址
	public User selectAddress(String uName){
		User  list=um.selectAddress(uName);
		return list;
		
	}
	//查询当前登录用户的默认地址
		public User selectAddressDef(String uName){
			User  list=um.selectAddressDef(uName);
			return list;
			
		}
	
	
	//新增Order订单
		public int insertOrder(Order order){			 
			return om.insertOrder(order);			
		}
		
	//新增Orderdetail订单
	  public int insertOrderdetail(List<Orderdetail>  odlist){			 
		   return om.insertOrderdetail(odlist);			
		}	
	  
 	//查询Order
 	  public  Order  selectOrder(int oid) {
 		
 
 		  return  om.selectOrder(oid);
 		  
 	  }


 	  //jiesuan中删除某商品更改order表total
	public int updateorder(  int oid,double price) {
		return om.updateorder( oid,price);
		 
	} 

       //删除orderdetail中对应的orderdetail
	public int delod(int pId, int oid) {
		 
		return om.delod( pId,oid);
	}
	 
		 //当total减为0的时候删除adminorder中对应的记录
		public int deladminorder( int oid) {
			 
			return om.deladminorder(oid);
		}
	//jiesuan界面中确定地址后修改收货地址
	public int updateOrderAddr(int addId, int oid) {
		 return om.updateOrderAddr(addId,  oid);
	}
	//jiesuan界面中确定地址后修改adminordeAddr中收货地址
		public int updateadminordeAddr(AdminOrder adminOrder,int oId) {
			 return om.updateadminordeAddr(adminOrder,oId);
		}
	
	 

     //向adminorder表中添加order中新添加的
	public int insertadminorder(Order od) {
		return om.insertadminorder(od);
		 
	}

 //back中 orders.html 中展示订单 
	 //back中 orders.html 中	 
	public Order OrderByOid(int oid){
	        return om.OrderByOid(oid);	     	  
	}
	 //back中 orders.html 中	展现所有的订单 
	public List<AdminOrder> adminOrderquery(AdminOrder or) {
		// TODO Auto-generated method stub
		  return om.adminOrderquery( or);
	}

	//back order.html中修改订单信息后保存
	public int updateadminorder(int oId, String addName, int oState, String addAddr, String addPhone) {
		// TODO Auto-generated method stub
		return om.updateadminorder(oId, addName,oState,addAddr,addPhone);
	}
	//back order.html中修改订单信息后 相应改变order表的ostate
	public int updateorderState(int oId, int oState) {
		// TODO Auto-generated method stub
		return om.updateoState(oId,oState);
		
	}
 
    
	 
	  

}
