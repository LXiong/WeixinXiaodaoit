package co.xiaodao.weixin.message.pojo;

/**
 * �ظ�ͼ����Ϣ��ʵ����
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class Article {
	// ͼ����Ϣ����
	private String Title;
	// ͼ����Ϣ����
	private String Description;
	// ͼƬ���ӣ�֧��JPG��PNG��ʽ���Ϻõ�Ч��Ϊ��ͼ640*320��Сͼ80*80������ͼƬ���ӵ�������Ҫ�뿪������д�Ļ��������е�Urlһ��
	private String PicUrl;
	// ���ͼ����Ϣ��ת����
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return null == Description ? "" : Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return null == PicUrl ? "" : PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return null == Url ? "" : Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

}
