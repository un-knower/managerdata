package com.es.common.service;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.ufc.user.utils.Pager;

public class ArticleRepositoryTest{
	//@Resource(name = "transportClientRepository")
	public static TransportClientRepository client;

	public static void main(String args[]) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-es.xml");
		client = (TransportClientRepository)ac.getBean("transportClientRepository");
		
		String index = "managerdataindex";// InvalidIndexNameException[Invalid index name [managerdataIndex], must be lowercase]
		String type = "news";
		String id = "";
		
		// 批量插入测试数据
//		insertContent(index, type);
//		insertTargetContent();
		
		
		// 查询title包含标题的内容
//		Pager<JSONObject> pager = client.searchFullText("title", "", 3, 10, type, index);
//		System.out.println(JSONObject.toJSONString(pager));
		Pager<JSONObject> pager = client.searchFullText("", "", 1, 10, "content", "managerdataindex");
		System.out.println(JSONObject.toJSONString(pager));
		
		
		
		String result = null;
		//client.getIdx(index, type, id);
		//client.getIdx(index, type, id, t)
//		id = "2003";
//		result = client.getIdx(index, type, id);
//		System.out.println("查询2003：" + result);
		
//		Article article = new Article();
//		article.setId("2003");
//		article.setTitle("标题3：杨超万岁");
//		article.setContent("内容3：杨超万岁万岁万万岁");
//		article.setDeployTime("2017-08-06 11:11:11");
//		article.setSource("https://www.baidu.com/yangchao3");
//		article.setAuthor("刘德华3");
//		article.setRemrak("备注3");
//		result = client.saveDoc(index, type, article.getId(), article);
//		System.out.println("保存2003：" + result);
		
//		Article param = new Article();
//		param.setContent("杨超");
//		ElasticSearchPage<Article> page = new ElasticSearchPage<Article>();
//		page.setPageSize(10);
//		HighlightBuilder highlight = new HighlightBuilder();
//		highlight.field("content").preTags("<span style=\"color:red\">")
//				.postTags("</span>");
//		page = client.searchFullText(param, page, highlight, index);
//		List<Article> articleList = page.getRetList();
//		for(int i=0; i<articleList.size(); i++) {
//			System.out.println(articleList.get(i));
//		}
		
//		Article param = new Article();
//		param.setContent("杨超");
//		ElasticSearchPage<Article> page = new ElasticSearchPage<Article>();
//		page.setPageSize(10);
//		HighlightBuilder highlight = new HighlightBuilder();
//		highlight.field("content").preTags("<span style=\"color:red\">")
//				.postTags("</span>");
//		Pager<JSONObject> pager = client.searchFullText("content","杨超",1,10, index);
//		System.out.println(JSONObject.toJSONString(pager));
		
		
		
		
	}

	@Test
	public void searchFullText() {
		Article param = new Article();
		//param.setDescription("太阳");
		ElasticSearchPage<Article> page = new ElasticSearchPage<Article>();
		page.setPageSize(10);
		HighlightBuilder highlight = new HighlightBuilder();
		highlight.field("description").preTags("<span style=\"color:red\">")
				.postTags("</span>");
		page = client.searchFullText(param, page, highlight, "blog1");
//		for (Article aa : page.getRetList()) {
//			System.out.println(aa.getId() + "====" + aa.getDescription()
//					+ "===title:==" + aa.getTitle());
//		}
		System.out.println(page.getTotal());
	}
	
	/**
	 * 批量插入测试数据
	 * @param index
	 * @param type
	 */
	public static void insertContent(String index, String type) {
		String result = null;
		int maxNum = 20;
		Article article = null;
		for (int i = 0; i < maxNum; i++) {
			article = new Article();
			article.setId(getUUID());
			article.setTitle("test标题" + i + "：标题" + i);
			article.setContent("test内容" + i + "：“十二五”时期，外经贸控股公司将“一体两翼” 作为引领企业发展的总体战略定位，通过全面深化国资国企改革、改造传统产业结构、盘活资产存量、强化重点项目建设等举措，实现企业内生动力增加、外延需求加速扩张，整体向外向型现代服务业转型取得明显成效。");
			article.setDeployTime("2017-08-06 11:" + i + ":11");
			article.setCollectTime("2017-08-12 11:" + i + ":11");
			article.setSource("https://www.baidu.com/yangchao/" + i + "/test");
			article.setAuthor("刘德华" + i);
			article.setRemrak("备注" + i);
			result = client.saveDoc(index, type, article.getId(), article);
			System.out.println("SAVE" + i + " : " + result);
		}
	}
	
	// 语料批量插入
	public static void insertTargetContent() {
		String index = "managerdataindex"; 
		String type = "content";
		String result = null;
		int maxNum = 20;
		TargetContent targetContent = null;
		for (int i = 0; i < maxNum; i++) {
			targetContent = new TargetContent();
			targetContent.setId(getUUID());
			targetContent.setContent("test内容" + i + "：test内容");
			targetContent.setTargetid("516f7b13-68f4-4e8f-8451-686f6ffe56c8");
			targetContent.setUpdatetime("2017-08-06 11:" + i + ":11");
			targetContent.setSource("https://www.baidu.com/yangchao/" + i + "/test");
			targetContent.setRemrak("备注" + i);
			result = client.saveDoc(index, type, targetContent.getId(), targetContent);
			System.out.println("SAVE" + i + " : " + result);
		}
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
