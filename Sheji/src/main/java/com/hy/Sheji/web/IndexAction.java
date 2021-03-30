package com.hy.Sheji.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hy.Sheji.bean.Product;
import com.hy.Sheji.dao.ProductMapper;

@Controller
public class IndexAction{
       
	    @Resource
        ProductMapper pm;
        
        @GetMapping("index" )
        public List<Product> index(Model m) {
			//首页商城热销
        	List<Product> list=pm.selectIndexhot();
        	m.addAttribute("list",list);
        	return list;
        	
        }
        
        
        
}
