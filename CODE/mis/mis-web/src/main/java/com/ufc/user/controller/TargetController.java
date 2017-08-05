package com.ufc.user.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.pcloud.common.el.EL;
import org.pcloud.common.json.JsonHelper;
import org.pcloud.common.json.RespResult;
import org.pcloud.spring.web.WebUtilsAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 文档类别管理Controller
 * @author ThinkPad
 *
 */
@RequestMapping(value="target")
@Controller
public class TargetController extends BaseMethodController{
		
	
	@Autowired
	 public WebUtilsAdapter webUtilsAdapter;

	/***
	 * 表单处理
	 * 
	 * Example：
	 * 		$.ajax({
	 *			url : 'xxx/formProcessing',
	 *			type : "post",
	 *			data : {
	 *				    "jdbcTemplateName":"xxx1,xxx2",
	 *					"pFile":"xxx1,xxx2",
	 *					"pKey":"xxx1|xxx1-1...,xxx2|xxx2-2...",
	 *					-----------------------------------------
	 *					"mainFrom":JSON.stringify(
	 *						[{
	 *							"jdbcTemplateName":"xxx",
	 *							"pFile":"xxx",
	 *							"pKey":"xxx",
	 *							"main":true,
	 *							"onlyKey":true,
	 *							"from":{
	 *								"jdbcTemplateName":"xxx",
	 *								"pFile":"xxx",
	 *								"pKey":"xxx",
	 *								"fields":['f1','f2',...]
	 *							}
	 *						},...]
	 *					),
	 *					"a":"xxx","b":"xxx",
	 *					...
	 *			},
	 *			dataType : 'json',
	 *			async :true,
	 *			success : function(result) {
	 *				if (result.status === 'success') {
	 *					//下面写你的任何业务
	 *					...
	 *				}
	 *			},
	 *			error : function(XMLHttpRequest, textStatus, errorThrown) {...}
	 *		});
	 * @return
	 * 		字段信息JSON
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/formProcessing", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String formProcessing(HttpServletRequest request) throws Exception {
		
		String person = "admin";// 操作人
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String time = sdf.format(new Date());
		
		params.put("createPerson", person);
		params.put("createTime", time);
		params.put("updatePerson", person);
		params.put("updateTime", time);
		
		params.put("sys.uuid", UUID.randomUUID().toString());// 手动添加主键
		
	try {
		String mainFrom = params.get("mainFrom");
		if(mainFrom == null){
			baseInfoValidate(params);
			String[] jdbcTemplateNames = params.get("jdbcTemplateName").split(","),
					pFiles = params.get("pFile").split(","),
					pKeys = params.get("pKey").split(",");
			if(jdbcTemplateNames.length != pFiles.length
					|| jdbcTemplateNames.length != pKeys.length
					|| pFiles.length != pKeys.length){
				throw new Exception("您当前请求中模板基本信息个数不对等！");
			}
			Map<String, Map<String, Object[]>>  sqlInfoMap = new LinkedHashMap<String, Map<String, Object[]>> ();
			Map<String, String> sqlListVariables = new HashMap<String, String>();
			sqlListVariables.putAll(params);
	 		sqlListVariables.putAll(getLoginResource());
			EL sqlTemplate = null;
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
		}else{
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
		}

	} catch (Exception e) {
		throw new Exception("公共组件表单数据执行方法formProcessing出错！系统提示："+e.getMessage());
	}
		return RespResult.getInstance().setInfo("status", "success").toJson();
	}
}
