package com.hy.Sheji.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hy.Sheji.bean.Order;
import com.hy.Sheji.bean.Orderdetail;

public interface OrderMapper {

	//新增订单Order   默认订单状态为0  地址为1
	  @Insert("insert into hy_order values(null,#{oTotal},"
	  		+ "now(),0,#{oUid},1)")
	  @Options(useGeneratedKeys = true,keyProperty = "oId",keyColumn = "o_id")
	  public int insertOrder(Order order);
	
	//新增订单Order对应的Orderdeatil   默认订单状态为0  地址为1
	  @Insert("<script> insert into hy_orderdetail(d_count,d_total,d_pid,d_oid) values "
	  		+ "<foreach collection='odlist' item='odlist' separator=','>"
	  		+ "  (#{odlist.dCount},#{odlist.dTotal}," 
	  		+ "#{odlist.dPid},#{odlist.dOid})"
	  		+  "  </foreach></script>")
	  public int insertOrderdetail(@Param("odlist") List<Orderdetail> odlist);
	
  	  //查询Order 关联selectOrderdetail
  	  @Select("select * from hy_order where o_id=#{oId} and  o_state=0")
  	  @Results({@Result(column="o_id",property="oId"),
  		        @Result(column="o_id",property="ordertail",
  		       one=@One(select="selectOrderdetail"))
  		        })
  	  public  List<Order>  selectOrder(int oId);
  	  
  	  //查询orderdetail
 	  @Select("select * from hy_orderdetail where d_oid=#{dOid}")
  	  @Results({@Result(column="d_oid",property="dOid"),
 	        @Result(column="d_pid",property="product",
 	       one=@One(select="com.hy.Sheji.dao.ProductMapper.selectBypid"))
 	        })
  	  public List<Orderdetail> selectOrderdetail(int dOid);

 	 //jiesuan中删除某商品更改order表total
 	  @Update("update hy_order set o_total=o_total-#{price} where o_id=#{oid}")
	public int updateorder( int oid,double price);

 	//删除orderdetail中对应的orderdetail
 	  @Delete("delete  from hy_orderdetail where d_oid=#{dOid} and d_pid=#{dPid}")
	public int delod(int dPid, int dOid);
 	  
}
