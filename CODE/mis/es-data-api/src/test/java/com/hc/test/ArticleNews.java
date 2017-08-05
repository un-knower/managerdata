package com.hc.test;


import java.util.Date;

import com.hc.common.annotation.DateFormat;
import com.hc.common.annotation.ESearchTypeColumn;

public class ArticleNews {

	@ESearchTypeColumn(type = "text", analyzer = "ik_max_word")
	private String city;
	@ESearchTypeColumn(type = "text", analyzer = "ik_max_word")
	private String clearContent;
	@ESearchTypeColumn(type = "text", analyzer = "ik_max_word")
	private String clearTitle;
	@ESearchTypeColumn(type = "text", not_analyzed = "not_analyzed")
	private String columnName;
	
	@ESearchTypeColumn(type = "text",  analyzer = "ik_max_word")
	private String content;
	@ESearchTypeColumn(type = "Date",  format = DateFormat.date_hour_minute_second_fraction)
	private Date gatherTime;
	@ESearchTypeColumn(type = "text",  not_analyzed = "not_analyzed")
	private String id;
	@ESearchTypeColumn(type = "text",  not_analyzed = "not_analyzed")
	private String industry;
	
	@ESearchTypeColumn(type = "text",  analyzer = "ik_max_word")
	private String province;
	
	@ESearchTypeColumn(type = "Date",  format = DateFormat.date_hour_minute_second_fraction)
	private Date publishTime;
	@ESearchTypeColumn(type = "text",  not_analyzed = "not_analyzed")
	private String siteName;
	
	
	@ESearchTypeColumn(type = "text", not_analyzed = "not_analyzed")
	private String sourceId;
	@ESearchTypeColumn(type = "keyword")
	private String keywords;
	@ESearchTypeColumn(type = "Date", format = DateFormat.date_hour_minute_second_fraction)
	private Date storeTime;
	
	@ESearchTypeColumn(type = "text",  analyzer = "ik_max_word")
	private String summary;
	
	@ESearchTypeColumn(type = "text",  analyzer = "ik_max_word")
	private String title;
	
	@ESearchTypeColumn(type = "text",  not_analyzed = "not_analyzed")
	private String url;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getClearContent() {
		return clearContent;
	}

	public void setClearContent(String clearContent) {
		this.clearContent = clearContent;
	}

	public String getClearTitle() {
		return clearTitle;
	}

	public void setClearTitle(String clearTitle) {
		this.clearTitle = clearTitle;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getGatherTime() {
		return gatherTime;
	}

	public void setGatherTime(Date gatherTime) {
		this.gatherTime = gatherTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Date getStoreTime() {
		return storeTime;
	}

	public void setStoreTime(Date storeTime) {
		this.storeTime = storeTime;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
