package com.Yang.controller;

import org.dromara.soul.client.common.annotation.SoulClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Yang.commom.utils.Is;
import com.Yang.commom.utils.V;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/version")
@Slf4j
public class VersionUpdateController {

	
	@PostMapping("/update")
	@SoulClient(path = "/version/update" , desc = "versionUpdate")
	public V parse(String version) {
		if(Is.Null(version)) {
			return V.error("version none");
		}
		String newVersion = "1.0.4";
		if(!version.equals(newVersion)) {
			//需要更新
			return V.isok()
					.setObject("update", true)
					.setObject("file", "https://vmake.vshidai.top/apk/__UNI__1325F5E.wgt");
		}else {
			return V.isok()
					.setObject("update", false);
		}
	}
	
}
