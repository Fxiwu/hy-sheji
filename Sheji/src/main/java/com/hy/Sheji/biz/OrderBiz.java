package com.hy.Sheji.biz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.Sheji.bean.Address;
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


	//jiesuan界面中确定修改收货地址
	public int updateOrderAddr(int addId, int oid) {
		 return om.updateOrderAddr(addId,  oid);
	}
	

}
