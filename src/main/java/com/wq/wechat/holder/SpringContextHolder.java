package com.wq.wechat.holder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy(false)
@Component
public class SpringContextHolder implements ApplicationContextAware{
 
    private static ApplicationContext applicationContext;
 
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
    }
 
    //取得存储在静态变量中的ApplicationContext.
    public static ApplicationContext getApplicationContext() {
    	 if (applicationContext == null) {
             throw new IllegalStateException("applicationContext未设置");
         }
        return applicationContext;
    }
     
    @SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
        return (T) getApplicationContext().getBean(name);
    }
     
    public static <T> T getBean(Class<T> clazz) {
      return getApplicationContext().getBean(clazz);
    }
}