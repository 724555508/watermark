package com.Yang.watermark;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.Yang.util.RandomCharUtil;
import com.Yang.util.http.HttpRequest;
import com.Yang.util.http.HttpResponse;
import com.Yang.util.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TencentVideo extends BaseExecute{
	
	String appVersion = "1.0.143";
	String platform = "10201";
	String v = "3.5.57";
	String encryptVer = "9.1";

	public TencentVideo(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute() {
		String start = "<link rel=\"canonical\" href=";
		HttpResponse response = requestNoHeader(url);
		String body = response.body();
		int indexOf = body.indexOf(start);
		body = body.substring(indexOf + start.length() + 1);
		int index = body.indexOf("\" />");
		body = body.substring(0,index);
		String[] split = body.split("/");
		String vid = split[split.length - 1].replaceAll(".html","");
		String[] split2 = url.split("/");
		String coverid = split2[split2.length - 1].replaceAll(".html","");
		
		String flowid = RandomCharUtil.getRandomNumberLowerLetterChar(32) + "_" + platform;
		String tm = true ? String.valueOf(System.currentTimeMillis()).substring(0,10) : "1584782550";
		String rfid = RandomCharUtil.getRandomNumberLowerLetterChar(32) + "_" + tm;
		String cKey = HttpUtil.get("http://123.57.141.60:9797/?vid="+ vid +"&tm=" + tm);
		
		log.info("vid:{}",vid);
		log.info("coverid:{}",coverid);
		log.info("flowid:{}",flowid);
		log.info("tm:{}",tm);
		log.info("rfid:{}",rfid);
		log.info("url:{}",url);
		log.info("cKey:{}",cKey);
		
//		String cookieStr = "eas_sid=c1W5J8g1C155s5J1a2Y834r4K0; pgv_pvid=6229304748; pgv_pvi=4063204352; tvfe_boss_uuid=cf6705d65812ef49; RK=FXJwrAcnef; ptcz=afb3ad2c1963b13ee5d6571a17735200c7d29971bf63d6b24044ef4c37618887; video_guid=671143db9d110738; video_platform=2; ts_refer=www.baidu.com/link; ts_uid=807040940; bucket_id=9231001; pgv_info=ssid=s1370424290; qv_als=lo+mRPt+cQoQ7shNA11584779740GSZ4Pw==; login_remember=qq; _qpsvr_localtk=0.016026140085167118; pgv_si=s2560611328; main_login=qq; vqq_vuserid=149223809; vqq_vusession=4oE1q3QNyfE2FQ_1OBGPLw..; vqq_access_token=C5863A8CA68EF35970DCB64A7F2EB60D; vqq_openid=338A829D4F3BA819F72613540A0D3EBA; vqq_appid=101483052; qq_nick=%E7%88%B1%E8%90%9D%E8%8E%89%E7%9C%9F%E6%98%AF%E5%A4%AA%E5%A5%BD%E4%BA%86; qq_head=http://thirdqq.qlogo.cn/g?b=oidb&k=JaEiafVv6UXLCwDibu14dCPw&s=640&t=1555626538; lw_nick=%E7%88%B1%E8%90%9D%E8%8E%89%E7%9C%9F%E6%98%AF%E5%A4%AA%E5%A5%BD%E4%BA%86|0|http://thirdqq.qlogo.cn/g?b=oidb&k=JaEiafVv6UXLCwDibu14dCPw&s=640&t=1555626538|0; uid=226280444; ptag=|movie_v3_new:img:蓝月; ts_last=v.qq.com/x/cover/mzc00200zoxcyzd.html; ad_play_index=89";
		String cookieStr = "";
		String[] split3 = cookieStr.split("; ");
		Map<String, String> cookieMap = new HashMap<String, String>();
		if(cookieStr != "" && cookieStr != null) {
			for(String str : split3) {
				cookieMap.put(str.split("=")[0], str.split("=")[1]);
			}
		}
		
		
		HttpRequest post = HttpUtil.createPost("https://vd.l.qq.com/proxyhttp");
		post.header("Referer" , url);
		String buildParam = buildParam(coverid, flowid, url, rfid, vid, cKey, tm , cookieMap);
		post.body(buildParam);
		//执行
		String res = post.execute().body();
		System.out.println(res);
		JSONArray ul = JSONObject.parseObject(res).getJSONObject("vinfo").getJSONObject("vl").getJSONArray("vi").getJSONObject(0).getJSONObject("ul").getJSONArray("ui");
		JSONObject ui = ul.getJSONObject(ul.size()-1);
		System.out.println(ui.getString("url") + ui.getJSONObject("hls").getString("pt"));
		return null;
	}

	public static String get(String address) {
		return new TencentVideo(address).execute();
	}
	
	public static void main(String[] args) {
		TencentVideo.get("https://v.qq.com/x/cover/nfdpz32y9qn8mkj.html");
	}
	
	
	public String buildParam(String coverid,String flowid,String url,String rfid , String vid , String cKey , String tm , Map<String, String> cookieMap) {
		
		boolean haveCookie = false;
		if(!cookieMap.isEmpty()) {
			haveCookie = true;
		}
		
		
		StringBuffer adparam = new StringBuffer();
		adparam.append("ad_type=LD|KB|PVL").append("&");
		adparam.append("adaptor=2").append("&");
		adparam.append("appversion="+ appVersion).append("&");
		adparam.append("chid=0").append("&");
		adparam.append("coverid=" + coverid).append("&");
		adparam.append("dtype=1").append("&");
		adparam.append("flowid=" + flowid).append("&");
		adparam.append("from=0").append("&");
		adparam.append("guid=" + "d1384f3028935f6465a473a5a07113b5").append("&");
		adparam.append("live=0").append("&");
		adparam.append("pf=in").append("&");
		adparam.append("platform=" + platform).append("&");
		adparam.append("pf_ex=pc").append("&");
		adparam.append("plugin=1.0.0").append("&");
		adparam.append("pt=").append("&");
		adparam.append("pu=0").append("&");
		adparam.append("refer=" + url).append("&");
		adparam.append("req_type=1").append("&");
		adparam.append("resp_type=json").append("&");
		adparam.append("rfid=" + rfid).append("&");
		adparam.append("tpid=1").append("&");
		adparam.append("ty=web").append("&");
		adparam.append("url=" + url).append("&");
		adparam.append("v=" + v).append("&");
		adparam.append("vid=" + vid).append("&");
		adparam.append("vptag=www_baidu_com");
		if(haveCookie) {
			String uid = cookieMap.get("vqq_vuserid");
			String tkn = cookieMap.get("vqq_vusession");
			String opid = cookieMap.get("vqq_openid");
			String lt = cookieMap.get("main_login");
			String atkn = cookieMap.get("vqq_access_token");
			String appid = cookieMap.get("vqq_appid");
			
			adparam.append("&");
			adparam.append("uid=" + uid).append("&");
			adparam.append("tkn=" + tkn).append("&");
			adparam.append("lt=" + lt).append("&");
			adparam.append("opid=" + opid).append("&");
			adparam.append("atkn=" + atkn).append("&");
			adparam.append("appid=" + appid);
		}
			
		
		
		
		StringBuffer vinfoparam = new StringBuffer();
		vinfoparam.append("appVer=" + v).append("&");
		vinfoparam.append("cKey=" + cKey).append("&");
		vinfoparam.append("charge=0").append("&");
		vinfoparam.append("defaultfmt=auto").append("&");
		vinfoparam.append("defn=fhd").append("&");//取值：fhd（蓝光） shd（超清）   hd（高清）  sd（标清）
		vinfoparam.append("defnpayver=1").append("&");
		vinfoparam.append("defsrc=1").append("&");
		vinfoparam.append("dlver=2").append("&");
		vinfoparam.append("drm=32").append("&");
		vinfoparam.append("dtype=3").append("&");
		vinfoparam.append("ehost=" + url).append("&");
		vinfoparam.append("encryptVer=" + encryptVer).append("&");
		vinfoparam.append("fhdswitch=0").append("&");
		vinfoparam.append("flowid=" + flowid).append("&");
		vinfoparam.append("fp2p=1").append("&");
		vinfoparam.append("guid=" + "d1384f3028935f6465a473a5a07113b5").append("&");
		vinfoparam.append("hdcp=0").append("&");
		vinfoparam.append("host=v.qq.com").append("&");
		vinfoparam.append("isHLS=1").append("&");
		
		if(haveCookie) {
			String uid = cookieMap.get("vqq_vuserid");
			String tkn = cookieMap.get("vqq_vusession");
			String opid = cookieMap.get("vqq_openid");
			String lt = cookieMap.get("main_login");
			String atkn = cookieMap.get("vqq_access_token");
			String appid = cookieMap.get("vqq_appid");
			
			String logintoken = "{\"main_login\":\""+ lt +"\",\"openid\":\""+ opid +"\",\"appid\":\""+ appid +"\",\"access_token\":\""+atkn+"\",\"vuserid\":\""+uid+"\",\"vusession\":\""+tkn+"\"}";
			vinfoparam.append("logintoken=" + URLEncoder.encode(logintoken)).append("&");
		}
		
		
		vinfoparam.append("otype=ojson").append("&");
		vinfoparam.append("platform=" + platform).append("&");
		vinfoparam.append("refer=v.qq.com").append("&");
		vinfoparam.append("sdtfrom=v1010").append("&");
		vinfoparam.append("show1080p=1").append("&");
		vinfoparam.append("spadseg=1").append("&");
		vinfoparam.append("spau=1").append("&");
		vinfoparam.append("spaudio=15").append("&");
		vinfoparam.append("spgzip=1").append("&");
		vinfoparam.append("sphls=2").append("&");
		vinfoparam.append("sphttps=1").append("&");
		vinfoparam.append("spwm=4").append("&");
		vinfoparam.append("tm=" + tm).append("&");
		vinfoparam.append("unid=cdbb5f0a6aad11ea981ca042d48ad00a").append("&");
		vinfoparam.append("vid=" + vid);
		
		
		//构造参数时buid可取值
		//1.vinfoad（包含视频信息和广告）
		//2.onlyvinfo（只包含视频信息）
		return "{\"buid\":\"vinfoad\",\"adparam\":\"" + adparam.toString() + "\",\"vinfoparam\":\"" + vinfoparam.toString() + "\"}";
	}
}
