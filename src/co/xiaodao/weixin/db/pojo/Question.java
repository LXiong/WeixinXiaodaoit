package co.xiaodao.weixin.db.pojo;

/**
 * ����ʵ���࣬��Ӧ���ݿ��tb_question
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class Question {

	// �������
	private String category;
	// ����
	private String question;
	// �����
	private String answer;
	// ��Ӧ�ı�id
	private String id;
	// ��һ�β�ѯ���û�openID
	private String openID;
	// ��һ�β�ѯʱ��
	private String createTime;

	public Question(String id, String category, String question, String answer,
			String openID, String createTime) {
		super();
		this.id = id;
		this.question = question;
		this.answer = answer;
		this.category = category;
		this.openID = openID;
		this.createTime = createTime;
	}
	
	public Question(String id, String category, String question, String answer) {
		super();
		this.id = id;
		this.question = question;
		this.answer = answer;
		this.category = category;
	}

	public Question(String category, String question, String answer,
			String openID, String createTime) {
		super();
		this.question = question;
		this.answer = answer;
		this.category = category;
		this.openID = openID;
		this.createTime = createTime;
	}

	public Question(String category, String question, String answer) {
		super();
		this.question = question;
		this.answer = answer;
		this.category = category;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
