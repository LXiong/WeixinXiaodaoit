package co.xiaodao.weixin.message.request;

import co.xiaodao.weixin.util.WeixinUtil;

/**
 * �û�������ı���Ϣ
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class ReqTextMessage extends ReqBaseMessage {
	// ��Ϣ����
	private String Content;
	// ��Ϣid��64λ����
	private String MsgId;

	public ReqTextMessage(String fromUserName, String createTime, String msgId,
			String content) {
		super(fromUserName, createTime, WeixinUtil.REQ_MESSAGE_TYPE_TEXT);
		Content = content;
		MsgId = msgId;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}