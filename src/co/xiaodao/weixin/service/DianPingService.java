package co.xiaodao.weixin.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.xiaodao.weixin.db.util.BaseDBUtil;
import co.xiaodao.weixin.json.dianping.DianPingBusinesses;
import co.xiaodao.weixin.json.dianping.DianPingJson;
import co.xiaodao.weixin.message.pojo.Article;

import co.xiaodao.weixin.util.DianPingUtil;

/**
 * ������ʳ������
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class DianPingService {
	private static Logger log = LoggerFactory.getLogger(DianPingService.class);

	public static String sign(String appKey, String secret,
			Map<String, String> paramMap) {
		// �Բ����������ֵ�����
		String[] keyArray = paramMap.keySet().toArray(new String[0]);
		Arrays.sort(keyArray);

		// ƴ������Ĳ�����-ֵ��
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(appKey);
		for (String key : keyArray) {
			stringBuilder.append(key).append(paramMap.get(key));
		}

		stringBuilder.append(secret);
		String codes = stringBuilder.toString();

		@SuppressWarnings("deprecation")
		String sign = org.apache.commons.codec.digest.DigestUtils.shaHex(codes)
				.toUpperCase();

		return sign;
	}

	public static String getQueryString(String appKey, String secret,
			Map<String, String> paramMap) {
		String sign = sign(appKey, secret, paramMap);

		// ���ǩ��
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("appkey=").append(appKey).append("&sign=")
				.append(sign);
		for (Entry<String, String> entry : paramMap.entrySet()) {
			stringBuilder.append('&').append(entry.getKey()).append('=')
					.append(entry.getValue());
		}
		String queryString = stringBuilder.toString();
		return queryString;
	}

	public static DianPingJson requestApi(String apiUrl, String appKey,
			String secret, Map<String, String> paramMap) {
		String queryString = getQueryString(appKey, secret, paramMap);
		DianPingJson dianPingJson = null;
		StringBuffer response = new StringBuffer();
		HttpClientParams httpConnectionParams = new HttpClientParams();
		httpConnectionParams.setConnectionManagerTimeout(1000);
		HttpClient client = new HttpClient(httpConnectionParams);
		HttpMethod method = new GetMethod(apiUrl);

		try {
			if (StringUtils.isNotBlank(queryString)) {
				// Encode query string with UTF-8
				String encodeQuery = URIUtil.encodeQuery(queryString, "UTF-8");
				log.debug("Encoded Query:" + encodeQuery);
				method.setQueryString(encodeQuery);
			}

			client.executeMethod(method);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					method.getResponseBodyAsStream(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				response.append(line).append(
						System.getProperty("line.separator"));
			}
			reader.close();
		} catch (URIException e) {
			log.error("Can not encode query: " + queryString
					+ " with charset UTF-8. ", e);
		} catch (IOException e) {
			log.error("Request URL: " + apiUrl + " failed. ", e);
		} finally {
			method.releaseConnection();
		}
		if (!"".equals(response.toString())) {
			dianPingJson = DianPingUtil.jsonToDianPing(response.toString());
		}
		return dianPingJson;
	}

	/**
	 * ����DianPingJson��װͼ����Ϣ
	 * 
	 * @return List<Article>
	 */
	public static List<Article> makeArticlesByDianPingJson(String apiUrl,
			String appKey, String secret, Map<String, String> paramMap) {

		DianPingJson dianPingJson = requestApi(apiUrl, appKey, secret, paramMap);
		List<Article> articles = new ArrayList<Article>();

		Article article0 = new Article();
		article0.setTitle("���ܱߵ���ʳ��Ϣ���£�");
		article0.setDescription("");
		article0.setUrl("http://m.dianping.com");
		article0.setPicUrl("http://" + BaseDBUtil.WEB_HOSE
				+ "/WeixinXiaodaoit/image/dianping_logo.jpg");
		articles.add(article0);

		List<DianPingBusinesses> dianPingBusinesses = dianPingJson
				.getBusinesses();

		for (int i = 0; i < dianPingJson.getCount(); i++) {
			Article article = new Article();
			article.setTitle("��" + dianPingBusinesses.get(i).getName() + "��\n"
					+ "��ַ��" + dianPingBusinesses.get(i).getAddress() + "\n"
					+ "�绰��" + dianPingBusinesses.get(i).getTelephone() + "\n"
					+ "�Ǽ����֣�" + dianPingBusinesses.get(i).getAvg_rating()
					+ "  " + "��ζ���ۣ�"
					+ dianPingBusinesses.get(i).getProduct_grade() + "\n"
					+ "�������ۣ�" + dianPingBusinesses.get(i).getDecoration_grade()
					+ "     " + "�������ۣ�"
					+ dianPingBusinesses.get(i).getService_grade());
			article.setDescription("");
			article.setUrl(dianPingBusinesses.get(i).getBusiness_url());
			article.setPicUrl(dianPingBusinesses.get(i).getS_photo_url());
			articles.add(article);
			article = null;
		}

		return articles;
	}

}
