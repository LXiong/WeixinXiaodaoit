package co.xiaodao.weixin.service;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import co.xiaodao.weixin.util.AutoReplyUtil;
import co.xiaodao.weixin.util.WeixinUtil;

/**
 * ����Ӧ�����
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class AutoReplyService {
	private static Logger log = LoggerFactory.getLogger(AutoReplyService.class);

	/**
	 * �������и��ݹؼ��ʼ���
	 */
	@SuppressWarnings("deprecation")
	public static String search(String keyWord) {
		long startTime = System.currentTimeMillis();

		String answer = WeixinUtil.MSG_CONTENT_OTHER_TYPE;
		try {
			Directory directory = FSDirectory.open(new File(
					AutoReplyUtil.PATH_INDEX));
			IndexReader reader = IndexReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);

			QueryParser parser = new QueryParser(Version.LUCENE_40,
					AutoReplyUtil.FIELD_QUESTION, new IKAnalyzer(true));
			Query query = parser.parse(QueryParser.escape(keyWord));
			TopDocs topDocs = searcher.search(query, 1);
			ScoreDoc[] scoreDoc = topDocs.scoreDocs;

			for (ScoreDoc sd : scoreDoc) {
				Document d = searcher.doc(sd.doc);
				answer = d.get(AutoReplyUtil.FIELD_ANSWER);

				System.out.println(d.get(AutoReplyUtil.FIELD_QUESTION));
			}

			reader.close();
			directory.close();
		} catch (Exception e) {
			log.error("{}", e);
		}

		long endTime = System.currentTimeMillis();
		System.out.println("������ʱ " + (endTime - startTime) / 1000.0 + " ��");
		return answer;
	}
}
