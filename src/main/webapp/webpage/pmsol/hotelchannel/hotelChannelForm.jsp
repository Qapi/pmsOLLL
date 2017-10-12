<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>酒店销售渠道管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        var validateForm;

        function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
            if (validateForm.form()) {
                $("#inputForm").submit();
                return true;
            }

            return false;
        }

        $(document).ready(function () {
            validateForm = $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            laydate.render({
                elem: '#contractPeriod',
                event: 'focus',
                theme: 'molv',
                min: 0
            })
        });
    </script>
</head>
<body class="hideScroll">
<div id="modelDiv">
    <form id="inputForm" action="${ctx}/hotelchannel/hotelChannel/save" method="post"
          class="form-horizontal">
        <input id="id" name="id" value="${hotelChannel.id}" type="hidden"/>
        <sys:message content="${message}"/>
        <table class="table table-bhotelChanneled  table-condensed dataTables-example dataTable no-footer">
            <tbody>
            <tr>
                <td class="width-15 active"><label class="pull-right"><font color="red">*</font>名称：</label></td>
                <td class="width-35">
                    <input name="name" v-model="hotelChannel.name" htmlEscape="false" class="form-control required"/>
                </td>
                <td class="width-15 active"><label class="pull-right"><font color="red">*</font>所属酒店：</label></td>
                <td class="width-35">
                    <select name="hotel.id" htmlEscape="false" class="form-control required">
                        <option value=""></option>
                        <option v-for="hotel in hotels" :label="hotel.office.name" :value="hotel.id"
                                v-if="hotel.id == hotelChannel.hotel.id" selected></option>
                        <option :label="hotel.office.name" :value="hotel.id" v-else></option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="width-15 active"><label class="pull-right">联系人：</label></td>
                <td class="width-35">
                    <input name="contacts" v-model="hotelChannel.contacts" htmlEscape="false"
                           class="form-control required"/>
                </td>
                <td class="width-15 active"><label class="pull-right">联系人电话：</label></td>
                <td class="width-35">
                    <input name="contactsPhone" v-model="hotelChannel.contactsPhone" htmlEscape="false"
                           class="form-control required"/>
                </td>
            </tr>
            <tr>
                <td class="width-15 active"><label class="pull-right">合同期：</label></td>
                <td class="width-35">
                    <input id="contractPeriod" name="contractPeriod" type="text" maxlength="20"
                           class="laydate-icon form-control layer-date "
                           value="<fmt:formatDate value="${hotelChannel.contractPeriod}" pattern="yyyy-MM-dd"/>"/>
                </td>
                <td class="width-15 active"><label class="pull-right">后台管理地址：</label></td>
                <td class="width-35">
                    <input name="adminUrl" v-model="hotelChannel.adminUrl" placeholder="以http://或https://开头" htmlEscape="false"
                           class="form-control required"/>
                </td>
            </tr>
            <shiro:hasPermission name="hotelchannel:hotelChannel:admin">
                <tr>
                    <td class="width-15 active"><label class="pull-right">管理帐号：</label></td>
                    <td class="width-35">
                        <input name="userName" v-model="hotelChannel.userName" htmlEscape="false"
                               class="form-control required"/>
                    </td>
                    <td class="width-15 active"><label class="pull-right">管理密码：</label></td>
                    <td class="width-35">
                        <input name="password" v-model="hotelChannel.password" htmlEscape="false"
                               class="form-control required"/>
                    </td>
                </tr>
            </shiro:hasPermission>
            <tr>
                <td class="width-15 active"><label class="pull-right">状态：</label></td>
                <td class="width-35">
                    <select name="status" class="form-control ">
                        <c:forEach items="${fns:getDictList('general_status')}" var="status">
                            <option label="${status.label}" value="${status.value}" htmlEscape="false"
                                    <c:if test="${hotelChannel.status == status.value}">selected</c:if>/>
                        </c:forEach>
                    </select>
                </td>
                <td class="width-15 active"><label class="pull-right">备注信息：</label></td>
                <td class="width-35">
                    <textarea name="remarks" v-model="hotelChannel.remarks" htmlEscape="false" rows="4"
                              class="form-control "></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
    <script src="${ctxStatic}/pmsol/js/hotelChannelForm.js"></script>
</div>
</body>
</html>