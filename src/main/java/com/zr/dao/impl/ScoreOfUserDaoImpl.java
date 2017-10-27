package com.zr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.zr.dao.ScoreOfUserDao;
import com.zr.utils.JDBCUtil;

public class ScoreOfUserDaoImpl implements ScoreOfUserDao{

	@Override
	public void updateScore(int u_id, int e_id, int score) {
		StringBuffer sql = new StringBuffer("UPDATE scoreofuser SET scoreofuser.score = ? WHERE u_id=? AND e_id=?");
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql.toString());
			pst.setInt(1, score);
			pst.setInt(2, u_id);
			pst.setInt(3, e_id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
