<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>会员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			laydate({
	            elem: '#birthday', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>会员列表 </h5>
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
	<form:form id="searchForm" modelAttribute="member" action="${ctx}/member/member/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>昵称：</span>
				<form:input path="nickName" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>所属酒店：</span>
				<form:input path="hotelId" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>身份证号：</span>
				<form:input path="idNum" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>生日：</span>
				<input id="birthday" name="birthday" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${member.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<span>手机号：</span>
				<form:input path="phone" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>紧急联系人：</span>
				<form:input path="emergencyContact" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>紧急联系人电话：</span>
				<form:input path="emergencyContactPhone" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>会员号：</span>
				<form:input path="memberNum" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>所属会员等级：</span>
				<form:input path="menberLevelId" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
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
			<shiro:hasPermission name="member:member:add">
				<table:addRow url="${ctx}/member/member/form" title="会员"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="member:member:edit">
			    <table:editRow url="${ctx}/member/member/form" title="会员" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="member:member:del">
				<table:delRow url="${ctx}/member/member/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="member:member:import">
				<table:importExcel url="${ctx}/member/member/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="member:member:export">
	       		<table:exportExcel url="${ctx}/member/member/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column nickName">昵称</th>
				<th  class="sort-column hotelId">所属酒店</th>
				<th  class="sort-column idNum">身份证号</th>
				<th  class="sort-column birthday">生日</th>
				<th  class="sort-column homeAddress">家庭地址</th>
				<th  class="sort-column phone">手机号</th>
				<th  class="sort-column emergencyContact">紧急联系人</th>
				<th  class="sort-column emergencyContactPhone">紧急联系人电话</th>
				<th  class="sort-column memberNum">会员号</th>
				<th  class="sort-column menberLevelId">所属会员等级</th>
				<th  class="sort-column status">状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="member">
			<tr>
				<td> <input type="checkbox" id="${member.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看会员', '${ctx}/member/member/form?id=${member.id}','800px', '500px')">
					${member.nickName}
				</a></td>
				<td>
					${member.hotelId}
				</td>
				<td>
					${member.idNum}
				</td>
				<td>
					${member.birthday}
				</td>
				<td>
					${member.homeAddress}
				</td>
				<td>
					${member.phone}
				</td>
				<td>
					${member.emergencyContact}
				</td>
				<td>
					${member.emergencyContactPhone}
				</td>
				<td>
					${member.memberNum}
				</td>
				<td>
					${member.menberLevelId}
				</td>
				<td>
					${member.status}
				</td>
				<td>
					<shiro:hasPermission name="member:member:view">
						<a href="#" onclick="openDialogView('查看会员', '${ctx}/member/member/form?id=${member.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="member:member:edit">
    					<a href="#" onclick="openDialog('修改会员', '${ctx}/member/member/form?id=${member.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="member:member:del">
						<a href="${ctx}/member/member/delete?id=${member.id}" onclick="return confirmx('确认要删除该会员吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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