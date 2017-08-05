package com.hc.common.jdbc;

import static org.apache.commons.lang.StringUtils.split;
import static org.apache.commons.lang.StringUtils.substringAfterLast;
import static org.apache.commons.lang.StringUtils.substringBeforeLast;
import static org.nlpcn.es4sql.TestsConstants.TEST_INDEX;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.sql.SQLFeatureNotSupportedException;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.nlpcn.es4sql.SearchDao;
import org.nlpcn.es4sql.exception.SqlParseException;
import org.nlpcn.es4sql.query.SqlElasticSearchRequestBuilder;

import com.google.common.io.ByteStreams;

public class MainSuite {

	private static SearchDao searchDao;

	private static String clusterNodes = "172.168.30.117:9300";
	private static String clusterName = "elasticsearch";
	private static Boolean clientTransportSniff = true;
	private static Boolean clientIgnoreClusterName = Boolean.FALSE;
	private static String clientPingTimeout = "10s";
	private static String clientNodesSamplerInterval = "10s";
	public static TransportClient client;
	static final String COLON = ":";
	static final String COMMA = ",";
	private static volatile MainSuite instance = null;

	public static MainSuite getInstance(String clusterNodes, String clusterName) {
		MainSuite.clusterNodes = clusterNodes;
		MainSuite.clusterName = clusterName;
		synchronized (MainSuite.class) {
			if (instance == null) {
				instance = new MainSuite();
				try {
					buildClient();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				// if (client != null) {
				// client.close();
				// }
			}
		}
		return instance;
	}

	protected static void buildClient() throws Exception {
		client = new PreBuiltTransportClient(settings());
		for (String clusterNode : split(clusterNodes, COMMA)) {
			String hostName = substringBeforeLast(clusterNode, COLON);
			String port = substringAfterLast(clusterNode, COLON);
			client.addTransportAddress(new InetSocketTransportAddress(
					InetAddress.getByName(hostName), Integer.valueOf(port)));
		}
		client.connectedNodes();
		searchDao = new SearchDao(client);
	}

	private static Settings settings() {
		// if (properties != null) {
		// return Settings.builder().put(properties).build();
		// }
		// return Settings.EMPTY;
		return Settings
				.builder()
				.put("cluster.name", clusterName)
//				.put("client.transport.sniff", clientTransportSniff)
				.put("client.transport.ignore_cluster_name",
						clientIgnoreClusterName)
				.put("client.transport.ping_timeout", clientPingTimeout)
				.put("client.transport.nodes_sampler_interval",
						clientNodesSamplerInterval).build();
	}

	/**
	 * Delete all data inside specific index
	 * 
	 * @param indexName
	 *            the documents inside this index will be deleted.
	 */
	public static void deleteQuery(String indexName) {
		deleteQuery(indexName, null);
	}

	/**
	 * Delete all data using DeleteByQuery.
	 * 
	 * @param indexName
	 *            the index to delete
	 * @param typeName
	 *            the type to delete
	 */
	public static void deleteQuery(String indexName, String typeName) {

		DeleteByQueryRequestBuilder deleteQueryBuilder = new DeleteByQueryRequestBuilder(
				client, DeleteByQueryAction.INSTANCE);
		deleteQueryBuilder.request().indices(indexName);
		if (typeName != null) {
			deleteQueryBuilder.request().getSearchRequest().types(typeName);
		}
		deleteQueryBuilder.filter(QueryBuilders.matchAllQuery());
		deleteQueryBuilder.get();
		System.out.println(String.format("Deleted index %s and type %s",
				indexName, typeName));

	}

	/**
	 * Loads all data from the json into the test elasticsearch cluster, using
	 * TEST_INDEX
	 * 
	 * @param jsonPath
	 *            the json file represents the bulk
	 * @throws Exception
	 */
	public static void loadBulk(String jsonPath) throws Exception {
		System.out.println(String.format(
				"Loading file %s into elasticsearch cluster", jsonPath));

		BulkRequestBuilder bulkBuilder = client.prepareBulk();
		byte[] buffer = ByteStreams.toByteArray(new FileInputStream(jsonPath));
		bulkBuilder.add(buffer, 0, buffer.length, TEST_INDEX, null);
		BulkResponse response = bulkBuilder.get();

		if (response.hasFailures()) {
			throw new Exception(String.format(
					"Failed during bulk load of file %s. failure message: %s",
					jsonPath, response.buildFailureMessage()));
		}
	}

	public static SearchDao getSearchDao() {
		return searchDao;
	}

	public TransportClient getClient() {
		return client;
	}

	public SearchHits searchQuery(String query) throws SqlParseException,
			SQLFeatureNotSupportedException, SQLFeatureNotSupportedException {
		SqlElasticSearchRequestBuilder select = getSearchRequestBuilder(query);
		System.out.println(select);
		System.out.println(client.nodeName());
		return ((SearchResponse) select.get()).getHits();
	}

	public Aggregations aggrQuery(String query) throws SqlParseException,
			SQLFeatureNotSupportedException {
		SqlElasticSearchRequestBuilder select = getSearchRequestBuilder(query);
		System.out.println(select);
		return ((SearchResponse) select.get()).getAggregations();
	}

	public SqlElasticSearchRequestBuilder getSearchRequestBuilder(String query)
			throws SqlParseException, SQLFeatureNotSupportedException {
		return (SqlElasticSearchRequestBuilder) searchDao.explain(query)
				.explain();
	}

	public SqlElasticSearchRequestBuilder getRequestBuilder(String query)
			throws SqlParseException, SQLFeatureNotSupportedException,
			SQLFeatureNotSupportedException {
		return (SqlElasticSearchRequestBuilder) searchDao.explain(query)
				.explain();
	}

	public SearchResponse getSearchResponse(String query)
			throws SqlParseException, SQLFeatureNotSupportedException,
			SQLFeatureNotSupportedException {
		SqlElasticSearchRequestBuilder select = (SqlElasticSearchRequestBuilder) searchDao
				.explain(query).explain();
		return ((SearchResponse) select.get());
	}

}
