package com.zr.service.impl;

import com.zr.dao.QuestionDao;
import com.zr.dao.impl.QuestionDaoImpl;
import com.zr.model.Question;
import com.zr.service.QuestionService;

public class QuestionServiceImpl implements QuestionService{

	QuestionDao qd = new QuestionDaoImpl();
	@Override
	public Question queryOneQuestionByE_id(int e_id, int page, int pageSize) {
		
		Question question = qd.queryOneQuestionByE_id(e_id, (page-1)*pageSize, pageSize);
		return question;
	}

}
