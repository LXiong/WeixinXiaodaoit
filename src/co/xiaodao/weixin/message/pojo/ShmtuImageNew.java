package co.xiaodao.weixin.message.pojo;

/**
 * ����ҳͼƬ����
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-27
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class ShmtuImageNew {

	// ����
	private String title;
	// ����
	private String desc;
	// ���ӵ�ַ
	private String link;
	// ͼƬ����
	private String imageLink;
	// ʱ��
	private String date;

	public ShmtuImageNew() {
		super();
	}

	public ShmtuImageNew(String title, String desc, String link,
			String imageLink, String date) {
		super();
		this.title = title;
		this.desc = desc;
		this.link = link;
		this.imageLink = imageLink;
		this.date = date;
	}

	public ShmtuImageNew(String title, String desc, String link,
			String imageLink) {
		super();
		this.title = title;
		this.desc = desc;
		this.link = link;
		this.imageLink = imageLink;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
