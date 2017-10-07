package com.zr.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		HttpSession session = req.getSession();
		int u_id = (int) session.getAttribute("u_id");
		int e_id = (int) session.getAttribute("e_id");
		int qq_id = Integer.parseInt(req.getParameter("qq_id"));
		aous.delectAnswer(u_id, e_id, qq_id);
		PrintWriter pw = resp.getWriter();
		pw.write(1);
	}
}
