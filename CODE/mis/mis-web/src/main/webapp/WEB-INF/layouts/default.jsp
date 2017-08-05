<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<c:set var="ctx" value="<%=path%>" />
<!DOCTYPE html>
<html lang="en">

	<head>
	
		<!-- Basic -->
    	<meta charset="UTF-8" />

		<title>管理大数据平台</title>

		<!-- Mobile Metas -->
	    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<!-- Vendor CSS-->
		<link href="${ctx}/html/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
		<link href="${ctx}/html/assets/vendor/skycons/css/skycons.css" rel="stylesheet" />
		<link href="${ctx}/html/assets/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${ctx}/html/assets/plugins/bootstrap-table/bootstrap-table.css"/>
		<!-- Plugins CSS-->
		<link href="${ctx}/html/assets/plugins/jquery-ui/css/jquery-ui-1.10.4.min.css" rel="stylesheet" />	
		<link href="${ctx}/html/assets/plugins/scrollbar/css/mCustomScrollbar.css" rel="stylesheet" />
		<link href="${ctx}/html/assets/plugins/bootkit/css/bootkit.css" rel="stylesheet" />
		<!-- Page CSS -->		
		<link href="${ctx}/html/assets/css/style.css" rel="stylesheet" />
		<link href="${ctx}/html/assets/css/common.css" rel="stylesheet" />
		<!-- end: CSS file-->	    
		
		<!-- start: JavaScript-->
		<!-- Vendor JS-->
		<script src="${ctx}/html/assets/vendor/js/jquery-2.1.1.min.js"></script>
		<script src="${ctx}/html/assets/vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="${ctx}/html/assets/vendor/skycons/js/skycons.js"></script>
		<!-- Plugins JS-->
		<script src="${ctx}/html/assets/plugins/jquery-ui/js/jquery-ui-1.10.4.min.js"></script>
		<script src="${ctx}/html/assets/plugins/scrollbar/js/jquery.mCustomScrollbar.concat.min.js"></script>
		<script src="${ctx}/html/assets/plugins/bootstrap-table/bootstrap-table.js"></script>
		<script src="${ctx}/html/assets/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
		<script src="${ctx}/html/assets/plugins/bootkit/js/bootkit.js"></script>
		<!-- Theme JS -->
		<script src="${ctx}/html/assets/js/core.min.js"></script>
		<!-- Pages JS -->
		<script src="${ctx}/html/assets/js/pages/index.js"></script>
		<!-- end: JavaScript-->
		<sitemesh:head />

	</head>
	
	<body>
	<div id="header-topbar-option-demo" class="page-header-topbar">
	   <%@ include file="/WEB-INF/layouts/header.jsp"%>
	</div>
	<sitemesh:body />	
		
	</body>
	
</html>