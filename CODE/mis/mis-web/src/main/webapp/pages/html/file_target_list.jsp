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
				<!-- 添加文档类别按钮 -->
				<p class="send">
					<button class="btn btn-zdy" data-toggle="modal" data-target="#addcontent" onclick="addTarget();">添加文档类别</button>
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
		
		
		
		
		<!-- 新增 -->
		<div class="modal fade" id="addcontent" tabindex="-1" role="dialog"
			aria-labelledby="ModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="ModalLabel">添加文档类别</h4>
					</div>

					<form id="formId" enctype="multipart/form-data" class="form-horizontal">
						<div class="modal-body">
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">所属父类：</label>
								<div class="col-sm-4">
									<select id="targetList" name="targetList" onchange="selectChange_add(this.value)"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
									<input type="hidden" id="targetLevel" name="targetLevel" value='1'/>
								</div>
							</div>
							<div class="form-group">
								<label for="mname1" class="col-sm-3 control-label">类别名称：</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="target" name="target" />
								</div>
							</div>
						</div>
						<div class="modal-footer ">
							<button type="button" class="btn btn-zdy" id="submit">确定</button>
							<button type="button" class="btn btn-zdy" data-dismiss="modal" id="miss">取消</button>
						</div>
					</form>
				</div> <!-- /.modal-content -->
			</div> <!-- /.modal -->
		</div> <!-- /.modal fade -->
	</div>


	<!-- 修改 -->
	<div class="modal fade" id="ee" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">类别修改</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="editUI" method="post">

						<input type="hidden" value="" id="ee_id" name="ee_id" />
						<div class="form-group">
							<label for="contentname" class="col-sm-3 control-label">所属父类：</label>
							<div class="col-sm-4">
								<select id="ee_targetList" name="ee_targetList" onchange="selectChange_edit(this.value)"
									style="height: 33px; width: 100%; border-color: #e3e6f3;">
									<option value="0">请选择</option>
								</select>
								<input type="hidden" id="ee_targetLevel" name="ee_targetLevel" value='1'/>
							</div>
						</div>
						<div class="form-group">
							<label for="mname1" class="col-sm-3 control-label">类别名称：</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="ee_target" name="ee_target" />
							</div>
						</div>
						<div class="modal-footer ">
							<button type="button" class="btn btn-zdy" id="sub">确定</button>
							<button type="button" class="btn btn-zdy" id="qx1" data-dismiss="modal">取消</button>
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
			new CuiAjaxForm($('#formId'), {
				submitSelector : $('#submit'),
				action : '../target/formProcessing',
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
						alert('保存成功!');
						$("#miss").click();// 关闭按钮
						pageSearch(1);
					}
				}
			});
			// 修改提交
			new CuiAjaxForm($('#editUI'), {
				submitSelector : $('#sub'),
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
						$("#qx1").click();// 关闭按钮
						pageSearch(1);
					}
				}
			});
		}
	}
});

// 分页查询
function pageSearch(pageNo) {
	var pageSize = 6;
	$.asyncRequest({
		url : '../member/pageSearch',
		data : $.extend({}, {
			"jdbcTemplateName" : "mysqlTemplate",
			"pFile" : "file",
			"pKey" : "selectFileTargetList",
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
						+ "<a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#ee\" onclick=\"editTarget("
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
// 查询并设置文档类别父类列表
function listParentTarget() {
	$.asyncRequest({
		url : '../member/baseQuery',
		data : $.extend({}, {
			"jdbcTemplateName" : "mysqlTemplate",
			"pFile" : "file",
			"pKey" : "filelistParentTarget",
		}, $('#searchFrom').buildQueryInfo(), true),
		event : function(result) {
			if (result.status === 'success') {
				//后台返回的结果集，格式:
				var pageSource = result.listInfo;
				//下面写你的任何业务
				//alert(JSON.stringify(pageSource));
				$("#targetList").html("");// 清空
				$("#ee_targetList").html("");// 清空
				var targetHtml = "";
				targetHtml += "<option value='0'>请选择</option>";
				for (var i = 0; i < pageSource.length; i++) {
					targetHtml += "<option value='"+pageSource[i].id+"'>" + pageSource[i].targetname + "</option>"
				}
				$("#targetList").html(targetHtml);
				$("#ee_targetList").html(targetHtml);
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
	listParentTarget();// 查询并设置文档类别父类列表
	$("#targetList").val(0);// 文档类别父类
	$("#target").val("");// 文档类别名称
	$("#targetLevel").val(1);// 文档类别等级
}
// "修改"-修改文档类别
function editTarget(id) {
	listParentTarget();// 查询并设置文档类别父类列表
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
				$("#ee_id").val(listInfo[0].id);// 赋值
				$("#ee_targetList").val(listInfo[0].parentid);// 赋值
				$("#ee_target").val(listInfo[0].targetname);// 赋值
				$("#ee_targetLevel").val(listInfo[0].targetlevel)
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("跳转修改页面失败！");
		}
	});
}
// 新增中选择文档类别触发文档级别的调整
function selectChange_add(value) {
	if(value == null || value == '0') {
		$("#targetLevel").val(1);
	} else {
		$("#targetLevel").val(2);
	}
}
// 修改中选择文档类别触发文档级别的调整
function selectChange_edit(value) {
	if(value == null || value == '0') {
		$("#ee_targetLevel").val(1);
	} else {
		$("#ee_targetLevel").val(2);
	}
}
</script>
</html>