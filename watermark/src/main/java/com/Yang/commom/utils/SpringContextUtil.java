package com.Yang.commom.utils;

import org.springframework.context.ApplicationContext;

public class SpringContextUtil {

	private static ApplicationContext applicationContext;
	
	/**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     * @param applicationContext
     */
	public static void setApplicationContext(ApplicationContext applicationContext) {
		if (null == SpringContextUtil.applicationContext) {
			SpringContextUtil.applicationContext = applicationContext;
		}
	}
	
	 public static ApplicationContext getApplicationContext(){
	        return applicationContext;
	 }
	/**
	 * 通过name获取 Bean.
	 * @param name
	 * @return
	 */
	 public static Object getBean(String name) {
		 return getApplicationContext().getBean(name);
	 }
	 /**
	  * 通过name获取 Bean.
	  * @param clazz
	  * @return
	  */
	 public static <T> T getBean(Class<T> clazz) {
	        return getApplicationContext().getBean(clazz);
	 }
	 /**
	  * 通过name,以及Clazz返回指定的Bean
	  * @param name
	  * @param clazz
	  * @return
	  */
	 public static <T> T getBean(String name, Class<T> clazz) {
	        return getApplicationContext().getBean(name, clazz);
	 }
}
