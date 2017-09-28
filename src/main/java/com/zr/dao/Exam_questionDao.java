package com.zr.dao;

import com.zr.model.Exam_question;

public interface Exam_questionDao {

	public Exam_question queryOneExam_questionByE_id(int e_id, int start, int size);
	
	public int getQuestionNumByE_id(int e_id);
}
