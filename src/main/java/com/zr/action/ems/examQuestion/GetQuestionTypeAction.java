package com.zr.action.ems.examQuestion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zr.service.qktemp.QuestionService;
import com.zr.service.qktemp.QuestionServiceImpl;

import net.sf.json.JSONArray;
/**
 * 获取所有题型,
 * 若试卷中已存在某题型的题则返回该类题的数量,分值等;
 * 否则返回数量分值均为0
 * 
 * @author Kramer
 *
 */
public class GetQuestionTypeAction extends HttpServlet{
	QuestionService qs = new QuestionServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		int currentExamId = (int)req.getSession().getAttribute("currentExamId");
		JSONArray typeJson = qs.getAllQuestionType(currentExamId);
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(typeJson.toString());
	}
}
