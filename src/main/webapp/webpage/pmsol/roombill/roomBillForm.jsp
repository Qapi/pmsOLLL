<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>房单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
					laydate({
			            elem: '#checkInTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#checkOutTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="roomBill" action="${ctx}/roombill/roomBill/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">订单id：</label></td>
					<td class="width-35">
						<form:input path="order" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">实际入住人（集合）：</label></td>
					<td class="width-35">
						<form:input path="actualResident" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">入住房间：</label></td>
					<td class="width-35">
						<form:input path="room" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">水费单价（每吨）：</label></td>
					<td class="width-35">
						<form:input path="waterPrice" htmlEscape="false"    class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">电费单价（每度）：</label></td>
					<td class="width-35">
						<form:input path="electricityPrice" htmlEscape="false"    class="form-control  number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">水初始读数：</label></td>
					<td class="width-35">
						<form:input path="waterFirstReading" htmlEscape="false"    class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">电初始读数：</label></td>
					<td class="width-35">
						<form:input path="electricityFirstReading" htmlEscape="false"    class="form-control  number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">水最终读数：</label></td>
					<td class="width-35">
						<form:input path="waterLastReading" htmlEscape="false"    class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">电最终读数：</label></td>
					<td class="width-35">
						<form:input path="electricityLastReading" htmlEscape="false"    class="form-control  number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">物业管理费（每月）：</label></td>
					<td class="width-35">
						<form:input path="managementFee" htmlEscape="false"    class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">入住时间：</label></td>
					<td class="width-35">
						<input id="checkInTime" name="checkInTime" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${roomBill.checkInTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">离店时间：</label></td>
					<td class="width-35">
						<input id="checkOutTime" name="checkOutTime" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${roomBill.checkOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
					<td class="width-15 active"><label class="pull-right">支付记录（集合）：</label></td>
					<td class="width-35">
						<form:input path="paymentRecord" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">押金金额：</label></td>
					<td class="width-35">
						<form:input path="depositAmount" htmlEscape="false"    class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">应收金额：</label></td>
					<td class="width-35">
						<form:input path="receivableAmount" htmlEscape="false"    class="form-control  number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">实收金额：</label></td>
					<td class="width-35">
						<form:input path="netreceiptsAmount" htmlEscape="false"    class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">未收金额：</label></td>
					<td class="width-35">
						<form:input path="unreceivedAmount" htmlEscape="false"    class="form-control  number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">退还金额：</label></td>
					<td class="width-35">
						<form:input path="returnAmount" htmlEscape="false"    class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">所属酒店：</label></td>
					<td class="width-35">
						<form:input path="hotel" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>