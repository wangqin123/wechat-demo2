package com.wq.wechat.res;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.wq.wechat.util.CDataAdapter;






/**
 * 图文消息
 * 
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class Article {
    /**
     * 图文消息名称
     */
	@XmlJavaTypeAdapter(CDataAdapter.class)
    private String Title;

    /**
     * 图文消息描述
     */
	@XmlJavaTypeAdapter(CDataAdapter.class)
    private String Description;

    /**
     * 图片链接，支持JPG、PNG格式，<br>
     * 较好的效果为大图640*320，小图80*80
     */
	@XmlJavaTypeAdapter(CDataAdapter.class)
    private String PicUrl;

    /**
     * 点击图文消息跳转链接
     */
	@XmlJavaTypeAdapter(CDataAdapter.class)
    private String Url;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return null == Description ? "" : Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPicUrl() {
        return null == PicUrl ? "" : PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getUrl() {
        return null == Url ? "" : Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

}