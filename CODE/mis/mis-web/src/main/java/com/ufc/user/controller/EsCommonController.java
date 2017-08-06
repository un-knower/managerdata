package com.ufc.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.pcloud.common.json.RespResult;
import org.pcloud.spring.web.Page;
import org.pcloud.spring.web.WebUtilsAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ufc.user.utils.RequestUtils;

/**
 * EsCommonController
 * 
 * @author ThinkPad
 *
 */
@Controller
@RequestMapping(value = "escommon")
public class EsCommonController extends BaseMethodController {

	@Autowired
	public WebUtilsAdapter webUtilsAdapter;
	
	/**
	 * 模糊查询
	 * @param paramJson
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="searchall", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String searchall(HttpServletRequest request) throws JsonProcessingException {
		JSONObject jsonObject = new JSONObject();
//		List <String>  list =new ArrayList<String>();
//		for(String id :params.get("ids").split(",")){
//			list.add(id);
//		}
//		String filed = params.get("filed");
//		String queryValue = params.get("queryValue");
		String filed = "title";
		String queryValue = params.get("title");
//		String filed2 = "source";
//		String queryValue2 = params.get("source");
//		String filed3 = "author";
//		String queryValue3 = params.get("author");
		
		
		
		String index = params.get("index");
		String type = params.get("type");
		String pageNo = params.get("pageNo");
		String pageSize = params.get("pageSize");
		String order = params.get("order");
		jsonObject.put("filed", filed);
		jsonObject.put("queryValue", queryValue);
//		jsonObject.put("filed2", filed);
//		jsonObject.put("queryValue2", queryValue);
//		jsonObject.put("filed3", filed);
//		jsonObject.put("queryValue3", queryValue);
		jsonObject.put("index", index);
		jsonObject.put("type", type);
		jsonObject.put("pageNo", pageNo);
		jsonObject.put("pageSize", pageSize);
//		jsonObject.put("ids", list);
//		jsonObject.put("url", "http://172.168.17.95:8080/es-data-service/app/queryByIds");
//		System.out.println(params.get("ids").split(",").toString());
//		String ss = null;
		String ss =new RequestUtils().sendPost("http://127.0.0.1:8080/es-data-service/escommon/searchfulltext",jsonObject.toJSONString());
		//Pager<JSONObject> pager = JSONObject.parseObject(ss);
		
		
		Page page = getPage();
		page.setOrder(order);
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		page.setList((List<Map<String,Object>>)JSONObject.parseObject(ss).get("list"));
		
		Object countObj = JSONObject.parseObject(ss).get("count");
		int count = 0;
		if(countObj!=null) {
			count = (Integer) countObj;
		} else {
			
		}
		page.setTotal(count);
		
		return RespResult.getInstance().setInfo("status", "success").setInfo("listInfo", page).toJson();
	}
	
	
	/**
	 * 根据ID查询索引
	 * @param paramJson
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="queryById", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String queryByID(HttpServletRequest request) throws JsonProcessingException {
		JSONObject jsonObject = new JSONObject();
		String index = params.get("index");
		String type = params.get("type");
		String id = params.get("id");
		jsonObject.put("index", index);
		jsonObject.put("type", type);
		jsonObject.put("id", id);
		String ss =new RequestUtils().sendPost("http://127.0.0.1:8080/es-data-service/escommon/queryById",jsonObject.toJSONString());
		Map<String,Object> resultmap = JSONObject.parseObject(ss);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list.add(resultmap);
		
		return RespResult.getInstance().setInfo("status", "success").setInfo("listInfo", list).toJson();
	}
	
	/**
	 * 根据ID删除索引
	 * @param paramJson
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="deleteById", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String deleteByID(HttpServletRequest request) throws JsonProcessingException {
		JSONObject jsonObject = new JSONObject();
		String index = params.get("index");
		String type = params.get("type");
		String id = params.get("id");
		jsonObject.put("index", index);
		jsonObject.put("type", type);
		jsonObject.put("id", id);
		String ss =new RequestUtils().sendPost("http://127.0.0.1:8080/es-data-service/escommon/deleteById",jsonObject.toJSONString());
		return RespResult.getInstance().setInfo("status", "success").setInfo("listInfo", ss).toJson();
	}
	
	/**
	 * 根据ID更新索引
	 * @param paramJson
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="updateById", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String updateByID(HttpServletRequest request) throws JsonProcessingException {
		JSONObject jsonObject = new JSONObject();
		// {edit_title=test标题6：标题666, edit_content=test内容6：“十二五”时期，外经贸控股公司将“一体两翼” 作为引领企业发展的总体战略定位，通过全面深化国资国企改革、改造传统产业结构、盘活资产存量、强化重点项目建设等举措，实现企业内生动力增加、外延需求加速扩张，整体向外向型现代服务业转型取得明显成效。, edit_deploytime=2017-08-06 11:6:11, edit_id=d797fa29e1124d03805658a7e6589f9c, edit_authoer=刘德华6}
		String index = params.get("index");
		String type = params.get("type");
		String id = params.get("edit_id");
		
		jsonObject.put("title", params.get("edit_title"));
		jsonObject.put("content", params.get("edit_content"));
		jsonObject.put("deploytime", params.get("edit_deploytime"));
		jsonObject.put("author", params.get("edit_author"));
		
		jsonObject.put("index", index);
		jsonObject.put("type", type);
		jsonObject.put("id", id);
		String ss =new RequestUtils().sendPost("http://127.0.0.1:8080/es-data-service/escommon/updateById",jsonObject.toJSONString());
		return RespResult.getInstance().setInfo("status", "success").setInfo("listInfo", ss).toJson();
	}
}
