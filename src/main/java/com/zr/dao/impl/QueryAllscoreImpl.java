package com.zr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zr.dao.QueryAllscore;
import com.zr.model.Exam;
import com.zr.utils.JDBCUtil;

public class QueryAllscoreImpl implements QueryAllscore {

	@Override
	public List<Exam> getAllExams(int page, int pageSize) {
		List<Exam> exams = new ArrayList<Exam>();
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT e_name,e_starttime,e_endtime,e_total ");
		sql.append("FROM exam ");
		sql.append("where e_state='true' limit ?,? ");
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql.toString());
			pst.setInt(1, page);
			pst.setInt(2, pageSize);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Exam exam = new Exam();
				exam.setE_name(rs.getString("e_name"));
				exam.setE_starttime(rs.getString("e_starttime"));
				exam.setE_endtime(rs.getString("e_endtime"));
				exam.setE_total(rs.getInt("e_total"));
				exams.add(exam);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.closeJDBC(pst, con);
		}

		return exams;
	}

	@Override
	public int getExamNum() {
		int a = 0;
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT COUNT(e_id) a ");
		sql.append("FROM exam ");
		sql.append("WHERE e_state='true' ");
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				a = rs.getInt("a");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.closeJDBC(pst, con);
		}
		return a;
	}

}
