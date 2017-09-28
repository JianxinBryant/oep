package com.zr.dao;

import java.util.List;

import com.zr.model.Exam;

public interface QueryAllscore {
	/**
	 * 查询所有考试信息
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<Exam> getAllExams(int page, int pageSize);
	
	/**
	 * 查询考试总数
	 * @return
	 */
	public int getExamNum();
}
