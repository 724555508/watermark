package com.Yang.commom.aspectj;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.Yang.util.json.JSONUtil;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AopLog {

	
	private static final String START_TIME = "request-start";

	/**
	 * 切入点
	 */
	@Pointcut("execution(public * com.Yang.controller.*Controller.*(..))")
	public void log() {

	}

	/**
	 * 前置操作
	 *
	 * @param point 切入点
	 */
	@Before("log()")
	public void beforeLog(JoinPoint point) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

		
		log.info("【请求 IP】：{}", getIpAddr(request));

		Map<String, String[]> parameterMap = request.getParameterMap();
		log.info("【请求参数】：{}，", JSONUtil.toJsonStr(parameterMap));
		Long start = System.currentTimeMillis();
		request.setAttribute(START_TIME, start);
	}

	/**
	 * 环绕操作
	 *
	 * @param point 切入点
	 * @return 原方法返回值
	 * @throws Throwable 异常信息
	 */
	@Around("log()")
	public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
	
		Object result = point.proceed();
		log.info("【返回值】：{}", JSONUtil.toJsonStr(result));
		return result;
	}

	/**
	 * 后置操作
	 */
	@AfterReturning("log()")
	public void afterReturning() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(attributes).getRequest();

		Long start = (Long) request.getAttribute(START_TIME);
		
		Long end = System.currentTimeMillis();
		log.info("【请求耗时】：{}毫秒", end - start);
		String header = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(header);
		log.info("【浏览器类型】：{}，【操作系统】：{}，【原始User-Agent】：{}", userAgent.getBrowser().toString(), userAgent.getOperatingSystem().toString(), header);
		
	}
	
	public static String getIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("X-Real-IP");  
        if (ip!= null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {  
            return ip;  
        }  
        ip = request.getHeader("X-Forwarded-For");  
        if (ip!= null && !"".equals(ip)  && !"unknown".equalsIgnoreCase(ip)) {  
            // 多次反向代理后会有多个IP值，第一个为真实IP。  
            int index = ip.indexOf(',');  
            if (index != -1) {  
                return ip.substring(0, index);  
            } else {  
                return ip;  
            }  
        } else {  
            return request.getRemoteAddr();  
        }  
    }  
}
