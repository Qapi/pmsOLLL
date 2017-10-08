<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>会员等级管理</title>
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
                elem: '#validityTerm', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus', //响应事件。如果没有传入event，则按照默认的click
                min: 0 ,
                theme: 'molv'
            });
        });
    </script>
</head>
<body class="hideScroll">
    <div id="modelDiv">
        <form id="inputForm" action="${ctx}/memberlevel/memberLevel/save" method="post" class="form-horizontal">
            <input id="id" name="id" value="${memberLevel.id}" type="hidden"/>
            <sys:message content="${message}"/>
            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
                <tbody>
                <tr>
                    <td class="width-15 active"><label class="pull-right">名称：</label></td>
                    <td class="width-35">
                        <input name="name" :value="memberLevel.name" htmlEscape="false" class="form-control "/>
                    </td>
                    <td class="width-15 active"><label class="pull-right">等级标识：</label></td>
                    <td class="width-35">
                        <select name="flag" :value="memberLevel.flag" class="form-control">
                            <c:forEach items="${fns:getDictList('member_grade')}" var="grade">
                                <option  label="${grade.label}" value="${grade.value}" htmlEscape="false"/>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">所属酒店：</label></td>
                    <td class="width-35">
                        <select name="hotel.id" htmlEscape="false"    class="form-control required">
                            <option value=""></option>
                            <option v-for="hotel in hotels" :label="hotel.office.name" :value="hotel.id" v-if="hotel.id == memberLevel.hotel.id" selected></option>
                            <option :label="hotel.office.name" :value="hotel.id" v-else></option>
                        </select>
                    </td>
                    <td class="width-15 active"><label class="pull-right">折扣优惠：</label></td>
                    <td class="width-35">
                        <input name="salePercent" :value="memberLevel.salePercent" htmlEscape="false" class="form-control" placeholder="格式：0.9"/>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">有效期：</label></td>
                    <td class="width-35">
                        <input id="validityTerm" name="validityTerm" type="text" maxlength="20"
                               class="laydate-icon form-control layer-date "
                               value="<fmt:formatDate value="${memberLevel.validityTerm}" pattern="yyyy-MM-dd"/>"/>
                    </td>
                    <td class="width-15 active"><label class="pull-right">状态：</label></td>
                    <td class="width-35">
                        <select name="status" :value="memberLevel.status" class="form-control">
                            <option value=""></option>
                            <c:forEach items="${fns:getDictList('general_status')}" var="status">
                                <option  label="${status.label}" value="${status.value}" htmlEscape="false"/>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">备注信息：</label></td>
                    <td class="width-35">
                        <textarea name="remarks" htmlEscape="false" rows="4" class="form-control "></textarea>
                    </td>
                    <td class="width-15 active"></td>
                    <td class="width-35"></td>
                </tr>
                </tbody>
            </table>
        </form>
        <script src="${ctxStatic}/pmsol/js/memberLevelForm.js"></script>
    </div>
</body>
</html>