<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文档管理</title>
<script type="text/javascript" src="/mis-web/source/mvc/lib/cui.js"></script>
<script type="text/javascript"
	src="/mis-web/source/mvc/config/config.js"></script>
<script type="text/javascript" src="/mis-web/source/mvc/page/page.js"></script>
<link href="/mis-web/source/mvc/page/page.css" rel="stylesheet" />

<!-- jquery库 -->  
<!-- <script type="text/javascript" src="/mis-web/source/mvc/js/jquery-1.4.2.js"></script>  
<script type="text/javascript" src="/mis-web/source/mvc/My97DatePicker/calendar.js"></script>
<script type="text/javascript" src="/mis-web/source/mvc/My97DatePicker/WdatePicker.js"></script>
 -->
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
					<label for="menu_name">文档名称：</label> 
					<input id="fileName" name="fileName" usemap="{'logic':'and','compare':'like'}" placeholder="请输入文档名称" />
					<label for="menu_name">行业名称：</label> 
					<input id="industry" name="industry" usemap="{'logic':'and','compare':'like'}" placeholder="请输行业名称" />
					<label for="menu_name">企业名称：</label> 
					<input id="company" name="company" usemap="{'logic':'and','compare':'like'}" placeholder="请输入企业名称" />
				</div>
				<div>
					<label for="menu_name">文档类别：</label><input id="targetName" name="targetName" usemap="{'logic':'and','compare':'like'}" placeholder="请输入文档类别" />
					<label for="menu_name">类别：</label><select id="filesuffix" name="filesuffix" usemap="{'logic':'and','compare':'='}" >
						<option value="">请选择</option>
						<option value=".doc">doc</option>
						<option value=".docx">docx</option>
						<option value=".xls">xls</option>
						<option value=".xlsx">xlsx</option>
					</select>
					<button type="button" class="btn btn-zdy search" id="search">查询</button>
					<button type="reset" class="btn btn-zdy">重置</button>
				</div>
			</form>
			<div>
				<!-- 上传文档按钮 -->
				<p class="send">
					<button class="btn btn-zdy" data-toggle="modal" data-target="#addcontent" onclick="file_add();">上传文档</button>
				</p>

				<!-- 文档列表 -->	
				<table class="table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>序号</th>
							<th>文档名称</th>
							<th>行业</th>
							<th>企业</th>
							<th>文档类别</th>
							<th>文档时限</th>
							<!-- <th>类别</th> -->
							<th>创建时间</th>
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
		<div class="modal fade" id="addcontent" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="ModalLabel">上传文档</h4>
					</div>

					<form id="add_formId" enctype="multipart/form-data" class="form-horizontal">
						<div class="modal-body">
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">行业：</label>
								<div class="col-sm-4">
									<select id="add_industry" name="add_industry" onchange="addindustry(this.value);"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">企业：</label>
								<div class="col-sm-4">
									<select id="add_company" name="add_company" onchange="addcompany(this.value);"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							
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
								<label for="contentname" class="col-sm-3 control-label">四级类别：</label>
								<div class="col-sm-4">
									<select id="add_type4" name="add_type4" onchange="add_clickOpt('4');"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">文档时限：</label>
								<div class="col-sm-4">
									<input id="add_time" name="add_time" type="text" "/><!-- onClick="WdatePicker() -->
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">上传文档：</label>
								<div class="col-sm-4">
									<input type="file" name="add_detail" id="add_detail" multiple="multiple">
								</div>
							</div>
							<input type="hidden" id="add_typeId" name="add_typeId" value="0"/>
						</div>
						<div class="modal-footer ">
							<button type="button" class="btn btn-zdy" id="add_submit">确定</button>
							<button type="button" class="btn btn-zdy" data-dismiss="modal" id="add_cancel">取消</button>
						</div>
					</form>
				</div> <!-- /.modal-content -->
			</div> <!-- /.modal -->
		</div><!-- /.modal fade -->
		
		<!-- 修改 -->
		<div class="modal fade" id="editcontent" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="ModalLabel">上传文档</h4>
					</div>

					<form id="edit_formId" enctype="multipart/form-data" class="form-horizontal">
						<div class="modal-body">
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">行业：</label>
								<div class="col-sm-4">
									<select id="edit_industry" name="edit_industry" onchange="editindustry(this.value);"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">企业：</label>
								<div class="col-sm-4">
									<select id="edit_company" name="edit_company" onchange="editcompany(this.value);"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							
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
								<label for="contentname" class="col-sm-3 control-label">四级类别：</label>
								<div class="col-sm-4">
									<select id="edit_type4" name="edit_type4" onchange="edit_clickOpt('4');"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">文档时限：</label>
								<div class="col-sm-4">
									<input id="edit_time" name="edit_time" type="text" "/><!-- onClick="WdatePicker() -->
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">上传文档：</label>
								<div class="col-sm-4">
									<label id="edit_filename"></label>
									<!-- <input type="file" name="edit_detail" id="edit_detail" multiple="multiple"> -->
								</div>
							</div>
							<input type="hidden" id="edit_typeId" name="edit_typeId" value="0"/>
							<input type="hidden" id="edit_id" name="edit_id" value="0"/>
						</div>
						<div class="modal-footer ">
							<button type="button" class="btn btn-zdy" id="edit_submit">确定</button>
							<button type="button" class="btn btn-zdy" data-dismiss="modal" id="edit_cancel">取消</button>
						</div>
					</form>
				</div> <!-- /.modal-content -->
			</div> <!-- /.modal -->
		</div><!-- /.modal fade -->
		
	<!-- 功能部分结束 -->
</body>


<script type="text/javascript">
//查询按钮绑定点击事件
CUI.use([ 'ajaxform', 'utils', 'layer' ], function($ajax, $utils,$layer) {
	return {
		initialize : function() {//页面加载后执行
			pageSearch(1);// 查询列表第一页数据
			listTarget();
			listCompany();
			listIndustry();
			$("#search").on("click",function() {
				pageSearch(1);
			});
			new CuiAjaxForm($('#add_formId'), {// 新增文档
				submitSelector : $('#add_submit'),
				action : '../updownload/upload',
				beforeEvent : function(formData, jqForm, options) {
					$.insertDynamicDataForTheForm(formData,
							jqForm, options, 'jdbcTemplateName', 'mysqlTemplate');
					$.insertDynamicDataForTheForm(formData,
							jqForm, options, 'pFile', 'file');
					$.insertDynamicDataForTheForm(formData,
							jqForm, options, 'pKey', 'insertFile');
					return true;
				},
				callbackEvent : function(result) {
					if (result.status == 'success') {
						alert('上传成功!');
						$("#add_cancel").click();// 关闭新增窗口
						pageSearch(1);
					}
				}
			});
			new CuiAjaxForm($('#edit_formId'), {// 新增文档
				submitSelector : $('#edit_submit'),
				action : '../target/formProcessing',
				beforeEvent : function(formData, jqForm, options) {
					$.insertDynamicDataForTheForm(formData,
							jqForm, options, 'jdbcTemplateName', 'mysqlTemplate');
					$.insertDynamicDataForTheForm(formData,
							jqForm, options, 'pFile', 'file');
					$.insertDynamicDataForTheForm(formData,
							jqForm, options, 'pKey', 'updateFile');
					return true;
				},
				callbackEvent : function(result) {
					if (result.status == 'success') {
						alert('修改成功!');
						$("#edit_cancel").click();// 关闭新增窗口
						pageSearch(1);
					}
				}
			});
		}// /.initialize
	}// /.return
});// /.CUI.use

// 分页查询
function pageSearch(pageNo) {
	var pageSize = 6;// 每页条目数
	$.asyncRequest({
		url : '../member/pageSearch',
		data : $.extend({}, {
			"jdbcTemplateName" : "mysqlTemplate",
			"pFile" : "file",
			"pKey" : "selectFilelist",
			"type" : "mysql",
			"pageNo" : pageNo,
			"pageSize" : pageSize,
			"order" : ""
		}, 
		$('#searchFrom').buildQueryInfo(), true),
		event : function(result) {
			if (result.status === 'success') {
				//后台返回的结果集，格式:
				var pageSource = result.listInfo;
				//alert(JSON.stringify(pageSource));
				$("#mytable").html("");// 清空列表
				$(pageSource.list).each(
					function(index, element) {
						var ind = parseInt(index) + 1;
						var filetarget = element.filetarget;
						if(filetarget == null) {
							filetarget = "";
						}
						var targetname = element.targetname;
						if(targetname == null) {
							targetname = "";
						}
						var createtime = element.createtime;
						if(createtime == null) {
							createtime = "";
						}
						var industry = element.industry;// 行业
						if(industry == null) {
							industry = "";
						}
						var company = element.company;// 企业
						if(company == null) {
							company = "";
						}
						var filetime = element.filetime;// 时限
						if(filetime == null) {
							filetime = "";
						}
						$("#mytable").append(
								"<tr><td>"+ind
								+ "</td><td>"
								+ element.filename
								+"</td><td>"
								+ industry
								+"</td><td>"
								+ company
								+"</td><td>"
								+ targetname
								+"</td><td>"
								+ filetime
								/* +"</td><td>"
								+ element.filesuffix */
								+"</td><td>"
								+ createtime
								+"</td><td>"
								+ "<a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#editcontent\" onclick=\"file_edit("
								+ "'" + element.id +"'" + ")\">"
								+ "修改</a>"
								+ "|"
								+ "<a href=\"javascript:void(0)\" onclick=\"deletefile("
								+ "'" + element.id +"'" + ")\">"
								+ "删除</a>"
								+ "|<a href=\"javascript:void(0)\" onclick=\"downloadfile("
								+ "'" + element.filenewname +"'" + ","
								+ "'" + element.filename + "'" + ","
								+ "'" + element.filesuffix + "'" + ")\">"
								+ "下载</a>"
								+ "</td></tr>"
								);
					});
				$("#mytable").append("<tr><td colspan=\"10\" id=\"pager\"></td></tr>");
				//创建分页
				var target = $('#pager');
				pageClick(pageSource.pageNo, pageSource.total,target,pageSize);
			}
		},
	});
}

// 下载; Param:存储的文档名，原文档名，后缀
function downloadfile(filenewname,filename,filesuffix) {//
	window.location.href = "../updownload/download?storeName="+filenewname+filesuffix+"&downloadName="+filename;
}
// 删除; Param:文档ID
function deletefile(id) {
	if (confirm("确认删除吗")) {
		$.ajax({
			url : "../member/formProcessing",
			data : {
				"jdbcTemplateName" : "mysqlTemplate",
				"pFile" : "file",
				"pKey" : "deleteFile",
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

var selectSource;
//查询并设置指标类别选择框
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
				$("#add_type4").html(type4Html);
				$("#edit_type1").html(type1Html);
				$("#edit_type2").html(type2Html);
				$("#edit_type3").html(type3Html);
				$("#edit_type4").html(type4Html);
			}
		},
	});
}

function listIndustry() {
	$.asyncRequest({
		url : '../member/baseQuery',
		data : $.extend({}, {
			"jdbcTemplateName" : "mysqlTemplate",
			"pFile" : "file",
			"pKey" : "selectFileIndustrySelect",
		}, $('#searchFrom').buildQueryInfo(), true),
		event : function(result) {
			if (result.status === 'success') {
				//后台返回的结果集，格式:
				var pageSource = result.listInfo;
				//下面写你的任何业务
				//alert(JSON.stringify(pageSource));
				
				var type1Html = "<option value='0'>请选择</option>";
				for (var i = 0; i < pageSource.length; i++) {
					type1Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
				}
				$("#add_industry").html(type1Html);
				$("#edit_industry").html(type1Html);
			}
		},
	});
}
var selectCompany;
//查询并设置指标类别选择框
function listCompany() {
	$.asyncRequest({
		url : '../member/baseQuery',
		data : $.extend({}, {
			"jdbcTemplateName" : "mysqlTemplate",
			"pFile" : "file",
			"pKey" : "selectFileCompanySelect",
		}, $('#searchFrom').buildQueryInfo(), true),
		event : function(result) {
			if (result.status === 'success') {
				//后台返回的结果集，格式:
				var pageSource = result.listInfo;
				selectCompany = pageSource;
				//下面写你的任何业务
				//alert(JSON.stringify(pageSource));
				
				var type1Html = "<option value='0'>请选择</option>";
				for (var i = 0; i < pageSource.length; i++) {
					type1Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
				}
				$("#add_company").html(type1Html);
				$("#edit_company").html(type1Html);
			}
		},
	});
}

function file_add() {
	$("#add_typeId").val("");
	$("#add_industry").val("0");
	$("#add_company").val("0");
	$("#add_detail").val("");
	$("#add_company").val("0");
	$("#add_type1").val("0");
	$("#add_time").val("");
	add_clickOpt("1");
}
function file_edit(id) {
	$.ajax({
		url : '../member/baseQuery',
		type : "post",
		data : {
			"jdbcTemplateName" : "mysqlTemplate",
			"pFile" : "file",
			"pKey" : "selectFileInfo",
			"type" : "mysql",
			"id" : id
		},
		dataType : 'json',
		async : true,
		success : function(result) {
			if (result.status === 'success') {
				var listInfo = result.listInfo;
				$("#edit_id").val(listInfo[0].id);// 赋值
				$("#edit_industry").val(listInfo[0].ts_industry_id);
				$("#edit_company").val(listInfo[0].ts_company_id);
				$("#edit_time").val(listInfo[0].filetime);
				$("#edit_filename").html(listInfo[0].filename);
				
				var level = listInfo[0].targetlevel;
				var targetid = listInfo[0].filetarget;
				if(level == '1') {
					$("#edit_type1").val(targetid)
				} else if(level == '2') {
					$("#edit_type2").val(targetid)
				} else if(level == '3') {
					$("#edit_type3").val(targetid)
				} else if(level == '4') {
					$("#edit_type4").val(targetid)
				}
				edit_clickOpt(level)
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("跳转修改页面失败！");
		}
	});
}

function addindustry(value) {
	var pageSource = selectCompany;
	var type1Html = "<option value='0'>请选择</option>";
	if(value != "0") {
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].parentid == value) {
				type1Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			}
		}
	} else {
		for (var i = 0; i < pageSource.length; i++) {
			type1Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
		}
	}
	$("#add_company").html(type1Html);
	
}
function addcompany(value) {
	var pageSource = selectCompany;
	var industryid = "0";
	for (var i = 0; i < pageSource.length; i++) {
		if(pageSource[i].id == value) {
			industryid = pageSource[i].parentid;
		}
	}
	$("#add_industry").val(industryid);
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
		add_clickOpt_select4($("#add_type4").val())
	}
}
// 选定一级指标
function add_clickOpt_select1(value) {
	var pageSource = selectSource;
	if(value != '0') {
		var type2Html = "<option value='0'>请选择</option>";
		var type3Html = "<option value='0'>请选择</option>";
		var type4Html = "<option value='0'>请选择</option>";
		var typeContext2 = "";// 所有匹配的二级ID
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '2' && pageSource[i].parentid == value) {
				type2Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
				typeContext2 += pageSource[i].id+",";
			}
		}
		var typeContext3 = "";// 所有匹配的三级ID
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '3' && (typeContext2.indexOf(pageSource[i].parentid) >= 0)) {
				type3Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
				typeContext3 += pageSource[i].id+",";
			}
		}
		
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '4' && (typeContext3.indexOf(pageSource[i].parentid) >= 0)) {
				type4Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			}
		}
		$("#add_type2").html(type2Html);
		$("#add_type3").html(type3Html);
		$("#add_type4").html(type4Html);
		
		$("#add_typeId").val(value);
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
		$("#add_type4").html(type4Html);
		
		$("#add_typeId").val(value);
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
		var typeContext3 = "";// 所有匹配的三级ID
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '3' && pageSource[i].parentid == value) {
				type3Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
				typeContext3 += pageSource[i].id+",";
			}
		}
		var type4Html = "<option value='0'>请选择</option>";
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '4' && (typeContext3.indexOf(pageSource[i].parentid) >= 0)) {
				type4Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			}
		}
		$("#add_type3").html(type3Html);
		$("#add_type4").html(type4Html);
		
		$("#add_typeId").val(value);
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
		
		var type4Html = "<option value='0'>请选择</option>";
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '4' && pageSource[i].parentid == value) {
				type4Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			}
		}
		$("#add_type4").html(type4Html);
		
		$("#add_typeId").val(value);
	} else {
		add_clickOpt_select2($("#add_type2").val());
	}
}
//选定四级指标
function add_clickOpt_select4(value) {
	if(value != '0') {
		var pageSource = selectSource;
		var parentvalue1 = "";
		var parentvalue2 = "";
		var parentvalue3 = $("#add_type3").val();// 上一级的值
		if(parentvalue3 == '0') {// 上一级为空
			for (var i = 0; i < pageSource.length; i++) {
				if(pageSource[i].level == '4' && pageSource[i].id == value) {
					parentvalue3 = pageSource[i].parentid;
				}
			}
			for (var i = 0; i < pageSource.length; i++) {
				if(pageSource[i].level == '3' && pageSource[i].id == parentvalue3) {
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
			$("#add_type3").val(parentvalue3);
			add_clickOpt_select3(parentvalue3);
			$("#add_type4").val(value);
		}
		
		$("#add_typeId").val(value);
	} else {
		add_clickOpt_select3($("#add_type3").val());
	}
}






function editindustry(value) {
	var pageSource = selectCompany;
	var type1Html = "<option value='0'>请选择</option>";
	if(value != "0") {
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].parentid == value) {
				type1Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
			}
		}
	} else {
		for (var i = 0; i < pageSource.length; i++) {
			type1Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
		}
	}
	$("#edit_company").html(type1Html);
	
}
function editcompany(value) {
	var pageSource = selectCompany;
	var industryid = "0";
	for (var i = 0; i < pageSource.length; i++) {
		if(pageSource[i].id == value) {
			industryid = pageSource[i].parentid;
		}
	}
	$("#edit_industry").val(industryid);
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
		edit_clickOpt_select4($("#edit_type4").val())
	}
}
// 选定一级指标
function edit_clickOpt_select1(value) {
	var pageSource = selectSource;
	if(value != '0') {
		var type2Html = "<option value='0'>请选择</option>";
		var type3Html = "<option value='0'>请选择</option>";
		var type4Html = "<option value='0'>请选择</option>";
		var typeContext2 = "";// 所有匹配的二级ID
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '2' && pageSource[i].parentid == value) {
				type2Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
				typeContext2 += pageSource[i].id+",";
			}
		}
		var typeContext3 = "";// 所有匹配的三级ID
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '3' && (typeContext2.indexOf(pageSource[i].parentid) >= 0)) {
				type3Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
				typeContext3 += pageSource[i].id+",";
			}
		}
		
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '4' && (typeContext3.indexOf(pageSource[i].parentid) >= 0)) {
				type4Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
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
		$("#edit_type4").html(type4Html);
		
		$("#edit_typeId").val(value);
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
		var typeContext3 = "";// 所有匹配的三级ID
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '3' && pageSource[i].parentid == value) {
				type3Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
				typeContext3 += pageSource[i].id+",";
			}
		}
		var type4Html = "<option value='0'>请选择</option>";
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '4' && (typeContext3.indexOf(pageSource[i].parentid) >= 0)) {
				type4Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
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
			//edit_clickOpt_select1(parentvalue3);
		}
		
		var type4Html = "<option value='0'>请选择</option>";
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].level == '4' && pageSource[i].parentid == value) {
				type4Html += "<option value='"+pageSource[i].id+"'>" + pageSource[i].name + "</option>";
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
	if(value != '0') {
		var pageSource = selectSource;
		var parentvalue1 = "";
		var parentvalue2 = "";
		var parentvalue3 = $("#edit_type3").val();// 上一级的值
		if(parentvalue3 == '0') {// 上一级为空
			for (var i = 0; i < pageSource.length; i++) {
				if(pageSource[i].level == '4' && pageSource[i].id == value) {
					parentvalue3 = pageSource[i].parentid;
				}
			}
			for (var i = 0; i < pageSource.length; i++) {
				if(pageSource[i].level == '3' && pageSource[i].id == parentvalue3) {
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