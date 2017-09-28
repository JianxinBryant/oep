package com.zr.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zr.service.impl.AddUserExamServiceImpl;

import net.sf.json.JSONObject;

public class AddUserExamAction extends HttpServlet{
	AddUserExamServiceImpl as = new AddUserExamServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String exam = req.getParameter("exam");
		String user = req.getParameter("uname");
		//System.out.println(exam);
		//System.out.println(user);
		boolean a = as.isApply(exam, user);
		//System.out.println(a);
		if(a){
			as.addUserExam(exam, user);
			JSONObject json = new JSONObject();
			json.put("flag", true);
			PrintWriter pw = resp.getWriter();
			pw.write(json.toString());
		}else{
			JSONObject json = new JSONObject();
			json.put("flag", false);
			PrintWriter pw = resp.getWriter();
			pw.write(json.toString());
		}
	}
}
