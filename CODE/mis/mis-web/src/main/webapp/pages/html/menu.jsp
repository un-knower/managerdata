<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/mis-web/source/mvc/lib/cui.js"></script>
<script type="text/javascript"
	src="/mis-web/source/mvc/config/config.js"></script>
<script type="text/javascript" src="/mis-web/source/mvc/page/page.js"></script>
<link href="/mis-web/source/mvc/page/page.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	//查询分页列表
	/*
	function show(pageNo){
		$.ajax({
	 			url : '/mis-web/member/pageSearch',
	 			type : "post",
	 			data : {
	 				    "jdbcTemplateName":"mysqlTemplate",
	 					"pFile":"menu",
	 					"pKey":"userInfo2",
	 					"type":"mysql",
	 					"pageNo":pageNo,
	 					"pageSize":"10",
	 					"order":"",
	 					"queryTermStr":""
	 			},
	 			dataType : 'json',
	 			async :true,
	 			success : function(result) {
	 				if (result.status === 'success') {
	 				//后台返回的结果集，格式:
	 					var pageSource = result.listInfo;
	 					//下面写你的任何业务
	 					//alert(JSON.stringify(pageSource));
	 					//alert(pageSource.list[0].create_date);
	 					$("#mytable").html("");
	 					$(pageSource.list).each(function(index,element){
	 						var ind = parseInt(index)+1;
	 						var update_date = element.update_date;
	 						if(update_date == null) update_date = "";
	 						var ra_str = element.radion;
	 						var ra_name = "";
	 						
	 						if(ra_str=="0"){
	 							ra_name = "启用";
	 							ra_str="1";
	 						}else{
	 							ra_name = "禁用";
	 							ra_str="0";
	 						}
	 						$("#mytable").append("<tr><td>"+ind+"</td><td>"+element.menu_name+"</td><td>"+element.parent_id+
	 								"</td><td>"+element.url+"</td></td><td>"+element.create_date+"</td><td>"+update_date+"</td><td>"+element.describe_msg+"</td><td><a href=\"javascript:void(0)\"  onclick=\"radion('"+element.id+"','"+ra_str+"')\">"+ra_name+"</a> | <a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#moodel\" onclick=\"kan('"+element.id+"')\">查看</a> | <a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#ee\" onclick=\"edit('"+element.id+"')\">编辑</a> | <a href=\"javascript:void(0)\" onclick=\"del('"+element.id+"')\">删除</a></td></tr>");
	 						//下拉列表选项 
	 						$("#mname2").append("<option value='"+element.id+"'>"+element.menu_name+"</option>");
	 					});
	 					$("#mytable").append("<tr><td colspan=\"8\" id=\"pager\"></td></tr>");
	 			         //创建分页
	 			        var target = $('#pager');
	 					pageClick(pageSource.pageNo,pageSource.total,target,10);
	 				}
	 			},
	 			error : function(XMLHttpRequest, textStatus, errorThrown) {
	 				alert("查询失败!!!");
	 			}
	 		});
	}

	 */

	//创建菜单
	CUI.use([ 'ajaxform', 'layer', 'utils' ], function($ajax, $layer, $utils) {
		var options = $("#mname1 option:selected");
		return {
			initialize : function() {
				new CuiAjaxForm($('#formson'), {
					submitSelector : $('#submit2'),
					action : '/mis-web/member/formProcessing',
					beforeEvent : function(formData, jqForm, options) {
						$.insertDynamicDataForTheForm(formData, jqForm,
								options, 'jdbcTemplateName', 'mysqlTemplate');
						$.insertDynamicDataForTheForm(formData, jqForm,
								options, 'pFile', 'menu');
						$.insertDynamicDataForTheForm(formData, jqForm,
								options, 'pKey', 'add1');
						return true;
					},
					callbackEvent : function(result) {
						if (result.status == 'success') {
							$("#mode2222").click();
							pageSearch(1);
						}
					}
				});
			}
		}
	});

	// 编辑完更新菜单
	CUI.use([ 'ajaxform', 'layer', 'utils' ], function($ajax, $layer, $utils) {
		return {
			initialize : function() {
				pageSearch(1);//页面加载后执行
				new CuiAjaxForm($('#editUI'), {
					submitSelector : $('#sub'),
					action : '/mis-web/member/formProcessing',
					beforeEvent : function(formData, jqForm, options) {
						$.insertDynamicDataForTheForm(formData, jqForm,
								options, 'jdbcTemplateName', 'mysqlTemplate');
						$.insertDynamicDataForTheForm(formData, jqForm,
								options, 'pFile', 'menu');
						$.insertDynamicDataForTheForm(formData, jqForm,
								options, 'pKey', 'update');
						$.insertDynamicDataForTheForm(formData, jqForm,
								options, 'update_date', new Date());
						return true;
					},
					callbackEvent : function(result) {
						if (result.status == 'success') {
							$("#qx1").click();
							alert('修改成功');
							pageSearch(1);
						}
					}
				});
			}
		}
	});

	//查看菜单
	function kan(id) {
		$(":disabled").removeAttr("disabled");//解除查看表单上元素的禁用状态，否则添充方法获取不到元素
		
		$.ajax({
			url : '/mis-web/member/baseQuery',
			type : "post",
			data : {
				"jdbcTemplateName" : "mysqlTemplate",
				"pFile" : "menu",
				"pKey" : "userInfo1",
				"type" : "mysql",
				"id" : id
			},
			dataType : 'json',
			async : true,
			success : function(result) {
				if (result.status === 'success') {
					//后台返回的结果集，格式[{"xx":"x"},{"xxx":"xx"},...]
					//[{id=0ca0b02f-8879-4a8c-a150-abd8aa8c4972, rolename=&#29992;&#25143;2, create_date=06/07/2017, update_date=null, reserved1=null, reserved2=null, reserved3=null}]
					var listInfo = result.listInfo;
					//alert(listInfo[0].toString());
					//下面写你的任何业务
					$.formDataView($('#showUI'),listInfo);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("跳转编辑页面失败");
			}
		});
	}

	//跳转编辑页面，回显数据
	function edit(id) {
		//alert(id);
		$.ajax({
			url : '/mis-web/member/baseQuery',
			type : "post",
			data : {
				"jdbcTemplateName" : "mysqlTemplate",
				"pFile" : "menu",
				"pKey" : "userInfo1",
				"type" : "mysql",
				"id" : id
			},
			dataType : 'json',
			async : true,
			success : function(result) {
				if (result.status === 'success') {
					//后台返回的结果集，格式[{"xx":"x"},{"xxx":"xx"},...]
					//[{id=0ca0b02f-8879-4a8c-a150-abd8aa8c4972, rolename=&#29992;&#25143;2, create_date=06/07/2017, update_date=null, reserved1=null, reserved2=null, reserved3=null}]
					var listInfo = result.listInfo;
					//alert(listInfo[0].toString());
					//下面写你的任何业务
					$.formDataFill($('#editUI'),listInfo);
					
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("跳转编辑页面失败");
			}
		});
	}

	//删除菜单
	function del(obj) {
		if (confirm("你确认要删除这个菜单吗？")) {
			$.ajax({
				url : '/mis-web/menu/del',
				type : "post",
				data : {
					"jdbcTemplateName" : "mysqlTemplate",
					"pFile" : "menu",
					"pKey" : "del",
					"type" : "mysql",
					"id" : obj
				},
				dataType : 'json',
				async : true,
				success : function(result) {
					if (result.status === 'success') {
						//后台返回的结果集，格式[{"xx":"x"},{"xxx":"xx"},...]
						var listInfo = result.listInfo;
						//下面写你的任何业务
						//alert(listInfo[0].username);
						alert("删除成功");
						pageSearch(1);

					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("删除失败");
				}
			});
		}

	}
	//设置菜单启用/禁用
	function radion(id, status) {
		// if(confirm("你确认要删除这个角色吗？")){
		$.ajax({
			url : '/mis-web/menu/setRadion',
			type : "post",
			data : {
				"jdbcTemplateName" : "mysqlTemplate",
				"pFile" : "menu",
				"pKey" : "setRadion",
				"type" : "mysql",
				"id" : id,
				"radion" : status
			},
			dataType : 'json',
			async : true,
			success : function(result) {
				if (result.status === 'success') {
					pageSearch(1);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("设置失败");
			}
		});
		// }

	}
 //查询按钮绑定点击事件
	CUI.use([ 'ajaxform', 'utils', 'layer' ], function($ajax, $utils, $layer) {
		return {
			initialize : function() {
				var _this = this;
				$('.search').click(function() {
					pageSearch(1);
				});
			}
		}
	});
 //列表页，查询用到的
	function pageSearch(pageNo) {
		$
				.asyncRequest({
					url : '/mis-web/member/pageSearch',
					data : $.extend({}, {
						"jdbcTemplateName" : "mysqlTemplate",
						"pFile" : "menu",
						"pKey" : "userInfo2",
						"type" : "mysql",
						"pageNo" : pageNo,
						"pageSize" : "20",
						"order" : ""
					}, $('#searchFrom').buildQueryInfo(), true),
					event : function(result) {
						if (result.status === 'success') {
							//后台返回的结果集，格式:
							var pageSource = result.listInfo;
							//下面写你的任何业务
							//alert(JSON.stringify(pageSource));
							//alert(pageSource.list[0].create_date);
							$("#mytable").html("");
							$(".select").empty();
							$(".select").append(
									"<option value='0'>请选择...</option>");
							$(pageSource.list)
									.each(
											function(index, element) {
												var ind = parseInt(index) + 1;//　序号
												var update_date = element.update_date;// 修改时间
												if (update_date == null){update_date = "";}
												var create_date = element.create_date;// 创建时间
												if (create_date == null){create_date = "";}
												var parent_name = element.parent_name;// 父菜单
												if (parent_name == null){parent_name = "";}
												var url = element.url;// URL
												if (url == null) {url = "";}
												var describe_msg = element.describe_msg;// 用途描述
												if (describe_msg == null) {describe_msg = "";}
												
												
													
												
												
												var ra_str = element.radion;
												var ra_name = "";

												if (ra_str == "0") {
													ra_name = "启用";
													ra_str = "1";
												} else {
													ra_name = "禁用";
													ra_str = "0";
												}
												$("#mytable")
														.append(
																"<tr><td>"
																		+ ind
																		+ "</td><td>"
																		+ element.menu_name
																		+ "</td><td>"
																		+ parent_name
																		+ "</td><td>"
																		+ url
																		+ "</td></td><td>"
																		+ create_date
																		+ "</td><td>"
																		+ update_date
																		+ "</td><td>"
																		+ describe_msg
																		+ "</td><td><a href=\"javascript:void(0)\"  onclick=\"radion('"
																		+ element.id
																		+ "','"
																		+ ra_str
																		+ "')\">"
																		+ ra_name
																		+ "</a> | <a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#moodel\" onclick=\"kan('"
																		+ element.id
																		+ "')\">查看</a> | <a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#ee\" onclick=\"edit('"
																		+ element.id
																		+ "')\">编辑</a> | <a href=\"javascript:void(0)\" onclick=\"del('"
																		+ element.id
																		+ "')\">删除</a></td></tr>");
												//下拉列表选项 
												if (parent_name == ""){
													$(".select")
													.append(
															"<option value='"+element.id+"'>"
																	+ element.menu_name
																	+ "</option>");	
												}

											});
							$("#mytable")
									.append(
											"<tr><td colspan=\"8\" id=\"pager\"></td></tr>");
							//创建分页
							var target = $('#pager');
							pageClick(pageSource.pageNo, pageSource.total,
									target, 20);
						}
					},
					errorFn : function() {
						layer.alert('列表查询出错!');
					}
				});
	}
</script>


</head>

<body>

	<section id="page-content">

		<div id="Inquire-two">
			<h3 class="title">菜单管理</h3>
			<form id="searchFrom">
				<p>
					<label for="menu_name">菜单名称：</label><input id="menu_name"
						name="menu_name" usemap="{'logic':'and','compare':'like'}"
						placeholder="请输入内容" />
					<!-- <label for="begin">创建时间：</label><input id="begin" type="text"/><span class="line" ></span><input id="end" type="text"/> -->
					<!-- <label for="state">状态：</label> -->
					<button type="button" class="btn btn-zdy search">查询</button>
					<button type="reset" class="btn btn-zdy">重置</button>
					<button class="btn btn-zdy" type="button" data-toggle="modal"
						data-target="#createchild">添加</button>
			</form>

			<div>
				<table class="table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>序号</th>
							<th>菜单名称</th>
							<th>父菜单</th>
							<th>URL</th>
							<th>创建时间</th>
							<th>修改时间</th>
							<th>用途描述</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="mytable">

					</tbody>
				</table>
			</div>
		</div>

	</section>
	<!-- 添加表单开始 -->
	<div class="modal fade" id="createchild" tabindex="-1" role="dialog"
		aria-labelledby="ModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="ModalLabel">菜单名称</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="formson"
						method="post">
						<div class="form-group">
							<label for="cname" class="col-sm-3 control-label"><span
								class="text-danger">*</span>菜单名称:</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="menu_name"
									usemap="{byteRangeLength:[1,30,3],required:true,string:true}">
							</div>
						</div>
						<div class="form-group">
							<label for="mname1" class="col-sm-3 control-label"><span
								class="text-danger">*</span>所属父菜单</label>
							<div class="col-sm-6">
								<select name="parent_id" id="mname2"
									style="height: 33px; width: 100%; border-color: #e3e6f3;" class="select">
									<option value="0">请选择...</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="address" class="col-sm-3 control-label"><span
								class="text-danger">*</span>URL</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="url"
									usemap="{byteRangeLength:[1,30,3],required:true}">
							</div>
						</div>

						<div class="form-group">
							<label for="address" class="col-sm-3 control-label"><span
								class="text-danger">*</span>用途描述</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="describe_msg">
							</div>
						</div>

						<div class="form-group">
							<label for="address" class="col-sm-3 control-label"><span
								class="text-danger">*</span>是否有效</label>
							<div class="col-sm-6">
								<input name="radion" type="radio" value="0" checked="checked" />启用
								<input name="radion" type="radio" value="1" />禁用
							</div>
						</div>
						<div class="modal-footer ">
							<button type="button" class="btn btn-zdy" id="submit2">确定</button>
							<button type="button" class="btn btn-zdy" data-dismiss="modal"
								id="mode2222">取消</button>
						</div>

					</form>
				</div>




			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>

	<!-- 查看表单开始 -->
	<div class="modal fade" id="moodel" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">菜单信息查看</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="showUI" method="post">
						<div class="form-group">

							<label class="col-sm-5 control-label">菜单名称:</label>
							<div class="col-sm-3">
								<input id="menu_name" name="menu_name" type="text" class="form-control"
									><br>
							</div>

							<label class="col-sm-5 control-label">菜单地址:</label>
							<div class="col-sm-3">
								<input id="url" name="url" type="text" class="form-control"
									><br>
							</div>

							<label class="col-sm-5 control-label">创建时间:</label>
							<div class="col-sm-3">
								<input id="create_date" name="create_date" type="text" class="form-control"
									><br>
							</div>

							<label class="col-sm-5 control-label">修改时间:</label>
							<div class="col-sm-3">
								<input id="update_date" name="update_date" type="text" class="form-control"
									><br>
							</div>

							<label class="col-sm-5 control-label">用途描述：</label>
							<div class="col-sm-3">
								<input id="describe_msg" name="describe_msg" type="text" class="form-control"
									><br>
							</div>
						</div>
						<div class="modal-footer ">
							<button type="button" class="btn btn-zdy" data-dismiss="modal">返回</button>

						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- 查看表单结束 -->

	<!-- 编辑表单开始 -->
	<div class="modal fade" id="ee" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">菜单编辑</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="editUI" method="post">
						<div class="form-group">
							<label for="cname" class="col-sm-3 control-label"><span
								class="text-danger">*</span>菜单名称:</label>
							<div class="col-sm-6">
								<input type="hidden" name="id"/>
								<input type="text" class="form-control" name="menu_name"
									usemap="{byteRangeLength:[1,30,3],required:true}">
							</div>
						</div>
						<div class="form-group">
							<label for="mname1" class="col-sm-3 control-label"><span
								class="text-danger">*</span>所属父菜单</label>
							<div class="col-sm-6">
								<select name="parent_id" id="mname2"
									style="height: 33px; width: 100%; border-color: #e3e6f3;"  class="select">
									<option value="0">请选择...</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="address" class="col-sm-3 control-label"><span
								class="text-danger">*</span>URL</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="url"
									usemap="{byteRangeLength:[1,30,3],required:true}">
							</div>
						</div>

						<div class="form-group">
							<label for="address" class="col-sm-3 control-label"><span
								class="text-danger">*</span>用途描述</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="describe_msg">
							</div>
						</div>

						<div class="form-group">
							<label for="address" class="col-sm-3 control-label"><span
								class="text-danger">*</span>是否有效</label>
							<div class="col-sm-6">
								<input name="radion" type="radio" value="0" checked="checked" />启用
								<input name="radion" type="radio" value="1" />禁用
							</div>
						</div>
						<div class="modal-footer ">
							<button type="button" class="btn btn-zdy" id="sub">确定</button>
							<button type="button" class="btn btn-zdy" data-dismiss="modal"
								id="qx1">取消</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>

</html>