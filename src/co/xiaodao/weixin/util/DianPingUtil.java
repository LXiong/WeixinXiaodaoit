package co.xiaodao.weixin.util;

import java.util.HashMap;
import java.util.Map;

import co.xiaodao.weixin.json.dianping.DianPingJson;
import co.xiaodao.weixin.service.DianPingService;

import com.google.gson.Gson;

/**
 * ���ڵ���������
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class DianPingUtil {

	public static String DIAN_PING_API_URL = "http://api.dianping.com/v1/business/find_businesses";
	public static String DIAN_PING_API_KEY = "446749641";
	public static String DIAN_PING_SECRET = "8ac305dbd2074c8ab29e94f613dc1b8a";
	//public static String MY_DOMAIN = "cdztop.eicp.net";
	// public static String MY_DOMAIN = "xiaodaoit.gotoip2.com";

	/**
	 * ��json��ʽ���̼���Ϣת����java����
	 * 
	 * @param json
	 * @return
	 */
	public static DianPingJson jsonToDianPing(String json) {
		Gson gson = new Gson();
		DianPingJson dianPingJson = gson.fromJson(json, DianPingJson.class);
		return dianPingJson;
	}

	/**
	 * �����������
	 * 
	 * @param latitude
	 * @param longitude
	 * @param category
	 * @return
	 */
	public static Map<String, String> getParamMap(String latitude,
			String longitude, String category) {

		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("format", "json");// �������ݸ�ʽ����ѡֵΪjson��xml���粻���룬Ĭ��ֵΪjson
		paramMap.put("latitude", latitude);// γ�����꣬���뾭������ͬʱ���룬��������ƶ��߱�ѡ��һ����
		paramMap.put("longitude", longitude);// �������꣬����γ������ͬʱ���룬��������ƶ��߱�ѡ��һ����
		paramMap.put("category",
				(category != null && !"".equals(category)) ? category : "��ʳ");// ����������ѡ��Χ�����API���ؽ��
		paramMap.put("limit", "9");// ���ص��̻������Ŀ�����ޣ���Сֵ1�����ֵ20���粻����Ĭ��Ϊ20
		paramMap.put("radius", "2000");// �����뾶����λΪ�ף���Сֵ1�����ֵ5000���粻����Ĭ��Ϊ1000
		// paramMap.put("offset_type", "0");//
		// ƫ�����ͣ�0:δƫ�ƣ�1:�ߵ�����ϵƫ�ƣ�2:��άͼ������ϵƫ�ƣ��粻���룬Ĭ��ֵΪ0
		// paramMap.put("has_coupon", "1");// �����Ƿ����Ż�ȯ��ɸѡ���ص��̻���1:�У�0:û��
		// paramMap.put("has_deal", "1");// �����Ƿ����Ź���ɸѡ���ص��̻���1:�У�0:û��
		// paramMap.put("keyword", "̩����");// �ؼ��ʣ�������Χ�����̻�������ַ����ǩ��
		paramMap.put("sort", "7");// �������1:Ĭ�ϣ�2:�Ǽ������ȣ�3:��Ʒ���۸����ȣ�4:�������۸����ȣ�5:�������۸����ȣ�6:�������������ȣ�7:�봫�뾭γ��������������
		paramMap.put("platform", "2");// �����������ͣ�1:webվ���ӣ���������ҳӦ�ã���2:wapվ���ӣ��������ƶ�Ӧ�ú���������Ӧ�ã����粻���룬Ĭ��ֵΪ1

		return paramMap;
	}

	public static void main(String[] args) {
		DianPingJson dianPingJson = DianPingService.requestApi(
				DIAN_PING_API_URL, DIAN_PING_API_KEY, DIAN_PING_SECRET,
				getParamMap("31.118500", "121.494035", ""));

		System.out.println(dianPingJson.getStatus());
		System.out.println(dianPingJson.getCount());
		System.out.println(dianPingJson.getBusinesses().get(0).getAddress());

	}

}
