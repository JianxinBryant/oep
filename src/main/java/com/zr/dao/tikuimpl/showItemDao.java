package com.zr.dao.tikuimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zr.model.Optionofquestion;
import com.zr.model.Question;
import com.zr.model.showItem;
import com.zr.utils.JDBCUtil;

public class showItemDao {
	Connection con = JDBCUtil.getConnection();
	public int showallitem() {return 0;};
	public int getExamByKey(String key, int start, int pageSize, List<Question> questionList) {
		int count = 0;// 该页考试总数
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		// 信息sql部分
		String selectInfo = "select q_content,o_content from question,optionofquestion where question.q_id = optionofquestion.q_id and q_answer = abcd";
		
		// 总数sql部分
		String selectCount = "SELECT COUNT(q_content) AS count ";
		// 条件和分页sql部分
		String whereSql = "FROM question WHERE q_id LIKE ? ";
		String limit = "LIMIT " + start + "," + pageSize;
		// 关键字处理
		key = '%' + key + '%';
		try {
			sql.append(selectCount).append(whereSql);
			 pst = con.prepareStatement(sql.toString());
			int i = 1;
			pst.setString(i++, key);
			pst.setString(i++, key);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");// 总数
				//count = rs.getInt("selectCount");
			}
			sql = new StringBuffer();
			sql.append(selectInfo).append(whereSql).append(limit);
			pst = con.prepareStatement(sql.toString());
			i = 1;
			pst.setString(i++, key);
			pst.setString(i++, key);
			rs = pst.executeQuery();
			while (rs.next()) {
				questionList.add(row2entity(rs));// Question实体

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.closeJDBC(pst, con);
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
	private static Question row2entity(ResultSet rs) {
		Question question = new Question();
		try {
			question.setQ_id(rs.getInt("q_id"));
			question.setT_id(rs.getInt("t_id"));
			question.setQ_content(rs.getString("q_content"));
			question.setQ_content(rs.getString("q_answer"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return question;
	}
	//删除试题
	public boolean deleteByIds(int[] questionIds) {
		// 记录结果
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		PreparedStatement pst = null;
		sql.append("DELETE FROM question WHERE q_id IN (");
		//组装sql语句
		for (int i = 0; i < questionIds.length; i++) {
			sql.append(questionIds[i]);
			if (i == questionIds.length - 1) {
				sql.append(")");
			} else {
				sql.append(",");
			}
		}
		// 信息sql部分
		try {
			pst = con.prepareStatement(sql.toString());
			if(questionIds.length != pst.executeUpdate()){
				throw new SQLException();//删除过程异常
			}
			else{
				result = true;//删除成功
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.closeJDBC(pst, con);
		}
		return result;
	}
	
	//获取当前最大的q_id号
	public int getmaxqid() {
		int a = 0 ;
		Connection con = JDBCUtil.getConnection();
		StringBuffer sb = new StringBuffer("select max(q_id) from question");
		PreparedStatement pst = null;
			try {
				pst = con.prepareStatement(sb.toString());
				ResultSet rs = pst.executeQuery();
			    a = rs.getInt(0);//获取当前数据库中最大的id值
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCUtil.closeJDBC(pst, con);
			}
			return a;
	}
	
	//添加单选试题
	public int addItemcontent_i_danswer(String q_content,int t_id,String q_answer) {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pst = null;
		StringBuffer sb = new StringBuffer("insert into question(q_content,t_id,q_answer) values(?,?,?)");
		try {
			pst = con.prepareStatement(sb.toString());
			pst.setString(1, q_content);
			pst.setInt(2, t_id);
			pst.setString(3, q_answer);
			return pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.closeJDBC(pst, con);
		}
		return 0;	
	}
	//??????????????????????????????????????
	//增加每道单选题的选项内容q_id为题目id,o_content为每个选项内容,abcd为每项内容对应的A,B,C,D
	 public int addItemoption(int q_id,String o_content,String abcd) {
		 Connection con = JDBCUtil.getConnection();
		 PreparedStatement pst = null;
		 StringBuffer sb = new StringBuffer("insert into optionofquestion value(?,?,?)");
		 try {
			pst = con.prepareStatement(sb.toString());
			pst.setInt(1, q_id);
			pst.setString(2, o_content);
			pst.setString(3, abcd);
			return pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.closeJDBC(pst, con);
		}
		 return 0;
	 }
	 
	//增加大题q_id为题目id,q_content为题目的内容,q_answer为题目的答案
		 public int adddatiItem(String q_content, String q_answer,int q_id) {
			 Connection con = JDBCUtil.getConnection();
			 PreparedStatement pst = null;
			 StringBuffer sb = new StringBuffer("insert INTO question(q_id,t_id,q_content,q_answer) value(?,'4',?,?)");
			 try {
				pst = con.prepareStatement(sb.toString());
				pst.setInt(1, q_id);
				pst.setString(2, q_content);
				pst.setString(3, q_answer);
				pst.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JDBCUtil.closeJDBC(pst, con);
			}
			return 0;
		 }
		 
		 public int getq_answer(int q_id) {
			 Connection con = JDBCUtil.getConnection();
			 PreparedStatement pst = null;
			 StringBuffer sb = new StringBuffer("select q_answer from question where q_id = ?");
			 try {
				pst = con.prepareStatement(sb.toString());
				pst.setInt(1, q_id);
				pst.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCUtil.closeJDBC(pst, con);
			} 
			 return 0;
		 }
	 
		// 记录结果
		
	//通过q_content查询考题
		public List<showItem> searchitembycontent(String partofcontent){
			List<showItem> list = new ArrayList<showItem>();
			StringBuffer  sb = new StringBuffer("");
			//sb.append("SELECT question.q_id,q_content,q_answer,o_content from question,optionofquestion ");
			//sb.append("where (q_content like ?) ");
			//sb.append("AND (question.q_answer = optionofquestion.abcd)");
			sb.append("SELECT question.q_id,q_content,q_answer, o_content from question ");
			sb.append("LEFT OUTER JOIN optionofquestion on question.q_id = optionofquestion.q_id ");
			sb.append("and q_answer = abcd where q_content like ?");
			Connection  con = JDBCUtil.getConnection();
			PreparedStatement pst = null;
			try {
				pst = con.prepareStatement(sb.toString());
				pst.setString(1, partofcontent);
				ResultSet rs = pst.executeQuery();
				while(rs.next()) {
					showItem searchitem = new showItem();
					searchitem.setQ_id(rs.getInt("question.q_id"));
					searchitem.setQ_content(rs.getString("q_content"));
					searchitem.setQ_answer(rs.getString("q_answer"));
					searchitem.setO_content(rs.getString("o_content"));
					list.add(searchitem);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCUtil.closeJDBC(pst, con);
			}
			return list;
		}	
		
		
		
		public List<showItem> showItem() {
			// TODO Auto-generated method stub
			Connection con=JDBCUtil.getConnection();
			StringBuffer sb=new StringBuffer();
//			sb.append("SELECT question.q_id,q_content,q_answer, o_content ");
//			sb.append("from question LEFT OUTER JOIN optionofquestion on ");
//			sb.append("question.q_id = optionofquestion.q_id and abcd ");
//			sb.append("in ((select left((select q_answer from question),1)), ");
//			sb.append("(select right((select left((select q_answer from question),2)),1)), ");
//			sb.append("(SELECT left((select right((select q_answer from question),2)),1)), ");
//			sb.append("(select right((select q_answer from question),1)))");
			sb.append("SELECT question.q_id,q_content,q_answer, o_content ");
			sb.append("from question LEFT OUTER JOIN optionofquestion on ");
			sb.append("question.q_id = optionofquestion.q_id and abcd = q_answer");
			PreparedStatement pst = null;
			try {
				pst=con.prepareStatement(sb.toString());
				ResultSet rs=pst.executeQuery();
				List<showItem> list=new ArrayList<showItem>();
				while(rs.next()){
					showItem showitem=new showItem();
					showitem.setQ_id(rs.getInt("question.q_id"));
					showitem.setQ_content(rs.getString("q_content"));
					showitem.setQ_answer(rs.getString("q_answer"));
					showitem.setO_content(rs.getString("o_content"));
					list.add(showitem);
				}
				return list;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCUtil.closeJDBC(pst, con);
			}
			return null;
		}
		
		
		
	//修改试题信息
		public boolean updatebyqid( String o_content , int questionId) {
			// 记录结果
			Connection con = JDBCUtil.getConnection();
			boolean result = false;
			PreparedStatement pst = null;
			StringBuffer sb = new StringBuffer();
			sb.append("update optionofquestion set o_content = ? where ");
			sb.append("q_id = (select q_id from question where q_id = ?)");
			try {
				pst = con.prepareStatement(sb.toString());
				pst.setString(1, o_content);
				pst.setInt(2, questionId);
				ResultSet rs = pst.executeQuery();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCUtil.closeJDBC(pst, con);
			}
			return result;
		}
}
