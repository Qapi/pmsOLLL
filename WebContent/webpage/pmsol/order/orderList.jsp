<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			laydate({
	            elem: '#bookTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#beginCheckInDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endCheckInDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>订单列表 </h5>
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
	<form:form id="searchForm" modelAttribute="order" action="${ctx}/order/order/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>备注信息：</span>
				<form:input path="remarks" htmlEscape="false" maxlength="255"  class=" form-control input-sm"/>
			<span>订单号：</span>
				<form:input path="orderNum" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>所属渠道：</span>
				<form:input path="channelId" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>所属酒店：</span>
				<form:input path="hotelId" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>所属房型：</span>
				<form:input path="roomTypeId" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>租期：</span>
				<form:input path="leaseMode" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>长租月数：</span>
				<form:input path="rentMonths" htmlEscape="false" maxlength="16"  class=" form-control input-sm"/>
			<span>预订时间：</span>
				<input id="bookTime" name="bookTime" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${order.bookTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<span>入住日期：</span>
				<input id="beginCheckInDate" name="beginCheckInDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${order.beginCheckInDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> - 
				<input id="endCheckInDate" name="endCheckInDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${order.endCheckInDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<span>联系人：</span>
				<form:input path="contacts" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>联系人电话：</span>
				<form:input path="contactsPhone" htmlEscape="false" maxlength="32"  class=" form-control input-sm"/>
			<span>预约房间：</span>
				<form:input path="bookRoomId" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>状态：</span>
				<form:input path="status" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="order:order:add">
				<table:addRow url="${ctx}/order/order/form" title="订单"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="order:order:edit">
			    <table:editRow url="${ctx}/order/order/form" title="订单" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="order:order:del">
				<table:delRow url="${ctx}/order/order/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="order:order:import">
				<table:importExcel url="${ctx}/order/order/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="order:order:export">
	       		<table:exportExcel url="${ctx}/order/order/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column orderNum">订单号</th>
				<th  class="sort-column channelId">所属渠道</th>
				<th  class="sort-column hotelId">所属酒店</th>
				<th  class="sort-column roomTypeId">所属房型</th>
				<th  class="sort-column leaseMode">租期</th>
				<th  class="sort-column rentMonths">长租月数</th>
				<th  class="sort-column bookTime">预订时间</th>
				<th  class="sort-column checkInDate">入住日期</th>
				<th  class="sort-column contacts">联系人</th>
				<th  class="sort-column contactsPhone">联系人电话</th>
				<th  class="sort-column memberId">预订人id</th>
				<th  class="sort-column bookRoomId">预约房间</th>
				<th  class="sort-column status">状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="order">
			<tr>
				<td> <input type="checkbox" id="${order.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看订单', '${ctx}/order/order/form?id=${order.id}','800px', '500px')">
					${order.remarks}
				</a></td>
				<td>
					${order.orderNum}
				</td>
				<td>
					${order.channelId}
				</td>
				<td>
					${order.hotelId}
				</td>
				<td>
					${order.roomTypeId}
				</td>
				<td>
					${order.leaseMode}
				</td>
				<td>
					${order.rentMonths}
				</td>
				<td>
					${order.bookTime}
				</td>
				<td>
					${order.checkInDate}
				</td>
				<td>
					${order.contacts}
				</td>
				<td>
					${order.contactsPhone}
				</td>
				<td>
					${order.memberId}
				</td>
				<td>
					${order.bookRoomId}
				</td>
				<td>
					${order.status}
				</td>
				<td>
					<shiro:hasPermission name="order:order:view">
						<a href="#" onclick="openDialogView('查看订单', '${ctx}/order/order/form?id=${order.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="order:order:edit">
    					<a href="#" onclick="openDialog('修改订单', '${ctx}/order/order/form?id=${order.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="order:order:del">
						<a href="${ctx}/order/order/delete?id=${order.id}" onclick="return confirmx('确认要删除该订单吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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