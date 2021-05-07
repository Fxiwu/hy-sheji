package com.hy.Sheji.bean;

import java.sql.Timestamp;

public class Comment {
  
	private  Integer coId;
	private  Integer coOid;
	private  Integer coDid;
    private String coComm;
    private  Integer coUid;
    private  Integer coPid;
    private  Timestamp  createtime;
    private  String coImg;
    private User user;
	public Integer getCoPid() {
		return coPid;
	}
	public void setCoPid(Integer coPid) {
		this.coPid = coPid;
	}
	public Integer getCoId() {
		return coId;
	}
	public void setCoId(Integer coId) {
		this.coId = coId;
	}
	public Integer getCoOid() {
		return coOid;
	}
	public void setCoOid(Integer coOid) {
		this.coOid = coOid;
	}
	
	public Integer getCoDid() {
		return coDid;
	}
	public void setCoDid(Integer coDid) {
		this.coDid = coDid;
	}
	public String getCoComm() {
		return coComm;
	}
	public void setCoComm(String coComm) {
		this.coComm = coComm;
	}
	public Integer getCoUid() {
		return coUid;
	}
	public void setCoUid(Integer coUid) {
		this.coUid = coUid;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getCoImg() {
		return coImg;
	}
	public void setCoImg(String coImg) {
		this.coImg = coImg;
	}
    
}
