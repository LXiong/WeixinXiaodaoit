package co.xiaodao.weixin.json.face;

/**
 * ���ٵ�λ�ü���
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class FacePosition {
	// ���������������ĵ�����, x & y ����ֱ��ʾ��ͼƬ�еĿ�Ⱥ͸߶ȵİٷֱ� (0~100֮���ʵ��)
	private FacePositionSub center;
	// ��Ӧ�������������꣬x & y ����ֱ��ʾ��ͼƬ�еĿ�Ⱥ͸߶ȵİٷֱ� (0~100֮���ʵ��)
	private FacePositionSub eye_left;
	// ��Ӧ�������������꣬x & y ����ֱ��ʾ��ͼƬ�еĿ�Ⱥ͸߶ȵİٷֱ� (0~100֮���ʵ��)
	private FacePositionSub eye_right;
	// ��Ӧ���������������꣬x & y ����ֱ��ʾ��ͼƬ�еĿ�Ⱥ͸߶ȵİٷֱ� (0~100֮���ʵ��)
	private FacePositionSub mouth_left;
	// ��Ӧ�������Ҳ�������꣬x & y ����ֱ��ʾ��ͼƬ�еĿ�Ⱥ͸߶ȵİٷֱ� (0~100֮���ʵ��)
	private FacePositionSub mouth_right;
	// ��Ӧ�����ıǼ����꣬x & y ����ֱ��ʾ��ͼƬ�еĿ�Ⱥ͸߶ȵİٷֱ� (0~100֮���ʵ��)
	private FacePositionSub nose;
	// 0~100֮���ʵ������ʾ��������ĸ߶���ͼƬ�аٷֱ�
	private String height;
	// 0~100֮���ʵ������ʾ��������Ŀ����ͼƬ�аٷֱ�
	private String width;

	public FacePositionSub getCenter() {
		return center;
	}

	public void setCenter(FacePositionSub center) {
		this.center = center;
	}

	public FacePositionSub getEye_left() {
		return eye_left;
	}

	public void setEye_left(FacePositionSub eye_left) {
		this.eye_left = eye_left;
	}

	public FacePositionSub getEye_right() {
		return eye_right;
	}

	public void setEye_right(FacePositionSub eye_right) {
		this.eye_right = eye_right;
	}

	public FacePositionSub getMouth_left() {
		return mouth_left;
	}

	public void setMouth_left(FacePositionSub mouth_left) {
		this.mouth_left = mouth_left;
	}

	public FacePositionSub getMouth_right() {
		return mouth_right;
	}

	public void setMouth_right(FacePositionSub mouth_right) {
		this.mouth_right = mouth_right;
	}

	public FacePositionSub getNose() {
		return nose;
	}

	public void setNose(FacePositionSub nose) {
		this.nose = nose;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

}
