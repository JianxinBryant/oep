package com.zr.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.zr.dao.ExamDao;
import com.zr.dao.TypeDao;
import com.zr.dao.impl.ExamDaoImpl;
import com.zr.dao.impl.TypeDaoImpl;
import com.zr.model.Exam;
import com.zr.model.Exam_question;
import com.zr.model.Question;
import com.zr.model.Type;
import com.zr.service.ExamService;
import com.zr.utils.FormatUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExamServiceImpl implements ExamService {
	ExamDao examDao = new ExamDaoImpl();

	@Override
	public JSONObject getExamByKey(String key, int currentPage, int pageSize) {
		JSONObject json = new JSONObject();
		List<Exam> examList = new LinkedList<>();
		// count是记录总数即json中的total
		int count = examDao.getExamByKey(key, (currentPage - 1) * pageSize, pageSize, examList);
		json.put("total", count);
		JSONArray examsJson = new JSONArray();
		for (Exam ee : examList) {
			// rows数组
			JSONObject exam = new JSONObject();
			exam.put("examId", ee.getE_id());
			exam.put("examName", ee.getE_name());
			exam.put("examStartTime", ee.getE_starttime());
			exam.put("examEndTime", ee.getE_endtime());
			exam.put("examTotal", ee.getE_total());
			String state = ee.getE_state();
			String ss = "";
			if (state.equals("false")) {
				ss = "未发布";
			} else {
				ss = "已发布";
				String stime = ee.getE_starttime().replace("年", "-").replace("月", "-").replace("日", "");
				String etime = ee.getE_endtime().replace("年", "-").replace("月", "-").replace("日", "");
				try {
					Date sdate = FormatUtil.tempDateSecond(stime);
					Date edate = FormatUtil.tempDateSecond(etime);
					Date currentDate = new Date();
					if (currentDate.before(sdate)) {
						ss += ",未开始";
					} else if (currentDate.after(edate)) {
						ss += ",已结束";
					} else {
						ss += ",正在考试";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			exam.put("examState", ss);
			examsJson.add(exam);
		}
		json.put("rows", examsJson);
		return json;
	}

	public static void main(String[] args) {
		ExamService es = new ExamServiceImpl();
		JSONObject obj = es.getExamByKey("", 1, 1);
		System.out.println(obj);
	}

	@Override
	public boolean deleteExamByIds(int[] examIds) {
		return examDao.deleteByIds(examIds);
	}

	@Override
	public int insertExam(String examName, String examStartTime, String examEndTime, int examTotal) {
		Exam exam = new Exam();
		exam.setE_name(examName);
		exam.setE_starttime(examStartTime);
		exam.setE_endtime(examEndTime);
		exam.setE_total(examTotal);
		exam.setE_state("false");
		return examDao.insert(exam);
	}

	@Override
	public JSONObject getExamById(int id) {
		Exam examEntity = examDao.getExamById(id);
		JSONObject examJson = new JSONObject();
		examJson.put("examId", examEntity.getE_id());
		examJson.put("examName", examEntity.getE_name());
		int length = examEntity.getE_endtime().length();
		examJson.put("examTime",
				examEntity.getE_starttime() + " - " + examEntity.getE_endtime().substring(length - 8, length));
		examJson.put("examTotal", examEntity.getE_total());
		examJson.put("examState", examEntity.getE_state());
		return examJson;
	}

	@Override
	public boolean setExamQuestion(int examId, JSONArray insertQuestions, JSONArray updateQuestions) {
		// 新增的考试试题
		List<Exam_question> insertQuestionList = new LinkedList<>();
		// 更新的考试试题
		List<Exam_question> updateQuestionList = new LinkedList<>();
		if (!insertQuestions.isEmpty()) {
			for (int i = 0; i < insertQuestions.size(); i++) {
				Exam_question exam_question = new Exam_question();
				JSONObject obj = insertQuestions.getJSONObject(i);
				exam_question.setE_id(examId);
				exam_question.setQ_id(obj.getInt("qid"));
				exam_question.setScore(obj.getInt("score"));
				insertQuestionList.add(exam_question);
			}
		}
		if (!updateQuestions.isEmpty()) {
			for (int i = 0; i < updateQuestions.size(); i++) {
				Exam_question exam_question = new Exam_question();
				JSONObject obj = updateQuestions.getJSONObject(i);
				exam_question.setE_id(examId);
				exam_question.setQ_id(obj.getInt("qid"));
				exam_question.setScore(obj.getInt("score"));
				updateQuestionList.add(exam_question);
			}
		}
		return examDao.setExamQuestions(insertQuestionList, updateQuestionList);
	}

	/**
	 * 去除两个数组中相同的值并返回一个新的数组
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	private static Integer[] arrContrast(int[] arr1, int[] arr2) {
		List<Integer> list = new LinkedList<Integer>();
		for (Integer str : arr1) { // 处理第一个数组
			if (!list.contains(str)) {
				list.add(str);
			}
		}
		for (Integer str : arr2) { // 如果第二个数组存在和第一个数组相同的值，就删除
			if (list.contains(str)) {
				list.remove(str);
			}
		}
		Integer[] temp = {}; // 创建空数组
		list.toArray(temp); // List to Array
		return list.toArray(temp);
	}

	@Override
	public JSONArray getQuestionOfExam(int examId) {
		List<Question> questionList = examDao.getQuestionOfExam(examId);

		JSONArray resultJson = new JSONArray();
		for (Question question : questionList) {
			JSONObject qJson = new JSONObject();
			qJson.put("id", question.getQ_id());
			qJson.put("typeId", question.getT_id());
			TypeDao tDao = new TypeDaoImpl();
			Type type = tDao.getTypeById(question.getT_id());
			// 题型名
			qJson.put("typeName", type.getT_name());
			qJson.put("content", question.getQ_content());
			qJson.put("answer", question.getQ_answer());
			// 题的分值
			qJson.put("score", examDao.getScore(examId, question.getQ_id()));
			qJson.put("state", "committed");// 已提交过
			resultJson.add(qJson);
		}
		return resultJson;
	}

	@Override
	public boolean removeQuestionOfExamByIds(int examId, int[] ids) {
		return examDao.removeQuestionOfExam(examId, ids);
	}

	@Override
	public boolean updateExam(Exam exam) {
		return examDao.updateExamInfo(exam);
	}

	@Override
	public boolean issueExam(int examId) {
		return examDao.issueExam(examId);
	}

	@Override
	public String getExamTimeService(int e_id) {
		Exam exam = examDao.getExamTime(e_id);
		StringBuffer examtime = new StringBuffer();
		String starttime = exam.getE_starttime().replace("年","/").replace("月", "/").replace("日", "").replace(" ", ",");
		String endtime = exam.getE_endtime().replace("年","/").replace("月", "/").replace("日", "").replace(" ", ",");
		examtime.append(starttime);
		examtime.append(" - ");
		examtime.append(endtime);
		System.out.println(examtime.toString());
		return examtime.toString();
	}
}
