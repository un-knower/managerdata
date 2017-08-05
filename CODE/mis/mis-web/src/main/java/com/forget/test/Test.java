package com.forget.test;


import com.alibaba.fastjson.JSONObject;
import com.forget.articleSimilarity.ArticleSimilarity;
import com.forget.sub.Analysis;

public class Test {

	public static void main(String[] args) {
		// 待处理文本文件路径（必填,格式UTF-8）;【1、具体文件全路径；2、文件目录】
		String srcFilePath = "D:/tmp/NlpForget/analysis/data/data_bak20170803";
		// 序列化词库路径（必填）;
		String serFilePath = "D:/tmp/NlpForget/analysis/keywordColl.txt";// ItemColl序列化结果
		// 直观词库文件路径（非必填）;
		String keywordCollFilePath = "D:/tmp/NlpForget/analysis/keyword.txt";
//		String keywordCollFilePath = null;
		String stopWordsPath = "D:/tmp/NlpForget/analysis/stopWord.txt";
		
		
		System.setProperty("stopWordsPath", stopWordsPath);
		
		// 1、词库生成-生成serFilePath和keywordCollFilePath
		JSONObject keywordColl = Analysis.keywordColl(srcFilePath, keywordCollFilePath, serFilePath);
		System.out.println("1、词库生成："+keywordColl.toJSONString());
		
		// 2、初始化词库
//		JSONObject initKeywordColl = Analysis.initKeywordColl(serFilePath);
//		System.out.println("2、初始化词库："+initKeywordColl.toJSONString());
		
		// 3、更新词库
//		srcFilePath = "D:/tmp/NlpForget/analysis/data";
//		JSONObject updKeywordColl = Analysis.updKeywordColl(srcFilePath, keywordCollFilePath, serFilePath);
//		System.out.println(updKeywordColl.toJSONString());
		

		
		// 3、词权重
		// serFilePath:序列化词库路径（必填[初始化词库后不需填写]）；
//		JSONObject keywordWeight = Analysis.keywordWeight(serFilePath,"集团");
//		System.out.println("3、词权重：集团  "+keywordWeight.toJSONString());
		
		// 4、分词
//		JSONObject segment = Analysis.segment("内饰做工精细，各个地方的真皮包裹也很到位。就是我女朋友提个小建议，档把带震动就好了。我也不知为嘛", serFilePath, 7);
//		System.out.println("4、分词："+segment.toString());
		
		/**
		 * 删除词库（将内存中的词库清理并保存）
		 * keywordCollFilePath：直观词库文件路径（非必填）；
		 * serFilePath：序列化词库路径（非必填）;
		 */
//		JSONObject delKeywordColl = Analysis.delKeywordColl("", "");
//		System.out.println(delKeywordColl.toJSONString());
		
		
		/**
		 * 相似度
		 */
		/*System.setProperty("appPath", serFilePath);
		Double articleSimilarity = ArticleSimilarity.articleSimilarity("外观大气，内饰也挺好外观大气，内饰也挺好", "内饰不错，外观也不错的外观大气，内饰也挺好");
//		Double articleSimilarity = ArticleSimilarity.articleSimilarity("外观大气，内饰也挺好外观大气，内饰也挺好", "外观大气，内饰也挺好外观大气，内饰也挺好");
		System.out.println(articleSimilarity);*/
		
		
		
/*		
		segment = Analysis.segment("内饰做工精细，各个地方的真皮包裹也很到位。就是我女朋友提个小建议，档把带震动就好了。我也不知为嘛", serFilePath, 7);
		System.out.println(segment.toString());
		
		System.out.println("=================新增训练");
		
		srcFilePath = "D:/新建文件夹/JD/analysis/200000";
		JSONObject updKeywordColl = Analysis.updKeywordColl(srcFilePath, keywordCollFilePath, serFilePath);
		System.out.println(updKeywordColl.toJSONString());
		serFilePath = "D:/新建文件夹/JD/analysis/keywordColl2.txt";
		JSONObject saveKeywordColl = Analysis.saveKeywordColl(serFilePath, "");
		System.out.println(saveKeywordColl.toJSONString());
		
		keywordWeight = Analysis.keywordWeight(serFilePath,"集团");
		System.out.println(keywordWeight.toJSONString());
		
		segment = Analysis.segment("内饰做工精细，各个地方的真皮包裹也很到位。就是我女朋友提个小建议，档把带震动就好了。我也不知为嘛", serFilePath, 4);
		System.out.println(segment.toString());
		
		JSONObject docFingerprint = Analysis.docFingerprint("详情一、集团简介益客集团成立于2004年10月，是中国领", serFilePath);
		System.out.println(docFingerprint.toJSONString());
*/		
		/*JSONObject keywordColl2 = Analysis.getKeywordColl(serFilePath);
		System.out.println(keywordColl2.toJSONString());*/
	}
	
	
}
