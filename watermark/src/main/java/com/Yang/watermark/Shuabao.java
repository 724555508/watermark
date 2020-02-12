package com.Yang.watermark;

import com.Yang.util.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;

public class Shuabao extends BaseExecute{

	public Shuabao(String url) {
		super(url);
	}

	@Override
	public String execute() {
		
		String show_id = url.split("&show_id=")[1].split("&uid")[0];
		HttpResponse response = request("http://h5.shua8cn.com/api/video/detail?show_id="+ show_id +"&provider=weixin");
		String body = response.body();
		return JSONObject.parseObject(body).getJSONObject("data").getString("video_url");
	}

	public static String get(String address) {
		return new Shuabao(address).execute();
	}
}
