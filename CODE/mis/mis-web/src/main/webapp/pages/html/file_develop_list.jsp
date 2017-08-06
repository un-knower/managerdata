<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>人工研制</title>
<script type="text/javascript" src="/mis-web/source/mvc/lib/cui.js"></script>
<script type="text/javascript"
	src="/mis-web/source/mvc/config/config.js"></script>
<script type="text/javascript" src="/mis-web/source/mvc/page/page.js"></script>
<link href="/mis-web/source/mvc/page/page.css" rel="stylesheet" />

</head>
<body>
	<!-- 功能部分开始 -->
	<section id="page-content">
	<div id="Inquire-two">
		<!-- 查询模块 -->
		<form id="searchFrom">
			<div class="input-group">
				<label for="menu_name">内容标题：</label> <input id="search_title"
					name="search_title" placeholder="请输入内容标题" />
				<!-- <label for="menu_name">内容作者：</label> <input id="search_author"
					name="search_author" placeholder="请输入内容作者" />
				<label for="menu_name">内容来源：</label> <input id="search_source"
				name="search_source" placeholder="请输入内容来源" /> -->
				<button type="button" class="btn btn-zdy search" id="search">查询</button>
				<button type="reset" class="btn btn-zdy">重置</button>
			</div>
		</form>
		<div>
			<!-- 添加人工研制按钮 -->
			<p class="send">
				<button class="btn btn-zdy" data-toggle="modal"
					data-target="#addcontent" onclick="addType();">添加人工研制</button>
				<button class="btn btn-zdy" data-toggle="modal"
					data-target="#addcontent_2" onclick="addcontent_2();">导入人工研制</button>
				<button class="btn btn-zdy" onclick="downloadfile();">导入模板下载</button>
			</p>

			<!-- 人工研制列表 -->
			<table class="table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>序号</th>
						<th>内容标题</th>
						<th>内容作者</th>
						<th>发布时间</th>
						<th>来源</th>
						<th>采集时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="mytable">

				</tbody>
			</table>
		</div>
	</div>
	</section>
	
	
<!-- 修改 -->
		<div class="modal fade" id="editcontent" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="ModalLabel">修改新闻</h4>
					</div>

					<form id="edit_formId" enctype="multipart/form-data" class="form-horizontal">
						<div class="modal-body">
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">内容标题：</label>
								<div class="col-sm-4">
									<input id="edit_title" name="edit_title" type="text" "/><!-- onClick="WdatePicker() -->
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">内容作者：</label>
								<div class="col-sm-4">
									<input id="edit_author" name="edit_author" type="text" "/><!-- onClick="WdatePicker() -->
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">发布时间：</label>
								<div class="col-sm-4">
									<input id="edit_deploytime" name="edit_deploytime" type="text" "/><!-- onClick="WdatePicker() -->
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">内容：</label>
								<div class="col-sm-4">
									<textarea rows="8" cols="50" id="edit_content" name="edit_content"></textarea>
								</div>
							</div>
							<input type="hidden" id="edit_id" name="edit_id" value="0"/>
						</div>
						<div class="modal-footer ">
							<button type="button" class="btn btn-zdy" id="edit_submit" >确定</button>
							<button type="button" class="btn btn-zdy" data-dismiss="modal" id="edit_cancel">取消</button>
						</div>
					</form>
				</div> <!-- /.modal-content -->
			</div> <!-- /.modal -->
		</div><!-- /.modal fade -->
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
			new CuiAjaxForm($('#edit_formId'), {
				submitSelector : $('#edit_submit'),
				action : '../escommon/updateById',
				beforeEvent : function(formData, jqForm, options) {
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'index', 'managerdataindex');
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'type', 'news');
					return true;
				},
				callbackEvent : function(result) {
					if (result.status == 'success') {
						alert('修改成功!');
						$("#edit_cancel").click();// 关闭按钮
						pageSearch(1);
					}
				}
			});
		}
	}
});
	
	function pageSearch(pageNo) {
		var pageSize = 10;
		$.asyncRequest({
			url : '../escommon/searchall',
			data : {
				"index" : "managerdataindex",
				"type" : "news",
				"pageNo" : pageNo,
				"pageSize" : pageSize,
				"title" : $("#search_title").val(),
				/* "source" : $("#search_source").val(),
				"author" : $("#search_author").val(), */
				"order" : ""
			},
			event : function(result) {
				if (result.status === 'success') {
					//后台返回的结果集，格式:
					var pageSource = result.listInfo;
					//下面写你的任何业务
					//alert(JSON.stringify(pageSource));
					$("#mytable").html("");// 清空
					$(pageSource.list)
							.each(
							function(index, element) {
								var ind = parseInt(index) + 1;
								
								var deployTime = element.deployTime;
								if(deployTime == null) {
									deployTime = "";
								}
								var collectTime = element.collectTime;
								if(collectTime == null) {
									collectTime = "";
								}
								
								$("#mytable")
										.append(
									"<tr><td>"
									+ ind
									+ "</td><td>"
									+ element.title
									+ "</td><td>"
									+ element.author
									+ "</td><td>"
									+ deployTime
									+ "</td><td>"
									+ element.source
									+ "</td><td>"
									+ collectTime
									+ "</td><td>"
									+ "<a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#editcontent\" onclick=\"editContent("
									+ "'"
									+ element.id
									+ "'"
									+ ")\">"
									+ "修改</a> | "
									+ "<a href=\"javascript:void(0)\" onclick=\"deleteContent("
									+ "'"
									+ element.id
									+ "'"
									+ ")\">"
									+ "删除</a>"
									+ "</td></tr>");
									});
							$("#mytable")
									.append(
											"<tr><td colspan=\"8\" id=\"pager\"></td></tr>");
							//创建分页
							var target = $('#pager');
							pageClick(pageSource.pageNo, pageSource.total, target, pageSize);
						}
					},
				});
	}
	
	// "修改"
	function editContent(id) {
		$.ajax({
			url : '../escommon/queryById',
			type : "post",
			data : {
				"index" : "managerdataindex",
				"type" : "news",
				"id" : id
			},
			dataType : 'json',
			async : true,
			success : function(result) {
				if (result.status === 'success') {
					var listInfo = result.listInfo;
					$("#edit_title").val(listInfo[0].title);
					$("#edit_author").val(listInfo[0].author);
					$("#edit_deploytime").val(listInfo[0].deployTime);
					$("#edit_content").val(listInfo[0].content);
					$("#edit_id").val(listInfo[0].id);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("跳转修改页面失败！");
			}
		});
	}
	
	// 删除
	function deleteContent(id) {
		if (confirm("确认删除吗")) {
			$.ajax({
				url : "../escommon/deleteById",
				data : {
					"index" : "managerdataindex",
					"type" : "news",
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

	function downloadfile() {// 模板下载
		window.location.href = "../updownload/download?storeName=FileDevelopUploadModel.xlsx&downloadName=人工研判批量上传模板.xlsx";
	}
	
	
	

</script>
</html>