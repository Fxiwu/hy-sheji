package com.hy.Sheji.biz;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import com.hy.Sheji.bean.Cart;
import com.hy.Sheji.bean.User;
import com.hy.Sheji.dao.CartMapper;

@Service
public class CartBiz {
 
	@Resource
	private CartMapper cm;
   
		//展示购物车
		public List<Cart> selectCart(int cUid) throws BizException{
			System.out.println("cUid"+cUid);
			List<Cart> clist=cm.selectCart(cUid);
//			 for (Cart str : clist) {            
//			        System.out.println(str);
//			     }

			return clist;
			
		  }
		//商品到添加购物车
		public int  addCart(Cart cart){
	         
			return cm.addCart(cart);
		}
		//添加购物车中商品的数量
		public  int addupdate(int cUid,int cPid,int cCount){
			 
			return cm.addupdate(cUid,cPid,cCount);
		}
 	    
		//减少购物车中商品的数量
		public  int deupdate(int cUid,int cPid){
	         
			return cm.deupdate(cUid,cPid);
		}		     
		
		//删除购物车中的商品类商品
		public  int deleteByUid(int cId){
	         
			return cm.deleteByCid(cId);
		}
		//在结算时通过用户ID实现购物车清空
		public int deleteBycUid(int cUid) {
			 return cm.deleteBycUid(cUid);
		}
		
		//查询登录用户是否有某件商品 
		@Select("select count(*) from hy_cart where c_uId=#{cUid} and c_pid=#{cPid}")
		  
		public int  selectupId(int cUid,int cPid) {
			  return cm.selectupId(cUid,cPid);
	     } 
}
