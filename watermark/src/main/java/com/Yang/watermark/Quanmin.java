package com.Yang.watermark;

import com.Yang.util.http.HttpResponse;

public class Quanmin extends BaseExecute{

	static String START = "\"url\":\"http";
	static String END = "\",\"videoBps";
	
	
	public Quanmin(String url) {
		super(url);
	}

	@Override
	public String execute() {
		HttpResponse response = requestNoHeader(url);
		String body = response.body();
		return body.substring(body.indexOf(START) + 7, body.indexOf(END)).replaceAll("\\\\", "");
	}

	
	public static String get(String address) {
		return new Quanmin(address).execute();
	}
}
