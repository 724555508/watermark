package com.Yang.watermark;

import com.Yang.commom.utils.UrlUtil;
import com.Yang.util.http.HttpResponse;
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
		object.getString("cover");
		return object.getString("source");
	}

	public static String get(String address) {
		return new Wide(address).execute();
	}
	public static void main(String[] args) {
		System.out.println(Wide.get("https://h5.wide.meipai.com/app/share/video/?video_id=1534750180003875343"));
	}
}
