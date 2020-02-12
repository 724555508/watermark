package com.Yang.watermark;

import com.Yang.util.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.crypto.digest.MD5;

public class Kuaishou extends BaseExecute{

	public Kuaishou(String url) {
		super(url);
	}

	@Override
	public String execute() {
		HttpResponse response = requestNoHeader("http://api.gifshow.com/rest/n/tokenShare/info/byText?client_key=3c2cd3f3&shareText=" + url + "&sig=" + getGifSign("3c2cd3f3",url,"382700b563f4"));
		String body = response.body();
		JSONObject result = JSONObject.parseObject(body);
		return result.getJSONObject("shareTokenDialog").getJSONObject("feed").getString("main_mv_url");
	}
	
	public static String get(String address) {
		return	new Kuaishou(address).execute();
		
		
	}
	
	String getGifSign(String client_key , String shareText , String salt) {
		String m_str ="client_key=" + client_key + "shareText=" + shareText + salt;
		return MD5.create().digestHex(m_str);
	}

	
	
	
}
