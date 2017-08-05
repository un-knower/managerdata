package org.pcloud.common.json;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
public class JsonHelper {
	/***
	 * 将json字符串转换成Map对象
	 * @param str
	 * 			{"name":"peter","sex":"男"}
	 * @return
	 * 		Map<?,?> 集合对象
	 * @throws Exception
	 */
	public static Map<?,?> string2Map(String str) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		return objectMapper.readValue(str.replaceAll("\r","").replaceAll("\n","").replaceAll("\t",""), Map.class);  
	}
	
	/***
	 * 将json字符串转换成以Map对象为子集的List集合
	 * @param str
	 * 		[{"name":"peter","sex":"男"},{"name":"peter1","sex":"男",...]
	 * @return
	 * 		List<Map<?,?>>以Map对象为子集的List集合对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<?,?>> string2List_Map(String str) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		return objectMapper.readValue(str.replaceAll("\r","").replaceAll("\n","").replaceAll("\t",""), List.class);  
	}
	
	
	/**
	 * 将json字符串转换成List结合
	 * @param str
	 * 		["peter","peter1",...]
	 * @return
	 * 		List<String> list集合对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<String> string2List(String str) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		return objectMapper.readValue(str.replaceAll("\r","").replaceAll("\n","").replaceAll("\t",""), List.class);  
	}
	
}
