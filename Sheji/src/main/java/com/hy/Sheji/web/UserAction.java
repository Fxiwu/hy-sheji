package com.hy.Sheji.web;

import java.io.File;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hy.Sheji.bean.Address;
import com.hy.Sheji.bean.Comment;
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
		  m.addAttribute("loginImg",session.getAttribute("loginImg"));
		  User user=ub.selectByuName(uName); 
		  m.addAttribute("user",user);
		  return m;
	}
	
	
	//结算中添加地址
	@PostMapping("addaddress") 
	@ResponseBody
	public Result addaddress(Address address,
			               String shen,String shi,String xian,String xianxi,
			               HttpSession session) {
		String uName= (String) session.getAttribute("LoginUser");
		
		if(address.getAddName().isEmpty()||address.getAddName().trim()==null) {
			return new Result(0,"姓名不能为空");	
		} 
		if(address.getAddPhone().isEmpty()||address.getAddPhone().trim()==null) {
			return new Result(0,"电话不能为空");	
		}
		if(shi.isEmpty()||shi.trim()==null) {
			return new Result(0,"地址不完整");	
		}
		if(shen.isEmpty()||shen.trim()==null) {
			return new Result(0,"地址不完整");		
		}
		if(xian.isEmpty()||xian.trim()==null) {
			return new Result(0,"地址不完整");	
		}
//		System.out.println("shen:::"+shen);
		int uId=ub.selectByuName(uName).getuId();
		//判断是否有默认地址，没有该地址则为默认地址
		if(um.seAddressdft(uId)!=null&&address.getAddDft()==1) {
		   //修改原本的默认地址为不默认
			 ub.setAddressDft(uId);
			 
			}
		 
		address.setAddUid(uId);
		address.setAddAddr(shen+shi+xian+xianxi);
		
	    ub.addaddress(address);
	    return new Result(1,"添加地址成功！");
	}
	
	//用户信息订单模块
	@GetMapping("user_order")
	public Model userorder(Model m, 
			               @RequestParam(value="pageNum",defaultValue="1") int pageNum,HttpSession session)  {
		 String uName= (String) session.getAttribute("LoginUser");
		  m.addAttribute("se",uName);
		  m.addAttribute("loginImg",session.getAttribute("loginImg"));
		 int ouid= ub.selectByuName(uName).getuId();
		 PageHelper.startPage(pageNum, 5);
		  List<Order> olist=ub.userorder(ouid);
		 PageInfo<Order> pageInfo=new PageInfo<>(olist);
		   m.addAttribute("pageInfo", pageInfo);
		  
		  return m;
	} 
	
	
	//用户确认收货
	@GetMapping("updateoState")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Result updateoState(int oId,int oState)  {
		Object savePoint = null;
		 
			//设置回滚点
        savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
		   if(om.updateoState(oId,4)>0&&om.updateaoOstate(oId,4)>0) {
			    return new Result(1,"确认收货成功");
		   }else {
			 //回滚
				 TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
			
			   return new Result(0,"确认收货失败");
		   }
		 
	}
	
 
	@PostMapping("addComment")
	@ResponseBody
	public Result addComment(Comment comm,
			@RequestParam(value="file",required = false)MultipartFile file)  {
		if(file!=null) {       //判断用户是否上传了图片
			 //获取文件名
	        String fileName = file.getOriginalFilename();
	       System.out.println("fileName"+fileName);
	        //获取文件后缀名
	        //String suffixName = fileName.substring(fileName.lastIndexOf("."));
	        //重新生成文件名
	       // fileName =fileName+suffixName;
	        System.out.println("fileName:"+fileName);
	        //指定本地文件夹存储图片
	        String filePath = "E:/github/hy-sheji/hy-sheji/Sheji/src/main/resources/static/comment/";
	        try {
	            //将图片保存到static文件夹里
	        	file.transferTo(new File(filePath+fileName));
	        	System.out.println("file"+filePath+fileName);
	        	String f="comment/"+fileName;
	        	comm.setCoImg(f);       
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new Result(0,"图片上传失败！");
	        } 
		}
		 
		  if(comm.getCoComm().isEmpty()||comm.getCoComm().trim()==null) {
			  return new Result(0,"评论不能为空！！");
		  }
		  System.out.println("kkklklklkl"+comm.getCoImg());
		  int i=ub.addComment(comm);
		  if(i>0) {
			 int m= om.updateaoOstate(comm.getCoOid(), 5);//将订单状态改为已评价状态
			 int n= om.updateoState(comm.getCoOid(), 5);
			  if(m>0&&n>0) {
				  return new Result(1,"评论成功");
			  }
			  
		  }
		return new Result(0,"评论失败");
	}
	
	 
	@GetMapping("delOrder")
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public Result delOrder(int oId)  {
		Object savePoint = null;
		 
		//设置回滚点
      savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
		  int m= om.deladminorder(oId);
		  int n= om.delorder(oId);
		  int k= om.delorderdetail(oId);
		   if(m>0&&n>0&&k>0) {
			   return new Result(1,"删除成功！");
		   }else {
			   TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
				
			   return new Result(0,"删除失败");
		   }
		
	}
	
	@GetMapping("user_address")
	public Model useraddress(Model m, HttpSession session)  {
		
		String uName= (String) session.getAttribute("LoginUser");
		  m.addAttribute("se",uName);
		  m.addAttribute("loginImg",session.getAttribute("loginImg"));
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
		System.out.println("======="+add.getAddDft());
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
		
		 @PostMapping("upTou")	 
		 @ResponseBody
		 public Result upTou(@RequestParam(value="file",required = false)MultipartFile file,int uId){
			       
			        //获取文件名
			 
			        String fileName = file.getOriginalFilename();
			       System.out.println("fileName"+fileName);
			        //获取文件后缀名
			        //String suffixName = fileName.substring(fileName.lastIndexOf("."));
			        //重新生成文件名
			       // fileName =fileName+suffixName;
			        System.out.println("fileName:"+fileName);
			        //指定本地文件夹存储图片
			        String filePath = "E:/github/hy-sheji/hy-sheji/Sheji/src/main/resources/static/touxiang/";
			        try {
			            //将图片保存到static文件夹里
			        	file.transferTo(new File(filePath+fileName));
			        	System.out.println("file"+filePath+fileName);
			        	String f="touxiang/"+fileName;
			        	 ub.updatTou(f,uId);
			            return new Result(1,"更改成功",f);
			        } catch (Exception e) {
			            e.printStackTrace();
			            return new Result(0,"更改失败");
			        } 
			    }
	 
}
