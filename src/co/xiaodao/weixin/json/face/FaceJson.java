package co.xiaodao.weixin.json.face;

import java.util.List;

/**
 * ��ȡͼƬ�е���������
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class FaceJson {
	// ��Ӧ�����session��ʶ���������ڽ����ѯ
	private String session_id;
	// ������ͼƬ��url
	private String url;
	// Face++ϵͳ�е�ͼƬ��ʶ�������ڱ�ʶ�û������е�ͼƬ
	private String img_id;
	// ����ͼƬ�Ŀ��
	private String img_width;
	// ����ͼƬ�ĸ߶�
	private String img_height;
	// ���������������б�
	private List<FaceSingle> face;

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg_id() {
		return img_id;
	}

	public void setImg_id(String img_id) {
		this.img_id = img_id;
	}

	public String getImg_width() {
		return img_width;
	}

	public void setImg_width(String img_width) {
		this.img_width = img_width;
	}

	public String getImg_height() {
		return img_height;
	}

	public void setImg_height(String img_height) {
		this.img_height = img_height;
	}

	public List<FaceSingle> getFace() {
		return face;
	}

	public void setFace(List<FaceSingle> face) {
		this.face = face;
	}

}
