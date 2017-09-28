package com.zr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.zr.dao.ExamDao;
import com.zr.model.Exam;
import com.zr.model.Exam_question;
import com.zr.model.Question;
import com.zr.utils.JDBCUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExamDaoImpl implements ExamDao {
	Connection con;

	public ExamDaoImpl() {
		this.con = JDBCUtil.getConnection();
	}

	@Override
	public int getExamByKey(String key, int start, int pageSize, List<Exam> examList) {
		int count = 0;// 该页考试总数
		StringBuffer sql = new StringBuffer();
		// 信息sql部分
		String selectInfo = "SELECT e_id,e_name,e_starttime,e_endtime,e_state,e_total ";
		// 总数sql部分
		String selectCount = "SELECT COUNT(e_id) AS count ";
		// 条件和分页sql部分
		String whereSql = "FROM exam WHERE e_name LIKE ? OR e_state LIKE ? ";
		String limit = "LIMIT " + start + "," + pageSize;
		// 关键字处理
		key = '%' + key + '%';
		try {
			sql.append(selectCount).append(whereSql);
			PreparedStatement ps = con.prepareStatement(sql.toString());
			int i = 1;
			ps.setString(i++, key);
			ps.setString(i++, key);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");// 总数
			}
			sql = new StringBuffer();
			sql.append(selectInfo).append(whereSql).append(limit);
			ps = con.prepareStatement(sql.toString());
			i = 1;
			ps.setString(i++, key);
			ps.setString(i++, key);
			rs = ps.executeQuery();
			while (rs.next()) {
				examList.add(row2entity(rs));// exam实体

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 记录转化为实体对象
	 * 
	 * @param rs
	 *            一条记录
	 * @return 实体对象
	 */
	private static Exam row2entity(ResultSet rs) {
		Exam exam = new Exam();
		try {
			exam.setE_id(rs.getInt("e_id"));
			exam.setE_name(rs.getString("e_name"));
			exam.setE_starttime(rs.getString("e_starttime"));
			exam.setE_endtime(rs.getString("e_endtime"));
			exam.setE_state(rs.getString("e_state"));
			exam.setE_total(rs.getInt("e_total"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exam;
	}

	@Override
	public boolean deleteByIds(int[] examIds) {
		// 记录结果
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM exam WHERE e_id IN (");
		// 组装sql语句
		for (int i = 0; i < examIds.length; i++) {
			sql.append(examIds[i]);
			if (i == examIds.length - 1) {
				sql.append(")");
			} else {
				sql.append(",");
			}
		}
		// 信息sql部分
		try {
			PreparedStatement ps = con.prepareStatement(sql.toString());
			if (examIds.length != ps.executeUpdate()) {
				throw new SQLException();// 删除过程异常
			} else {
				result = true;// 删除成功
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int insert(Exam exam) {
		// 记录结果
		int result = -1;
		// 组装sql语句
		StringBuffer sql = new StringBuffer();
		// 插入sql语句
		sql.append("INSERT INTO exam ")
				.append("(exam.e_name,exam.e_starttime,exam.e_endtime,exam.e_state,exam.e_total) ")
				.append("VALUES (?,?,?,?,?)");
		// 获得该考试的记录sql语句
		StringBuffer getCurrendExamIdSql = new StringBuffer();
		getCurrendExamIdSql.append("SELECT MAX(exam.e_id) AS currentExamId FROM exam");
		// 信息sql部分
		try {
			PreparedStatement ps = con.prepareStatement(sql.toString());
			int i = 1;
			ps.setString(i++, exam.getE_name());
			ps.setString(i++, exam.getE_starttime());
			ps.setString(i++, exam.getE_endtime());
			ps.setString(i++, exam.getE_state());
			ps.setInt(i++, exam.getE_total());
			// 不可重复读？？
			// 并发操作时防止读取到其他管理员插入的记录，确保获取的是本次插入考试的id
			con.setAutoCommit(false);
			if (ps.executeUpdate() < 0) {
				return result;
			}
			ps = con.prepareStatement(getCurrendExamIdSql.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt("currentExamId");
			}
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Exam getExamById(int id) {
		Exam exam = new Exam();
		// sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM exam WHERE e_id = ?");
		try {
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				exam = row2entity(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exam;
	}

	@Override
	public int[] getExistQuestionId(int examId) {
		List<Integer> ids = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT q_id FROM exam_question WHERE e_id = ? ORDER BY q_id");
		try {
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setInt(1, examId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ids.add(rs.getInt("q_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int[] questionIds = new int[ids.size()];
		for (int i = 0; i < ids.size(); i++) {
			questionIds[i] = ids.get(i);
		}
		return questionIds;
	}

	@Override
	public boolean insertExamQuestions(List<Exam_question> eqList) {
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO exam_question (e_id,q_id,score) VALUES (?,?,?)");
		try {
			// 批量查询
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(sql.toString());
			for (int i = 0; i < eqList.size(); i++) {
				Exam_question eq = eqList.get(i);
				ps.setInt(1, eq.getE_id());
				ps.setInt(2, eq.getQ_id());
				ps.setInt(3, eq.getScore());
				ps.addBatch();
			}
			ps.executeBatch();
			con.commit();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Question> getQuestionOfExam(int examId) {
		List<Question> questionList = new LinkedList<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT question.q_id,t_id,q_content,q_answer ").append(" FROM question ")
				.append(" INNER JOIN exam_question ").append(" ON question.q_id = exam_question.q_id ")
				.append(" WHERE exam_question.e_id = ? ").append(" ORDER BY question.t_id");
		try {
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setInt(1, examId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Question question = new Question();
				question.setQ_id(rs.getInt("q_id"));
				question.setT_id(rs.getInt("t_id"));
				question.setQ_content(rs.getString("q_content"));
				question.setQ_answer(rs.getString("q_answer"));
				questionList.add(question);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questionList;
	}

	@Override
	public boolean removeQuestionOfExam(int examId, int[] ids) {
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM exam_question WHERE e_id = ? AND q_id = ?");
		try {
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(sql.toString());
			for (int i = 0; i < ids.length; i++) {
				ps.setInt(1, examId);
				ps.setInt(2, ids[i]);
				ps.addBatch();
			}
			ps.executeBatch();
			con.commit();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int getScore(int examId, int q_id) {
		int score = -1;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT score FROM exam_question WHERE e_id = ? AND q_id = ?");
		try {
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setInt(1, examId);
			ps.setInt(2, q_id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				score = rs.getInt("score");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return score;
	}

	@Override
	public JSONArray getTypeInfo(int examId) {
		JSONArray json = new JSONArray();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT question.t_id,type.t_name,COUNT(exam_question.q_id) AS number, ")
				.append("AVG(exam_question.score) AS score,SUM(exam_question.score) AS total ")
				.append("FROM exam_question ").append("INNER JOIN question ON exam_question.q_id = question.q_id ")
				.append("INNER JOIN type ON type.t_id = question.t_id ").append("WHERE exam_question.e_id = ? ")
				.append("GROUP BY question.t_id ").append("ORDER BY question.t_id ");
		try {
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setInt(1, examId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				JSONObject typeInfo = new JSONObject();
				typeInfo.put("typeId", rs.getInt("t_id"));
				typeInfo.put("typeName", rs.getString("t_name"));
				typeInfo.put("questionNumber", rs.getInt("number"));
				typeInfo.put("questionScore", rs.getInt("score"));
				typeInfo.put("questionTotal", rs.getInt("total"));
				json.add(typeInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public boolean updateExamQuestions(List<Exam_question> eqList) {
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE exam_question SET score = ? WHERE e_id = ? AND q_id = ?");
		try {
			// 批量更新
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(sql.toString());
			for (int i = 0; i < eqList.size(); i++) {
				Exam_question eq = eqList.get(i);
				ps.setInt(1, eq.getScore());
				ps.setInt(2, eq.getE_id());
				ps.setInt(3, eq.getQ_id());
				ps.addBatch();
			}
			ps.executeBatch();
			con.commit();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean setExamQuestions(List<Exam_question> insertQuestionList, List<Exam_question> updateQuestionList) {
		boolean result = false;
		try {
			con.setAutoCommit(false);
			boolean result1 = this.insertExamQuestions(insertQuestionList);
			boolean result2 = this.updateExamQuestions(updateQuestionList);
			result = result1 & result2;
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean updateExamInfo(Exam exam) {
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE exam ").append("SET e_name = ?,e_starttime=?, ")
			.append("e_endtime=?,e_total=? ")
			.append("WHERE e_id = ?");
		try {
			PreparedStatement ps = con.prepareStatement(sql.toString());
			int i = 1;
			ps.setString(i++, exam.getE_name());
			ps.setString(i++, exam.getE_starttime());
			ps.setString(i++, exam.getE_endtime());
			ps.setInt(i++, exam.getE_total());
			ps.setInt(i++, exam.getE_id());
			if (ps.executeUpdate() > 0) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean issueExam(int examId) {
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE exam ").append("SET e_state = ?")
			.append("WHERE e_id = ?");
		try {
			PreparedStatement ps = con.prepareStatement(sql.toString());
			int i = 1;
			ps.setString(i++, "true");
			ps.setInt(i++, examId);
			if (ps.executeUpdate() > 0) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Exam getExamTime(int e_id) {
		Exam exam = new Exam();
		Connection con = JDBCUtil.getConnection();
		StringBuffer sql = new StringBuffer("SELECT exam.e_starttime,exam.e_endtime FROM exam WHERE e_id = ?");
		try {
			PreparedStatement pst = con.prepareStatement(sql.toString());
			pst.setInt(1, e_id);
			ResultSet set = pst.executeQuery();
			if(set.next()){
				exam.setE_starttime(set.getString(1));
				exam.setE_endtime(set.getString(2));
			}
			return exam;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exam;
	}
}