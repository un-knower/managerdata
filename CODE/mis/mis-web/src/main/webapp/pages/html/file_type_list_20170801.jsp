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

<!-- ztree -->
<script type="text/javascript" src="/mis-web/source/mvc/tree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="/mis-web/source/mvc/tree/js/jquery.ztree.excheck.js"></script>
<link href="/mis-web/source/mvc/tree/css/zTreeStyle.css" rel="stylesheet" />
<!-- 
 -->
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
	
	<div class="form-group">
		<a href="javascript: openAll();">open all</a> | <a href="javascript: closeAll();">close all</a>
		<div class="ztree"  id="typeTree">

		</div>
	</div>
	
	<!-- dtree树 -->
	<!-- <p><a href="javascript: d.openAll();">open all</a> | <a href="javascript: d.closeAll();">close all</a></p>
	<script type="text/javascript">
		d = new dTree('d');
		d.add(0,-1,'要素类别');
		$.ajax ({
			url : '../member/baseQuery',
			async : false,//同步，等这个请求完成后才继续往下执行,这样myTree才能使用返回来的数据  
			dataType:"json",
			data : {
				"jdbcTemplateName" : "mysqlTemplate",
				"pFile" : "file",
				"pKey" : "selectFileTypeList",
			},
			success:function(result) {
				if (result.status === 'success') {
					//后台返回的结果集，格式:
					var pageSource = result.listInfo;
					//下面写你的任何业务
					//alert(JSON.stringify(pageSource));
					for (var i = 0; i < pageSource.length; i++) {
						d.add(pageSource[i].id,pageSource[i].parentid,pageSource[i].typename);
					}
				}
			},
		});
		//document.write(d);
	</script> -->
	
	
		<!-- <div id="Inquire-two"> -->
			<!-- 查询模块 -->
			<form id="searchFrom">
				<div class="input-group">
					<label for="menu_name">要素名称：</label>
					<input id="typeName" name="typeName" usemap="{'logic':'and','compare':'like'}" placeholder="请输入要素名称" /> 
					<button type="button" class="btn btn-zdy search" id="search">查询</button>
					<button type="reset" class="btn btn-zdy">重置</button>
				</div>
			</form>
			<div>
				<!-- 添加要素类别按钮 -->
				<p class="send">
					<button class="btn btn-zdy" data-toggle="modal" data-target="#addcontent" onclick="addType();">添加要素类别</button>
				</p>

				<!-- 要素类别列表 -->	
				<table class="table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>序号</th>
							<th>要素名称</th>
							<th>要素级别</th>
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
						<h4 class="modal-title" id="ModalLabel">添加要素类别</h4>
					</div>

					<form id="formId" enctype="multipart/form-data" class="form-horizontal">
						<div class="modal-body">
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">一级指标：</label>
								<div class="col-sm-4">
									<select id="type1" name="type1" onchange="clickOpt();"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">二级指标：</label>
								<div class="col-sm-4">
									<select id="type2" name="type2" onchange="clickOpt2();"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">三级指标：</label>
								<div class="col-sm-4">
									<select id="type3" name="type3" onchange="clickOpt3();"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<!-- <div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">四级指标：</label>
								<div class="col-sm-4">
									<select id="type4" name="type4"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div> -->
							
							<input type="hidden" id="parentId" name="parentId" value="0"/>
							<input type="hidden" id="typeLevel" name="typeLevel" value="1"/>
							<div class="form-group">
								<label for="mname1" class="col-sm-3 control-label">指标名称：</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="typeName_" name="typeName_" />
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
						<input type="hidden" value="" id="2_id" name="2_id" />
						<div class="modal-body">
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">一级指标：</label>
								<div class="col-sm-4">
									<select id="2_type1" name="2_type1" onchange="clickOpt_2();"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">二级指标：</label>
								<div class="col-sm-4">
									<select id="2_type2" name="2_type2" onchange="clickOpt2_2();"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">三级指标：</label>
								<div class="col-sm-4">
									<select id="2_type3" name="2_type3" onchange="clickOpt3_2();"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div>
							<!-- <div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">四级指标：</label>
								<div class="col-sm-4">
									<select id="type4" name="type4"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div> -->
							
							<input type="hidden" id="2_parentId" name="2_parentId" value="0"/>
							<input type="hidden" id="2_typeLevel" name="2_typeLevel" value="1"/>
							<div class="form-group">
								<label for="mname1" class="col-sm-3 control-label">指标名称：</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="2_typeName_" name="2_typeName_" />
								</div>
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
			selectTree();
			$("#search").on("click", function() {
				pageSearch(1);
			});
			// 新增
			new CuiAjaxForm($('#formId'), {
				submitSelector : $('#submit'),
				action : '../target/formProcessing',
				beforeEvent : function(formData, jqForm, options) {
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'jdbcTemplateName', 'mysqlTemplate');
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'pFile', 'file');
					$.insertDynamicDataForTheForm(formData, jqForm,
							options, 'pKey', 'insertFileType');
					return true;
				},
				callbackEvent : function(result) {
					if (result.status == 'success') {
						alert('保存成功!');
						$("#miss").click();
						pageSearch(1);
						selectTree();// 更新树
					}
				}
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
							options, 'pKey', 'updateFileType');
					return true;
				},
				callbackEvent : function(result) {
					if (result.status == 'success') {
						alert('修改成功');
						$("#qx1").click();
						pageSearch(1);
						selectTree();// 更新树
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
			"pKey" : "selectFileTypeList",
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
						+ element.typename
						+ "</td><td>"
						+ element.typelevel
						+ "</td><td>"
						+ "<a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#ee\" onclick=\"editType("
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
// 父类list
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
				$("#2_type1").html(type1Html);
				//$("#type2").html(type2Html);
				//$("#type3").html(type3Html);
				//$("#type4").html(type4Html);
			}
		},
	});
}


//select 联动1
function clickOpt(){ 
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
    	$("#type3").html(emptyHtml);
    	$("#parentId").val(value);
    	$("#typeLevel").val(2);
    } else {
    	$("#type2").html("");
    	$("#type3").html("");
    	$("#parentId").val(0);
    	$("#typeLevel").val(1);
    }
     
	
	
}
//select 联动2
function clickOpt2(){  
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
		$("#parentId").val(value);
    	$("#typeLevel").val(3);
    } else {
    	$("#type3").html("");
    	$("#parentId").val($("#type1").val());
    	$("#typeLevel").val(2);
    }
}
//select 联动3
function clickOpt3(){  
    var value = $("#type3").val();
    if(value != '0') {
		$("#parentId").val(value);
    	$("#typeLevel").val(4);
    } else {
    	$("#parentId").val($("#type2").val());
    	$("#typeLevel").val(3);
    }
}

//select_2 联动1
function clickOpt_2(){  
	var emptyHtml = "<option value='0'>请选择</option>";
    var value = $("#2_type1").val();
    if(value != '0') {
    	var pageSource = selectSource;
    	var typeHtml = "<option value='0'>请选择</option>";
    	for (var i = 0; i < pageSource.length; i++) {
    		if(pageSource[i].typelevel == '2' && pageSource[i].parentid == value) {
    			typeHtml += "<option value='"+pageSource[i].id+"'>" + pageSource[i].typename + "</option>";
    		}
    	}
    	$("#2_type2").html(typeHtml);
    	$("#2_type3").html(emptyHtml);
    	$("#2_parentId").val(value);
    	$("#2_typeLevel").val(2);
    } else {
    	$("#2_type2").html("");
    	$("#2_type3").html("");
    	$("#2_parentId").val(0);
    	$("#2_typeLevel").val(1);
    }
     
	
	
}
//select_2 联动2
function clickOpt2_2(){  
    var value = $("#2_type2").val();
    if(value != '0') {
	    var pageSource = selectSource;
		var typeHtml = "<option value='0'>请选择</option>";
		for (var i = 0; i < pageSource.length; i++) {
			if(pageSource[i].typelevel == '3' && pageSource[i].parentid == value) {
				typeHtml += "<option value='"+pageSource[i].id+"'>" + pageSource[i].typename + "</option>";
			}
		}
		$("#2_type3").html(typeHtml);
		$("#2_parentId").val(value);
    	$("#2_typeLevel").val(3);
    } else {
    	$("#2_type3").html("");
    	$("#2_parentId").val($("#2_type1").val());
    	$("#2_typeLevel").val(2);
    }
}
//select_2 联动3
function clickOpt3_2(){  
    var value = $("#2_type3").val();
    if(value != '0') {
		$("#2_parentId").val(value);
    	$("#2_typeLevel").val(4);
    } else {
    	$("#2_parentId").val($("#2_type2").val());
    	$("#2_typeLevel").val(3);
    }
}

function deletefile(id) {
	if (confirm("确认删除吗")) {
		$.ajax({
			url : "../member/formProcessing",
			data : {
				"jdbcTemplateName" : "mysqlTemplate",
				"pFile" : "file",
				"pKey" : "deleteFileType",
				"id" : id
			},
			dataType : "json",
			type : 'post',
			async : false,
			success : function(result) {
				if (result.status === 'success') {
					alert("删除成功!");
					pageSearch(1);
					selectTree();// 更新树
				}
			}
		})
	} else {
		return;
	}
}


// "新增"-新增要素类别
function addType() {
	listSearch();// 设定选择框内容
	$("#type1").val(0);
	$("#type2").val(0);
	$("#type3").val(0);
	$("#parentId").val(0);
	$("#typeLevel").val(1);
	$("#typeName_").val("");
	     
}
// "修改"-修改要素类别
function editType(id) {
	listSearch();// 设定选择框内容
	$.ajax({
		url : '../member/baseQuery',
		type : "post",
		data : {
			"jdbcTemplateName" : "mysqlTemplate",
			"pFile" : "file",
			"pKey" : "selectFileTypeInfo",
			"type" : "mysql",
			"id" : id
		},
		dataType : 'json',
		async : true,
		success : function(result) {
			if (result.status === 'success') {
				var listInfo = result.listInfo;

				var parentid = listInfo[0].parentid;
				var typelevel = listInfo[0].typelevel;
				$("#2_id").val(listInfo[0].id);// 赋值
				$("#2_parentId").val(parentid);// 赋值
				$("#2_typeLevel").val(typelevel);// 赋值
				$("#2_typeName_").val(listInfo[0].typename);// 赋值
				
				var pageSource = selectSource;
				if(typelevel == '1') {
					
				} else if(typelevel == '2') {
					$("#2_type1").val(parentid);
					clickOpt_2();
				} else if(typelevel == '3') {
					var value1;
					for (var i = 0; i < pageSource.length; i++) {
			    		if(pageSource[i].typelevel == '2' && pageSource[i].id == parentid) {
			    			value1 = pageSource[i].parentid;
			    			break;
			    		}
			    	}
					$("#2_type1").val(value1);
					clickOpt_2();
					$("#2_type2").val(parentid);
					clickOpt2_2();
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
					$("#2_type1").val(value2);
					clickOpt_2();
					$("#2_type2").val(value1);
					clickOpt2_2();
					$("#2_type3").val(parentid);
					clickOpt2_3();
				}
				
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("跳转编辑页面失败");
		}
	});
}

// ztree
var setting = {
		/* check: {
			enable: true
		}, */
		data: {
			simpleData: {
				enable: true
			}
		}
		//,
		//callback: {
			//onCheck: zTreeOnCheck
		//}
	};
function selectTree() {
	$.asyncRequest({
	 	url :'../member/baseQuery',
	 	async : false,//同步，等这个请求完成后才继续往下执行,这样myTree才能使用返回来的数据  
	 	dataType:"json",
		data : {
			"jdbcTemplateName":"mysqlTemplate",
			"pFile":"file",
			"pKey":"selectFileTypeSelectList",
		},
		event : function(result) {
			if (result.status === 'success') {
				var pageSource = result.listInfo;
				var a = JSON.stringify(pageSource);
				a = a.replace(/pid/g,"pId");//别名不行，传过来的全是小写
				//a = a.replace(/pid/g,"pId");//别名不行，传过来的全是小写
				//var zNodes = [{"id":"1","pId":"0","name":"菜单管理"},{"id":"2","pId":"1","name":"菜单管理"},{"id":"3","pId":"1","name":"角色管理"}]; 
				var zNodes = JSON.parse(a);//zNodes不是字符串
				$.fn.zTree.init($("#typeTree"), setting, zNodes);
				//var treeObj = $.fn.zTree.getZTreeObj("typeTree"); 
				//treeObj.expandAll(true); //展开所有
			} 
		},errorFn:function(){
			layer.alert('列表查询出错!');
		}
	});
}
function openAll() {
	var treeObj = $.fn.zTree.getZTreeObj("typeTree");
	treeObj.expandAll(true); //展开所有
}
function closeAll() {
	var treeObj = $.fn.zTree.getZTreeObj("typeTree");
	treeObj.expandAll(false); //关闭所有
	
}
</script>
</html>