package com.example.msl.rainbow1;


import java.util.Date;
public class User {
	private int id;
	private String tel;
	private String userName;
	private String password;
	private String headPicture;
	private String sex;
	private String address;
	private String birth;
	private String qqId;
	private String weiboId;


	public String getQqId() {
		return qqId;
	}

	public void setQqId(String qqId) {
		this.qqId = qqId;
	}

	public String getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}

	public int getUserId() {
		return id;
	}
	public void setUserId(int userId) {
		this.id = userId;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHeadPicture() {
		return headPicture;
	}
	public void setHeadPicture(String headPicture) {
		this.headPicture = headPicture;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId=" + id +
				", tel='" + tel + '\'' +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", headPicture='" + headPicture + '\'' +
				", sex='" + sex + '\'' +
				", address='" + address + '\'' +
				", birth=" + birth +
				'}';
	}
}
