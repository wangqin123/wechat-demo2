package com.wq.wechat.prop;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

public class PropertyPlaceholderConfigurer extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

	private static Properties props;

	public Properties mergeProperties() throws IOException {
		if ( props != null) return props;
		props = super.mergeProperties();
		logger.debug(MessageFormat.format("properties loaded size {0}.", props.size()));
		return props;
	}

	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);

	}

	public static Properties getProperties() {
		return props;
	}
	
	

}
