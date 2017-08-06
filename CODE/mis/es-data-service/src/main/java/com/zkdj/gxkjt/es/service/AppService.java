package com.zkdj.gxkjt.es.service;

import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.nlpcn.es4sql.exception.SqlParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ufc.user.utils.ESTransportClient;
import com.ufc.user.utils.FullSearchUtils;
import com.ufc.user.utils.Pager;
import com.ufc.user.utils.ParseRuleUtils;
/** 
* @author DengJie 
* @version 创建时间：2017年7月28日
* 类说明:
*/
//@Service
public class AppService {
	private static Logger logger = LoggerFactory.getLogger(AppService.class);
	//@Autowired
	private ESTransportClient eSTransportClient;
    /**
     * 全文查询
     * @param params 参数
     * @return Pager<JSONObject>
     */
	public Pager<JSONObject> query(JSONObject params){
		String newSql = "";
		try {
			StringBuffer buffer = new  StringBuffer();	
			buffer.append(" SELECT /*! HIGHLIGHT(phrase,pre_tags : ['<b>'], post_tags : ['</b>']  ) */ * FROM news/article where ");
			//关键字
			if(StringUtils.isNotEmpty(params.getString("search"))){
				String queryString=params.getString("search");
				JSONObject jsonSearch=JSONObject.parseObject(queryString);
				String title = String.valueOf(jsonSearch.get("title"));
				String content = String.valueOf(jsonSearch.get("content"));
				String all = String.valueOf(jsonSearch.get("all"));
				if (!StringUtils.isEmpty(title) || !StringUtils.isEmpty(content) || !StringUtils.isEmpty(all)) {
					buffer.append(" (");
					if (!StringUtils.isEmpty(title)) {
						FullSearchUtils.getSearch(buffer, "title", title);
					}
					if (!StringUtils.isEmpty(content)) {
						FullSearchUtils.getSearch(buffer, "content", content);
					}
					if (!StringUtils.isEmpty(all)) {
						boolean flag = ParseRuleUtils.isSingleWord(all);
						// 单词
						if (flag) {
							FullSearchUtils.getSearch(buffer, "all", all);
						} else {
							FullSearchUtils.getSearch(buffer, "title", all);
							FullSearchUtils.getSearch(buffer, "content", all);
						}
					}
					newSql = buffer.toString();
					//去除or结尾的情况
					if (newSql.trim().endsWith("or")) {
						newSql = newSql.trim().substring(0, newSql.trim().length() - 2);
						buffer.setLength(0);
						buffer.append(newSql);
					}
					buffer.append(")");
					//sql条件处理
					SqlFilter(newSql, buffer);
				}
			}
			//行业
			if(StringUtils.isNotEmpty(params.getString("industry"))){
				String industry=params.getString("industry");
				if (com.ufc.user.utils.JSONUtils.checkJsonArray(industry)) {
					JSONArray array = JSONArray.parseArray(industry);
					buffer.append(" ( ");
					for (int i = 0; i < array.size(); i++) {
						String name= String.valueOf(array.get(i));
						buffer.append(" industry = '" + name + "' or");
					}
					// 最后结尾是 or, 将or替换成空
					if (buffer.toString().trim().endsWith("or")) {
						String n = buffer.toString().trim().substring(0, buffer.toString().trim().length() - 3);
						buffer.setLength(0);
						buffer.append(n);
					}
					buffer.append(" ) ");
					//sql条件处理
					SqlFilter(newSql, buffer);
				}
			}
			//九张名片
			if(StringUtils.isNotEmpty(params.getString("card"))){
				String card=params.getString("card");
				if (com.ufc.user.utils.JSONUtils.checkJsonArray(card)) {
					JSONArray array = JSONArray.parseArray(card);
					buffer.append(" ( ");
					for (int i = 0; i < array.size(); i++) {
						String name= String.valueOf(array.get(i));
						buffer.append(" card = '" + name + "' or");
					}
					// 最后结尾是 or, 将or替换成空
					if (buffer.toString().trim().endsWith("or")) {
						String n = buffer.toString().trim().substring(0, buffer.toString().trim().length() - 3);
						buffer.setLength(0);
						buffer.append(n);
					}
					buffer.append(" ) ");
					//sql条件处理
					SqlFilter(newSql, buffer);
				}
			}
			//站点
			if(StringUtils.isNotEmpty(params.getString("siteName"))){
				String siteName=params.getString("siteName");
				if (com.ufc.user.utils.JSONUtils.checkJsonArray(siteName)) {
					JSONArray array = JSONArray.parseArray(siteName);
					buffer.append(" ( ");
					for (int i = 0; i < array.size(); i++) {
						String name= String.valueOf(array.get(i));
						buffer.append(" siteName = '" + name + "' or");
					}
					// 最后结尾是 or, 将or替换成空
					if (buffer.toString().trim().endsWith("or")) {
						String n = buffer.toString().trim().substring(0, buffer.toString().trim().length() - 3);
						buffer.setLength(0);
						buffer.append(n);
					}
					buffer.append(" ) ");
					//sql条件处理
					SqlFilter(newSql, buffer);
				}
			}
			//栏目
			if(StringUtils.isNotEmpty(params.getString("columnName"))){
				String columnName=params.getString("columnName");
				if (com.ufc.user.utils.JSONUtils.checkJsonArray(columnName)) {
					JSONArray array = JSONArray.parseArray(columnName);
					buffer.append(" ( ");
					for (int i = 0; i < array.size(); i++) {
						String name= String.valueOf(array.get(i));
						buffer.append(" columnName = '" + name + "' or");
					}
					// 最后结尾是 or, 将or替换成空
					if (buffer.toString().trim().endsWith("or")) {
						String n = buffer.toString().trim().substring(0, buffer.toString().trim().length() - 3);
						buffer.setLength(0);
						buffer.append(n);
					}
					buffer.append(" ) ");
					//sql条件处理
					SqlFilter(newSql, buffer);
				}
			}
			//sql结束处理
			SqlEedFilter(newSql, buffer);
			//排序
			if(StringUtils.isNotEmpty(params.getString("sortField"))){
				buffer.append(" order by ").append(params.get("sortField"));
				String sortType=StringUtils.isNotEmpty(params.getString("sortType"))?params.getString("sortType"):"desc";
				buffer.append(" ").append(sortType);
			}
			//分页
			String pageNo = StringUtils.isNotEmpty(params.getString("page_no"))?params.getString("page_no"):"1";
			String pageSize = StringUtils.isNotEmpty(params.getString("page_size"))?params.getString("page_size"):"10";
			buffer.append(" limit " +pageNo +","+pageSize);
			newSql=buffer.toString();
			logger.info("============= query sql =============");
			logger.info(newSql);
			SearchHits search = eSTransportClient.searchQuery(newSql);
			SearchHit[] hits = search.getHits();
			List<JSONObject> list = new  ArrayList<>();
			for(SearchHit hit : hits) {
				String object = hit.getSourceAsString();
				list.add(JSONObject.parseObject(object));
			}
			int count = (int) search.getTotalHits();
			Pager<JSONObject> pageIndex = new Pager<JSONObject>();
			pageIndex.setList(list);  
			pageIndex.setCount(count);  
			pageIndex.setPageSize(Integer.valueOf(pageSize));  
			pageIndex.setPageNo(Integer.valueOf(pageNo));  
			return pageIndex;
		} catch (SQLFeatureNotSupportedException e) {
			e.printStackTrace();
		} catch (SqlParseException e) {
			e.printStackTrace();
		}
		return null;
		
	}
    /**
     * 根据Id查询
     * @param id 文档ID
     * @return JSONObject
     */
	public JSONObject queryById(String id) {
		List<String> ids = new ArrayList<>();
		ids.add(id);
		JSONArray array = queryById(ids);
		if (null != array) {
			return array.getJSONObject(0);
		}
		return null;
	}
	/**
	 * 根据批量ID查询
	 * @param ids ID集合
	 * @return JSONArray
	 */
	public JSONArray queryById(List<String> ids) {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" select * from news/article where ");
			buffer.append(" ( ");
			for (String id : ids) {
				buffer.append(" id = '").append(id).append("' or ");
			}
			// 最后结尾是 or, 将or替换成空
			if (buffer.toString().trim().endsWith("or")) {
				String n = buffer.toString().trim().substring(0, buffer.toString().trim().length() - 3);
				buffer.setLength(0);
				buffer.append(n);
			}
			buffer.append(" ) ");
			String sql = buffer.toString();
			SearchHits search = eSTransportClient.searchQuery(sql);
			SearchHit[] hits = search.getHits();
			JSONArray array = new JSONArray();
			for (SearchHit hit : hits) {
				String object = hit.getSourceAsString();
				array.add(JSONObject.parseObject(object));
			}
			return array;
		} catch (SQLFeatureNotSupportedException e) {
			e.printStackTrace();
		} catch (SqlParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * sql条件处理，以and结尾
	 * @param newSql
	 * @param buffer
	 */
	public void SqlFilter(String newSql,StringBuffer buffer){
		// 如果是()结尾,说明没有匹配上,替换成空
		newSql = buffer.toString();
		if (newSql.trim().endsWith("()")) {
			newSql = newSql.trim().substring(0, newSql.trim().length() - 2);
		}
		if (newSql.trim().endsWith(" and")) {
			buffer.setLength(0);
			buffer.append(newSql);
		} else {
			buffer.setLength(0);
			buffer.append(newSql);
			buffer.append(" and");
		}
	}
	/**
	 * sql结束处理，去除非正常结束符
	 * @param newSql
	 * @param buffer
	 */
	public void SqlEedFilter(String newSql,StringBuffer buffer){
		newSql = buffer.toString();
		if (newSql.trim().endsWith("where")) {
			newSql = newSql.trim().substring(0, newSql.trim().length() - 5);
		}
		if (newSql.trim().endsWith("and")) {
			newSql = newSql.trim().substring(0, newSql.trim().length() - 3);
		}
		buffer.setLength(0);
		buffer.append(newSql);
	}
}
