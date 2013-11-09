package co.xiaodao.weixin.json.face;

/**
 * ����һϵ�����������Է������
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class FaceAttribute {
	// ����������������value��ֵΪһ���Ǹ�������ʾ���Ƶ�����, range��ʾ�����������������
	private FaceAttributeAge age;
	// �����Ա���������value��ֵΪMale/Female, confidence��ʾ���Ŷ�
	private FaceAttributeGender gender;
	// �������ַ��������value��ֵΪAsian/White/Black, confidence��ʾ���Ŷ�
	private FaceAttributeRace race;

	public static String getFaceAttribute(FaceAttributeAge age,
			FaceAttributeGender gender, FaceAttributeRace race) {
		String returnStr = "";
		returnStr += FaceAttributeAge.getFaceAttributeAge(age.getValue(),
				age.getRange())
				+ "\n";
		returnStr += FaceAttributeGender.getFaceAttributeGender(
				gender.getValue(), gender.getConfidence())
				+ "\n";
		returnStr += FaceAttributeRace.getFaceAttributeRace(race.getValue(),
				race.getConfidence());
		return returnStr;
	}

	public FaceAttributeAge getAge() {
		return age;
	}

	public void setAge(FaceAttributeAge age) {
		this.age = age;
	}

	public FaceAttributeGender getGender() {
		return gender;
	}

	public void setGender(FaceAttributeGender gender) {
		this.gender = gender;
	}

	public FaceAttributeRace getRace() {
		return race;
	}

	public void setRace(FaceAttributeRace race) {
		this.race = race;
	}

}
