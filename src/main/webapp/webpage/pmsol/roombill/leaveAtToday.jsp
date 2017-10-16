<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>房单管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            laydate.render({
                elem: '#checkOutDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus', //响应事件。如果没有传入event，则按照默认的click
                min: 0
            });
        });
    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-title">
            <h5>房单列表 </h5>
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
                    <form:form id="searchForm" modelAttribute="roomBill" action="${ctx}/roombill/roomBill/leaveAtToday"
                               method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">
                            <span>订单号：</span>
                            <form:input path="order.orderNum" htmlEscape="false" maxlength="64"
                                        class=" form-control input-sm"/>
                            <span>入住房间：</span>
                            <form:input path="room.id" htmlEscape="false" maxlength="64"
                                        class=" form-control input-sm"/>
                            <span>离店时间：</span>
                            <input id="checkOutDate" name="order.checkOutDate" type="text" maxlength="20"
                                   class="form-control layer-date input-sm"
                                   value="<fmt:formatDate value="${roomBill.order.checkOutDate}" pattern="yyyy-MM-dd"/>"/>
                            <span>所属酒店：</span>
                            <form:select path="hotel.id" class="form-control ">
                                <form:option value=""></form:option>
                                <form:options items="${hotels}" itemLabel="office.name" itemValue="id"
                                              htmlEscape="false"/>
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
                        <shiro:hasPermission name="roombill:roomBill:add">
                            <table:addRow url="${ctx}/roombill/roomBill/form" title="房单"></table:addRow><!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="roombill:roomBill:edit">
                            <table:editRow url="${ctx}/roombill/roomBill/form" title="房单"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="roombill:roomBill:del">
                            <table:delRow url="${ctx}/roombill/roomBill/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="roombill:roomBill:import">
                            <table:importExcel url="${ctx}/roombill/roomBill/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="roombill:roomBill:export">
                            <table:exportExcel url="${ctx}/roombill/roomBill/export"></table:exportExcel><!-- 导出按钮 -->
                        </shiro:hasPermission>
                        <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left"
                                onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新
                        </button>

                    </div>
                    <div class="pull-right">
                        <button class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()"><i
                                class="fa fa-search"></i> 查询
                        </button>
                        <button class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()"><i
                                class="fa fa-refresh"></i> 重置
                        </button>
                    </div>
                </div>
            </div>

            <!-- 表格 -->
            <table id="contentTable"
                   class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                <thead>
                <tr>
                    <th><input type="checkbox" class="i-checks"></th>
                    <th class="sort-column room_id">入住房间</th>
                    <th class="sort-column order_id">订单号</th>
                    <th class="sort-column checkInTime">入住时间</th>
                    <th class="sort-column checkOutTime">预离时间</th>
                    <th class="sort-column depositAmount">押金金额</th>
                    <th class="sort-column receivableAmount">应收金额</th>
                    <th class="sort-column netreceiptsAmount">实收金额</th>
                    <th class="sort-column unreceivedAmount">未收金额</th>
                    <th class="sort-column hotel_id">所属酒店</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="roomBill">
                    <tr>
                        <td><input type="checkbox" id="${roomBill.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看房单', '${ctx}/roombill/roomBill/form?id=${roomBill.id}','800px', '500px')">
                                ${roomBill.room.roomNum}
                        </a></td>
                        <td>
                                ${roomBill.order.orderNum}
                        </td>
                        <td>
                                ${roomBill.checkInTime}
                        </td>
                        <td>
                                ${roomBill.order.checkOutDate}
                        </td>
                        <td>
                                ${roomBill.depositAmount}
                        </td>
                        <td>
                                ${roomBill.receivableAmount}
                        </td>
                        <td>
                                ${roomBill.netreceiptsAmount}
                        </td>
                        <td>
                                ${roomBill.unreceivedAmount}
                        </td>
                        <td>
                                ${roomBill.hotel.office.name}
                        </td>
                        <td>
                            <shiro:hasPermission name="roombill:roomBill:view">
                                <a href="#"
                                   onclick="openDialogView('查看房单', '${ctx}/roombill/roomBill/form?id=${roomBill.id}','800px', '500px')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="roombill:roomBill:edit">
                                <a href="#"
                                   onclick="openDialog('修改房单', '${ctx}/roombill/roomBill/form?id=${roomBill.id}','800px', '500px')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="roombill:roomBill:del">
                                <a href="${ctx}/roombill/roomBill/delete?id=${roomBill.id}"
                                   onclick="return confirmx('确认要删除该房单吗？', this.href)" class="btn btn-danger btn-xs"><i
                                        class="fa fa-trash"></i> 删除</a>
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