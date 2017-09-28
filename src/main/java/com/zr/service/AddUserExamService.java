package com.zr.service;

public interface AddUserExamService {
	/**
	 * 报名考试
	 * @param ename
	 * @param uname
	 */
	public void addUserExam(String ename,String uname);
	
	/**
	 * 是否报名
	 * @param ename
	 * @param uname
	 * @return
	 */
	public boolean isApply(String ename,String uname);
}
