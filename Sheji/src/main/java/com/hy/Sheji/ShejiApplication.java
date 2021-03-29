package com.hy.Sheji;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@SpringBootApplication
@MapperScan("com.hy.Sheji")
public class ShejiApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(ShejiApplication.class, args);
	}

	 
}
