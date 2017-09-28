package com.zr.service.impl;

import com.zr.dao.PersonInfoDao;
import com.zr.dao.impl.PersonInfoDaoImpl;
import com.zr.model.User;
import com.zr.service.PersonInfoService;

public class PersonInfoServiceImpl implements PersonInfoService {
	PersonInfoDao pdao = new PersonInfoDaoImpl();
	@Override
	public User getUserInfo(String uname) {
		User user = pdao.getUserInfo(uname);
		return user;
	}

}
