package com.ufc.user.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.pcloud.common.el.EL;
import org.pcloud.common.json.RespResult;
import org.pcloud.spring.jdbc.JtaManager;
import org.pcloud.spring.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/role")
@Controller
@SuppressWarnings("unchecked")
public class RoleController extends BaseController{

	@Autowired
	private JtaManager jtaManager;
	
	@RequestMapping(value = "/update", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String update() throws Exception{
		Map<String, String> params = webUtilsAdapter.getParameters();
		EL pagingTemplate = new EL("role","update",webUtilsAdapter.getBasePath()+"sql");
		pagingTemplate.setVariables(params);
		jtaManager.saveOrupdate("mysqlTemplate",pagingTemplate.getSqlSegmentInfo());
		return RespResult.getInstance().setInfo("status", "success").toJson();
	}
	
	@RequestMapping(value = "/del", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String del(HttpServletRequest request) throws Exception {
		Map<String, String> params = webUtilsAdapter.getParameters();
		EL pagingTemplate = new EL("role","delete",webUtilsAdapter.getBasePath()+"sql");
		pagingTemplate.setVariables(params);
		jtaManager.saveOrupdate("mysqlTemplate",pagingTemplate.getSqlSegmentInfo());
		return RespResult.getInstance().setInfo("status", "success").toJson();
	}
	@RequestMapping(value = "/delTree", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String delTree(HttpServletRequest request) throws Exception {
		Map<String, String> params = webUtilsAdapter.getParameters();
		EL pagingTemplate = new EL("role","delTree",webUtilsAdapter.getBasePath()+"sql");
		pagingTemplate.setVariables(params);
		jtaManager.saveOrupdate("mysqlTemplate",pagingTemplate.getSqlSegmentInfo());
		return RespResult.getInstance().setInfo("status", "success").toJson();
	}
	@RequestMapping(value = "/insTree", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String insTree(HttpServletRequest request) throws Exception {
		Map<String, String> params = webUtilsAdapter.getParameters();
		EL pagingTemplate = new EL("role","insTree",webUtilsAdapter.getBasePath()+"sql");
		Map<String, String> sqlListVariables = new HashMap<String, String>();
 		sqlListVariables.putAll(params);
 		sqlListVariables.putAll(getLoginResource());
 		pagingTemplate.setVariables(sqlListVariables);
		jtaManager.saveOrupdate("mysqlTemplate",pagingTemplate.getSqlSegmentInfo());
		return RespResult.getInstance().setInfo("status", "success").toJson();
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
        String str = sdf.format(new Date());
        System.out.println(str);
	}
}
