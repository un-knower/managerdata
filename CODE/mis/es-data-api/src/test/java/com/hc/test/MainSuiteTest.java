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

public class MainSuiteTest {
	      
	
	public static void main(String[] args) {
		String clusterNodes = "172.168.30.117:9300";
		String clusterName = "elasticsearch";
		String index = "elasticsearch-sql_test_index";
		MainSuite ms = MainSuite.getInstance(clusterNodes, clusterName);
		try {
			SearchHits response = ms.searchQuery(String.format("SELECT phrase,insert_time2 FROM %s/phrase limit 10", index));
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
	}

}
