package co.xiaodao.weixin.util;

import co.xiaodao.weixin.json.express.ExpressJson;

import com.google.gson.Gson;

/**
 * ��ݲ�ѯ��ѯ������
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class ExpressUtil {

	public final static String URL_EXPRESS = "http://www.aikuaidi.cn/rest/?key=3a9d4f627d5b42509b119ba3b9a1576e&order={order_num}&id={express_id}";

	/**
	 * errCode ���ش����룺 0���޴��� 1�����KEY��Ч�� 2����ݴ�����Ч�� 3�����ʴ����ﵽ����ȣ�
	 * 4����ѯ���������ش��󼴷���״̬���200, 5������ִ�г���
	 */
	public final static String express_errCode_msg_0 = "�޴���";
	public final static String express_errCode_msg_1 = "���KEY��Ч";
	public final static String express_errCode_msg_2 = "��ݴ�����Ч";
	public final static String express_errCode_msg_3 = "���ʴ����ﵽ�����";
	public final static String express_errCode_msg_4 = "��ѯ���������ش��󼴷���״̬���200";
	public final static String express_errCode_msg_5 = "����ִ�г���";

	/**
	 * status ��������״̬�� 0����ѯ������errCode!=0���� 1�����޼�¼�� 2����;�У� 3�������У� 4����ǩ�գ� 5�����գ�
	 * 6�����Ѽ� 7���˻�
	 */
	public final static String express_status_msg_0 = "��ѯ������errCode!=0��";
	public final static String express_status_msg_1 = "���޼�¼";
	public final static String express_status_msg_2 = "��;��";
	public final static String express_status_msg_3 = "������";
	public final static String express_status_msg_4 = "��ǩ��";
	public final static String express_status_msg_5 = "����";
	public final static String express_status_msg_6 = "���Ѽ�";
	public final static String express_status_msg_7 = "�˻�";
	public final static String express_status_msg_default = "δ֪";

	public static String expressStatusFromCodeToString(String statusCodeStr) {
		int statusCode = Integer.parseInt(statusCodeStr);
		String returnStr = "";
		switch (statusCode) {
		case 0:
			returnStr = express_status_msg_0;
			break;
		case 1:
			returnStr = express_status_msg_1;
			break;
		case 2:
			returnStr = express_status_msg_2;
			break;
		case 3:
			returnStr = express_status_msg_3;
			break;
		case 4:
			returnStr = express_status_msg_4;
			break;
		case 5:
			returnStr = express_status_msg_5;
			break;
		case 6:
			returnStr = express_status_msg_6;
			break;
		case 7:
			returnStr = express_status_msg_7;
			break;
		default:
			returnStr = express_status_msg_default;
			break;
		}
		return returnStr;
	}

	public static ExpressJson jsonToExpress(String json) {
		Gson gson = new Gson();
		ExpressJson expressJson = gson.fromJson(json, ExpressJson.class);
		return expressJson;
	}

	public static void main(String[] args) {
		System.out.println(URL_EXPRESS.replace("{order_num}", "3147403986")
				.replace("{express_id}", "yuantong"));
	}

}
