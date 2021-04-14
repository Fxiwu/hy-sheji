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

	public int insertarea(Area area) {
		return am.insertarea(area);
		 
		
	}

	public int updatearea(Area area) {
		// TODO Auto-generated method stub
		return am.updatearea( area);
	}
    
}
