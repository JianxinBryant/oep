package com.zr.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zr.model.User;
import com.zr.service.PersonInfoService;
import com.zr.service.impl.PersonInfoServiceImpl;

import net.sf.json.JSONObject;

public class PersonInfoAction extends HttpServlet{
	PersonInfoService ps = new PersonInfoServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		//System.out.println(req.getParameter("sname"));
		String sname = req.getParameter("sname");
		User user = ps.getUserInfo(sname);
		JSONObject json = new JSONObject();
		json.put("uname", user.getU_name());
		json.put("uemail", user.getU_email());
		json.put("utelephone",user.getU_telephone());
		json.put("uregistertime", user.getU_registertime());
		//System.out.println(json.toString());
		PrintWriter pw = resp.getWriter();
		pw.write(json.toString());
	}
}
