<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>订单管理</title>
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
                elem: '#checkInDate',
                event: 'focus',
                min: 0 ,
                theme: 'molv' ,
                done: function(value, date, endDate){
                    laydate.render({
                        elem: '#checkOutDate',
                        event: 'focus' ,
                        min: value,
                        theme: 'molv'
                    });
                }
            });
        });
    </script>
</head>
<body class="hideScroll">
    <div id="modelDiv">
        <form id="inputForm" modelAttribute="order" action="${ctx}/order/order/save" method="post" class="form-horizontal">
            <input id="id" name="id" value="${order.id}" type="hidden"/>
            <sys:message content="${message}"/>
            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
                <tr>
                    <td class="width-15 active"><label class="pull-right">订单号：</label></td>
                    <td class="width-35">
                        <input v-model="order.orderNum" htmlEscape="false" class="form-control" disabled/>
                    </td>
                    <td class="width-15 active"><label class="pull-right"><font color="red">*</font>所属酒店：</label></td>
                    <td class="width-35">
                        <select name="hotel.id" htmlEscape="false"    class="form-control required">
                            <option value=""></option>
                            <option v-for="hotel in hotels" :label="hotel.office.name" v-model="hotel.id" v-if="hotel.id == order.hotel.id" selected></option>
                            <option :label="hotel.office.name" v-model="hotel.id" v-else></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">渠道：</label></td>
                    <td class="width-35">
                        <select name="channel.id" htmlEscape="false"    class="form-control ">
                            <option v-for="hotel in channels" :label="channel.name" v-model="channel.id" v-if="channel.id == order.channel.id" selected></option>
                            <option :label="channel.name" v-model="channel.id" v-else></option>
                        </select>
                    </td>
                    <td class="width-15 active"><label class="pull-right">渠道订单号：</label></td>
                    <td class="width-35">
                        <input name="chlOrderNum" v-model="order.chlOrderNum" htmlEscape="false" class="form-control"/>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right"><font color="red">*</font>房型：</label></td>
                    <td class="width-35">
                        <select id="roomTypeId" name="roomType.id" htmlEscape="false"  @change="selectRoomType"  class="form-control required">
                            <option v-for="roomType in roomTypes" :label="roomType.name" v-model="roomType.id" v-if="roomType.id == order.roomType.id" selected></option>
                            <option :label="roomType.name" v-model="roomType.id" v-else></option>
                        </select>
                    </td>
                    <td class="width-15 active"><label class="pull-right">预约房间：</label></td>
                    <td class="width-35">
                        <select name="bookRoom.id" htmlEscape="false"    class="form-control">
                            <option value=""></option>
                            <option v-for="room in rooms" :label="room.roomNum" v-model="room.id" v-if="order.bookRoom != null && room.id == order.bookRoom.id" selected></option>
                            <option :label="room.roomNum" v-model="room.id" v-else></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">预订人：</label></td>
                    <td class="width-35">
                        <select name="booker.id" htmlEscape="false"    class="form-control">
                            <option value=""></option>
                            <option v-for="member in members" :label="member.name" v-model="member.id" v-if="order.member != null && member.id == order.booker.id" selected></option>
                            <option :label="member.name" v-model="member.id" v-else></option>
                        </select>
                    </td>
                    <td class="width-15 active"><label class="pull-right"><font color="red">*</font>租赁类型：</label></td>
                    <td class="width-35">
                        <%--<select  id="leaseMode" data-id="${order.leaseMode}" name="leaseMode" @change="selectLeaseMode" class="form-control required">--%>
                            <%--<c:forEach items="${fns:getDictList('lease_mode')}" var="mode" >--%>
                                <%--<option label="${mode.label}" value="${mode.value}" htmlEscape="false"  <c:if test="${order.leaseMode == mode.value}">selected</c:if>/>--%>
                            <%--</c:forEach>--%>
                        <%--</select>--%>
                            <select id="leaseMode" name="leaseMode" v-model="order.leaseMode" @change="selectLeaseMode" htmlEscape="false"    class="form-control required">
                                <option v-for="mode in leaseModes" :label="mode.name" v-model="mode.value" v-if="order.leaseMode != null && mode.value == order.leaseMode.id" selected></option>
                                <option :label="mode.name" v-model="mode.value" v-else></option>
                            </select>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right"><font color="red">*</font>入住日期：</label></td>
                    <td class="width-35">
                        <input id="checkInDate" name="checkInDate" type="text" maxlength="20"
                               class="laydate-icon form-control required layer-date "
                               value="<fmt:formatDate value="${order.checkInDate}" pattern="yyyy-MM-dd"/>"/>
                    </td>
                    <td class="width-15 active"><label class="pull-right"><font color="red">*</font>离店日期：</label></td>
                    <td class="width-35">
                        <input id="checkOutDate" name="checkOutDate" type="text" maxlength="20"
                               class="laydate-icon form-control required layer-date "
                               value="<fmt:formatDate value="${order.checkOutDate}" pattern="yyyy-MM-dd"/>"/>
                    </td>
                </tr>
                <tr id="liveForDay" v-show="leaseModes[0].value">
                    <td class="width-15 active"><label class="pull-right">入住天数：</label></td>
                    <td class="width-35">
                        <input name="livedays" v-model="order.livedays" htmlEscape="false" class="form-control  number"/>
                    </td>
                    <td class="width-15 active"><label class="pull-right">每天租金：</label></td>
                    <td class="width-35">
                        <input name="dailyPrice" v-model="order.dailyPrice" htmlEscape="false" class="form-control  number"/>
                    </td>
                </tr>
                <tr id="liveForHour" v-show="leaseModes[1].value">
                    <td class="width-15 active"><label class="pull-right">入住小时数：</label></td>
                    <td class="width-35">
                        <input name="livehours" v-model="order.livehours" htmlEscape="false" class="form-control  number"/>
                    </td>
                    <td class="width-15 active"><label class="pull-right">每小时租金：</label></td>
                    <td class="width-35">
                        <input name="hourPrice" v-model="order.hourPrice" htmlEscape="false" class="form-control  number"/>
                    </td>
                </tr>
                <tr id="liveForMonth" v-show="leaseModes[2].value">
                    <td class="width-15 active"><label class="pull-right">长租月数：</label></td>
                    <td class="width-35">
                        <input name="rentMonths" v-model="order.rentMonths" htmlEscape="false" class="form-control  number"/>
                    </td>
                    <td class="width-15 active"><label class="pull-right">每月租金：</label></td>
                    <td class="width-35">
                        <input name="monthlyRent" v-model="order.monthlyRent" htmlEscape="false" class="form-control  number"/>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">订单总额：</label></td>
                    <td class="width-35">
                        <input name="totalAmount" v-model="calTotalAmount" htmlEscape="false" class="form-control  number"/>
                    </td>
                    <td class="width-15 active"><label class="pull-right">状态：</label></td>
                    <td class="width-35">
                        <select name="status" class="form-control ">
                            <c:forEach items="${fns:getDictList('order_status')}" var="status">
                                <option label="${status.label}" value="${status.value}" htmlEscape="false" <c:if test="${order.status == status.value}">selected</c:if>/>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right"><font color="red">*</font>入住人：</label></td>
                    <td class="width-35">
                        <input name="contacts" v-model="order.contacts" htmlEscape="false" class="form-control required"/>
                    </td>
                    <td class="width-15 active"><label class="pull-right"><font color="red">*</font>入住人电话：</label></td>
                    <td class="width-35">
                        <input name="contactsPhone" v-model="order.contactsPhone" htmlEscape="false" class="form-control required"/>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">备注信息：</label></td>
                    <td class="width-35">
                        <textarea name="remarks" v-model="order.remarks" htmlEscape="false" rows="4" class="form-control "></textarea>
                    </td>
                    <td class="width-15 active"></td>
                    <td class="width-35" ></td>
                </tr>
                </tbody>
            </table>
        </form>
        <script src="${ctxStatic}/pmsol/js/orderForm.js"></script>
    </div>
</body>
</html>