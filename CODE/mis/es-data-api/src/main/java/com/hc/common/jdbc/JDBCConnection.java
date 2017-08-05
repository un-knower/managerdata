package com.hc.common.jdbc;

import java.sql.Connection;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;

public class JDBCConnection {
	private static volatile JDBCConnection instance = null;
	public static Connection connection = null;
	public static DruidDataSource dds = null;
	
	public static JDBCConnection getInstance() {
		synchronized (JDBCConnection.class) {
			if(instance == null){
				instance = new JDBCConnection();
				Properties properties = new Properties();
		        properties.put("url", "jdbc:elasticsearch://172.168.30.117:9300/elasticsearch-sql_test_index");
				try {
					dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory.createDataSource(properties);
					connection = dds.getConnection();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return instance;
	}
}
