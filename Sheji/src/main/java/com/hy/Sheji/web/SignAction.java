package com.hy.Sheji.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.Sheji.bean.Result;
import com.hy.Sheji.bean.User;
import com.hy.Sheji.biz.BizException;
import com.hy.Sheji.biz.SignBiz;
import com.hy.Sheji.util.Utils;
import com.hy.Sheji.util.VerifyUtil;
 
import io.swagger.annotations.ApiOperation;



@Controller
public class SignAction {

	@Resource 
	private SignBiz sb;
	
	@ApiOperation("生成验证码")
	@GetMapping("getCode")
	public void getCode(HttpServletResponse response, HttpSession session) throws Exception{
	    //利用图片工具生成图片
	    //第一个参数是生成的验证码，第二个参数是生成的图片
	    Object[] objs = VerifyUtil.createImage();
	    //将验证码存入Session
	    session.setAttribute("captcha",objs[0]);

	    //将图片输出给浏览器
	    BufferedImage image = (BufferedImage) objs[1];
	    response.setContentType("image/png");
	    OutputStream os = response.getOutputStream();
	    ImageIO.write(image, "png", os);
	}
	
	//注册界面
		@GetMapping("register")
		public String register() {
			return "register";
		}
	
	 @PostMapping("registers")
	 public String registers(@Valid User user,Errors errors,String repassword,String captcha,Model m,HttpSession session) {
	       user.setuStatus("用户");
		 System.out.println(user.getuName()+user.getuPassword()+user.getuPassword());
		// m.addAttribute("user", user);
		if(errors.hasErrors()) {
			m.addAttribute("errors",Utils.asMap(errors));
					return "register";
		}	
		String realcaptcha=session.getAttribute("captcha").toString();
		System.out.println("realcaptcha："+realcaptcha);
		System.out.println("captcha:"+captcha);
		
		if(user.getuPassword().equals(repassword)==false) {
			m.addAttribute("pwderror","两次密码输入不一致");
			System.out.println("两次密码输入不一致");
			return "register";
		}
		if(captcha.equals(realcaptcha)==false) {
			m.addAttribute("captchaerror","验证码错误");
			System.out.println("验证码错误");
			return "register";
		}
		try {
			sb.register(user);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   if(e.getMessage().equals("该用户名已注册账户")) {
			   m.addAttribute("nameerrors","该用户名已注册账户");
		   }
		   if(e.getMessage().equals("该用户名已注册账户")) {
			   m.addAttribute("phoneerrors","该手机号已注册");
		   }
			 
			return "register";
		}
		return "sign";
	}
	
	
	//登录界面
	@GetMapping("sign")
	public String sign() {
		return "sign";
	}
	
	//登录验证
	@PostMapping("login")
	@ResponseBody
	public Result login( User user,String captcha, HttpSession session) {
		 
 	try {
           System.out.println("======="+user.getuStatus());
			String realcaptcha=session.getAttribute("captcha").toString();
                 System.out.println("realcaptcha:"+realcaptcha);
		if(captcha.equals(realcaptcha)) {
		    User dbuser = sb.login(user);
 			Result res = new Result(1, "登录成功!", dbuser);
			session.setAttribute("loginedUser", dbuser);
			 session.setAttribute("LoginUser", dbuser.getuName());
 
			return res;
			}else {
				Result res = new Result(0, "验证码错误!");
				
				return res;
			}
			
		} catch (BizException e) {
 			e.printStackTrace();
			Result res =new Result(e.getMessage());
			 
		return res;
		 
		}
		
	}
	
		 
}
