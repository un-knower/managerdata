package com.forget.keywordSegment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.forget.bondModel.BondColl;
import com.forget.forgetDAL.ForgetDAL;
import com.forget.itemModel.ItemColl;
import com.forget.itemModel.ItemColls;
import com.forget.utils.DateTest;

/**
 * 分词
 * 
 * @author yangchao
 *
 */
public class KeywordSegment {

	/**
	 * 分词
	 * 
	 * @param content
	 * @param maxKeywordLength
	 * @return
	 */
	public static JSONObject toSegment(String content, int maxKeywordLength) {
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();
		if (compare_date) {
			if (content != null && !content.trim().equals("")) {
				if (maxKeywordLength <= 1) {
					maxKeywordLength = 6;
				} else {
					maxKeywordLength = maxKeywordLength - 1;
				}
				ItemColls itemColls = ItemColls.getItemColls();
				ItemColl itemColl = itemColls.getItemColl();
				List<String> segment = segment(content, itemColl, null,
						maxKeywordLength, false, false);
				json.put("state", "success");
				json.put("msg", "");
				json.put("segment", segment);
			} else {
				json.put("state", "error");
				json.put("msg", "参数不合法.");
				json.put("segment", null);
			}
		} else {
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
			json.put("segment", null);
		}
		return json;
	}
	
	/**
	 * 分词
	 * 
	 * @param txt 待分词文本
	 * @param itemColl 词库
	 * @param bondColl
	 * @param maxKeywordLength 词的最大长度
	 * @param bUpdateCharBondColl 是否更新邻键集合
	 * @param bUpdateKeyWordColl 是否更新词库
	 * @return
	 */
	public static List<String> segment(String txt, ItemColl itemColl,
			BondColl bondColl, int maxKeywordLength,
			boolean bUpdateCharBondColl, boolean bUpdateKeyWordColl) {
		if (txt == null) {
			return null;
		}
		if (txt.length() == 0) {
			maxKeywordLength = txt.length();
		}

		/*
		 * if(bUpdateCharBondColl || bUpdateKeyWordColl){
		 * KeywordColl.txtContext2BondColl(txt,bondColl,itemColl,true); }
		 */

		double dLogTotalCount = Math.log(1.0 / (1.0 - ForgetDAL
				.rememberValue(7)));
		// 用于缓存各个位置对应的最佳分词结果
		Map<Integer, List<String>> objKeyWordBufferDict = new HashMap<Integer, List<String>>();
		// 用于缓存各个位置对应的概率积，避免不必要的重复计算
		Map<Integer, Double> objKeyWordValueDict = new HashMap<Integer, Double>();

		List<String> objKeyWordList = null;
		// 逐字扫描
		for (int i = 0; i < txt.length(); i++) {
			objKeyWordList = new ArrayList<String>();
			double dKeyWordValue = 0;
			for (int len = 0; len <= Math.min(i, maxKeywordLength); len++) {
				int startpos = i - len;

				String keyword = txt.substring(startpos, i + 1);
				// 检索以当前字结尾、指定字长内的所有词
				if (len > 0 && !itemColl.getKeyItemColl().containsKey(keyword))
					continue;

				double tempValue = 0;

				if (itemColl.getKeyItemColl().containsKey(keyword)) {
					// 计算该词的概率，此处使用对数形式计算，化乘除为加减的同时避免连乘导致的数值过小
					tempValue = -(dLogTotalCount - Math.log(itemColl
							.getKeyItemColl().get(keyword).getValidCount()));

				}
				// 读取该词之前所有词的概率积对数，并累加上当前词的概率
				if (objKeyWordValueDict.containsKey(startpos - 1)) {
					tempValue += objKeyWordValueDict.get(startpos - 1);
					// 判断该词的概率是否最大
					if (dKeyWordValue == 0 || tempValue > dKeyWordValue) {
						dKeyWordValue = tempValue;
						objKeyWordList = new ArrayList<String>(
								objKeyWordBufferDict.get(startpos - 1));
						objKeyWordList.add(keyword);
					}
				} else {
					// 如果此词是由句首开始的，则无需读取缓存
					if (dKeyWordValue == 0 || tempValue > dKeyWordValue) {
						dKeyWordValue = tempValue;
						objKeyWordList = new ArrayList<String>();
						objKeyWordList.add(keyword);
					}
				}
			}

			// 记录本位置的最佳结果
			objKeyWordBufferDict.put(i, objKeyWordList);
			objKeyWordValueDict.put(i, dKeyWordValue);
		}

		// 最后一个字的最佳结果也是整句的最佳结果，即为所求。
		return objKeyWordBufferDict.get(txt.length() - 1);
	}
}
