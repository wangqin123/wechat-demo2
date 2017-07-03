package com.wq.wechat.res;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.wq.wechat.util.CDataAdapter;



/**
 * 音乐消息
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class MusicMessage extends BaseMessage {
    /**
     * 音乐
     */
	@XmlJavaTypeAdapter(CDataAdapter.class)
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
