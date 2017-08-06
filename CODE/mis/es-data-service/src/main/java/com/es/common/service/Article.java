package com.es.common.service;

import java.io.Serializable;

public class Article implements Serializable {
	private static final long serialVersionUID = 1L;

	@ESearchTypeColumn
	private String id;// ID
	@ESearchTypeColumn
	private String title;// 标题
	@ESearchTypeColumn
	private String content;// 内容
	@ESearchTypeColumn
	private String deployTime;// 发布时间
	@ESearchTypeColumn
	private String collectTime;// 采集时间
	@ESearchTypeColumn
	private String source;// 来源
	@ESearchTypeColumn
	private String author;// 作者
	@ESearchTypeColumn
	private String remrak;// 备注
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDeployTime() {
		return deployTime;
	}
	public void setDeployTime(String deployTime) {
		this.deployTime = deployTime;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getRemrak() {
		return remrak;
	}
	public void setRemrak(String remrak) {
		this.remrak = remrak;
	}
	public String getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}

}