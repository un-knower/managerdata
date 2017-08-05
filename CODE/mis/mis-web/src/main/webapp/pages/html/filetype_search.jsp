<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>要素类别</title>
<script type="text/javascript" src="/mis-web/source/mvc/lib/cui.js"></script>
<script type="text/javascript" src="/mis-web/source/mvc/config/config.js"></script>
<script type="text/javascript" src="/mis-web/source/mvc/page/page.js"></script>
<link href="/mis-web/source/mvc/page/page.css" rel="stylesheet" />

<style>
.mytable tr td {  
    text-overflow: ellipsis; /* for IE */  
    -moz-text-overflow: ellipsis; /* for Firefox,mozilla */  
    overflow: hidden;  
    white-space: nowrap;  
    border: 1px solid;  
    text-align: left  
}
</style>
</head>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
String time = sdf.format(new Date());
%>
<body>
	<!-- 功能部分开始 -->
	<section id="page-content">
	<div id="Inquire-two">
	
	<!-- 查询模块 -->
	<form id="searchFrom">
		<div class="input-group">
			<label>一级指标：</label>
			<select id="type1" onchange="clickOpt1();">
				<option value="0">请选择</option>
			</select>
			<label>二级指标：</label>
			<select id="type2" onchange="clickOpt2();">
				<option value="0">请选择</option>
			</select>
			<label>三级指标：</label>
			<select id="type3" onchange="clickOpt3();">
				<option value="0">请选择</option>
			</select>
			<label>四级指标：</label>
			<select id="type4" onchange="clickOpt4();">
				<option value="0">请选择</option>
			</select>
		</div>
		<div>
			<input type="hidden" id="id" name="fileTypeId" usemap="{'logic':'and','compare':'='}"/>
			<label for="menu_name">关键词：</label>
			<input id="text" name="text" usemap="{'logic':'and','compare':'like'}" placeholder="请输入关键词" /> 

			<button type="button" class="btn btn-zdy search" id="search">查询</button>
			<button type="reset" class="btn btn-zdy" onclick="resetType()">重置</button>
		</div>
	</form>
	
	
			<div>
				<!-- 要素类别列表 -->	
				<table class="table-striped table-bordered table-hover">
					<!-- <thead>
						<tr>
							<th>序号</th>
							<th>要素名称</th>
							<th>要素级别</th>
							<th>操作</th>
						</tr>
					</thead> -->
					<tbody id="mytable">

					</tbody>
				</table>
			</div>
		</div>
		</section>



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
						<div class="modal-body">
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">一级指标：</label>
								<div class="col-sm-4">
									<select id="type1_2" name="type1_2" onchange="clickOpt1_2();"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">二级指标：</label>
								<div class="col-sm-4">
									<select id="type2_2" name="type2_2" onchange="clickOpt2_2();"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">三级指标：</label>
								<div class="col-sm-4">
									<select id="type3_2" name="type3_2" onchange="clickOpt3_2();"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">四级指标：</label>
								<div class="col-sm-4">
									<select id="type4_2" name="type4_2" onchange="clickOpt4_2();"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							
							<input type="hidden" value="" id="2_id" name="2_id" />
							<input type="hidden" class="form-control" id="2_filetypeid" name="2_filetypeid" />
							<div class="form-group">
								<textarea rows="8" cols="70" id="2_text" name="2_text"></textarea>
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
			var time = '<%=time %>';
			pageSearch(1);// 查询列表第一页数据
			listSearch();
			$("#search").on("click", function() {
				pageSearch(1);
			});
			// 修改
			new CuiAjaxForm($('#editUI'), {
				submitSelector : $('#sub'),
				action : '../target/formProcessing',
				beforeEvent : function(formData, jqForm, options) {
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'jdbcTemplateName', 'mysqlTemplate');
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'pFile', 'file');
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'pKey', 'updateFileText');
					return true;
				},
				callbackEvent : function(result) {
					if (result.status == 'success') {
						alert('修改成功');
						$("#qx1").click();
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
			"pKey" : "selectFileText",
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
						
						var typename = "";
						var typename1 = element.typename1;
						if(typename1 != null){
							typename += typename1 + '&lt; ';
						} 
						var typename2 = element.typename2;
						if(typename2 != null){
							typename += typename2 + '&lt; ';
						}
						var typename3 = element.typename3;
						if(typename3 != null){
							typename += typename3 + '&lt; ';
						}
						var typename4 = element.typename4;
						if(typename4 != null){
							typename += typename4;
						}
						$("#mytable").append(
						"<tr><td style='text-align:left;' colspan='2'>"
						+ "<span style=\"font-family:'Arial-BoldMT', 'Arial Bold', 'Arial';font-weight:700;\">"
						+ ind + " " +typename
						+ "</span>"
						+ "</td>"
						+ "</tr><tr>"
						+ "<td style='text-align:left;'>"
						+ element.text
						+ "</td><td nowrap>"
						+ "<a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#ee\" onclick=\"editFileText("
						+ "'"
						+ element.id
						+ "'"
						+ ")\">"
						+ "修改</a> "
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
function listSearch() {
	$.asyncRequest({
		url : '../member/baseQuery',
		data : $.extend({}, {
			"jdbcTemplateName" : "mysqlTemplate",
			"pFile" : "file",
			"pKey" : "selectFileTypeList",
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
					if(pageSource[i].typelevel == '1') {
						type1Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].typename + "</option>";
					} else if(pageSource[i].typelevel == '2') {
						type2Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].typename + "</option>";
					} else if(pageSource[i].typelevel == '3') {
						type3Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].typename + "</option>";
					} else if(pageSource[i].typelevel == '4') {
						type4Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].typename + "</option>";
					}
				}
				$("#type1").html(type1Html);
				$("#type1_2").html(type1Html);
				$("#type2").html("<option value='0'>请选择</option>");
				$("#type3").html("<option value='0'>请选择</option>");
				$("#type4").html("<option value='0'>请选择</option>");
				$("#type2_2").html("<option value='0'>请选择</option>");
				$("#type3_2").html("<option value='0'>请选择</option>");
				$("#type4_2").html("<option value='0'>请选择</option>");
			}
		},
	});
}
function resetType() {
	$("#id").val('');
}
//select 联动1
function clickOpt1(){  
	var emptyHtml = "<option value='0'>请选择</option>";
    var value = $("#type1").val();
    if(value != '0') {
    	var pageSource = selectSource;
    	var typeHtml = "<option value='0'>请选择</option>";
    	for (var i = 0; i < pageSource.length; i++) {
    		if(pageSource[i].typelevel == '2' && pageSource[i].parentid == value) {
    			typeHtml += "<option value='"+pageSource[i].id+"'>" + pageSource[i].typename + "</option>";
    		}
    	}
    	$("#type2").html(typeHtml);
    	
    } else {
    	$("#type2").html(emptyHtml);
    	
    }
    $("#id").val(value);
    $("#typeLevel").val(1);
    $("#type3").html(emptyHtml);
	$("#type4").html(emptyHtml);
     
	
	
}
//select 联动2
function clickOpt2(){  
	var emptyHtml = "<option value='0'>请选择</option>";
    var value = $("#type2").val();
    if(value != '0') {
	    var pageSource = selectSource;
		var typeHtml = "<option value='0'>请选择</option>";
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].typelevel == '3' && pageSource[i].parentid == value) {
				typeHtml += "<option value='"+pageSource[i].id+"'>" + pageSource[i].typename + "</option>";
			}
		}
		$("#type3").html(typeHtml);
		$("#typeLevel").val(2);
		$("#id").val(value);
    } else {
    	$("#type3").html(emptyHtml);
    	$("#typeLevel").val(1);
    	$("#id").val($("#type1").val());
    }
    $("#type4").html(emptyHtml);
}
//select 联动3
function clickOpt3(){  
	var emptyHtml = "<option value='0'>请选择</option>";
    var value = $("#type3").val();
    if(value != '0') {
    	var pageSource = selectSource;
		var typeHtml = "<option value='0'>请选择</option>";
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].typelevel == '4' && pageSource[i].parentid == value) {
				typeHtml += "<option value='"+pageSource[i].id+"'>" + pageSource[i].typename + "</option>";
			}
		}
		$("#type4").html(typeHtml);
		$("#typeLevel").val(3);
		$("#id").val(value);
    } else {
    	$("#type4").html(emptyHtml);
    	$("#typeLevel").val(2);
    	$("#id").val($("#type2").val());
    }
    
}
//select 联动4
function clickOpt4(){  
	var emptyHtml = "<option value='0'>请选择</option>";
    var value = $("#type4").val();
    if(value != '0') {
    	$("#typeLevel").val(4);
    	$("#id").val(value);
    } else {
    	$("#typeLevel").val(3);
    	$("#id").val($("#type3").val());
    }
    
}

function deletefile(id) {
	if (confirm("确认删除吗")) {
		$.ajax({
			url : "../member/formProcessing",
			data : {
				"jdbcTemplateName" : "mysqlTemplate",
				"pFile" : "file",
				"pKey" : "deleteFileText",
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


//select 联动1
function clickOpt1_2(){ 
	var emptyHtml = "<option value='0'>请选择</option>";
 var value = $("#type1_2").val();
 if(value != '0') {
 	var pageSource = selectSource;
 	var typeHtml = "<option value='0'>请选择</option>";
 	for (var i = 0; i < pageSource.length; i++) {
 		if(pageSource[i].typelevel == '2' && pageSource[i].parentid == value) {
 			typeHtml += "<option value='"+pageSource[i].id+"'>" + pageSource[i].typename + "</option>";
 		}
 	}
 	$("#type2_2").html(typeHtml);
 	$("#type3_2").html(emptyHtml);
 	$("#type4_2").html(emptyHtml);
 } else {
 	$("#type2_2").html(emptyHtml);
 	$("#type3_2").html(emptyHtml);
 	$("#type4_2").html(emptyHtml);
 }
 $("#2_filetypeid").val(value);
}
//select 联动2
function clickOpt2_2(){  
	var emptyHtml = "<option value='0'>请选择</option>";
 var value = $("#type2_2").val();
 if(value != '0') {
	    var pageSource = selectSource;
		var typeHtml = "<option value='0'>请选择</option>";
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].typelevel == '3' && pageSource[i].parentid == value) {
				typeHtml += "<option value='"+pageSource[i].id+"'>" + pageSource[i].typename + "</option>";
			}
		}
		$("#type3_2").html(typeHtml);
		$("#type4_2").html(emptyHtml);
		$("#2_filetypeid").val(value);
 } else {
 	$("#type3_2").html(emptyHtml);
 	$("#type4_2").html(emptyHtml);
 	$("#2_filetypeid").val($("#type1_2").val());
 }
}
//select 联动3
function clickOpt3_2(){  
	var emptyHtml = "<option value='0'>请选择</option>";
 var value = $("#type3_2").val();
 if(value != '0') {
	 var pageSource = selectSource;
		var typeHtml = "<option value='0'>请选择</option>";
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].typelevel == '4' && pageSource[i].parentid == value) {
				typeHtml += "<option value='"+pageSource[i].id+"'>" + pageSource[i].typename + "</option>";
			}
		}
		$("#type4_2").html(typeHtml);
	$("#2_filetypeid").val(value);
 } else {
 	$("#2_filetypeid").val($("#type2_2").val());
 	$("#type4_2").html(emptyHtml);
 }
}
//select 联动3
function clickOpt4_2(){  
 var value = $("#type4_2").val();
 if(value != '0') {
	$("#2_filetypeid").val(value);
 } else {
 	$("#2_filetypeid").val($("#type3_2").val());
 }
}

// "修改"-修改要素类别
function editFileText(id) {
	listSearch();// 设定选择框内容
	$.ajax({
		url : '../member/baseQuery',
		type : "post",
		data : {
			"jdbcTemplateName" : "mysqlTemplate",
			"pFile" : "file",
			"pKey" : "selectFileTextInfo",
			"type" : "mysql",
			"id" : id
		},
		dataType : 'json',
		async : true,
		success : function(result) {
			if (result.status === 'success') {
				var listInfo = result.listInfo;
				var filetypeid = listInfo[0].filetypeid;
				var parentid = listInfo[0].parentid;
				var typelevel = listInfo[0].typelevel;
				$("#2_id").val(listInfo[0].id);// 赋值
				$("#2_text").val(listInfo[0].text);// 赋值
				//$("#2_filetypeid").val(filetypeid);// 赋值
				
				var pageSource = selectSource;
				if(typelevel == '1') {
					$("#type1_2").val(filetypeid);
					clickOpt1_2();
				} else if(typelevel == '2') {
					$("#type1_2").val(parentid);
					clickOpt1_2();
					$("#type2_2").val(filetypeid);
					clickOpt2_2();
				} else if(typelevel == '3') {
					var value1;
					for (var i = 0; i < pageSource.length; i++) {
			    		if(pageSource[i].typelevel == '2' && pageSource[i].id == parentid) {
			    			value1 = pageSource[i].parentid;
			    			break;
			    		}
			    	}
					$("#type1_2").val(value1);
					clickOpt1_2();
					$("#type2_2").val(parentid);
					clickOpt2_2();
					$("#type3_2").val(filetypeid);
					clickOpt3_2();
				} else if(typelevel == '4') {
					var value1;
					for (var i = 0; i < pageSource.length; i++) {
			    		if(pageSource[i].typelevel == '3' && pageSource[i].id == parentid) {
			    			value1 = pageSource[i].parentid;
			    			break;
			    		}
			    	}
					var value2;
					for (var i = 0; i < pageSource.length; i++) {
			    		if(pageSource[i].typelevel == '2' && pageSource[i].id == value1) {
			    			value2 = pageSource[i].parentid;
			    			break;
			    		}
			    	}
					$("#type1_2").val(value2);
					clickOpt1_2();
					$("#type2_2").val(value1);
					clickOpt2_2();
					$("#type3_2").val(parentid);
					clickOpt3_2();
					$("#type4_2").val(filetypeid);
					clickOpt4_2();
				}
				
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("跳转编辑页面失败");
		}
	});
}
</script>
</html>