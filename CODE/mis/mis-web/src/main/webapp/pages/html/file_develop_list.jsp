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
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String time = sdf.format(new Date());
%>
<body>
	<!-- 功能部分开始 -->
	<section id="page-content">
	<div id="Inquire-two">
		<!-- 查询模块 -->
		<form id="searchFrom">
			<div class="input-group">
				<label for="menu_name">文章标题：</label> <input id="filetitle"
					name="filetitle" usemap="{'logic':'and','compare':'like'}"
					placeholder="请输入文章标题" />
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
</body>
<script type="text/javascript">
//查询按钮绑定点击事件
CUI.use([ 'ajaxform', 'utils', 'layer' ], function($ajax, $utils, $layer) {
	return {
		initialize : function() {//页面加载后执行
			var time = '<%=time%>';
				pageSearch(1);// 查询列表第一页数据
				selectTree();
				$("#search").on("click", function() {
					pageSearch(1);
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
				"pKey" : "selectFileContentList",
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
					$(pageSource.list)
							.each(
							function(index, element) {
								var ind = parseInt(index) + 1;
								$("#mytable")
										.append(
									"<tr><td>"
									+ ind
									+ "</td><td>"
									+ element.filetitle
									+ "</td><td>"
									+ element.fileauthor
									+ "</td><td>"
									+ element.filedeploytime
									+ "</td><td>"
									+ element.filesource
									+ "</td><td>"
									+ element.filecollecttime
									+ "</td><td>"
									+ "<a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#ee\" onclick=\"editType("
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

	function deleteContent(id) {
		if (confirm("确认删除吗")) {
			$.ajax({
				url : "../member/formProcessing",
				data : {
					"jdbcTemplateName" : "mysqlTemplate",
					"pFile" : "file",
					"pKey" : "deleteFileContent",
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