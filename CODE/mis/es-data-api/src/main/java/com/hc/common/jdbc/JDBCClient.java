package com.hc.common.jdbc;

import java.net.InetAddress;
import java.util.Properties;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;

import static org.apache.commons.lang.StringUtils.*;

public class JDBCClient {

	private static String clusterNodes = "172.168.30.117:9300";
	private static String clusterName = "elasticsearch";
	private static Boolean clientTransportSniff = true;
	private static Boolean clientIgnoreClusterName = Boolean.FALSE;
	private static String clientPingTimeout = "5s";
	private static String clientNodesSamplerInterval = "5s";
	public static TransportClient client;
	static final String COLON = ":";
	static final String COMMA = ",";
	private static volatile JDBCClient instance = null;
	
	public static JDBCClient getInstance(String clusterNodes, String clusterName) {
		JDBCClient.clusterNodes = clusterNodes;
		JDBCClient.clusterName = clusterName;
		synchronized (JDBCConnection.class) {
			if(instance == null){
				instance = new JDBCClient();
				
				try {
					buildClient();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				if (client != null) {
//					client.close();
//				}
			}
		}
		return instance;
	}
	
	
	
	protected static void buildClient() throws Exception {
		client = new PreBuiltTransportClient(settings());
	       //client = TransportClient.builder().settings(settings()).build();
	       for (String clusterNode : split(clusterNodes, COMMA)) {
	           String hostName =substringBeforeLast(clusterNode, COLON);
	           String port = substringAfterLast(clusterNode, COLON);
	           client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName),Integer.valueOf(port)));
	        }
	       client.connectedNodes();
	}

	private static Settings settings() {
		// if (properties != null) {
		// return Settings.builder().put(properties).build();
		// }
		// return Settings.EMPTY;
		return Settings
				.builder()
				.put("cluster.name", clusterName)
				.put("client.transport.sniff", clientTransportSniff)
				.put("client.transport.ignore_cluster_name",
						clientIgnoreClusterName)
				.put("client.transport.ping_timeout", clientPingTimeout)
				.put("client.transport.nodes_sampler_interval",
						clientNodesSamplerInterval).build();
	}
	
	
}
