package com.hy.Sheji.biz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hy.Sheji.bean.Area;
import com.hy.Sheji.bean.Product;
import com.hy.Sheji.dao.SpecialMapper;

@Service
public class SpecialBiz {

	 @Resource
     SpecialMapper am;
      
     
      public List<Area> selspecial() {
			//special页面展示
      	List<Area> slist=am.selectArea();
      	return slist;
      	
      }
      
      public List<Product> sdetail(int pAid) {
			//special_detail页面展示
     	List<Product> sdlist=am.selectProduct(pAid);
     	 
     	return sdlist;
     	
     }

	public List<Area> souaddr(String txt) {
		 
		return am.souaddr( txt);
	}
}
