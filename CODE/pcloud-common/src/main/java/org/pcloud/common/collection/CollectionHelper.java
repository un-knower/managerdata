package org.pcloud.common.collection;

import java.util.List;
public class CollectionHelper {
	
	/**
	 * 将list对象转换成，以某种分割符分割的字符串
	 * @param list
	 * 			集合对象
	 * @param flag
	 * 			分割符
	 * @return
	 * 		String 类型字符串
	 */
	public static String list2String(List<String> list,String flag) {
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size() - 1) {
					sb.append(list.get(i) + flag);
				} else {
					sb.append(list.get(i));
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 将array类型的对象转换成，以某种分割符分割的字符串
	 * @param array
	 * 			数组类型对象 Array
	 * @param flag
	 * 			分割符
	 * @return
	 */
	public static String array2String(String[] array,String flag) {
		StringBuilder sb = new StringBuilder();
		if (array != null && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				if (i < array.length - 1) {
					sb.append(array[i] + flag);
				} else {
					sb.append(array[i]);
				}
			}
		}
		return sb.toString();
	}
}
