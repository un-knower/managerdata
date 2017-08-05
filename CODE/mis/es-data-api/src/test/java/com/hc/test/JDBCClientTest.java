package com.hc.test;

import static org.nlpcn.es4sql.TestsConstants.TEST_INDEX;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.junit.Assert;
import org.nlpcn.es4sql.MainTestSuite;
import org.nlpcn.es4sql.SearchDao;
import org.nlpcn.es4sql.exception.SqlParseException;

import com.alibaba.fastjson.JSON;
import com.hc.common.create.CreateApi;
import com.hc.common.jdbc.JDBCClient;
import com.hc.common.jdbc.MainSuite;

public class JDBCClientTest {
	      
	
	public static void main(String[] args) {
		String clusterNodes = "111.67.205.24:9300";
		String clusterName = "elasticsearch";
		String index = "news";
		String type = "account";
		// TODO Auto-generated method stub
		MainSuite ms = MainSuite.getInstance(clusterNodes, clusterName);
		TransportClient client = ms.getClient();
//		CreateApi create = new CreateApi(client);
	    // 创建一个索引
//		create.createIndex("news");
		// 创建一个mapping
//		ArticleNews article = new ArticleNews();
//		create.addMapping("news","article",article);
//		create.getXContentBuilderKeyValue(article);
		
		try {
			SearchHits response = ms.searchQuery(String.format("SELECT title,publishTime FROM %s/article limit 10", index));
			SearchHit[] hits = response.getHits();
			for(SearchHit hit : hits) {
				Map<String, Object> entity = hit.getSourceAsMap();
				System.out.println(hit.getSource().keySet());
				System.out.println(hit.getSource().values());
				System.out.println(hit.getId());
			}
		} catch (SQLFeatureNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SqlParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/** 2.1.聚合 **/
//		try {
//			Aggregations result = mt.aggrQuery(String.format("SELECT COUNT(*) FROM %s/account", index));
//			System.out.println(result);
//			ValueCount count = result.get("COUNT(*)");
//			System.out.println(count);
//		} catch (SQLFeatureNotSupportedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SqlParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		/** 2.2.聚合 **/
//		try {
//			Aggregations result = mt.aggrQuery(String.format("SELECT COUNT(*) FROM %s/account GROUP BY gender", index));
//			Terms gender = result.get("gender");
//			for(Terms.Bucket bucket : gender.getBuckets()) {
//				String key = bucket.getKey().toString();
//				long count = ((ValueCount) bucket.getAggregations().get("COUNT(*)")).getValue();
//				if(key.equalsIgnoreCase("m")) {
//					System.out.println(count);
//				}
//				else if(key.equalsIgnoreCase("f")) {
//					System.out.println(count);
//				}
//				else {
//					System.out.println(String.format("Unexpected key. expected: m OR f. found: %s", key));
//				}
//			}
//		} catch (SQLFeatureNotSupportedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SqlParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
