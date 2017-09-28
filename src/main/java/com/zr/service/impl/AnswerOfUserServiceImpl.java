package com.zr.service.impl;

import com.zr.dao.AnswerOfUserDao;
import com.zr.dao.impl.AnswerOfUserDaoImpl;
import com.zr.model.Answerofuser;
import com.zr.service.AnswerOfUserService;

public class AnswerOfUserServiceImpl implements AnswerOfUserService{

	AnswerOfUserDao answerofuserdao = new AnswerOfUserDaoImpl();
	@Override
	public void commitAnswer(int u_id, int e_id, int q_id, String answer) {
		
		answerofuserdao.commitAnswer(u_id, e_id, q_id, answer);
	}
	
	
	@Override
	public void delectAnswer(int u_id, int e_id, int q_id) {
		
		answerofuserdao.deleteAnswer(u_id, e_id, q_id);
	}


	@Override
	public boolean checkAnswer(int u_id, int e_id, int q_id) {
		
		return answerofuserdao.checkAnswer(u_id, e_id, q_id);
		
		
	}


	@Override
	public Answerofuser selectAnswerofuser(int u_id, int e_id, int q_id) {
		// TODO Auto-generated method stub
		return answerofuserdao.selectAnswerofuser(u_id, e_id, q_id);
	}

	
}
