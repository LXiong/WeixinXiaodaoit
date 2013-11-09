package co.xiaodao.weixin.json.face;

/**
 * �������ַ������
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class FaceAttributeRace {
	// value��ֵΪAsian/White/Black
	private String value;
	// confidence��ʾ���Ŷ�
	private String confidence;

	public static String getFaceAttributeRace(String value, String confidence) {
		if (confidence.length() >= 4) {
			confidence = confidence.substring(0, 4);
		}
		if (value.equals("Asian")) {
			value = "������";
		} else if (value.equals("White")) {
			value = "������";
		} else {
			value = "������";
		}
		String returnStr = "  ���֣�" + confidence + "%��" + value;
		return returnStr;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getConfidence() {
		return confidence;
	}

	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}

}
