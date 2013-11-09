package co.xiaodao.weixin.db.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.xiaodao.weixin.db.pojo.Question;

/**
 * �������������ݿ⽻��
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class QuestionDBUtil {

	/**
	 * ��ȡ��
	 * 
	 * @param question
	 * @return
	 */
	public static String getAnswer(String question) {
		Connection conn = BaseDBUtil.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String checkSql = "select answer from tb_question where question = '"
				+ question + "'";
		try {
			pstmt = conn.prepareStatement(checkSql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("answer");
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDBUtil.closeCon(conn);
		}
		return null;
	}

	/**
	 * ��������
	 * 
	 * @param question
	 * @return
	 */
	public static boolean insertQuestion(Question question) {
		Connection conn = BaseDBUtil.getCon();
		PreparedStatement pstmt = null;
		String insertSql = "insert into tb_question(category, question, answer,open_id,create_time) values('"
				+ question.getCategory()
				+ "','"
				+ question.getQuestion()
				+ "','"
				+ question.getAnswer()
				+ "','"
				+ question.getOpenID()
				+ "','" + question.getCreateTime() + "')";
		try {
			pstmt = conn.prepareStatement(insertSql);
			if (pstmt != null && pstmt.executeUpdate() > 0) {
				return true;
			}
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDBUtil.closeCon(conn);
		}
		return false;
	}

	public static void main(String[] args) {

	}
}
