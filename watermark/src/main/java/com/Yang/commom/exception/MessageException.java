/**   
 * Copyright © 2019 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * 功能描述：
 * @Package: com.Yang.exception 
 * @author: Administrator   
 * @date: 2019年4月16日 下午4:50:56 
 */
package com.Yang.commom.exception;

public class MessageException extends RuntimeException {
	
	/**
	 * 自定义异常类(继承运行时异常)
	 * 
	 * @author AlanLee
	 * @version 2016/11/26
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 错误编码
	 */
	private String errorCode;

	/**
	 * 消息是否为属性文件中的Key
	 */
	private boolean propertiesKey = true;

	/**
	 * 构造一个基本异常.
	 *
	 * @param message
	 *            信息描述
	 */
	public MessageException(String message) {
		super(message);
		//logger.info(message);
	}

	/**
	 * 构造一个基本异常.
	 *
	 * @param errorCode
	 *            错误编码
	 * @param message
	 *            信息描述
	 */
	public MessageException(String errorCode, String message) {
		this(errorCode, message, true);
//		logger.info(message);
	}

	/**
	 * 构造一个基本异常.
	 *
	 * @param errorCode
	 *            错误编码
	 * @param message
	 *            信息描述
	 */
	public MessageException(String errorCode, String message, Throwable cause) {
		this(errorCode, message, cause, true);
//		logger.info(message);
	}

	/**
	 * 构造一个基本异常.
	 *
	 * @param errorCode
	 *            错误编码
	 * @param message
	 *            信息描述
	 * @param propertiesKey
	 *            消息是否为属性文件中的Key
	 */
	public MessageException(String errorCode, String message, boolean propertiesKey) {
		super(message);
		this.setErrorCode(errorCode);
		this.setPropertiesKey(propertiesKey);
//		logger.info(message);
	}

	/**
	 * 构造一个基本异常.
	 *
	 * @param errorCode
	 *            错误编码
	 * @param message
	 *            信息描述
	 */
	public MessageException(String errorCode, String message, Throwable cause, boolean propertiesKey) {
		super(message, cause);
		this.setErrorCode(errorCode);
		this.setPropertiesKey(propertiesKey);
//		logger.info(message);
	}

	/**
	 * 构造一个基本异常.
	 *
	 * @param message
	 *            信息描述
	 * @param cause
	 *            根异常类（可以存入任何异常）
	 */
	public MessageException(String message, Throwable cause) {
		super(message, cause);
//		logger.info(message);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isPropertiesKey() {
		return propertiesKey;
	}

	public void setPropertiesKey(boolean propertiesKey) {
		this.propertiesKey = propertiesKey;
	}
}
