package com.nextech.erp.status;


public class UserStatus {
	private int code;
	private String message;
	private Object data;
	private Object user;

	public UserStatus() {
	}
	public UserStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public UserStatus(int code, String message,Object data, Object user) {
		this.code = code;
		this.message = message;
		this.data = data;
		this.user= user;
	}
	public UserStatus(int code, String message,String data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public UserStatus(int code,Object data){
		this.code=code;
		this.data=data;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}
	public Object getUser() {
		return user;
	}
	public void setUser(Object user) {
		this.user = user;
	}



}
