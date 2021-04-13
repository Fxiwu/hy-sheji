package com.hy.Sheji.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;

import com.hy.Sheji.bean.Category;
import com.hy.Sheji.bean.Product;

public interface ProductMapper {

	//首页商城精选商品展示
	@Select("select * from hy_product where p_hot=1")
	List<Product> selectIndexhot();
	
	//产品详情+地区展示
		@Select("select * from hy_product where p_id=#{pId}")
		@Results({
			 @Result(column="p_id",property="pId"),
			 @Result(column="p_aid",property="pAid"),
			 @Result(column="p_aid",property="area",
					 one=@One(select="com.hy.Sheji.dao.SpecialMapper.selArea" ))
		 })
		Product selectPro(int pId);
	 //product_detail喜欢模块
	@Select("select * from hy_product where p_hot=1 order by p_createtime desc limit 0,3 ;")
	List<Product> selectLike();
	
	//查询商品
		@Select("select * from hy_product where p_id=#{pId}")
     Product selectBypid(int pId);

		
	//后台商品管理 product.html全部商品展示
	 String Sql="<where><if  test='pCid!=null'> and p_cid = #{pCid} </if>" 
	 			+" <if  test='pAid!=null'> and p_aid = #{pAid} </if></where> " ;
	@Select("<script>"
			+ "select * from hy_product "
			+ Sql
			+ "</script>")
		@Results({	@Result(column="p_cid",property="category",
			         one=@One(select="selectCategory")),
				     @Result(column="p_aid",property="area",
				     one=@One(select="com.hy.Sheji.dao.AreaMapper.selectAreaD"))
		     })
	List<Product> productquery(Product pro);

	//商品类别
	@Select("select * from hy_category where pc_id=#{pCid}")	
	Category selectCategory(int pCid);

	//后台商品管理  product.html 商品删除
	@Delete("delete from hy_product where p_id=#{pId}")
	int delpro(int pId);

	 
	//后台商品管理  product.html 商品修改
	 String sql="<if  test='pCid!=null'>,p_cid = #{pCid} </if>" 
	 			+" <if  test='pAid!=null'>,p_aid = #{pAid} </if> " ;
  	@Update("<script> update hy_product set p_desc =#{pDesc}"
 			+ ",p_name=#{pName}"
 			+ ",p_guige=#{pGuige}"
 			+ ", p_hot=#{pHot}"
 			+ ",p_img=#{pImg}"
 			+ ",price=#{price} "
 			+ sql
 			+ " where p_id=#{pId} </script>")
	int updatepro(Product pro);
 	
 	@Select("select * from hy_category")
 	List<Category> category();

 	@Insert("insert into hy_product values(null,#{pName},"
 			+ "#{price},#{pImg},#{pDesc},now(),#{pCid},#{pHot},#{pAid},#{pGuige})")
	int insertpro(Product pro);
}

  