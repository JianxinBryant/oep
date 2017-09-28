package com.zr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zr.dao.OptionofquestionDao;
import com.zr.model.Optionofquestion;
import com.zr.utils.JDBCUtil;

public class OptionofquestionDaoImpl implements OptionofquestionDao{

	@Override
	public List<Optionofquestion> queryOptionofquestionsByQ_id(int q_id) {
		List<Optionofquestion> list = new ArrayList<Optionofquestion>();
		Connection con = JDBCUtil.getConnection();
		StringBuilder sql = new StringBuilder("select * from optionofquestion where q_id = ?");
		try {
			PreparedStatement pst = con.prepareStatement(sql.toString());
			pst.setInt(1, q_id);
			ResultSet set = pst.executeQuery();
			while(set.next()){
				Optionofquestion optionofquestion = new Optionofquestion();
				optionofquestion.setQ_id(set.getInt(1));
				optionofquestion.setO_content(set.getString(2));
				optionofquestion.setAbcd(set.getString(3));
				list.add(optionofquestion);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
