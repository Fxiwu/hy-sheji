package com.hy.Sheji.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.hy.Sheji.bean.Address;
import com.hy.Sheji.bean.Order;
import com.hy.Sheji.bean.Result;
import com.hy.Sheji.bean.User;
import com.hy.Sheji.biz.BizException;
import com.hy.Sheji.biz.UserBiz;
import com.hy.Sheji.dao.OrderMapper;
import com.hy.Sheji.dao.UserMapper;

@Controller
public class UserAction {

	@Resource
	private UserBiz ub;
	
	@Resource
	private UserMapper um;
	
	@Resource
	private OrderMapper om;

	
	@GetMapping("user")
	public Model user(Model m, HttpSession session)  {
		 String uName= (String) session.getAttribute("LoginUser");
		  m.addAttribute("se",uName);
		  User user=ub.selectByuName(uName); 
		  m.addAttribute("user",user);
		  return m;
	}
	
	
	//结算中添加地址
	@PostMapping("addaddress") 
	public String addaddress(Address address,
			               String shen,String shi,String xian,String xianxi,
			               HttpSession session) {
		String uName= (String) session.getAttribute("LoginUser");
		int uId=ub.selectByuName(uName).getuId();
		//判断是否有默认地址，没有该地址则为默认地址
		if(um.seAddressdft(uId)!=null&&address.getAddDft()==1) {
		   //修改原本的默认地址为不默认
			 ub.setAddressDft(uId);
			 
			}
		 
		address.setAddUid(uId);
		address.setAddAddr(shen+shi+xian+xianxi);
		
	    ub.addaddress(address);
	    return "redirect:/jiesuan";
	}
	
	//用户信息订单模块
	@GetMapping("user_order")
	public Model userorder(Model m, HttpSession session)  {
		 String uName= (String) session.getAttribute("LoginUser");
		  m.addAttribute("se",uName);
		 int ouid= ub.selectByuName(uName).getuId();
		  List<Order> olist=ub.userorder(ouid);
		   m.addAttribute("olist", olist);
		  
		  return m;
	} 
	
	
	//用户确认收货
	@GetMapping("updateoState")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Result updateoState(int oId,int oState)  {
		Object savePoint = null;
		if(oState==0) {
			 return new Result(0,"该商品还未付款！！");
		}else{
			//设置回滚点
        savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
		   if(om.updateoState(oId)>0&&om.updateadminorderOstate(oId)>0) {
			    return new Result(1,"确认收货");
		   }else {
			 //回滚
				 TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
			
			   return new Result(0,"确认收货失败");
		   }
		   
		}
		
		 
	}
	
	@GetMapping("user_address")
	public Model useraddress(Model m, HttpSession session)  {
		
		String uName= (String) session.getAttribute("LoginUser");
		  m.addAttribute("se",uName);
 
		  //通过user的uname查出对应的uId的所有Address
		User user= ub.selectAddress(uName);
		m.addAttribute("user", user);
		return m;
	}
	
	//user_address中del modfydft
	@GetMapping("delAddr")
	@ResponseBody
	public Result delAddr(int addId) {
		Address add= um.selectAddressByaid(addId);
		if(add.getAddDft()==1) {
			return new Result(0,"请设置其他地址为默认地址！");
		}else {
			if(um.delAddr(addId)>0) {
				return new Result(1,"删除成功！");
			}
		}
		return new Result(0,"删除失败！");

	}
	//user_address中modfydft
		@GetMapping("modfydft")
		@ResponseBody
		public Result modfydft(int addId,int addUid) {
			if(um.seAddressdft(addUid)!=null) {
			 
				int i=ub.setAddressDft(addUid);//修改原本默认地址为不默认
			 
			   if(i>0) {
				   um.modfydft(addId);
				   return new Result(1,"修改成功！");
			   }else {
				   return new Result(0,"修改失败！");
			   }
			}else {
				um.modfydft(addId);
			   return new Result(1,"修改成功！");
			}
			 
			 
		}
	 
}
