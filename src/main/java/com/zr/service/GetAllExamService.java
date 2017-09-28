package com.zr.service;

import net.sf.json.JSONArray;

public interface GetAllExamService {
	/**
	 * 查询所有考试信息
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public JSONArray getAllExam(int page,int pageSize);
	
	/**
	 * 查询考试总数
	 * @return
	 */
	public int getExamNum();
}
