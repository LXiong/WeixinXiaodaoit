package co.xiaodao.weixin.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.xiaodao.weixin.db.pojo.MsgCache;
import co.xiaodao.weixin.db.util.MsgCacheDBUtil;
import co.xiaodao.weixin.json.express.ExpressJson;
import co.xiaodao.weixin.json.express.ExpressSingleData;
import co.xiaodao.weixin.util.ExpressUtil;
import co.xiaodao.weixin.util.XiaoDaoUtil;

/**
 * ��ݲ�ѯ�ķ���
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
@SuppressWarnings("unchecked")
public class ExpressService {
	private static Logger log = LoggerFactory.getLogger(ExpressService.class);

	// classpath
	public static final String classpath = ExpressService.class
			.getResource("/").getPath().replaceAll("%20", " ");
	// ��ݴ��������ļ�
	public static String PATH_CONF = classpath + "express-conf.xml";

	public static Map<String, String> expressMap = new HashMap<String, String>();

	// ��̬�������ؿ�ݴ���������Ϣ
	static {
		try {
			// ͨ��SAX����������
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(PATH_CONF));
			// �õ�xml��Ԫ��
			Element root = document.getRootElement();
			// �õ�root�ӽڵ㼯��
			List<Element> urlList = root.elements("express");
			for (Element e : urlList)
				expressMap
						.put(e.attributeValue("name"), e.attributeValue("id"));
		} catch (Exception e) {
			log.error("{}", e);
		}
	}

	public static String queryExpress(String order_num, String expressName, String openId) {
		String express_id = findExpressCodeByName(expressName);
		StringBuffer returnStrBuffer = new StringBuffer();
		returnStrBuffer.append("��ݹ�˾��" + expressName + "\n");
		returnStrBuffer.append("��ݵ��ţ�" + order_num + "\n");
		if (express_id == null) {
			returnStrBuffer.append("û�в�ѯ����ݹ�˾����˶���Ϣ���ٲ�ѯ��");
		} else {
			ExpressJson expressJson = null;
			String requestUrl = ExpressUtil.URL_EXPRESS.replace("{order_num}",
					order_num).replace("{express_id}", express_id);
			String returnJsonStr = XiaoDaoUtil.getJsonByHttp(requestUrl,
					"gb2312");
		//	System.out.println(returnJsonStr);
			expressJson = ExpressUtil.jsonToExpress(returnJsonStr);
			if (expressJson == null) {
				returnStrBuffer.append("û�в�ѯ��������Ϣ����˶���Ϣ���ٲ�ѯ��");
			} else {
				if (expressJson.getErrCode().equals("0")) {
					// 0���޴���
					returnStrBuffer.append("���״̬��" + ExpressUtil.expressStatusFromCodeToString(expressJson.getStatus()) + "\n");
					if (!expressJson.getStatus().equals("1")) {
						returnStrBuffer.append("\n��ݸ��٣�\n");
						List<ExpressSingleData> expressData = expressJson.getData();
						for (ExpressSingleData expressSingleData : expressData) {
							returnStrBuffer.append(expressSingleData.getTime() + "\n");
							returnStrBuffer.append(expressSingleData.getContent() + "\n");
						}
					}
					MsgCache msgCache = MsgCacheDBUtil.getMsgCacheByOpenID(openId);
					if(msgCache != null){
						msgCache.setExpress(order_num + "@" + expressName);
						MsgCacheDBUtil.updateMsgCache(msgCache);
					}else{
						MsgCacheDBUtil.insertMsgCache(new MsgCache(openId,"",order_num + "@" + expressName));
					}
				} else if (expressJson.getErrCode().equals("2")) {
					// 2����ݴ�����Ч��
					returnStrBuffer.append("��ݹ�˾����ȷ����˶���Ϣ���ٲ�ѯ��");
				} else {
					returnStrBuffer.append("�������˳������ˣ��뷴����������΢�źš�cdztop��");
				}
			}
		}
		returnStrBuffer.append("\n\n-------------------\n��Ҫ��ѯ�������\n\n���ͣ����+����@��˾\n���緢�ͣ�\n���3147403986@Բͨ");
		return returnStrBuffer.toString();
	}

	public static String findExpressCodeByName(String expressName) {
		String expressId = null;

		if (expressMap.containsKey(expressName))
			expressId = expressMap.get(expressName);
		else if (expressMap.containsKey(expressName + "���"))
			expressId = expressMap.get(expressName + "���");
		else if (expressMap.containsKey(expressName + "����"))
			expressId = expressMap.get(expressName + "����");
		else if (expressMap.containsKey(expressName + "����"))
			expressId = expressMap.get(expressName + "����");

		return expressId;
	}

	public static void main(String[] args) {
	}
}
