package com.zr.dao;

import com.zr.model.Question;

public interface QuestionDao {
	
	public Question queryOneQuestionByE_id(int e_id, int start, int size);
}
