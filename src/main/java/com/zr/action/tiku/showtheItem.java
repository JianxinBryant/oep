package com.zr.action.tiku;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zr.dao.tikuimpl.showItemDao;
import com.zr.model.showItem;

import net.sf.json.JSONObject;

public class showtheItem extends HttpServlet{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("utf-8");
		String key = req.getParameter("key");
		String nu = "";
		showItemDao showitem = new showItemDao();
		if(nu == key) {	//首页显示
			List<showItem> list = showitem.showItem();
			JSONObject json = new JSONObject();
			json.put("total", 5);
			json.put("rows", list);
			PrintWriter out=resp.getWriter();
			out.write(json.toString());
			System.out.println(json.toString());
		}else {//模糊查询显示
			showItemDao searchitem = new showItemDao();
			List<showItem> list = searchitem.searchitembycontent("%"+key+"%");
			System.out.println("qq");
			JSONObject json = new JSONObject();
			json.put("total",10);
			json.put("rows", list);
			PrintWriter out = resp.getWriter();
			out.write(json.toString());
		}
	}
}
