package com.zr.service.qktemp;

import java.util.LinkedList;
import java.util.List;

import com.zr.dao.ExamDao;
import com.zr.dao.TypeDao;
import com.zr.dao.impl.ExamDaoImpl;
import com.zr.dao.impl.TypeDaoImpl;
import com.zr.dao.qktemp.QuestionDao;
import com.zr.dao.qktemp.QuestionDaoImpl;
import com.zr.model.Question;
import com.zr.model.Type;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QuestionServiceImpl implements QuestionService {
	QuestionDao qdao = new QuestionDaoImpl();
	ExamDao examDao = new ExamDaoImpl();

	/**
	 * 若试卷中已存在题型信息则返回已存在的，若没有的某题型的试题，则返回number,score,total均为0
	 */
	@Override
	public JSONArray getAllQuestionType(int examId) {
		// 所有的题型信息
		List<Type> typeList = qdao.getAllQuestionType();
		// 考试中已经存在的题型信息
		JSONArray typeInfo = examDao.getTypeInfo(examId);
		// 存放最后结果
		JSONArray typeJsonArray = new JSONArray();
		for (Type tt : typeList) {
			JSONObject temp = new JSONObject();
			temp.put("typeId", tt.getT_id());
			temp.put("typeName", tt.getT_name());
			temp.put("questionNumber", 0);
			temp.put("questionScore", 0);
			temp.put("questionTotal", 0);
			for (int i = 0; i < typeInfo.size(); i++) {
				// 试卷中已存在该类题型
				if (typeInfo.getJSONObject(i).getInt("typeId") == tt.getT_id()) {
					temp = typeInfo.getJSONObject(i);
				}
			}
			typeJsonArray.add(temp);
		}
		return typeJsonArray;
	}

	@Override
	public JSONObject getAllQuestionJsonByTypeIdAndKey(int currentPage, int pageSize, int typeId, String key) {
		List<Question> questionList = new LinkedList<>();
		int totol = qdao.getQustionByTypeIdAndKey((currentPage - 1) * pageSize, pageSize, typeId, key, questionList);
		JSONObject json = new JSONObject();
		json.put("total", totol);
		JSONArray rows = new JSONArray();
		for (Question qq : questionList) {
			JSONObject tempQuestion = new JSONObject();
			tempQuestion.put("id", qq.getQ_id());
			tempQuestion.put("typeId", qq.getT_id());
			TypeDao tDao = new TypeDaoImpl();
			Type type = tDao.getTypeById(qq.getT_id());
			tempQuestion.put("typeName", type.getT_name());
			tempQuestion.put("content", qq.getQ_content());
			tempQuestion.put("answer", qq.getQ_answer());
			rows.add(tempQuestion);
		}
		json.put("rows", rows);
		return json;
	}
}
