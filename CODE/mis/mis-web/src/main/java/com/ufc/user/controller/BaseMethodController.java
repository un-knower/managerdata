package com.ufc.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.pcloud.common.el.EL;
import org.pcloud.common.json.JsonHelper;
import org.pcloud.common.json.RespResult;
import org.pcloud.spring.web.BaseController;



public class BaseMethodController extends BaseController{
	
	/*
	 * 单标的实务操作
	 * 
	 * */
	public String singleFormProcessing(Map<String,String> params) throws Exception{
		baseInfoValidate(params);//传过来的
		EL sqlTemplate = null;
		Map<String, String> sqlListVariables = new HashMap<String, String>();
		sqlListVariables.putAll(params);
 		sqlListVariables.putAll(getLoginResource());
		String[] jdbcTemplateNames = params.get("jdbcTemplateName").split(","),
				pFiles = params.get("pFile").split(","),
				pKeys = params.get("pKey").split(",");
		Map<String, Map<String, Object[]>>  sqlInfoMap = new LinkedHashMap<String, Map<String, Object[]>> ();
		//注释：string =jdbctemplateName object[] =sql+params  Map<String =sql, Object[]>=params
		for (int i = 0; i < jdbcTemplateNames.length; i++) {
			String[] sqlKeys = pKeys[i].split(",");
			for (int j = 0; j < sqlKeys.length; j++) {
				sqlTemplate = new EL(pFiles[i], sqlKeys[j],webUtilsAdapter.getBasePath()+"sql");
				sqlTemplate.setVariables(sqlListVariables);
				if(sqlInfoMap.get(jdbcTemplateNames[i]) != null){
					sqlInfoMap.get(jdbcTemplateNames[i]).putAll(sqlTemplate.getSqlSegmentInfo());
				}else 
				sqlInfoMap.put(jdbcTemplateNames[i], sqlTemplate.getSqlSegmentInfo());
			}		
		}
		jtaManager.saveOrupdate(sqlInfoMap);
		return RespResult.getInstance().setInfo("status", "success").toJson();
	}
	
	
	@SuppressWarnings("unchecked")
	public String moreFormProcessing(Map<String,String> params) throws Exception{
		
		Map<String, Map<String, Object[]>>  sqlInfoMap = new LinkedHashMap<String, Map<String, Object[]>> ();
		List<Map<?,?>> mainFromObj = JsonHelper.string2List_Map(params.get("mainFrom"));
		for (Map<?, ?> map : mainFromObj) {
			String key = null;
			if(map.get("onlyKey") != null){
				key = UUID.randomUUID().toString();
			}
			if(map.get("main") != null && map.get("main").toString().equals("true")){
				baseInfoValidate((Map<String, String>) map);
				Map<String, String> mainSqlParamVariables = new HashMap<String, String>();
				mainSqlParamVariables.put("key", key);
				mainSqlParamVariables.putAll(params);
				mainSqlParamVariables.putAll(getLoginResource());
				
				EL mainSqlTemplate = new EL(map.get("pFile").toString(), map.get("pKey").toString(),webUtilsAdapter.getBasePath()+"sql");
				mainSqlTemplate.setVariables(mainSqlParamVariables);
				String mainJdbcTemplateName = map.get("jdbcTemplateName").toString();
				if(sqlInfoMap.get(mainJdbcTemplateName) != null){
					sqlInfoMap.get(mainJdbcTemplateName).putAll(mainSqlTemplate.getSqlSegmentInfo());
				}else 
				sqlInfoMap.put(mainJdbcTemplateName, mainSqlTemplate.getSqlSegmentInfo());
			}
			
			if(map.get("from") != null){
				Map<?,?> fromObj = JsonHelper.string2Map(map.get("from").toString());
				baseInfoValidate((Map<String, String>) fromObj);
				Object fields = fromObj.get("fields");
				if(fields == null){
					throw new Exception("主从查询，从表入参结构出错，缺少“fields”节点！");
				}
				
				List<?> fromFields = (ArrayList<?>) fields;
				Map<String,String[]>  fieldMap = new HashMap<String, String[]> ();
				Map<String,String>  fieldNameMap = new HashMap<String, String> ();
				int fvcount = 0;
				
				for (int i = 0; i < fromFields.size(); i++) {
					String field = fromFields.get(i).toString();
					String val = params.get(field);
					if(val == null){
						throw new Exception("主从查询，获取从表字段“"+field+"”的值出错！");
					}
					String[] vals = val.split(",");
					if(i > 0 && fvcount != vals.length){
						throw new Exception("主从查询，从表字段“"+field+"”的值同比上一轮个数出错！");
					}
					fvcount = vals.length;
					fieldMap.put("f"+i, vals);
					fieldNameMap.put("f"+i, field);
				}
				
				int fcount =  fieldMap.size();
				EL sqlTemplate = null;
				
				for (int i = 0; i < fvcount; i++) {
					Map<String,String>  fromParamMap = new HashMap<String, String> ();
					
					fromParamMap.putAll(params);
					
					for (int j = 0; j < fcount; j++) {
						fromParamMap.put(fieldNameMap.get("f"+j), fieldMap.get("f"+j)[i]);
					}
					
					fromParamMap.put("key", key);
					fromParamMap.putAll(getLoginResource());
					
					sqlTemplate = new EL(fromObj.get("pFile").toString(), fromObj.get("pKey").toString(),webUtilsAdapter.getBasePath()+"sql");
					sqlTemplate.setVariables(fromParamMap);
					
					String jdbcTemplateName = fromObj.get("jdbcTemplateName").toString();
					
					if(sqlInfoMap.get(jdbcTemplateName) != null){
						sqlInfoMap.get(jdbcTemplateName).putAll(sqlTemplate.getSqlSegmentInfo(i+""));
					}else 
					sqlInfoMap.put(jdbcTemplateName, sqlTemplate.getSqlSegmentInfo(i+""));
				}
			}
		}
		jtaManager.saveOrupdate(sqlInfoMap);
		return null;
	}	

}
