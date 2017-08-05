package com.forget.itemModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.forget.keywords.KeywordColl;
import com.forget.utils.ReadObject2Deserialize;

/**
 * 单字-集合
 * 
 * @author yangchao
 *
 */
public class ItemColl implements java.io.Serializable {
	
	final static Logger logger = LoggerFactory.getLogger(ItemColl.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * keyword 数据模型-集合
	 */
	private Map<String, ItemModel> keyItemColl = new HashMap<String, ItemModel>();
	/**
	 * 全局计数器
	 */
	private double totalOffsetCount = 0;

	private static Set<String> stopWords = new HashSet<String>();
	static {
		String stopWordsPath = System.getProperty("stopWordsPath");
		readStopWords(stopWordsPath);
	}

	public Map<String, ItemModel> getKeyItemColl() {
		return keyItemColl;
	}

	public void setKeyItemColl(Map<String, ItemModel> keyItemColl) {
		this.keyItemColl = keyItemColl;
	}

	public double getTotalOffsetCount() {
		return totalOffsetCount;
	}

	public void setTotalOffsetCount(double totalOffsetCount) {
		this.totalOffsetCount = totalOffsetCount;
	}

	public Set<String> getStopWords() {
		return stopWords;
	}

	public void setStopWords(Set<String> stopWords) {
		this.stopWords = stopWords;
	}

//	public static void readEmotion(Set<String> set, InputStreamReader read) {
//		try {
//			// 考虑到编码格式
//			BufferedReader bufferedReader = new BufferedReader(read);
//			String line = null;
//			while ((line = bufferedReader.readLine()) != null) {
//				set.add(line.trim());
//			}
//			read.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void readStopWords(String stopWordsFilePath) {
//		Set<String> stopWords = new HashSet<String>();
		
		File file = new File(stopWordsFilePath);
		BufferedReader bufferedReader = null;
		try {
			// 考虑到编码格式
			bufferedReader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				stopWords.add(line.trim());
			}
		} catch (Exception e) {
			logger.error("readStopWords(" + stopWordsFilePath + ") ERROR! : ", e);
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				logger.error("bufferedReader.close() ERROR! : ", e);
			}
		}
	}
}
