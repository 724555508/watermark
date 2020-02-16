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
}
