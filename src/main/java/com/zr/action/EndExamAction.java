package com.zr.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zr.service.ScoreOfUserService;
import com.zr.service.impl.ScoreOfUserServiceImpl;

public class EndExamAction extends HttpServlet{

	ScoreOfUserService sous = new ScoreOfUserServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		int u_id = (int) session.getAttribute("u_id");
		int e_id = (int) session.getAttribute("e_id");
		int value = 60;
		sous.updateScore(u_id, e_id, value);
		resp.sendRedirect("myexam.jsp");
	}
}
