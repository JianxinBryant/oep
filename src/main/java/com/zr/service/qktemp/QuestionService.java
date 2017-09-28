package com.zr.service.qktemp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface QuestionService {
	/**
	 * 获取所有的题型
	 * @return
	 */
	JSONArray getAllQuestionType(int examId);

	/**
	 * 
	 * 通过题型获取所有该类题
	 * 
	 * @param currentPage	页码
	 * @param pageSize		每页数量
	 * @param typeId 	题型id
	 * @param key		关键字
	 * @return	该类题json
	 */
	JSONObject getAllQuestionJsonByTypeIdAndKey(int currentPage,int pageSize,int typeId,String key);
	
}
