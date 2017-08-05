<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="userTags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="en">

<head>
<script type="text/javascript" src="../source/mvc/lib/cui.js"></script>
<script type="text/javascript" src="../source/mvc/config/config.js"></script>
</head>

<body>

	<!-- Start: Content -->
	<div class="container-fluid content">
		<div class="row">
			<!--BEGIN SIDEBAR MENU-->
			<userTags:left currentNav="user"></userTags:left>
			<!--END SIDEBAR MENU-->
		</div>

	</div>
	<!-- End Sidebar -->
	<div id="page">

	</div>

	<!--/container-->


	<div class="clearfix"></div>


</body>

</html>