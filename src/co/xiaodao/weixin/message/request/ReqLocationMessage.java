package co.xiaodao.weixin.message.request;

import co.xiaodao.weixin.util.WeixinUtil;

/**
 * �û�����ĵ���λ����Ϣ
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class ReqLocationMessage extends ReqBaseMessage {
	// ����λ��ά��
	private String Location_X;
	// ����λ�þ���
	private String Location_Y;
	// ��ͼ���Ŵ�С
	private String Scale;
	// ����λ����Ϣ
	private String Label;
	// ��Ϣid��64λ����
	private String MsgId;

	public ReqLocationMessage(String fromUserName, String createTime,
			String msgId, String location_X, String location_Y, String scale,
			String label) {
		super(fromUserName, createTime, WeixinUtil.REQ_MESSAGE_TYPE_LOCATION);
		Location_X = location_X;
		Location_Y = location_Y;
		Scale = scale;
		Label = label;
		MsgId = msgId;
	}

	public String getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}

	public String getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}

	public String getScale() {
		return Scale;
	}

	public void setScale(String scale) {
		Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}