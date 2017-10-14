<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>订单管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            laydate.render({
                elem: '#checkInDate',
                event: 'focus',
                min: 0,
                theme: 'molv',
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
                    <form:form id="searchForm" modelAttribute="order" action="${ctx}/order/order/arriveAtToday" method="post"
                               class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">
                            <span>订单号：</span>
                            <form:input path="orderNum" htmlEscape="false" maxlength="64"
                                        class=" form-control input-sm"/>
                            <span>所属房型：</span>
                            <form:select path="roomType.id" class="form-control ">
                                <form:option value=""></form:option>
                                <form:options items="${roomTypes}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                            </form:select>
                            <span>入住人：</span>
                            <form:input path="contacts" htmlEscape="false" maxlength="64"
                                        class=" form-control input-sm"/>
                            <span>入住人电话：</span>
                            <form:input path="contactsPhone" htmlEscape="false" maxlength="32"
                                        class=" form-control input-sm"/>
                        </div>
                        <div class="col-sm-12">
                            <span>入住日期：</span>
                            <input id="checkInDate" name="checkInDate" type="text" maxlength="20"
                                   class=" form-control layer-date input-sm"
                                   value="<fmt:formatDate value="${order.checkInDate}" pattern="yyyy-MM-dd"/>"/>
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
                        <shiro:hasPermission name="order:order:add">
                            <table:addRow url="${ctx}/order/order/form" title="订单"></table:addRow><!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="order:order:edit">
                            <table:editRow url="${ctx}/order/order/form" title="订单"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="order:order:del">
                            <table:delRow url="${ctx}/order/order/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="order:order:import">
                            <table:importExcel url="${ctx}/order/order/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="order:order:export">
                            <table:exportExcel url="${ctx}/order/order/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th class="sort-column orderNum">订单号</th>
                    <%--<th class="sort-column hotel">所属酒店</th>--%>
                    <th class="sort-column channel_id">渠道</th>
                    <th class="sort-column roomType_id">房型</th>
                    <th class="sort-column leaseMode">租赁方式</th>
                    <%--<th class="sort-column rentMonths">长租月数</th>--%>
                    <th class="sort-column checkInDate">入住日期</th>
                    <th class="sort-column checkOutDate">离店日期</th>
                    <th class="sort-column contacts">入住人</th>
                    <th class="sort-column contactsPhone">入住人电话</th>
                    <%--<th class="sort-column booker">预订人</th>--%>
                    <%--<th class="sort-column bookRoom">预约房间</th>--%>
                    <th class="sort-column status">状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="order">
                    <tr>
                        <td><input type="checkbox" id="${order.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看订单', '${ctx}/order/order/form?id=${order.id}','800px', '500px')">
                                ${order.orderNum}
                        </a></td>
                        <%--<td>--%>
                                <%--${order.hotel.office.name}--%>
                        <%--</td>--%>
                        <td>
                                ${order.channel.name}
                        </td>
                        <td>
                                ${order.roomType.name}
                        </td>
                        <td>
                                ${fns:getDictLabel(order.leaseMode,'lease_mode','')}
                        </td>
                        <%--<td>--%>
                                <%--${order.rentMonths}--%>
                        <%--</td>--%>
                        <td>
                                ${fns:formatDate(order.checkInDate)}
                        </td>
                        <td>
                                ${fns:formatDate(order.checkOutDate)}
                        </td>
                        <td>
                                ${order.contacts}
                        </td>
                        <td>
                                ${order.contactsPhone}
                        </td>
                        <%--<td>--%>
                                <%--${order.booker.name}--%>
                        <%--</td>--%>
                        <%--<td>--%>
                                <%--${order.bookRoom.roomNum}--%>
                        <%--</td>--%>
                        <td>
                                ${fns:getDictLabel(order.status,'order_status','')}
                        </td>
                        <td>
                            <shiro:hasPermission name="order:order:view">
                                <a href="#"
                                   onclick="openDialogView('查看订单', '${ctx}/order/order/form?id=${order.id}','800px', '500px')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="order:order:edit">
                                <c:if test="${'0' eq order.status}">
                                    <a href="#"
                                       onclick="openDialog('修改订单', '${ctx}/order/order/form?id=${order.id}','800px', '500px')"
                                       class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                                </c:if>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="order:order:del">
                                <a href="${ctx}/order/order/delete?id=${order.id}"
                                   onclick="return confirmx('确认要删除该订单吗？', this.href)" class="btn btn-danger btn-xs"><i
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