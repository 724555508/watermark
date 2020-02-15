package com.Yang.commom.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Yang.commom.utils.V;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理器
 * @author Aroli
 *
 */
@ControllerAdvice
@RestController("exceptionHandle")
@Slf4j
public class ExceptionHandle {
	
	
	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(MessageException.class)
	public V handleRRException(MessageException e){
		return V.error(e.getMessage());
	}
	
	
	/**
	 * 400 - Bad Request
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public V handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
		return V.error("参数解析失败");
	}

	/**
	 * 405 - Method Not Allowed
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public V handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
		return V.error("不支持“" + e.getMethod() + "”请求方式");
	}
	
	/**
	 * 415 - Unsupported Media Type
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public V handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
		return V.error("不支持的媒体类型");
	}
	
	
	@ExceptionHandler(Exception.class)
	public V handleException(Exception e){
		V v = new V();
		v.setCode(-1);
		v.setMsg("服务器响应失败，请稍后再试");
		log.error(e.getMessage(), e);
		return v;
	}
}
