package com.wq.wechat.res;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.wq.wechat.bean.Image;
import com.wq.wechat.util.CDataAdapter;




/**
 * 消息基类（公众帐号 -> 用户）
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseMessage {
    
    /**
     * 接收方帐号（收到的OpenID）
     */
	@XmlJavaTypeAdapter(CDataAdapter.class)
    private String ToUserName;
    /**
     * 开发者微信号
     */
	@XmlJavaTypeAdapter(CDataAdapter.class)
    private String FromUserName;
    /**
     * 消息创建时间 （整型）
     */
    private long CreateTime;
    
    /**
     * 消息类型
     */
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String MsgType;
    
    
    private Image Image;
    
    /**
     * 位0x0001被标志时，星标刚收到的消息
     */
    private int FuncFlag;
    

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public int getFuncFlag() {
        return FuncFlag;
    }

    public void setFuncFlag(int funcFlag) {
        FuncFlag = funcFlag;
    }

	public Image getImage() {
		return Image;
	}

	public void setImage(Image Image) {
		this.Image = Image;
	}

}
