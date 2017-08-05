package com.forget.dataSort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.forget.itemModel.ItemModel;

/**
 * 数据集排序
 * 
 * @author yangchao
 *
 */
public class SortByValidValue {

	/**
	 * 使用 Map按value进行排序
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, ItemModel> sortMapByValue(
			Map<String, ItemModel> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<String, ItemModel> sortedMap = new LinkedHashMap<String, ItemModel>();
		List<Map.Entry<String, ItemModel>> entryList = new ArrayList<Map.Entry<String, ItemModel>>(
				oriMap.entrySet());
		Collections.sort(entryList, new MapValueObjectComparator());

		Iterator<Map.Entry<String, ItemModel>> iter = entryList.iterator();
		Map.Entry<String, ItemModel> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}

}
