package com.ufc.user.controller;

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

@RequestMapping("/menu")
@Controller
@SuppressWarnings("unchecked")
public class MenuController extends BaseController{

	@Autowired
	private JtaManager jtaManager;
	
	
	@RequestMapping(value = "/del", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String del(HttpServletRequest request) throws Exception {
		Map<String, String> params = webUtilsAdapter.getParameters();
		EL pagingTemplate = new EL("menu","del",webUtilsAdapter.getBasePath()+"sql");
		pagingTemplate.setVariables(params);
		jtaManager.saveOrupdate("mysqlTemplate",pagingTemplate.getSqlSegmentInfo());
		return RespResult.getInstance().setInfo("status", "success").toJson();
	}
	@RequestMapping(value = "/setRadion", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String setRadion(HttpServletRequest request) throws Exception {
		Map<String, String> params = webUtilsAdapter.getParameters();
		EL pagingTemplate = new EL("menu","setRadion",webUtilsAdapter.getBasePath()+"sql");
		pagingTemplate.setVariables(params);
		jtaManager.saveOrupdate("mysqlTemplate",pagingTemplate.getSqlSegmentInfo());
		return RespResult.getInstance().setInfo("status", "success").toJson();
	}
}
