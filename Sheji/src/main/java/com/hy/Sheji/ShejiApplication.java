package com.hy.Sheji;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hy.Sheji.web.LoginInterceptor;

 
@SpringBootApplication
@MapperScan("com.hy.Sheji")
public class ShejiApplication implements WebMvcConfigurer{

	@Override   //拦截器注册方法 拦截器注册器
	public void addInterceptors(InterceptorRegistry registry) {
		 
		WebMvcConfigurer.super.addInterceptors(registry);
		InterceptorRegistration	ir=registry.addInterceptor(new LoginInterceptor());
		ir.addPathPatterns("/user","/user_order","/user_address",
				"/cart","/jiesuan","/fukuan","/liji","/user","/user1");
	}
	
	 
	public static void main(String[] args) {
		SpringApplication.run(ShejiApplication.class, args);
	}

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/touxiang/**").addResourceLocations("file:E:/github/hy-sheji/hy-sheji/Sheji/src/main/resources/static/touxiang/");
     	registry.addResourceHandler("/comment/**").addResourceLocations("file:E:/github/hy-sheji/hy-sheji/Sheji/src/main/resources/static/comment/");

	}
  
}
