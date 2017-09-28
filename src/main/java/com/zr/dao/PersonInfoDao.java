package com.zr.dao;

import com.zr.model.User;

public interface PersonInfoDao {
	/**
	 * 通过用户名获取用户信息
	 * @param uname
	 * @return
	 */
	public User getUserInfo(String uname);
}
