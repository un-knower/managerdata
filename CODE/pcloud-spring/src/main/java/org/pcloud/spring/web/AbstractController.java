
package org.pcloud.spring.web;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.pcloud.common.properties.PropertiesHelper;
import org.pcloud.spring.jdbc.JtaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public abstract class AbstractController{
	@Autowired
	public WebUtilsAdapter webUtilsAdapter;
	
	@Autowired
	public JtaManager jtaManager;
	
	public Map<String, String> params;

	/** 基于@ExceptionHandler异常处理 
	 * @throws Exception */
	@ExceptionHandler
	public void exp(HttpServletRequest request, HttpServletResponse response,
			Exception ex) throws Exception {
		ex.printStackTrace();
		PrintWriter writer = response.getWriter();
		writer.write(ex.getMessage());
		writer.flush();
	}

	/**
	 * 所有一切入口之前执行操作方法
	 * 
	 * @throws Exception
	 */
	@ModelAttribute
	public void beforeAction(HttpServletRequest request, HttpSession session) throws Exception {
		
		try {
			MultipartResolver resolver = new CommonsMultipartResolver(session.getServletContext());
			MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
			
			params = webUtilsAdapter.getParameters(multipartRequest);
			try {
				WebFileHander.uploadFile(multipartRequest, params, webUtilsAdapter);
			} catch (Exception e) {
				throw new Exception("附件存储出错！");
			}
		} catch (Exception e) {
			params = webUtilsAdapter.getParameters();
		}
	}
	
	public  Map<String,String> getLoginResource() throws Exception {
		Object obj = webUtilsAdapter.getSession().getAttribute("loginResource");
		if(obj != null){
			return ((SysDataManager) obj).getMap();
		}
		return new HashMap<String,String>();
	}
	

	public Page getPage() {
		Page page = new Page();
		page.setPageNo(Integer.parseInt(params.get("pageNo")));
		page.setPageSize(Integer.parseInt(params.get("pageSize")));
		String order = params.get("order");
		if(order == null ){order = "";}
		page.setOrder(order);
		return page;
	}
	

	public void baseInfoValidate(Map<String,String> params) throws Exception {
		if(params.get("jdbcTemplateName") == null){
			throw new Exception("您当前请求没有传入数据源模板参数jdbcTemplateName！");
		}
		
		if(params.get("pFile") == null ||  params.get("pKey") == null){
			throw new Exception("您当前请求没有传入sqlFile或者sqlKey！");
		}
	} 
	
	
	public String getSql(String pFile,String pKey,String...path) throws Exception {
		if(path == null || path.length == 0){
			return PropertiesHelper.getInstance().readPropertiesFile(pFile, pKey,webUtilsAdapter.getBasePath()+"sql");
		}
		return PropertiesHelper.getInstance().readPropertiesFile(pFile, pKey,path);
	}  
}
