package co.xiaodao.weixin.json.face;

/**
 * ��������������
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class FaceAttributeAge {
	// value��ֵΪһ���Ǹ�������ʾ���Ƶ�����
	private String value;
	// range��ʾ�����������������
	private String range;

	public static String getFaceAttributeAge(String value, String range) {
		String returnStr = "  ���䣺" + value + "��" + range + "��";
		return returnStr;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
