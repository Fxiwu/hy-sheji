package com.hy.Sheji.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.hy.Sheji.bean.User;
import com.hy.Sheji.biz.BizException;
import com.hy.Sheji.biz.UserBiz;
import com.hy.Sheji.dao.UserMapper;

@RestController
public class UserAction {

	@Resource
	private UserBiz ub;
	
	@Resource
	private UserMapper um;

	
	@GetMapping("user")
	public Model user(Model m, HttpSession session)  {
		 String uName= (String) session.getAttribute("LoginUser");
		  m.addAttribute("se",uName);
		  User user=ub.selectByuName(uName); 
		  m.addAttribute("user",user);
		  return m;
	}
	 
}
