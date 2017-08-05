package com.forget.sub;

import com.alibaba.fastjson.JSONObject;
import com.forget.articleSimilarity.ArticleSimilarity;
import com.forget.articleWeight.ArticleWeight;
import com.forget.keywordSegment.KeywordSegment;
import com.forget.keywords.KeywordColl;
import com.forget.sm.GetSimHash;

public class Analysis {
	public static void main(String args[]) {
		String srcFilePath = "D:\\tmp\\forget\\news.sohunews.1410807.txt";
		String keywordCollFilePath = "D:\\tmp\\forget\\test1.txt";
		String serFilePath = "D:\\tmp\\forget\\test2.txt";
		// 1、词库生成
		//keywordColl(srcFilePath,keywordCollFilePath,serFilePath);
		// 词库展示
		System.out.println(getKeywordColl(serFilePath));
	}

	/**
	 * 词库生成（250MB只是经验值，非固定）
	 * 
	 * @param srcFilePath
	 *            待处理文本文件路径（必填,格式UTF-8）;
	 * @param keywordCollFilePath
	 *            直观词库文件路径（非必填）;
	 * @param serFilePath
	 *            序列化词库路径（必填）;
	 * @return {"state":"success","msg":"ok"}
	 */
	public static JSONObject keywordColl(String srcFilePath,String keywordCollFilePath,String serFilePath){
		System.setProperty("appPath", "");
		JSONObject json = KeywordColl.toKeywordColl(srcFilePath, keywordCollFilePath, serFilePath);
		return json;
	}
	
	/**
	 * 增量更新词库(没有固定值，宜多不宜少)
	 * 
	 * @param srcFilePath
	 *            待处理文本文件路径（必填,格式UTF-8）；
	 * @param keywordCollFilePath
	 *            直观词库文件路径（非必填）；
	 * @param serFilePath
	 *            序列化词库路径（非必填[初始化词库后不需填写]）;
	 * @return
	 */
	public static JSONObject updKeywordColl(String srcFilePath,String keywordCollFilePath,String serFilePath){
		System.setProperty("appPath", serFilePath);
		JSONObject json = KeywordColl.toUpdKeywordColl(srcFilePath, keywordCollFilePath, serFilePath);
		return json;
	}
	
	/**
	 * 保存词库(将内存中的词库保存到磁盘)
	 * 
	 * @param serFilePath
	 *            直观词库文件路径（非必填）；
	 * @param keywordCollFilePath
	 *            序列化词库路径（必填）;
	 * @return
	 */
	public static JSONObject saveKeywordColl(String serFilePath,String keywordCollFilePath){
		JSONObject json = KeywordColl.saveKeywordColl(serFilePath, keywordCollFilePath);
		return json;
	}
	
	/**
	 * 删除词库（将内存中的词库清理并保存）
	 * 
	 * @param serFilePath
	 *            直观词库文件路径（非必填）；
	 * @param keywordCollFilePath
	 *            序列化词库路径（非必填）;
	 * @return
	 */
	public static JSONObject delKeywordColl(String serFilePath,String keywordCollFilePath){
		JSONObject json = KeywordColl.toDelKeywordColl(serFilePath, keywordCollFilePath);
		return json;
	}
	
	/**
	 * 初始化词库
	 * 
	 * @param serFilePath
	 *            序列化词库路径（必填）；
	 * @return {"state":"success","msg":""}
	 */
	public static JSONObject initKeywordColl(String serFilePath){
		JSONObject json = KeywordColl.toInitKeywordColl(serFilePath);
		return json;
	}
	
	/**
	 * 词库展示
	 * 
	 * @param serFilePath
	 *            序列化词库路径（必填）；
	 * @return 
	 *         {"keywordColl":{"江苏华罗贸易有限公司 ":1.0,...},"state":"success","msg":""}
	 *         如返回：{"state":"error","msg":"ok"}，则请求失败;
	 */
	public static JSONObject getKeywordColl(String serFilePath){
		JSONObject json = KeywordColl.getKeywordColl(serFilePath);
		return json;
	}
	
	/**
	 * 分词
	 * 
	 * @param content
	 *            待处理文章内容（必填）
	 * @param serFilePath
	 *            序列化词库路径（非必填[初始化词库后不需填写]）;
	 * @param maxKeywordLength
	 *            最大分词长度（经验总结：4～7）;
	 * @return {"segment":["我是","中国人"],"state":"success","msg":""}
	 */
	public static JSONObject segment(String content, String serFilePath,
			int maxKeywordLength) {
		System.setProperty("appPath", serFilePath);
		JSONObject json = KeywordSegment.toSegment(content, maxKeywordLength);
		return json;
	}
	
	/**
	 * 词权重
	 * 
	 * @param serFilePath
	 *            序列化词库路径（必填[初始化词库后不需填写]）；
	 * @param keyword
	 *            词（必填）；
	 * @return {"weight":1.0,"state":"success","msg":""}
	 */
	public static JSONObject keywordWeight(String serFilePath,String keyword){
		System.setProperty("appPath", serFilePath);
		JSONObject json = ArticleWeight.keywordWeight(keyword);
		return json;
	}
	
	/**
	 * 文档指纹
	 * 
	 * @param content
	 *            文本内容（必填）；
	 * @param serFilePath
	 *            序列化词库路径（非必填[初始化词库后不需填写]）；
	 * @return {"fingerprint":
	 *         "1100010110010010111111011101100011010001010000000000000000000000"
	 *         ,"state":"success","msg":""}
	 */
	public static JSONObject docFingerprint(String content,String serFilePath){
		System.setProperty("appPath", serFilePath);
		
		JSONObject json = GetSimHash.getSimHash(content);
		
		return json;
	}
	
	/**
	 * 根据字符串更新词库
	 * 
	 * @param content
	 *            待处理文本(必填)；
	 * @param keywordCollFilePath
	 *            直观词库文件路径（非必填）；
	 * @param serFilePath
	 *            序列化词库路径（非必填[初始化词库后不需填写]）;
	 * @return
	 */
	public static JSONObject updKeywordCollByStr(String content,String keywordCollFilePath,String serFilePath){
		System.setProperty("appPath", serFilePath);
		JSONObject json = KeywordColl.toUpdKeywordCollByStr(content, keywordCollFilePath, serFilePath);
		return json;
	}
	
	/*
	 * 相似度
	 * srcArticle 文1
	 * targetArticle 文2
	 * serFilePath  序列化词库路径
	 */
	public static JSONObject articleSimilarity(String srcArticle,String targetArticle,String serFilePath){
		System.setProperty("appPath", serFilePath);
		Double articleSimilarity = ArticleSimilarity.articleSimilarity(srcArticle, targetArticle);
		JSONObject json = new JSONObject();
		json.put("articleSimilarity", articleSimilarity);
		return json;
	}
}
