package co.xiaodao.weixin.message.response;

import co.xiaodao.weixin.message.pojo.Music;
import co.xiaodao.weixin.util.WeixinUtil;

/**
 * ��Ӧ������Ϣ
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class RespMusicMessage extends RespBaseMessage {
	// ����
	private Music Music;

	public RespMusicMessage(String fromUserName, String toUserName,
			String createTime, String funcFlag, Music music) {
		super(fromUserName, toUserName, createTime,
				WeixinUtil.RESP_MESSAGE_TYPE_MUSIC, funcFlag);
		Music = music;
	}

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}