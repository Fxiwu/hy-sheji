package com.hy.Sheji.bean;

import java.sql.Timestamp;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;



public class User {

	 
	private Integer uId;
	
	@NotEmpty(message="用户名不能为空")
	private String uName;
	
	@NotEmpty(message="密码不能为空")
	@Length(min=6,max=20,message = "密码必须是6-20位")
	
	private String uPassword;
	
	@NotEmpty(message="联系电话不能为空")
 	private String uPhone;
	private String uSex;
	private Timestamp uCreatetime;
	private String uStatus;
	public Integer getuId() {
		return uId;
	}
	public void setuId(Integer uId) {
		this.uId = uId;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuPassword() {
		return uPassword;
	}
	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}
	public String getuPhone() {
		return uPhone;
	}
	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}
	public String getuSex() {
		return uSex;
	}
	public void setuSex(String uSex) {
		this.uSex = uSex;
	}
	public Timestamp getuCreatetime() {
		return uCreatetime;
	}
	public void setuCreatetime(Timestamp uCreatetime) {
		this.uCreatetime = uCreatetime;
	}
	public String getuStatus() {
		return uStatus;
	}
	public void setuStatus(String uStatus) {
		this.uStatus = uStatus;
	}
	
	
	
}
