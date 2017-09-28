package com.zr.service;

import java.util.List;

import com.zr.model.User;

import net.sf.json.JSONObject;

public interface UserService_leiwei {
	/**
	 * 获得用户信息 
	 * 
	 * @param start
	 * @param pageSize
	 * @param userkey
	 * @return 用户信息的JSON对象
	 */
	public JSONObject getUserInfo(String boxvalue,int page, int pageSize, String userkey);

	/**
	 * 删除用户记录通过uid
	 * 
	 * @param ids
	 * @return
	 */
	public int RemoveUserByUid(List ids);

	/**
	 * 编辑用户信息
	 * 
	 * @param user
	 */
	public int editUser(User user);
}
