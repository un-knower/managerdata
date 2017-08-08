package com.es.common.service;

import java.io.Serializable;
import java.util.List;

/**
 * ES索引
 * Elasticsearch.id = HBASE.rowkey
 * targetid 指标id
 * contentkey 分词后的关键字list
 * 
 * @author ThinkPad
 *
 */
public class TargetContentKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@ESearchTypeColumn
	private String id;// ID = rowkey
	@ESearchTypeColumn
	private String targetid;// 指标ID
	@ESearchTypeColumn
	private List<String> contentkey;// 关键字
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTargetid() {
		return targetid;
	}
	public void setTargetid(String targetid) {
		this.targetid = targetid;
	}
}