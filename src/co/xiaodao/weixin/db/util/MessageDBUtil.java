package co.xiaodao.weixin.db.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import co.xiaodao.weixin.message.request.ReqEventMessage;
import co.xiaodao.weixin.message.request.ReqImageMessage;
import co.xiaodao.weixin.message.request.ReqLocationMessage;
import co.xiaodao.weixin.message.request.ReqTextMessage;
import co.xiaodao.weixin.message.response.RespMusicMessage;
import co.xiaodao.weixin.message.response.RespNewsMessage;
import co.xiaodao.weixin.message.response.RespTextMessage;
import co.xiaodao.weixin.util.WeixinUtil;

/**
 * ��Ϣ������ݿ⽻��
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class MessageDBUtil {

	/**
	 * ������Ϣ��������
	 * 
	 * @param insertMsgSql
	 * @return
	 */
	public static boolean insertMsg(String insertMsgSql) {
		Connection conn = BaseDBUtil.getCon();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(insertMsgSql);
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
	 * ����������ı���Ϣ
	 * 
	 * @param reqTextMessage
	 * @return
	 */
	public static boolean insertReqTextMsg(ReqTextMessage reqTextMessage) {
		String insertTextMsgSql = "insert into tb_message(is_request,msg_type,open_id,create_time,msg_id,content) values('"
				+ WeixinUtil.IS_REQUEST
				+ "','"
				+ reqTextMessage.getMsgType()
				+ "','"
				+ reqTextMessage.getFromUserName()
				+ "','"
				+ reqTextMessage.getCreateTime()
				+ "','"
				+ reqTextMessage.getMsgId()
				+ "','"
				+ reqTextMessage.getContent() + "')";
		return MessageDBUtil.insertMsg(insertTextMsgSql);
	}

	/**
	 * ���������ͼƬ��Ϣ
	 * 
	 * @param reqImageMessage
	 * @return
	 */
	public static boolean insertReqImageMsg(ReqImageMessage reqImageMessage) {
		String insertImageMsgSql = "insert into tb_message(is_request,msg_type,open_id,create_time,msg_id,image_pic_url) values('"
				+ WeixinUtil.IS_REQUEST
				+ "','"
				+ reqImageMessage.getMsgType()
				+ "','"
				+ reqImageMessage.getFromUserName()
				+ "','"
				+ reqImageMessage.getCreateTime()
				+ "','"
				+ reqImageMessage.getMsgId()
				+ "','"
				+ reqImageMessage.getPicUrl() + "')";
		return MessageDBUtil.insertMsg(insertImageMsgSql);
	}

	/**
	 * ���������λ����Ϣ
	 * 
	 * @param reqLocationMessage
	 * @return
	 */
	public static boolean insertReqLocationMsg(
			ReqLocationMessage reqLocationMessage) {
		String insertLocationMsgSql = "insert into tb_message(is_request,msg_type,open_id,create_time,msg_id,location_x,location_y,scale,label) values('"
				+ WeixinUtil.IS_REQUEST
				+ "','"
				+ reqLocationMessage.getMsgType()
				+ "','"
				+ reqLocationMessage.getFromUserName()
				+ "','"
				+ reqLocationMessage.getCreateTime()
				+ "','"
				+ reqLocationMessage.getMsgId()
				+ "','"
				+ reqLocationMessage.getLocation_X()
				+ "','"
				+ reqLocationMessage.getLocation_Y()
				+ "','"
				+ reqLocationMessage.getScale()
				+ "','"
				+ reqLocationMessage.getLabel() + "')";
		return MessageDBUtil.insertMsg(insertLocationMsgSql);
	}

	/**
	 * ����������¼���Ϣ
	 * 
	 * @param reqEventMessage
	 * @return
	 */
	public static boolean insertReqEventMsg(ReqEventMessage reqEventMessage) {
		String insertEventMsgSql = "insert into tb_message(is_request,msg_type,open_id,create_time,event,event_key) values('"
				+ WeixinUtil.IS_REQUEST
				+ "','"
				+ reqEventMessage.getMsgType()
				+ "','"
				+ reqEventMessage.getFromUserName()
				+ "','"
				+ reqEventMessage.getCreateTime()
				+ "','"
				+ reqEventMessage.getEvent()
				+ "','"
				+ reqEventMessage.getEventKey() + "')";
		return MessageDBUtil.insertMsg(insertEventMsgSql);
	}

	/**
	 * ������Ӧ���ı���Ϣ
	 * 
	 * @param respTextMessage
	 * @return
	 */
	public static boolean insertRespTextMsg(RespTextMessage respTextMessage) {
		String insertRespTextMsgSql = "insert into tb_message(is_request,msg_type,open_id,create_time,content,func_flag) values('"
				+ WeixinUtil.IS_RESPONSE
				+ "','"
				+ respTextMessage.getMsgType()
				+ "','"
				+ respTextMessage.getToUserName()
				+ "','"
				+ respTextMessage.getCreateTime()
				+ "','"
				+ respTextMessage.getContent()
				+ "','"
				+ respTextMessage.getFuncFlag() + "')";
		return MessageDBUtil.insertMsg(insertRespTextMsgSql);
	}

	/**
	 * ������Ӧ��������Ϣ
	 * 
	 * @param respMusicMessage
	 * @return
	 */
	public static boolean insertRespMusicMsg(RespMusicMessage respMusicMessage) {
		String insertRespMusicMsgSql = "insert into tb_message(is_request,msg_type,open_id,create_time,music_url,hq_music_url,func_flag) values('"
				+ WeixinUtil.IS_RESPONSE
				+ "','"
				+ respMusicMessage.getMsgType()
				+ "','"
				+ respMusicMessage.getToUserName()
				+ "','"
				+ respMusicMessage.getCreateTime()
				+ "','"
				+ respMusicMessage.getMusic().getMusicUrl()
				+ "','"
				+ respMusicMessage.getMusic().getHQMusicUrl()
				+ "','"
				+ respMusicMessage.getFuncFlag() + "')";
		return MessageDBUtil.insertMsg(insertRespMusicMsgSql);
	}

	/**
	 * ������Ӧ��ͼ����Ϣ
	 * 
	 * @param respNewsMessage
	 * @param articleXML
	 * @return
	 */
	public static boolean insertRespNewsMsg(RespNewsMessage respNewsMessage,
			String articleXML) {

		String insertRespNewsMsgSql = "insert into tb_message(is_request,msg_type,open_id,create_time,article_count,articles_xml,func_flag) values('"
				+ WeixinUtil.IS_RESPONSE
				+ "','"
				+ respNewsMessage.getMsgType()
				+ "','"
				+ respNewsMessage.getToUserName()
				+ "','"
				+ respNewsMessage.getCreateTime()
				+ "','"
				+ respNewsMessage.getArticleCount()
				+ "','"
				+ articleXML
				+ "','" + respNewsMessage.getFuncFlag() + "')";
		return MessageDBUtil.insertMsg(insertRespNewsMsgSql);
	}

}
