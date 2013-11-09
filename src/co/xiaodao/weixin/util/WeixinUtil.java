package co.xiaodao.weixin.util;

import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.xiaodao.weixin.message.pojo.Article;
import co.xiaodao.weixin.message.response.RespMusicMessage;
import co.xiaodao.weixin.message.response.RespNewsMessage;
import co.xiaodao.weixin.message.response.RespTextMessage;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * ΢�Ź�����
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class WeixinUtil {
	
	public static void main(String[] sre){
		System.out.println(SMU_SHOWLOVE_HELP);
	}

	public static final String TOKEN = "xiaodaoit525396193";

	// �Ƿ���������Ϣ
	public static final String IS_REQUEST = "1";
	public static final String IS_RESPONSE = "0";

	// �Ƿ���
	public static final String IS_SUBSCRIBE = "1";
	public static final String IS_UNSUBSCRIBE = "0";

	// QQ����������ʽ
	public static final String REGEX_QQ_FACE = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";

	// ������Ϣ���ͣ��ı�
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	// ������Ϣ���ͣ�����
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	// ������Ϣ���ͣ�ͼ��
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	// ������Ϣ���ͣ��ı�
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	// ������Ϣ���ͣ�ͼƬ
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	// ������Ϣ���ͣ�����
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	// ������Ϣ���ͣ�����λ��
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	// ������Ϣ���ͣ���Ƶ
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	// ������Ϣ���ͣ��¼�
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	// ��������ͣ�ѧУ
	public static final String QUESTION_CATEGORY_SCHOOL = "school";
	// ��������ͣ�������
	public static final String QUESTION_CATEGORY_TEMP = "temp";

	// ��ӭ��Ϣ
	public static final String MSG_CONTENT_WELCOME = "��ӭ��ע��С��IT��[����]\n\n " + getHelpMsgContent();

	// Ϊ�Ű�ʹ�ð���
	// public static final String MSG_CONTENT_YAAN_HELP =
	// "[����]��Ϊ�Ű�������ָ�ϣ�\n\n�鿴������Ԯ��Ϣ��\n���ͣ���Ԯ+��ַ\n���緢�ͣ���Ԯ�Ű�\nҲ����ֱ�ӷ��ͣ���Ԯ\n\n�鿴����Ѱ����Ϣ��\n���ͣ�Ѱ��+����\n���緢�ͣ�Ѱ�������\nҲ����ֱ�ӷ��ͣ�Ѱ��\n\n�鿴ĳ���Ƿ�ƽ����\n���ͣ�ƽ��+����\n���緢�ͣ�ƽ��������\nҲ����ֱ�ӷ��ͣ�ƽ��";

	// ������ʹ�ð���
	public static final String MSG_CONTENT_MUSIC_HELP = "[ʤ��]�������֡�����ָ�ϣ�\n\n���ͣ�����+����\n���磺��������\n\n���ͣ�����+����@����\n���磺��������@����\n\nΪ�������и��õ����飬����WIFI������ʹ�á�";

	// ������ʹ�ð���
	public static final String MSG_CONTENT_WEATHER_HELP = "[ʤ��]��������������ָ�ϣ�\n\n���ͣ�����+������\n���磺�����Ϻ�";

	// ��ٿ�ʹ�ð���
	public static final String MSG_CONTENT_BAIKE_HELP = "[ʤ��]����ٿơ�����ָ�ϣ�\n\n���ͣ���������Ϣ\n���磺΢��";

	// ������ʹ�ð���
	public static final String MSG_CONTENT_FACE_HELP = "[ʤ��]��������������ָ�ϣ�\n\n����һ�ź�������������ͼƬ�Ϳ����ˡ�";

	// ��ʳ��ʹ�ð���
	public static final String MSG_CONTENT_DIANPING_HELP = "[ʤ��]������ʳ������ָ�ϣ�\n\n����·���Ϣ������Աߵġ�+����ť��Ȼ��ѡ��λ�á�����������λ����Ϣ�Ϳ�������ʳ��[΢Ц]";

	// ��ݵ�ʹ�ð���
	public static final String MSG_CONTENT_EXPRESS_HELP = "[ʤ��]�����ݡ�����ָ�ϣ�\n\n���ͣ����+����@��˾\n���緢�ͣ�\n���3147403986@Բͨ";

	// ��ѧ�ŵ�ʹ�ð���
	public static final String MSG_CONTENT_BIND_STUID_HELP = "���ͣ���+ѧ��@����\n���緢�ͣ�\n��200810314021@cdz";

	// �����ʹ�ð���_ͨ��
	// public static final String MSG_CONTENT_SHMTU_HELP =
	// "���͡�111���ҿս���\n���͡�222�����񹫸�\n���͡�333��ͼ�����\n���͡�444������ͨ��\n���͡�555���Ź���ҵ\n��666������ͼƬ����\n��777������У԰��̬\n��888������֪ͨ����\n��999������������Ѷ";
	public static final String MSG_CONTENT_SHMTU_HELP = "ͬѧ��:�������¹ؼ��ֻ��Ӧ�����־Ϳ�����.[����]\n"
			+ "01�ҿս���  02���񹫸�\n03ͼ�����  11�α���ʾ\n12���޿γ�  13���³ɼ�\n14����ǩ��  15��TA���\n16�绰��ѯ  17����ʳ��";// \n13���³ɼ�(���ѧ��)
	
//	public static final String MSG_CONTENT_SHMTU_HELP = "ͬѧ��:�������¹ؼ��ֻ��Ӧ�����־Ϳ�����.[����]\n"
//		+ "01�ҿս���  02���񹫸�\n03ͼ�����  04����ͨ��\n05��������  06У԰��̬\n07֪ͨ����  08�Ź���ҵ\n09����У��  10����㲥\n11�α���ʾ\n12���޿γ�\n13���³ɼ�[ǿ][����]\n14����ǩ��  15��TA���\n16�绰��ѯ  17����ʳ��";// \n13���³ɼ�(���ѧ��)

	
	// ����ʵ�ù���ʹ�ð���
	public static final String MSG_CONTENT_OTHER_HELP = "---������ʵ�ù��ܡ�---\n�������¹ؼ��ֻ��Ӧ�����ֶ����н����.[����]\n1����   2���   3����\n4��ʳ   5�㲥   6��ʷ\n7�ٿ�   8����   9���";

	// �����ʹ�ð���_�ڲ�
	public static final String MSG_CONTENT_SHMTU_INTERNAL_HELP = "��������Ѷ������ָ��\n\n"
			+ MSG_CONTENT_SHMTU_HELP;

	// �����ʹ�ð���_�ⲿ
	public static final String MSG_CONTENT_SHMTU_INDEX_HELP = "---���Ϻ����´�ѧ��---\n"
			+ MSG_CONTENT_SHMTU_HELP;

	// ����ϰ���Ҽ�̵�ʹ�ð���
	public static final String MSG_CONTENT_SHMTU_CLASSROOM_SHORT_HELP = "���͡��ս������ڡ�\n -->�����ڿ��еĽ���\n"
			+ "���͡��ս���ȫ�졿\n -->��ȫ����еĽ���\n"
			+ "���͡��ս������硿\n -->��������еĽ���\n"
			+ "���͡��ս������硿\n -->��������еĽ���\n"
			+ "���͡��ս������ϡ�\n -->�����Ͽ��еĽ���\n"
			+ "���͡��ս���15��\n -->���һ��ں͵����ڿ��еĽ��ң�Ҳ���Բ������ĽڴΣ����õĽڴ���124567����Ϊ��3���������ʱ�䣩��";

	// ����ϰ����������ʹ�ð���
	
	public static final String MSG_CONTENT_SHMTU_CLASSROOM_OTHER_HELP = "���͡��ս���ȫ�졿\n -->��ȫ����еĽ���\n"
			+ "���͡��ս������硿\n -->��������еĽ���\n"
			+ "���͡��ս������硿\n -->��������еĽ���\n"
			+ "���͡��ս������ϡ�\n -->�����Ͽ��еĽ���\n"
			+ "���͡��ս���15��\n -->���һ��ں͵����ڿ��еĽ��ң�Ҳ���Բ������ĽڴΣ����õĽڴ���124567����Ϊ��3���������ʱ�䣩��";

//	//�����ܿս��Ҳ�ѯ
//	public static final String MSG_CONTENT_SHMTU_CLASSROOM_OTHER_19_HELP = "��ѯ�����ܣ�7��1����7��5�գ��Ŀս���\n\n������ÿ�찲���ĳ����ԣ�\n"
//		+ "��һ����08:20��10:05��\n"
//		+ "�ڶ�����10:25��12:10��\n"
//		+ "��������13:10��14:55��\n"
//		+ "���ĳ���15:15��17:00��\n\n"
//		+ "���͡��ս������ڡ�\n -->�����ڿ��еĽ���\n"
//		+ "���͡��ս���ȫ�졿\n -->��ȫ����еĽ���\n"
//		+ "���͡��ս������硿\n -->��������еĽ���\n"
//		+ "���͡��ս������硿\n -->��������еĽ���\n"
//		+ "���͡��ս������ϡ�\n -->�����Ͽ��еĽ���\n"
//		+ "���͡��ս���12��\n -->��ѯ�����һ���͵ڶ������еĽ��ң�Ҳ���Բ������ĳ��Σ����õĳ�����1234��";
//	
//	public static final String MSG_CONTENT_SHMTU_CLASSROOM_SHORT_19_HELP = "��ѯ�����ܣ�7��1����7��5�գ��Ŀս���\n\n������ÿ�찲���ĳ����ԣ�\n"
//		+ "��һ����08:20��10:05��\n"
//		+ "�ڶ�����10:25��12:10��\n"
//		+ "��������13:10��14:55��\n"
//		+ "���ĳ���15:15��17:00��\n\n"
//		+ "���͡��ս���ȫ�졿\n -->��ȫ����еĽ���\n"
//		+ "���͡��ս������硿\n -->��������еĽ���\n"
//		+ "���͡��ս������硿\n -->��������еĽ���\n"
//		+ "���͡��ս������ϡ�\n -->�����Ͽ��еĽ���\n"
//		+ "���͡��ս���12��\n -->��ѯ�����һ���͵ڶ������еĽ��ң�Ҳ���Բ������ĳ��Σ����õĳ�����1234��";
//	
	
	// ����ϰ���ҵ�ʹ�ð���
	// ���Ƿ������������󣺵�����һ����������ϰ��ʱ�򣬷�����������пΣ������㲻�ò������ҡ�\n\n
	public static final String MSG_CONTENT_SHMTU_CLASSROOM_HELP = "���ҿս��ҡ�����ָ��\n\n"
			+ MSG_CONTENT_SHMTU_CLASSROOM_SHORT_HELP;
	
//	public static final String MSG_CONTENT_SHMTU_CLASSROOM_HELP_19 = "���ҿս��ҡ�����ָ��\n\n"
//		+ MSG_CONTENT_SHMTU_CLASSROOM_SHORT_19_HELP;

	// ͼ���ѯ��ʹ�ð���
	public static final String MSG_CONTENT_SHMTU_LAB_HELP = "��ͼ�����������ָ��\n\n���ͣ�����+�ؼ���\n���磺����iOS";

	// ������������
	public static final String MSG_CONTENT_ON_FOUND_WEATHER_CITY = "��Ĵ��ڡ�{city_name}�����������[����]����С���Ҳ���Ŷ�����ټ��һ���ǲ���д����[΢Ц]";

	// ��Ƶ��Ϣ����ʾ����
	public static final String MSG_CONTENT_VOICE = "����С���Ѿ��յ��ˣ����б�Ҫ��С�����ں�̨�ظ��㡣";

	// �û�������������ʶ�����͵���Ϣʱ
	public static final String MSG_CONTENT_OTHER_TYPE = "�㷢�͵���Ϣ�е����⣬���б�Ҫ��С�����ں�̨�ظ��㡣\n\n����?�鿴����ָ�ϡ�[΢Ц]";

	// �绰��ѯ����
	public final static String SMU_TELEPHONE_COMMON_HELP = "���ͣ��绰+�ؼ���\n\n�ؼ��ֿ����������֡�Ӣ����ĸ��ȫ���򲿷֡�\n\n���緢�ͣ��绰��Ϣ����\n���ͣ��绰xxgc";
	public final static String SMU_TELEPHONE_HELP = "���绰��ѯ������ָ��\n\n"
			+ SMU_TELEPHONE_COMMON_HELP;

	// ��װ���
	public final static String SMU_SHOWLOVE_COMMON_HELP = "---�����ͱ����Ϣ��---\n���ͣ�@��׶���#�������#�����\n���緢�ͣ�@С��#���Ǹ�ʲô��#С��"
			+ "\n\n---���鿴�����Ϣ��---\n�ٷ��ͣ���׶���+����\n���緢�ͣ���׶���С��\n -->�鿴������С����׵���Ϣ��\n�ڷ��ͣ������+����\n���緢�ͣ������С��\n -->�鿴С�����������б����Ϣ��\n�۷��ͣ���׽���\n -->�鿴��������б����Ϣ[��Ƥ]";
	public final static String SMU_SHOWLOVE_HELP = "����TA��ס�����ָ��\n\n"
			+ SMU_SHOWLOVE_COMMON_HELP;

	/**
	 * ����ָ�� С��IT΢�źţ�xiaodaoit
	 * 
	 * @return
	 */
	public static String getHelpMsgContent() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[ʤ��]��С��IT������ָ��:").append("\n\n");
		// buffer.append("���͡�0��Ϊ�Ű�[����]").append("\n");
		buffer.append(MSG_CONTENT_SHMTU_INDEX_HELP).append("\n\n");
		buffer.append("����?�������˵�").append("\n\n");
		buffer.append(MSG_CONTENT_OTHER_HELP);
		// buffer.append("���͡�2����Ҫ���").append("\n");
		// buffer.append("���͡�3������Ԥ��").append("\n");
		// buffer.append("���͡�4�����ǳԻ�").append("\n");
		// buffer.append("���͡�5�����㲥").append("\n");
		// buffer.append("���͡�6������ʷ").append("\n");
		// buffer.append("���͡�7����ٿ�").append("\n");
		// buffer.append("���͡�8��������").append("\n");
		// buffer.append("���͡�9������").append("\n");
		// buffer.append(MSG_CONTENT_SHMTU_INDEX_HELP).append("\n");
		return buffer.toString();
	}
	
	public static String getHelpMsgContentForMenu() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[ʤ��]��С��IT���������:").append("\n\n");
		buffer.append("---���Ϻ����´�ѧ��---\n" +
				"ͬѧ��:�������¹ؼ��ֻ��Ӧ�����־Ϳ�����.[����]\n" +
				"14����ǩ��  15��TA���\n" +
				"16�绰��ѯ  03ͼ����� ").append("\n\n");
		buffer.append("����?�������˵�").append("\n\n");
		buffer.append("---������ʵ�ù��ܡ�---\n�������¹ؼ��ֻ��Ӧ�����ֶ����н����.[����]\n1���� 5�㲥 6��ʷ 7�ٿ�");
		return buffer.toString();
	}

	/**
	 * û���ҵ�����
	 * 
	 * @param song
	 * @param singer
	 * @return
	 */
	public static String notFoundMusic(String song, String singer) {
		if (singer == "") {
			return "������:��" + song + "��\n���׸�û���ҵ�Ŷ.\n�����Ƿ�������ȷ[����]\nҲ���Ի��׸���һ��.";
		} else {
			return "������:��" + song + "��\n�ݳ���:��" + singer
					+ "��\n���׸�û���ҵ�Ŷ.\n�����Ƿ�������ȷ[����]\nҲ���Ի��׸���һ��.";
		}
	}

	/**
	 * ������Ϣ����������ʾ
	 * 
	 * @return
	 */
	public static String pleaseInputMsg(int key) {
		String weixin_please_input = "";
		switch (key) {
		case 1:
			weixin_please_input = "�����Ƿ��͹ؼ�����.\n\n���ͣ�����+�ؼ���\n���磺����iOS";
			break;
		case 2:
			weixin_please_input = "�����Ƿ��͸�������.\n\n���ͣ�����+����\n���磺��������\n\n���ͣ�����+����@����\n���磺��������@����";
			break;
		case 3:
			weixin_please_input = "�����Ƿ��ͳ�������.\n\n���ͣ�����+������\n���磺�����Ϻ�";
			break;
		case 4:
			weixin_please_input = "�㷢�͵���Ϣ������.\n\n���ͣ����+����@��˾\n���緢�ͣ�\n���3147403986@Բͨ";
			break;
		case 5:
			weixin_please_input = "�㷢�͵���Ϣ������.\n\n"
					+ MSG_CONTENT_BIND_STUID_HELP;
			break;
		case 6:
			weixin_please_input = "�㷢�͵���Ϣ������.\n\n"
					+ SMU_SHOWLOVE_COMMON_HELP;
			break;
		}
		return weixin_please_input;
	}

	/**
	 * �ı���Ϣ����ת����xml
	 * 
	 * @param textMessage
	 *            �ı���Ϣ����
	 * @return xml
	 */
	public static String textMessageToXml(RespTextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * ������Ϣ����ת����xml
	 * 
	 * @param musicMessage
	 *            ������Ϣ����
	 * @return xml
	 */
	public static String musicMessageToXml(RespMusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * ͼ����Ϣ����ת����xml
	 * 
	 * @param newsMessage
	 *            ͼ����Ϣ����
	 * @return xml
	 */
	public static String newsMessageToXml(RespNewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * �ж��Ƿ���QQ����
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isQqFace(String content) {
		boolean result = false;
		Pattern p = Pattern.compile(REGEX_QQ_FACE);
		Matcher m = p.matcher(content);
		if (m.matches()) {
			result = true;
		}
		return result;
	}

	/**
	 * ��չxstream��ʹ��֧��CDATA��
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				boolean cdata = false;

				public void startNode(String name,
						@SuppressWarnings("rawtypes") Class clazz) {
					super.startNode(name, clazz);
					cdata = (name.equals("ToUserName")
							|| name.equals("FromUserName")
							|| name.equals("MsgType") || name.equals("Title")
							|| name.equals("Description")
							|| name.equals("MusicUrl")
							|| name.equals("HQMusicUrl") || name.equals("Url")
							|| name.equals("PicUrl") || name.equals("Content"));
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	/**
	 * ��֤ǩ��
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String[] arr = new String[] { WeixinUtil.TOKEN, timestamp, nonce };
		// ��token��timestamp��nonce�������������ֵ�������
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// �����������ַ���ƴ�ӳ�һ���ַ�������sha1����
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = XiaoDaoUtil.byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// ��sha1���ܺ���ַ�������signature�Աȣ���ʶ��������Դ��΢��
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}
}
