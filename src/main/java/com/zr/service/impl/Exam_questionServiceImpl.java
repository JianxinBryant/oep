package com.zr.service.impl;

import java.util.List;

import com.zr.dao.Exam_questionDao;
import com.zr.dao.impl.Exam_questionDaoImpl;
import com.zr.model.Exam_question;
import com.zr.service.Exam_questionService;

public class Exam_questionServiceImpl implements Exam_questionService{

	Exam_questionDao eqd = new Exam_questionDaoImpl();
	@Override
	public Exam_question queryOneExam_questionByE_id(int e_id, int page, int pageSize) {
		
		Exam_question exam_question = eqd.queryOneExam_questionByE_id(e_id, (page-1)*pageSize, pageSize);
		return exam_question;
	}
	@Override
	public int getQuestionNumByE_id(int e_id) {
		
		return eqd.getQuestionNumByE_id(e_id);
	}

}
