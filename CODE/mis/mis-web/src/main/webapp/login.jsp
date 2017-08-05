<%@ page language="java" import="java.util.*,java.net.URLEncoder" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>登录页面</title>

		<script type="text/javascript" src="source/mvc/lib/cui.js"></script>
		<script type="text/javascript" src="source/mvc/config/config.js"></script>
	</head>

	<body class="login-layout">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="ace-icon fa fa-leaf green"></i>
									<span class="red">PPT</span>
									<span class="white" id="id-text2">应用管理</span>
								</h1>
								 
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
												请输入您的信息
											</h4>

											<div class="space-6"></div>

											<form action="member/toLogin" method="post" id="loginForm">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" id="username" value ="test" name="username" placeholder="用户名称" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" value ="test" id="password" name="password" placeholder="密码" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<div class="clearfix">
														<label class="inline">
															<span class="lbl" style="color:red;">${str}</span>
														</label>
													</div>
												</fieldset>
											</form>

											 
										</div><!-- /.widget-main -->
										<div class="toolbar clearfix">
											<button type="button" class=" btn btn-primary btn-login" style="width: 100%;">
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">登录</span>
														</button>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->
 
							</div><!-- /.position-relative -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->

	 
		<script type="text/javascript">
				CUI.use(['ajaxform','layer'], function($ajax,$layer) {
					return {
						initialize : function() {
							 $('.btn-login').on('click', function(e) {
								 if($('#username').val() != '' && $('#password').val() != ''){
									 $('#loginForm').submit();
								 }
								e.preventDefault();
							 });
						}
					}
				});
		</script>
	</body>
</html>

