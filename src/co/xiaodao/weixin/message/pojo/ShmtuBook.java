package co.xiaodao.weixin.message.pojo;

/**
 * ͼ��������ͼ����Ϣ
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-27
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class ShmtuBook {

	// ����
	private String title;
	// ���ӵ�ַ
	private String link;
	// ����
	private String author;
	// ����ʱ��
	private String pubDate;
	// ����
	private String description;

	public ShmtuBook(String title, String link, String author, String pubDate,
			String description) {
		super();
		this.title = title;
		this.link = link;
		this.author = author;
		this.pubDate = pubDate;
		this.description = description;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
