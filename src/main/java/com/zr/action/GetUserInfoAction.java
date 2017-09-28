package com.zr.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

import com.zr.model.User;
import com.zr.service.UserService_leiwei;
import com.zr.service.impl.UserServiceImpl_leiwei;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * leiwei  
 * 根据关键字查询用户信息 
 * @author Administrator
 *
 */
public class GetUserInfoAction extends HttpServlet{
	UserService_leiwei gs = new UserServiceImpl_leiwei();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置编码
		req.setCharacterEncoding("utf-8");
		//获取参数
		String userkey = req.getParameter("userkey");//搜索关键词
		String boxvalue = req.getParameter("boxvalue");//下拉列表中被选中的值
		int page = Integer.valueOf(req.getParameter("page"));//当前页码
		int pageSize = Integer.valueOf(req.getParameter("rows"));//每页行数
		JSONObject json = gs.getUserInfo(boxvalue,page, pageSize, userkey);
		
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(json.toString());
	}

}
