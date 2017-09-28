package com.zr.service;

import com.zr.model.Exam_question;

public interface Exam_questionService {

	public Exam_question queryOneExam_questionByE_id(int e_id, int page, int pageSize);
	
	public int getQuestionNumByE_id(int e_id);
}
