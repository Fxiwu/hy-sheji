package com.hy.Sheji.bean;

import java.sql.Timestamp;
import java.util.List;
/*
 * 订单表
 */
public class Order {
	private Integer oId;
	private double  oTotal;
	private Timestamp oCreatetime;
	private Integer oState;
	private Integer oUid;
	private Integer oAddid;
	
	private List<Orderdetail> ordertail;
	
	//back order.html中需要
	private  Address address;
	//back order.html中需要
	private User user;
	private User user1;
	 
	
	public User getUser1() {
		return user1;
	}
	public void setUser1(User user1) {
		this.user1 = user1;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Integer getoId() {
		return oId;
	}
	public void setoId(Integer oId) {
		this.oId = oId;
	}
	public double getoTotal() {
		return oTotal;
	}
	public void setoTotal(double oTotal) {
		this.oTotal = oTotal;
	}
	public Timestamp getoCreatetime() {
		return oCreatetime;
	}
	public void setoCreatetime(Timestamp oCreatetime) {
		this.oCreatetime = oCreatetime;
	}
	public Integer getoState() {
		return oState;
	}
	public void setoState(Integer oState) {
		this.oState = oState;
	}
	public Integer getoUid() {
		return oUid;
	}
	public void setoUid(Integer oUid) {
		this.oUid = oUid;
	}
	public Integer getoAddid() {
		return oAddid;
	}
	public void setoAddid(Integer oAddid) {
		this.oAddid = oAddid;
	}
	public List<Orderdetail> getOrdertail() {
		return ordertail;
	}
	public void setOrdertail(List<Orderdetail> ordertail) {
		this.ordertail = ordertail;
	}
	
}
