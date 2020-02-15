package com.Yang.watermark;

import com.Yang.commom.utils.UrlUtil;
import com.Yang.util.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;

public class Xunlei extends BaseExecute{

	public Xunlei(String url) {
		super(url);
	}

	@Override
	public String execute() {
		String vid = UrlUtil.parse(url).params.get("id");
		HttpResponse response = requestNoHeader("https://api-xl9-ssl.xunlei.com/sl/ivideo_v5/info?movieid=" + vid);
		JSONObject jsonObject = JSONObject.parseObject(response.body());
		JSONObject videoInfo = jsonObject.getJSONObject("video_info");
		videoInfo.getString("cover_url");
		return videoInfo.getString("play_url");
	}

	public static String get(String address) {
		return new Xunlei(address).execute();
	}
}
