package com.zr.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zr.model.Answerofuser;
import com.zr.model.Exam_question;
import com.zr.model.Optionofquestion;
import com.zr.model.Question;
import com.zr.service.AnswerOfUserService;
import com.zr.service.Exam_questionService;
import com.zr.service.OptionofquestionService;
import com.zr.service.QuestionService;
import com.zr.service.impl.AnswerOfUserServiceImpl;
import com.zr.service.impl.Exam_questionServiceImpl;
import com.zr.service.impl.OptionofquestionServiceImpl;
import com.zr.service.impl.QuestionServiceImpl;

import net.sf.json.JSONObject;

public class QueryQuestionAction extends HttpServlet{

	AnswerOfUserService aous = new AnswerOfUserServiceImpl();
	Exam_questionService eqs = new Exam_questionServiceImpl();
	QuestionService qs = new QuestionServiceImpl();
	OptionofquestionService ooqs = new OptionofquestionServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("执行query方法！");
		req.setCharacterEncoding("utf8");
		resp.setCharacterEncoding("utf8");
		//从session里得到考试id
		int e_id = 1;
		int u_id = 1;
//		int page = 1;
		System.out.println("qqqqid: " + req.getParameter("q_id"));
		int page = Integer.parseInt(req.getParameter("page"));
		int pageSize = 1;
		
		Exam_question exam_question = eqs.queryOneExam_questionByE_id(e_id, page, pageSize);
		Question question = qs.queryOneQuestionByE_id(e_id, page, pageSize);
		List<Optionofquestion> options = ooqs.queryOptionofquestionsByQ_id(question.getQ_id());
		int questionnum = eqs.getQuestionNumByE_id(e_id);
		Answerofuser answerofuser = aous.selectAnswerofuser(u_id, e_id, page);
		JSONObject json = new JSONObject();
		System.out.println("下一页答案：" + answerofuser.getAnswer());
		json.put("exam_question", exam_question);
		json.put("question", question);
		json.put("options", options);
		json.put("questionnum", questionnum);
		json.put("page", page);
		json.put("answerofuser", answerofuser);
		PrintWriter pw = resp.getWriter();
		pw.write(json.toString());
	}
}
