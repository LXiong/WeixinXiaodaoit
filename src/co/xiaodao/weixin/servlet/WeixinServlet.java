package co.xiaodao.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.xiaodao.weixin.db.pojo.GetUpSign;
import co.xiaodao.weixin.db.pojo.MsgCache;
import co.xiaodao.weixin.db.pojo.Question;
import co.xiaodao.weixin.db.pojo.ShowLove;
import co.xiaodao.weixin.db.pojo.User;
import co.xiaodao.weixin.db.util.ClassroomDBUtil;
import co.xiaodao.weixin.db.util.GetUpSignDBUtil;
import co.xiaodao.weixin.db.util.MessageDBUtil;
import co.xiaodao.weixin.db.util.MsgCacheDBUtil;
import co.xiaodao.weixin.db.util.QuestionDBUtil;
import co.xiaodao.weixin.db.util.ShowLoveDBUtil;
import co.xiaodao.weixin.db.util.UserDBUtil;
import co.xiaodao.weixin.json.weather.WeatherJson;
import co.xiaodao.weixin.message.pojo.Article;
import co.xiaodao.weixin.message.pojo.Music;
import co.xiaodao.weixin.message.request.ReqEventMessage;
import co.xiaodao.weixin.message.request.ReqImageMessage;
import co.xiaodao.weixin.message.request.ReqLocationMessage;
import co.xiaodao.weixin.message.request.ReqTextMessage;
import co.xiaodao.weixin.message.response.RespMusicMessage;
import co.xiaodao.weixin.message.response.RespNewsMessage;
import co.xiaodao.weixin.message.response.RespTextMessage;
import co.xiaodao.weixin.service.BaiduBaikeCrawler;
import co.xiaodao.weixin.service.BaiduMusicService;
import co.xiaodao.weixin.service.BroadcastService;
import co.xiaodao.weixin.service.ClassroomService;
import co.xiaodao.weixin.service.DianPingService;
import co.xiaodao.weixin.service.ExpressService;
import co.xiaodao.weixin.service.FaceService;
import co.xiaodao.weixin.service.GameService;
import co.xiaodao.weixin.service.ShmtuAuthService;
import co.xiaodao.weixin.service.ShmtuService;
import co.xiaodao.weixin.service.TodayService;
import co.xiaodao.weixin.service.WeatherService;
import co.xiaodao.weixin.util.DianPingUtil;
import co.xiaodao.weixin.util.ShmtuUtil;
import co.xiaodao.weixin.util.WeixinUtil;
import co.xiaodao.weixin.util.XiaoDaoUtil;
import co.xiaodao.weixin.util.secret.AES;

/**
 * ��΢�Ź���ƽ̨����
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-29
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class WeixinServlet extends HttpServlet {

	private static final long serialVersionUID = -4267408236898837036L;

	private static Logger log = LoggerFactory.getLogger(WeixinServlet.class);

	/**
	 * ����΢�Ź���ƽ̨����֤<br>
	 * ͨ������signature���������У��:��ȷ�ϴ˴�GET��������΢�ŷ�������ԭ������echostr�������ݣ��������Ч���������ʧ�ܡ�
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// ΢�ż���ǩ��
		String signature = request.getParameter("signature");
		// ʱ���
		String timestamp = request.getParameter("timestamp");
		// �����
		String nonce = request.getParameter("nonce");
		// ����ַ���
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		if (WeixinUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	/**
	 * ������ͨ�û����͵���Ϣ
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		try {
			Map<String, String> requestMap = XiaoDaoUtil.parseXml(request);
			// ���ͷ��ʺ�
			String fromUserName = requestMap.get("FromUserName");
			// �����ʺ�
			String toUserName = requestMap.get("ToUserName");
			// ����ʱ��
			String requestCreateTime = requestMap.get("CreateTime");
			String dbRequestCreateTime = XiaoDaoUtil.getFormatTime(new Date(Long.parseLong(requestCreateTime + "000")),"yyyy-MM-dd HH:mm:ss");
			// ��ϢID
			String msgId = requestMap.get("MsgId");

			// ��Ӧʱ��
			long responseCreateTime = new Date().getTime();
			String dbResponseCreateTime = XiaoDaoUtil.getFormatTime(new Date(responseCreateTime), "yyyy-MM-dd HH:mm:ss");
			// ��Ϣ����
			String msgType = requestMap.get("MsgType");

			// ͼ����Ϣ��XML
			String articleXML = "";

			// �ı���Ϣ
			if (WeixinUtil.REQ_MESSAGE_TYPE_TEXT.equals(msgType)) {
				// ��Ϣ����
				String content = requestMap.get("Content").trim();
				log.info("���ı���Ϣ����{}����{}��", fromUserName, content);

				MessageDBUtil.insertReqTextMsg(new ReqTextMessage(fromUserName, dbRequestCreateTime, msgId, content));

				// �ظ��ı���Ϣ
				RespTextMessage textMessage = new RespTextMessage(toUserName, fromUserName, dbResponseCreateTime, "0", "");

				// ������Ѷ�İ�����Ϣ
				if ("0".equals(content) || "00".equals(content)
						|| "����".equals(content) || "������Ѷ".equals(content)) {
					textMessage
							.setContent(WeixinUtil.MSG_CONTENT_SHMTU_INTERNAL_HELP);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}
				// ʹ�ð���,�������˵�
				else if ("?".equals(content) || "��".equals(content)
						|| "h".equalsIgnoreCase(content)
						|| "����".equals(content)
						|| "help".equalsIgnoreCase(content)) {
					textMessage.setContent(WeixinUtil.getHelpMsgContent());
					out.print(WeixinUtil.textMessageToXml(textMessage));

					MessageDBUtil.insertRespTextMsg(textMessage);
				}
				// ������Ѷ-����ϰ����
				else if ("01".equals(content) || "111".equals(content)
						|| "�ҿս���".equals(content) || "�ս���".equals(content)
						|| "����ϰ����".equals(content) || "����".equals(content)
						|| "��ϰ".equals(content) || "����ϰ".equals(content)) {
					textMessage.setContent(WeixinUtil.MSG_CONTENT_SHMTU_CLASSROOM_HELP);
					//textMessage.setContent(WeixinUtil.MSG_CONTENT_SHMTU_CLASSROOM_HELP_19);
					//textMessage.setContent("ͬѧ�ǣ��ҿս��ҵĹ�����ʱ�رգ�����ѡ����֮���ٿ��š�\n\n-��С��");
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}
				// ������Ѷ-���񹫸�
				else if ("02".equals(content) || "222".equals(content)
						|| "���񹫸�".equals(content) || "����".equals(content)) {
					List<Article> articles = ShmtuService.makeArticlesForJWC();
					RespNewsMessage newsMessage = new RespNewsMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", String.valueOf(articles.size()), articles);
					articleXML = WeixinUtil.newsMessageToXml(newsMessage);
					out.print(articleXML);
					MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
				}
				// ������Ѷ-ͼ�����
				else if ("03".equals(content) || "333".equals(content)
						|| "ͼ�����".equals(content) || "����".equals(content)
						|| "ͼ��ݼ���".equals(content) || "ͼ��".equals(content)
						|| "��ͼ��".equals(content)) {
					textMessage
							.setContent(WeixinUtil.MSG_CONTENT_SHMTU_LAB_HELP);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}
				// ������Ѷ-����ͨ��
/*
				else if ("04".equals(content) || "444".equals(content)
						|| "����ͨ��".equals(content) || "����".equals(content)) {
					List<Article> articles = ShmtuService
							.makeArticlesForNewBook();
					RespNewsMessage newsMessage = new RespNewsMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", String.valueOf(articles.size()), articles);
					articleXML = WeixinUtil.newsMessageToXml(newsMessage);
					out.print(articleXML);
					MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
				}
				// ����ͼƬ����
				else if ("05".equals(content) || "666".equals(content)
						|| "��������".equals(content) || "����".equals(content)
						|| "ͼƬ����".equals(content) || "����ͼƬ����".equals(content)) {
					List<Article> articles = ShmtuService
							.makeArticlesForImageNews();
					RespNewsMessage newsMessage = new RespNewsMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", String.valueOf(articles.size()), articles);
					articleXML = WeixinUtil.newsMessageToXml(newsMessage);
					out.print(articleXML);
					MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
				}
				// ����У�ڶ�̬
				else if ("06".equals(content) || "777".equals(content)
						|| "У԰��̬".equals(content) || "��̬".equals(content)
						|| "����У԰��̬".equals(content)) {
					List<Article> articles = ShmtuService.makeArticlesForNews();
					RespNewsMessage newsMessage = new RespNewsMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", String.valueOf(articles.size()), articles);
					articleXML = WeixinUtil.newsMessageToXml(newsMessage);
					out.print(articleXML);
					MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
				}
				// ����֪ͨ����
				else if ("07".equals(content) || "888".equals(content)
						|| "֪ͨ����".equals(content) || "����֪ͨ����".equals(content)) {
					List<Article> articles = ShmtuService
							.makeArticlesForNotes();
					RespNewsMessage newsMessage = new RespNewsMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", String.valueOf(articles.size()), articles);
					articleXML = WeixinUtil.newsMessageToXml(newsMessage);
					out.print(articleXML);
					MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
				}
				// ������Ѷ-�㼣����Ϣ
				else if ("08".equals(content) || "555".equals(content)
						|| "�Ź���ҵ".equals(content) || "�㼣��".equals(content)
						|| "�Ź���ҵ��Ϣ".equals(content) || "�㼣����Ϣ".equals(content)) {
					List<Article> articles = ShmtuService.makeArticlesForXGJY();
					RespNewsMessage newsMessage = new RespNewsMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", String.valueOf(articles.size()), articles);
					articleXML = WeixinUtil.newsMessageToXml(newsMessage);
					out.print(articleXML);
					MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
				}
				// ������Ѷ-����У��
				else if ("09".equals(content) || "У��".equals(content)
						|| "����У��".equals(content)) {
					List<Article> articles = ShmtuService
							.makeArticlesForNewspaper();
					RespNewsMessage newsMessage = new RespNewsMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", String.valueOf(articles.size()), articles);
					articleXML = WeixinUtil.newsMessageToXml(newsMessage);
					out.print(articleXML);
					MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
				}
				// ������Ѷ-����㲥
				else if ("10".equals(content) || "����㲥".equals(content)) {
					List<Article> articles = ShmtuService
							.makeArticlesForBroadcast();
					RespNewsMessage newsMessage = new RespNewsMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", String.valueOf(articles.size()), articles);
					articleXML = WeixinUtil.newsMessageToXml(newsMessage);
					out.print(articleXML);
					MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
				}
*/
				// �α���ʾ
				else if ("11".equals(content) || "�α�".equals(content)
						|| "��α�".equals(content) || "�α���ʾ".equals(content)) {
					String str[] = ShmtuAuthService
							.getClassSchedule(fromUserName);
					if (!str[0].equals("0")) {
						str[1] = UserDBUtil.getUserDataByOpenID(fromUserName,
								"student_info") + "�㱾ѧ�ڿα����£�\n\n" + str[1];
					}
					str[1] = XiaoDaoUtil.byteSubstring(str[1]);
					textMessage.setContent(str[1]);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}

				// ͨ���γ�
				else if ("12".equals(content) || "���޿γ�".equals(content)
						|| "����".equals(content) || "GPA".equals(content)
						|| "����".equals(content) || "ͨ���γ�".equals(content)) {
					String str[] = ShmtuAuthService.getPassScore(fromUserName);
					if (!str[0].equals("0")) {
						str[1] = UserDBUtil.getUserDataByOpenID(fromUserName,
								"student_info") + "�����޵Ŀγ����£�\n\n" + str[1];
					}
					str[1] = XiaoDaoUtil.byteSubstring(str[1]);
					textMessage.setContent(str[1]);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}

				// ���³ɼ�
				else if ("13".equals(content) || "���".equals(content)
						|| "�����".equals(content) || "��ɼ�".equals(content)
						|| "�ɼ�".equals(content) || "���³ɼ�".equals(content)) {
					String str[] = ShmtuAuthService.getNewScore(fromUserName);
					if (!str[0].equals("0")) {
						str[1] = UserDBUtil.getUserDataByOpenID(fromUserName,
								"student_info") + "�㱾ѧ�ڵĳɼ����£�\n\n" + str[1];
					}
					str[1] = XiaoDaoUtil.byteSubstring(str[1]);
					//System.out.println(str[1] );
					textMessage.setContent(str[1]);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
					
//					textMessage.setContent("���ǲ����³ɼ��Ĺ��ܣ����Ǳ�ѧ�ڵĳɼ�����û�г�������������Ѿ������ĳɼ��������С��һ�£�С������΢�źţ�cdztop");
//					out.print(WeixinUtil.textMessageToXml(textMessage));
//					MessageDBUtil.insertRespTextMsg(textMessage);
				}

				// ����ǩ��
				else if ("14".equals(content) || "����ǩ��".equals(content)
						|| "����".equals(content) || "ǩ��".equals(content)
						|| "�簲".equals(content)) {
					String respStr = "";
					String rankAll = "";
					String rankSomeOne = "";
					String weatherStr = "";
					String bindStr = "";
					String strArr[] = XiaoDaoUtil
							.getNowDateTimeByFormatForGetUpSign();
					if (!UserDBUtil.isUserExist(fromUserName)) {
						User user = new User(fromUserName, "", "",
								dbRequestCreateTime, dbRequestCreateTime,
								WeixinUtil.IS_SUBSCRIBE, "", "", "", "", "",
								"", dbRequestCreateTime + "\n��д��ע��Ϣ\n\n");
						UserDBUtil.insertUser(user);
					}

					if (strArr[1].compareTo("06:00:00") >= 0
							&& strArr[1].compareTo("08:00:00") <= 0) {
						GetUpSign getUpSign = GetUpSignDBUtil
								.getGetUpSignByOpenID(fromUserName, strArr[0]);
						if (getUpSign == null) {
							if (GetUpSignDBUtil.insertGetUpSign(new GetUpSign(
									fromUserName, strArr[0], strArr[1]),
									strArr[0])) {
								getUpSign = GetUpSignDBUtil
										.getGetUpSignByOpenID(fromUserName,
												strArr[0]);
								rankSomeOne = "�𴲳ɹ������Ǻ�������"
										+ getUpSign.getRank()
										+ "���μ�����ǩ����ͬѧ��\n\n";
							} else {
								rankSomeOne = "��ʧ�ܣ�����ϵ������΢�źţ�cdztop\n\n";
							}
						} else {
							rankSomeOne = "����" + getUpSign.getTime()
									+ "�Ѿ�ǩ�����ˣ����Ǻ�������" + getUpSign.getRank()
									+ "���μ�����ǩ����ͬѧ��\n\n";
						}

						if (Integer.parseInt(getUpSign.getRank()) <= 10
								&& UserDBUtil.getUserDataByOpenID(fromUserName,
										"name").equals("")) {
							String bindUrl = ShmtuAuthService.auth_url_bind_pre
									+ fromUserName;
							bindStr = "���л�����������������а񣬵����㻹û�а�ѧ�ţ��������������ȥ�󶨡�\n\n"
									+ "<a href=\""
									+ bindUrl
									+ "\">����>ȥ��ѧ��</a>\n\n�����������������޷���ɰ󶨣��볢����������ַ�ʽ��\n"
									+ WeixinUtil.MSG_CONTENT_BIND_STUID_HELP
									+ "\n\n";

						}
						rankAll = ShmtuService.getTodayAllSignTop(strArr[0]);
						weatherStr = WeatherService.getTodayShmtuWeather();
						respStr = rankAll + rankSomeOne + bindStr + weatherStr;
					} else {
						respStr = "���ǵ�����ǩ��ʱ��������6:00-8:00��ͬѧ���ǰ����˻��ǵ�Ϸ���أ�������Ƥ��";
					}
					textMessage.setContent(respStr);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}

				// ��TA���
				else if ("15".equals(content) || "��TA���".equals(content)
						|| "���".equals(content) || "�����".equals(content)) {
					textMessage.setContent(WeixinUtil.SMU_SHOWLOVE_HELP);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}

				// �绰��ѯ
				else if ("16".equals(content) || "�绰��ѯ".equals(content)
						|| "�绰".equals(content)) {
					textMessage.setContent(WeixinUtil.SMU_TELEPHONE_HELP);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}

				// ����ʳ��
				else if ("17".equals(content) || "����ʳ��".equals(content)
						|| "ʳ��".equals(content)) {
					textMessage.setContent(ShmtuUtil.CANTEEN_HELP);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}

				// ����������Ѷ
//				else if ("999".equals(content) || "����������Ѷ".equals(content)
//						|| "������Ѷ".equals(content)) {
//					textMessage.setContent(ShmtuService.shmtuGetAll());
//					out.print(WeixinUtil.textMessageToXml(textMessage));
//					MessageDBUtil.insertRespTextMsg(textMessage);
//				}

				// ����ʹ�ù���
				else if ("1".equals(content) || "����".equals(content)
						|| "��Ϸ".equals(content) || "����Ϸ".equals(content)
						|| "game".equalsIgnoreCase(content)) {
					List<Article> games = GameService.makeArticlesByGame();
					RespNewsMessage newsMessage = new RespNewsMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", String.valueOf(games.size()), games);
					articleXML = WeixinUtil.newsMessageToXml(newsMessage);
					out.print(articleXML);
					MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
				}
				// ���İ�����Ϣ
				else if ("2".equals(content) || "���".equals(content)
						|| "����".equals(content) || "������".equals(content)
						|| "����".equals(content)
						|| "music".equalsIgnoreCase(content)) {
					textMessage.setContent(WeixinUtil.MSG_CONTENT_MUSIC_HELP);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}
				// ����Ԥ���İ�����Ϣ
				else if ("3".equals(content) || "����".equals(content) || "����Ԥ��".equals(content) || "������".equals(content)) {
					if(MsgCacheDBUtil.isMsgCacheExist(fromUserName)){
						MsgCache msgCache = MsgCacheDBUtil.getMsgCacheByOpenID(fromUserName);
						String msgCacheWeather = msgCache.getWeather();
						if(msgCacheWeather != null && !msgCacheWeather.equals("")){
							String cityCode = WeatherService.findCityCodeByName(msgCacheWeather);
							WeatherJson weather = WeatherService.queryWeather(cityCode);
							// ����������Ϣ��װͼ����Ϣ
							List<Article> articles = WeatherService.makeArticlesByWeather(weather, msgCacheWeather);
							RespNewsMessage newsMessage = new RespNewsMessage(
									toUserName, fromUserName, dbResponseCreateTime,
									"0", String.valueOf(articles.size()), articles);
							articleXML = WeixinUtil.newsMessageToXml(newsMessage);
							out.print(articleXML);
							MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
						}else{
							textMessage.setContent(WeixinUtil.MSG_CONTENT_WEATHER_HELP);
							out.print(WeixinUtil.textMessageToXml(textMessage));
							MessageDBUtil.insertRespTextMsg(textMessage);
						}
					}else{
						textMessage.setContent(WeixinUtil.MSG_CONTENT_WEATHER_HELP);
						out.print(WeixinUtil.textMessageToXml(textMessage));
						MessageDBUtil.insertRespTextMsg(textMessage);
					}
				}
				// ����ʳ�İ�����Ϣ
				else if ("4".equals(content) || "��ʳ".equals(content)
						|| "�Ի�".equals(content) || "����ʳ".equals(content)) {
					textMessage
							.setContent(WeixinUtil.MSG_CONTENT_DIANPING_HELP);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}
				// ���㲥�İ�����Ϣ
				else if ("5".equals(content) || "�㲥".equals(content)
						|| "���㲥".equals(content)) {
					List<Article> broadcasts = BroadcastService
							.makeArticlesByBroadcast();
					RespNewsMessage newsMessage = new RespNewsMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", String.valueOf(broadcasts.size()), broadcasts);
					articleXML = WeixinUtil.newsMessageToXml(newsMessage);
					out.print(articleXML);
					MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
				}
				// ����ʷ
				else if ("6".equals(content) || "��ʷ".equals(content)
						|| "����ʷ".equals(content)) {
					List<String> list = TodayService.queryToday();
					String todayHistory = "";
					if (list != null && list.size() > 0) {
						for (String today : list) {
							todayHistory += today + "\n\n";
						}
						String historyToday = "[ʤ��]��ʷ��һ�澵��\n���ع���ʷ�ϵĽ��졿"
								+ todayHistory;
						// ��ȡ
						historyToday = XiaoDaoUtil.byteSubstring(historyToday);
						textMessage.setContent(historyToday);
					} else {
						textMessage
								.setContent("[ʤ��]��ʷ��һ�澵��\n���ع���ʷ�ϵĽ��졿\n\n����������������\n\n����ϵ΢�źţ�cdztop");
					}
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}
				// ��ٿƵİ�����Ϣ
				else if ("7".equals(content) || "�ٿ�".equals(content)
						|| "��ٿ�".equals(content)) {
					textMessage.setContent(WeixinUtil.MSG_CONTENT_BAIKE_HELP);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);

				}
				// �������İ�����Ϣ
				else if ("8".equals(content) || "����".equals(content)
						|| "������".equals(content)) {
					textMessage.setContent(WeixinUtil.MSG_CONTENT_FACE_HELP);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);

				}
				// ���ݵİ�����Ϣ
				else if ("9".equals(content) || "���".equals(content) || "����".equals(content) || "����".equals(content) || "������".equals(content)) {
					if(MsgCacheDBUtil.isMsgCacheExist(fromUserName)){
						MsgCache msgCache = MsgCacheDBUtil.getMsgCacheByOpenID(fromUserName);
						String msgCacheExpress = msgCache.getExpress();
						if(msgCacheExpress != null && !msgCacheExpress.equals("")){
							String[] expressArr = msgCacheExpress.split("@");
							String order_num = expressArr[0].trim();
							String expressName = msgCacheExpress.substring(expressArr[0].length() + 1).trim();
							textMessage.setContent(ExpressService.queryExpress(order_num, expressName, fromUserName));
							out.print(WeixinUtil.textMessageToXml(textMessage));
							MessageDBUtil.insertRespTextMsg(textMessage);
						}else{
							textMessage.setContent(WeixinUtil.MSG_CONTENT_EXPRESS_HELP);
							out.print(WeixinUtil.textMessageToXml(textMessage));
							MessageDBUtil.insertRespTextMsg(textMessage);
						}
					}else{
						textMessage.setContent(WeixinUtil.MSG_CONTENT_EXPRESS_HELP);
						out.print(WeixinUtil.textMessageToXml(textMessage));
						MessageDBUtil.insertRespTextMsg(textMessage);
					}
				}

				// ��ս���
				else if (content.startsWith("�ս���")) {
					String time = content.substring(3).trim();
					String returnStr = null;
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date(); 
					String DateStr = df.format(date);
					SimpleDateFormat nowTimeDateFm = new SimpleDateFormat(
							"HH:mm");
					String nowTime = nowTimeDateFm.format(date);
					// ��ȡ�ڼ���
					String weekly = ClassroomService.getWeekly(DateStr);

					if (weekly == null) {
						// �ż�ʱ��
						returnStr = "ͬѧ�������Ƿż�ʱ�䣬�뿪ѧ����ʹ�øù��ܰɡ�";
					} 
					else if ((nowTime.compareTo("23:00") >= 0 && nowTime
							.compareTo("24:59") <= 0)
							|| (nowTime.compareTo("00:00") >= 0 && nowTime
									.compareTo("06:00") <= 0)) {
						returnStr = "ͬѧ������ʱ����" + nowTime
								+ "\n��Ҫ��ҹ���ú���Ϣ��������������ܶ���";
					} 
					else {
						List<String> classroomListAllTemp = new ArrayList<String>();
						classroomListAllTemp
								.addAll(ClassroomService.classroomListAll);
						List<String> classroomInUseList = new ArrayList<String>();

						SimpleDateFormat dateXinqi = new SimpleDateFormat(
								"EEEE");
						String zoucistr = "�����ǡ�" + DateStr + "��\n��" + weekly
								+ "��  " + dateXinqi.format(date) + "\n\n";
						String xinqiNum = ClassroomService.getXinQI(date);
						String kongxianStr = "{kongxian}���еĽ��ң�\n\n";
						if (time.equals("����")) {
							String jieChi = ClassroomService.getJieChi(date);
							if (jieChi == null) {
								returnStr = "���ڲ����Ͽ�ʱ�䣬����������Ĺ�������ѯ��\n\n"
										+ WeixinUtil.MSG_CONTENT_SHMTU_CLASSROOM_OTHER_HELP; 
							} else {
								// ֻ����124567�е�һ��
								kongxianStr = kongxianStr.replace("{kongxian}",
										time);
								classroomInUseList.addAll(ClassroomDBUtil
										.getInUseClassroom(xinqiNum + jieChi,
												weekly));

								classroomListAllTemp
										.removeAll(classroomInUseList);
								returnStr = zoucistr
										+ kongxianStr
										+ ClassroomService
												.getClassroomStr(classroomListAllTemp);
							}
						} else if (time.equals("ȫ��") || time.equals("����")) {// 124567
							kongxianStr = kongxianStr.replace("{kongxian}",
									time);
							classroomInUseList.addAll(ClassroomDBUtil
									.getInUseClassroom(xinqiNum + "1", weekly));
							classroomInUseList.addAll(ClassroomDBUtil
									.getInUseClassroom(xinqiNum + "2", weekly));
							classroomInUseList.addAll(ClassroomDBUtil
									.getInUseClassroom(xinqiNum + "4", weekly));
							classroomInUseList.addAll(ClassroomDBUtil
									.getInUseClassroom(xinqiNum + "5", weekly));
							classroomInUseList.addAll(ClassroomDBUtil
									.getInUseClassroom(xinqiNum + "6", weekly));
							classroomInUseList.addAll(ClassroomDBUtil
									.getInUseClassroom(xinqiNum + "7", weekly));

							classroomListAllTemp.removeAll(classroomInUseList);
							returnStr = zoucistr
									+ kongxianStr
									+ ClassroomService
											.getClassroomStr(classroomListAllTemp);

						} else if (time.equals("����")) {// 12
							kongxianStr = kongxianStr.replace("{kongxian}",
									time);
							classroomInUseList.addAll(ClassroomDBUtil
									.getInUseClassroom(xinqiNum + "1", weekly));
							classroomInUseList.addAll(ClassroomDBUtil
									.getInUseClassroom(xinqiNum + "2", weekly));
							classroomListAllTemp.removeAll(classroomInUseList);
							returnStr = zoucistr
									+ kongxianStr
									+ ClassroomService
											.getClassroomStr(classroomListAllTemp);

						} else if (time.equals("����")) {// 45
							kongxianStr = kongxianStr.replace("{kongxian}",
									time);
							classroomInUseList.addAll(ClassroomDBUtil
									.getInUseClassroom(xinqiNum + "4", weekly));
							classroomInUseList.addAll(ClassroomDBUtil
									.getInUseClassroom(xinqiNum + "5", weekly));
							classroomListAllTemp.removeAll(classroomInUseList);
							returnStr = zoucistr
									+ kongxianStr
									+ ClassroomService
											.getClassroomStr(classroomListAllTemp);
						} else if (time.equals("����")) {// 67
							kongxianStr = kongxianStr.replace("{kongxian}",
									time);
							classroomInUseList.addAll(ClassroomDBUtil
									.getInUseClassroom(xinqiNum + "6", weekly));
							classroomInUseList.addAll(ClassroomDBUtil
									.getInUseClassroom(xinqiNum + "7", weekly));
							classroomListAllTemp.removeAll(classroomInUseList);
							returnStr = zoucistr
									+ kongxianStr
									+ ClassroomService
											.getClassroomStr(classroomListAllTemp);
						} else if (ClassroomService.isTrueNumFormat(time)) {
							String jieciTemp = "";
							char[] strArr = time.toCharArray();
							for (int i = 0; i < strArr.length; i++) {
								jieciTemp += strArr[i] + "��";
								classroomInUseList.addAll(ClassroomDBUtil
										.getInUseClassroom(
												xinqiNum + strArr[i], weekly));
							}
							kongxianStr = kongxianStr.replace(
									"{kongxian}",
									"��"
											+ jieciTemp.substring(0,
													jieciTemp.length() - 1)
											+ "���");

							classroomListAllTemp.removeAll(classroomInUseList);
							returnStr = zoucistr
									+ kongxianStr
									+ ClassroomService
											.getClassroomStr(classroomListAllTemp);
						} else {
							// ��ʽ����
							returnStr = "������Ĺ�����ȷ���밴������Ĺ�������ѯ��\n\n"
									+ WeixinUtil.MSG_CONTENT_SHMTU_CLASSROOM_SHORT_HELP;
						}
					}

					textMessage.setContent(returnStr);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}
				
				// ��ս���
//				else if (content.startsWith("�ս���")) {
//					textMessage.setContent("ͬѧ�ǣ��ҿս��ҵĹ�����ʱ�رգ�����ѡ����֮���ٿ��š�\n\n-��С��");
//					out.print(WeixinUtil.textMessageToXml(textMessage));
//					MessageDBUtil.insertRespTextMsg(textMessage);
//					String time = content.substring(3).trim();
//					String returnStr = null;
//					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//					Date date = new Date();
//					String DateStr = df.format(date);
//					SimpleDateFormat nowTimeDateFm = new SimpleDateFormat("HH:mm");
//					String nowTime = nowTimeDateFm.format(date);
//					// ��ȡ�ڼ���
//					//String weekly = "19";
//					if (DateStr.compareTo("2013-07-01") < 0){
//						returnStr = "ͬѧ��ã������ܲ�ѯ�ս��ҽ���7��1����7��5��ʹ�á�";
//					}
//					else if (DateStr.compareTo("2013-07-05") > 0){
//						returnStr = "ͬѧ��ã���ѧ���Ѿ��ż��ˣ��Ȼؼ�������ˣ���Ϣ���졣Ȼ����Ͷ�뵽ѧϰ�У��ú���ϧ��ٵ���������ʱ�䣬" +
//								"ȥ�Ҹ�ʵϰ������ȥ�μ��������ʵ��������ѧϰһЩ����������ѧϰӢ��ȵȡ�";
//					}
//					else if ((nowTime.compareTo("00:00") >= 0 && nowTime.compareTo("05:00") <= 0)) {
//						returnStr = "ͬѧ������ʱ����" + nowTime + "\n��Ҫ��ҹ���ú���Ϣ�����������ٸ�ϰ�ɡ�";
//					} else {
//						List<String> classroomListAllTemp = new ArrayList<String>();
//						classroomListAllTemp.addAll(ClassroomService.classroomListAll);
//						List<String> classroomInUseList = new ArrayList<String>();
//
//						SimpleDateFormat dateXinqi = new SimpleDateFormat("EEEE");
//						String zoucistr = "�����ǡ�" + DateStr + "��\n��19�ܣ������ܣ�\n" + dateXinqi.format(date) + "\n\n";
//						String xinqiNum = ClassroomService.getXinQI(date);
//						String kongxianStr = "{kongxian}���еĽ��ң�\n\n";
//						if (time.equals("����")) {
//							String jieChi = ClassroomService.getJieChi(date);
//							if (jieChi == null) {
//								returnStr = "���ڲ��ǿ���ʱ�䣬����������Ĺ�������ѯ��\n\n" + WeixinUtil.MSG_CONTENT_SHMTU_CLASSROOM_SHORT_19_HELP;
//							} else {
//								// ֻ����1234�е�һ��
//								kongxianStr = kongxianStr.replace("{kongxian}", time);
//								classroomInUseList.addAll(ClassroomDBUtil.getInUseClassroom(xinqiNum + jieChi));
//								classroomListAllTemp.removeAll(classroomInUseList);
//								returnStr = zoucistr + kongxianStr + ClassroomService.getClassroomStr(classroomListAllTemp);
//							}
//						} else if (time.equals("ȫ��") || time.equals("����")) {// 124567
//							kongxianStr = kongxianStr.replace("{kongxian}",time);
//							classroomInUseList.addAll(ClassroomDBUtil.getInUseClassroom(xinqiNum + "1"));
//							classroomInUseList.addAll(ClassroomDBUtil.getInUseClassroom(xinqiNum + "2"));
//							classroomInUseList.addAll(ClassroomDBUtil.getInUseClassroom(xinqiNum + "3"));
//							classroomInUseList.addAll(ClassroomDBUtil.getInUseClassroom(xinqiNum + "4"));
//							classroomListAllTemp.removeAll(classroomInUseList);
//							returnStr = zoucistr + kongxianStr + ClassroomService.getClassroomStr(classroomListAllTemp);
//
//						} else if (time.equals("����")) {// 12
//							kongxianStr = kongxianStr.replace("{kongxian}",time);
//							classroomInUseList.addAll(ClassroomDBUtil.getInUseClassroom(xinqiNum + "1"));
//							classroomInUseList.addAll(ClassroomDBUtil.getInUseClassroom(xinqiNum + "2"));
//							classroomListAllTemp.removeAll(classroomInUseList);
//							returnStr = zoucistr + kongxianStr + ClassroomService.getClassroomStr(classroomListAllTemp);
//
//						} else if (time.equals("����")) {// 34
//							kongxianStr = kongxianStr.replace("{kongxian}",time);
//							classroomInUseList.addAll(ClassroomDBUtil.getInUseClassroom(xinqiNum + "3"));
//							classroomInUseList.addAll(ClassroomDBUtil.getInUseClassroom(xinqiNum + "4"));
//							classroomListAllTemp.removeAll(classroomInUseList);
//							returnStr = zoucistr + kongxianStr + ClassroomService.getClassroomStr(classroomListAllTemp);
//							
//						} else if (time.equals("����")) {// 67
//							kongxianStr = kongxianStr.replace("{kongxian}",time);
//							returnStr = "����������û�а��ſ��ԣ��������н��Ҷ����У�";
//						} else if (ClassroomService.isTrueNumFormat(time)) {
//							String jieciTemp = "";
//							char[] strArr = time.toCharArray();
//							for (int i = 0; i < strArr.length; i++) {
//								jieciTemp += strArr[i] + "��";
//								classroomInUseList.addAll(ClassroomDBUtil.getInUseClassroom(xinqiNum + strArr[i]));
//							}
//							kongxianStr = kongxianStr.replace("{kongxian}","��" + jieciTemp.substring(0,jieciTemp.length() - 1)+ "��");
//
//							classroomListAllTemp.removeAll(classroomInUseList);
//							returnStr = zoucistr + kongxianStr + ClassroomService.getClassroomStr(classroomListAllTemp);
//						} else {
//							// ��ʽ����
//							returnStr = "������Ĺ�����ȷ���밴������Ĺ�������ѯ��\n\n" + WeixinUtil.MSG_CONTENT_SHMTU_CLASSROOM_OTHER_19_HELP;
//						}
//					}
//
//					textMessage.setContent(returnStr);
//					out.print(WeixinUtil.textMessageToXml(textMessage));
//					MessageDBUtil.insertRespTextMsg(textMessage);
//				}

				// ͼ��ݼ���
				
				else if (content.startsWith("����")) {
					String bookNameKey = content.substring(2).trim();
					if ("".equals(bookNameKey)) {
						textMessage.setContent(WeixinUtil.pleaseInputMsg(1));
						out.print(WeixinUtil.textMessageToXml(textMessage));
						MessageDBUtil.insertRespTextMsg(textMessage);
					} else {
						List<Article> articles = ShmtuService
								.makeArticlesForBook(bookNameKey);
						if (articles.size() == 0) {
							textMessage.setContent("������������ؼ��֣�\n��"
									+ bookNameKey + "��\n���Ҳ�����¼��������ؼ��֡�");
							out.print(WeixinUtil.textMessageToXml(textMessage));
							MessageDBUtil.insertRespTextMsg(textMessage);
						} else {
							RespNewsMessage newsMessage = new RespNewsMessage(
									toUserName, fromUserName,
									dbResponseCreateTime, "0",
									String.valueOf(articles.size()), articles);
							articleXML = WeixinUtil
									.newsMessageToXml(newsMessage);
							out.print(articleXML);
							MessageDBUtil.insertRespNewsMsg(newsMessage,
									articleXML);
						}
					}
				}
				

				// ��
				else if (content.startsWith("��")) {
					String returnStr = "";
					String stuid_pwd = content.substring(2).trim();
					String[] stuid_pwd_Arr = stuid_pwd.split("@");
					if ("".equals(stuid_pwd) || stuid_pwd_Arr.length < 2) {
						returnStr = WeixinUtil.pleaseInputMsg(5);
					} else {
						String stuid = stuid_pwd_Arr[0].trim();// ѧ��
						Pattern p1 = Pattern.compile("^20\\d{10}$");
						Matcher m1 = p1.matcher(stuid);
						if (!m1.matches()) {
							returnStr = "�뷢����ȷ��ѧ�š�";
						} else {
							String pwd = "";
							pwd = stuid_pwd.substring(
									stuid_pwd_Arr[0].length() + 1).trim();
							String stuInfo = ShmtuAuthService
									.getStuInfoByHttpGet(stuid, pwd);
							if (stuInfo != null) {
								if (!UserDBUtil.isUserExist(fromUserName)) {
									User user = new User(fromUserName, "", "",
											dbRequestCreateTime,
											dbRequestCreateTime,
											WeixinUtil.IS_SUBSCRIBE, "", "",
											"", "", "", "", dbRequestCreateTime
													+ "\n��д��ע��Ϣ\n\n");
									UserDBUtil.insertUser(user);
								}
								String AESPwd = AES.encryptAES(pwd,
										AES.ENCRYPT_KEY);
								if (UserDBUtil.updateUserForStu(new User(
										fromUserName, "", "", "",
										dbResponseCreateTime, "", "", stuid,
										AESPwd, stuInfo, "", "", ""))) {
									UserDBUtil.updateLog(fromUserName,
											dbResponseCreateTime + "\n��ѧ�ţ�"
													+ stuid + "\n\n");
									returnStr = "��ϲ�㣡�󶨳ɹ��ˡ�";
								} else {
									returnStr = "�������չ��ˣ�����ϵ������΢�źţ�cdztop";
								}
							} else {
								returnStr = "��ʧ�ܣ����ѧ�Ż�������ò���д���Ӵ�����°�һ��Ӵ��";
							}

						}
					}
					textMessage.setContent(returnStr);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}

				// ����
				else if (content.startsWith("����")) {
					// ��������
					String song = content.substring(2).trim();
					if ("".equals(song)) {
						textMessage.setContent(WeixinUtil.pleaseInputMsg(2));
						out.print(WeixinUtil.textMessageToXml(textMessage));

						MessageDBUtil.insertRespTextMsg(textMessage);
					} else {
						String[] songArr = song.split("@");
						// ����
						String musicTitle = songArr[0].trim();
						// ����
						String musicAuthor = "";

						if (songArr.length >= 2) {
							musicAuthor = song.substring(
									songArr[0].length() + 1).trim();
						}

						// ��������
						Music music = BaiduMusicService.searchMusic(musicTitle,
								musicAuthor);
						// û��������
						if (null == music) {
							textMessage.setContent(WeixinUtil.notFoundMusic(
									musicTitle, musicAuthor));
							out.print(WeixinUtil.textMessageToXml(textMessage));

							MessageDBUtil.insertRespTextMsg(textMessage);
						} else {
							// ������Ϣ
							RespMusicMessage musicMessage = new RespMusicMessage(
									toUserName, fromUserName,
									dbResponseCreateTime, "0", music);
							out.print(WeixinUtil
									.musicMessageToXml(musicMessage));

							MessageDBUtil.insertRespMusicMsg(musicMessage);
						}
					}
				}

				// ���ͱ����Ϣ
				else if (content.startsWith("@")) {
					String[] strArr = content.split("#");
					String respStr = "";
					if (strArr.length != 3) {
						respStr = WeixinUtil.pleaseInputMsg(6);
					} else {
						String strArrDate[] = XiaoDaoUtil
								.getNowDateTimeByFormatForGetUpSign();
						String toName = strArr[0].trim().substring(1).trim();
						String loveContent = strArr[1].trim();
						String fromName = strArr[2].trim();
						if (ShowLoveDBUtil.insertShowLove(new ShowLove(
								fromUserName, fromName, toName, loveContent,
								strArrDate[0], dbRequestCreateTime))) {
							respStr = "��׳ɹ���";
						} else {
							respStr = "���ʧ�ܣ�����ϵ������΢�źţ�cdztop";
						}
					}
					textMessage.setContent(respStr);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}

				// �鿴�����Ϣ-��׶���
				else if (content.startsWith("��׶���")) {
					String toName = content.substring(4).trim();
					String respStr = "";
					if (toName.equals("")) {
						respStr = WeixinUtil.pleaseInputMsg(6);
					} else {
						respStr = XiaoDaoUtil.byteSubstring(ShmtuService
								.getShowLoveByNameToStr(toName, false));
					}
					textMessage.setContent(respStr);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}

				// �鿴�����Ϣ-�����
				else if (content.startsWith("�����")) {
					String fromName = content.substring(3).trim();
					String respStr = "";
					if (fromName.equals("")) {
						respStr = WeixinUtil.pleaseInputMsg(6);
					} else {
						respStr = XiaoDaoUtil.byteSubstring(ShmtuService
								.getShowLoveByNameToStr(fromName, true));
					}
					textMessage.setContent(respStr);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}

				// �鿴�����Ϣ-����
				else if (content.equals("��׽���")) {
					String strArr[] = XiaoDaoUtil
							.getNowDateTimeByFormatForGetUpSign();
					textMessage.setContent(XiaoDaoUtil
							.byteSubstring(ShmtuService
									.getTodayAllShowLove(strArr[0])));
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}

				// �绰��ѯ
				else if (content.startsWith("�绰")) {
					// �ؼ���
					String keyword = content.substring(2).trim();
					String respStr = XiaoDaoUtil.byteSubstring(ShmtuService.searchTelephone(keyword));
					textMessage.setContent(respStr);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}

				// ����Ԥ�� ֧�ָ�ʽ��������������������������������
				else if (content.startsWith("����") || content.endsWith("����")) {
					// ��������
					String cityName = "";
					if (content.startsWith("����")) {
						cityName = content.substring(2).replace("��", "");
					} else {
						cityName = content.substring(0, content.length() - 2).replace("��", "");
					}
					// ���ݳ������Ʋ��ҳ��д���
					String cityCode = WeatherService.findCityCodeByName(cityName.trim());
					// �û��������ˡ�������2��
					if ("".equals(cityName)) {
						textMessage.setContent(WeixinUtil.pleaseInputMsg(3));
						out.print(WeixinUtil.textMessageToXml(textMessage));
						MessageDBUtil.insertRespTextMsg(textMessage);
						// δ�ҵ��û�����ĳ�����Ϣ
					} else if (null == cityCode) {
						textMessage.setContent(WeixinUtil.MSG_CONTENT_ON_FOUND_WEATHER_CITY.replace("{city_name}", cityName));
						out.print(WeixinUtil.textMessageToXml(textMessage));
						MessageDBUtil.insertRespTextMsg(textMessage);
					} else {
						MsgCache msgCache = MsgCacheDBUtil.getMsgCacheByOpenID(fromUserName);
						if(msgCache != null){
							msgCache.setWeather(cityName);
							MsgCacheDBUtil.updateMsgCache(msgCache);
						}else{
							MsgCacheDBUtil.insertMsgCache(new MsgCache(fromUserName,cityName,""));
						}
						WeatherJson weather = WeatherService.queryWeather(cityCode);
						// ����������Ϣ��װͼ����Ϣ
						List<Article> articles = WeatherService.makeArticlesByWeather(weather, cityName);

						RespNewsMessage newsMessage = new RespNewsMessage(toUserName, fromUserName, dbResponseCreateTime,
								"0", String.valueOf(articles.size()), articles);

						articleXML = WeixinUtil.newsMessageToXml(newsMessage);
						out.print(articleXML);
						MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
					}
				}

				// ���
				else if (content.startsWith("���")) {
					String express = content.substring(2);
					if (express.split("@").length < 2) {
						textMessage.setContent(WeixinUtil.pleaseInputMsg(4));
						out.print(WeixinUtil.textMessageToXml(textMessage));
						MessageDBUtil.insertRespTextMsg(textMessage);
					} else {
						String[] expressArr = express.split("@");// ����
						String order_num = expressArr[0].trim();// ��ݹ�˾
						String expressName = express.substring(expressArr[0].length() + 1).trim();
						textMessage.setContent(ExpressService.queryExpress(order_num, expressName, fromUserName));
						out.print(WeixinUtil.textMessageToXml(textMessage));
						MessageDBUtil.insertRespTextMsg(textMessage);
					}
				}

				// ����ʳ��-��ܰ
				else if ("171".equals(content) || "��ܰ".equals(content)
						|| "��ܰʳ��".equals(content) || "��ܰ¥ʳ��".equals(content)
						|| "��ܰ¥".equals(content)) {
					List<Article> articles = ShmtuService
							.makeCanteenHXForNewspaper();
					RespNewsMessage newsMessage = new RespNewsMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", String.valueOf(articles.size()), articles);
					articleXML = WeixinUtil.newsMessageToXml(newsMessage);
					out.print(articleXML);
					MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
				}
				// ����ʳ��-����
				else if ("172".equals(content) || "����".equals(content)
						|| "����ʳ��".equals(content) || "����¥ʳ��".equals(content)
						|| "����¥".equals(content)) {
					List<Article> articles = ShmtuService
							.makeCanteenHQForNewspaper();
					RespNewsMessage newsMessage = new RespNewsMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", String.valueOf(articles.size()), articles);
					articleXML = WeixinUtil.newsMessageToXml(newsMessage);
					out.print(articleXML);
					MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
				}
				// ����ʳ��-����
				else if ("173".equals(content) || "����".equals(content)
						|| "����ʳ��".equals(content) || "����¥ʳ��".equals(content)
						|| "����¥".equals(content)) {
					List<Article> articles = ShmtuService
							.makeCanteenHLForNewspaper();
					RespNewsMessage newsMessage = new RespNewsMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", String.valueOf(articles.size()), articles);
					articleXML = WeixinUtil.newsMessageToXml(newsMessage);
					out.print(articleXML);
					MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
				}

				// ����QQ���飨ԭ�����أ�
				else if (WeixinUtil.isQqFace(content)) {
					textMessage.setContent(content);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}

				// ���տ��ֵ�ƮѩЧ��
				else if (content.equals("���տ���")) {
					textMessage.setContent("���տ���[����]");
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				}

				// ����
				else {
					// 1.���������ݿ����������
					String answer = QuestionDBUtil.getAnswer(content);
					// 2.����������ݿ�����û���ҵ�
					if (answer == null || answer.equals("")) {
						// 3.�ӰٶȰٿƵ���ҳ����
						answer = BaiduBaikeCrawler.queryBaike(content, false);
						// 4.����ٶȰٿ���ҳ����������ȡ
						if (answer == null || answer.equals("")) {
							// // 5.�������в���
							// String[] answerArr = AutoReplyService.search(
							// content).split("//");
							// answer = answerArr[AutoReplyUtil
							// .getRandomNumber(answerArr.length)];
							// answer = answer + "..";
							textMessage
									.setContent(WeixinUtil.MSG_CONTENT_OTHER_TYPE);
							textMessage.setFuncFlag("1");
							answer = WeixinUtil.MSG_CONTENT_OTHER_TYPE;
						} else {
							QuestionDBUtil.insertQuestion(new Question(
									"fromUser", content, answer, fromUserName,
									dbRequestCreateTime));
							answer = XiaoDaoUtil.byteSubstring(answer);
							textMessage.setContent(answer);
							textMessage.setFuncFlag("0");
						}

						// // 4.����ٶȰٿ���ҳ����������ȡ
						// if (answer == null || answer.equals("")) {
						// // 5.�������в���
						// String[] answerArr = AutoReplyService.search(
						// content).split("//");
						// answer = answerArr[AutoReplyUtil
						// .getRandomNumber(answerArr.length)];
						// answer = answer + "..";
						// } else {
						// QuestionDBUtil.insertQuestion(new Question(
						// "fromUser", content, answer, fromUserName,
						// dbRequestCreateTime));
						// }
						// // 6.����������ҵ���
						// if
						// (!answer.equals(WeixinUtil.MSG_CONTENT_OTHER_TYPE)) {
						// // ��ȡ
						// answer = XiaoDaoUtil.byteSubstring(answer);
						// textMessage.setContent(answer);
						// textMessage.setFuncFlag("0");
						// } else {
						// textMessage
						// .setContent(WeixinUtil.MSG_CONTENT_OTHER_TYPE);
						// textMessage.setFuncFlag("1");
						// }
					} else {
						// ��ȡ
						answer = XiaoDaoUtil.byteSubstring(answer);
						textMessage.setContent(answer + ".");
						textMessage.setFuncFlag("0");
					}
					out.print(WeixinUtil.textMessageToXml(textMessage));
					// log.info("��Ӧ����Ϣ����{}����{}����{}��", new Object[] {
					// fromUserName,
					// content, answer });
					MessageDBUtil.insertRespTextMsg(textMessage);
				}
			}

			// ����λ��
			else if (WeixinUtil.REQ_MESSAGE_TYPE_LOCATION.equals(msgType)) {
				// λ����Ϣ
				String label = requestMap.get("Label");
				// ��ͼ���Ŵ�С
				String scale = requestMap.get("Scale");
				// γ��
				String lat = requestMap.get("Location_X");
				// ����
				String lng = requestMap.get("Location_Y");

				log.info("��λ����Ϣ����{}�����ȡ�{}��γ�ȡ�{}��λ�á�{}��", new Object[] {
						fromUserName, lng, lat, label });

				MessageDBUtil.insertReqLocationMsg(new ReqLocationMessage(
						fromUserName, dbRequestCreateTime, msgId, lat, lng,
						scale, label));

				List<Article> dianPingArticles = DianPingService
						.makeArticlesByDianPingJson(
								DianPingUtil.DIAN_PING_API_URL,
								DianPingUtil.DIAN_PING_API_KEY,
								DianPingUtil.DIAN_PING_SECRET,
								DianPingUtil.getParamMap(lat, lng, ""));

				// �ܱ�δ��������ʳ��Ϣ
				if (null == dianPingArticles || dianPingArticles.size() < 2) {
					RespTextMessage textMessage = new RespTextMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", "");
					textMessage
							.setContent("�����2���ﷶΧ��û����������ʳŶ�����ٴη������λ�ã��򵽱�����Ŷ��");
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);
				} else {
					// ������ʳ��Ϣ��װͼ����Ϣ
					RespNewsMessage newsMessage = new RespNewsMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", String.valueOf(dianPingArticles.size()),
							dianPingArticles);
					articleXML = WeixinUtil.newsMessageToXml(newsMessage);
					out.print(articleXML);
					MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
				}
			}

			// ͼƬ
			else if (WeixinUtil.REQ_MESSAGE_TYPE_IMAGE.equals(msgType)) {
				String picUrl = requestMap.get("PicUrl");
				log.info("��ͼƬ��Ϣ����{}����{}��", fromUserName, picUrl);
				MessageDBUtil.insertReqImageMsg(new ReqImageMessage(
						fromUserName, dbRequestCreateTime, msgId, picUrl));

				String returnStr = FaceService.queryFace(picUrl);
				// ��ȡ
				returnStr = XiaoDaoUtil.byteSubstring(returnStr);
				// �ظ��ı���Ϣ
				RespTextMessage textMessage = new RespTextMessage(toUserName,
						fromUserName, dbResponseCreateTime, "0", returnStr);
				out.print(WeixinUtil.textMessageToXml(textMessage));
				MessageDBUtil.insertRespTextMsg(textMessage);
			}

			// ��Ƶ
			else if (WeixinUtil.REQ_MESSAGE_TYPE_VOICE.equals(msgType)) {
				log.info("����Ƶ��Ϣ����{}��", fromUserName);

				ReqTextMessage reqTextMessage = new ReqTextMessage(
						fromUserName, dbRequestCreateTime, msgId, "��Ƶ��Ϣ");
				reqTextMessage.setMsgType(WeixinUtil.REQ_MESSAGE_TYPE_VOICE);
				MessageDBUtil.insertReqTextMsg(reqTextMessage);

				// �ظ��ı���Ϣ
				RespTextMessage textMessage = new RespTextMessage(toUserName,
						fromUserName, dbResponseCreateTime, "0",
						WeixinUtil.MSG_CONTENT_VOICE);
				out.print(WeixinUtil.textMessageToXml(textMessage));
				MessageDBUtil.insertRespTextMsg(textMessage);
			}

			// �¼�
			else if (WeixinUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {
				String event = requestMap.get("Event");
				String eventKey = requestMap.get("EventKey");

				MessageDBUtil.insertReqEventMsg(new ReqEventMessage(
						fromUserName, dbRequestCreateTime, event, eventKey));

				if ("subscribe".equals(event)) {
					log.info("��������Ϣ����{}��", fromUserName);
					// �ظ��ı���Ϣ
					RespTextMessage textMessage = new RespTextMessage(
							toUserName, fromUserName, dbResponseCreateTime,
							"0", WeixinUtil.MSG_CONTENT_WELCOME);
					out.print(WeixinUtil.textMessageToXml(textMessage));
					MessageDBUtil.insertRespTextMsg(textMessage);

					User user = null;
					if (UserDBUtil.isUserExist(fromUserName)) {
						user = new User(fromUserName, "", "", "",
								dbRequestCreateTime, WeixinUtil.IS_SUBSCRIBE,
								"", "", "", "", "", "", "");
						UserDBUtil.updateUser(user);
						UserDBUtil.updateLog(fromUserName, dbRequestCreateTime
								+ "\n���¹�ע\n\n");
					} else {
						user = new User(fromUserName, "", "",
								dbRequestCreateTime, dbRequestCreateTime,
								WeixinUtil.IS_SUBSCRIBE, "", "", "", "", "",
								"", dbRequestCreateTime + "\n��һ�ι�ע\n\n");
						UserDBUtil.insertUser(user);
					}
				}
				// unsubscribe(ȡ������)
				else if ("unsubscribe".equals(event)) {
					User user = null;
					if (UserDBUtil.isUserExist(fromUserName)) {
						user = new User(fromUserName, "", "", "",
								dbRequestCreateTime, WeixinUtil.IS_UNSUBSCRIBE,
								"", "", "", "", "", "", "");
						UserDBUtil.updateUser(user);
						UserDBUtil.updateLog(fromUserName, dbRequestCreateTime
								+ "\nȡ����ע\n\n");
					} else {
						user = new User(fromUserName, "", "",
								dbRequestCreateTime, dbRequestCreateTime,
								WeixinUtil.IS_UNSUBSCRIBE, "", "", "", "", "",
								"", dbRequestCreateTime + "\nȡ����ע\n\n");
						UserDBUtil.insertUser(user);
					}
				} else if ("CLICK".equals(event)) {
					RespTextMessage textMessage = new RespTextMessage(toUserName, fromUserName, dbResponseCreateTime, "0", "");
					if(eventKey.equals("11")){//��������2
						textMessage.setContent(WeixinUtil.MSG_CONTENT_MUSIC_HELP);
						out.print(WeixinUtil.textMessageToXml(textMessage));
						MessageDBUtil.insertRespTextMsg(textMessage);
					}else if(eventKey.equals("12")){//��������3
						if(MsgCacheDBUtil.isMsgCacheExist(fromUserName)){
							MsgCache msgCache = MsgCacheDBUtil.getMsgCacheByOpenID(fromUserName);
							String msgCacheWeather = msgCache.getWeather();
							if(msgCacheWeather != null && !msgCacheWeather.equals("")){
								String cityCode = WeatherService.findCityCodeByName(msgCacheWeather);
								WeatherJson weather = WeatherService.queryWeather(cityCode);
								// ����������Ϣ��װͼ����Ϣ
								List<Article> articles = WeatherService.makeArticlesByWeather(weather, msgCacheWeather);
								RespNewsMessage newsMessage = new RespNewsMessage(
										toUserName, fromUserName, dbResponseCreateTime,
										"0", String.valueOf(articles.size()), articles);
								articleXML = WeixinUtil.newsMessageToXml(newsMessage);
								out.print(articleXML);
								MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
							}else{
								textMessage.setContent(WeixinUtil.MSG_CONTENT_WEATHER_HELP);
								out.print(WeixinUtil.textMessageToXml(textMessage));
								MessageDBUtil.insertRespTextMsg(textMessage);
							}
						}else{
							textMessage.setContent(WeixinUtil.MSG_CONTENT_WEATHER_HELP);
							out.print(WeixinUtil.textMessageToXml(textMessage));
							MessageDBUtil.insertRespTextMsg(textMessage);
						}
					}else if(eventKey.equals("13")){//������ʳ4
						textMessage.setContent(WeixinUtil.MSG_CONTENT_DIANPING_HELP);
						out.print(WeixinUtil.textMessageToXml(textMessage));
						MessageDBUtil.insertRespTextMsg(textMessage);
					}else if(eventKey.equals("14")){//�����9
						if(MsgCacheDBUtil.isMsgCacheExist(fromUserName)){
							MsgCache msgCache = MsgCacheDBUtil.getMsgCacheByOpenID(fromUserName);
							String msgCacheExpress = msgCache.getExpress();
							if(msgCacheExpress != null && !msgCacheExpress.equals("")){
								String[] expressArr = msgCacheExpress.split("@");
								String order_num = expressArr[0].trim();
								String expressName = msgCacheExpress.substring(expressArr[0].length() + 1).trim();
								textMessage.setContent(ExpressService.queryExpress(order_num, expressName, fromUserName));
								out.print(WeixinUtil.textMessageToXml(textMessage));
								MessageDBUtil.insertRespTextMsg(textMessage);
							}else{
								textMessage.setContent(WeixinUtil.MSG_CONTENT_EXPRESS_HELP);
								out.print(WeixinUtil.textMessageToXml(textMessage));
								MessageDBUtil.insertRespTextMsg(textMessage);
							}
						}else{
							textMessage.setContent(WeixinUtil.MSG_CONTENT_EXPRESS_HELP);
							out.print(WeixinUtil.textMessageToXml(textMessage));
							MessageDBUtil.insertRespTextMsg(textMessage);
						}
					}else if(eventKey.equals("15")){//�������8
						textMessage.setContent(WeixinUtil.MSG_CONTENT_FACE_HELP);
						out.print(WeixinUtil.textMessageToXml(textMessage));
						MessageDBUtil.insertRespTextMsg(textMessage);
					}else if(eventKey.equals("21")){//�ҿս���01
						textMessage.setContent(WeixinUtil.MSG_CONTENT_SHMTU_CLASSROOM_HELP);
						//textMessage.setContent(WeixinUtil.MSG_CONTENT_SHMTU_CLASSROOM_HELP_19);
						//textMessage.setContent("ͬѧ�ǣ��ҿս��ҵĹ�����ʱ�رգ�����ѡ����֮���ٿ��š�\n\n-��С��");
						out.print(WeixinUtil.textMessageToXml(textMessage));
						MessageDBUtil.insertRespTextMsg(textMessage);
					}else if(eventKey.equals("22")){//���񹫸�02
						List<Article> articles = ShmtuService.makeArticlesForJWC();
						RespNewsMessage newsMessage = new RespNewsMessage(toUserName, fromUserName, dbResponseCreateTime, "0", String.valueOf(articles.size()), articles);
						articleXML = WeixinUtil.newsMessageToXml(newsMessage);
						out.print(articleXML);
						MessageDBUtil.insertRespNewsMsg(newsMessage, articleXML);
					}else if(eventKey.equals("23")){//�ҵĿα�11
						String str[] = ShmtuAuthService.getClassSchedule(fromUserName);
						if (!str[0].equals("0")) {
							str[1] = UserDBUtil.getUserDataByOpenID(fromUserName, "student_info") + "�㱾ѧ�ڿα����£�\n\n" + str[1];
						}
						str[1] = XiaoDaoUtil.byteSubstring(str[1]);
						textMessage.setContent(str[1]);
						out.print(WeixinUtil.textMessageToXml(textMessage));
						MessageDBUtil.insertRespTextMsg(textMessage);
					}else if(eventKey.equals("24")){//���޿γ�12
						String str[] = ShmtuAuthService.getPassScore(fromUserName);
						if (!str[0].equals("0")) {
							str[1] = UserDBUtil.getUserDataByOpenID(fromUserName, "student_info") + "�����޵Ŀγ����£�\n\n" + str[1];
						}
						str[1] = XiaoDaoUtil.byteSubstring(str[1]);
						textMessage.setContent(str[1]);
						out.print(WeixinUtil.textMessageToXml(textMessage));
						MessageDBUtil.insertRespTextMsg(textMessage);
					}else if(eventKey.equals("25")){//���³ɼ�13
						String str[] = ShmtuAuthService.getNewScore(fromUserName);
						if (!str[0].equals("0")) {
							str[1] = UserDBUtil.getUserDataByOpenID(fromUserName, "student_info") + "�㱾ѧ�ڵĳɼ����£�\n\n" + str[1];
						}
						str[1] = XiaoDaoUtil.byteSubstring(str[1]);
						textMessage.setContent(str[1]);
						out.print(WeixinUtil.textMessageToXml(textMessage));
						MessageDBUtil.insertRespTextMsg(textMessage);
					}else if(eventKey.equals("30")){//�������h
						textMessage.setContent(WeixinUtil.getHelpMsgContentForMenu());
						out.print(WeixinUtil.textMessageToXml(textMessage));
						MessageDBUtil.insertRespTextMsg(textMessage);
					}

				}
			}

			// ��������
			else {
				System.out.println(msgType);
				log.info("������δ֪���͵���Ϣ����{}��", fromUserName);
				// �ظ��ı���Ϣ
				// RespTextMessage textMessage = new RespTextMessage(toUserName,
				// fromUserName, dbResponseCreateTime, "1",
				// WeixinUtil.MSG_CONTENT_OTHER_TYPE);
				// out.print(WeixinUtil.textMessageToXml(textMessage));
				// MessageDBUtil.insertRespTextMsg(textMessage);
			}

		} catch (Exception e) {
			log.error("{}", e);
		}

		out.close();
		out = null;
	}

	public static void main(String[] args) {

	}

}
