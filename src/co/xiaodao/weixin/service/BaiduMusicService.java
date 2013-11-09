package co.xiaodao.weixin.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.xiaodao.weixin.message.pojo.Music;
import co.xiaodao.weixin.util.BaiduMusicUtil;

/**
 * �ٶ�������������
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class BaiduMusicService {
	private static Logger log = LoggerFactory
			.getLogger(BaiduMusicService.class);

	/**
	 * ����������������
	 * 
	 * @param musicTitle
	 *            ��������
	 * @return
	 */
	public static Music searchMusic(String musicTitle, String musicAuthor) {
		Music music = null;
		// http://box.zhangmen.baidu.com/x?op=12&count=1&title=%E5%AD%98%E5%9C%A8$$$$$$
		// �ٶ����������ĵ�ַ�������еĿո��滻��%20��
		String requestUrl = BaiduMusicUtil.URL
				.replace("{music_title}", musicTitle)
				.replace("{music_author}", musicAuthor).replaceAll(" +", "%20");
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url
					.openConnection();

			httpUrlConn.setDoOutput(false);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();

			InputStream inputStream = httpUrlConn.getInputStream();
			music = parseMusicFromInputStream(inputStream);

			// ���ɹ����������ֺ�������������
			if (null != music) {
				music.setTitle(musicTitle);
				if (!"".equals(musicAuthor))
					music.setDescription("�ݳ���:" + musicAuthor + "\n���ԡ�С��IT��");
				else
					music.setDescription("���ԡ�С��IT��");
			}

			// �ͷ���Դ
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (Exception e) {
			log.error("{}", e);
		}
		return music;
	}

	/**
	 * ���ݰٶ����ַ��ص���������������Ϣ
	 * 
	 * @param inputStream
	 *            ������
	 * @return Music
	 */
	@SuppressWarnings("unchecked")
	private static Music parseMusicFromInputStream(InputStream inputStream) {
		Music music = null;
		try {
			// ͨ��SAX����������
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// �õ�xml��Ԫ��
			Element root = document.getRootElement();
			// count��ʾ�������ĸ�����
			Element countElement = root.element(BaiduMusicUtil.ELEMENT_COUNT);
			String count = countElement.getText();

			// ���������ĸ���������0ʱ
			if (!"0".equals(count)) {
				// ��ͨ����
				List<Element> urlList = root
						.elements(BaiduMusicUtil.ELEMENT_URL);
				// �߼�����
				List<Element> durlList = root
						.elements(BaiduMusicUtil.ELEMENT_DURL);

				// �õ���ͨ���ʵ�url
				String urlEncode = urlList.get(0)
						.element(BaiduMusicUtil.ELEMENT_ENCODE).getText();
				String urlDecode = urlList.get(0)
						.element(BaiduMusicUtil.ELEMENT_DECODE).getText();

				// ��ͨ����url
				String url = urlEncode.substring(0,
						urlEncode.lastIndexOf("/") + 1) + urlDecode;
				if (-1 != urlDecode.lastIndexOf("&"))
					url = urlEncode
							.substring(0, urlEncode.lastIndexOf("/") + 1)
							+ urlDecode
									.substring(0, urlDecode.lastIndexOf("&"));

				// Ĭ������£������ʵ�url = ��Ʒ�ʵ�url
				String durl = url;
				Element durlElement = durlList.get(0).element(
						BaiduMusicUtil.ELEMENT_ENCODE);
				// �������д��ڸ����ʵĽڵ�
				if (null != durlElement) {
					String durlEncode = durlList.get(0)
							.element(BaiduMusicUtil.ELEMENT_ENCODE).getText();
					String durlDecode = durlList.get(0)
							.element(BaiduMusicUtil.ELEMENT_DECODE).getText();
					// �߼�����url
					durl = durlEncode.substring(0,
							durlEncode.lastIndexOf("/") + 1) + durlDecode;
					if (-1 != durlDecode.lastIndexOf("&"))
						durl = durlEncode.substring(0,
								durlEncode.lastIndexOf("/") + 1)
								+ durlDecode.substring(0,
										durlDecode.lastIndexOf("&"));
				}

				music = new Music();
				music.setMusicUrl(url);
				music.setHQMusicUrl(durl);
			}
		} catch (Exception e) {
			log.error("{}", e);
		}

		return music;
	}

	public static void main(String[] args) {
		Music music = searchMusic("����", "");
		System.out.println(music.getTitle());
		System.out.println(music.getDescription());
		System.out.println(music.getMusicUrl());
		System.out.println(music.getHQMusicUrl());
	}
}
