package com.zr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zr.dao.AnswerOfUserDao;
import com.zr.model.Answerofuser;
import com.zr.utils.JDBCUtil;

public class AnswerOfUserDaoImpl implements AnswerOfUserDao{

	@Override
	public void commitAnswer(int u_id, int e_id, int q_id, String answer) {
		
		Connection con = JDBCUtil.getConnection();
		StringBuilder sql = new StringBuilder("INSERT INTO answerofuser VALUES(?,?,?,?)");
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql.toString());
			pst.setInt(1, u_id);
			pst.setInt(2, e_id);
			pst.setInt(3, q_id);
			pst.setString(4, answer);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.closeJDBC(pst, con);
		}
	}

	@Override
	public boolean checkAnswer(int u_id, int e_id, int q_id) {
		Connection con = JDBCUtil.getConnection();
		StringBuilder sql = new StringBuilder("select * from answerofuser where u_id=? AND e_id=? AND q_id=?");
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql.toString());
			pst.setInt(1, u_id);
			pst.setInt(2, e_id);
			pst.setInt(3, q_id);
			ResultSet set = pst.executeQuery();
			if(set.next()){
				return true;
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.closeJDBC(pst, con);
		}
		return false;
	}

	@Override
	public void deleteAnswer(int u_id, int e_id, int q_id) {
		
		Connection con = JDBCUtil.getConnection();
		StringBuilder sql = new StringBuilder("DELETE FROM answerofuser WHERE u_id=? AND e_id=? AND q_id=?");
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql.toString());
			pst.setInt(1, u_id);
			pst.setInt(2, e_id);
			pst.setInt(3, q_id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.closeJDBC(pst, con);
		}
	}

	@Override
	public Answerofuser selectAnswerofuser(int u_id, int e_id, int q_id) {
		Answerofuser answerofuser = new Answerofuser();
		Connection con = JDBCUtil.getConnection();
		StringBuilder sql = new StringBuilder("select * from answerofuser WHERE u_id=? AND e_id=? AND q_id=?");
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql.toString());
			pst.setInt(1, u_id);
			pst.setInt(2, e_id);
			pst.setInt(3, q_id);
			ResultSet set = pst.executeQuery();
			if(set.next()){
				answerofuser.setU_id(set.getInt(1));
				answerofuser.setE_id(set.getInt(2));
				answerofuser.setQ_id(set.getInt(3));
				answerofuser.setAnswer(set.getString(4));
			}
			return answerofuser;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.closeJDBC(pst, con);
		}
		return answerofuser;
	}

}
