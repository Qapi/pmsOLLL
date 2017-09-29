<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>酒店员工管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			laydate({
	            elem: '#birthday', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#beginContractPeriod', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endContractPeriod', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>酒店员工列表 </h5>
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
	<form:form id="searchForm" modelAttribute="staff" action="${ctx}/staff/staff/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>备注信息：</span>
				<form:input path="remarks" htmlEscape="false" maxlength="255"  class=" form-control input-sm"/>
			<span>姓名：</span>
				<form:input path="name" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>手机号：</span>
				<form:input path="phone" htmlEscape="false" maxlength="32"  class=" form-control input-sm"/>
			<span>所属酒店：</span>
				<form:input path="hotelId" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>身份证号：</span>
				<form:input path="idNum" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>生日：</span>
				<input id="birthday" name="birthday" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${staff.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<span>性别：</span>
				<form:input path="gender" htmlEscape="false" maxlength="8"  class=" form-control input-sm"/>
			<span>家庭地址：</span>
				<form:input path="homeAddress" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>所属部门：</span>
				<sys:treeselect id="departmentId" name="departmentId" value="${staff.departmentId}" labelName="" labelValue="${staff.}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			<span>所属岗位：</span>
				<form:input path="postId" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>合同期：</span>
				<input id="beginContractPeriod" name="beginContractPeriod" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${staff.beginContractPeriod}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> - 
				<input id="endContractPeriod" name="endContractPeriod" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${staff.endContractPeriod}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
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
			<shiro:hasPermission name="staff:staff:add">
				<table:addRow url="${ctx}/staff/staff/form" title="酒店员工"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="staff:staff:edit">
			    <table:editRow url="${ctx}/staff/staff/form" title="酒店员工" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="staff:staff:del">
				<table:delRow url="${ctx}/staff/staff/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="staff:staff:import">
				<table:importExcel url="${ctx}/staff/staff/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="staff:staff:export">
	       		<table:exportExcel url="${ctx}/staff/staff/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column name">姓名</th>
				<th  class="sort-column phone">手机号</th>
				<th  class="sort-column hotelId">所属酒店</th>
				<th  class="sort-column idNum">身份证号</th>
				<th  class="sort-column birthday">生日</th>
				<th  class="sort-column gender">性别</th>
				<th  class="sort-column homeAddress">家庭地址</th>
				<th  class="sort-column ">所属部门</th>
				<th  class="sort-column postId">所属岗位</th>
				<th  class="sort-column contractPeriod">合同期</th>
				<th  class="sort-column status">状态</th>
				<th  class="sort-column userId">用户id</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="staff">
			<tr>
				<td> <input type="checkbox" id="${staff.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看酒店员工', '${ctx}/staff/staff/form?id=${staff.id}','800px', '500px')">
					${staff.remarks}
				</a></td>
				<td>
					${staff.name}
				</td>
				<td>
					${staff.phone}
				</td>
				<td>
					${staff.hotelId}
				</td>
				<td>
					${staff.idNum}
				</td>
				<td>
					${staff.birthday}
				</td>
				<td>
					${staff.gender}
				</td>
				<td>
					${staff.homeAddress}
				</td>
				<td>
					${staff.}
				</td>
				<td>
					${staff.postId}
				</td>
				<td>
					${staff.contractPeriod}
				</td>
				<td>
					${staff.status}
				</td>
				<td>
					${staff.userId}
				</td>
				<td>
					<shiro:hasPermission name="staff:staff:view">
						<a href="#" onclick="openDialogView('查看酒店员工', '${ctx}/staff/staff/form?id=${staff.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="staff:staff:edit">
    					<a href="#" onclick="openDialog('修改酒店员工', '${ctx}/staff/staff/form?id=${staff.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="staff:staff:del">
						<a href="${ctx}/staff/staff/delete?id=${staff.id}" onclick="return confirmx('确认要删除该酒店员工吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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