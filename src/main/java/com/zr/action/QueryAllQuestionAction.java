package com.zr.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zr.service.QuestionService;
import com.zr.service.impl.QuestionServiceImpl;

import net.sf.json.JSONArray;

public class QueryAllQuestionAction extends HttpServlet{
	
	QuestionService qs = new QuestionServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int e_id = (int) req.getSession().getAttribute("e_id");
//		int e_id = 1;
		JSONArray ja = qs.quertAllQuestionsByE_id(e_id);
		System.out.println("page与q_id的JSONArray: " + ja);
		PrintWriter pw = resp.getWriter();
		pw.write(ja.toString());
	}
}
