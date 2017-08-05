package com.hc.test;


import java.util.Date;

import com.hc.common.annotation.DateFormat;
import com.hc.common.annotation.ESearchTypeColumn;

public class Article {

	@ESearchTypeColumn(type = "text", analyzer = "ik_max_word")
	private String title; // 标题
	@ESearchTypeColumn(type = "text", not_analyzed = "not_analyzed")
	private String link; // 外部链接
	@ESearchTypeColumn(type = "text",  not_analyzed = "not_analyzed")
	private String color; // 标题颜色（red：红色；green：绿色；blue：蓝色；yellow：黄色；orange：橙色）
	@ESearchTypeColumn(type = "text", not_analyzed = "not_analyzed")
	private String image; // 文章图片
	@ESearchTypeColumn(type = "keyword")
	private String keywords;// 关键字
	@ESearchTypeColumn(type = "Date", format = DateFormat.basic_date)
	private Date basicFormatDate;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Date getBasicFormatDate() {
		return basicFormatDate;
	}
	public void setBasicFormatDate(Date basicFormatDate) {
		this.basicFormatDate = basicFormatDate;
	}
	
	
	
}
