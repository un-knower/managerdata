<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>要素管理</title>
<script type="text/javascript" src="/mis-web/source/mvc/lib/cui.js"></script>
<script type="text/javascript"
	src="/mis-web/source/mvc/config/config.js"></script>
<script type="text/javascript" src="/mis-web/source/mvc/page/page.js"></script>
<link href="/mis-web/source/mvc/page/page.css" rel="stylesheet" />

</head>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
String time = sdf.format(new Date());
%>
<body>
	<!-- 功能部分开始 -->
	<!-- <section id="page-content"> -->
	<div id="Inquire-two">
	<!-- 选择模块 -->
	<form>
		<div class="input-group">
			<label for="menu_name">文档名称：</label> 
			<input id="fileName" readonly="readonly" name="fileName" style="width:240px;" usemap="{'logic':'and','compare':'like'}" placeholder="请选择文档" /> 
			<span>
				<button type="button" class="btn btn-zdy search"
					data-toggle="modal" data-target="#addcontent">选择文档</button>
			</span>
			<!-- 选中的文件ID -->
			<input type="hidden" id="fileId" name="fileId">
			<!-- 选中的文件实际存储名称 -->
			<input type="hidden" id="filePath" name="filePath">
		</div>
	</form>

	<div style="BORDER-RIGHT: 7px groove; BORDER-TOP: 7px groove; BACKGROUND: transprant; BORDER-LEFT: 7px groove; WIDTH: 100%; BORDER-BOTTOM: 7px groove; HEIGHT: 100%">
	<div style="height: 185px; overflow: auto;">
		<!-- 复选框区 -->
		<table border="1" width="100%" cellpadding="0" cellspacing="0">
			<tbody id="texttable">
			</tbody>
		</table>
	</div>
	</div>


		<!-- 左 -->
		<div style="height: 200px; float: left;">
			<!-- 文本分割区 -->
			<textarea rows="10" cols="80" id="textarea"></textarea>
		</div>
		<!-- 右 -->
		<div style="height: 200px;">
			<table align="center">
				<tr>
					<td><label>一级指标：</label></td>
					<td><select id="type1" name="type1" onchange="clickOpt1();"
						style="height: 33px; width: 100%; border-color: #e3e6f3;">
							<option value="0">请选择</option>
					</select></td>
				</tr>
				<tr>
					<td><label>二级指标：</label></td>
					<td><select id="type2" name="type2" onchange="clickOpt2();"
						style="height: 33px; width: 100%; border-color: #e3e6f3;">
							<option value="0">请选择</option>
					</select></td>
				</tr>
				<tr>
					<td><label>三级指标：</label></td>
					<td><select id="type3" name="type3" onchange="clickOpt3();"
						style="height: 33px; width: 100%; border-color: #e3e6f3;">
							<option value="0">请选择</option>
					</select></td>
				</tr>
				<tr>
					<td><label>四级指标：</label></td>
					<td><select id="type4" name="type4" onchange="clickOpt4();"
						style="height: 33px; width: 100%; border-color: #e3e6f3;">
							<option value="0">请选择</option>
					</select></td>
				</tr>
				<tr>
					<!-- 指标ID -->
					<input type="hidden" id="id" name="id" value="0" />
					<!-- 指标级别 -->
					<input type="hidden" id="typeLevel" name="typeLevel" value="1" />
					
					<td><button type="button" class="btn btn-zdy search" data-toggle="modal" data-target="#addcontent2" onclick="addtype();">增加指标</button></td>
					<td><button type="button" class="btn btn-zdy search" onclick="submit();">确认</button></td>
				</tr>
			</table>
		</div>
		<!-- ./右 -->
	</div>


	</div>
		</section>

		<!-- 选择文档-->
		<div class="modal fade" id="addcontent" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="ModalLabel">选择文档</h4>
					</div>

					<!-- <div id="page"> -->
					<!-- <section id="page-content"> -->
					<!-- <div id="Inquire-two"> -->
					<!-- 查询模块 -->
					<form id="searchFrom">
						<div class="input-group">
							<label for="menu_name">文档名称：</label> 
							<input id="fileName" name="fileName" usemap="{'logic':'and','compare':'like'}" placeholder="请输入文档名称" /> 
							<button type="button" class="btn btn-zdy search" id="search">查询</button>
						</div>
					</form>
				<div>
				<!-- 文档列表 -->	
				<table class="table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>选择</th>
							<th>序号</th>
							<th>文档名称</th>
							<th>文档类别</th>
							<th>创建时间</th>
							<!-- <th>操作</th> -->
						</tr>
					</thead>
					<tbody id="mytable">

					</tbody>
				</table>
			</div>
			
			<!-- </div> -->
			<!-- </section> -->
			<!-- </div> -->
				<div class="modal-footer ">
					<button type="button" class="btn btn-zdy" id="submit" onclick="selectFile();">确定</button>
					<button type="button" class="btn btn-zdy" data-dismiss="modal" id="miss">取消</button>
				</div>
				</div> <!-- /.modal-content -->
			</div> <!-- /.modal -->
		</div><!-- /.modal fade -->
		
		
		<!-- 新增要素 -->
		<div class="modal fade" id="addcontent2" tabindex="-1" role="dialog"
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
							<!-- <div class="form-group">
								<label for="contentname" class="col-sm-3 control-label">四级指标：</label>
								<div class="col-sm-4">
									<select id="type4" name="type4"
										style="height: 33px; width: 100%; border-color: #e3e6f3;">
										<option value="0">请选择</option>
									</select>
								</div>
							</div> -->
							
							<input type="hidden" id="parentId_2" name="parentId_2" value="0"/>
							<input type="hidden" id="typeLevel_2" name="typeLevel_2" value="1"/>
							<div class="form-group">
								<label for="mname1" class="col-sm-3 control-label">指标名称：</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="typeName_2" name="typeName_2" />
								</div>
							</div>
						</div>
						<div class="modal-footer ">
							<button type="button" class="btn btn-zdy" id="submit_2">确定</button>
							<button type="button" class="btn btn-zdy" data-dismiss="modal" id="miss_2">取消</button>
						</div>
					</form>
				</div> <!-- /.modal-content -->
			</div> <!-- /.modal -->
		</div> <!-- /.modal fade -->
	</div>
		
	<!-- 功能部分结束 -->
</body>


<script type="text/javascript">
//查询按钮绑定点击事件
CUI.use([ 'ajaxform', 'utils', 'layer' ], function($ajax, $utils,$layer) {
	return {
		initialize : function() {//页面加载后执行
			var time = '<%=time %>';
			pageSearch(1);// 查询列表第一页数据
			listSearch();// 查询select选择框数据
			$("#search").on("click",function() {
				pageSearch(1);
			});
			// 新增
			new CuiAjaxForm($('#formId'), {
				submitSelector : $('#submit_2'),
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
						$("#miss_2").click();
						listSearch();// 查询select选择框数据
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
						$("#mytable").append(
								"<tr>"
								+"<td>"
								+"<input type='radio' name='ra' id='ra'  value='"+element.id+"'/>"
								+"</td>"
								+"<td>"+ind
								+ "</td><td>"
								+ element.filename
								+"</td><td>"
								+ targetname
								+"</td><td>"
								+ createtime
								+"</td>"
								/* +"<td>"
								+ "<a href=\"javascript:void(0)\" onclick=\"deletefile("
								+ "'" + element.id +"'" + ")\">"
								+ "删除</a>"
								+ " | <a href=\"javascript:void(0)\" onclick=\"downloadfile("
								+ "'" + element.filenewname +"'" + ","
								+ "'" + element.filename + "'" + ","
								+ "'" + element.filesuffix + "'" + ")\">"
								+ "下载</a>"
								+ "</td>" */
								+ "</tr>"
								);
					});
				$("#mytable").append("<tr><td colspan=\"8\" id=\"pager\"></td></tr>");
				//创建分页
				var target = $('#pager');
				pageClick(pageSource.pageNo, pageSource.total,target,pageSize);
			}
		},
	});
}

// 选择文档点击事件
function selectFile(select) {
	var select = $('input:radio:checked').val();
	$("#texttable").html("");
	$.ajax({
		//url : '../member/baseQuery',
		url : '../manager/selectFileInfo',
		type : "post",
		data : {
			"jdbcTemplateName" : "mysqlTemplate",
			"pFile" : "file",
			"pKey" : "selectFileInfo",
			"type" : "mysql",
			"id" : select
		},
		dataType : 'json',
		async : true,
		success : function(result) {
			if (result.status === 'success') {
				var listInfo = result.listInfo;
				$("#fileId").val(listInfo[0].id);
				$("#fileName").val(listInfo[0].filename);
				$("#filePath").val(listInfo[0].filepath);
				
				var textInfo = result.textInfo;
				var textHtml = "";
				for(var i=0; i<textInfo.length; i++) {
					textHtml += "<tr><td>";
					textHtml += "<input type='checkbox' name='text_checkbox' id='text_checkbox'  value='"+textInfo[i].text+"' onclick='selectText()'/>"
					textHtml += textInfo[i].text; 
					textHtml += "</td></tr>";
				}
				$("#texttable").append(textHtml);
			} else if(result.status === 'FileNotFound') {
				alert("文件未找到！");
			} else if(result.status === 'error') {
				alert("文件解析异常！");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("跳转选择页面失败");
		}
	});
	
	$("#miss").click();// 关闭选择窗口
}


function selectText(text){
	var obj = document.getElementsByName("text_checkbox");//选择所有name="text_checkbox"的对象，返回数组 
	var s='';//如果这样定义var s;变量s中会默认被赋个null值
    for(var i=0;i<obj.length;i++){
         if(obj[i].checked) //取到对象数组后，我们来循环检测它是不是被选中
         s+=obj[i].value+'\n';   //如果选中，将value添加到变量s中    
    }
	$("#textarea").val(s);
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
function submit() {
	var fileId = $("#fileId").val();// 文档ID
	if(fileId == null || fileId == '') {
		alert("请选择文档！");
		return;
	}
	var textarea = $("#textarea").val();// 文档内容
	if(textarea == null || textarea == '') {
		alert("请选择内容！");
		return;
	}
	var fileTypeId = $("#id").val();// 指标ID
	if(fileTypeId == 0) {
		alert("请选择指标！");
		return;
	}
	if (confirm("确认提交吗")) {
		$.ajax({
			url : "../file/insertfiletext",
			data : {
				"jdbcTemplateName" : "mysqlTemplate",
				"pFile" : "file",
				"pKey" : "insertFileText",
				"text":textarea,
				"fileTypeId":fileTypeId,
				"fileId":fileId
			},
			dataType : "json",
			type : 'post',
			async : false,
			success : function(result) {
				if (result.status === 'success') {
					alert("提交成功!");
					cleanHtml();
					//location.reload();// 重载页面，刷新列表
				}
			}
		})
	} else {
		return;
	}
	
}

function cleanHtml() {
	var obj = document.getElementsByName("text_checkbox");//选择所有name="text_checkbox"的对象，返回数组 
	var s='';//如果这样定义var s;变量s中会默认被赋个null值
	for (var i = 0; i < obj.length; i++) {
		if (obj[i].checked) {//取到对象数组后，我们来循环检测它是不是被选中
			obj[i].checked="";
		}
	}
	$("#textarea").val("");// 文档内容
}


// 新增要素
function addtype() {
	listSearch();
	$("#type1_2").val(0);
 	$("#type2_2").val(0);
 	$("#type3_2").val(0);
 	$("#typeName_2").val("");
 	$("#parentId_2").val(0);
 	$("#typeLevel_2").val(1);
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
 	$("#parentId_2").val(value);
 	$("#typeLevel_2").val(2);
 } else {
 	$("#type2_2").html("");
 	$("#type3_2").html("");
 	$("#parentId_2").val(0);
 	$("#typeLevel_2").val(1);
 }
}
//select 联动2
function clickOpt2_2(){  
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
		$("#parentId_2").val(value);
 	$("#typeLevel_2").val(3);
 } else {
 	$("#type3_2").html("");
 	$("#parentId_2").val($("#type1_2").val());
 	$("#typeLevel_2").val(2);
 }
}
//select 联动3
function clickOpt3_2(){  
 var value = $("#type3_2").val();
 if(value != '0') {
		$("#parentId_2").val(value);
 	$("#typeLevel_2").val(4);
 } else {
 	$("#parentId_2").val($("#type2_2").val());
 	$("#typeLevel_2").val(3);
 }
}
</script>
</html>