package com.wq.wechat.util;

public class URLController {
    private static String[] urls={"/center/","/health/"};
    public static boolean isContainsUrl(String url){
    	boolean flag=false;
    	for(String s :urls){
    		System.out.println("s:"+s);
    		System.out.println("url:"+url);
    		if(url.contains(s)){
    			flag=true;
    			break;
    		}
    	}
    	return flag;
    }
    
    private static String[] adminUrls={"/admin/"};
    public static boolean isAdminContainsUrl(String url){
    	boolean flag=false;
    	for(String s :adminUrls){
    		System.out.println("s:"+s);
    		System.out.println("url:"+url);
    		if(url.contains(s)){
    			flag=true;
    			break;
    		}
    	}
    	return flag;
    }
    
    private static String[] wxUrls={
    		"/center/medicalnsuranceDetails.html",
    		"/center/userinfo.html",
    		"/center/error",
    		
    		};
    public static boolean isWxContainsUrl(String url){
    	boolean flag=false;
    	for(String s :wxUrls){
    		System.out.println("s:"+s);
    		System.out.println("url:"+url);
    		if(url.contains(s)){
    			flag=true;
    			break;
    		}
    	}
    	return flag;
    }
}
