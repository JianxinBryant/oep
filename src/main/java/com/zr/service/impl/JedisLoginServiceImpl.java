package com.zr.service.impl;

import com.zr.dao.JedisLoginDao;
import com.zr.dao.impl.JedisLoginDaoImpl;
import com.zr.service.JedisLoginService;

public class JedisLoginServiceImpl implements JedisLoginService{

	JedisLoginDao jld = new JedisLoginDaoImpl();
	@Override
	public void insertUsernameAndSessionId(String userName, String sessionId) {
		jld.insertUsernameAndSessionId(userName, sessionId);
	}
	@Override
	public String getSessionIdByUsername(String userName) {
		
		return jld.getSessionIdByUsername(userName);
	}

}
