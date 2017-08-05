package com.forget.dataSort;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;


/*
 * map集合排序  根据value
 */
public class MapValueComparator implements Comparator<Map.Entry<String, Double>> {

	/*
	 * 比较器
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Entry<String, Double> me1, Entry<String, Double> me2) {

		Double test1 = me1.getValue();
		Double test2 = me2.getValue();
		
		//根据有效值   正序排列
		return test2.compareTo(test1);
	}
	
}
