package com.hc.common.create;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.split;
import static org.apache.commons.lang.StringUtils.substringAfterLast;
import static org.apache.commons.lang.StringUtils.substringBeforeLast;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSON;
import com.hc.common.annotation.DateFormat;
import com.hc.common.annotation.ESearchTypeColumn;
import com.hc.common.persistence.ElasticSearchPage;

public class CreateApi {
	private TransportClient client;

	public CreateApi(TransportClient client) {
		super();
		this.client = client;
	}
	private static String clusterNodes = "172.168.30.117:9300";
	private static String clusterName = "elasticsearch";
	private static Boolean clientTransportSniff = true;
	private static Boolean clientIgnoreClusterName = Boolean.FALSE;
	private static String clientPingTimeout = "5s";
	private static String clientNodesSamplerInterval = "5s";
	private Properties properties;
	static final String COLON = ":";
	static final String COMMA = ",";
	
	
	public static final String FIELD_DATA = "fielddata";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_INDEX = "index";
	public static final String FIELD_FORMAT = "format";
	public static final String FIELD_SEARCH_ANALYZER = "search_analyzer";
	public static final String FIELD_INDEX_ANALYZER = "analyzer";
	public static final String FIELD_PROPERTIES = "properties";
	public static final String FIELD_IK_MAX_WORD = "ik_max_word";

	public static final String INDEX_VALUE_NOT_ANALYZED = "not_analyzed";
	
	protected void buildClient() throws Exception {
		client = new PreBuiltTransportClient(settings());
	       for (String clusterNode : split(clusterNodes, COMMA)) {
	           String hostName =substringBeforeLast(clusterNode, COLON);
	           String port = substringAfterLast(clusterNode, COLON);
	           client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName),Integer.valueOf(port)));
	        }
	       client.connectedNodes();
	}

	private static Settings settings() {
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
	
	 /**
     * 创建一个索引
     * @param indexName 索引名
     */
    public void createIndex(String indexName) {
		try {
			CreateIndexResponse indexResponse = this.client.admin().indices()
					.prepareCreate(indexName).get();
			System.out.println(indexResponse.isAcknowledged()); // true表示创建成功
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 给索引增加mapping。
     * @param index 索引名
     * @param type mapping所对应的type
     */
    public void addMapping(String index, String type,Object o) {
		try {
			// 使用XContentBuilder创建Mapping
			XContentBuilder builder = creatXContentBuilderMapping(o);
			try {
				System.out.println(builder.string());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PutMappingRequest mappingRequest = Requests
					.putMappingRequest(index).type(type).source(builder);
			client.admin().indices().putMapping(mappingRequest)
					.actionGet();
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		}
    }
    
    
	/**
	 * 创建搜索引擎文档
	 * 
	 * @param index
	 *            索引名称
	 * @param type
	 *            索引类型
	 * @param id
	 *            索引id
	 * @param doc
	 * @return
	 */
	public String saveDoc(String index, String type, Object doc) {
		IndexResponse response = client.prepareIndex(index, type)
				.setSource(getXContentBuilderKeyValue(doc)).get();
		return response.getId();
	}

	/**
	 * 更新文档
	 *
	 * @param index
	 * @param type
	 * @param id
	 * @param doc
	 * @return
	 */
	public String updateDoc(String index, String type, String id, Object doc) {
		UpdateResponse response = client.prepareUpdate(index, type, id)
				.setDoc(getXContentBuilderKeyValue(doc)).get();
		return response.getId();
	}

	/**
	 * 删除索引
	 *
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	public String deleteById(String index, String type, String id) {
		DeleteResponse response = client.prepareDelete(index, type, id).get();
		return response.getId();
	}

	/**
	 * 获取索引对应的存储内容
	 *
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	public String getIdx(String index, String type, String id) {
		GetResponse response = client.prepareGet(index, type, id).get();
		if (response.isExists()) {
			return response.getSourceAsString();
		}
		return null;
	}

	/**
	 * 对象转换
	 *
	 * @param t
	 * @param src
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T parseObject(T t, String src) {
		try {
			return (T) JSON.parseObject(src, t.getClass());
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 获取索引对应的存储内容自动转换成对象的方式
	 *
	 * @param index
	 * @param type
	 * @param id
	 * @param t
	 * @return
	 */
	public <T> T getIdx(String index, String type, String id, T t) {
		return parseObject(t, getIdx(index, type, id));
	}

	public void searchFullText(String filed, String queryValue, int pageNum,
			int pageSize, String... indexs) {
		QueryBuilder builder = QueryBuilders.matchQuery(filed, queryValue);
		SearchResponse scrollResp = client.prepareSearch(indexs)
				.addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
				.setFrom(pageNum * pageSize).setSize(pageSize)
				.setScroll(new TimeValue(60000)).setQuery(builder).get();
		do {
			for (SearchHit hit : scrollResp.getHits().getHits()) {
				System.out.println("result:+++++" + hit.getSourceAsString());
			}
			scrollResp = client.prepareSearchScroll(scrollResp.getScrollId())
					.setScroll(new TimeValue(60000)).execute().actionGet();
		} while (scrollResp.getHits().getHits().length != 0);
	}

	/**
	 * 全文搜索
	 *
	 * @param param
	 * @param pageNum
	 * @param pageSize
	 * @param indexs
	 * @return
	 */
	public <T> ElasticSearchPage<T> searchFullText(T param,
			ElasticSearchPage<T> page, String... indexs) {
		QueryBuilder builder = null;
		Map<String, Object> map = getObjectMap(param);
		if (map == null)
			return null;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				// builder =QueryBuilders.wildcardQuery( entry.getKey(), "*" +
				// entry.getValue().toString()+ "*" );
				builder = QueryBuilders.matchQuery(entry.getKey(),
						entry.getValue());
				// builder =QueryBuilders.multiMatchQuery(text, fieldNames)(
				// entry.getKey(),entry.getValue());
			}
		}
		HighlightBuilder highlight = new HighlightBuilder();
		highlight.field("title").field("description");
		SearchResponse scrollResp = client.prepareSearch(indexs)
				.setFrom(page.getPageNum() * page.getPageSize())
				.highlighter(highlight).setSize(page.getPageSize())
				// .setScroll(newTimeValue(60000))
				.setQuery(builder).get();
		List<T> result = new ArrayList<>();
		// ElasticSearchPage<T> ret = new ElasticSearchPage<>();
		for (SearchHit hit : scrollResp.getHits().getHits()) {
			try {
				Map<String, HighlightField> highlightResult = hit
						.getHighlightFields();
				highlightResult.get("description");
				result.add(parseObject(param, hit.getSourceAsString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		page.setTotal(scrollResp.getHits().totalHits);
		page.setParam(param);
		page.setRetList(result);
		return page;
	}


	public static Map<String, Object> getObjectMap(Object o) {
		List<Field> fieldList = new ArrayList<Field>();
		@SuppressWarnings("rawtypes")
		Class tempClass = o.getClass();
		while (tempClass != null) {
			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			tempClass = tempClass.getSuperclass();
		}
		try {
			Map<String, Object> result = new HashMap<>();
			for (Field field : fieldList) {
				if (field.isAnnotationPresent(ESearchTypeColumn.class)) {
					PropertyDescriptor descriptor = new PropertyDescriptor(
							field.getName(), o.getClass());
					result.put(field.getName(), descriptor.getReadMethod()
							.invoke(o));
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断某个索引下type是否存在
	 *
	 * @param index
	 * @param type
	 * @return
	 */
	public boolean isTypeExist(String index, String type) {
		return client.admin().indices().prepareTypesExists(index)
				.setTypes(type).execute().actionGet().isExists();
	}

	/**
	 * 判断索引是否存在
	 *
	 * @param index
	 * @return
	 */
	public boolean isIndexExist(String index) {
		return client.admin().indices().prepareExists(index).execute()
				.actionGet().isExists();
	}

	/**
	 * 创建type（存在则进行更新）
	 *
	 * @param index
	 *            索引名称
	 * @param type
	 *            type名称
	 * @param o
	 *            要设置type的object
	 * @return
	 */
	public boolean createType(String index, String type, Object o) {
		if (!isIndexExist(index)) {
			return false;
		}
		try {
			// 若type存在则可通过该方法更新type
			return client.admin().indices().preparePutMapping(index)
					.setType(type).setSource(o).get().isAcknowledged();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static XContentBuilder creatXContentBuilderMapping(Object o) {
		try {
			XContentBuilder mapping = jsonBuilder().startObject();
			// Properties
			XContentBuilder xContentBuilder = mapping.startObject(FIELD_PROPERTIES);
			List<Field> fieldList = new ArrayList<Field>();
			@SuppressWarnings("rawtypes")
			Class tempClass = o.getClass();
			while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
				fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
				tempClass = tempClass.getSuperclass();// 得到父类,然后赋给自己
			}
			
			for (Field field : fieldList) {
				System.out.println(o.getClass().getName());
				System.out.println(o.getClass());
				if (field.isAnnotationPresent(ESearchTypeColumn.class)) {
					//生成sql字段的名
					ESearchTypeColumn column = field.getAnnotation(ESearchTypeColumn.class);
					addSingleFieldMapping(xContentBuilder, field, column);
				}
			}
			xContentBuilder.endObject().endObject();
			System.out.println(xContentBuilder.string());
			return xContentBuilder;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static XContentBuilder getXContentBuilderKeyValue(Object o) {
		try {
			XContentBuilder builder = XContentFactory.jsonBuilder()
					.startObject();
			List<Field> fieldList = new ArrayList<Field>();
			@SuppressWarnings("rawtypes")
			Class tempClass = o.getClass();
			while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
				fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
				tempClass = tempClass.getSuperclass();// 得到父类,然后赋给自己
			}
			for (Field field : fieldList) {
				if (field.isAnnotationPresent(ESearchTypeColumn.class)) {
					PropertyDescriptor descriptor = new PropertyDescriptor(
							field.getName(), o.getClass());
					Object value = descriptor.getReadMethod().invoke(o);
					if (value != null) {
						builder.field(field.getName(), value.toString());
					}
				}
			}
			builder.endObject();
			System.out.println(builder.string());
			return builder;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void addSingleFieldMapping(XContentBuilder xContentBuilder, java.lang.reflect.Field field,
			ESearchTypeColumn fieldAnnotation) throws IOException {
		xContentBuilder.startObject(field.getName());
		if(fieldAnnotation.fielddata()) {
			xContentBuilder.field(FIELD_DATA, fieldAnnotation.fielddata());
		}
		
		if (StringUtils.isNotBlank(fieldAnnotation.not_analyzed())) {
			xContentBuilder.field(FIELD_INDEX, fieldAnnotation.not_analyzed());
		}
		if (StringUtils.isNotBlank(fieldAnnotation.type())) {
			xContentBuilder.field(FIELD_TYPE, fieldAnnotation.type().toLowerCase());
		}
		if (DateFormat.none != fieldAnnotation.format()) {
			xContentBuilder.field(FIELD_FORMAT, DateFormat.custom == fieldAnnotation.format() ? fieldAnnotation.pattern() : fieldAnnotation.format());
		}
		if(!fieldAnnotation.index()) {
			xContentBuilder.field(FIELD_INDEX, fieldAnnotation.index());
		}
		if (isNotBlank(fieldAnnotation.searchAnalyzer())) {
			xContentBuilder.field(FIELD_SEARCH_ANALYZER, fieldAnnotation.searchAnalyzer());
		}
		if (isNotBlank(fieldAnnotation.analyzer())) {
			xContentBuilder.field(FIELD_INDEX_ANALYZER, fieldAnnotation.analyzer());
		}
		xContentBuilder.endObject();
	}
}
