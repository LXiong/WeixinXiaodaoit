package co.xiaodao.weixin.util;

import java.util.Random;

/**
 * ���ܻظ�������
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class AutoReplyUtil {
	// classpath
	public static final String classpath = AutoReplyUtil.class.getResource("/")
			.getPath().replaceAll("%20", " ");
	// �����洢λ��
	public static String PATH_INDEX = classpath + "index/";

	// ������ʶ
	public static String FIELD_ID = "id";
	// ����
	public static String FIELD_QUESTION = "q";
	// ��
	public static String FIELD_ANSWER = "a";
	// ����
	public static String FIELD_CATEGORY = "c";

	/**
	 * ������� 0~length-1 ֮���ĳ��ֵ
	 * 
	 * @return int
	 */
	public static int getRandomNumber(int length) {
		Random random = new Random();
		return random.nextInt(length);
	}

	public static void main(String[] args) {
		// System.out.println(AutoReplyService.search("���ǳ�"));
		//System.out.println(getRandomNumberForFileName());
	}
}
