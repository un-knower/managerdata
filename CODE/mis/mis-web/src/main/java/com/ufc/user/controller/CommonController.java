package com.ufc.user.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
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
import org.pcloud.spring.web.WebFileHander;
import org.pcloud.spring.web.WebUtilsAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufc.user.utils.file.WordUtils;


/**
 * 文档管理Controller
 * @author ThinkPad
 *
 */
@RequestMapping(value="manager")
@Controller
public class CommonController extends BaseMethodController{
		
	
	@Autowired
	 public WebUtilsAdapter webUtilsAdapter;

	/**
	 * 不添加, produces = "text/plain;charset=UTF-8"的情况下，中文在页面显示会乱码
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="selectFileInfo", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String selectFileInfo (HttpServletRequest request) throws Exception{
		List<Map<String,Object>> result = commonBaseQuery(request);// {"listInfo":[{"id":"7c0b772d-5037-4a8d-a500-d2b4c5307450","filename":"7-14例会会议纪要.doc","filepath":"c196318af880455eae757fcaa0be6493.doc"}],"status":"success"}
		List<Map<String,Object>> resultText = null;
		try {
//			String dir = request.getSession().getServletContext().getRealPath(File.separator)+"file_directory";
			String dir = WebFileHander.downLoadDir + File.separator + "file_directory";
			
			String filePath = dir + File.separator + result.get(0).get("filepath");
			resultText = WordUtils.readData(filePath);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			return RespResult.getInstance().setInfo("status", "FileNotFound").toJson();
		} catch (Exception e) {
			e.printStackTrace();
			return RespResult.getInstance().setInfo("status", "error").toJson();
		}
		
		
		return RespResult.getInstance().setInfo("status", "success").setInfo("listInfo", result).setInfo("textInfo", resultText).toJson();
	}
	
	public List<Map<String,Object>> commonBaseQuery(HttpServletRequest request) throws Exception {
		List<Map<String,Object>> result = null;
		try {
			baseInfoValidate(params);
			
			String jdbcTemplateName = params.get("jdbcTemplateName");
			Map<String, String> sqlListVariables = new HashMap<String, String>();

	 		EL sqlTemplate = new EL(params.get("pFile"),  params.get("pKey"),webUtilsAdapter.getBasePath()+"sql");
	 		sqlListVariables.putAll(params);
	 		sqlListVariables.putAll(getLoginResource());
	 		sqlTemplate.setVariables(sqlListVariables);
			 
	 		result = jtaManager.query(jdbcTemplateName,sqlTemplate.getSqlSegmentInfo());
		} catch (Exception e) {
			throw new Exception("公共系统查询方法baseQuery执行出错！系统提示："+e.getMessage());
		}
		return result;
	}
}
