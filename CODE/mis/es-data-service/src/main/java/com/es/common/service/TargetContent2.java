package com.es.common.service;

import java.io.Serializable;
import java.util.List;

public class TargetContent2 implements Serializable {
	private static final long serialVersionUID = 1L;

	@ESearchTypeColumn
	private String id;// ID
	@ESearchTypeColumn
	private String content;// 内容
	@ESearchTypeColumn
	private List<String> key;// 采集时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getKey() {
		return key;
	}
	public void setKey(List<String> list) {
		this.key = list;
	}
}