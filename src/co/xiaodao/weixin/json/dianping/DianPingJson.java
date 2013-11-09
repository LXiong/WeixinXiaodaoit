package co.xiaodao.weixin.json.dianping;

import java.util.List;

/**
 * ���ڵ���JSONʵ����
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class DianPingJson {
	// ����API����״̬������ɹ�����"OK"�������ؽ���ֶΣ����ʧ�ܷ���"ERROR"�������ش���˵��
	private String status;
	// ����API��������ȡ���̻�����
	private int count;
	// �����̻���Ϣ����
	private List<DianPingBusinesses> businesses;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<DianPingBusinesses> getBusinesses() {
		return businesses;
	}

	public void setBusinesses(List<DianPingBusinesses> businesses) {
		this.businesses = businesses;
	}
}
