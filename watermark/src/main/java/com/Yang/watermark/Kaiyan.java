package com.Yang.watermark;

import com.Yang.commom.utils.UrlUtil;
import com.Yang.commom.utils.UrlUtil.UrlEntity;
import com.Yang.util.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;

public class Kaiyan extends BaseExecute{

	public Kaiyan(String url) {
		super(url);
	}

	@Override
	public String execute() {
		String vid = UrlUtil.parse(url).params.get("vid");
		HttpResponse response = request("https://baobab.kaiyanapp.com/api/v1/video/" + vid);
		JSONObject json = JSONObject.parseObject(response.body());
		String pic = json.getString("coverForDetail");
		return json.getString("playUrl");
	}
	
	
	public static String get(String address) {
		return new Kaiyan(address).execute();
	}

	
	public static void main(String[] args) {
		System.out.println(Kaiyan.get("https://www.eyepetizer.net/detail.html?vid=186455&utm_campaign=routine&utm_medium=share&utm_source=others&uid=0&resourceType=video&udid=bec399dd070a4ff58bc0233cbb773a4c0d05475a&vc=591&vn=6.2.1&size=1080X2069&deviceModel=SM-N9760&first_channel=eyepetizer_samsung_market&last_channel=eyepetizer_samsung_market&system_version_code=28"));
	}
}
