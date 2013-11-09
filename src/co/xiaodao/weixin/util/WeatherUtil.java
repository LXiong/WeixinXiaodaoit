package co.xiaodao.weixin.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import co.xiaodao.weixin.json.weather.WeatherJson;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

/**
 * ����Ԥ����ѯ������
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class WeatherUtil {

	/**
	 * ����Ԥ����ѯ��ַ<br>
	 * ���磬����������Ԥ����Ϣ��http://m.weather.com.cn/data/101260101.html<br>
	 * 3G�棺http://wap.weather.com.cn/wap/weather/101260101.shtml
	 */
	public final static String URL_WEATHER = "http://m.weather.com.cn/data/{city_code}.html";

	// ����ͼƬ��ַ
	public final static String URL_PIC = "http://m.weather.com.cn/img/a{pic_name}.gif";

	// ����Ԥ�����ӵ�ַ
	public static final String WEATHER_URL = "http://mp.weixin.qq.com/mp/appmsg/show?__biz=MjM5NzU0MDIyMQ==&appmsgid=10000022&itemidx=1#wechat_redirect";

	
	/**
	 * ��json��ʽ��������Ϣת����java����
	 * 
	 * @param json
	 *            ������Ϣ
	 * @return
	 */
	public static WeatherJson jsonToWeather(String json) {
		XStream xs = new XStream(new JettisonMappedXmlDriver());
		xs.alias("weatherinfo", WeatherJson.class);
		WeatherJson weather = (WeatherJson) xs.fromXML(json);
		return weather;
	}

	/**
	 * ͨ��diff����ָ�����ڵ�ǰһ�죨��һ�죩
	 * 
	 * @param date
	 *            ָ������
	 * @param diff
	 * @return
	 */
	public static String getDateByDiff(String date, int diff) {
		String dateStr = "";
		try {
			DateFormat df1 = new SimpleDateFormat("yyyy��M��d��");
			Calendar c = Calendar.getInstance();
			c.setTime(df1.parse(date));
			c.add(Calendar.DAY_OF_YEAR, diff);

			DateFormat df2 = new SimpleDateFormat("M��d��");
			dateStr = df2.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	public static void main(String[] args) {
		System.out.println(getDateByDiff("2013��4��8��", +1));
	}

}
