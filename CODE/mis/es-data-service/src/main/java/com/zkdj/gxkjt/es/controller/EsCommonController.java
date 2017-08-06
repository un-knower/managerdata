package com.zkdj.gxkjt.es.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.es.common.service.Article;
import com.es.common.service.TargetContent;
import com.ufc.user.utils.Pager;
import com.ufc.user.utils.PostJsonUtils;
import com.zkdj.gxkjt.es.service.AppService;
import com.zkdj.gxkjt.es.service.EsCommonService;

@Controller
@RequestMapping("/escommon")
public class EsCommonController{
	@Autowired
	private EsCommonService esCommonService;
	
	/**
	 * 全文查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/searchfulltext",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(HttpServletRequest request, HttpServletResponse response) {
		try {
			String paramJson = PostJsonUtils.getPostJson(request);
			JSONObject params = JSONObject.parseObject(paramJson);

			String filed = params.getString("filed");
			String queryValue = params.getString("queryValue");
			String index = params.getString("index");
			String type = params.getString("type");
			int pageNo = Integer.valueOf(params.getString("pageNo"));
			int pageSize = Integer.valueOf(params.getString("pageSize"));
			Pager<JSONObject> pager = esCommonService.searchFullText(filed,
					queryValue, pageNo, pageSize, type, index);
			System.out.println(JSONObject.toJSONString(pager));
			return JSONObject.toJSONString(pager);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	@RequestMapping(value = "/queryById",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryById(HttpServletRequest request, HttpServletResponse response){
		String paramJson = PostJsonUtils.getPostJson(request);
		if(StringUtils.isNotEmpty(paramJson)){
			JSONObject params = JSONObject.parseObject(paramJson);
			String index = params.getString("index");
			String type = params.getString("type");
			String id= params.getString("id");
			if(StringUtils.isNotEmpty(id)){
				String jsonStr = esCommonService.queryById(index,type,id);
				return jsonStr;
			}
		}
		return null;
	}
	@RequestMapping(value = "/deleteById",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deleteById(HttpServletRequest request, HttpServletResponse response){
		String paramJson = PostJsonUtils.getPostJson(request);
		if(StringUtils.isNotEmpty(paramJson)){
			JSONObject params = JSONObject.parseObject(paramJson);
			String index = params.getString("index");
			String type = params.getString("type");
			String id= params.getString("id");
			if(StringUtils.isNotEmpty(id)){
				String jsonStr = esCommonService.deleteById(index,type,id);
				return jsonStr;
			}
		}
		return null;
	}
	@RequestMapping(value = "/updateById",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateById(HttpServletRequest request, HttpServletResponse response){
		String paramJson = PostJsonUtils.getPostJson(request);
		if(StringUtils.isNotEmpty(paramJson)){
			JSONObject params = JSONObject.parseObject(paramJson);
			String index = params.getString("index");
			String type = params.getString("type");
			String id= params.getString("id");
			if(StringUtils.isNotEmpty(id)){
				Article article = new Article();
				
				String title = params.getString("title");
				String content = params.getString("content");
				String deploytime = params.getString("deploytime");
				String author = params.getString("author");
				article.setTitle(title);
				article.setContent(content);
				article.setDeployTime(deploytime);
				article.setAuthor(author);
				
				String jsonStr = esCommonService.updateById(index,type,id,article);
				return jsonStr;
			}
		}
		return null;
	}
	
	public static SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	@RequestMapping(value = "/updateContentById",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateContentById(HttpServletRequest request, HttpServletResponse response){
		String paramJson = PostJsonUtils.getPostJson(request);
		if(StringUtils.isNotEmpty(paramJson)){
			JSONObject params = JSONObject.parseObject(paramJson);
			String index = params.getString("index");
			String type = params.getString("type");
			String id= params.getString("id");
			if(StringUtils.isNotEmpty(id)){
				TargetContent targetContent = new TargetContent();
				
				String content = params.getString("content");
				String targetid = params.getString("targetid");
				targetContent.setTargetid(targetid);
				targetContent.setContent(content);
				
				String updatetime = sdf.format(new Date());
				targetContent.setUpdatetime(updatetime);
				
				String jsonStr = esCommonService.updateById(index,type,id,targetContent);
				return jsonStr;
			}
		}
		return null;
	}
	
	
	/*
	@RequestMapping(value = "/queryByIds", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryByIds(HttpServletRequest request, HttpServletResponse response){
		String paramJson = PostJsonUtils.getPostJson(request);
		if(StringUtils.isNotEmpty(paramJson)){
			JSONObject params = JSONObject.parseObject(paramJson);
			String ids= params.getString("ids");
			JSONArray array = JSONArray.parseArray(ids);
			if(null!=array && array.size()>0){
				List<String> list = new ArrayList<>();
				for (int i = 0; i < array.size(); i++) {
					list.add(String.valueOf(array.get(i)));
				}
				JSONArray result = appService.queryById(list);
				return result.toJSONString();
			}
		}
		return null;
	}*/
}
