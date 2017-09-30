package com.zr.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zr.dao.UserDao;
import com.zr.dao.UserDao_hwx;
import com.zr.dao.impl.UserDaoImpl;
import com.zr.dao.impl.UserDaoImpl_hwx;
import com.zr.model.User;
import com.zr.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserServiceImpl implements UserService{
	UserDao udao = new UserDaoImpl();
	UserDao_hwx userdao_hwx = new UserDaoImpl_hwx();
	/**
	 * 获取用户信息
	 */
	@Override
	public JSONObject getUserInfo(int page, int pageSize, String userkey) {
		JSONObject json = new JSONObject();
		List<User> userlist = new ArrayList<User>() ;
		
		//获取total
		int count = udao.SelectUserInfoByUserkey((page-1)*pageSize, pageSize, userkey, userlist);
		json.put("total", count);
		//获取rows数组对象
		JSONArray jsonArray = new JSONArray();
		for (User user : userlist) {
			JSONObject u = new JSONObject();
			u.put("u_id", user.getU_id());
			u.put("u_name", user.getU_name());
			u.put("u_telephone",user.getU_telephone() );
			u.put("u_email", user.getU_email());
			u.put("u_registertime", user.getU_registertime());
			jsonArray.add(u);
		}
		json.put("rows", jsonArray);
		return json;
	}
	
	/**
	 * 删除用户
	 */
	@Override
	public int RemoveUserByUid(List ids) {
		int l = udao.RemoveUserByUid(ids);
		return l;
	}
	
	/**
	 * 编辑用户
	 */
	@Override
	public int editUser(User user) {
		int i =udao.editUser(user);
		return i;
	}

	@Override
	public JSONArray getUserofscoreAndExam(int u_id) {
		return udao.getUserofscoreAndExam(u_id);
	}

	@Override
	public int getU_idByUname(String u_name) {
		return userdao_hwx.getU_idByUname(u_name);
	}
}
