package com.wq.wechat.util;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;  

/**
 * 
 * 
 * @author wangqin
 *
 * @date 2017年7月4日 下午3:45:11
 */
public class Sign1 {
	public static void main(String[] args) {
		String content = "jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VIwDl6hP41oKse7AnaaWf0grD3GGaxLJG9w2I8bmQiMTseIkOYBk8sH6_cgaI0pkkA&noncestr=b6138758-22b3-4a29-ad5b-f3e67266cc14&timestamp=1477559383&url=http://192.168.2.193:8380/appapi/wap/upload/index";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(content.getBytes("UTF-8"));
			String signature = byteToHex(crypt.digest());
			System.out.println(signature);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	public static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String signature = "";

		
		String content = "jsapi_ticket=" + jsapi_ticket +
				"&noncestr=" + nonce_str +
				"&timestamp=" + timestamp +
				"&url=" + url;

		try{
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(content.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash)
		{
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
