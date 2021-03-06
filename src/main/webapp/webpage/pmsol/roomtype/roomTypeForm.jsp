<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>房型管理</title>
	<meta name="decorator" content="default"/>
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
							<input name="name" v-model="roomType.name" htmlEscape="false"    class="form-control required"/>
						</td>
						<td class="width-15 active"><label class="pull-right">可入住人数：</label></td>
						<td class="width-35">
							<input name="capacity" v-model="roomType.capacity" htmlEscape="false"    class="form-control  digits"/>
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
							<input name="bedNum" v-model="roomType.bedNum" htmlEscape="false"    class="form-control required digits"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>平日价：</label></td>
						<td class="width-35">
							<input name="dailyPrice" v-model="roomType.dailyPrice" htmlEscape="false"    class="form-control required number"/>
						</td>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>周末价：</label></td>
						<td class="width-35">
							<input name="weekendPrice" v-model="roomType.weekendPrice" htmlEscape="false"    class="form-control required number"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>钟点房价：</label></td>
						<td class="width-35">
							<input name="hourPrice" v-model="roomType.hourPrice" htmlEscape="false"    class="form-control required number"/>
						</td>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>钟点小时数：</label></td>
						<td class="width-35">
							<input name="hourNum" v-model="roomType.hourNum" htmlEscape="false"    class="form-control required number"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>节假日价：</label></td>
						<td class="width-35">
							<input name="holidayPrice" v-model="roomType.holidayPrice" htmlEscape="false"    class="form-control required number"/>
						</td>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>月租价：</label></td>
						<td class="width-35">
							<input name="monthlyRent" v-model="roomType.monthlyRent" htmlEscape="false"    class="form-control required number"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>所属酒店：</label></td>
						<td class="width-35">
							<select name="hotel.id" htmlEscape="false"    class="form-control required">
								<option value=""></option>
								<option v-for="hotel in hotels" :label="hotel.office.name" :value="hotel.id" v-if="roomType.hotel != null && hotel.id == roomType.hotel.id" selected></option>
								<option :label="hotel.office.name" :value="hotel.id" v-else></option>
							</select>
						</td>
						<td class="width-15 active"><label class="pull-right">说明：</label></td>
						<td class="width-35">
							<textarea name="remarks" v-model="roomType.remarks" htmlEscape="false" rows="4"    class="form-control "></textarea>
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