package com.hy.Sheji.bean;
/*
 * 订单详情表
 */
public class Orderdetail {
	private Integer dId;
	private Integer dCount;
	private Double dTotal;
	private Integer dPid;
	private Integer dOid;
	
	private Product product;
	public Integer getdId() {
		return dId;
	}
	public void setdId(Integer dId) {
		this.dId = dId;
	}
	public Integer getdCount() {
		return dCount;
	}
	public void setdCount(Integer dCount) {
		this.dCount = dCount;
	}
	public Double getdTotal() {
		return dTotal;
	}
	public void setdTotal(Double dTotal) {
		this.dTotal = dTotal;
	}
	public Integer getdPid() {
		return dPid;
	}
	public void setdPid(Integer dPid) {
		this.dPid = dPid;
	}
	public Integer getdOid() {
		return dOid;
	}
	public void setdOid(Integer dOid) {
		this.dOid = dOid;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	 
	
}
