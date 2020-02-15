package com.Yang.watermark;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.Yang.util.http.HttpRequest;
import com.Yang.util.http.HttpResponse;
import com.Yang.util.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

public class Miaopai extends BaseExecute{

	public Miaopai(String url) {
		super(url);
	}

	@Override
	public String execute() {
		String vid = url.split("/")[4].split("\\.")[0];
		
		HttpRequest request = HttpUtil.createGet("https://n.miaopai.com/api/aj_media/info.json?smid="+vid+"&appid=530&_cb=_jsonpmc2zrx9dpn");
		request.header("Referer", url);
		String body = request.execute().body();
		String json = body.replaceAll("window._jsonpmc2zrx9dpn && _jsonpmc2zrx9dpn\\(", "").replaceAll("\\);", "");
		JSONObject jsonObject = JSONObject.parseObject(json);
		JSONObject meta_data = jsonObject.getJSONObject("data").getJSONArray("meta_data").getJSONObject(0);
		
		String pic = meta_data.getJSONObject("pics").getString("l");
	
		
		return meta_data.getJSONObject("play_urls").getString("n");
	}
	
	
	public static String get(String address) {
		return new Miaopai(address).execute();
	}
}
