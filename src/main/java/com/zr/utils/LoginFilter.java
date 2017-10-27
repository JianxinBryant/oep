/*package com.zr.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

*//**
 * 
 * @author zhang
 *
 *//*
@WebFilter(value = "*.jsp", initParams = { 
		@WebInitParam(name = "notCheckURLList", value = "/hwx/Login.jsp;/hwx/Login4Manager.jsp;/llj/mainadmin.jsp;/llj/personinfo.jsp;"
				+ "/llj/score.jsp;"),
		@WebInitParam(name = "redirectURL", value = "/hwx/Login.jsp"), })
public class LoginFilter implements Filter {

	protected FilterConfig filterConfig = null;
	private String redirectURL = null;
	private List<String> notCheckURLList = new ArrayList<String>();
	private String sessionKey = null;

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		HttpSession session = request.getSession();
		sessionKey = (String) session.getAttribute("uname");

		// 只要登录就放行
		if (sessionKey != null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		//过滤直接输入SubDetail.jsp
//		if("/SubDetail.jsp".equals(request.getServletPath()) || "/subDetail.jsp".equals(request.getServletPath())){
//			if(request.getParameter("sid") == null){
//				response.sendRedirect(request.getContextPath() + "/index.jsp");
//				return;
//			}
//		}

		// 没登录又访问了必须要登录才能访问的页面就 return
		if ((!checkRequestURIIntNotFilterList(request)) && session.getAttribute("uname") == null) {
			System.out.println("过滤到非法请求,请求的路径为： "+request.getServletPath());
			response.sendRedirect(request.getContextPath() + redirectURL);
			return;
		}

		filterChain.doFilter(request, response);
	}

	public void destroy() {
		notCheckURLList.clear();
	}

	private boolean checkRequestURIIntNotFilterList(HttpServletRequest request) {
		String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
		return notCheckURLList.contains(uri);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		redirectURL = filterConfig.getInitParameter("redirectURL");

		String notCheckURLListStr = filterConfig.getInitParameter("notCheckURLList");
		if (notCheckURLListStr != null) {
			StringTokenizer st = new StringTokenizer(notCheckURLListStr, ";");
			notCheckURLList.clear();
			while (st.hasMoreTokens()) {
				notCheckURLList.add(st.nextToken());
			}
		}
	}
}*/

