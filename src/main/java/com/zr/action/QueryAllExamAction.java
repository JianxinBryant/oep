package com.zr.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zr.service.GetAllExamService;
import com.zr.service.impl.GetAllExamServiceImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QueryAllExamAction extends HttpServlet{
	GetAllExamService gaes = new GetAllExamServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		int page = Integer.parseInt(req.getParameter("start"));
		int size = Integer.parseInt(req.getParameter("size"));
		JSONArray l1 = gaes.getAllExam(page, size);
		int total = gaes.getExamNum();
		int d;
		d = total%size == 0?total/size:total/size+1;
		JSONObject json = new JSONObject();
		json.put("total", d);
		json.put("exams", l1);
		PrintWriter pw = resp.getWriter();
		pw.write(json.toString());
	}
}
