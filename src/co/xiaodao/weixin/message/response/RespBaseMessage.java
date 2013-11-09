package co.xiaodao.weixin.message.response;

/**
 * ��Ӧ����Ϣ���ࣨ�����˺� -> ��ͨ�û���
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class RespBaseMessage {
	// ���շ��ʺţ��յ���OpenID��
	private String ToUserName;
	// ������΢�ź�
	private String FromUserName;
	// ��Ϣ����ʱ�� �����ͣ�
	private String CreateTime;
	// ��Ϣ���ͣ�text/music/news��
	private String MsgType;
	// λ0x0001����־ʱ���Ǳ���յ�����Ϣ
	private String FuncFlag;

	public RespBaseMessage(String fromUserName, String toUserName,
			String createTime, String msgType, String funcFlag) {
		super();
		FromUserName = fromUserName;
		ToUserName = toUserName;
		CreateTime = createTime;
		MsgType = msgType;
		FuncFlag = funcFlag;
	}

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getFuncFlag() {
		return FuncFlag;
	}

	public void setFuncFlag(String funcFlag) {
		FuncFlag = funcFlag;
	}
}
