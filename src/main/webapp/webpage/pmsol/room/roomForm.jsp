<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>房间管理</title>
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

        });
    </script>
</head>
<body class="hideScroll">
    <div id="modelDiv">
        <form id="inputForm" action="${ctx}/room/room/save" method="post" class="form-horizontal">
            <input id="id" name="id" value="${room.id}" type="hidden"/>
            <sys:message content="${message}"/>
            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
                <tbody>
                <tr>

                    <td class="width-15 active"><label class="pull-right">房间号：</label></td>
                    <td class="width-35">
                        <input name="roomNum" :value="room.roomNum" htmlEscape="false" class="form-control  digits"/>
                    </td>
                    <td class="width-15 active"><label class="pull-right">房间名：</label></td>
                    <td class="width-35">
                        <input name="topicName" :value="room.topicName" htmlEscape="false" class="form-control "/>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">所属酒店：</label></td>
                    <td class="width-35">
                        <select name="hotel.id" htmlEscape="false"    class="form-control required">
                            <option value=""></option>
                            <option v-for="hotel in hotels" :label="hotel.office.name" :value="hotel.id" v-if="hotel.id == room.hotel.id" selected></option>
                            <option :label="hotel.office.name" :value="hotel.id" v-else></option>
                        </select>
                    </td>
                    <td class="width-15 active"><label class="pull-right">楼层：</label></td>
                    <td class="width-35">
                        <input name="floorNum" :value="room.floorNum" htmlEscape="false" class="form-control  digits"/>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">户型：</label></td>
                    <td class="width-35">
                        <input name="layout" :value="room.layout" htmlEscape="false" class="form-control "/>
                    </td>
                    <td class="width-15 active"><label class="pull-right">房型：</label></td>
                    <td class="width-35">
                        <select name="roomType.id" htmlEscape="false"    class="form-control required">
                            <option value=""></option>
                            <option v-for="roomType in roomTypes" :label="roomType.name" :value="roomType.id" v-if="roomType.id == room.roomType.id" selected></option>
                            <option :label="roomType.name" :value="roomType.id" v-else></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">说明：</label></td>
                    <td class="width-35">
                        <textarea name="remarks" :value="room.remarks" htmlEscape="false" rows="4" class="form-control "></textarea>
                    </td>
                    <td class="width-15 active"></td>
                    <td class="width-35"></td>
                </tr>
                </tbody>
            </table>
        </form>
        <script src="${ctxStatic}/pmsol/js/roomForm.js"></script>
    </div>
</body>
</html>