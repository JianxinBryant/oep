package com.zr.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zr.service.AnswerOfUserService;
import com.zr.service.TypeService;
import com.zr.service.impl.AnswerOfUserServiceImpl;
import com.zr.service.impl.TypeServiceImpl;

public class InsertAnswerActionF extends HttpServlet{

	TypeService ts = new TypeServiceImpl();
	AnswerOfUserService aous = new AnswerOfUserServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf8");
		//session
		HttpSession session = req.getSession();
		int u_id = (int) session.getAttribute("u_id");
		int e_id = (int) session.getAttribute("e_id");
		int q_id = Integer.parseInt(req.getParameter("q_id"));
		int t_id = Integer.parseInt(req.getParameter("t_id"));
		if(aous.checkAnswer(u_id, e_id, q_id)){
			aous.delectAnswer(u_id, e_id, q_id);
		}
		if(ts.queryTypeByT_id(t_id).getT_name().equals("简答题")){
			System.out.println("ri2= " + req.getParameter("textareaz"));
			String answer = req.getParameter("textareaz");
			aous.commitAnswer(u_id, e_id, q_id, answer);
		}else if(ts.queryTypeByT_id(t_id).getT_name().equals("选择题")||ts.queryTypeByT_id(t_id).getT_name().equals("多选题")){
			StringBuilder sb = new StringBuilder("");
			String[] options = req.getParameterValues("option");
			System.out.println(options[0]);
			for(int i = 0; i < options.length; i++){
				sb.append("," + options[i]);
			}
			String answer = sb.deleteCharAt(0).toString();
			aous.commitAnswer(u_id, e_id, q_id, answer);
		}
	}
	
}
