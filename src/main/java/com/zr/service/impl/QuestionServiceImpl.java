package com.zr.service.impl;

import java.util.List;

import com.zr.dao.Exam_questionDao;
import com.zr.dao.QuestionDao;
import com.zr.dao.impl.Exam_questionDaoImpl;
import com.zr.dao.impl.QuestionDaoImpl;
import com.zr.model.Question;
import com.zr.service.QuestionService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QuestionServiceImpl implements QuestionService{

	QuestionDao qd = new QuestionDaoImpl();
	Exam_questionDao eqd = new Exam_questionDaoImpl();
	@Override
	public Question queryOneQuestionByE_id(int e_id, int page, int pageSize) {
		
		Question question = qd.queryOneQuestionByE_id(e_id, (page-1)*pageSize, pageSize);
		return question;
	}
	@Override
	public JSONArray quertAllQuestionsByE_id(int e_id) {
		JSONArray ja = new JSONArray();
		List<Question> questions = qd.queryAllQuestionsByE_id(e_id);
		int questionNum = eqd.getQuestionNumByE_id(e_id);
		for (int i = 0; i < questions.size(); i++) {
			JSONObject jo = new JSONObject();
			jo.put("jsonpage", i+1);
			jo.put("jsonq_id", questions.get(i).getQ_id());
			ja.add(jo);
		}
		return ja;
	}

}
