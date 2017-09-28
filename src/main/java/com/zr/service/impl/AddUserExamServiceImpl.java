package com.zr.service.impl;

import com.zr.service.AddUserExamService;
import com.zr.dao.AddUserExamDao;
import com.zr.dao.QueryScoreDao;
import com.zr.dao.impl.AddUserExamDaoImpl;
import com.zr.dao.impl.QueryScoreDaoImpl;

public class AddUserExamServiceImpl implements AddUserExamService {
	AddUserExamDao aue = new AddUserExamDaoImpl();
	QueryScoreDao qsdao = new QueryScoreDaoImpl();
	@Override
	public void addUserExam(String ename, String uname) {
		int eid = qsdao.getEidByEname(ename);
		int uid = qsdao.getUidByUname(uname);
		aue.addUserExam(eid, uid);
	}
	@Override
	public boolean isApply(String ename, String uname) {
		int eid = qsdao.getEidByEname(ename);
		int uid = qsdao.getUidByUname(uname);
		boolean a = aue.isApply(eid, uid);
		return a;
	}

}
