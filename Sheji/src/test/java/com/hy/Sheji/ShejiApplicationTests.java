package com.hy.Sheji;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hy.Sheji.bean.Product;
import com.hy.Sheji.dao.ProductMapper;

@SpringBootTest
class ShejiApplicationTests {
	//判断数据源是否连接成功
	 @Autowired
	    DataSource dataSource;

	    @Test
	    
	    void contextLoads() throws Exception {
	        System.out.println("获取的数据库连接为:"+dataSource.getConnection());
	    }
         @Autowired
	    	private ProductMapper pm;
	    @Test
	    public void test01(){
	    	
	    	List<Product> userInfo=pm.selectIndexhot();
	        System.out.println("userInfo:"+userInfo.toString());
	    }
	 }
