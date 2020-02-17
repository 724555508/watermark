package com.Yang.watermark;

import com.Yang.commom.utils.UrlUtil;
import com.Yang.util.http.HttpRequest;
import com.Yang.util.http.HttpResponse;
import com.Yang.util.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

public class Wide extends BaseExecute{

	public Wide(String url) {
		super(url);
	}

	@Override
	public String execute() {
		String vid = UrlUtil.parse(url).params.get("video_id");
		HttpResponse response = requestNoHeader("https://api.wide.meipai.com/h5/video_show.json?video_id=" + vid);
		JSONObject jsonObject = JSONObject.parseObject(response.body());
		JSONObject object = jsonObject.getJSONObject("response").getJSONObject("video_info");
		
		object.getString("title");
		object.getString("cover");
		return object.getString("source");
	}

	public static String get(String address) {
		return new Wide(address).execute();
	}
}
