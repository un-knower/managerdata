package com.zkdj.gxkjt.es.controller;

import java.util.ArrayList;
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
import com.ufc.user.utils.Pager;
import com.ufc.user.utils.PostJsonUtils;
import com.zkdj.gxkjt.es.service.AppService;

/** 
* @author DengJie 
* @version 创建时间：2017年7月26日
* 类说明:
*/
@Controller
@RequestMapping("/app")
public class AppController{
	@Autowired
	private AppService appService;
	
	/**
	 * 全文查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/query",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(HttpServletRequest request, HttpServletResponse response){
		try {
			String paramJson = PostJsonUtils.getPostJson(request);
			JSONObject params = JSONObject.parseObject(paramJson);
			Pager<JSONObject> pager=appService.query(params);
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
			String id= params.getString("id");
			if(StringUtils.isNotEmpty(id)){
				JSONObject json=appService.queryById(id);
				return json.toJSONString();
			}
		}
		return null;
	}
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
	}
}
