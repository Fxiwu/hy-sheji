package com.hy.Sheji.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    	registry.addResourceHandler("../upload/**").addResourceLocations("file:E:/github/hy-sheji/hy-sheji/Sheji/src/main/resources/static/upload/");
	    	registry.addResourceHandler("../areaimg/**").addResourceLocations("file:E:/github/hy-sheji/hy-sheji/Sheji/src/main/resources/static/areaimg/");
	    }
	 }
