package com.zr.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zr.service.JedisLoginService;
import com.zr.service.impl.JedisLoginServiceImpl;

public class CheckUserAction extends HttpServlet{

	JedisLoginService jls = new JedisLoginServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		ServletContext application = this.getServletContext();
		PrintWriter pw = resp.getWriter();
		String userName = (String) session.getAttribute("u_name");
		String sessionId = (String) session.getAttribute("sessionId");
		String jedissessionId = jls.getSessionIdByUsername(userName);
		System.out.println(sessionId);
//		System.out.println(application.getAttribute(userName));
//		if(!application.getAttribute(userName).equals(sessionId)){
//			pw.write("1");
//		}
//		else
//			pw.write("0");
		
		if(!jedissessionId.equals(sessionId)){
			pw.write("1");
		}
		else
			pw.write("0");
	}
}
