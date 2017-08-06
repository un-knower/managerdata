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

			<button type="button" class="btn btn-zdy search" id="search">查询【尚未实现】</button>
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
	<div class="modal fade" id="editcontent" tabindex="-1" role="dialog"
		aria-labelledby="ModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="ModalLabel">修改语料</h4>
				</div>

				<form id="edit_formId" enctype="multipart/form-data"
					class="form-horizontal">
					<div class="modal-body">
						<div class="form-group">
							<label for="contentname" class="col-sm-3 control-label">一级类别：</label>
							<div class="col-sm-4">
								<select id="edit_type1" name="edit_type1"
									onchange="edit_clickOpt('1');"
									style="height: 33px; width: 100%; border-color: #e3e6f3;">
									<option value="0">请选择</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="contentname" class="col-sm-3 control-label">二级类别：</label>
							<div class="col-sm-4">
								<select id="edit_type2" name="edit_type2"
									onchange="edit_clickOpt('2');"
									style="height: 33px; width: 100%; border-color: #e3e6f3;">
									<option value="0">请选择</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="contentname" class="col-sm-3 control-label">三级类别：</label>
							<div class="col-sm-4">
								<select id="edit_type3" name="edit_type3"
									onchange="edit_clickOpt('3');"
									style="height: 33px; width: 100%; border-color: #e3e6f3;">
									<option value="0">请选择</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="contentname" class="col-sm-3 control-label">四级类别：</label>
							<div class="col-sm-4">
								<select id="edit_type4" name="edit_type4"
									onchange="edit_clickOpt('4');"
									style="height: 33px; width: 100%; border-color: #e3e6f3;">
									<option value="0">请选择</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="contentname" class="col-sm-3 control-label">要素内容：</label>
							<div class="col-sm-4">
								<textarea rows="8" cols="50" id="edit_content" name="edit_content"></textarea>
							</div>
						</div>
						<input type="hidden" id="edit_typeId" name="edit_typeId" value="0" />
						<input type="hidden" id="edit_id" name="edit_id" value="0" />
					</div>
					<div class="modal-footer ">
						<button type="button" class="btn btn-zdy" id="edit_submit">确定</button>
						<button type="button" class="btn btn-zdy" data-dismiss="modal"
							id="edit_cancel">取消</button>
					</div>
				</form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<!-- /.modal fade -->

	<!-- 功能部分结束 -->
</body>
<script type="text/javascript">
//查询按钮绑定点击事件
CUI.use([ 'ajaxform', 'utils', 'layer' ], function($ajax, $utils, $layer) {
	return {
		initialize : function() {//页面加载后执行
			pageSearch(1);// 查询列表第一页数据
			listSearch();
			$("#search").on("click", function() {
				pageSearch(1);
			});
			// 修改
			new CuiAjaxForm($('#edit_formId'), {
				submitSelector : $('#edit_submit'),
				action : '../escommon/updateContentById',
				beforeEvent : function(formData, jqForm, options) {
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'index', 'managerdataindex');
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'type', 'content');
					return true;
				},
				callbackEvent : function(result) {
					if (result.status == 'success') {
						alert('修改成功');
						$("#edit_cancel").click();
						pageSearch(1);
					}
				}
			});
		}
	}
});

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










//分页查询
function pageSearch(pageNo) {
	var pageSize = 10;
	$.asyncRequest({
		url : '../escommon/searchall',
		data : {
			"index" : "managerdataindex",
			"type" : "content",
			"pageNo" : pageNo,
			"pageSize" : pageSize,
			"order" : ""
		}, 
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
						var targetid = element.targetid;
						if(targetid == null) {
							targetid = "";
						}
						$("#mytable").append(
						"<tr><td style='text-align:left;' colspan='2'>"
						+ "<span style=\"font-family:'Arial-BoldMT', 'Arial Bold', 'Arial';font-weight:700;\">"
						+ ind + " " +targetid //+ " " +element.source
						+ "</span>"
						+ "</td>"
						+ "</tr><tr>"
						+ "<td style='text-align:left;'>"
						+ element.content
						+ "</td><td nowrap>"
						+ "<a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#editcontent\" onclick=\"editContent("
						+ "'"
						+ element.id
						+ "'"
						+ ")\">"
						+ "修改</a> "
						+ "<a href=\"javascript:void(0)\" onclick=\"deleteContent("
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
// 删除
function deleteContent(id) {
	if (confirm("确认删除吗")) {
		$.ajax({
			url : "../escommon/deleteById",
			data : {
				"index" : "managerdataindex",
				"type" : "content",
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

// "修改"-修改要素类别
function editContent(id) {
	listTarget();
	$.ajax({
		url : '../escommon/queryById',
		type : "post",
		data : {
			"index" : "managerdataindex",
			"type" : "content",
			"id" : id
		},
		dataType : 'json',
		async : true,
		success : function(result) {
			if (result.status === 'success') {
				var listInfo = result.listInfo;
				$("#edit_id").val(listInfo[0].id);// 赋值
				$("#edit_content").val(listInfo[0].content);// 赋值
				$("#edit_typeId").val(listInfo[0].targetid);// 赋值
				
				var targetid = listInfo[0].targetid;
				editSelectTarget(targetid);
				
				//var filetypeid = listInfo[0].filetypeid;
				//var parentid = listInfo[0].parentid;
				//var typelevel = listInfo[0].typelevel;
				
				}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("跳转编辑页面失败");
		}
	});
}

// 根据指标值设计edit中的指标选择列表
function editSelectTarget(targetid) {
	if(targetid == null || targetid == "") {
		$("#edit_type1").val("0");
		edit_clickOpt("1");
	} else {
		var pageSource = selectSource;
		var level;
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].id == targetid) {
				level = pageSource[i].level;
				break;
			}
		}

		if (level == '1') {
			$("#edit_type1").val(targetid);
		} else if (level == '2') {
			$("#edit_type2").val(targetid);
		} else if (level == '3') {
			$("#edit_type3").val(targetid);
		} else if (level == '4') {
			$("#edit_type4").val(targetid);
		}
		edit_clickOpt(level);
	}
	
}

	var selectSource;
	//查询并设置指标类别选择框
	function listTarget() {
		$
				.asyncRequest({
					url : '../member/baseQuery',
					data : $.extend({}, {
						"jdbcTemplateName" : "mysqlTemplate",
						"pFile" : "file",
						"pKey" : "selectFileTypeList_select",
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
								if (pageSource[i].level == '1') {
									type1Html += "<option value='"+pageSource[i].id+"'>"
											+ pageSource[i].name + "</option>";
								} else if (pageSource[i].level == '2') {
									type2Html += "<option value='"+pageSource[i].id+"'>"
											+ pageSource[i].name + "</option>";
								} else if (pageSource[i].level == '3') {
									type3Html += "<option value='"+pageSource[i].id+"'>"
											+ pageSource[i].name + "</option>";
								} else if (pageSource[i].level == '4') {
									type4Html += "<option value='"+pageSource[i].id+"'>"
											+ pageSource[i].name + "</option>";
								}
							}
							$("#add_type1").html(type1Html);
							$("#add_type2").html(type2Html);
							$("#add_type3").html(type3Html);
							$("#add_type4").html(type4Html);
							
							$("#type1").html(type1Html);
							$("#type2").html("<option value='0'>请选择</option>");
							$("#type3").html("<option value='0'>请选择</option>");
							$("#type4").html("<option value='0'>请选择</option>");
							
							$("#edit_type1").html(type1Html);
							$("#edit_type2").html(type2Html);
							$("#edit_type3").html(type3Html);
							$("#edit_type4").html(type4Html);
						}
					},
				});
	}

	//select 修改联动
	function edit_clickOpt(level) {
		var pageSource = selectSource;
		if (level == "1") {
			edit_clickOpt_select1($("#edit_type1").val());
		} else if (level == "2") {
			edit_clickOpt_select2($("#edit_type2").val());
		} else if (level == "3") {
			edit_clickOpt_select3($("#edit_type3").val())
		} else if (level == "4") {
			edit_clickOpt_select4($("#edit_type4").val())
		}
	}
	// 选定一级指标
	function edit_clickOpt_select1(value) {
		var pageSource = selectSource;
		if (value != '0') {
			var type2Html = "<option value='0'>请选择</option>";
			var type3Html = "<option value='0'>请选择</option>";
			var type4Html = "<option value='0'>请选择</option>";
			var typeContext2 = "";// 所有匹配的二级ID
			for (var i = 0; i < pageSource.length; i++) {
				if (pageSource[i].level == '2'
						&& pageSource[i].parentid == value) {
					type2Html += "<option value='"+pageSource[i].id+"'>"
							+ pageSource[i].name + "</option>";
					typeContext2 += pageSource[i].id + ",";
				}
			}
			var typeContext3 = "";// 所有匹配的三级ID
			for (var i = 0; i < pageSource.length; i++) {
				if (pageSource[i].level == '3'
						&& (typeContext2.indexOf(pageSource[i].parentid) >= 0)) {
					type3Html += "<option value='"+pageSource[i].id+"'>"
							+ pageSource[i].name + "</option>";
					typeContext3 += pageSource[i].id + ",";
				}
			}

			for (var i = 0; i < pageSource.length; i++) {
				if (pageSource[i].level == '4'
						&& (typeContext3.indexOf(pageSource[i].parentid) >= 0)) {
					type4Html += "<option value='"+pageSource[i].id+"'>"
							+ pageSource[i].name + "</option>";
				}
			}
			$("#edit_type2").html(type2Html);
			$("#edit_type3").html(type3Html);
			$("#edit_type4").html(type4Html);

			$("#edit_typeId").val(value);
		} else {
			var type1Html = "<option value='0'>请选择</option>";
			var type2Html = "<option value='0'>请选择</option>";
			var type3Html = "<option value='0'>请选择</option>";
			var type4Html = "<option value='0'>请选择</option>";
			for (var i = 0; i < pageSource.length; i++) {
				if (pageSource[i].level == '1') {
					type1Html += "<option value='"+pageSource[i].id+"'>"
							+ pageSource[i].name + "</option>";
				} else if (pageSource[i].level == '2') {
					type2Html += "<option value='"+pageSource[i].id+"'>"
							+ pageSource[i].name + "</option>";
				} else if (pageSource[i].level == '3') {
					type3Html += "<option value='"+pageSource[i].id+"'>"
							+ pageSource[i].name + "</option>";
				} else if (pageSource[i].level == '4') {
					type4Html += "<option value='"+pageSource[i].id+"'>"
							+ pageSource[i].name + "</option>";
				}
			}
			$("#edit_type1").html(type1Html);
			$("#edit_type2").html(type2Html);
			$("#edit_type3").html(type3Html);
			$("#edit_type4").html(type4Html);

			$("#edit_typeId").val(value);
		}
	}
	//选定二级指标
	function edit_clickOpt_select2(value) {
		var pageSource = selectSource;
		if (value != '0') {
			var pageSource = selectSource;
			var parentvalue1 = $("#edit_type1").val();// 上一级的值
			if (parentvalue1 == '0') {// 上一级为空
				for (var i = 0; i < pageSource.length; i++) {
					if (pageSource[i].level == '2' && pageSource[i].id == value) {
						parentvalue1 = pageSource[i].parentid;
					}
				}
				$("#edit_type1").val(parentvalue1);
				edit_clickOpt_select1(parentvalue1);
				$("#edit_type2").val(value);
			}

			var type3Html = "<option value='0'>请选择</option>";
			var typeContext3 = "";// 所有匹配的三级ID
			for (var i = 0; i < pageSource.length; i++) {
				if (pageSource[i].level == '3'
						&& pageSource[i].parentid == value) {
					type3Html += "<option value='"+pageSource[i].id+"'>"
							+ pageSource[i].name + "</option>";
					typeContext3 += pageSource[i].id + ",";
				}
			}
			var type4Html = "<option value='0'>请选择</option>";
			for (var i = 0; i < pageSource.length; i++) {
				if (pageSource[i].level == '4'
						&& (typeContext3.indexOf(pageSource[i].parentid) >= 0)) {
					type4Html += "<option value='"+pageSource[i].id+"'>"
							+ pageSource[i].name + "</option>";
				}
			}
			$("#edit_type3").html(type3Html);
			$("#edit_type4").html(type4Html);

			$("#edit_typeId").val(value);
		} else {
			edit_clickOpt_select1($("#edit_type1").val());
		}
	}
	//选定三级指标
	function edit_clickOpt_select3(value) {
		if (value != '0') {
			var pageSource = selectSource;
			var parentvalue1 = "";
			var parentvalue2 = $("#edit_type2").val();// 上一级的值
			if (parentvalue2 == '0') {// 上一级为空
				for (var i = 0; i < pageSource.length; i++) {
					if (pageSource[i].level == '3' && pageSource[i].id == value) {
						parentvalue2 = pageSource[i].parentid;
					}
				}
				for (var i = 0; i < pageSource.length; i++) {
					if (pageSource[i].level == '2'
							&& pageSource[i].id == parentvalue2) {
						parentvalue1 = pageSource[i].parentid;
					}
				}
				$("#edit_type1").val(parentvalue1);
				edit_clickOpt_select1(parentvalue1);
				$("#edit_type2").val(parentvalue2);
				edit_clickOpt_select2(parentvalue2);
				$("#edit_type3").val(value);
				//edit_clickOpt_select1(parentvalue3);
			}

			var type4Html = "<option value='0'>请选择</option>";
			for (var i = 0; i < pageSource.length; i++) {
				if (pageSource[i].level == '4'
						&& pageSource[i].parentid == value) {
					type4Html += "<option value='"+pageSource[i].id+"'>"
							+ pageSource[i].name + "</option>";
				}
			}
			$("#edit_type4").html(type4Html);

			$("#edit_typeId").val(value);
		} else {
			edit_clickOpt_select2($("#edit_type2").val());
		}
	}
	//选定四级指标
	function edit_clickOpt_select4(value) {
		if (value != '0') {
			var pageSource = selectSource;
			var parentvalue1 = "";
			var parentvalue2 = "";
			var parentvalue3 = $("#edit_type3").val();// 上一级的值
			if (parentvalue3 == '0') {// 上一级为空
				for (var i = 0; i < pageSource.length; i++) {
					if (pageSource[i].level == '4' && pageSource[i].id == value) {
						parentvalue3 = pageSource[i].parentid;
					}
				}
				for (var i = 0; i < pageSource.length; i++) {
					if (pageSource[i].level == '3'
							&& pageSource[i].id == parentvalue3) {
						parentvalue2 = pageSource[i].parentid;
					}
				}
				for (var i = 0; i < pageSource.length; i++) {
					if (pageSource[i].level == '2'
							&& pageSource[i].id == parentvalue2) {
						parentvalue1 = pageSource[i].parentid;
					}
				}
				$("#edit_type1").val(parentvalue1);
				edit_clickOpt_select1(parentvalue1);
				$("#edit_type2").val(parentvalue2);
				edit_clickOpt_select2(parentvalue2);
				$("#edit_type3").val(parentvalue3);
				edit_clickOpt_select3(parentvalue3);
				$("#edit_type4").val(value);
			}

			$("#edit_typeId").val(value);
		} else {
			edit_clickOpt_select3($("#edit_type3").val());
		}
	}
</script>
</html>