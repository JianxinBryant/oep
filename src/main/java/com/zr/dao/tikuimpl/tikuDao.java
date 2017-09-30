package com.zr.dao.tikuimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zr.model.Question;
import com.zr.utils.JDBCUtil;

public class tikuDao {
	//获取当前数据库中最大的id值
	public int getmaxqid() {
		int a = 0 ;
		Connection con = JDBCUtil.getConnection();
		StringBuffer sb = new StringBuffer("select max(q_id) a from question");
			PreparedStatement pst = null;
			try {
				pst = con.prepareStatement(sb.toString());
				ResultSet rs = pst.executeQuery();
			    if(rs.next()) {
			    	a=rs.getInt("a");//获取当前数据库中最大的id值
			    	System.out.println(a);
			    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCUtil.closeJDBC(pst, con);
			}
			return a;
	}

	
	
	
	
	
	//增加题目的标题q_content,确定题目的类型 t_id,增加选择题的答案(为a,b,c,d)
	public int addItemcontent_i_danswer(int q_id,String q_content,int t_id,String q_answer) {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pst = null;
		StringBuffer sb = new StringBuffer("insert into question(q_id,q_content,t_id,q_answer) values(?,?,?,?)");
		try {
			 pst = con.prepareStatement(sb.toString());
			 pst.setInt(1, q_id);
			pst.setString(2, q_content);
			pst.setInt(3, t_id);
			pst.setString(4, q_answer);
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
	 public int adddatiItem(int q_id,String q_content, String q_answer) {
		 Connection con = JDBCUtil.getConnection();
		 PreparedStatement pst = null;
		 StringBuffer sb = new StringBuffer("insert INTO question(t_id,q_id,q_content,q_answer) value('4',?,?,?)");//value('4',?,?)"
		 try {
			pst = con.prepareStatement(sb.toString());
			pst.setInt(1, q_id);
			pst.setString(2, q_content);
			pst.setString(3, q_answer);
			return pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.closeJDBC(pst, con);
		}
		return 0;
	 }
	 
	 //***********************************************************************************
		public int deletequestionitem(int q_id) {
			int i = 0 ;
			Connection con = JDBCUtil.getConnection();
			PreparedStatement pst = null;
			StringBuffer sb = new StringBuffer("");
			sb.append("delete from question where q_id = ?");
			try {
				 pst = con.prepareStatement(sb.toString());
				pst.setInt(1, q_id);
				i = pst.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCUtil.closeJDBC(pst, con);
			}
			return 0;
		}
		
		
		
		public int deleteanswerofuseritem(int q_id) {
			int a = 0 ;
			Connection con = JDBCUtil.getConnection();
			PreparedStatement pst = null;
			StringBuffer sb = new StringBuffer("");
			sb.append("delete from answerofuser where q_id = ?");
			try {
				pst = con.prepareStatement(sb.toString());
				pst.setInt(1, q_id);
				a = pst.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCUtil.closeJDBC(pst, con);
			}
			return 0;
		}
		
		
		
		
		public int deleteexamquestion(int q_id) {
			int a = 0 ; 
			Connection con = JDBCUtil.getConnection();
			PreparedStatement pst = null;
			StringBuffer sb = new StringBuffer("");
			sb.append("delete from exam_question where q_id = ?");
			try {
				pst = con.prepareStatement(sb.toString());
				pst.setInt(1, q_id);
				a = pst.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCUtil.closeJDBC(pst, con);
			}
			return 0;
		}
		
		
		
		public int deleteoptionofquestion(int q_id) {
			int a = 0 ; 
			Connection con = JDBCUtil.getConnection();
			StringBuffer sb = new StringBuffer("");
			PreparedStatement pst = null;
			sb.append("delete from optionofquestion where q_id = ?");
			try {
				 pst = con.prepareStatement(sb.toString());
				pst.setInt(1, q_id);
				a = pst.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCUtil.closeJDBC(pst, con);
			}
			return 0;
		}
	 //***********************************************************************************
		//依靠试题的内容查询试题信息q_content为试题内容
		public List<Question> searchitembycontent(String q_content){
			List<Question>  questions = new ArrayList<Question>();
			StringBuffer  sql = new StringBuffer(" ");
			PreparedStatement pst = null;
			sql.append("select q_content,q_answer from question ");
			sql.append("where q_content like '%?' or q_content like '%?%' ");
			sql.append("or q_content like '?%'");
			Connection  con = JDBCUtil.getConnection();
			try {
				 pst = con.prepareStatement(sql.toString());
				pst.setString(1, q_content);
				pst.setString(2, q_content);
				pst.setString(3, q_content);
				ResultSet  rs = pst.executeQuery();
				while (rs.next()) {
				Question  question = new Question();
				question.setQ_content(q_content);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCUtil.closeJDBC(pst, con);
			}
			return questions;
		}
	//***********************************************************************************
	//更新题目	
		
}
