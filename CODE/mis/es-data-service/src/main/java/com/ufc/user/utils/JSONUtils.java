package com.ufc.user.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONUtils {
	
	public static List<String> sortList(List<String> list){
		Collections.sort(list);
		return list;
	}

	public static List<Map.Entry<String,Long>> sortMap(Map<String, Long> map){
		List<Map.Entry<String,Long>> list = new ArrayList<Map.Entry<String,Long>>(map.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String, Long>>() {
            //升序排序
            public int compare(Entry<String, Long> o1,
                    Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
        return list;
	}
	
	/**
	 * JSON字符串转换成 List<String>集合类型
	 * @param jsons
	 * @return List<String>
	 */
    public static Collection<String> asCollectionString(String jsons) {
    	JSONArray jsonArray = JSON.parseArray(jsons);
    	Object[] obj = jsonArray.toArray();
    	Collection<String> list = new ArrayList<String>();
    	if(obj.length > 0){
    		for(int i=0; i < obj.length; i++){
    			list.add(obj[i].toString());
    		}
    	}
    	return list;
    }
    
    /**
	 * JSON字符串转换成 List<String>集合类型
	 * @param jsons
	 * @return List<String>
	 */
    public static Collection<?> asCollection(String jsons) {
    	
    	Collection<?> collection = JSON.parseArray(jsons, String.class);
    	return collection;
    }
    
    /**
     * 验证字符串是否为jsonObject格式
     * @param paramJson
     * @return
     */
    public static boolean checkJsonObject(String paramJson){
		boolean flag = false;
		try {
			JSONObject json = JSON.parseObject(paramJson);
			if(json.keySet().size()>0){
				flag = true;
			}
		} catch (Exception e1) {
			
		}
		return flag;
	}
	
    /**
     * 验证字符串是否为jsonArray格式
     * @param paramJson
     * @return
     */
	public static boolean checkJsonArray(String paramJson){
		boolean flag = false;
		try {
			Collection<?> collection = JSON.parseArray(paramJson, String.class);
			if(collection.size() > 0){
				flag = true;
			}
		} catch (Exception e1) {
			
		}
		return flag;
	}
	
    public static void main(String[] args) {
    	List<String> x = new ArrayList<String>();
    	x.add("2015-11-19 11:22:33");
    	x.add("2015-11-19 11:22:34");
    	x.add("2015-14-19 11:22:33");
    	x.add("2015-13-22 11:22:33");
    	for(int i = 0; i < x.size(); i++){
    		System.out.println(x.get(i));
    	}
    	System.out.println("-------------------");
    	sortList(x);
    	for(int i = 0; i < x.size(); i++){
    		System.out.println(x.get(i));
    	}
	}
}
