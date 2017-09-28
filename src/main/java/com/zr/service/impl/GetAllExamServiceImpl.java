package com.zr.service.impl;

import java.util.List;

import com.zr.dao.QueryAllscore;
import com.zr.dao.impl.QueryAllscoreImpl;
import com.zr.model.Exam;
import com.zr.model.Scoreofuser;
import com.zr.service.GetAllExamService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetAllExamServiceImpl implements GetAllExamService {
	QueryAllscore qas = new QueryAllscoreImpl();
	@Override
	public JSONArray getAllExam(int page, int pageSize) {
		int start = (page-1)*pageSize;
		List<Exam> exams = qas.getAllExams(start, pageSize);
		JSONArray ja = new JSONArray();
		for (int i = 0; i < exams.size(); i++) {
			JSONObject js = new JSONObject();
			js.put("ename",exams.get(i).getE_name());
			js.put("e_starttime",exams.get(i).getE_starttime());
			js.put("e_endtime",exams.get(i).getE_endtime());
			js.put("e_total",exams.get(i).getE_total());
			ja.add(js);
		}
		return ja;
	}
	@Override
	public int getExamNum() {
		int a = qas.getExamNum();
		return a;
	}

}
