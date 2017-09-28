package com.zr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zr.dao.AddUserExamDao;
import com.zr.utils.JDBCUtil;

public class AddUserExamDaoImpl implements AddUserExamDao {

	@Override
	public boolean isApply(int eid, int uid) {
		boolean a = true;
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT * ");
		sql.append("FROM scoreofuser ");
		sql.append("WHERE e_id=? and u_id=? ");
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql.toString());
			pst.setInt(1, eid);
			pst.setInt(2, uid);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
					a = false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.closeJDBC(pst, con);
		}
		//System.out.println("dao1:"+a);
		return a;
	}

	@Override
	public void addUserExam(int eid, int uid) {
		StringBuffer sql = new StringBuffer("");
		sql.append("INSERT INTO scoreofuser ");
		sql.append("(e_id,u_id) ");
		sql.append("VALUES(?,?) ");
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql.toString());
			pst.setInt(1, eid);
			pst.setInt(2, uid);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.closeJDBC(pst, con);
		}
	}

}
