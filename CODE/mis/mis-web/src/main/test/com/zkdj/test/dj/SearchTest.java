package com.zkdj.test.dj;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ufc.user.utils.HttpClientUtil;

public class SearchTest {

	public static void main(String[] args) {
		String httpUrl = "http://114.55.4.218:8001/Api/Search";
		String keyword = "学校不同，规定不同，有些规定总相似比30%以下不计入个人信用分值，有些规定总相似比20%以下，甚至有些10%以下。而期刊一般总相似比超过30%则直接退稿。具体规定请参考各单位具体要求。";
		Map<String, String> maps = new HashMap<>();
		maps.put("source", keyword);
		String result=HttpClientUtil.getInstance().sendHttpPost(httpUrl, maps);
		JSONObject json=JSONObject.parseObject(result);
		System.out.println(json.toJSONString());
	}
}
