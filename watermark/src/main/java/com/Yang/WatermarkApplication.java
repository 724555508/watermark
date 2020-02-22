package com.Yang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.Yang.commom.utils.SpringContextUtil;

@SpringBootApplication
public class WatermarkApplication implements ApplicationListener<ContextRefreshedEvent>{

	public static void main(String[] args) {
		SpringApplication.run(WatermarkApplication.class, args);
	}

	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		SpringContextUtil.setApplicationContext(event.getApplicationContext());
	}
}
