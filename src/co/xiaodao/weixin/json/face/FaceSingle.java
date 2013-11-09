package co.xiaodao.weixin.json.face;

/**
 * ��������
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class FaceSingle implements Comparable<FaceSingle> {
	// �������
	private String tag;
	// ����ID
	private String face_id;
	// ����һϵ�����������Է������
	private FaceAttribute attribute;
	// ���λ��
	private FacePosition position;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getFace_id() {
		return face_id;
	}

	public void setFace_id(String face_id) {
		this.face_id = face_id;
	}

	public FaceAttribute getAttribute() {
		return attribute;
	}

	public void setAttribute(FaceAttribute attribute) {
		this.attribute = attribute;
	}

	public FacePosition getPosition() {
		return position;
	}

	public void setPosition(FacePosition position) {
		this.position = position;
	}

	@Override
	public int compareTo(FaceSingle faceSingle) {
		int result = 0;
		if (this.position.getCenter().getX() > faceSingle.getPosition()
				.getCenter().getX()) {
			result = 1;
		} else {
			result = -1;
		}
		return result;
	}

}
