package co.xiaodao.weixin.message.request;

import co.xiaodao.weixin.util.WeixinUtil;

/**
 * �û������ͼƬ��Ϣ
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class ReqImageMessage extends ReqBaseMessage {
	// ͼƬ����
	private String PicUrl;
	// ��Ϣid��64λ����
	private String MsgId;

	public ReqImageMessage(String fromUserName, String createTime,
			String msgId, String picUrl) {
		super(fromUserName, createTime, WeixinUtil.REQ_MESSAGE_TYPE_IMAGE);
		PicUrl = picUrl;
		MsgId = msgId;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
