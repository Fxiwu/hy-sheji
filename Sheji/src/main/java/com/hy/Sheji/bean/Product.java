package com.hy.Sheji.bean;

import java.sql.Timestamp;

public class Product {

	private Integer pId; 
	private String pName;
	private Double price;
	private String pImg;
	private String pDesc;
	private Timestamp pCreateime;
	private Integer pCid;
	private Integer pHot;
	private Integer pAid;
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getpImg() {
		return pImg;
	}
	public void setpImg(String pImg) {
		this.pImg = pImg;
	}
	public String getpDesc() {
		return pDesc;
	}
	public void setpDesc(String pDesc) {
		this.pDesc = pDesc;
	}
	public Timestamp getpCreateime() {
		return pCreateime;
	}
	public void setpCreateime(Timestamp pCreateime) {
		this.pCreateime = pCreateime;
	}
	public Integer getpCid() {
		return pCid;
	}
	public void setpCid(Integer pCid) {
		this.pCid = pCid;
	}
	public Integer getpHot() {
		return pHot;
	}
	public void setpHot(Integer pHot) {
		this.pHot = pHot;
	}
	public Integer getpAid() {
		return pAid;
	}
	public void setpAid(Integer pAid) {
		this.pAid = pAid;
	}
	 
	 
}
