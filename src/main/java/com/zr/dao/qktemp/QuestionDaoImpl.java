package com.zr.dao.qktemp;

import java.nio.charset.MalformedInputException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.zr.model.Optionofquestion;
import com.zr.model.Question;
import com.zr.model.Type;
import com.zr.utils.JDBCUtil;

public class QuestionDaoImpl implements QuestionDao {
	private Connection con;

	public QuestionDaoImpl() {
		con = JDBCUtil.getConnection();
	}

	@Override
	public List<Type> getAllQuestionType() {
		List<Type> typeList = new LinkedList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM type");
		try {
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Type type = new Type();
				type.setT_id(rs.getInt("t_id"));
				type.setT_name(rs.getString("t_name"));
				typeList.add(type);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return typeList;
	}

	@Override
	public int getQustionByTypeIdAndKey(int start, int size, int typeId, String key, List<Question> questionList) {
		int count = 0;
		String selectSql = new String(" SELECT * ");
		String countSql = new String(" SELECT COUNT(q_id) AS count");
		// String whereSql = new String(" FROM question WHERE t_id = " + typeId
		// + " AND q_content LIKE '%"+ key +"%'");
		String whereSql = new String();
		if (typeId == -1)
			whereSql = " FROM question WHERE q_content LIKE '%" + key + "%'";
		else
			whereSql = " FROM question WHERE t_id = " + typeId + " AND q_content LIKE '%" + key + "%'";
		String limitSql = new String(" LIMIT " + start + "," + size);
		try {
			PreparedStatement ps = con.prepareStatement(countSql + whereSql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}
			ps = con.prepareStatement(selectSql + whereSql + limitSql);
			rs = ps.executeQuery();
			while (rs.next()) {
				questionList.add(row2entity(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	private static Question row2entity(ResultSet rs) throws SQLException {
		Question question = new Question();
		question.setQ_id(rs.getInt("q_id"));
		question.setT_id(rs.getInt("t_id"));
		question.setQ_content(rs.getString("q_content"));
		question.setT_id(rs.getInt("t_id"));
		question.setQ_answer(rs.getString("q_answer"));
		return question;
	}

	@Override
	public List<Optionofquestion> hasOption(int questionId) {
		List<Optionofquestion> optionlist = new LinkedList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT q_id,o_content,abcd FROM optionofquestion WHERE q_id = ?");
		try {
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setInt(1, questionId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Optionofquestion option = new Optionofquestion();
				option.setQ_id(rs.getInt("q_id"));
				option.setO_content(rs.getString("o_content"));
				option.setAbcd(rs.getString("abcd"));
				optionlist.add(option);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return optionlist;
	}

	public static void main(String[] args) {
		int[] eid = {102,103};
		QuestionDao dao = new QuestionDaoImpl();
		dao.removeAllQuestionOfExam(eid);
	}

	@Override
	public boolean removeAllQuestionOfExam(int[] examIds) {
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM exam_question WHERE e_id IN (");
		for (int i = 0; i < examIds.length; i++) {
			if (i == examIds.length - 1) {
				sql.append("?)");
			} else {
				sql.append("?,");
			}
		}
		try {
			// 批量更新
			PreparedStatement ps = con.prepareStatement(sql.toString());
			int i = 1;
			for (int eid : examIds) {
				ps.setInt(i++, eid);
			}
			System.out.println(ps.toString());
			if(ps.executeUpdate() > 0)
				result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
