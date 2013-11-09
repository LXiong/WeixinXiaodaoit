package co.xiaodao.weixin.db.pojo;

/**
 * ����ǩ������Ӧ���ݿ��tb_getup_sign
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-5-12
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class GetUpSign {
	private String openID;
	private String date;
	private String time;
	private String rank;

	public GetUpSign(String openID, String date, String time) {
		super();
		this.openID = openID;
		this.date = date;
		this.time = time;
	}

	public GetUpSign(String openID, String date, String time, String rank) {
		super();
		this.openID = openID;
		this.date = date;
		this.time = time;
		this.rank = rank;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
