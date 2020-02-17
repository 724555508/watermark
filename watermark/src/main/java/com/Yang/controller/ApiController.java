package com.Yang.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.dromara.soul.client.common.annotation.SoulClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Yang.commom.exception.MessageException;
import com.Yang.commom.utils.DomainName;
import com.Yang.commom.utils.Is;
import com.Yang.commom.utils.V;
import com.Yang.watermark.Bilibili;
import com.Yang.watermark.Bixin;
import com.Yang.watermark.Douyin;
import com.Yang.watermark.Huoguo;
import com.Yang.watermark.Huoshan;
import com.Yang.watermark.Inke;
import com.Yang.watermark.Kaiyan;
import com.Yang.watermark.Kandian;
import com.Yang.watermark.Kuaishou;
import com.Yang.watermark.Lishipin;
import com.Yang.watermark.Miaopai;
import com.Yang.watermark.Pipixia;
import com.Yang.watermark.Quanmin;
import com.Yang.watermark.Quanminkg;
import com.Yang.watermark.Qutoutiao;
import com.Yang.watermark.Shuabao;
import com.Yang.watermark.Uc;
import com.Yang.watermark.Weishi;
import com.Yang.watermark.Wide;
import com.Yang.watermark.Xiaokaxiu;
import com.Yang.watermark.Xiaoying;
import com.Yang.watermark.Xigua;
import com.Yang.watermark.Xunlei;
import com.Yang.watermark.Zuiyou;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

	@PostMapping("/get")
	@SoulClient(path = "/api/get" , desc = "parse_watermark")
	public V parse(String url , Integer type) {
		if(Is.Null(url)) {
			return V.urlisnone();
		}
		if(Is.Null(type)) {
			type = getType(url);
		}
		try {
			switch (type) {
			case 1:
				//抖音
				return V.isok("请求成功").setObject("url", Douyin.get(url));
			case 2:
				//快手
				return V.isok("请求成功").setObject("url", Kuaishou.get(url));
			case 3:
				//皮皮虾
				return V.isok("请求成功").setObject("url", Pipixia.get(url));
			case 4:
				//全民小视频
				return V.isok("请求成功").setObject("url", Quanmin.get(url));
			case 5:
				//趣头条视频
				return V.isok("请求成功").setObject("url", Qutoutiao.get(url));
			case 6:
				//刷宝
				return V.isok("请求成功").setObject("url", Shuabao.get(url));
			case 7:
				//微视
				return V.isok("请求成功").setObject("url", Weishi.get(url));
			case 8:
				//小咖秀
				return V.isok("请求成功").setObject("url", Xiaokaxiu.get(url));
			case 9:
				//西瓜
				return V.isok("请求成功").setObject("url", Xigua.get(url));
			case 10:
				//最右
				return V.isok("请求成功").setObject("url", Zuiyou.get(url));
			case 11:
				//火山
				return V.isok("请求成功").setObject("url", Huoshan.get(url));
			case 12:
				//梨视频
				return V.isok("请求成功").setObject("url", Lishipin.get(url));
			case 13:
				//秒拍
				return V.isok("请求成功").setObject("url", Miaopai.get(url));
			case 14:
				//小影
				return V.isok("请求成功").setObject("url", Xiaoying.get(url));
			case 15:
				//映客
				return V.isok("请求成功").setObject("url", Inke.get(url));
			case 16:
				//迅雷
				return V.isok("请求成功").setObject("url", Xunlei.get(url));
			case 17:
				//哔哩哔哩
				return V.isok("请求成功").setObject("url", Bilibili.get(url));
			case 18:
				//wide
				return V.isok("请求成功").setObject("url", Wide.get(url));
			case 19:
				//QQ看点
				return V.isok("请求成功").setObject("url", Kandian.get(url));
			case 20:
				//开眼
				return V.isok("请求成功").setObject("url", Kaiyan.get(url));
			case 21:
				//UC
				return V.isok("请求成功").setObject("url", Uc.get(url));
			case 22:
				//全民k歌
				return V.isok("请求成功").setObject("url", Quanminkg.get(url));
			case 23:
				//比心
				return V.isok("请求成功").setObject("url", Bixin.get(url));
			case 24:
				//火锅视频
				return V.isok("请求成功").setObject("url", Huoguo.get(url));
			default:
				return V.unkownType();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return V.error("解析失败");
		}
		
	}
	
	
	Integer getType(String url) {
		Integer type = DomainName.getType(url);
		if(type == 0) {
			throw new MessageException("未能识别的url，请手动选择类型");
		}
		return type;
	}
}
