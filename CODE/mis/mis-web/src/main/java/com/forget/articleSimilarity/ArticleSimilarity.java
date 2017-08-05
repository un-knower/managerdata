package com.forget.articleSimilarity;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import com.forget.articleModel.ArticleModel;
import com.forget.articleWeight.ArticleWeight;
import com.forget.itemModel.ItemColl;
import com.forget.itemModel.ItemColls;

/*
 * 计算文章相似度
 */
public class ArticleSimilarity {

	public static void main(String[] args) {
		System.setProperty("appPath", "D://新建文件夹");
		Double articleSimilarity = articleSimilarity("外观大气，内饰也挺好外观大气，内饰也挺好", "内饰不错，外观也不错的外观大气，内饰也挺好");
		System.out.println(articleSimilarity);
	}
	
	public static Double articleSimilarity(String src,String des){
		
		ItemColls itemColls = ItemColls.getItemColls();
		ItemColl itemColl = itemColls.getItemColl();// 词库
		ArticleModel articleMDL1 = new ArticleModel();
		articleMDL1.setContent(src);
		ArticleWeight.updateArticleWeight(articleMDL1, itemColl, null, 7, false, true);

		ArticleModel articleMDL2 = new ArticleModel();
		articleMDL2.setContent(des);
		ArticleWeight.updateArticleWeight(articleMDL2, itemColl, null, 7, false, false);
		
		Double calcArticleSimilarity = ArticleSimilarity.calcArticleSimilarity(articleMDL1, articleMDL2);
		
		DecimalFormat df = new DecimalFormat("#.0000");
		
		String format = df.format(calcArticleSimilarity);
		
		calcArticleSimilarity = Double.valueOf(format);
		
		return calcArticleSimilarity;
	}
	
	/*
	 * src 源文章对象
	 * des 目标文章对象
	 */
	public static Double calcArticleSimilarity(ArticleModel src,ArticleModel des){
		
		Map<String, Double> map = commonKeyword(src,des);
		
		//累加两篇文章所有共同出现的词的权重 
		Double commonWeight = map.get("commonWeight");
		Double weightSum = map.get("weightSum");
		Double similarity = Double.valueOf(0);
		if(weightSum > 0){
			similarity = commonWeight/weightSum;
		}
		return similarity;
	}
	
	/*
	 * 统计两篇文章中共有词权重
	 * 全部词的权重和 
	 */
	public static Map<String,Double> commonKeyword(ArticleModel src,ArticleModel des){
		
		Map<String,Double> map = new HashMap<String,Double>();
		
		//共有词权重
		double commonWeight = 0;
		//总权重
		double weightSum = 0;
		
		Map<String, Double> srcKeywordDict = src.getKeywordDict();
		Map<String, Double> desKeywordDict = des.getKeywordDict();
		String keyword = "";
		for (Map.Entry<String, Double> entry : srcKeywordDict.entrySet()) {
			keyword = entry.getKey();
			if(desKeywordDict.containsKey(keyword)){
				commonWeight += entry.getValue() + desKeywordDict.get(keyword);
			}
			weightSum += entry.getValue();
		}
		
		for (Map.Entry<String, Double> entry : desKeywordDict.entrySet()) {
			weightSum += entry.getValue();
		}
		map.put("weightSum", weightSum);
		map.put("commonWeight", commonWeight);
		
		return map;
	}
	
	/*
	 * 统计两篇文章中共有词权重
	 * 全部词的权重和 
	 */
	public static Double commonWordWing(Map<String, Double> srcKeywordDict,Map<String, Double> desKeywordDict ){
		DecimalFormat df = new DecimalFormat("#.00");
		//共有词权重
		double commonWeight = 0;
		//总权重
		double weightSum = 0;
		
		String keyword = "";
		for (Map.Entry<String, Double> entry : srcKeywordDict.entrySet()) {
			keyword = entry.getKey();
			if(desKeywordDict.containsKey(keyword)){
				commonWeight += entry.getValue() + desKeywordDict.get(keyword);
			}
			weightSum += entry.getValue();
		}
		
		for (Map.Entry<String, Double> entry : desKeywordDict.entrySet()) {
			weightSum += entry.getValue();
		}
		//累加两篇文章所有共同出现的词的权重 
		Double similarity = Double.valueOf(0);
		if(weightSum > 0){
			similarity = commonWeight/weightSum;
		}
		
		similarity = Double.valueOf(df.format(similarity));
		
		return similarity;
	}
}
