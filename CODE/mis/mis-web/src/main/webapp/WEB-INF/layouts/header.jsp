<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>


		<!-- Start: Header -->
		<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
		<div class="navbar" role="navigation">
			<div class="container-fluid container-nav">
				<!-- Navbar Right -->
				<div class="navbar-right">					

					<!-- Userbox -->
					<div class="userbox">
							<div class="profile-info">
								<span class="name"><shiro:principal></shiro:principal></span>
								

							</div>	
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<figure class="profile-picture hidden-xs">
								<img src="${ctx }/html/assets/images/avatar.jpg" class="img-circle" alt="" />
							</figure>
			
							<i class="fa custom-caret"></i>
						</a>
						<div class="dropdown-menu">
							<ul class="list-unstyled">
								<li class="dropdown-menu-header bk-bg-white bk-margin-top-15">						
									<div class="progress progress-xs  progress-striped active">
										<div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
											60%
										</div>
									</div>							
								</li>	
<!-- 								<li	>
									<a href="javascript:void(0);"><i class="fa fa-user"></i> 我的账户</a>
								</li> -->
								<li>
									<a  id="ckpass"  data-toggle="modal" data-target="#mm" ><i class="fa fa-wrench"></i>重置密码</a>
								</li>

								<li>
									<a href="../member/logout"><i class="fa fa-power-off"></i>退出</a>
								</li>
							</ul>
						</div>						
					</div>
					<!-- End Userbox -->
				</div>
				<!-- End Navbar Right -->
			</div>
			<!----------------------------------ww----------------------------------->	
		</div>
		<!-- End: Header -->
		<div class="modal " id="mm" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">修改密码</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form" id ="editpass">
							
							<div class="form-group">
								<label for="mname" class="col-sm-3 control-label">旧密码:</label>
								<div class="col-sm-3">
									<input type="password" class="form-control"  name="old_password" usermap="{required:true}">
								</div>
							</div>
							
							<div class="form-group">
								<label for="mname" class="col-sm-3 control-label">密码:</label>
								<div class="col-sm-3">
									<input type="password" class="form-control" id="re_password" name="password" usermap="{required:true,compare:['comfirm_password']}">
								</div>
							</div>
							
							<div class="form-group">
								<label for="mname" class="col-sm-3 control-label">确认密码:</label>
								<div class="col-sm-3">
									<input type="password" class="form-control" id="comfirm_password" name="comfirm_password" usermap="{required:true,compare:['re_password']}">
								</div>
							</div>
							<div class="modal-footer ">
								<a class="btn btn-zdy" id="re_submit">提交修改</a>
								<button type="button" class="btn btn-zdy" data-dismiss="modal" id ="renew" >取消</button>
							</div>
						</form>
						
					</div>

				</div>
			</div>
		</div>
	<script type="text/javascript" src="${ctx}/source/mvc/lib/cui.js"></script>
	<script type="text/javascript" src="${ctx}/source/mvc/config/config.js"></script>
		<script>
		$('#mm').on('show.bs.modal', function () {
		})
		$('#mm').on('hide.bs.modal', function () {
				 $("#editpass").resetForm();
			})
			
	CUI.use(['ajaxform','layer','utils'], function($ajax,$layer,$utils) {
		return {
			initialize : function() {
	            new CuiAjaxForm($('#editpass'), {
	                submitSelector: $('#re_submit'),
	                action: '../member/editPassword',
	                beforeEvent: function(formData, jqForm, options) {
	                    $.insertDynamicDataForTheForm(formData, jqForm, options, 'username','${username1}');
	                    return true;
	                },
	                callbackEvent: function(result) {
	                    if (result.status == 'success') {
	                        alert('success!!!');
	                        $('#renew').click();
	                    }else{
	                    	  alert('修改失败!!!');
	                    	  $('#renew').click();
	                    }
	                }
	            });
			}
		}
	});
		</script>
