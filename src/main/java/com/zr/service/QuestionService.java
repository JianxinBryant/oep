package com.zr.service;

import com.zr.model.Question;

public interface QuestionService {

	public Question queryOneQuestionByE_id(int e_id, int page, int pageSize);
}
