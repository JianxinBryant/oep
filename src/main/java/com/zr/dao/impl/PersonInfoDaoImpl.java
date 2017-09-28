package com.zr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zr.dao.PersonInfoDao;
import com.zr.model.User;
import com.zr.utils.JDBCUtil;

public class PersonInfoDaoImpl implements PersonInfoDao {

	@Override
	public User getUserInfo(String uname) {
		User user = new User();
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT u_name,u_email,u_telephone,u_registertime ");
		sql.append("FROM user ");
		sql.append("WHERE u_name=?");
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql.toString());
			pst.setString(1, uname);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				user.setU_name(uname);
				user.setU_email(rs.getString("u_email"));
				user.setU_telephone(rs.getString("u_telephone"));
				user.setU_registertime(rs.getString("u_registertime"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.closeJDBC(pst, con);
		}
		return user;
	}
}
