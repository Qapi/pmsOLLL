<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>入住人管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>入住人列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="resident" action="${ctx}/resident/resident/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>姓名：</span>
				<form:input path="name" htmlEscape="false" maxlength="32"  class=" form-control input-sm"/>
			<span>身份证号：</span>
				<form:input path="idNum" htmlEscape="false" maxlength="32"  class=" form-control input-sm"/>
			<span>手机号：</span>
				<form:input path="phone" htmlEscape="false" maxlength="16"  class=" form-control input-sm"/>
			<span>性别：</span>
				<form:input path="gender" htmlEscape="false" maxlength="2"  class=" form-control input-sm"/>
			<span>入住类型  1、入住  2、来访  3、长租：</span>
				<form:input path="residentType" htmlEscape="false" maxlength="2"  class=" form-control input-sm"/>
			<span>入住酒店：</span>
				<form:input path="hotel" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="resident:resident:add">
				<table:addRow url="${ctx}/resident/resident/form" title="入住人"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="resident:resident:edit">
			    <table:editRow url="${ctx}/resident/resident/form" title="入住人" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="resident:resident:del">
				<table:delRow url="${ctx}/resident/resident/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="resident:resident:import">
				<table:importExcel url="${ctx}/resident/resident/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="resident:resident:export">
	       		<table:exportExcel url="${ctx}/resident/resident/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column remarks">来访说明/目的</th>
				<th  class="sort-column name">姓名</th>
				<th  class="sort-column idNum">身份证号</th>
				<th  class="sort-column phone">手机号</th>
				<th  class="sort-column gender">性别</th>
				<th  class="sort-column homeAddress">家庭地址/居住地址</th>
				<th  class="sort-column emergencyContact">紧急联系人</th>
				<th  class="sort-column emergencyContactPhone">紧急联系人电话</th>
				<th  class="sort-column residentType">入住类型  1、入住  2、来访  3、长租</th>
				<th  class="sort-column leaveTime">来访离开时间</th>
				<th  class="sort-column hotel">入住酒店</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="resident">
			<tr>
				<td> <input type="checkbox" id="${resident.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看入住人', '${ctx}/resident/resident/form?id=${resident.id}','800px', '500px')">
					${resident.remarks}
				</a></td>
				<td>
					${resident.name}
				</td>
				<td>
					${resident.idNum}
				</td>
				<td>
					${resident.phone}
				</td>
				<td>
					${resident.gender}
				</td>
				<td>
					${resident.homeAddress}
				</td>
				<td>
					${resident.emergencyContact}
				</td>
				<td>
					${resident.emergencyContactPhone}
				</td>
				<td>
					${resident.residentType}
				</td>
				<td>
					${resident.leaveTime}
				</td>
				<td>
					${resident.hotel}
				</td>
				<td>
					<shiro:hasPermission name="resident:resident:view">
						<a href="#" onclick="openDialogView('查看入住人', '${ctx}/resident/resident/form?id=${resident.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="resident:resident:edit">
    					<a href="#" onclick="openDialog('修改入住人', '${ctx}/resident/resident/form?id=${resident.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="resident:resident:del">
						<a href="${ctx}/resident/resident/delete?id=${resident.id}" onclick="return confirmx('确认要删除该入住人吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>