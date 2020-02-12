package com.Yang.commom.utils;

import java.util.HashMap;
import java.util.Map;

import com.Yang.util.IsNull;
import com.alibaba.fastjson.JSON;

public class V{
	private int code;
	private String msg;
	private Map<String, Object> data = new HashMap();

	

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return this.msg;
	}

	public V setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Map<String, Object> getData() {
		return this.data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public int setInteger(String key, Integer value) {
		if ((IsNull.isNullOrEmpty(key)) || (IsNull.isNullOrEmpty(value))) {
			return -1;
		}
		if (this.data.containsKey(key)) {
			return -2;
		}
		this.data.put(key, value);
		return 1;
	}

	public int setIntegerCanOverride(String key, Integer value) {
		if ((IsNull.isNullOrEmpty(key)) || (IsNull.isNullOrEmpty(value))) {
			return -1;
		}
		this.data.put(key, value);
		return 1;
	}

	public Integer getInteger(String key) {
		return (Integer) this.data.get(key);
	}

	public int setString(String key, String value) {
		if ((IsNull.isNullOrEmpty(key)) || (value == null)) {
			return -1;
		}
		if (this.data.containsKey(key)) {
			return -2;
		}
		this.data.put(key, value);
		return 1;
	}

	public int setStringCanOverride(String key, String value) {
		if ((IsNull.isNullOrEmpty(key)) || (value == null)) {
			return -1;
		}
		this.data.put(key, value);
		return 1;
	}

	public String getString(String key) {
		return (String) this.data.get(key);
	}

	public V setObject(String key, Object value) {
		if ((IsNull.isNullOrEmpty(key)) || (value == null)) {
			return this;
		}
		if (this.data.containsKey(key)) {
			return this;
		}
		this.data.put(key, value);
		return this;
	}

	public int setObjectCanOverride(String key, Object value) {
		if ((IsNull.isNullOrEmpty(key)) || (value == null)) {
			return -1;
		}
		this.data.put(key, value);
		return 1;
	}

	public Object getObject(String key) {
		return this.data.get(key);
	}

	public Object removeObject(String key) {
		return this.data.remove(key);
	}

	public String toString() {
		return "ResponseObject [code=" + this.code + ", msg=" + this.msg + ", data=" + this.data + "]";
	}

	public String toJson() {
		return JSON.toJSONString(this);
	}
	
	public static V isok() {
		V v = new V();
		v.code = 200;
		return v;
	}
	
	public static V isok(String msg) {
		return isok().setMsg(msg);
	}
	
	public static V error() {
		V v = new V();
		v.code = 100;
		return v;
	}
	
	public static V error(String msg) {
		return error().setMsg(msg);
	}
	
	public static V urlisnone() {
		V v = new V();
		v.code = -100;
		v.msg = "视频地址不可为空";
		return v;
	}
	public static V typeisnone() {
		V v = new V();
		v.code = -100;
		v.msg = "视频类型不可为空";
		return v;
	}
	
	public static V unkownType() {
		V v = new V();
		v.code = -100;
		v.msg = "未知的视频类型";
		return v;
	}
}
