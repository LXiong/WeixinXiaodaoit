package co.xiaodao.weixin.message.request;

import co.xiaodao.weixin.util.WeixinUtil;

/**
 * �û�������¼���Ϣ
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class ReqEventMessage extends ReqBaseMessage {
	// �¼����ͣ�subscribe(����)��unsubscribe(ȡ������)��CLICK(�Զ���˵�����¼�)
	private String Event;
	// �¼�KEYֵ�����Զ���˵��ӿ���KEYֵ��Ӧ
	private String EventKey;

	public ReqEventMessage(String fromUserName, String createTime,
			String event, String eventKey) {
		super(fromUserName, createTime, WeixinUtil.REQ_MESSAGE_TYPE_EVENT);
		Event = event;
		EventKey = eventKey;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getEvent() {
		return Event;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getEventKey() {
		return EventKey;
	}

}