<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>酒店销售渠道管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            laydate.render({
                elem: '#beginContractPeriod',
                event: 'focus',
                theme: 'molv',
                done: (value, date, endDate) => {
                    laydate.render({
                        elem: '#endContractPeriod',
                        event: 'focus',
                        min: value,
                        theme: 'molv'
                    });
                }
            });
        });
    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-title">
            <h5>酒店销售渠道列表 </h5>
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
                    <form:form id="searchForm" modelAttribute="hotelChannel" action="${ctx}/hotelchannel/hotelChannel/"
                               method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">
                            <span>名称：</span>
                            <form:input path="name" htmlEscape="false" maxlength="64" class=" form-control input-sm"/>
                            <span>所属酒店：</span>
                            <form:select path="hotel.id" class="form-control ">
                                <form:option value=""></form:option>
                                <form:options items="${hotels}" itemLabel="office.name" itemValue="id"
                                              htmlEscape="false"/>
                            </form:select>
                                <%--<span>联系人：</span>--%>
                                <%--<form:input path="contacts" htmlEscape="false" maxlength="64"--%>
                                <%--class=" form-control input-sm"/>--%>
                                <%--<span>联系人电话：</span>--%>
                                <%--<form:input path="contactsPhone" htmlEscape="false" maxlength="32"--%>
                                <%--class=" form-control input-sm"/>--%>
                            <span>合同期：</span>
                            <input id="beginContractPeriod" name="beginContractPeriod" type="text" maxlength="20"
                                   class="form-control layer-date input-sm"
                                   value="<fmt:formatDate value="${hotelChannel.beginContractPeriod}" pattern="yyyy-MM-dd"/>"/>
                            -
                            <input id="endContractPeriod" name="endContractPeriod" type="text" maxlength="20"
                                   class="form-control layer-date input-sm"
                                   value="<fmt:formatDate value="${hotelChannel.endContractPeriod}" pattern="yyyy-MM-dd"/>"/>
                            <span>状态：</span>
                            <form:select path="status" class="form-control">
                                <form:option value=""></form:option>
                                <form:options items="${fns:getDictList('general_status')}" itemLabel="label"
                                              itemValue="value" htmlEscape="false"/>
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
                        <shiro:hasPermission name="hotelchannel:hotelChannel:add">
                            <table:addRow url="${ctx}/hotelchannel/hotelChannel/form"
                                          title="酒店销售渠道"></table:addRow><!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="hotelchannel:hotelChannel:edit">
                            <table:editRow url="${ctx}/hotelchannel/hotelChannel/form" title="酒店销售渠道"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="hotelchannel:hotelChannel:del">
                            <table:delRow url="${ctx}/hotelchannel/hotelChannel/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="hotelchannel:hotelChannel:import">
                            <table:importExcel
                                    url="${ctx}/hotelchannel/hotelChannel/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="hotelchannel:hotelChannel:export">
                            <table:exportExcel
                                    url="${ctx}/hotelchannel/hotelChannel/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th class="sort-column name">名称</th>
                    <th class="sort-column hotel">所属酒店</th>
                    <th class="sort-column contacts">联系人</th>
                    <th class="sort-column contactsPhone">联系人电话</th>
                    <th class="sort-column contractPeriod">合同期</th>
                    <th class="sort-column status">状态</th>
                    <th class="sort-column adminUrl">管理地址</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="hotelChannel">
                    <tr>
                        <td><input type="checkbox" id="${hotelChannel.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看酒店销售渠道', '${ctx}/hotelchannel/hotelChannel/form?id=${hotelChannel.id}','800px', '500px')">
                                ${hotelChannel.name}
                        </a></td>
                        <td>
                                ${hotelChannel.hotel.office.name}
                        </td>
                        <td>
                                ${hotelChannel.contacts}
                        </td>
                        <td>
                                ${hotelChannel.contactsPhone}
                        </td>
                        <td>
                                ${fns:formatDate(hotelChannel.contractPeriod)}
                        </td>
                        <td>
                                ${fns:getDictLabel(hotelChannel.status,'general_status','')}
                        </td>
                        <td>
                            <c:choose>
                               <c:when test="${not empty hotelChannel.adminUrl}" >
                                   <a href="${hotelChannel.adminUrl}" target="_blank">
                                       点击进入
                                   </a>
                               </c:when>
                                <c:otherwise>
                                    暂无
                                </c:otherwise>
                            </c:choose>

                        </td>
                        <td>
                            <shiro:hasPermission name="hotelchannel:hotelChannel:view">
                                <a href="#"
                                   onclick="openDialogView('查看酒店销售渠道', '${ctx}/hotelchannel/hotelChannel/form?id=${hotelChannel.id}','800px', '500px')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="hotelchannel:hotelChannel:edit">
                                <a href="#"
                                   onclick="openDialog('修改酒店销售渠道', '${ctx}/hotelchannel/hotelChannel/form?id=${hotelChannel.id}','800px', '500px')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="hotelchannel:hotelChannel:del">
                                <a href="${ctx}/hotelchannel/hotelChannel/delete?id=${hotelChannel.id}"
                                   onclick="return confirmx('确认要删除该酒店销售渠道吗？', this.href)" class="btn btn-danger btn-xs"><i
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