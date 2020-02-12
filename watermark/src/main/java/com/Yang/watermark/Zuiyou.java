package com.Yang.watermark;

import com.Yang.util.http.HttpResponse;

public class Zuiyou extends BaseExecute{

	static String START = "<video src=\"";
	static String END = "\" webkit-playsinline=\"true\"";
	
	public Zuiyou(String url) {
		super(url);
	}

	@Override
	public String execute() {
		HttpResponse response = requestNoHeader("https://share.izuiyou.com" + getRealPlayAddress(url));
		String body = response.body();
		
		return body.substring(body.indexOf(START) + START.length(), body.indexOf(END));
	}

	public static String get(String address) {
		return new Zuiyou(address).execute();
	}
	
}
