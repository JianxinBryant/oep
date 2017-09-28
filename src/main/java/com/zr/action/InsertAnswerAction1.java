package com.zr.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zr.service.AnswerOfUserService;
import com.zr.service.impl.AnswerOfUserServiceImpl;

public class InsertAnswerAction1 extends HttpServlet{

	AnswerOfUserService aous = new AnswerOfUserServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//session
		int u_id = 1;
		int e_id = 1;
		int q_id = Integer.parseInt(req.getParameter("q_id"));
		if(aous.checkAnswer(u_id, e_id, q_id)){
			aous.delectAnswer(u_id, e_id, q_id);
		}
		System.out.println("ri2= " + req.getParameter("textareaz"));
		String answer = req.getParameter("textareaz");
		
		aous.commitAnswer(u_id, e_id, q_id, answer);
	}
	
}
