package com.pojo;

public class User {
	private Integer Uid;
	private String Uname;
	private String PassWord;
	
	
	
	public User(Integer uid, String uname, String passWord) {
		super();
		Uid = uid;
		Uname = uname;
		PassWord = passWord;
	}
	public User() {
		super();
	}
	public Integer getUid() {return Uid;}
	public void setUid(Integer uid) {Uid = uid;}
	public String getUname() {return Uname;}
	public void setUname(String uname) {Uname = uname;}
	public String getPassWord() {return PassWord;}
	public void setPassWord(String passWord) {PassWord = passWord;}
	@Override
	public String toString() {
		return "User [Uid=" + Uid + ", Uname=" + Uname + ", PassWord="
				+ PassWord + "]";
	}
	
}
