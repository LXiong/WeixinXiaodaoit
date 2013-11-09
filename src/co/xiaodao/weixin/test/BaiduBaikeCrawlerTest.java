package co.xiaodao.weixin.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.xiaodao.weixin.util.XiaoDaoUtil;

//import co.xiaodao.weixin.util.AutoReplyUtil;
//import co.xiaodao.weixin.util.WeixinUtil;

/**
 * �ٶȰٿ����ݵ���ȡ��
 * 
 * @author liufeng
 * @date 2013-04-05
 */
public class BaiduBaikeCrawlerTest {

	public static void main2(String[] args) {
		int inNum = 0;
		int outNum = 0;
		while (true) {
			try {
				String outputStr = null;
				// �����ļ�
				File inputFile = new File("D:/lucene/example/input" + inNum
						+ ".txt");
				FileInputStream inputFis = new FileInputStream(inputFile);
				InputStreamReader inputIsr = new InputStreamReader(inputFis);
				BufferedReader inputBr = new BufferedReader(inputIsr);
				String inputStr;
				// ������ȡ���ݵ�����ļ�
				// long fileNameRandom = AutoReplyUtil
				// .getRandomNumberForFileName();
				File outputFile = new File("D:/lucene/example/output" + outNum
						+ ".txt");
				FileOutputStream outputFos = new FileOutputStream(outputFile);
				OutputStreamWriter outputOsw = new OutputStreamWriter(outputFos);
				BufferedWriter outputBw = new BufferedWriter(outputOsw);
				PrintWriter outputPw = new PrintWriter(outputBw, true);
				// δ��ȡ���ݵ�����ļ�
				inNum++;
				File outputFile2 = new File("D:/lucene/example/input" + inNum
						+ ".txt");
				FileOutputStream outputFos2 = new FileOutputStream(outputFile2);
				OutputStreamWriter outputOsw2 = new OutputStreamWriter(
						outputFos2);
				BufferedWriter outputBw2 = new BufferedWriter(outputOsw2);
				PrintWriter outputPw2 = new PrintWriter(outputBw2, true);

				while (true) {
					inputStr = inputBr.readLine();
					if (inputStr != null && !inputStr.equals("")) {
						outputStr = queryBaikeTest(inputStr.trim(), true);
						if (outputStr != null && !outputStr.equals("")) {
							outputPw.println(outputStr);
						} else {
							outputPw2.println(inputStr);
						}
					} else {
						break;
					}
				}
				inputBr.close();
				outputPw.close();
				outputPw2.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			outNum++;
		}
	}

	/**
	 * �ٶȰٿƲ�ѯ
	 * 
	 * @return
	 */
	public static String queryBaikeTest(String word, boolean isGetSql) {
		String baikeStr = null;
		try {
			String requestUrl = "http://baike.baidu.com/search/word?enc=utf8&sug=1&pic=1&word="
					+ URLEncoder.encode(word, "utf-8");
			String returnStr = XiaoDaoUtil.getJsonByHttp(requestUrl, "utf-8");
			baikeStr = extractTest(returnStr);
			if (isGetSql && baikeStr != null && !baikeStr.equals("")) {
				baikeStr = "INSERT INTO `tb_baike`(question, answer) VALUES ('"
						+ word + "','" + baikeStr + "------���ԡ��ٶȰٿơ�');";
			}
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("-------------------------------");
		}
		if (isGetSql) {
			System.out.println(baikeStr);
		}
		return baikeStr;
	}

	/**
	 * ��html�г�ȡ��Ϣ
	 * 
	 * @param html
	 * @return
	 */
	private static String extractTest(String html) {
		String result = null;
		Pattern p = Pattern
				.compile("(.*)(<div class=\"card-summary-content\"><div class=\"para\">)(.*?)(</div>)(.*)");
		Matcher m = p.matcher(html);
		if (m.matches()) {
			result = m.group(3).replaceAll("<[^>]*>", "")
					.replaceAll("[(/>)<]", "");
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(queryBaikeTest("�Ϻ����´�ѧ", false));
	}

}
