package com.zr.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zr.model.Exam;
import com.zr.service.ExamService;
import com.zr.service.impl.ExamServiceImpl;

public class JoinWaitExamAction extends HttpServlet{
	
	ExamService es = new ExamServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf8");
		int e_id = Integer.parseInt(req.getParameter("e_id"));
		HttpSession session = req.getSession();
		session.setAttribute("e_id", e_id);
		Exam exam = es.getExamByE_id(e_id);
		StringBuffer examtime = new StringBuffer();
		String starttime = exam.getE_starttime().replace("年","/").replace("月", "/").replace("日", "").replace(" ", ",");
		String endtime = exam.getE_endtime().replace("年","/").replace("月", "/").replace("日", "").replace(" ", ",");
		examtime.append(starttime);
		examtime.append(" - ");
		examtime.append(endtime);
		req.setAttribute("examtime", examtime.toString());
		req.setAttribute("e_name", exam.getE_name());
		req.getRequestDispatcher("waitexam.jsp").forward(req, resp);
	}
	
	
}