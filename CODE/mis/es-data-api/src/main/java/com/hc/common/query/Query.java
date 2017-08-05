package com.hc.common.query;

import static org.nlpcn.es4sql.TestsConstants.TEST_INDEX;

import java.io.FileInputStream;
import java.sql.SQLFeatureNotSupportedException;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.search.SearchHits;
import org.nlpcn.es4sql.MainTestSuite;
import org.nlpcn.es4sql.SearchDao;
import org.nlpcn.es4sql.exception.SqlParseException;
import org.nlpcn.es4sql.query.SqlElasticSearchRequestBuilder;

import com.google.common.io.ByteStreams;

public class Query {
	private static TransportClient client;
	private static SearchDao searchDao;
	
	public Query(TransportClient client){
		client = this.client;
		searchDao = new SearchDao(client);
	}

	/**
	 * Delete all data inside specific index
	 * @param indexName the documents inside this index will be deleted.
	 */
	public static void deleteQuery(String indexName) {
		deleteQuery(indexName, null);
	}

	/**
	 * Delete all data using DeleteByQuery.
	 * @param indexName the index to delete
	 * @param typeName the type to delete
	 */
	public static void deleteQuery(String indexName, String typeName) {

        DeleteByQueryRequestBuilder deleteQueryBuilder = new DeleteByQueryRequestBuilder(client, DeleteByQueryAction.INSTANCE);
        deleteQueryBuilder.request().indices(indexName);
        if (typeName!=null) {
            deleteQueryBuilder.request().getSearchRequest().types(typeName);
        }
        deleteQueryBuilder.filter(QueryBuilders.matchAllQuery());
        deleteQueryBuilder.get();
        System.out.println(String.format("Deleted index %s and type %s", indexName, typeName));

    }


	/**
	 * Loads all data from the json into the test
	 * elasticsearch cluster, using TEST_INDEX
	 * @param jsonPath the json file represents the bulk
	 * @throws Exception
	 */
	public static void loadBulk(String jsonPath) throws Exception {
		System.out.println(String.format("Loading file %s into elasticsearch cluster", jsonPath));

		BulkRequestBuilder bulkBuilder = client.prepareBulk();
		byte[] buffer = ByteStreams.toByteArray(new FileInputStream(jsonPath));
		bulkBuilder.add(buffer, 0, buffer.length, TEST_INDEX, null);
		BulkResponse response = bulkBuilder.get();

		if(response.hasFailures()) {
			throw new Exception(String.format("Failed during bulk load of file %s. failure message: %s", jsonPath, response.buildFailureMessage()));
		}
	}

	public static SearchDao getSearchDao() {
		return searchDao;
	}

	public static TransportClient getClient() {
		return client;
	}
	
	
	public static SearchHits query(String query) throws SqlParseException, SQLFeatureNotSupportedException, SQLFeatureNotSupportedException {
        SearchDao searchDao = MainTestSuite.getSearchDao();
        SqlElasticSearchRequestBuilder select = (SqlElasticSearchRequestBuilder) searchDao.explain(query).explain();
        return ((SearchResponse)select.get()).getHits();
    }


    private static SqlElasticSearchRequestBuilder getRequestBuilder(String query) throws SqlParseException, SQLFeatureNotSupportedException, SQLFeatureNotSupportedException {
        SearchDao searchDao = MainTestSuite.getSearchDao();
        return  (SqlElasticSearchRequestBuilder) searchDao.explain(query).explain();
    }

    private static SearchResponse getSearchResponse(String query) throws SqlParseException, SQLFeatureNotSupportedException, SQLFeatureNotSupportedException {
        SearchDao searchDao = MainTestSuite.getSearchDao();
        SqlElasticSearchRequestBuilder select = (SqlElasticSearchRequestBuilder) searchDao.explain(query).explain();
        return ((SearchResponse)select.get());
    }
}
