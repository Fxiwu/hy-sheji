package com.hy.Sheji.biz;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.hy.Sheji.bean.Product;
import com.hy.Sheji.dao.MallMapper;

@Service
public class MallBiz {
   
	@Resource
	private MallMapper mm;
	
	public List<Product> Mall() {
     	//商城mall商品界面展示
     	List<Product> mlist=mm.selectMall();
     	 
     	return mlist;
     	
     }
	
	public List<Product> Category( int cid) {
     	//商城mall商品界面分类展示
     	List<Product> clist=mm.selectCategory(cid);
     	 
     	return clist;
     	
     }
}
