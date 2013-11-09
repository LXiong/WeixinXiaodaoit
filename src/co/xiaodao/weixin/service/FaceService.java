package co.xiaodao.weixin.service;

import java.util.Collections;
import java.util.List;

import co.xiaodao.weixin.json.face.FaceAttribute;
import co.xiaodao.weixin.json.face.FaceAttributeAge;
import co.xiaodao.weixin.json.face.FaceAttributeGender;
import co.xiaodao.weixin.json.face.FaceAttributeRace;
import co.xiaodao.weixin.json.face.FaceJson;
import co.xiaodao.weixin.json.face.FaceSingle;
import co.xiaodao.weixin.util.FaceUtil;
import co.xiaodao.weixin.util.XiaoDaoUtil;

/**
 * ����������
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */

public class FaceService {

	public static String queryFace(String faceUrl) {

		FaceJson faceJson = null;
		String returnStr = null;
		String requestUrl = FaceUtil.FACE_URL_PRE + faceUrl;
		String returnJsonStr = XiaoDaoUtil.getJsonByHttp(requestUrl, "UTF-8");
		faceJson = FaceUtil.jsonToFace(returnJsonStr);
		if (faceJson == null) {
			returnStr = "�޷���⣬�뻻һ�������Ƚ�������ͼƬ�����ԡ�";
		} else {
			if (faceJson.getFace() == null || faceJson.getFace().size() < 1) {
				returnStr = "û�м�⵽�����������������뻻һ������ͼƬ�����ԡ�";
			} else {
				List<FaceSingle> faceList = faceJson.getFace();
				// ����
				Collections.sort(faceList);
				int faceNum = faceList.size();
				FaceAttribute faceAttribute = null;
				FaceAttributeAge faceAttributeAge = null;
				FaceAttributeGender faceAttributeGender = null;
				FaceAttributeRace faceAttributeRace = null;

				if (faceNum > 1) {
					returnStr = "��⵽���С�" + faceNum + "��������.\n������������Ϊ-->\n";
					int j = 0;
					for (int i = 0; i < faceNum; i++) {
						faceAttribute = faceList.get(i).getAttribute();
						faceAttributeAge = faceAttribute.getAge();
						faceAttributeGender = faceAttribute.getGender();
						faceAttributeRace = faceAttribute.getRace();
						j = i + 1;
						returnStr += "����ڡ�" + j + "��������:\n";
						returnStr += FaceAttribute.getFaceAttribute(
								faceAttributeAge, faceAttributeGender,
								faceAttributeRace);
						returnStr += "\n";

					}
				} else {
					faceAttribute = faceList.get(0).getAttribute();
					faceAttributeAge = faceAttribute.getAge();
					faceAttributeGender = faceAttribute.getGender();
					faceAttributeRace = faceAttribute.getRace();

					returnStr = "��⵽���С�1��������.\n";
					returnStr += FaceAttribute.getFaceAttribute(
							faceAttributeAge, faceAttributeGender,
							faceAttributeRace);

				}

			}
		}
		return returnStr;
	}

	public static void main(String[] args) {
		// System.out
		// .println(queryFace("http://mmsns.qpic.cn/mmsns/eUrup80bIMgrqHk1Cbta22N6NYmg0ZRKaWXJrbFv07b2LMnwNxibdLg/0"));
		// System.out
		// .println(queryFace("http://mmsns.qpic.cn/mmsns/eUrup80bIMgrqHk1Cbta22N6NYmg0ZRKZcAmRfiabDmuxsnVwPTHffg/0"));
		// System.out
		// .println(queryFace("http://mmsns.qpic.cn/mmsns/eUrup80bIMgrqHk1Cbta22N6NYmg0ZRKWh0iaD1CvxPRs8nL11k6xQQ/0"));

		String returnStr = queryFace("http://mmsns.qpic.cn/mmsns/eUrup80bIMgrqHkdd1Cbta22N6NYmg0ZRKcoQeVBWW0QyAND5VORMuhg/0");
		// String returnStr =
		// "����ʷ�ϵĽ�����ʷ�Ľ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ��һ�澵�ӻع˽���ʷ�ϵĽ������ӻع���ʷ�ϵĽ���ʷ�ϵĽ����ҽ���ʷ�ϵĽ������ӻع���ʷ�ϵĽ���ʷ�ϵĽ�����ʷ�ϵĽ���ʷ�ϵĽ����ҽ���ʷ�ϵĽ������ӻع���ʷ�ϵĽ���ʷ�ϵĽ����ϵĽ����ҽ���ʷ�ϵĽ������ӻع���ʷ�ϵĽ���ʷ�ϵĽ����ϵĽ����ҽ���ʷ�ϵĽ������ӻع���ʷ�ϵĽ���ʷ�ϵ��ϵĽ���ʷ�ϵ��ϵĽ���ʷ�ϵ��ϵĽ���ʷ�ϵ��ϵĽ���ʷ�ϵ��ϵĽ���ʷ�ϵ�����ʷ�ϵ�����ʷ�ϵ�������....";
		// System.out.println(returnStr);
		System.out.println("------------------\nlength:" + returnStr.length());
		System.out.println("------------------\nByteLength:"
				+ returnStr.getBytes().length);
		if (returnStr.getBytes().length > 1366) {
			returnStr = XiaoDaoUtil.byteSubstring(returnStr, 1336) + "\n......";
		}
		System.out.println(returnStr);
		System.out.println("------------------\nlength:" + returnStr.length());
		System.out.println("------------------\nByteLength:"
				+ returnStr.getBytes().length);

	}
}
