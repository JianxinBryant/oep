package com.zr.dao;

import java.util.List;

import com.zr.model.Question;

public interface QuestionDao {
	
	public Question queryOneQuestionByE_id(int e_id, int start, int size);
	
	public List<Question> queryAllQuestionsByE_id(int e_id);
}
