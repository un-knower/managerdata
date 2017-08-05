package org.pcloud.spring.web;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

@Component
public class WebUtilsAdapter extends WebUtils{
	@Autowired
	private HttpServletRequest request;
	
	public HttpSession getSession() {
		return request.getSession();
	}
	
	public String getBasePath() {
		return getSession().getServletContext().getRealPath("/");
	}
 

	public Map<String, String> getParameters() {
		Map<String, String> params = new HashMap<String, String>();
		Enumeration<?> paramNames = request.getParameterNames();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String value = request.getParameter(paramName);
			if (request.getParameterValues(paramName).length > 1) {
				value = array2String(request.getParameterValues(paramName), ",");
			}
			params.put(paramName, value);
		}
		return params;
	}
	
	public Map<String, String> getParameters(MultipartHttpServletRequest multipartRequest) {
		Map<String, String> params = new HashMap<String, String>();
		Enumeration<?> paramNames = multipartRequest.getParameterNames();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String value = multipartRequest.getParameter(paramName);
			if (multipartRequest.getParameterValues(paramName).length > 1) {
				value = array2String(multipartRequest.getParameterValues(paramName), ",");
			}
			params.put(paramName, value);
		}
		return params;
	}
	
	private String array2String(String[] array,String flag) {
		StringBuilder sb = new StringBuilder();
		if (array != null && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				if (i < array.length - 1) {
					sb.append(array[i] + flag);
				} else {
					sb.append(array[i]);
				}
			}
		}
		return sb.toString();
	}
	
}
