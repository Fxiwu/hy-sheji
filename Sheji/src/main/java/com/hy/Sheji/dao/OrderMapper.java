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

import com.hy.Sheji.bean.Address;
import com.hy.Sheji.bean.AdminOrder;
import com.hy.Sheji.bean.Order;
import com.hy.Sheji.bean.Orderdetail;
import com.hy.Sheji.bean.Product;

public interface OrderMapper {

	//新增订单Order   默认订单状态为0  地址为1
	  @Insert("insert into hy_order values(null,#{oTotal},"
	  		+ "now(),0,#{oUid},#{oAddid})")
	  @Options(useGeneratedKeys = true,keyProperty = "oId",keyColumn = "o_id")
	  public int insertOrder(Order order);
	
	//新增订单Order对应的Orderdeatil    
	  @Insert("<script> insert into hy_orderdetail(d_count,d_total,d_pid,d_oid) values "
	  		+ "<foreach collection='odlist' item='odlist' separator=','>"
	  		+ "  (#{odlist.dCount},#{odlist.dTotal}," 
	  		+ "#{odlist.dPid},#{odlist.dOid})"
	  		+  "  </foreach></script>")
	  public int insertOrderdetail(@Param("odlist") List<Orderdetail> odlist);
	
  	  //查询Order 关联selectOrderdetail
  	  @Select("select * from hy_order where o_id=#{oId} ")
  	  @Results({@Result(column="o_id",property="oId"),
  		        @Result(column="o_id",property="ordertail",
  		       one=@One(select="selectOrderdetail")),
  		     @Result(column="o_addid",property="address",
		       one=@One(select="com.hy.Sheji.dao.UserMapper.selectAddressByaid"))
  		        })
  	  public  Order  selectOrder(int oId);
  	  
  	  //查询orderdetail
 	  @Select("select * from hy_orderdetail where d_oid=#{dOid}")
  	  @Results({@Result(column="d_oid",property="dOid"),
 	        @Result(column="d_pid",property="product",
 	       one=@One(select="com.hy.Sheji.dao.ProductMapper.selectBypid"))
 	        })
  	  public Orderdetail selectOrderdetail(int dOid);

 	 //jiesuan中删除某商品更改order表total
 	  @Update("update hy_order set o_total=o_total-#{price} where o_id=#{oid}")
	public int updateorder( int oid,double price);

 	//删除orderdetail中对应的orderdetail
 	  @Delete("delete  from hy_orderdetail where d_oid=#{dOid} and d_pid=#{dPid}")
	public int delod(int dPid, int dOid);
 	 
 	//当total减为0的时候删除adminorder中对应的记录  user_order也用
 	 @Delete("delete  from hy_adminorder where o_id=#{oId} ")
 	public int deladminorder( int oId);  
 	 
 	//user_order中删除 orderdetail中对应的记录
 	 @Delete("delete  from hy_orderdetail where d_oid=#{oId} ")
 	public int delorderdetail( int oId);
 	 
 	//user_order中删除 order中对应的记录  
 	 @Delete("delete  from hy_order where o_id=#{oId} ")
 	public int delorder( int oId); 
 	 
 	//jiesuan界面中确定修改收货地址
 	  @Update("update hy_order set o_addid=#{addId} where o_id=#{oid}")
	public int updateOrderAddr(int addId, int oid);
 	  
 	  //back中 orders.html 中
 	 @Select("select * from  hy_order where o_id=#{oid}")
 	@Results({@Result(column="o_uid",property="user",
 			          one=@One(select="com.hy.Sheji.dao.UserMapper.selectByuId")),
              @Result(column="o_addid",property="address",
            		   one=@One(select="com.hy.Sheji.dao.UserMapper.selectAddressByaid"))
            		  })
 	public Order OrderByOid(int oid);

 	 
 	@Insert("insert into hy_adminorder (o_id,u_name,add_name,o_total,o_state,o_createtime,"
 			+ "add_addr,add_phone) values"
 			 
	  		+ "(#{oId},#{user.uName}," 
	  		+ "#{address.addName},#{oTotal},"
	  		+ "#{oState},#{oCreatetime},"
	  		+ "#{address.addAddr},#{address.addPhone})"
	  		 )
	public int insertadminorder(Order od);

 	@Select("<script>"
 			+ "select * from hy_adminorder "
 			+ "<where>"
 			+ "<if test='uName!=null'>u_name=#{uName}</if>"
 			+ "<if test='oState!=null'> and o_state=#{oState}</if>"
 			+ "</where>"
 			+ "order by o_createtime desc"
 			+ "</script>")
	public List<AdminOrder> adminOrderquery(AdminOrder or);
 	
 	//查询adminorder对应条件下总数
 	@Select("<script>"
			+ "select count(*) from hy_adminorder"
			+ "<where>"
			+ "<if test='uName!=null'>u_name=#{uName}</if>"
 			+ "<if test='oState!=null'> and o_state=#{oState}</if>"
 			+ "</where>"
			+ "</script>")
	int  countaord(AdminOrder or);
 	
 	//jiesuan界面中确定地址后修改adminordeAddr中收货地址
 	@Update("<script>"
 			+ "update hy_adminorder"
 			+ "<set>"
 			+ "<if test='adminOrder.addAddr!=null'>add_addr=#{adminOrder.addAddr}</if>"
 			+ "<if test='adminOrder.addName!=null'>,add_name=#{adminOrder.addName}</if>"
 			+ "<if test='adminOrder.addPhone!=null'>,add_phone=#{adminOrder.addPhone}</if>"
 			+ "</set>"
 			+ "where o_id=#{oId}"
 			+ "</script>")
	public int updateadminordeAddr(AdminOrder adminOrder,int oId);
 
 	//address
 	 @Select("select * from hy_address where add_id=#{addId}")
 	  public Address selectAddressById(int addId);

 	//back order.html中修改订单信息后保存
 	@Update("<script>"
 			+ "update hy_adminorder"
 			+ "<set>"
 			+ "<if test='addAddr!=null'>add_addr=#{addAddr}</if>"
 			+ "<if test='addName!=null'>,add_name=#{addName}</if>"
 			 +"<if test='oState!=null'>,o_state=#{oState}</if>"
 			+ "<if test='addPhone!=null'>,add_phone=#{addPhone}</if>"
 			 
 			+ "</set>"
 			+ "where o_id=#{oId}"
 			+ "</script>")
	public int updateadminorder(int oId, String addName, int oState, String addAddr, String addPhone);

   
 	 //user_order中更改order表ostate 确认收货  4
		/*
		 * @Update("update hy_order set o_state=4 where o_id=#{oid}") public int
		 * updateoState( int oid);
		 */
 
	//user_order中更改adminorder表中ostate 确认收货  4
	/*
	 * @Update("update hy_adminorder set  o_state=4 where o_id=#{oid}") public int
	 * updateadminorderOstate( int oid);
	 */
	//付款中更改order表ostate  1
	  @Update("update hy_order set o_state=#{oState} where o_id=#{oid}")
	public int updateoState( int oid,int oState);
 
	//user_order中更改adminorder表中ostate  1
	  @Update("update hy_adminorder set  o_state=#{oState} where o_id=#{oid}")
	public int updateaoOstate( int oid,int oState);
	 
	/*
	 * //back order.html中修改订单信息后 相应改变order表的ostate
	 * 
	 * @Update("update hy_order set o_state=#{oState} where o_id=#{oId}") public int
	 * updateorderState(int oId, int oState);
	 */
 	  
}
