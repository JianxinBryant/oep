package com.zr.action.tiku;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.Request;

import com.zr.dao.tikuimpl.showItemDao;
import com.zr.dao.tikuimpl.tikuDao;
import com.zr.service.tikuimpl.tikuService;


public class AddItem extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String request = null;
	tikuService tiku = new tikuService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("utf-8");
		String addquestiondescribe = req.getParameter("addquestiondescribe");
		String addselectA = req.getParameter("addselectA");
		String addselectB = req.getParameter("addselectB");
		String addselectC = req.getParameter("addselectC");
		String addselectD = req.getParameter("addselectD");
		String doubleA = req.getParameter("doubleA");
		String doubleB = req.getParameter("doubleB");
		String doubleC = req.getParameter("doubleC");
		String doubleD = req.getParameter("doubleD");
		String addrightselect = req.getParameter("addrightselect");
		String addsubmitselect = req.getParameter("addsubmitselect");//单选题的提交
		String daticontent = req.getParameter("daticontent");//大题内容
		String datianswer = req.getParameter("datianswer");//大题答案
		String addsubmitdati = req.getParameter("addsubmitdati");//大题的提交
		tikuDao adddao = new tikuDao();
		int maxid = adddao.getmaxqid();
		showItemDao showitemdao = new showItemDao();
		
		
		if(addselectA!=""&&addselectB!=""&&addselectC!=""&&addselectD!=""&&addquestiondescribe!=""&&addrightselect!="") {
			if(maxid==0) {
				if("确认单选添加".equals(addsubmitselect)) {
					//添加进question的选择题部分表
					//添加进入optionofquestion表
						adddao.addItemcontent_i_danswer(1,addquestiondescribe, 1, addrightselect);
						adddao.addItemoption(maxid+1, addselectA, "A");
						adddao.addItemoption(maxid+1, addselectB, "B");
						adddao.addItemoption(maxid+1, addselectC, "C");
						adddao.addItemoption(maxid+1, addselectD, "D");
						resp.sendRedirect("/oep/zy/item.jsp");
				}else if("确认大题添加".equals(addsubmitdati)) {
					//添加进入question的大题部分表	
					adddao.adddatiItem(1,daticontent, datianswer);	
					resp.sendRedirect("/oep/zy/item.jsp");
				}
			}else if("确认单选添加".equals(addsubmitselect)) {
					adddao.addItemcontent_i_danswer(maxid+1,addquestiondescribe, 1, addrightselect);//添加进question的选择题部分表
					//添加进入optionofquestion表
						adddao.addItemoption(maxid+1, addselectA, "A");
						adddao.addItemoption(maxid+1, addselectB, "B");
						adddao.addItemoption(maxid+1, addselectC, "C");
						adddao.addItemoption(maxid+1, addselectD, "D");
						resp.sendRedirect("/oep/zy/item.jsp");
			  	} else if("确认大题添加".equals(addsubmitdati)) {
			  		//添加进入question的大题部分表	
					adddao.adddatiItem(maxid+1,daticontent, datianswer);	
					resp.sendRedirect("/oep/zy/item.jsp");
			  	}
		}else {
			resp.sendRedirect("/oep/zy/item.jsp");
		}
		}
}
