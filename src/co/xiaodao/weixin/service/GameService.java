package co.xiaodao.weixin.service;

import java.util.ArrayList;
import java.util.List;

import co.xiaodao.weixin.db.util.BaseDBUtil;
import co.xiaodao.weixin.message.pojo.Article;

/**
 * ��Ϸ����
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */

public class GameService {

	// ��ϷURLǰ׺
	public static final String GAME_URL_PRE = "http://resource.duopao.com/duopao/games/small_games/weixingame/";

	public static List<Article> makeArticlesByGame() {

		List<Article> articles = new ArrayList<Article>();

		Article article1 = new Article();
		article1.setTitle("������Ϸ��������������Ϸ:");
		article1.setDescription("");
		article1.setUrl(null);
		article1.setPicUrl("http://" + BaseDBUtil.WEB_HOSE
				+ "/WeixinXiaodaoit/image/game/game_head1.jpg");

		Article article2 = new Article();
		article2.setTitle("���ռ�������\nһ�ս�ľ�����������Ϸ");
		article2.setPicUrl("http://" + BaseDBUtil.WEB_HOSE
				+ "/WeixinXiaodaoit/image/game/game_kjxs.jpg");
		article2.setDescription("");
		article2.setUrl(GAME_URL_PRE + "spaceman/spaceman.html");

//		Article article3 = new Article();
//		article3.setTitle("����ս��");
//		article3.setPicUrl("http://" + BaseDBUtil.WEB_HOSE
//				+ "/WeixinXiaodaoit/image/game/game_ltzj.jpg");
//		article3.setDescription("");
//		article3.setUrl(GAME_URL_PRE + "X-Type2/X-Type.htm");

//		Article article4 = new Article();
//		article4.setTitle("֩��ֽ��");
//		article4.setPicUrl("http://" + BaseDBUtil.WEB_HOSE
//				+ "/WeixinXiaodaoit/image/game/game_zzzp.jpg");
//		article4.setDescription("");
//		article4.setUrl(GAME_URL_PRE + "SpiderSolitaire/SpiderSolitaire.htm");

		Article article5 = new Article();
		article5.setTitle("����������\n΢����Ҳ�ܶ�����");
		article5.setPicUrl("http://" + BaseDBUtil.WEB_HOSE
				+ "/WeixinXiaodaoit/image/game/game_ddz.jpg");
		article5.setDescription("");
		article5.setUrl(GAME_URL_PRE + "Doudizhu/doudizhu.htm");

		Article article6 = new Article();
		article6.setTitle("������˹���须\n���������һ����Ϸ");
		article6.setPicUrl("http://" + BaseDBUtil.WEB_HOSE
				+ "/WeixinXiaodaoit/image/game/game_elsfk.jpg");
		article6.setDescription("");
		article6.setUrl(GAME_URL_PRE + "blockDream/blockDream.htm");

//		Article article7 = new Article();
//		article7.setTitle("��������\n��ļ�ʻ���ɹ�����");
//		article7.setPicUrl("http://" + BaseDBUtil.WEB_HOSE
//				+ "/WeixinXiaodaoit/image/game/game_pcgs.jpg");
//		article7.setDescription("");
//		article7.setUrl(GAME_URL_PRE + "ParkingTrainee/ParkingTrainee.htm");

		Article article8 = new Article();
		article8.setTitle("����ʯ����ս��\n������������ò���");
		article8.setPicUrl("http://" + BaseDBUtil.WEB_HOSE
				+ "/WeixinXiaodaoit/image/game/game_bslhz.jpg");
		article8.setDescription("");
		article8.setUrl(GAME_URL_PRE + "JewelJive/JewelJive.htm");

//		Article article9 = new Article();
//		article9.setTitle("��ɨǧ��");
//		article9.setPicUrl("http://" + BaseDBUtil.WEB_HOSE
//				+ "/WeixinXiaodaoit/image/game/game_hsqj.jpg");
//		article9.setDescription("");
//		article9.setUrl(GAME_URL_PRE + "Cubium/Cubium.htm");

		Article article10 = new Article();
		article10.setTitle("��̨���ʦ��\n̨����Ϸ��һ������");
		article10.setPicUrl("http://" + BaseDBUtil.WEB_HOSE
				+ "/WeixinXiaodaoit/image/game/game_tqds.jpg");
		article10.setDescription("");
		article10.setUrl(GAME_URL_PRE + "BilliardsMaster/BilliardsMaster.htm");

		articles.add(article1);
		articles.add(article2);
//		articles.add(article3);
//		articles.add(article4);
		articles.add(article5);
		articles.add(article6);
//		articles.add(article7);
		articles.add(article8);
//		articles.add(article9);
		articles.add(article10);

		return articles;
	}

	public static void main(String[] args) {

	}
}
