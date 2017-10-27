package com.zr.pojo;

/**
 * 用户数据传输对象
 * @author zjx
 *
 */
public class UserInfo {
	
	private String address;
    private String sessid;
    private String username;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSessid() {
		return sessid;
	}
	public void setSessid(String sessid) {
		this.sessid = sessid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
    
}
