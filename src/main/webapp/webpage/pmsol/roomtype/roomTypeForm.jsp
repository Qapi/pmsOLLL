<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>房型管理</title>
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
			
		});
	</script>
</head>
<body class="hideScroll">
	<div id="modelDiv">
			<form id="inputForm" action="${ctx}/roomtype/roomType/save" method="post" class="form-horizontal">
			<input id="id" name="id" value="${roomType.id}" type="hidden"/>
			<sys:message content="${message}"/>
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			   <tbody>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>房型名称：</label></td>
						<td class="width-35">
							<input name="name" :value="roomType.name" htmlEscape="false"    class="form-control required"/>
						</td>
						<td class="width-15 active"><label class="pull-right">可入住人数：</label></td>
						<td class="width-35">
							<input name="capacity" :value="roomType.capacity" htmlEscape="false"    class="form-control  digits"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>床型：</label></td>
						<td class="width-35">
							<select name="bedType" htmlEscape="false"    class="form-control required">
								<c:forEach items="${fns:getDictList('bed_type')}" var="bedType">
									<option label="${bedType.label}" value="${bedType.value}"></option>
								</c:forEach>
							</select>
						</td>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>床位数：</label></td>
						<td class="width-35">
							<input name="bedNum" :value="roomType.bedNum" htmlEscape="false"    class="form-control required digits"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>平日价：</label></td>
						<td class="width-35">
							<input name="dailyPrice" :value="roomType.dailyPrice" htmlEscape="false"    class="form-control required number"/>
						</td>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>周末价：</label></td>
						<td class="width-35">
							<input name="weekendPrice" :value="roomType.weekendPrice" htmlEscape="false"    class="form-control required number"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>钟点房价：</label></td>
						<td class="width-35">
							<input name="hourPrice" :value="roomType.hourPrice" htmlEscape="false"    class="form-control required number"/>
						</td>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>钟点小时数：</label></td>
						<td class="width-35">
							<input name="hourNum" :value="roomType.hourNum" htmlEscape="false"    class="form-control required number"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>节假日价：</label></td>
						<td class="width-35">
							<input name="holidayPrice" :value="roomType.holidayPrice" htmlEscape="false"    class="form-control required number"/>
						</td>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>月租价：</label></td>
						<td class="width-35">
							<input name="monthlyRent" :value="roomType.monthlyRent" htmlEscape="false"    class="form-control required number"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>所属酒店：</label></td>
						<td class="width-35">
							<select name="hotel.id" htmlEscape="false"    class="form-control required">
								<option value=""></option>
								<option v-for="hotel in hotels" :label="hotel.office.name" :value="hotel.id"
									<c:if test="${roomType.hotel.id eq hotel.id}">selected</c:if>>
								</option>
							</select>
						</td>
						<td class="width-15 active"><label class="pull-right">说明：</label></td>
						<td class="width-35">
							<textarea name="remarks" :value="roomType.remarks" htmlEscape="false" rows="4"    class="form-control "></textarea>
						</td>
						<%--<td class="width-15 active"></td>--%>
						<%--<td class="width-35" ></td>--%>
					</tr>
				</tbody>
			</table>
		</form>
		<script src="${ctxStatic}/pmsol/js/roomTypeForm.js"></script>
	</div>
</body>
</html>