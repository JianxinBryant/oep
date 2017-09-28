package com.zr.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zr.service.AnswerOfUserService;
import com.zr.service.impl.AnswerOfUserServiceImpl;

public class DeleteAnswerAction extends HttpServlet{

	AnswerOfUserService aous = new AnswerOfUserServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf8");
		//session
		System.out.println("执行delete方法！");
		int u_id = 1;
		int e_id = 1;
		int q_id = Integer.parseInt(req.getParameter("q_id"));
		aous.delectAnswer(u_id, e_id, q_id);
		PrintWriter pw = resp.getWriter();
		pw.write(1);
	}
}
