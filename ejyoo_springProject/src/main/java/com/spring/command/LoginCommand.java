package com.spring.command;

public class LoginCommand {
	// 클라이언트에 넘어오는 데이터를 기준으로 데이터 매핑 ! 변수명 기준!
	private String id;
	private String pwd;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
