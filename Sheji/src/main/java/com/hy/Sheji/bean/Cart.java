package com.hy.Sheji.bean;

//购物车实体类
public class Cart {

	private  Integer cId;
	private  Integer cUid;
	private  Integer cPid;
	private  Integer cCount;
	
	
	private  Product product;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Integer getcId() {
		return cId;
	}
	public void setcId(Integer cId) {
		this.cId = cId;
	}
	public Integer getcUid() {
		return cUid;
	}
	public void setcUid(Integer cUid) {
		this.cUid = cUid;
	}
	public Integer getcPid() {
		return cPid;
	}
	public void setcPid(Integer cPid) {
		this.cPid = cPid;
	}
	public Integer getcCount() {
		return cCount;
	}
	public void setcCount(Integer cCount) {
		this.cCount = cCount;
	}
	
	
}
