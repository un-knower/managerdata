package com.forget.dataSort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SortByValidValueDouble {

	/**
	 * 使用 Map按value进行排序 
	 * type double
	 * @param map
	 * @return
	 */
	public static Map<String, Double> sortMapByValue(Map<String, Double> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		List<Map.Entry<String, Double>> entryList = new ArrayList<Map.Entry<String, Double>>(
				oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparator());

		Iterator<Map.Entry<String, Double>> iter = entryList.iterator();
		Map.Entry<String, Double> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}
	
}
