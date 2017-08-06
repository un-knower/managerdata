package com.es.common.service;

import java.io.Serializable;

public class TargetContent implements Serializable {
	private static final long serialVersionUID = 1L;

	@ESearchTypeColumn
	private String id;// ID
	@ESearchTypeColumn
	private String targetid;// 要素ID
	@ESearchTypeColumn
	private String content;// 内容
	@ESearchTypeColumn
	private String updatetime;// 采集时间
	@ESearchTypeColumn
	private String source;// 来源
	@ESearchTypeColumn
	private String remrak;// 备注
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRemrak() {
		return remrak;
	}
	public void setRemrak(String remrak) {
		this.remrak = remrak;
	}

}