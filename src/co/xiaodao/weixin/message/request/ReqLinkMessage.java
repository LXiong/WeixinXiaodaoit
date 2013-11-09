package co.xiaodao.weixin.message.request;

import co.xiaodao.weixin.util.WeixinUtil;

/**
 * �û������������Ϣ
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class ReqLinkMessage extends ReqBaseMessage {
	// ��Ϣ����
	private String Title;
	// ��Ϣ����
	private String Description;
	// ��Ϣ����
	private String Url;
	// ��Ϣid��64λ����
	private String MsgId;

	public ReqLinkMessage(String fromUserName, String createTime, String msgId,
			String title, String description, String url) {
		super(fromUserName, createTime, WeixinUtil.REQ_MESSAGE_TYPE_LINK);
		Title = title;
		Description = description;
		Url = url;
		MsgId = msgId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
