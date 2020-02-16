package com.Yang.commom.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class DomainName {

	static Map<String, Integer> values;

	static {
		if (values == null) {
			values = new HashMap<String, Integer>();
			values.put("douyin.com", 1);
			values.put("chenzhongtech.com", 2);
			values.put("pipix.com", 3);
			values.put("quanmin.hao222.com", 4);
			values.put("haokan.baidu.com",4);
			values.put("3qtt.cn", 5);
			values.put("shua8cn.com", 6);
			values.put("weishi.qq.com", 7);
			values.put("xiaokaxiu.com", 8);
			values.put("ixigua.com", 9);
			values.put("toutiaoimg.com", 9);
			values.put("izuiyou.com", 10);
			values.put("huoshan.com", 11);
			values.put("pearvideo.com", 12);
			values.put("miaopai.com", 13);
			values.put("xiaoying.tv", 14);
			values.put("inke.cn", 15);
			values.put("xunlei.com", 16);
			values.put("b23.tv", 17);
			values.put("bilibili.com", 17);
			values.put("wide.meipai.com", 18);
			values.put("post.mp.qq.com", 19);
			values.put("eyepetizer.net", 20);
			values.put("uc.cn", 21);
			values.put("kg2.qq.com", 22);
		}
	}

	public static Integer getType(String url) {
		if(Is.Null(url)) {
			return 0;
		}
		Iterator<String> keySet = values.keySet().iterator();
		while (keySet.hasNext()) {
			String key = keySet.next();
			if (url.contains(key)) {
				return values.get(key);
			}
		}
		return 0;
	}
	
	

}
