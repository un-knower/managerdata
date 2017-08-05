<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="userTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">

<head>

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

		<div id="cz" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div>
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title" id="myModalLabel">新增用户</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" id="formId">
							<div class="form-group">
								<label for="mname" class="col-sm-3 control-label">用户名:</label>
								<div class="col-sm-3">
									<input type="text" class="form-control" id="mname"  name ="username" usemap="{byteRangeLength:[1,30,3],required:true,string:true}" />
								</div>

							</div>
							<div class="form-group">
								<label for="mname" class="col-sm-3 control-label">密码:</label>
								<div class="col-sm-3">
									<input type="password" class="form-control" name="password" usemap="{byteRangeLength:[1,30,3],required:true}">
								</div>
							</div>
							<div class="form-group">
								<label for="mname" class="col-sm-3 control-label">确认密码:</label>
								<div class="col-sm-3">
									<input type="password" class="form-control" name="confirmPassword" usemap="{byteRangeLength:[1,30,3],required:true}">
								</div>
							</div>
							<div class="form-group">
								<label for="mname" class="col-sm-3 control-label">电话:</label>
								<div class="col-sm-3">
									<input type="text" class="form-control" name="phone">
								</div>
							</div>
							<div class="form-group">
								<label for="mname" class="col-sm-3 control-label">email:</label>
								<div class="col-sm-3">
									<input type="text" class="form-control" name="email">
									<input type="hidden" name ="locked"  value="1">
								</div>
							</div>
							<div class="modal-footer ">
								<a class="btn btn-zdy" id="submit">点击添加</a>
								<button type="button" class="btn btn-zdy" data-dismiss="modal">取消</button>

							</div>
						</form>
					</div>

				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</div>

	<!--/container-->
	<div class="clearfix"></div>
	<script type="text/javascript" src="${ctx}/source/mvc/lib/cui.js"></script>
	<script type="text/javascript" src="${ctx}/source/mvc/config/config.js"></script>
	<script type="text/javascript">
		CUI.use([ 'ajaxform', 'layer', 'utils' ], function($ajax, $layer,
				$utils) {
			return {
				initialize : function() {
					//alert("ssss");
					new CuiAjaxForm($('#formId'), {
						submitSelector : $('#submit'),
						 action : '${ctx}/member/addUser', 
					/* 	action : '${ctx}/member/formProcessing',  */
						beforeEvent : function(formData, jqForm, options) {
							$.insertDynamicDataForTheForm(formData, jqForm,
									options, 'jdbcTemplateName',
									'mysqlTemplate');
							$.insertDynamicDataForTheForm(formData, jqForm,
									options, 'pFile', 'user');
							$.insertDynamicDataForTheForm(formData, jqForm,
									options, 'pKey', 'add');
							return true;
						},
						callbackEvent : function(result) {
							if (result.status == 'success') {
								alert('success!!!');
							}
						}
					});
				}
			}
		});
	</script>
</body>

</html>