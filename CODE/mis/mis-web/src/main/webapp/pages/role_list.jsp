<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="userTags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="en">

<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/source/mvc/lib/bootstrap-paginator.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/source/mvc/lib/cui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/source/mvc/config/config.js"></script>
</head>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
String time = sdf.format(new Date());
%>
<body>
	<!-- Start: Content -->
	<div class="container-fluid content">
		<div class="row">
			<!--BEGIN SIDEBAR MENU-->
			<userTags:left currentNav="user"></userTags:left>
			<!--END SIDEBAR MENU-->
		</div>

	</div>
	<!-- End Sidebar -->
	<div id="page">
		<section id="page-content">

			<div id="Inquire-two">
				<h3 class="title">角色信息</h3>
				<!-- 下面是根据权限展示有权限展示111111,name的值是后台方法获取的 -->
				<!-- <shiro:hasPermission name="member:add">11111</shiro:hasPermission>
				<shiro:lacksPermission name="member:add">222</shiro:lacksPermission> -->
				
				
				<div >
					
						<input id="name" type="text" placeholder="请输入要查询的角色名称" width="500px" hight="200px"/>
						<button type="button" class="btn btn-success" onclick="show1()">查询</button>
					<div align="right" style="float:right;">
					<a href="javascript:void(0);" data-toggle="modal" data-target="#cz">
						<button type="button" class="btn btn-success">添加角色</button>
					</a>
					</div>
				</div>
				<div>
					<table class="table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>序号</th>
								<th>角色名称</th>
								<th>创建时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="mytable">
							
						</tbody>
					</table>
					
				</div>
			</div>
	
	<span style="font-family:'sans serif, tahoma, verdana, helvetica';"><span style="white-space:normal;"> </span></span>
		</section>
		<!-- 添加表单开始 -->
		<div class="modal fade" id="cz" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">角色添加</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" id="formId" method="post">
							<div class="form-group">
								<label for="mname" class="col-sm-3 control-label">角色名称:</label>
								<div class="col-sm-3">
									<input type="text" class="form-control" name="rolename">
								</div>
							</div>
							<div class="modal-footer ">
								<button type="button" class="btn btn-zdy"  id="submit">确定</button>
								<button type="button" class="btn btn-zdy"  id="qx" data-dismiss="modal">取消</button>
		
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- 添加表单结束 -->
		
		<!-- 编辑表单开始 -->
		<div class="modal fade" id="ee" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">角色编辑</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" id="editUI" method="post">
							<div class="form-group">
								<label for="mname" class="col-sm-3 control-label">角色名称:</label>
								<div class="col-sm-3">
									<input id="rolename" type="text" class="form-control" name="rolename">
									<input id="roleId" type="hidden" class="form-control" name="id">
								</div>
							</div>
							<div class="modal-footer ">
								<button type="button" class="btn btn-zdy"  id="sub">确定</button>
								<button type="button" class="btn btn-zdy"  id="qx1" data-dismiss="modal">取消</button>
		
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- 编辑表单结束 -->
		
		<!-- 查看表单开始 -->
		<div class="modal fade" id="ck" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">角色信息查看</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" id="showUI" method="post">
							<div class="form-group">
							
							  <label  class="col-sm-5 control-label">角色名称:</label>
								<div class="col-sm-3">
									<input id="rolename2" type="text" class="form-control" readonly="readonly" ><br>
								</div>
								
								<label  class="col-sm-5 control-label">创建时间:</label>
								<div class="col-sm-3">
									<input id="create_date" type="text" class="form-control" readonly="readonly" ><br>
								</div>
								
								<label  class="col-sm-5 control-label">修改时间:</label>
								<div class="col-sm-3">
									<input id="update_date" type="text" class="form-control" readonly="readonly">
								</div>
							</div>
							<div class="modal-footer ">
								<button type="button" class="btn btn-zdy"  data-dismiss="modal">返回</button>
		
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- 查看表单结束 -->
	</div>
	</div>
	</div>
	<!--/container-->
	<div class="clearfix"></div>

<script type="text/javascript">

CUI.use(['ajaxform','layer','utils'], function($ajax,$layer,$utils) {
	return {
		initialize : function() {
			var time = '<%=time %>';
			//页面加载完成，查询列表
			show();
			//保存角色
            new CuiAjaxForm($('#formId'), {
                submitSelector: $('#submit'),
                action: '${pageContext.request.contextPath}/member/formProcessing',
                beforeEvent: function(formData, jqForm, options) {
                    $.insertDynamicDataForTheForm(formData, jqForm, options, 'jdbcTemplateName','mysqlTemplate');
                    $.insertDynamicDataForTheForm(formData, jqForm, options, 'pFile','role');
                    $.insertDynamicDataForTheForm(formData, jqForm, options, 'pKey','add');
                    $.insertDynamicDataForTheForm(formData, jqForm, options, 'create_date',time);
                    return true;
                },
                callbackEvent: function(result) {
                    if (result.status == 'success') {
                    	$("#qx").click();
                    	//alert('添加成功');
                    	 var id = $("#cz").attr("rackId");
           				$("#cz").load("/RackLocations/kczt_refresh/" + id);
                    	location.reload();
                    }
                }
            });
            
            // 编辑完更新角色
            new CuiAjaxForm($('#editUI'), {
                submitSelector: $('#sub'),
                action: '${pageContext.request.contextPath}/member/formProcessing',
                beforeEvent: function(formData, jqForm, options) {
                    $.insertDynamicDataForTheForm(formData, jqForm, options, 'jdbcTemplateName','mysqlTemplate');
                    $.insertDynamicDataForTheForm(formData, jqForm, options, 'pFile','role');
                    $.insertDynamicDataForTheForm(formData, jqForm, options, 'pKey','update');
                    $.insertDynamicDataForTheForm(formData, jqForm, options, 'update_date',time);
                    return true;
                },
                callbackEvent: function(result) {
                    if (result.status == 'success') {
                    	$("#qx1").click();
                    	//alert('修改成功');
                    	location.reload();
                    }
                }
            });
		}
	}
});

//查询分页列表
function show(){
	$.ajax({
 			url : '${pageContext.request.contextPath}/member/pageSearch',
 			type : "post",
 			data : {
 				    "jdbcTemplateName":"mysqlTemplate",
 					"pFile":"role",
 					"pKey":"query",
 					"type":"mysql",
 					"pageNo":"1",
 					"pageSize":"10",
 					"order":"",
 					"queryTermStr":""
 			},
 			dataType : 'json',
 			async :true,
 			success : function(result) {
 				if (result.status === 'success') {
 				//后台返回的结果集，格式:
 					var pageSource = result.listInfo;
 					//下面写你的任何业务
 					//alert(JSON.stringify(pageSource));
 					//alert(pageSource.list[0].create_date);
 					$("#mytable").html("");
 					//$("#mytable").append("<tr><td>名称</td><td>密码</td><td>操作/td></tr>")
 					$(pageSource.list).each(function(index,element){
 						$("#mytable").append("<tr><td>"+index+"</td><td>"+element.rolename+"</td><td>"+
 								element.create_date+"</td><td><a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#ck\" onclick=\"kan('"+element.id+"')\">查看</a> | <a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#ee\" onclick=\"edit('"+element.id+"')\">编辑</a> | <a href=\"javascript:void(0)\" onclick=\"del('"+element.id+"')\">删除</a></td></tr>");
 					});
 					var options = {
 				            currentPage: 1,
 				            totalPages: 10,
 				        	numberOfPages:5
 				        }
 					$('#fy').bootstrapPaginator(options);
 				
 				}
 			},
 			error : function(XMLHttpRequest, textStatus, errorThrown) {
 				alert("查询失败");
 			}
 		});
}

//条件查询带分页
function show1(){
	var a = $('#name').val();
	$.ajax({
 			url : '${pageContext.request.contextPath}/member/pageSearch',
 			type : "post",
 			data : {
 				    "jdbcTemplateName":"mysqlTemplate",
 					"pFile":"role",
 					"pKey":"selectByRoleName",
 					"type":"mysql",
 					"pageNo":"1",
 					"pageSize":"10",
 					"order":"",
 					"queryTermStr":"",
 					"rolename":"%"+a+"%"
 			},
 			dataType : 'json',
 			async :true,
 			success : function(result) {
 				if (result.status === 'success') {
 				//后台返回的结果集，格式:
 					var pageSource = result.listInfo;
 					//下面写你的任何业务
 					//alert(JSON.stringify(pageSource));
 					//alert(pageSource.list[0].create_date);
 					$("#mytable").html("");
 					//$("#mytable").append("<tr><td>名称</td><td>密码</td><td>操作/td></tr>")
 					$(pageSource.list).each(function(index,element){
 						$("#mytable").append("<tr><td>"+(index+1)+"</td><td>"+element.rolename+"</td><td>"+
 								element.create_date+"</td><td><a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#ck\" onclick=\"kan('"+element.id+"')\">查看</a> | <a href=\"javascript:void(0)\" data-toggle=\"modal\" data-target=\"#ee\" onclick=\"edit('"+element.id+"')\">编辑</a> | <a href=\"javascript:void(0)\" onclick=\"del('"+element.id+"')\">删除</a></td></tr>");
 					});
 				}
 			},
 			error : function(XMLHttpRequest, textStatus, errorThrown) {
 				alert("查询失败");
 			}
 		});
}

//跳转编辑页面，回显数据
function edit(id){
	$.ajax({
		url : '${pageContext.request.contextPath}/member/baseQuery',
			type : "post",
			data : {
			"jdbcTemplateName":"mysqlTemplate",
				"pFile":"role",
				"pKey":"selectById",
				"type":"mysql",
				"id":id
			},
			dataType : 'json',
			async :true,
			success : function(result) {
				if (result.status === 'success') {
					//后台返回的结果集，格式[{"xx":"x"},{"xxx":"xx"},...]
					//[{id=0ca0b02f-8879-4a8c-a150-abd8aa8c4972, rolename=&#29992;&#25143;2, create_date=06/07/2017, update_date=null, reserved1=null, reserved2=null, reserved3=null}]
					var listInfo = result.listInfo;
					//alert(listInfo[0].toString());
					//下面写你的任何业务
					$("#rolename").val(listInfo[0].rolename);
					$("#roleId").val(listInfo[0].id);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("跳转编辑页面失败");
			}
		});
}

//查看角色
function kan(id){
	$.ajax({
		url : '${pageContext.request.contextPath}/member/baseQuery',
			type : "post",
			data : {
			"jdbcTemplateName":"mysqlTemplate",
				"pFile":"role",
				"pKey":"selectById",
				"type":"mysql",
				"id":id
			},
			dataType : 'json',
			async :true,
			success : function(result) {
				if (result.status === 'success') {
					//后台返回的结果集，格式[{"xx":"x"},{"xxx":"xx"},...]
					//[{id=0ca0b02f-8879-4a8c-a150-abd8aa8c4972, rolename=&#29992;&#25143;2, create_date=06/07/2017, update_date=null, reserved1=null, reserved2=null, reserved3=null}]
					var listInfo = result.listInfo;
					//alert(listInfo[0].toString());
					//下面写你的任何业务
					$("#rolename2").val(listInfo[0].rolename);
					$("#create_date").val(listInfo[0].create_date);
					$("#update_date").val(listInfo[0].update_date);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("跳转编辑页面失败");
			}
		});
}
//删除角色
function del(obj){
	 if(confirm("你确认要删除这个角色吗？")){
		 $.ajax({
				url : '${pageContext.request.contextPath}/role/del',
					type : "post",
					data : {
					"jdbcTemplateName":"mysqlTemplate",
						"pFile":"role",
						"pKey":"del",
						"type":"mysql",
						"id" : obj
					},
					dataType : 'json',
					async :true,
					success : function(result) {
						if (result.status === 'success') {
							//后台返回的结果集，格式[{"xx":"x"},{"xxx":"xx"},...]
							//var listInfo = result.listInfo;
							//下面写你的任何业务
							//alert(listInfo[0].username);
							alert("删除成功");
							show();
							
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("删除失败");
					}
				});
	 }
} 


$(function () {
    var carId = 1;
    $.ajax({
        url: "/OA/Setting/GetDate",
        datatype: 'json',
        type: "Post",
        data: "id=" + carId,
        success: function (data) {
            if (data != null) {
                $.each(eval("(" + data + ")").list, function (index, item) { //遍历返回的json
                    $("#list").append('<table id="data_table" class="table table-striped">');
                    $("#list").append('<thead>');
                    $("#list").append('<tr>');
                    $("#list").append('<th>Id</th>');
                    $("#list").append('<th>部门名称</th>');
                    $("#list").append('<th>备注</th>');
                    $("#list").append('<th>&nbsp;</th>');
                    $("#list").append('</tr>');
                    $("#list").append('</thead>');
                    $("#list").append('<tbody>');
                    $("#list").append('<tr>');
                    $("#list").append('<td>' + item.Id + '</td>');
                    $("#list").append('<td>' + item.Name + '</td>');
                    $("#list").append('<td>备注</td>');
                    $("#list").append('<td>');
                    $("#list").append('<button class="btn btn-warning" onclick="Edit(' + item.Id + ' );">修改</button>');
                    $("#list").append('<button class="btn btn-warning" onclick="Edit(' + item.Id + ' );">删除</button>');
                    $("#list").append('</td>');
                    $("#list").append('</tr>');
                    $("#list").append('</tbody>');

                    $("#list").append('<tr>');
                    $("#list").append('<td>内容</td>');
                    $("#list").append('<td>' + item.Message + '</td>');
                    $("#list").append('</tr>');
                    $("#list").append('</table>');
                });
                var pageCount = eval("(" + data + ")").pageCount; //取到pageCount的值(把返回数据转成object类型)
                var currentPage = eval("(" + data + ")").CurrentPage; //得到urrentPage
                var options = {
                    bootstrapMajorVersion: 2, //版本
                    currentPage: currentPage, //当前页数
                    totalPages: pageCount, //总页数
                    itemTexts: function (type, page, current) {
                        switch (type) {
                            case "first":
                                return "首页";
                            case "prev":
                                return "上一页";
                            case "next":
                                return "下一页";
                            case "last":
                                return "末页";
                            case "page":
                                return page;
                        }
                    },//点击事件，用于通过Ajax来刷新整个list列表
                    onPageClicked: function (event, originalEvent, type, page) {
                        $.ajax({
                            url: "/OA/Setting/GetDate?id=" + page,
                            type: "Post",
                            data: "page=" + page,
                            success: function (data1) {
                                if (data1 != null) {
                                    $.each(eval("(" + data + ")").list, function (index, item) { //遍历返回的json
                                        $("#list").append('<table id="data_table" class="table table-striped">');
                                        $("#list").append('<thead>');
                                        $("#list").append('<tr>');
                                        $("#list").append('<th>Id</th>');
                                        $("#list").append('<th>部门名称</th>');
                                        $("#list").append('<th>备注</th>');
                                        $("#list").append('<th>&nbsp;</th>');
                                        $("#list").append('</tr>');
                                        $("#list").append('</thead>');
                                        $("#list").append('<tbody>');
                                        $("#list").append('<tr>');
                                        $("#list").append('<td>' + item.Id + '</td>');
                                        $("#list").append('<td>' + item.Name + '</td>');
                                        $("#list").append('<td>备注</td>');
                                        $("#list").append('<td>');
                                        $("#list").append('<button class="btn btn-warning" onclick="Edit(' + item.Id + ' );">修改</button>');
                                        $("#list").append('<button class="btn btn-warning" onclick="Edit(' + item.Id + ' );">删除</button>');
                                        $("#list").append('</td>');
                                        $("#list").append('</tr>');
                                        $("#list").append('</tbody>');

                                        $("#list").append('<tr>');
                                        $("#list").append('<td>内容</td>');
                                        $("#list").append('<td>' + item.Message + '</td>');
                                        $("#list").append('</tr>');
                                        $("#list").append('</table>');
                                    });
                                }
                            }
                        });
                    }
                };
                $('#example').bootstrapPaginator(options);
            }
        }
    });
})
</script>
</body>
</html>