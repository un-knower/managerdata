<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>行业类别管理</title>
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
					<label for="menu_name">行业名称：</label>
					<input id="name" name="name" usemap="{'logic':'and','compare':'like'}" placeholder="请输入行业名称" /> 
					<button type="button" class="btn btn-zdy search" id="search">查询</button>
					<button type="reset" class="btn btn-zdy">重置</button>
				</div>
			</form>
			<div>
				<!-- 添加行业类别按钮 -->
				<p class="send">
					<button class="btn btn-zdy" data-toggle="modal" data-target="#addcontent" onclick="addcontent();">添加行业</button>
				</p>

				<!-- 行业列表 -->	
				<table class="table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>序号</th>
							<th>行业名称</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="mytable">

					</tbody>
				</table>
			</div>
		</div>
		</section>
		
		
		
		
		<!-- 新增 -->
		<div class="modal fade" id="addcontent" tabindex="-1" role="dialog"
			aria-labelledby="ModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="ModalLabel">添加行业</h4>
					</div>

					<form id="add_formId" enctype="multipart/form-data" class="form-horizontal">
						<div class="modal-body">
							<div class="form-group">
								<label for="mname1" class="col-sm-3 control-label">行业名称：</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="add_name" name="add_name" />
								</div>
							</div>
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
					<h4 class="modal-title" id="myModalLabel">类别修改</h4>
				</div>
				<div class="modal-body">
					<form id="edit_formId" class="form-horizontal" method="post">
						<input type="hidden" value="" id="edit_id" name="edit_id" />
						<div class="form-group">
							<label for="mname1" class="col-sm-3 control-label">行业名称：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="edit_name" name="edit_name" />
							</div>
						</div>
						<div class="modal-footer ">
							<button type="button" class="btn btn-zdy" id="edit_submit">确定</button>
							<button type="button" class="btn btn-zdy" id="edit_cancel" data-dismiss="modal">取消</button>
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
							options, 'pKey', 'insertFileIndustry');
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
							options, 'pKey', 'updateFileIndustry');
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
			"pKey" : "selectFileIndustryList",
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
						$("#mytable").append(
						"<tr><td>"
						+ ind
						+ "</td><td>"
						+ element.name
						+ "</td><td>"
						+ "<a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#editcontent\" onclick=\"editfile("
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
//"删除"-删除文档类别
function deletefile(id) {
	if (confirm("确认删除吗")) {
		$.ajax({
			url : "../member/formProcessing",
			data : {
				"jdbcTemplateName" : "mysqlTemplate",
				"pFile" : "file",
				"pKey" : "deleteFileIndustry",
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

// "新增"-新增行业
function addcontent() {
	$("#add_name").val("");// 文档类别名称
}
// "修改"-修改文档类别
function editfile(id) {
	$.ajax({
		url : '../member/baseQuery',
		type : "post",
		data : {
			"jdbcTemplateName" : "mysqlTemplate",
			"pFile" : "file",
			"pKey" : "selectFileIndustryInfo",
			"type" : "mysql",
			"id" : id
		},
		dataType : 'json',
		async : true,
		success : function(result) {
			if (result.status === 'success') {
				var listInfo = result.listInfo;
				$("#edit_id").val(listInfo[0].id);// 赋值
				$("#edit_name").val(listInfo[0].name)
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("跳转修改页面失败！");
		}
	});
}
</script>
</html>