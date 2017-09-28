package com.zr.action.ems;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zr.model.Exam;
import com.zr.service.ExamService;
import com.zr.service.impl.ExamServiceImpl;

import net.sf.json.JSONObject;
/**
 * 更新考试基本信息
 * @author Kramer
 *
 */
public class UpdateAction extends HttpServlet{
	ExamService es = new ExamServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		int examId = Integer.parseInt(req.getParameter("examId"));
		String examName = req.getParameter("examName");
		String startTime = req.getParameter("startTime");
		String endTime = req.getParameter("endTime");
		int examTotal = Integer.parseInt(req.getParameter("examTotal"));
		
		Exam exam = new Exam();
		exam.setE_id(examId);
		exam.setE_name(examName);
		exam.setE_starttime(startTime);
		exam.setE_endtime(endTime);
		exam.setE_total(examTotal);
		
		boolean result = es.updateExam(exam);
		JSONObject json = new JSONObject();
		json.put("result", result);
		if(result){
			//将考试信息存到session
			req.getSession().setAttribute("currentExamId", examId);
			json.put("msg", "更新考试成功！");
			json.put("url", req.getContextPath()+"/ems/setQuestion.jsp");
		}else{
			json.put("msg", "新增考试失败！");
		}
		resp.getWriter().write(json.toString());
	}

}
