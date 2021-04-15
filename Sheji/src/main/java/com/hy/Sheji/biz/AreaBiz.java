package com.hy.Sheji.biz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy.Sheji.bean.Area;
import com.hy.Sheji.dao.AreaMapper;

@Service
public class AreaBiz {

	 @Resource
	 AreaMapper am;
      
     
      public List<Area> area() {
			//area页面展示
      	List<Area> alist=am.selectArea();
      	return alist;
      	
      }
      
      public Area areade(int aId) {
			//area页面展示
    	Area adlist=am.selectAreaD(aId);
    	return adlist;
    	
    }

      //back 中添加area页面
	public int insertarea(Area area) {
		return am.insertarea(area);
		 
		
	}
	 //back 中更新area页面
	public int updatearea(Area area) {
		// TODO Auto-generated method stub
		return am.updatearea( area);
	}
	//back 中删除area页面
	public int  delarea(int aId) {
		return am.delarea(aId);
	}
    
}
