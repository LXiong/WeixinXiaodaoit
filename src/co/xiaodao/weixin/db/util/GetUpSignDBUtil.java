package co.xiaodao.weixin.db.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.xiaodao.weixin.db.pojo.GetUpSign;

/**
 * ����ǩ��������ݿ⽻��
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-5-12
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class GetUpSignDBUtil {

	public static void main(String[] sra) {
		
	}

	/**
	 * ����ǩ����Ϣ
	 * 
	 * @param getUpSign
	 * @return
	 */
	public static boolean insertGetUpSign(GetUpSign getUpSign,String date) {
		Connection conn = BaseDBUtil.getCon();
		PreparedStatement pstmt = null;

		String insertSql = "insert into tb_getup_sign(rank,open_id,date,time)( select count(*)+1,'"
				+ getUpSign.getOpenID()
				+ "','"
				+ getUpSign.getDate()
				+ "','"
				+ getUpSign.getTime()
				+ "' from tb_getup_sign where date = '"
				+ date + "')";

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

	/**
	 * ����openID��ȡ����������Ϣ
	 * 
	 * @param openID
	 * @return
	 */
	public static GetUpSign getGetUpSignByOpenID(String openID,String date) {
		Connection conn = BaseDBUtil.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String checkSql = "select * from tb_getup_sign where open_id = '"
				+ openID + "' and date = '" + date + "'";
		try {
			pstmt = conn.prepareStatement(checkSql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return new GetUpSign(openID, rs.getString("date"),
						rs.getString("time"), rs.getString("rank"));
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
	 * ��ȡ���������ǩ���û�
	 * 
	 * @param openID
	 * @return
	 */
	public static List<GetUpSign> getTodayAllSign(String date) {
		Connection conn = BaseDBUtil.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String checkSql = "select * from tb_getup_sign where date = '"
				+ date + "'";
		try {
			pstmt = conn.prepareStatement(checkSql);
			rs = pstmt.executeQuery();
			List<GetUpSign> getUpSignList = new ArrayList<GetUpSign>();
			while (rs.next()) {
				getUpSignList.add(new GetUpSign(rs.getString("open_id"), rs
						.getString("date"), rs.getString("time"), rs
						.getString("rank")));
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			return getUpSignList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDBUtil.closeCon(conn);
		}
		return null;
	}

}
