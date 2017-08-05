<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文档类别管理</title>
<script type="text/javascript" src="/mis-web/source/mvc/lib/cui.js"></script>
<script type="text/javascript"
	src="/mis-web/source/mvc/config/config.js"></script>
<script type="text/javascript" src="/mis-web/source/mvc/page/page.js"></script>
<link href="/mis-web/source/mvc/page/page.css" rel="stylesheet" />

</head>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<body>
	<!-- 功能部分开始 -->
		<section id="page-content">
		<div id="Inquire-two">
			<!-- 查询模块 -->
			<form id="searchFrom">
				<div class="input-group">
					<label for="menu_name">类别名称：</label>
					<input id="targetName" name="targetName" usemap="{'logic':'and','compare':'like'}" placeholder="请输入类别名称" /> 
					<button type="button" class="btn btn-zdy search" id="search">查询</button>
					<button type="reset" class="btn btn-zdy">重置</button>
				</div>
			</form>
			<div>
				<!-- 其他按钮 -->
				<p class="send">
					<button class="btn btn-zdy" data-toggle="modal" data-target="#addcontent" onclick="addTarget();">添加文档类别</button>
					<button class="btn btn-zdy" data-toggle="modal" data-target="#addcontent_2" onclick="addcontent_2();">导入文档类别</button>
					<button class="btn btn-zdy" onclick="downloadfile();">导入模板下载</button>
				</p>

				<!-- 文档类别列表 -->	
				<table class="table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>序号</th>
							<th>类别名称</th>
							<th>所属父类</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="mytable">

					</tbody>
				</table>
			</div>
		</div>
		</section>


	<!-- 批量新增 -->
	<div class="modal fade" id="addcontent_2" tabindex="-1" role="dialog"
		aria-labelledby="ModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="ModalLabel">导入文档类别模板</h4>
				</div>

				<form id="add_formId_2" enctype="multipart/form-data"
					class="form-horizontal">
					<div class="modal-body">
						<div class="form-group">
							<label for="contentname" class="col-sm-3 control-label">导入模板：</label>
							<div class="col-sm-4">
								<input type="file" name="add_detail" id="add_detail">
							</div>
						</div>
					</div>
					<div class="modal-footer ">
						<button type="button" class="btn btn-zdy" id="add_submit_2">确定</button>
						<button type="button" class="btn btn-zdy" data-dismiss="modal" id="add_cancel_2">取消</button>
					</div>
				</form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<!-- /.modal fade -->
	</div>

	<!-- 新增 -->
		<div class="modal fade" id="addcontent" tabindex="-1" role="dialog"
			aria-labelledby="ModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="ModalLabel">添加文档类别</h4>
					</div>

					<form id="add_formId" enctype="multipart/form-data" class="form-horizontal">
						<div class="modal-body">
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">一级类别：</label>
								<div class="col-sm-4">
									<select id="add_type1" name="add_type1" onchange="add_clickOpt('1');"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">二级类别：</label>
								<div class="col-sm-4">
									<select id="add_type2" name="add_type2" onchange="add_clickOpt('2');"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">三级类别：</label>
								<div class="col-sm-4">
									<select id="add_type3" name="add_type3" onchange="add_clickOpt('3');"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="mname1" class="col-sm-3 control-label">类别名称：</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="add_type" name="add_type" />
								</div>
							</div>
							<input type="hidden" id="add_parentId" name="add_parentId" value="0"/>
							<input type="hidden" id="add_level" name="add_level" value="1"/>
						</div>
						<div class="modal-footer ">
							<button type="button" class="btn btn-zdy" id="add_submit">确定</button>
							<button type="button" class="btn btn-zdy" data-dismiss="modal" id="add_cancel">取消</button>
						</div>
					</form>
				</div> <!-- /.modal-content -->
			</div> <!-- /.modal -->
		</div> <!-- /.modal fade -->
	</div>


	<!-- 修改 -->
	<div class="modal fade" id="editcontent" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">修改文档类别</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="edit_formId" method="post">
						<div class="modal-body">
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">一级类别：</label>
								<div class="col-sm-4">
									<select id="edit_type1" name="edit_type1" onchange="edit_clickOpt('1');"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">二级类别：</label>
								<div class="col-sm-4">
									<select id="edit_type2" name="edit_type2" onchange="edit_clickOpt('2');"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">三级类别：</label>
								<div class="col-sm-4">
									<select id="edit_type3" name="edit_type3" onchange="edit_clickOpt('3');"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="mname1" class="col-sm-3 control-label">类别名称：</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="edit_type" name="edit_type" />
								</div>
							</div>
							<input type="hidden" id="edit_id" name="edit_id" value=""/>
							<input type="hidden" id="edit_parentId" name="edit_parentId" value="0"/>
							<input type="hidden" id="edit_level" name="edit_level" value="1"/>
						</div>
						<div class="modal-footer ">
							<button type="button" class="btn btn-zdy" id="edit_submit">确定</button>
							<button type="button" class="btn btn-zdy" data-dismiss="modal" id="edit_cancel">取消</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	<!-- 功能部分结束 -->
</body>
<script type="text/javascript">
//查询按钮绑定点击事件
CUI.use([ 'ajaxform', 'utils', 'layer' ], function($ajax, $utils, $layer) {
	return {
		initialize : function() {//页面加载后执行
			pageSearch(1);// 查询列表第一页数据
			$("#search").on("click", function() {
				pageSearch(1);
			});
			// 批量新增
			new CuiAjaxForm($('#add_formId_2'), {
				submitSelector : $('#add_submit_2'),
				action : '../updownload/uploadTarget',
				beforeEvent : function(formData, jqForm, options) {
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'jdbcTemplateName', 'mysqlTemplate');
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'pFile', 'file');
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'pKey', 'insertFileTarget');
					return true;
				},
				callbackEvent : function(result) {
					if (result.status == 'success') {
						alert('导入成功!');
						$("#add_cancel_2").click();// 关闭按钮
						pageSearch(1);
					}
				}
			});
			// 新增提交
			new CuiAjaxForm($('#add_formId'), {
				submitSelector : $('#add_submit'),
				action : '../target/formProcessing',
				beforeEvent : function(formData, jqForm, options) {
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'jdbcTemplateName', 'mysqlTemplate');
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'pFile', 'file');
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'pKey', 'insertFileTarget2');
					return true;
				},
				callbackEvent : function(result) {
					if (result.status == 'success') {
						alert('保存成功!');
						$("#add_cancel").click();// 关闭按钮
						pageSearch(1);
					}
				}
			});
			// 修改提交
			new CuiAjaxForm($('#edit_formId'), {
				submitSelector : $('#edit_submit'),
				action : '../target/formProcessing',
				beforeEvent : function(formData, jqForm, options) {
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'jdbcTemplateName', 'mysqlTemplate');
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'pFile', 'file');
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'pKey', 'updateFileTarget');
					return true;
				},
				callbackEvent : function(result) {
					if (result.status == 'success') {
						alert('修改成功');
						$("#edit_cancel").click();// 关闭按钮
						pageSearch(1);
					}
				}
			});
		}
	}
});

// 分页查询
function pageSearch(pageNo) {
	var pageSize = 10;
	$.asyncRequest({
		url : '../member/pageSearch',
		data : $.extend({}, {
			"jdbcTemplateName" : "mysqlTemplate",
			"pFile" : "file",
			"pKey" : "selectFileTargetList_new",
			"type" : "mysql",
			"pageNo" : pageNo,
			"pageSize" : pageSize,
			"order" : ""
		}, $('#searchFrom').buildQueryInfo(), true),
		event : function(result) {
			if (result.status === 'success') {
				//后台返回的结果集，格式:
				var pageSource = result.listInfo;
				//下面写你的任何业务
				//alert(JSON.stringify(pageSource));
				$("#mytable").html("");// 清空
				$(pageSource.list).each(
					function(index, element) {
						var ind = parseInt(index) + 1;
						var parentname = element.parentname;
						if (parentname == null)
							parentname = "";
						$("#mytable").append(
						"<tr><td>"
						+ ind
						+ "</td><td>"
						+ element.targetname
						+ "</td><td>"
						+ parentname
						+ "</td><td>"
						+ "<a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#editcontent\" onclick=\"editcontent("
						+ "'"
						+ element.id
						+ "'"
						+ ")\">"
						+ "修改</a> | "
						+ "<a href=\"javascript:void(0)\" onclick=\"deletefile("
						+ "'"
						+ element.id
						+ "'"
						+ ")\">"
						+ "删除</a>"
						+ "</td></tr>");
				});
				$("#mytable").append("<tr><td colspan=\"8\" id=\"pager\"></td></tr>");
				//创建分页
				var target = $('#pager');
				pageClick(pageSource.pageNo, pageSource.total,target, pageSize);
			}
		},
	});
}

var selectSource;
// 查询并设置指标类别选择框
function listTarget() {
	$.asyncRequest({
		url : '../member/baseQuery',
		data : $.extend({}, {
			"jdbcTemplateName" : "mysqlTemplate",
			"pFile" : "file",
			"pKey" : "selectFileTargetList_select",
		}, $('#searchFrom').buildQueryInfo(), true),
		event : function(result) {
			if (result.status === 'success') {
				//后台返回的结果集，格式:
				var pageSource = result.listInfo;
				selectSource = pageSource;
				//下面写你的任何业务
				//alert(JSON.stringify(pageSource));
				
				var type1Html = "<option value='0'>请选择</option>";
				var type2Html = "<option value='0'>请选择</option>";
				var type3Html = "<option value='0'>请选择</option>";
				var type4Html = "<option value='0'>请选择</option>";
				for (var i = 0; i < pageSource.length; i++) {
					if(pageSource[i].level == '1') {
						type1Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
					} else if(pageSource[i].level == '2') {
						type2Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
					} else if(pageSource[i].level == '3') {
						type3Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
					} else if(pageSource[i].level == '4') {
						type4Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
					}
				}
				$("#add_type1").html(type1Html);
				$("#add_type2").html(type2Html);
				$("#add_type3").html(type3Html);
				//$("#add_type4").html(type4Html);
				$("#edit_type1").html(type1Html);
				$("#edit_type2").html(type2Html);
				$("#edit_type3").html(type3Html);
				$("#edit_type4").html(type4Html);
			}
		},
	});
}
// "删除"-删除文档类别
function deletefile(id) {
	if (confirm("确认删除吗")) {
		$.ajax({
			url : "../member/formProcessing",
			data : {
				"jdbcTemplateName" : "mysqlTemplate",
				"pFile" : "file",
				"pKey" : "deleteFileTarget",
				"id" : id
			},
			dataType : "json",
			type : 'post',
			async : false,
			success : function(result) {
				if (result.status === 'success') {
					alert("删除成功!");
					pageSearch(1);
				}
			}
		})
	} else {
		return;
	}
}
// "新增"-新增文档类别
function addTarget() {
	listTarget();// 查询并设置文档类别父类列表
	$("#add_type").val("");// 文档类别父类
	$("#add_parentId").val(0);// 文档类别名称
	$("#add_level").val(1);// 文档类别等级
}

function addcontent_2() {
	$("#add_detail").val("");// 文档类别等级
}

// "修改"-修改文档类别
function editcontent(id) {
	listTarget();// 查询并设置文档类别父类列表
	$.ajax({
		url : '../member/baseQuery',
		type : "post",
		data : {
			"jdbcTemplateName" : "mysqlTemplate",
			"pFile" : "file",
			"pKey" : "selectFileTargetInfo",
			"type" : "mysql",
			"id" : id
		},
		dataType : 'json',
		async : true,
		success : function(result) {
			if (result.status === 'success') {
				var listInfo = result.listInfo;
				$("#edit_id").val(listInfo[0].id);// 赋值
				$("#edit_parentId").val(listInfo[0].parentid);// 赋值
				$("#edit_level").val(listInfo[0].targetlevel);// 赋值
				$("#edit_type").val(listInfo[0].targetname);// 赋值
				
				if(listInfo[0].targetlevel == 1) {
					
				} else if(listInfo[0].targetlevel == 2) {
					edit_clickOpt_select1(listInfo[0].parentid);
				} else if(listInfo[0].targetlevel == 3) {
					edit_clickOpt_select2(listInfo[0].parentid);
				} else if(listInfo[0].targetlevel == 4) {
					edit_clickOpt_select3(listInfo[0].parentid);
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("跳转修改页面失败！");
		}
	});
}

function downloadfile() {//
	window.location.href = "../updownload/download?storeName=FileTargetUploadModel.xlsx&downloadName=文档类别批量上传模板.xlsx";
}





//select 联动1
function add_clickOpt(level){ 
	var pageSource = selectSource;
	if(level == "1") {
		add_clickOpt_select1($("#add_type1").val());
	} else if(level == "2") {
		add_clickOpt_select2($("#add_type2").val());
	} else if(level == "3") {
		add_clickOpt_select3($("#add_type3").val())
	} else if(level == "4") {
		// 空
	}
}
// 选定一级指标
function add_clickOpt_select1(value) {
	var pageSource = selectSource;
	if(value != '0') {
		var type2Html = "<option value='0'>请选择</option>";
		var type3Html = "<option value='0'>请选择</option>";
		var typeContext2 = "";// 所有匹配的二级ID
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '2' && pageSource[i].parentid == value) {
				type2Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
				typeContext2 += pageSource[i].id+",";
			}
		}
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '3' && (typeContext2.indexOf(pageSource[i].parentid) >= 0)) {
				type3Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			}
		}
		$("#add_type2").html(type2Html);
		$("#add_type3").html(type3Html);
		
		$("#add_parentId").val($("#add_type1").val());
		$("#add_level").val(2);
	} else {
		var type1Html = "<option value='0'>请选择</option>";
		var type2Html = "<option value='0'>请选择</option>";
		var type3Html = "<option value='0'>请选择</option>";
		var type4Html = "<option value='0'>请选择</option>";
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '1') {
				type1Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			} else if(pageSource[i].level == '2') {
				type2Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			} else if(pageSource[i].level == '3') {
				type3Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			} else if(pageSource[i].level == '4') {
				type4Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			}
		}
		$("#add_type1").html(type1Html);
		$("#add_type2").html(type2Html);
		$("#add_type3").html(type3Html);
		
		$("#add_parentId").val(0);
    	$("#add_level").val(1);
	}
}
//选定二级指标
function add_clickOpt_select2(value) {
	var pageSource = selectSource;
	if(value != '0') {
		var pageSource = selectSource;
		var parentvalue1 = $("#add_type1").val();// 上一级的值
		if(parentvalue1 == '0') {// 上一级为空
			for (var i = 0; i < pageSource.length; i++) {
				if(pageSource[i].level == '2' && pageSource[i].id == value) {
					parentvalue1 = pageSource[i].parentid;
				}
			}
			$("#add_type1").val(parentvalue1);
			add_clickOpt_select1(parentvalue1);
			$("#add_type2").val(value);
		}
		
		var type3Html = "<option value='0'>请选择</option>";
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '3' && pageSource[i].parentid == value) {
				type3Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			}
		}
		$("#add_type3").html(type3Html);
		
		$("#add_parentId").val($("#add_type2").val());
    	$("#add_level").val(3);
	} else {
		add_clickOpt_select1($("#add_type1").val());
	}
}
//选定三级指标
function add_clickOpt_select3(value) {
	if(value != '0') {
		var pageSource = selectSource;
		var parentvalue1 = "";
		var parentvalue2 = $("#add_type2").val();// 上一级的值
		if(parentvalue2 == '0') {// 上一级为空
			for (var i = 0; i < pageSource.length; i++) {
				if(pageSource[i].level == '3' && pageSource[i].id == value) {
					parentvalue2 = pageSource[i].parentid;
				}
			}
			for (var i = 0; i < pageSource.length; i++) {
				if(pageSource[i].level == '2' && pageSource[i].id == parentvalue2) {
					parentvalue1 = pageSource[i].parentid;
				}
			}
			$("#add_type1").val(parentvalue1);
			add_clickOpt_select1(parentvalue1);
			$("#add_type2").val(parentvalue2);
			add_clickOpt_select2(parentvalue2);
			$("#add_type3").val(value);
			//add_clickOpt_select1(parentvalue3);
		}
		
		$("#add_parentId").val($("#add_type3").val());
    	$("#add_level").val(4);
	} else {
		$("#add_parentId").val($("#add_type2").val());
    	$("#add_level").val(3);
	}
}
    
//select 联动2
function edit_clickOpt(level){ 
	var pageSource = selectSource;
	if(level == "1") {
		edit_clickOpt_select1($("#edit_type1").val());
	} else if(level == "2") {
		edit_clickOpt_select2($("#edit_type2").val());
	} else if(level == "3") {
		edit_clickOpt_select3($("#edit_type3").val())
	} else if(level == "4") {
		// 空
	}
}
// 选定一级指标
function edit_clickOpt_select1(value) {
	var pageSource = selectSource;
	if(value != '0') {
		var type2Html = "<option value='0'>请选择</option>";
		var type3Html = "<option value='0'>请选择</option>";
		var typeContext2 = "";// 所有匹配的二级ID
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '2' && pageSource[i].parentid == value) {
				type2Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
				typeContext2 += pageSource[i].id+",";
			}
		}
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '3' && (typeContext2.indexOf(pageSource[i].parentid) >= 0)) {
				type3Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			}
		}
		$("#edit_type2").html(type2Html);
		$("#edit_type3").html(type3Html);
		
		$("#edit_type1").val(value);
		$("#edit_parentId").val($("#edit_type1").val());
		$("#edit_level").val(2);
	} else {
		var type1Html = "<option value='0'>请选择</option>";
		var type2Html = "<option value='0'>请选择</option>";
		var type3Html = "<option value='0'>请选择</option>";
		var type4Html = "<option value='0'>请选择</option>";
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '1') {
				type1Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			} else if(pageSource[i].level == '2') {
				type2Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			} else if(pageSource[i].level == '3') {
				type3Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			} else if(pageSource[i].level == '4') {
				type4Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			}
		}
		$("#edit_type1").html(type1Html);
		$("#edit_type2").html(type2Html);
		$("#edit_type3").html(type3Html);
		
		$("#edit_parentId").val(0);
    	$("#edit_level").val(1);
	}
}
//选定二级指标
function edit_clickOpt_select2(value) {
	var pageSource = selectSource;
	if(value != '0') {
		var pageSource = selectSource;
		var parentvalue1 = $("#edit_type1").val();// 上一级的值
		if(parentvalue1 == '0') {// 上一级为空
			for (var i = 0; i < pageSource.length; i++) {
				if(pageSource[i].level == '2' && pageSource[i].id == value) {
					parentvalue1 = pageSource[i].parentid;
				}
			}
			$("#edit_type1").val(parentvalue1);
			edit_clickOpt_select1(parentvalue1);
			$("#edit_type2").val(value);
		}
		
		var type3Html = "<option value='0'>请选择</option>";
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '3' && pageSource[i].parentid == value) {
				type3Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			}
		}
		$("#edit_type3").html(type3Html);
		
		$("#edit_parentId").val($("#edit_type2").val());
    	$("#edit_level").val(3);
	} else {
		edit_clickOpt_select1($("#edit_type1").val());
	}
}
//选定三级指标
function edit_clickOpt_select3(value) {
	if(value != '0') {
		var pageSource = selectSource;
		var parentvalue1 = "";
		var parentvalue2 = $("#edit_type2").val();// 上一级的值
		if(parentvalue2 == '0') {// 上一级为空
			for (var i = 0; i < pageSource.length; i++) {
				if(pageSource[i].level == '3' && pageSource[i].id == value) {
					parentvalue2 = pageSource[i].parentid;
				}
			}
			for (var i = 0; i < pageSource.length; i++) {
				if(pageSource[i].level == '2' && pageSource[i].id == parentvalue2) {
					parentvalue1 = pageSource[i].parentid;
				}
			}
			$("#edit_type1").val(parentvalue1);
			edit_clickOpt_select1(parentvalue1);
			$("#edit_type2").val(parentvalue2);
			edit_clickOpt_select2(parentvalue2);
			$("#edit_type3").val(value);
		}
		
		$("#edit_parentId").val($("#edit_type3").val());
    	$("#edit_level").val(4);
	} else {
		$("#edit_parentId").val($("#edit_type2").val());
    	$("#edit_level").val(3);
	}
}    
</script>
</html>