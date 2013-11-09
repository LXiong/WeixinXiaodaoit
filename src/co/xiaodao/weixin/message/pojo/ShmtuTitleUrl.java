package co.xiaodao.weixin.message.pojo;

/**
 * ��Ӧ��������ӵ�ַ
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-27
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class ShmtuTitleUrl {
	// ����
	private String title;
	// ���ӵ�ַ
	private String url;
	// ʱ��
	private String date;
	// ��������
	private String department;

	public ShmtuTitleUrl(String title, String url) {
		super();
		this.title = title;
		this.url = url;
	}

	public ShmtuTitleUrl(String title, String url, String date) {
		super();
		this.title = title;
		this.url = url;
		this.date = date;
	}

	public ShmtuTitleUrl(String title, String url, String date,
			String department) {
		super();
		this.title = title;
		this.url = url;
		this.date = date;
		this.department = department;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
