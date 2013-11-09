package co.xiaodao.weixin.json.express;

import java.util.List;

/**
 * ���JSONʵ����
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class ExpressJson {
	// ��ݴ��ţ��磺Բͨ��yuantong������ͨ��shentong��
	private String id;
	// �������
	private String name;
	// ��ݵ��ţ���ע�����ִ�Сд
	private String order;
	// ������ʹ�ô���
	private String num;
	// �ü�¼����ѯʱ�䣨�ڱ�ϵͳ30������ͬһ�����ͬһ�����Ŷ�ν���ѯ���ػ������ݣ�
	private String updateTime;
	// �����Ϣ���ݣ��ɺ��ԣ�
	private String message;
	/*
	 * ���ش����룺 0���޴��� 1�����KEY��Ч�� 2����ݴ�����Ч�� 3�����ʴ����ﵽ����ȣ� 4����ѯ���������ش��󼴷���״̬���200,
	 * 5������ִ�г���
	 */
	private String errCode;
	/*
	 * ��������״̬�� 0����ѯ������errCode!=0���� 1�����޼�¼�� 2����;�У� 3�������У� 4����ǩ�գ� 5�����գ� 6�����Ѽ�
	 * 7���˻�
	 */
	private String status;
	// �����������ݼ���
	private List<ExpressSingleData> data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ExpressSingleData> getData() {
		return data;
	}

	public void setData(List<ExpressSingleData> data) {
		this.data = data;
	}
}
