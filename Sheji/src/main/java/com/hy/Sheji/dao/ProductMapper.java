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
import com.hy.Sheji.bean.Comment;
import com.hy.Sheji.bean.Product;

public interface ProductMapper {

	//首页商城精选商品展示
	@Select("select * from hy_product where p_hot=1 order by p_createtime desc limit 0,9 ")
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
	@Select("select * from hy_product where p_hot=1 order by p_createtime desc limit 0,5 ")
	List<Product> selectLike();
	
	 //product_detail 中商品评价
		@Select("select * from hy_comment where co_pid=#{pId}  order by createtime desc")
		@Results({
			 
			 @Result(column="co_aid",property="pAid"),
			 @Result(column="co_uid",property="user",
					 one=@One(select="com.hy.Sheji.dao.UserMapper.selectByuId" ))
		 })
	List<Comment> SelectComm(int pId);
	
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
 			+ ",p_kucun=#{pKucun} "
 			+ sql
 			+ " where p_id=#{pId} </script>")
	int updatepro(Product pro);
 	
 	 
  //后台商品管理  product.html 商品添加
 	@Insert("insert into hy_product values(null,#{pName},"
 			+ "#{price},#{pImg},#{pDesc},now(),#{pCid},#{pHot},#{pAid},#{pGuige},#{pKucun})")
	int insertpro(Product pro);
 	
 	//suosou
 	@Select("select * from hy_product where p_name like '%${txt}%'")
 	List<Product> SuoProduct(String txt);
 	
 	@Update(" update hy_product set p_kucun=p_kucun-#{count} "
 			
 			+ " where p_id=#{pId} ")
 	int updateKucun(int pId,int count);

 	//当订单删除时，重新添加原有的数量
@Update(" update hy_product set p_kucun=p_kucun+#{count} "
 			
 			+ " where p_id=#{pId} ")
	int addKucun(int pId, int count);
}

  