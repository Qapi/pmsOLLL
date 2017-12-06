<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>首页</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            WinMove();
        });
    </script>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/pmsol/css/home.css">
</head>
<body class="gray-bg">
    <sys:message content="${message}"/>
    <div id="modelDiv" class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-20">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>实时房态</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div v-for="room in rooms" class="room-block">
                            <p>{{room.roomNum}}</p>
                            <p>{{room.roomType.name}}</p>
                            <%--<s :class="room.status"></s>--%>
                        </div>
                        <hr>
                        <div class="r_info">
                            <ul>
                                <c:forEach items="${fns:getDictList('room_status')}" var="rs">
                                    <li>
                                        <s class="i-r0${rs.value}"></s>${rs.label}
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${ctxStatic}/pmsol/js/home.js"></script>
</body>
</html>