package com.zr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.zr.dao.Exam_questionDao;
import com.zr.model.Exam_question;
import com.zr.utils.JDBCUtil;

public class Exam_questionDaoImpl implements Exam_questionDao{
	
	private int qusetionnum;

	@Override
	public Exam_question queryOneExam_questionByE_id(int e_id, int start, int size) {
		Exam_question exam_question = new Exam_question();
		Connection con = JDBCUtil.getConnection();
		StringBuilder sql = new StringBuilder("select * from exam_question where e_id = ? limit ?,?");
		try {
			PreparedStatement pre = con.prepareStatement(sql.toString());
			pre.setInt(1, e_id);
			pre.setInt(2, start);
			pre.setInt(3, size);
			ResultSet set = pre.executeQuery();
			while(set.next()){
				exam_question.setE_id(set.getInt(1));
				exam_question.setQ_id(set.getInt(2));
				exam_question.setScore(set.getInt(3));
			}
			return exam_question;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return exam_question;
	}

	@Override
	public int getQuestionNumByE_id(int e_id) {
		Connection con = JDBCUtil.getConnection();
		StringBuilder sql = new StringBuilder("select count(exam_question.q_id) qusetionnum from exam_question where e_id = ?");
		try {
			PreparedStatement pst = con.prepareStatement(sql.toString());
			pst.setInt(1, e_id);
			ResultSet set = pst.executeQuery();
			if(set.next()){
				qusetionnum = set.getInt("qusetionnum");
			}
			return qusetionnum;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return qusetionnum;
	}
	
	

}
