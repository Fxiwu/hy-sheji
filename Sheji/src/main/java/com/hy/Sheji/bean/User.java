package com.hy.Sheji.bean;

import java.sql.Timestamp;
import java.util.List;

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
	
	@NotEmpty(message="手机号码不能为空")
	@Length(min = 11, max = 11, message = "手机号码只能为11位")
	@Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号码格式有误")
 	private String uPhone;
	private String uSex;
	private Timestamp uCreatetime;
	private String uStatus;
	private String uImg;
	
	private List<Address> address;
	
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
	public String getuImg() {
		return uImg;
	}
	public void setuImg(String uImg) {
		this.uImg = uImg;
	}
	public List<Address> getAddress() {
		return address;
	}
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	 
	
	
	
}
