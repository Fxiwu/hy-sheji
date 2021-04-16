package com.hy.Sheji.bean;

import java.sql.Timestamp;
import java.util.List;
/*
 * 管理员订单表
 */
public class AdminOrder {
	private Integer oId;
	private String uName;
	private  String addName; 
	private double  oTotal;
	private Integer oState;
	private Timestamp oCreatetime;
    private  String addAddr;
    private  String addPhone;
	public Integer getoId() {
		return oId;
	}
	public void setoId(Integer oId) {
		this.oId = oId;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getAddName() {
		return addName;
	}
	public void setAddName(String addName) {
		this.addName = addName;
	}
	public double getoTotal() {
		return oTotal;
	}
	public void setoTotal(double oTotal) {
		this.oTotal = oTotal;
	}
	public Integer getoState() {
		return oState;
	}
	public void setoState(Integer oState) {
		this.oState = oState;
	}
	public Timestamp getoCreatetime() {
		return oCreatetime;
	}
	public void setoCreatetime(Timestamp oCreatetime) {
		this.oCreatetime = oCreatetime;
	}
	public String getAddAddr() {
		return addAddr;
	}
	public void setAddAddr(String addAddr) {
		this.addAddr = addAddr;
	}
	public String getAddPhone() {
		return addPhone;
	}
	public void setAddPhone(String addPhone) {
		this.addPhone = addPhone;
	}
	
 
	 
}
