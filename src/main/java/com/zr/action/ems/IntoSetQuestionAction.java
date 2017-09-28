package com.zr.action.ems;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class IntoSetQuestionAction extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String examIdString = req.getParameter("currentExamId");
		JSONObject obj = new JSONObject();
		if(examIdString.equals("")){
			System.out.println("11  "+examIdString);
			obj.put("result", false);
		}else{
			int currentExamId = Integer.valueOf(examIdString);
			req.getSession().setAttribute("currentExamId", currentExamId);
			obj.put("result", true);
			obj.put("url", req.getContextPath()+"/ems/setQuestion.jsp");
		}
		resp.getWriter().write(obj.toString());
	}
	

}
