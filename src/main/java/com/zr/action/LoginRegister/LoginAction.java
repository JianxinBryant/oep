package com.zr.action.LoginRegister;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zr.dao.UserDao_hwx;
import com.zr.dao.impl.UserDaoImpl_hwx;
import com.zr.pojo.UserInfo;
import com.zr.service.JedisLoginService;
import com.zr.service.UserService;
import com.zr.service.impl.JedisLoginServiceImpl;
import com.zr.service.impl.UserServiceImpl;

/**
*@author VerSion
*@time 2017年10月26日20:46:13
*/
@SuppressWarnings("serial")
public class LoginAction extends HttpServlet{
	Map<String, String> map = new HashMap<String, String>();
	UserDao_hwx login = new UserDaoImpl_hwx();
	UserService us = new UserServiceImpl();
	JedisLoginService jls = new JedisLoginServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		ServletContext application = this.getServletContext();
		session.setMaxInactiveInterval(3000*60);
		String userName = req.getParameter("uname");
		String keyINpsw = req.getParameter("password");
		String identifyingcode = req.getParameter("idtfcode");
		//得到用户主机地址
//		String userHost = req.getRemoteHost();
		//得到用户本地cookie里的sessionId
		String sessionId = req.getSession().getId();
		jls.insertUsernameAndSessionId(userName, sessionId);
//		application.setAttribute(userName, sessionId);
		
		session.setAttribute("sessionId", sessionId);
//		UserInfo userInfo = new UserInfo();
//		userInfo.setAddress(userHost);
//		userInfo.setSessid(sessionId);
//		userInfo.setUsername(userName);
//		session.setAttribute("userInfo", userInfo);
//		
//		System.out.println("userHost:"+userHost);
//		System.out.println("sessionId:"+sessionId);
		int u_id = us.getU_idByUname(userName);
		session.setAttribute("u_id", u_id);
		session.setAttribute("u_name", userName);
		
		String upsw = login.Getpswbyuname(userName);
		if ((keyINpsw.equals(upsw))&&(session.getAttribute("codevalue").equals(identifyingcode))) {
			resp.setCharacterEncoding("utf8");
			PrintWriter out = resp.getWriter();
			out.print("314");
		}else if(!(keyINpsw.equals(upsw))){
			resp.setCharacterEncoding("utf8");
			PrintWriter out = resp.getWriter();
			out.print("365");
		}else if ("".equals(identifyingcode)) {
			resp.setCharacterEncoding("utf8");
			PrintWriter out = resp.getWriter();
			out.print("391");
		}else if (!(session.getAttribute("codevalue").equals(identifyingcode))) {
			resp.setCharacterEncoding("utf8");
			PrintWriter out = resp.getWriter();
			out.print("392");
		}
	}
	
	
}
