package co.xiaodao.weixin.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;

import org.apache.commons.httpclient.methods.GetMethod;

import org.apache.commons.httpclient.params.HttpMethodParams;

import co.xiaodao.weixin.db.pojo.User;
import co.xiaodao.weixin.db.util.BaseDBUtil;
import co.xiaodao.weixin.db.util.UserDBUtil;
import co.xiaodao.weixin.message.pojo.ClassSchedule;
import co.xiaodao.weixin.message.pojo.NewScore;
import co.xiaodao.weixin.message.pojo.PassScore;
import co.xiaodao.weixin.util.WeixinUtil;

public class ShmtuAuthService {

	// ���³ɼ�
	public static String latest_score = "http://jwc.shmtu.edu.cn:7778/pls/wwwbks/bkscjcx.curscopre?jym2005=11875.214065059998";
	// ѧ����Ϣ
	// public static String student_status =
	// "http://jwc.shmtu.edu.cn:7778/pls/wwwbks/bks_xj.xjcx?jym2005=6187.330176809112";
	// �α�
	public static String class_schedule = "http://jwc.shmtu.edu.cn:7778/pls/wwwbks/xk.CourseView?jym2005=12696.010737745353";
	// ͨ���ɼ�
	public static String pass_score = "http://jwc.shmtu.edu.cn:7778/pls/wwwbks/bkscjcx.yxkc?jym2005=12179.703070423186";
	// ���³ɼ�
	public static String new_score = "http://jwc.shmtu.edu.cn:7778/pls/wwwbks/bkscjcx.curscopre?jym2005=11875.214065059998";

	// ��֤�ӿ�
	public static String auth_url = "http://jwc.shmtu.edu.cn:7778/pls/wwwbks/bks_login2.login?stuid={stuid}&pwd={pwd}";

	public static String auth_url_short = "http://jwc.shmtu.edu.cn:7778/pls/wwwbks/bks_login2.login";

	// ��ҳ�󶨵�URL
	public static String auth_url_bind_pre = "http://" + BaseDBUtil.WEB_HOSE
			+ "/WeixinXiaodaoit/bind/auth.jsp?oauth=";

	// public static String auth_url_pass_cdz = auth_url.replace("{stuid}",
	// "200810314021").replace("{pwd}", "woaishmtu");
	// public static String auth_url_pass_zjl = auth_url.replace("{stuid}",
	// "200910314077").replace("{pwd}", "zjl5967786");

	// ͨ����֤֮����ض���
	public static String pass_auth_redirect = "http://jwc.shmtu.edu.cn:7778/pls/wwwbks/bks_login2.loginmessage";

	public static void main(String[] args) throws IOException {
		// System.out.println(getContentAfterAuthByHttpGet("200810314021",
		// "123",
		// pass_score));
		//String[] str = getPassScore("ofY_njiagXqwZCwbjFLoaaXV4erc");
		String[] str = getNewScore("ofY_njl1Sy8YbE7iJfj7k3SfOI80");
		System.out.println(str[1]);

	}
	
	//��ȡ���³ɼ�
	public static String[] getNewScore(String openID) {
		String strAll[] = goToBind(openID, new_score);
		if (!strAll[0].equals("0")) {
			Pattern p0 = Pattern.compile("(.*)(��ע</p></td>)(.*)(VALUE=\"��ʾ����\")(.*)");
			Matcher m0 = p0.matcher(strAll[1]);
			
			Pattern p3 = Pattern.compile("(.*)(<td width=\"112\" height=\"20\" class=td_biaogexian><p align=\"center\">)(.*)(</p>)(.*)");
			Pattern p4 = Pattern.compile("(.*)(<td width=\"200\" height=\"20\" class=td_biaogexian><p align=\"center\">)(.*)(</p>)(.*)");
			Matcher m3 = null;
			Matcher m4 = null;
			int i = 0;
			String strTemp = "";
			List<NewScore> listNewScore = new ArrayList<NewScore>();
			
			if (m0.matches()) {
				for (String info : m0.group(3).split("<TR>")) {
					i = 0;
					NewScore newScore = new NewScore();
					for (String info2 : info.split("</td>")) {
						i++;
						m3 = p3.matcher(info2);
						m4 = p4.matcher(info2);
						if (m3.matches()) {
							strTemp = m3.group(3).replaceAll("&nbsp;", " ").trim();
							//System.out.println(m3.group(3));
						} else if (m4.matches()) {
							strTemp = m4.group(3).replaceAll("&nbsp;", " ").trim();
							//System.out.println(m4.group(3));
						}
						switch (i) {
						case 3:
							newScore.setClass_name(strTemp);
							break;
						case 5:
							newScore.setXf(strTemp);
							break;
						case 7:
							newScore.setScore(strTemp);
							break;
						}
					}
					listNewScore.add(newScore);
				}
			}
			String returnStr = "";
			if(listNewScore.size() != 0){
				listNewScore.remove(0);
				for (NewScore n : listNewScore) {
					returnStr += n.getClass_name()  + "\nѧ��:" + n.getXf() + "\n�ɼ�:" + n.getScore() + "\n\n";
				}
			}else{
				returnStr = "�㱾ѧ��û�пγ̡�";
			}
			strAll[1] = returnStr;
			//System.out.println(returnStr);
		}	
		return strAll;
	}
	
	// ��ȡͨ���γ�
	public static String[] getPassScore(String openID) {
		String strAll[] = goToBind(openID, pass_score);
		if (!strAll[0].equals("0")) {

			String xfTotal = "";
			Pattern p0 = Pattern
					.compile("(.*)(<B>ѧλ����:</B>)(.*)(<font color=red>)(.*)(</font>)(.*)(<B>    ͳ��ʱ��:</B>)(.*)(<font color=red>)(.*)(</font>)(.*)(<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=table_biankuan>)(.*)(��ѧ�ƻ��ڣ��γ̺�һ�£�ͨ���γ̳ɼ���)(.*)");
			Matcher m0 = p0.matcher(strAll[1]);
			if (m0.matches() && !m0.group(5).trim().equals("null")) {
				xfTotal = "ѧλ����:" + m0.group(5).trim() + "\nͳ��ʱ��:"
						+ m0.group(11).trim() + "\n\n";
			}

			String strAll2 = null;
			Pattern p1 = Pattern
					.compile("(.*)(��ѧ�ƻ��ڣ��γ̺�һ�£�ͨ���γ̳ɼ���)(.*)(��ѧ�ƻ��⣨�γ̺Ų�һ�£�---ͨ���γ̳ɼ���)(.*)");
			Matcher m1 = p1.matcher(strAll[1]);
			if (m1.matches()) {
				strAll2 = m1.group(3);
			}
			Pattern p2 = Pattern
					.compile("(.*)(<td  height=\"25\"  class=td_hz_bj><p align=\"center\">��ע</p></td>)(.*)(<B>   �ۼ�ѧ�֣�</B>)(.*)");
			Matcher m2 = p2.matcher(strAll2);
			Pattern p3 = Pattern
					.compile("(.*)(<td width=\"112\" height=\"20\" class=td_biaogexian><p align=\"center\">)(.*)(</p>)(.*)");
			Pattern p4 = Pattern
					.compile("(.*)(<td width=\"200\" height=\"20\" class=td_biaogexian><p align=\"center\">)(.*)(</p>)(.*)");
			Matcher m3 = null;
			Matcher m4 = null;
			int i = 0;
			String strTemp = "";
			List<PassScore> listPassScore = new ArrayList<PassScore>();
			if (m2.matches()) {
				for (String info : m2.group(3).split("<TR>")) {
					i = 0;
					PassScore passScore = new PassScore();
					for (String info2 : info.split("</td>")) {
						i++;
						m3 = p3.matcher(info2);
						m4 = p4.matcher(info2);
						if (m3.matches()) {
							strTemp = m3.group(3).replaceAll("&nbsp;", " ")
									.trim();
							// System.out.println(m3.group(3));
						} else if (m4.matches()) {
							strTemp = m4.group(3).replaceAll("&nbsp;", " ")
									.trim();
							// System.out.println(m4.group(3));
						}
						switch (i) {
						case 1:
							passScore.setClass_num(strTemp);
							break;
						case 2:
							passScore.setClass_name(strTemp);
							break;
						case 3:
							passScore.setNum(strTemp);
							break;
						case 4:
							passScore.setXf(strTemp);
							break;
						case 5:
							passScore.setKssj(strTemp);
							break;
						case 6:
							passScore.setScore(strTemp);
							break;
						case 7:
							passScore.setCh_class_num(strTemp);
							break;
						case 8:
							passScore.setRemark(strTemp);
							break;
						}
					}
					listPassScore.add(passScore);
				}
			}
			listPassScore.remove(0);

			p1 = Pattern
					.compile("(.*)(��ѧ�ƻ��⣨�γ̺Ų�һ�£�---ͨ���γ̳ɼ���)(.*)(����ƻ�����ͨ���γ̳ɼ���)(.*)");
			m1 = p1.matcher(strAll[1]);
			if (m1.matches()) {
				strAll2 = m1.group(3);
			}
			p2 = Pattern
					.compile("(.*)(<td  height=\"25\"  class=td_hz_bj><p align=\"center\">��ע</p></td>)(.*)(<B>   �ۼ�ѧ�֣���</B>)(.*)");
			m2 = p2.matcher(strAll2);
			p3 = Pattern
					.compile("(.*)(<td width=\"112\" height=\"20\" class=td_biaogexian><p align=\"center\">)(.*)(</p>)(.*)");
			p4 = Pattern
					.compile("(.*)(<td width=\"200\" height=\"20\" class=td_biaogexian><p align=\"center\">)(.*)(</p>)(.*)");
			m3 = null;
			m4 = null;

			List<PassScore> listPassScore2 = new ArrayList<PassScore>();
			if (m2.matches()) {
				for (String info : m2.group(3).split("<TR>")) {
					i = 0;
					PassScore passScore = new PassScore();
					for (String info2 : info.split("</td>")) {

						i++;
						m3 = p3.matcher(info2);
						m4 = p4.matcher(info2);
						if (m3.matches()) {
							strTemp = m3.group(3).replaceAll("&nbsp;", " ")
									.trim();
						} else if (m4.matches()) {
							strTemp = m4.group(3).replaceAll("&nbsp;", " ")
									.trim();
						}
						switch (i) {
						case 1:
							passScore.setClass_num(strTemp);
							break;
						case 2:
							passScore.setClass_name(strTemp);
							break;
						case 3:
							passScore.setNum(strTemp);
							break;
						case 4:
							passScore.setXf(strTemp);
							break;
						case 5:
							passScore.setKssj(strTemp);
							break;
						case 6:
							passScore.setScore(strTemp);
							break;
						case 7:
							passScore.setCh_class_num(strTemp);
							break;
						case 8:
							passScore.setRemark(strTemp);
							break;
						}
					}
					listPassScore2.add(passScore);
				}
			}
			listPassScore2.remove(0);
			listPassScore.addAll(listPassScore2);
			String returnStr = xfTotal;
			for (PassScore p : listPassScore) {
				returnStr += p.getClass_name() + ":" + p.getScore() + "\n";
			}
			strAll[1] = returnStr;
		}
		return strAll;
	}

	// �α�
	public static String[] getClassSchedule(String openID) {
		String strAll[] = goToBind(openID, class_schedule);
		if (!strAll[0].equals("0")) {
			String xfTotal = "";
			Pattern p0 = Pattern
					.compile("(.*)(<span class=\"td1\">�㹲ѡ����)(.*)(ѧ��</span><BR>)(.*)");
			Matcher m0 = p0.matcher(strAll[1]);
			if (m0.matches()) {
				xfTotal = m0.group(3).trim();
			}

			Pattern p1 = Pattern
					.compile("(.*)(<td  height=\"25\"  class=td_hz_bj><p align=\"center\">ѡ������˵��</p></td>)(.*)(<tr><td height=\"25\" class=td_bt_bj align=center colspan=9>δ����ʱ��ص�Ŀγ���Ϣ����</td></tr>)(.*)");
			Matcher m1 = p1.matcher(strAll[1]);
			Pattern p3 = Pattern
					.compile("(.*)(<td width=\"112\" height=\"20\" class=td_biaogexian><p align=\"center\">)(.*)(</p>)(.*)");
			Pattern p4 = Pattern
					.compile("(.*)(<td width=\"112\" height=\"20\" class=td_biaogexian><p align=\"center\"><FONT COLOR=\"#FF0000\"></FONT>)(.*)(</p>)(.*)");
			Matcher m3 = null;
			Matcher m4 = null;
			int i = 0;
			String strTemp = "";
			List<ClassSchedule> listClassSchedule = new ArrayList<ClassSchedule>();
			if (m1.matches()) {
				for (String info : m1.group(3).split("</TR>")) {
					i = 0;
					ClassSchedule classSchedule = new ClassSchedule();
					for (String info2 : info.split("</td>")) {
						i++;
						m3 = p3.matcher(info2);
						m4 = p4.matcher(info2);
						if (m4.matches()) {
							strTemp = m4.group(3).replaceAll("&nbsp;", " ")
									.trim();
						} else if (m3.matches()) {
							strTemp = m3.group(3).replaceAll("&nbsp;", " ")
									.trim();
						}

						switch (i) {
						case 1:
							classSchedule.setClass_name(strTemp);
							break;
						case 2:
							classSchedule.setClass_num(strTemp);
							break;
						case 3:
							classSchedule.setKxh(strTemp);
							break;
						case 4:
							classSchedule.setKslx(strTemp);
							break;
						case 5:
							classSchedule.setAddr(strTemp);
							break;
						case 6:
							classSchedule.setTime(strTemp);
							break;
						case 7:
							classSchedule.setWeekly(strTemp);
							break;
						case 8:
							classSchedule.setRemark(strTemp);
							break;
						}
					}
					listClassSchedule.add(classSchedule);
				}
			}
			String returnStr = "�㹲ѡ����" + xfTotal + "ѧ��\n\n";

			if (!xfTotal.equals("0")) {
				for (ClassSchedule c : listClassSchedule) {
					returnStr += c.getClass_name() + "\n�ص�:" + c.getAddr()
							+ "\nʱ��:" + c.getTime() + "\n�ܴ�:" + c.getWeekly()
							+ "\n��������:" + c.getKslx() + "\n\n";
				}
			}
			strAll[1] = returnStr;
		}
		return strAll;
	}

	public static String[] goToBind(String openID, String contentUrl) {
		String returnStr[] = new String[2];
		String bindUrl = ShmtuAuthService.auth_url_bind_pre + openID;
		// ����Ƿ��
		User user = UserDBUtil.getUserByOpenID(openID);
		if (user != null && user.getStudentPwd() != null
				&& !user.getStudentPwd().equals("")) {
			String str = ShmtuAuthService.getContentAfterAuthByHttpGet(
					user.getStudentNum(), user.getStudentPwd(), contentUrl);
			//System.out.println(str);
			// �ʺŹ���
			if (str != null && str.equals("0")) {
				returnStr[0] = "0";
				returnStr[1] = "������޸Ĺ������ˣ�����������������°󶨡�\n\n" + "<a href=\""
						+ bindUrl
						+ "\">����>ȥ��ѧ��</a>\n\n�����������������޷���ɰ󶨣��볢����������ַ�ʽ��\n"
						+ WeixinUtil.MSG_CONTENT_BIND_STUID_HELP;
			} else {
				returnStr[0] = "1";
				returnStr[1] = str;
			}
			// ��ʾ��ѧ��
		} else {
			returnStr[0] = "0";
			returnStr[1] = "�㻹û�а�ѧ�ţ��������������ȥ�󶨡�\n\n" + "<a href=\"" + bindUrl
					+ "\">����>ȥ��ѧ��</a>\n\n�����������������޷���ɰ󶨣��볢����������ַ�ʽ��\n"
					+ WeixinUtil.MSG_CONTENT_BIND_STUID_HELP;
		}
		return returnStr;
	}

	public static String getContentAfterAuthByHttpGet(String stuid, String pwd,
			String contentUrl) {
		String returnStr = null;
		String auth_url_pass = auth_url.replace("{stuid}", stuid).replace(
				"{pwd}", pwd);
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(auth_url_pass);
		// ���ó���Ĭ�ϵĻָ����ԣ��ڷ����쳣ʱ���Զ�����3�Σ���������Ҳ�������ó��Զ���Ļָ�����
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			// ִ��getMethod�����֤
			httpClient.executeMethod(getMethod);
			byte[] responseBody = getMethod.getResponseBody();
			if (new String(responseBody).contains("�������˴����ѧ�Ż�����")) {
				returnStr = "0";
			} else {
				GetMethod getMethodForContent = new GetMethod(contentUrl);
				httpClient.executeMethod(getMethodForContent);

				StringBuffer returnStrBuffer = new StringBuffer();
				InputStream inputStream = getMethodForContent
						.getResponseBodyAsStream();
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream, "gbk");
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String str = null;
				while ((str = bufferedReader.readLine()) != null) {
					returnStrBuffer.append(str);
				}
				bufferedReader.close();
				inputStreamReader.close();
				inputStream.close();
				inputStream = null;

				returnStr = new String(returnStrBuffer);
			}
		} catch (HttpException e) {
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		return returnStr;
	}

	public static String getStuInfoByHttpGet(String stuid, String pwd) {
		String returnStr = null;
		String auth_url_pass = auth_url.replace("{stuid}", stuid).replace(
				"{pwd}", pwd);
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(auth_url_pass);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			httpClient.executeMethod(getMethod);
			StringBuffer returnStrBuffer = new StringBuffer();
			InputStream inputStream = getMethod.getResponseBodyAsStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "gbk");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				returnStrBuffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			Pattern p1 = Pattern
					.compile("(.*)(<h4><font color=\"#9900FF\">)(.*)(</font></h4><br>)(.*)");
			Matcher m1 = p1.matcher(returnStrBuffer);
			if (m1.matches()) {
				returnStr = m1.group(3).replaceAll("&nbsp;", " ").trim();
			}
		} catch (HttpException e) {
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		return returnStr;
	}

	/*
	 * import org.apache.commons.httpclient.methods.PostMethod; import
	 * org.apache.commons.httpclient.NameValuePair; import
	 * co.xiaodao.weixin.util.XiaoDaoUtil; public static boolean
	 * authByPost(String stuid, String pwd) { HttpClient httpClient = new
	 * HttpClient(); PostMethod postMethod = new PostMethod(auth_url_short); //
	 * ������������ֵ NameValuePair[] data = { new NameValuePair("stuid", stuid), new
	 * NameValuePair("pwd", pwd) }; // ������ֵ����postMethod��
	 * postMethod.setRequestBody(data); try { // ִ��postMethod int statusCode =
	 * httpClient.executeMethod(postMethod); //
	 * HttpClient����Ҫ����ܺ�̷����������POST��PUT�Ȳ����Զ�����ת�� if (statusCode == 302) { //
	 * ��֤�ɹ� return true; } } catch (HttpException e) { //
	 * �����������쳣��������Э�鲻�Ի��߷��ص�����������
	 * System.out.println("Please check your provided http address!");
	 * e.printStackTrace(); } catch (IOException e) { // ���������쳣
	 * e.printStackTrace(); } finally { // �ͷ����� postMethod.releaseConnection();
	 * } return false; }
	 */
}