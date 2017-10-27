package com.zr.dao;

public interface JedisLoginDao {
	
	public void insertUsernameAndSessionId(String userName, String sessionId);
	public String getSessionIdByUsername(String userName);
}
