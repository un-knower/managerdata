package org.pcloud.common.json;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RespResult {
	public static  RespResult instance = null;
	
	public static RespResult  getInstance(){
		return new RespResult();
	}
	private Map<String, Object> info = null;

	private Map<String, Object> getInfo() {
		if(info == null){
			info = new HashMap<String, Object>();
		}
		return info;
	}

	public RespResult setInfo(String str, Object obj) {
		getInfo();
		info.put(str, obj);
		return this;
	}
	  
	
	public String toJson() {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonStr = null;
		try {
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			jsonStr = objectMapper.writeValueAsString(info);
		} catch (Throwable t) {
			
		}
		info.clear();  info = null;
		return jsonStr;
	}
}
