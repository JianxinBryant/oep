package com.zr.service;

import java.util.List;

import com.zr.model.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface UserService {
	/**
	 * 获得用户信息
	 * @param start
	 * @param pageSize
	 * @param userkey
	 * @return  用户信息的JSON对象
	 */
	public JSONObject getUserInfo(int page, int pageSize, String userkey);
	
	/**
	 * 删除用户记录通过uid
	 * @param ids
	 * @return
	 */
	public int RemoveUserByUid(List ids);
	
	/**
	 * 编辑用户信息
	 * @param user
	 */
	public int editUser(User user);
	
	public JSONArray getUserofscoreAndExam(int u_id);
	
	public int getU_idByUname(String u_name);
}
