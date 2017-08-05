<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模板管理</title>
<script type="text/javascript" src="/mis-web/source/mvc/lib/cui.js"></script>
<script type="text/javascript"
	src="/mis-web/source/mvc/config/config.js"></script>
<script type="text/javascript" src="/mis-web/source/mvc/page/page.js"></script>
<link href="/mis-web/source/mvc/page/page.css" rel="stylesheet" />

<!-- ztree -->
<script type="text/javascript" src="/mis-web/source/mvc/tree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="/mis-web/source/mvc/tree/js/jquery.ztree.excheck.js"></script>
<link href="/mis-web/source/mvc/tree/css/zTreeStyle.css" rel="stylesheet" />

<style type="text/css">
.ztree li span.button.add {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle
}
</style>
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
					<label for="menu_name">模板名称：</label> 
					<input id="fileName" name="fileName" usemap="{'logic':'and','compare':'like'}" placeholder="请输入模板名称" /> 
					<button type="button" class="btn btn-zdy search" id="search">查询</button>
					<button type="reset" class="btn btn-zdy">重置</button>
				</div>
			</form>
			<div>
				<!-- 新增模板 -->
				<p class="send">
					<button class="btn btn-zdy" data-toggle="modal" data-target="#addcontent" onclick="addFileModel();">新增模板</button>
				</p>

				<!-- 文档列表 -->	
				<table class="table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>序号</th>
							<th>文档名称</th>
							<th>文档类别</th>
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

		<!-- 新增模板 -->
		<div class="modal fade" id="addcontent" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true">
			<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="ModalLabel">新增文档</h4>
				</div>

				<form id="formId" enctype="multipart/form-data"
					class="form-horizontal">
					<div class="form-group">
						<div style="float: left;">
							<a href="javascript: openAll();">open all</a> | <a href="javascript: closeAll();">close all</a>
							<div class="ztree" id="typeTree"></div>
							
							<div class="form-group">
							</div>
						</div>
					</div>




					<!-- <div class="form-group">
							<label for="contentname" class="col-sm-3 control-label">文档父类：</label>
							<div class="col-sm-4">
								<select id="s1" name="s1" onChange="clickOpt();"
									style="height: 33px; width: 100%; border-color: #e3e6f3;">
									<option value="0">请选择</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="contentname" class="col-sm-3 control-label">文档类别：</label>
							<div class="col-sm-4">
								<select id="s2" name="s2" onChange="clickOpt2();"
									style="height: 33px; width: 100%; border-color: #e3e6f3;">
									<option value="0">请选择</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="contentname" class="col-sm-3 control-label">上传文档：</label>
							<div class="col-sm-4">
								<input type="file" name="detail" id="detail" multiple="multiple">
							</div>
						</div> -->

						<div class="modal-footer ">
							<button type="button" class="btn btn-zdy" id="submit">确定</button>
							<button type="button" class="btn btn-zdy" data-dismiss="modal" id="miss">取消</button>
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
			//pageSearch(1);// 查询列表第一页数据
			//listSearch();// 查询select选择框数据
			$("#search").on("click",function() {
				pageSearch(1);
			});
			new CuiAjaxForm($('#formId'), {// 新增文档
				submitSelector : $('#submit'),
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
						$("#miss").click();// 关闭新增窗口
						pageSearch(1);
					}
				}
			});
		}// /.initialize
	}// /.return
});// /.CUI.use

function addFileModel() {
	selectModel();
}


//树属性的定义  
var setting = {
		//页面上的显示效果  
		view: {  
	        addHoverDom: addHoverDom, //当鼠标移动到节点上时，显示用户自定义控件  
	        removeHoverDom: removeHoverDom, //离开节点时的操作  
	        fontCss: getFontCss //个性化样式  
	    }, 
	    edit: {  
	        enable: true, //单独设置为true时，可加载修改、删除图标  
	        editNameSelectAll: true,  
	        showRemoveBtn: showRemoveBtn,  
	        showRenameBtn: showRenameBtn  
	    }, 
		/* check: {
			enable: true
		}, */
		data: {
			simpleData: {
				enable: true,
				idKey: "id",  
	           	pIdKey: "pId",  
	            system:"system",  
	            rootPId: ""
			}
		},
		callback: {  
	        onClick: zTreeOnClick, //单击事件  
	        onRemove: onRemove, //移除事件  
	        onRename: onRename //修改事件  
	    }
	};
	
function selectModel(fileModelId) {
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






 
function addHoverDom(treeId, treeNode) {  
    var sObj = $("#" + treeNode.tId + "_span"); //获取节点信息  
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;  
  
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='add node' onfocus='this.blur();'></span>"; //定义添加按钮  
    sObj.after(addStr); //加载添加按钮  
    var btn = $("#addBtn_"+treeNode.tId);  
  
    //绑定添加事件，并定义添加操作  
    if (btn) btn.bind("click", function(){  
        var zTree = $.fn.zTree.getZTreeObj("tree");  
        //将新节点添加到数据库中  
        var name='NewNode';  
        $.post('./index.php?r=data/addtree&pid='+treeNode.id+'&name='+name,function (data) {  
            var newID = data; //获取新添加的节点Id  
            zTree.addNodes(treeNode, {id:newID, pId:treeNode.id, name:name}); //页面上添加节点  
            var node = zTree.getNodeByParam("id", newID, null); //根据新的id找到新添加的节点  
            zTree.selectNode(node); //让新添加的节点处于选中状态  
        });  
    });  
};  
function removeHoverDom(treeId, treeNode) {  
    $("#addBtn_"+treeNode.tId).unbind().remove();  
};  
function onRename(e, treeId, treeNode, isCancel) {  
    //需要对名字做判定的，可以来这里写~~  
    $.post('./index.php?r=data/modifyname&id='+treeNode.id+'&name='+treeNode.name);  
}  
function onRemove(e, treeId, treeNode) {  
    //需要对删除做判定或者其它操作，在这里写~~  
    $.post('./index.php?r=data/del&id='+treeNode.id);  
}
function beforeRemove(treeId, treeNode) {  
    var zTree = $.fn.zTree.getZTreeObj("tree");  
    zTree.selectNode(treeNode);  
    return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");  
}
$(document).ready(function() {
	$.fn.zTree.init($("#typeTree"), setting);

});
	</script>
</html>