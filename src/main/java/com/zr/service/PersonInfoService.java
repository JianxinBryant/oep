package com.zr.service;

import com.zr.model.User;

public interface PersonInfoService {
	/**
	 * 通过用户名获取用户信息
	 * @param uname
	 * @return
	 */
	public User getUserInfo(String uname);
}
