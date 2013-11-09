package co.xiaodao.weixin.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.xiaodao.weixin.db.util.BaseDBUtil;
import co.xiaodao.weixin.json.weather.WeatherJson;
import co.xiaodao.weixin.message.pojo.Article;
import co.xiaodao.weixin.util.WeatherUtil;
import co.xiaodao.weixin.util.XiaoDaoUtil;

/**
 * ����Ԥ������
 * 
 * @author liufeng
 * @date 2013-03-11
 */
@SuppressWarnings("unchecked")
public class WeatherService {
	private static Logger log = LoggerFactory.getLogger(WeatherService.class);

	// classpath
	public static final String classpath = WeatherService.class
			.getResource("/").getPath().replaceAll("%20", " ");
	// ���д��������ļ�
	public static String PATH_CONF = classpath + "weather-conf.xml";

	public static Map<String, String> cityMap = new HashMap<String, String>();

	// ��̬�������س��д���������Ϣ
	static {
		try {
			// ͨ��SAX����������
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(PATH_CONF));
			// �õ�xml��Ԫ��
			Element root = document.getRootElement();
			// �õ�root�ӽڵ㼯��
			List<Element> urlList = root.elements("city");
			for (Element e : urlList)
				cityMap.put(e.attributeValue("name"), e.attributeValue("id"));
		} catch (Exception e) {
			log.error("{}", e);
		}
	}

	public static String getTodayShmtuWeather() {
		String returnStr = "";
		WeatherJson weather = queryWeather("101020600");
		returnStr += "��������Ԥ����\n���� " + weather.getTemp1() + " "
				+ weather.getWeather1() + "\n���� " + weather.getTemp2() + " "
				+ weather.getWeather2();
		return returnStr;
	}

	/**
	 * ���ݳ��д����ѯ������Ϣ
	 * 
	 * @param cityCode
	 *            ���д���
	 * @return
	 */
	public static WeatherJson queryWeather(String cityCode) {
		WeatherJson weather = null;
		// �������������Ԥ����ַ
		String requestUrl = WeatherUtil.URL_WEATHER.replace("{city_code}",
				cityCode);
		String returnJsonStr = XiaoDaoUtil.getJsonByHttp(requestUrl, "UTF-8");
		weather = WeatherUtil.jsonToWeather(returnJsonStr);
		return weather;
	}

	/**
	 * ���ݳ������Ʋ�ѯ���д���
	 * 
	 * @param cityName
	 *            ��������
	 * @return
	 */
	public static String findCityCodeByName(String cityName) {
		String cityCode = null;

		// ���ͨ���û������������ƥ��������Ƴ�ĩβ�ġ��С������ء��������������족����ĩβ׷�ӡ��С������ء��������������족���ٲ���
		if (cityMap.containsKey(cityName))
			cityCode = cityMap.get(cityName);
		else if (cityMap.containsKey(cityName.replace("��", "")))
			cityCode = cityMap.get(cityName.replace("��", ""));
		else if (cityMap.containsKey(cityName + "��"))
			cityCode = cityMap.get(cityName + "��");
		else if (cityMap.containsKey(cityName.replace("��", "")))
			cityCode = cityMap.get(cityName.replace("��", ""));
		else if (cityMap.containsKey(cityName + "��"))
			cityCode = cityMap.get(cityName + "��");
		else if (cityMap.containsKey(cityName.replace("��", "")))
			cityCode = cityMap.get(cityName.replace("��", ""));
		else if (cityMap.containsKey(cityName + "��"))
			cityCode = cityMap.get(cityName + "��");
		else if (cityMap.containsKey(cityName.replace("��", "")))
			cityCode = cityMap.get(cityName.replace("��", ""));
		else if (cityMap.containsKey(cityName + "��"))
			cityCode = cityMap.get(cityName + "��");
		else if (cityMap.containsKey(cityName.replace("ʡ", "")))
			cityCode = cityMap.get(cityName.replace("ʡ", ""));
		else if (cityMap.containsKey(cityName + "ʡ"))
			cityCode = cityMap.get(cityName + "ʡ");
		return cityCode;
	}

	/**
	 * ����Weather��Ϣ��װͼ����Ϣ
	 * 
	 * @return List<Article>
	 */
	public static List<Article> makeArticlesByWeather(WeatherJson weather, String cityName) {
		List<Article> articles = new ArrayList<Article>();
		// String date_y = weather.getDate_y();
		Article article1 = new Article();

		article1.setTitle("��" + cityName + "��" + "����Ԥ��");

		article1.setDescription("");
		article1.setUrl(WeatherUtil.WEATHER_URL);
		article1.setPicUrl("http://" + BaseDBUtil.WEB_HOSE
				+ "/WeixinXiaodaoit/image/weather_head.jpg");

		// ����ʱ��=18ʱ������Ԥ�����켰�Ժ�ģ�����ʱ��<18ʱ������Ԥ��Ϊ���켰�Ժ��
		int dayDiff = 0;
		if (weather.getFchh().compareTo("18") >= 0) {
			dayDiff = 1;
		}
		String day1 = "";
		String day2 = "";
		String day3 = "";
		if (dayDiff == 0) {
			day1 = "����";
			day2 = "����";
			day3 = "����";
		} else {
			day1 = "����";
			day2 = "����";
			day3 = "�����";
		}

		// ��һ��
		Article article2 = new Article();
		// WeatherUtil.getDateByDiff(date_y, +dayDiff)
		article2.setTitle(day1 + " " + weather.getTemp1() + " "
				+ weather.getWeather1() + " " + weather.getWind1());
		article2.setPicUrl(WeatherUtil.URL_PIC.replace("{pic_name}",
				weather.getImg1()));
		article2.setDescription("");
		article2.setUrl(WeatherUtil.WEATHER_URL);
		// �ڶ���
		Article article3 = new Article();
		// WeatherUtil.getDateByDiff(date_y, +(dayDiff + 1))
		article3.setTitle(day2 + " " + weather.getTemp2() + " "
				+ weather.getWeather2() + " " + weather.getWind2());
		article3.setPicUrl(WeatherUtil.URL_PIC.replace("{pic_name}",
				weather.getImg3()));
		article3.setDescription("");
		article3.setUrl(WeatherUtil.WEATHER_URL);
		// ������
		Article article4 = new Article();
		// WeatherUtil.getDateByDiff(date_y, +(dayDiff + 2))
		article4.setTitle(day3 + " " + weather.getTemp3() + " "
				+ weather.getWeather3() + " " + weather.getWind3());
		article4.setPicUrl(WeatherUtil.URL_PIC.replace("{pic_name}",
				weather.getImg5()));
		article4.setDescription("");
		article4.setUrl(WeatherUtil.WEATHER_URL);
		// ����ָ��
		Article article5 = new Article();
		article5.setTitle("���½��飺" + weather.getIndex_d() + "\n" + "����ָ����"
				+ weather.getIndex_co() + "\n" + "����ָ����"
				+ weather.getIndex_tr());
		article5.setDescription("");
		article5.setUrl(WeatherUtil.WEATHER_URL);
		article5.setPicUrl("");

		// ��ѯ��������
		Article article6 = new Article();
		article6.setTitle("\n��Ҫ��ѯ�������е�������\n\n���ͣ�����+������\n���磺�����Ϻ�");
		article6.setDescription("");
		article6.setUrl(WeatherUtil.WEATHER_URL);
		article6.setPicUrl("");
		
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		articles.add(article4);
		articles.add(article5);
		articles.add(article6);

		return articles;
	}

	public static void main(String[] args) {
		// WeatherJson weather = queryWeather(findCityCodeByName("�Ϻ�"));
		System.out.println(getTodayShmtuWeather());
	}
}
