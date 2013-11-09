package co.xiaodao.weixin.message.response;

import java.util.List;

import co.xiaodao.weixin.message.pojo.Article;
import co.xiaodao.weixin.util.WeixinUtil;

/**
 * ��Ӧͼ����Ϣ
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class RespNewsMessage extends RespBaseMessage {
	// ͼ����Ϣ����������Ϊ10������
	private String ArticleCount;
	// ����ͼ����Ϣ��Ϣ��Ĭ�ϵ�һ��itemΪ��ͼ
	private List<Article> Articles;

	public RespNewsMessage(String fromUserName, String toUserName,
			String createTime, String funcFlag, String articleCount,
			List<Article> articles) {
		super(fromUserName, toUserName, createTime,
				WeixinUtil.RESP_MESSAGE_TYPE_NEWS, funcFlag);
		ArticleCount = articleCount;
		Articles = articles;
	}

	public String getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(String articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
}