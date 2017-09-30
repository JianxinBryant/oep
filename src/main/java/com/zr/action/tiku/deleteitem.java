package com.zr.action.tiku;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zr.dao.tikuimpl.tikuDao;

public class deleteitem extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("utf-8");
		tikuDao delete = new tikuDao();
		String q_ids = req.getParameter("q_id");
		
		String[] q_id = q_ids.split(",");
		for (int i = 0; i < q_id.length; i++) {
		    String qid = q_id[i];
		    int q =Integer.parseInt(qid);
		   // System.out.println(q);
		    delete.deleteanswerofuseritem(q);
		    delete.deleteexamquestion(q);
		    delete.deleteoptionofquestion(q);
		    delete.deletequestionitem(q);
		   // System.out.println("成功");
		}
		PrintWriter out = resp.getWriter();
		out.write("11");
	}
}
