package com.zr.dao;

public interface AddUserExamDao {
	/**
	 * 判断用户是否已经报名
	 * @param eid
	 * @param uid
	 * @return
	 */
	public boolean isApply(int eid,int uid);
	
	/**
	 * 报名考试
	 * @param eid
	 * @param uid
	 */
	public void addUserExam(int eid,int uid);
}
