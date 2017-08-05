package com.forget.dataSort;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

import com.forget.itemModel.ItemModel;

/*
 * 比较器
 */
public class MapValueObjectComparator implements Comparator<Map.Entry<String, ItemModel>> {

	public int compare(Entry<String, ItemModel> me1, Entry<String, ItemModel> me2) {

		ItemModel test1 = (ItemModel) me1.getValue();
		ItemModel test2 = (ItemModel) me2.getValue();
		
		//根据有效值   倒序排列
		return test2.getValidCount().compareTo(test1.getValidCount());
	}
}