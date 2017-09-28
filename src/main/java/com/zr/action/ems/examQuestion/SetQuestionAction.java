package com.zr.action.ems.examQuestion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zr.service.ExamService;
import com.zr.service.impl.ExamServiceImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 设置考题
 * 获取考试id,考题id数组
 * @author Kramer
 *
 */
public class SetQuestionAction extends HttpServlet{
	ExamService es = new ExamServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		//获取正在配置的考试id
		/**
		 * 方便调试,记得要改！！！！！
		 * int currentExamId = (int)req.getSession().getAttribute("currentExamId");
		 */
		int currentExamId = (int)req.getSession().getAttribute("currentExamId");
		//int currentExamId = 1;
		String insertQuestionString = req.getParameter("insertQuestion");
		String updateQuestionString = req.getParameter("updateQuestion");
		//字符串转JSON数组
		JSONArray insertQuestionJson = JSONArray.fromObject(insertQuestionString);
		JSONArray updateQuestionJson = JSONArray.fromObject(updateQuestionString);
		//设置考题
		boolean result = es.setExamQuestion(currentExamId, insertQuestionJson, updateQuestionJson);
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		resp.getWriter().write(obj.toString());
	
	}
}
