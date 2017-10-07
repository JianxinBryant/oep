package com.zr.service;

import com.zr.model.Question;

import net.sf.json.JSONArray;

public interface QuestionService {

	public Question queryOneQuestionByE_id(int e_id, int page, int pageSize);
	
	public JSONArray quertAllQuestionsByE_id(int e_id);
}
