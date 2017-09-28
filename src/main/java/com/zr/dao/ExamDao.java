package com.zr.dao;

import java.util.List;

import com.zr.model.Exam;
import com.zr.model.Exam_question;
import com.zr.model.Question;

import net.sf.json.JSONArray;

public interface ExamDao {
	/**
	 * 根据关键词实现对exam的分页查找
	 * @param key		关键词
	 * @param start		起始位置
	 * @param pageSize	该页记录行数
	 * @param examList	存exam的链表
	 * @return
	 */
	int getExamByKey(String key, int start, int pageSize,List<Exam> examList);

	/**
	 * 批量删除考试
	 * @param examIds	考试id数组
	 * @return			删除成功返回true
	 */
	boolean deleteByIds(int[] examIds);

	/**
	 * 新增一门考试记录
	 * @param exam	考试实体:主键自增,状态默认为false
	 * @return		插入成功返回考试id,否则返回-1
	 */
	int insert(Exam exam);

	/**
	 * 通过id获取考试实体
	 * @param id
	 * @return
	 */
	Exam getExamById(int id);

	/**
	 * 获取已存在的考题id
	 * @param examId	考试id
	 * @return	
	 */
	int[] getExistQuestionId(int examId);

	/**
	 * 批量添加考题
	 * @param eqList	
	 * @return	插入成功则返回true
	 */
	boolean insertExamQuestions(List<Exam_question> eqList);

	/**
	 * 通过考试id获取该考试全部的题目信息
	 * @param examId
	 * @return 
	 */
	List<Question> getQuestionOfExam(int examId);

	/**
	 * 删除考题通过考试id和题id[]
	 * @param examId
	 * @param ids
	 * @return
	 */
	boolean removeQuestionOfExam(int examId, int[] ids);

	/**
	 * 获取考试某题的分值
	 * @param currentExamId
	 * @param q_id
	 * @return 某考试某题的分值
	 */
	int getScore(int examId, int q_id);
	/**
	 * 通过考试id,获取题型信息
	 * @examId 考试id
	 * @return
	 */
	JSONArray getTypeInfo(int examId);

	/**
	 * 批量更新试题
	 * @param updateQuestionList 待更新试题
	 * @return
	 */
	boolean updateExamQuestions(List<Exam_question> updateQuestionList);

	/**
	 * 设置考题:包括更新和新增
	 * @param insertQuestionList
	 * @param updateQuestionList
	 * @return	都成功返回true,否则返回false
	 */
	boolean setExamQuestions(List<Exam_question> insertQuestionList, List<Exam_question> updateQuestionList);

	/**
	 * 更新考试基本信息
	 * @param exam
	 * @return
	 */
	boolean updateExamInfo(Exam exam);

	/**
	 * 发布考试
	 * @param examId
	 * @return
	 */
	boolean issueExam(int examId);
	
	/**
	 * 得到考试时间
	 * @param e_id
	 * @return
	 */
	Exam getExamTime(int e_id);
	
}
