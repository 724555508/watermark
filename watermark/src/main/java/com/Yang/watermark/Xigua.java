package com.Yang.watermark;

import java.util.zip.CRC32;

import com.Yang.util.MathUtil;
import com.Yang.util.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.http.HttpRequest;

public class Xigua extends BaseExecute{

	public Xigua(String url) {
		super(url);
	}

	@Override
	public String execute() {
		String realUrl = getRealPlayAddress(url);
		String item_id = findItem_id(realUrl);
		String xigua_url = "https://www.ixigua.com/" + item_id;
		
		HttpRequest request = new HttpRequest(xigua_url);
		request.header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
		
		request.cookie("wafid=05de99a1-e05f-48db-ae68-703a88ee93d3; wafid.sig=M52Do6eLbDBy5_bdOAdMQVBLUps; xiguavideopcwebid=6791425228121769479; xiguavideopcwebid.sig=6gqGsBjeSRsehu5nqlUCgNs_I1U; SLARDAR_WEB_ID=8b2aad22-efff-4a5f-86d9-0f06fedeee62; _ga=GA1.2.1240117577.1581251934; _gid=GA1.2.807696873.1581251934; s_v_web_id=k6f0mglb_Roi6Xokq_mEPw_4Xfl_9F2F_H5FJFX9rPRhV; _gat_gtag_UA_138710293_1=1");
		cn.hutool.http.HttpResponse response = request.execute();
		String vid = response.body().split("\"vid\":\"")[1].split("\"")[0];
		CRC32 crc32 = new CRC32();
		String r = "1"+MathUtil.getIntCode8();
		crc32.update(("/video/urls/v/1/toutiao/mp4/" + vid + "?r=" + r).getBytes());
		
		HttpResponse response2 = requestNoHeader("http://i.snssdk.com/video/urls/v/1/toutiao/mp4/" + vid + "?nobase64=true&r=" + r + "&s=" + crc32.getValue());
		String res = response2.body();
		JSONObject parseObject = JSONObject.parseObject(res);
		return parseObject.getJSONObject("data").getJSONObject("video_list").getJSONObject("video_1").getString("main_url");
		
	}

	
	public static String get(String address) {
		return new Xigua(address).execute();
	}
	
	String findItem_id(String url) {
		String itemId = "";
		if(url.contains("m.toutiaoimg.com")) {//为今日头条视频
			
			itemId = url.split("/")[3];
		}else if(url.contains("m.ixigua.com")) {//为西瓜视频移动端
			
			itemId = url.split("/")[3];
		}else if(url.contains("www.ixigua.com")) {//为西瓜视频web端
			itemId = url.replaceAll("https://www.ixigua.com/i", "").replaceAll("http://www.ixigua.com/i","");
			itemId = itemId.split("/")[0];
		}
		return itemId;
	}
}
