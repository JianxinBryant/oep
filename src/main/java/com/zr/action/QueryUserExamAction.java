package com.zr.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zr.service.UserService;
import com.zr.service.impl.UserServiceImpl;

import net.sf.json.JSONArray;

public class QueryUserExamAction extends HttpServlet{

	UserService us = new UserServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setCharacterEncoding("utf8");
		//session
		HttpSession session = req.getSession();
		System.out.println("u_id: " + (int) session.getAttribute("u_id"));
		int u_id = (int) session.getAttribute("u_id");
		
		JSONArray ja = us.getUserofscoreAndExam(u_id);
		PrintWriter pw = resp.getWriter();
		pw.write(ja.toString());
	}
}