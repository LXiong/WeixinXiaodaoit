package co.xiaodao.weixin.util;

/**
 * �ٶ����������ӿڹ�����
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class BaiduMusicUtil {

	// �ٶ����������ĵ�ַ<br>
	// ���磺http://box.zhangmen.baidu.com/x?op=12&count=1&title=ͬ������$$$$$$
	public final static String URL = "http://box.zhangmen.baidu.com/x?op=12&count=1&title={music_title}$${music_author}$$$$";

	// �ٶ������������xml�б�ʾ��¼����Ԫ��
	public final static String ELEMENT_COUNT = "count";

	// �ٶ������������xml�б�ʾ��ͨ���ʵ�Ԫ��
	public final static String ELEMENT_URL = "url";

	// �ٶ������������xml�б�ʾ�߼����ʵ�Ԫ��
	public final static String ELEMENT_DURL = "durl";

	// �ٶ������������xml�б�ʾ������urlԪ��
	public final static String ELEMENT_ENCODE = "encode";

	// �ٶ������������xml�б�ʾ����ǰ��urlԪ��
	public final static String ELEMENT_DECODE = "decode";
}
