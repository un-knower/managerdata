package com.forget.articleWeight;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.forget.articleModel.ArticleModel;
import com.forget.bondModel.BondColl;
import com.forget.dataSort.SortByValidValueDouble;
import com.forget.forgetDAL.ForgetDAL;
import com.forget.itemModel.ItemColl;
import com.forget.itemModel.ItemColls;
import com.forget.itemModel.ItemModel;
import com.forget.keywordSegment.KeywordSegment;
import com.forget.utils.DateTest;

/*
 * 根据文章获取  出现词的权重
 */
public class ArticleWeight {

	public static void main(String[] args) {
		System.setProperty("appPath", "D://新建文件夹");
		Map<String, Double> keyword = getKeyword("外观大气，内饰漂亮");
		for(Map.Entry<String, Double> entry : keyword.entrySet()){
			System.out.println(entry.getKey()+"==="+entry.getValue());
		}
	}
	
	public static Map<String,Double> getKeyword(String srcArt){
		
		ItemColls itemColls = ItemColls.getItemColls();
		ItemColl itemColl = itemColls.getItemColl();
		ArticleModel articleMDL1 = new ArticleModel();
		articleMDL1.setContent(srcArt);
		Map<String, Double> map = updateArticleWeight(articleMDL1, itemColl, null, 7, false, true);

		return map;
	}
	
	/*
	 * 更新文章中 权重词典 
	 */
	@SuppressWarnings("null")
	public static Map<String, Double> updateArticleWeight(ArticleModel articleMDL,ItemColl itemColl,BondColl bondColl,int maxKeywordLength,boolean bUpdateCharBondColl, boolean bUpdateKeyWordColl){
		
		//权重词典
		Map<String, Double> keywordDict = articleMDL.getKeywordDict();
		
		//文章标题分词
		List<String> titleToKeywordList  = KeywordSegment.segment(articleMDL.getTitle(), itemColl, bondColl, maxKeywordLength, bUpdateCharBondColl, bUpdateKeyWordColl);
		
		//文章标题分词
		List<String> contentToKeywordList = KeywordSegment.segment(articleMDL.getContent(), itemColl, bondColl, maxKeywordLength, bUpdateCharBondColl, bUpdateKeyWordColl);
		double dLogTotlCount = 1.0/(1.0-ForgetDAL.rememberValue(7));
		Double weight = Double.valueOf(0);
		//遍历文章内容分词结果  存入权重词典
		for (String string : contentToKeywordList) {
			if(keywordDict != null){
				if(keywordDict.containsKey(string)){
					weight = keywordDict.get(string);
					ItemModel validCount = itemColl.getKeyItemColl().get(string);
					if(validCount == null){
						keywordDict.put(string,weight+Double.valueOf(0));
					}else{
						keywordDict.put(string,weight-Math.log(validCount.getValidCount()/dLogTotlCount));
					}
				}else{
					keywordDict.put(string,Double.valueOf(1));
				}
			}else{
				keywordDict.put(string,Double.valueOf(1));
			}
		}
		if(titleToKeywordList !=null && titleToKeywordList.size() > 0){
			//遍历文章标题分词结果  存入权重词典
			for (String string :titleToKeywordList) {
				if(!keywordDict.containsKey(string)){
					keywordDict.put(string,Double.valueOf(1));
				}else{
					weight = keywordDict.get(string);
					keywordDict.put(string,weight+Double.valueOf(-Math.log(itemColl.getKeyItemColl().get(string).getValidCount()/dLogTotlCount)));
				}
			}
		}
		DecimalFormat df = new DecimalFormat("#.00");
		String str = "";
		Set<String> stopWords = itemColl.getStopWords();
		Map<String,Double> newMap = new HashMap<String,Double>();
		for(Map.Entry<String, Double> entry : keywordDict.entrySet()){
			str = entry.getKey();
			
			// ???????
//			if(str.length() > 1){
			if(str.length() > 1 && !stopWords.contains(str)){
				newMap.put(str, Double.valueOf(df.format(entry.getValue())));
			}
		}
		
		Map<String, Double> map = SortByValidValueDouble.sortMapByValue(newMap);
		return map;
	}
	
	/**
	 * 词权重
	 * 
	 * @param keyword
	 * @return
	 */
	public static JSONObject keywordWeight(String keyword) {
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();
		if (compare_date) {
			if (keyword != null && !keyword.trim().equals("")) {
				double dLogTotlCount = 1.0 / (1.0 - ForgetDAL.rememberValue(7));
				ItemColls itemColls = ItemColls.getItemColls();
				ItemColl itemColl = itemColls.getItemColl();// 词库
				Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();
				double keywordWeight = 1.0;
				if (keyItemColl != null && !keyItemColl.isEmpty()) {
					if (keyItemColl.containsKey(keyword)) {
						ItemModel itemModel = keyItemColl.get(keyword);
						keywordWeight = -Math.log(itemModel.getValidCount()
								/ dLogTotlCount);
					}
				}
				json.put("state", "success");
				json.put("msg", "");
				json.put("weight", keywordWeight);
			} else {
				json.put("state", "error");
				json.put("msg", "参数不合法.");
				json.put("weight", "");
			}
		} else {
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
			json.put("weight", "");
		}

		return json;
	}
	
}
