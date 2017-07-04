package com.wq.wechat.bean;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.wq.wechat.util.CDataAdapter;

public class Image {
	
	 @XmlJavaTypeAdapter(CDataAdapter.class)
	    private String MediaId;

		public String getMediaId() {
			return MediaId;
		}

		public void setMediaId(String mediaId) {
			MediaId = mediaId;
		}


}
