package com.zr.action.ems;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zr.service.ExamService;
import com.zr.service.impl.ExamServiceImpl;

import net.sf.json.JSONObject;
/**
 * 预览信息
 * @author Kramer
 *
 */
public class PreviewAction extends HttpServlet {
	ExamService es = new ExamServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		//获取考试id
		int currentExamId = (int) req.getSession().getAttribute("currentExamId");
		req.setAttribute("examId", currentExamId);
		//
		JSONObject exmaInfo = es.getExamInfo(currentExamId);
		req.setAttribute("examInfo", exmaInfo);
		req.getRequestDispatcher("/ems/preview.jsp").forward(req, resp);
	}

}
