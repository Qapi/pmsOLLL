<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>房型管理</title>
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
		<h5>房型列表 </h5>
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
	<form:form id="searchForm" modelAttribute="roomType" action="${ctx}/roomtype/roomType/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>房型名称：</span>
				<form:input path="name" htmlEscape="false" maxlength="32"  class=" form-control input-sm"/>
			<span>床型：</span>
			<form:select path="bedType" class="form-control ">
				<form:option value=""></form:option>
				<form:options items="${fns:getDictList('bed_type')}"
						  itemLabel="description" itemValue="value" htmlEscape="false" />
			</form:select>
		    <%--<span>可入住人数：</span>--%>
				<%--<form:input path="capacity" htmlEscape="false" maxlength="8"  class=" form-control input-sm"/>--%>
			<%--<span>平日价：</span>--%>
				<%--<form:input path="dailyPrice" htmlEscape="false" maxlength="32"  class=" form-control input-sm"/>--%>
			<%--<span>节假日价：</span>--%>
				<%--<form:input path="holidayPrice" htmlEscape="false" maxlength="32"  class=" form-control input-sm"/>--%>
			<%--<span>钟点房价：</span>--%>
				<%--<form:input path="hourPrice" htmlEscape="false" maxlength="32"  class=" form-control input-sm"/>--%>
			<%--<span>月租价：</span>--%>
				<%--<form:input path="monthlyRent" htmlEscape="false" maxlength="32"  class=" form-control input-sm"/>--%>
			<span>床位数：</span>
				<form:input path="bedNum" htmlEscape="false" maxlength="8"  class=" form-control input-sm"/>
			<span>所属酒店：</span>
			<form:select path="hotel.id" class="form-control ">
				<form:options items="${hotels}"
							  itemLabel="office.name" itemValue="id" htmlEscape="false" />
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
			<shiro:hasPermission name="roomtype:roomType:add">
				<table:addRow url="${ctx}/roomtype/roomType/form" title="房型"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="roomtype:roomType:edit">
			    <table:editRow url="${ctx}/roomtype/roomType/form" title="房型" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="roomtype:roomType:del">
				<table:delRow url="${ctx}/roomtype/roomType/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="roomtype:roomType:import">
				<table:importExcel url="${ctx}/roomtype/roomType/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="roomtype:roomType:export">
	       		<table:exportExcel url="${ctx}/roomtype/roomType/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column name">房型名称</th>
				<%--<th  class="sort-column capacity">可入住人数</th>--%>
				<th  class="sort-column dailyPrice">平日价</th>
				<th  class="sort-column weekendPrice">周末价</th>
				<th  class="sort-column holidayPrice">节日价</th>
				<th  class="sort-column hourPrice">钟点价</th>
				<th  class="sort-column monthlyRent">月租价</th>
				<th  class="sort-column bedNum">床位数</th>
				<th  class="sort-column hotel_id">所属酒店</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="roomType">
			<tr>
				<td> <input type="checkbox" id="${roomType.id}" class="i-checks"></td>
				<%--<td><a  href="#" onclick="openDialogView('查看房型', '${ctx}/roomtype/roomType/form?id=${roomType.id}','800px', '500px')">--%>
					<%--${roomType.remarks}--%>
				<%--</a></td>--%>
				<td>
					${roomType.name}
				</td>
				<%--<td>--%>
					<%--${roomType.capacity}--%>
				<%--</td>--%>
				<td>
					${roomType.dailyPrice}
				</td>
				<td>
					${roomType.weekendPrice}
				</td>
				<td>
					${roomType.holidayPrice}
				</td>
				<td>
					${roomType.hourPrice}
				</td>
				<td>
					${roomType.monthlyRent}
				</td>
				<td>
					${roomType.bedNum}
				</td>
				<td>
					${roomType.hotel.office.name}
				</td>
				<td>
					<shiro:hasPermission name="roomtype:roomType:view">
						<a href="#" onclick="openDialogView('查看房型', '${ctx}/roomtype/roomType/form?id=${roomType.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="roomtype:roomType:edit">
    					<a href="#" onclick="openDialog('修改房型', '${ctx}/roomtype/roomType/form?id=${roomType.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="roomtype:roomType:del">
						<a href="${ctx}/roomtype/roomType/delete?id=${roomType.id}" onclick="return confirmx('确认要删除该房型吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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