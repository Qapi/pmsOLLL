<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>房间管理</title>
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
		<h5>房间列表 </h5>
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
	<form:form id="searchForm" modelAttribute="room" action="${ctx}/room/room/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>房间号：</span>
				<form:input path="roomNum" htmlEscape="false" maxlength="16"  class=" form-control input-sm"/>
			<%--<span>房间名：</span>--%>
				<%--<form:input path="topicName" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>--%>
			<span>所属酒店：</span>
                <form:select path="hotel.id" class="form-control ">
                    <form:option value=""></form:option>
                    <form:options items="${hotels}" itemLabel="office.name" itemValue="id" htmlEscape="false"/>
                </form:select>
            <span>楼层：</span>
				<form:input path="floorNum" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<%--<span>户型：</span>--%>
				<%--<form:input path="layout" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>--%>
			<span>房型：</span>
                <form:select path="roomType.id" class="form-control ">
                    <form:option value=""></form:option>
                    <form:options items="${roomTypes}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                </form:select>
			<span>状态：</span>
                <form:select path="status" class="form-control ">
                    <form:option value=""></form:option>
                    <form:options items="${fns:getDictList('room_status')}"
                                  itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
        </div>
	</form:form>
	<br/>
	</div>
	</div>

	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="room:room:add">
				<table:addRow url="${ctx}/room/room/form" title="房间"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="room:room:edit">
			    <table:editRow url="${ctx}/room/room/form" title="房间" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="room:room:del">
				<table:delRow url="${ctx}/room/room/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="room:room:import">
				<table:importExcel url="${ctx}/room/room/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="room:room:export">
	       		<table:exportExcel url="${ctx}/room/room/export"></table:exportExcel><!-- 导出按钮 -->
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
				<%--<th  class="sort-column remarks">备注信息</th>--%>
				<th  class="sort-column roomNum">房间号</th>
				<th  class="sort-column topicName">房间名</th>
				<th  class="sort-column hotel">所属酒店</th>
				<th  class="sort-column floorNum">楼层</th>
				<th  class="sort-column layout">户型</th>
				<th  class="sort-column roomType">房型</th>
				<th  class="sort-column bedType">床型</th>
				<th  class="sort-column status">状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="room">
			<tr>
				<td> <input type="checkbox" id="${room.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看房间', '${ctx}/room/room/form?id=${room.id}','800px', '500px')">
					${room.roomNum}
				</a></td>
				<td>
					${room.topicName}
				</td>
				<td>
				    ${room.hotel.office.name}
				</td>
				<td>
					${room.floorNum}
				</td>
				<td>
					${room.layout}
				</td>
				<td>
					${room.roomType.name}
				</td>
				<td>
				    ${fns:getDictLabel(room.roomType.bedType,'bed_type', '')}
				</td>
				<td>
				    ${fns:getDictLabel(room.status,'room_status', '')}
				</td>
				<td>
					<shiro:hasPermission name="room:room:view">
						<a href="#" onclick="openDialogView('查看房间', '${ctx}/room/room/form?id=${room.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="room:room:edit">
    					<a href="#" onclick="openDialog('修改房间', '${ctx}/room/room/form?id=${room.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="room:room:del">
						<a href="${ctx}/room/room/delete?id=${room.id}" onclick="return confirmx('确认要删除该房间吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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