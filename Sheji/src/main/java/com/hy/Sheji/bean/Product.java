package com.hy.Sheji.bean;

import java.sql.Timestamp;

public class Product {

	private Integer pId; 
	private String pName;
	private Double price;
	private String pImg;
	private String pDesc;
	private Timestamp pCreatetime;
	private Integer pCid;
	private Integer pHot;
	private Integer pAid;
	private String  pGuige;
	private Integer pKucun;
	private String pDanwei;
	
	private Area area;
	private Category category;
	
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	
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
	public Timestamp getpCreatetime() {
		return pCreatetime;
	}
	public void setpCreatetime(Timestamp pCreateime) {
		this.pCreatetime = pCreateime;
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
	public String getpGuige() {
		return pGuige;
	}
	public void setpGuige(String pGuige) {
		this.pGuige = pGuige;
	}
	public Integer getpKucun() {
		return pKucun;
	}
	public void setpKucun(Integer pKucun) {
		this.pKucun = pKucun;
	}
	public String getpDanwei() {
		return pDanwei;
	}
	public void setpDanwei(String pDanwei) {
		this.pDanwei = pDanwei;
	}
	 
	 
}
