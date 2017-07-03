package com.wq.wechat.res;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.wq.wechat.util.CDataAdapter;



/**
 * 文本消息
 */
@SuppressWarnings("restriction")
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class TextMessage extends BaseMessage {
    /**
     * 回复的消息内容
     */
	@XmlJavaTypeAdapter(CDataAdapter.class)
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}