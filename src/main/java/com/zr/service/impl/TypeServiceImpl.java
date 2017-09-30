package com.zr.service.impl;

import com.zr.dao.TypeDao;
import com.zr.dao.impl.TypeDaoImpl;
import com.zr.model.Type;
import com.zr.service.TypeService;

public class TypeServiceImpl implements TypeService{

	TypeDao typeDao = new TypeDaoImpl();
	@Override
	public Type queryTypeByT_id(int t_id) {
		return typeDao.getTypeById(t_id);
	}

}
