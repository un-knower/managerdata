package com.forget.articleModel;

import java.util.HashMap;
import java.util.Map;

/*
 * 文章对象
 */
public class ArticleModel {

	//文章标题
	private String title;
	//文章内容
	private String content;
	//文章中出现词的权重
	private Map<String,Double> keywordDict = new HashMap<String,Double>();
	//相似度
	private Double similarity;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Map<String, Double> getKeywordDict() {
		return keywordDict;
	}
	public void setKeywordDict(Map<String, Double> keywordDict) {
		this.keywordDict = keywordDict;
	}
	public Double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}
	
	
	
}
