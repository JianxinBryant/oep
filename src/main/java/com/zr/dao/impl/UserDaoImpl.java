package com.zr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zr.dao.UserDao;
import com.zr.model.User;
import com.zr.utils.JDBCUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserDaoImpl implements UserDao{
	/**
	 * 用户查询
	 */
	@Override
	public int SelectUserInfoByUserkey(int start, int pageSize, String userkey, List<User> userlist) {
		int count = 0;//查询的总行数
		userkey = '%'+userkey+'%';
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT COUNT(u_id)  ");
		sql.append("FROM `user` ");
		sql.append("WHERE   `user`.u_name LIKE ? OR `user`.u_telephone LIKE ? OR `user`.u_email LIKE ? OR `user`.u_registertime LIKE ? ");
		Connection con = JDBCUtil.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement(sql.toString());
			pst.setString(1, userkey);
			pst.setString(2, userkey);
			pst.setString(3, userkey);
			pst.setString(4, userkey);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()){
				 count = rs.getInt("COUNT(u_id)");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuffer sql1 = new StringBuffer("");
		sql1.append("select `user`.u_id,`user`.u_name,`user`.u_telephone,`user`.u_email,`user`.u_registertime  ");
		sql1.append("FROM `user` ");
		sql1.append("WHERE   `user`.u_name LIKE ? OR `user`.u_telephone LIKE ? OR `user`.u_email LIKE ? OR `user`.u_registertime LIKE ? ");
		sql1.append("LIMIT ?, ?");
		try {
			PreparedStatement pst1 = con.prepareStatement(sql1.toString());
			pst1.setString(1, userkey);
			pst1.setString(2, userkey);
			pst1.setString(3, userkey);
			pst1.setString(4, userkey);
			pst1.setInt(5, start);
			pst1.setInt(6, pageSize);
			ResultSet rs1 = pst1.executeQuery();
			while(rs1.next()){
				User user = new User();
				user.setU_id(rs1.getInt("u_id"));
				user.setU_name(rs1.getString("u_name"));
				user.setU_telephone(rs1.getString("u_telephone"));
				user.setU_email(rs1.getString("u_email"));
				user.setU_registertime(rs1.getString("u_registertime"));
				userlist.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 删除用户
	 */
	public int RemoveUserByUid(List ids) {
		int j = 0;
		List ls = new ArrayList();
		//先删除用户的外键关联信息
		StringBuffer sql = new StringBuffer("");
		sql.append("DELETE from scoreofuser WHERE u_id = ?");
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			 ps = con.prepareStatement(sql.toString());
			for (Object i : ids) {
				String i1= (String) i;
				int i2 = Integer.parseInt(i1);
				ps.setInt(1, i2);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.closeJDBC(ps, con);
		}
		
		//删除用户信息
		StringBuffer sql1 = new StringBuffer("");
		sql1.append("DELETE from `user` WHERE u_id = ? ");
		Connection con1 = JDBCUtil.getConnection();
		try {
			 ps = con1.prepareStatement(sql1.toString());
			for (Object i : ids) {
				String i1= (String) i;
				int i2 = Integer.parseInt(i1);
				ps.setInt(1, i2);
				j = ps.executeUpdate();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.closeJDBC(ps, con);
		}
		return j;
	}
	
	/**
	 * 编辑用户
	 */
	public int editUser(User user){
		int i=0 ;
		StringBuffer sql = new StringBuffer("");
		sql.append("UPDATE `user`");
		sql.append("SET u_name = ? ,u_telephone=? , u_email = ? ,  u_registertime = ? ");
		sql.append("WHERE u_id =? ");
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			 ps = con.prepareStatement(sql.toString());
				ps.setString(1, user.getU_name());
				ps.setString(2, user.getU_telephone());
				ps.setString(3, user.getU_email());
				ps.setString(4, user.getU_registertime());
				ps.setInt(5, user.getU_id());
				i = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.closeJDBC(ps, con);
		}
		return i;
	}

	@Override
	public JSONArray getUserofscoreAndExam(int u_id) {
		JSONArray ja = new JSONArray();
		Connection con = JDBCUtil.getConnection();
		StringBuffer sql = new StringBuffer("SELECT scoreofuser.e_id,u_id,score,e_name,e_starttime,e_endtime,e_total FROM scoreofuser JOIN exam ON scoreofuser.e_id = exam.e_id WHERE u_id = ?");
		PreparedStatement pst = null;
		try {
			 pst = con.prepareStatement(sql.toString());
			pst.setInt(1, u_id);
			ResultSet set = pst.executeQuery();
			while(set.next()){
				JSONObject jo = new JSONObject();
				jo.put("e_id", set.getInt(1));
				jo.put("u_id", set.getInt(2));
				jo.put("score", set.getInt(3));
				jo.put("e_name", set.getString(4));
				StringBuffer examtime = new StringBuffer();
				String starttime = set.getString(5).replace("年","/").replace("月", "/").replace("日", "").replace(" ", ",");
				String endtime = set.getString(6).replace("年","/").replace("月", "/").replace("日", "").replace(" ", ",");
				examtime.append(starttime);
				examtime.append(" - ");
				examtime.append(endtime);
				jo.put("examtime", examtime.toString());
				jo.put("e_total", set.getInt(7));
				ja.add(jo);
				System.out.println(ja.toString());
			}
			System.out.println(ja.toString());
			return ja;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.closeJDBC(pst, con);
		}
		return ja;
	}
	
			
}
