package com.zr.dao;

import java.util.List;

import com.zr.model.User;

public interface UserDao_leiwei {
	/**
	 * 根据用户关键字分页查询用户信息,获得总行数count 
	 * @param start
	 * @param pageSize
	 * @param userkey
	 * @return
	 */
	public int SelectUserInfoByUserkey(int start,int pageSize,String userkey,List<User> userlist);
	
	/**
	 * 删除用户信息
	 * @param ids
	 * @return
	 */
	 
	public int RemoveUserByUid(List ids);
	/**
	 * 编辑用户信息
	 * @param user
	 */
	public int editUser(User user);
}
