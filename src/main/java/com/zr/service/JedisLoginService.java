package com.zr.service;

public interface JedisLoginService {

	public void insertUsernameAndSessionId(String userName, String sessionId);
	
	public String getSessionIdByUsername(String userName);
}
