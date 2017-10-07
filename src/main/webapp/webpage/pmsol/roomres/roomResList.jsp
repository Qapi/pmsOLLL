<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>房间资源管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
	        laydate({
	            elem: '#beginResDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endResDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>房间资源列表 </h5>
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
	<form:form id="searchForm" modelAttribute="roomRes" action="${ctx}/roomres/roomRes/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>备注信息：</span>
				<form:input path="remarks" htmlEscape="false" maxlength="255"  class=" form-control input-sm"/>
			<span>房间号：</span>
				<form:input path="roomNum" htmlEscape="false" maxlength="8"  class=" form-control input-sm"/>
			<span>房间id：</span>
				<form:input path="roomId" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>所属酒店：</span>
				<form:input path="hotel" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>所属房型：</span>
				<form:input path="roomType" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>日期：</span>
				<input id="beginResDate" name="beginResDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${roomRes.beginResDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> - 
				<input id="endResDate" name="endResDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${roomRes.endResDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="roomres:roomRes:add">
				<table:addRow url="${ctx}/roomres/roomRes/form" title="房间资源"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="roomres:roomRes:edit">
			    <table:editRow url="${ctx}/roomres/roomRes/form" title="房间资源" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="roomres:roomRes:del">
				<table:delRow url="${ctx}/roomres/roomRes/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="roomres:roomRes:import">
				<table:importExcel url="${ctx}/roomres/roomRes/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="roomres:roomRes:export">
	       		<table:exportExcel url="${ctx}/roomres/roomRes/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column remarks">备注信息</th>
				<th  class="sort-column roomNum">房间号</th>
				<th  class="sort-column roomId">房间id</th>
				<th  class="sort-column hotel">所属酒店</th>
				<th  class="sort-column roomType">所属房型</th>
				<th  class="sort-column resDate">日期</th>
				<th  class="sort-column status">状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="roomRes">
			<tr>
				<td> <input type="checkbox" id="${roomRes.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看房间资源', '${ctx}/roomres/roomRes/form?id=${roomRes.id}','800px', '500px')">
					${roomRes.remarks}
				</a></td>
				<td>
					${roomRes.roomNum}
				</td>
				<td>
					${roomRes.roomId}
				</td>
				<td>
					${roomRes.hotel}
				</td>
				<td>
					${roomRes.roomType}
				</td>
				<td>
					${roomRes.resDate}
				</td>
				<td>
					${roomRes.status}
				</td>
				<td>
					<shiro:hasPermission name="roomres:roomRes:view">
						<a href="#" onclick="openDialogView('查看房间资源', '${ctx}/roomres/roomRes/form?id=${roomRes.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="roomres:roomRes:edit">
    					<a href="#" onclick="openDialog('修改房间资源', '${ctx}/roomres/roomRes/form?id=${roomRes.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="roomres:roomRes:del">
						<a href="${ctx}/roomres/roomRes/delete?id=${roomRes.id}" onclick="return confirmx('确认要删除该房间资源吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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