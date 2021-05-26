package com.hy.Sheji.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

	//是在请求到控制器之前执行，返回false终止控制器执行方法
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 判断当前是否登录
		if (request.getSession().getAttribute("LoginUser") == null) {
			
			// 判断当前是 页面跳转还是 ajax 请求
			String accept = request.getHeader("Accept");
			if(accept.startsWith("application/json")) {
 				response.setContentType("application/json;charset=utf-8");
 				response.getWriter().append("<script language=\"javascript\">alert('请先登录！！');"
						+ "window.location.href='/sign'</script>");
			} else {
				// 页面跳转请求
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("<script language=\"javascript\">alert('请先登录！！');"
						+ "window.location.href='/sign'</script>");
			}
			return false;
		}
		
		return true;
	}

}
