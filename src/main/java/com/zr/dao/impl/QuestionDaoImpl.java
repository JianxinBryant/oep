package com.zr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zr.dao.QuestionDao;
import com.zr.model.Question;
import com.zr.utils.JDBCUtil;

public class QuestionDaoImpl implements QuestionDao{

	@Override
	public Question queryOneQuestionByE_id(int e_id, int start, int size) {
		Question question = new Question();
		Connection con = JDBCUtil.getConnection();
		StringBuilder sql = new StringBuilder("SELECT question.q_id,t_id,q_content,q_answer FROM exam_question ");
		sql.append("JOIN question ON exam_question.q_id=question.q_id WHERE e_id = ? limit ?,? ");
		try {
			PreparedStatement pst = con.prepareStatement(sql.toString());
			pst.setInt(1, e_id);
			pst.setInt(2, start);
			pst.setInt(3, size);
			ResultSet set = pst.executeQuery();
			while(set.next()){
				question.setQ_id(set.getInt(1));
				question.setT_id(set.getInt(2));
				question.setQ_content(set.getString(3));
				question.setQ_answer(set.getString(4));
			}
			return question;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return question;
	}

}
