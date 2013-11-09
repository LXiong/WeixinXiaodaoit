package co.xiaodao.weixin.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.xiaodao.weixin.util.XiaoDaoUtil;

/**
 * ��ʷ�ϵĽ��������
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class TodayService {

	private static String TODAY_URL = "http://www.rijiben.com/";

	/**
	 * ��ѯ��ʷ�ϵĽ���
	 * 
	 * @return
	 */
	public static List<String> queryToday() {
		List<String> list = null;
		String returnJsonStr = XiaoDaoUtil.getJsonByHttp(TODAY_URL, "UTF-8");
		list = extractTodayInfo(returnJsonStr);
		return list;
	}

	/**
	 * ��html�г�ȡ����ʷ�ϵĽ�����Ϣ
	 * 
	 * @param html
	 * @return
	 */
	private static List<String> extractTodayInfo(String html) {
		List<String> list = null;
		Pattern p = Pattern
				.compile("(.*)(<div class=\"listren\">)(.*?)(</div>)(.*)");
		Matcher m = p.matcher(html);
		if (m.matches()) {
			list = new ArrayList<String>();
			for (String info : m.group(3).split("&nbsp;&nbsp;")) {
				list.add(info.replace("��ͼ��", "").replaceAll("</?[^>]+>", "")
						.trim());
			}
			Collections.reverse(list);
		}
		return list;
	}

	public static void main(String[] args) {
		List<String> list = queryToday();
		// Collections.reverse(list);
		for (String today : list)
			System.out.println(today);
	}
}
