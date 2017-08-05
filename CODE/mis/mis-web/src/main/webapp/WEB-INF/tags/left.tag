<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="currentNav" type="java.lang.String" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- Sidebar -->
				<div class="sidebar">
					<div class="sidebar-collapse">
						<!-- Sidebar Header Logo-->
						<div class="sidebar-header">
							<!-- <img src="#" class="img-responsive" alt="" /> 放logo的地方--> 
						</div>
						<!-- Sidebar Menu-->
						<div class="sidebar-menu">	 					
							<nav id="menu" class="nav-main" role="navigation">
								<ul class="nav nav-sidebar" id="sidebar">
									<div class="panel-body text-center">
										<div class="flag">
											<!--<img src="assets/img/flags/USA.png" class="img-flags" alt="" />-->
										</div>
									</div>
								</ul>
							</nav>
						</div>
<script type="text/javascript" src="${ctx}/source/mvc/lib/cui.js"></script>
<script type="text/javascript" src="${ctx}/source/mvc/config/config.js"></script>
	<script type="text/javascript">

 	CUI.use(['ajaxform','utils','layer'], function($ajax,$utils,$layer) {
		return {
			initialize : function() {
 			 	var _this = this; 
 			 	var user_info =  ${sessionScope.UserInfo};
				_this.baseQuery(user_info.id);
			},
			baseQuery:function(ts_user_id){
				$.asyncRequest({
					url :'../member/baseQuery',
						data : {
							"jdbcTemplateName":"mysqlTemplate",
							"pFile":"menu",
							"pKey":"select_menu",
							"ts_user_id":ts_user_id
						},
						event : function(result) {
							if (result.status === 'success') {
								var pageSource = result.listInfo;
								var menulv1 = "";
								var menulv2 = "";
								$(pageSource).each(function(index,element){
									if(element.parent_id == '0'){
										menulv1 = menulv1 + "<li id=\""+element.id+"\" class=\"nav-parent \"> <a href=\"javascript:void(0);\" > <i  class=\"fa fa-tasks\" aria-hidden=\"true\"></i><span>"+element.menu_name+"</span> </a><ul class=\"nav nav-children\" id=\"sub"+element.id+"\"></ul></li>";
									}
								});
								$('#menu').find("#sidebar").append(menulv1);
								$(pageSource).each(function(index,element){
									if(element.parent_id != '0'){
										menulv2 = " <li><a href=\"javascript:void(0);\" onclick=\"redirect('"+element.url+"')\"   ><span class=\"text\"> "+element.menu_name+"</span></a></li> ";
										$('#menu').find("#sub"+element.parent_id).append(menulv2);
									}
								});
								
							}
						},errorFn:function(){
							layer.alert('列表查询出错!');
						}
					});
			}
		}
	});

	function redirect(menu){
		$('#page').load( "../pages/html/"+menu+".jsp" );
	}
	
	</script>
						<!-- End Sidebar Menu-->
