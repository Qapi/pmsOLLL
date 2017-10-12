<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>会员管理</title>
	<meta name="decorator" content="default"/>
</head>
<body class="hideScroll">
	<div id="modelDiv">
		<form id="inputForm" action="${ctx}/member/member/save" method="post" class="form-horizontal">
			<input id="id" name="id" value="${member.id}" type="hidden"/>
			<sys:message content="${message}"/>
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			   <tbody>
				   <tr>
					   <td class="width-15 active"><label class="pull-right">会员号：</label></td>
					   <td class="width-35">
						   <input v-model="member.memberNum" htmlEscape="false"  disabled  class="form-control"/>
					   </td>
					   <td class="width-15 active"><label class="pull-right"><font color="red">*</font>会员等级：</label></td>
					   <td class="width-35">
						   <select name="memberLevel.id" htmlEscape="false"    class="form-control required">
							   <option v-for="memberLevel in memberLevels" :label="memberLevel.name" v-model="memberLevel.id" v-if="memberLevel.id == member.memberLevel.id" selected></option>
							   <option :label="memberLevel.name" v-model="memberLevel.id" v-else></option>
						   </select>
					   </td>
				   </tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>姓名：</label></td>
						<td class="width-35">
							<input name="name" v-model="member.name" htmlEscape="false"    class="form-control "/>
						</td>
						<td class="width-15 active"><label class="pull-right">昵称：</label></td>
						<td class="width-35">
							<input name="nickName" v-model="member.nickName" htmlEscape="false"    class="form-control "/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>身份证号：</label></td>
						<td class="width-35">
							<input id="idNum" name="idNum" v-model="member.idNum" @blur="calBirth" htmlEscape="false"    class="form-control required"/>
						</td>
						<td class="width-15 active"><label class="pull-right">生日：</label></td>
						<td class="width-35">
							<input id="birthday" name="birthday" type="text" maxlength="20" class="laydate-icon form-control layer-date "
								   value="<fmt:formatDate value="${member.birthday}" pattern="yyyy-MM-dd"/>"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right">居住地址：</label></td>
						<td class="width-35">
							<input name="homeAddress" v-model="member.homeAddress"  htmlEscape="false"    class="form-control "/>
						</td>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>手机号：</label></td>
						<td class="width-35">
							<input name="phone" v-model="member.phone"  htmlEscape="false"    class="form-control required digits"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right">邮箱：</label></td>
						<td class="width-35">
							<input name="email" v-model="member.email" htmlEscape="false"    class="form-control email"/>
						</td>
						<td class="active"><label class="pull-right">性别:</label></td>
						<td>
							<select name="gender" v-model="member.gender" class="form-control ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('sex')}" var="sex">
								<option label="${sex.label}" value="${sex.value}" htmlEscape="false"/>
							</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">*</font>所属酒店：</label></td>
						<td class="width-35">
							<select name="hotel.id" htmlEscape="false"    class="form-control required">
								<option value=""></option>
								<option v-for="hotel in hotels" :label="hotel.office.name" v-model="hotel.id" v-if="hotel.id == member.hotel.id" selected></option>
								<option :label="hotel.office.name" v-model="hotel.id" v-else></option>
							</select>
						</td>
						<td class="width-15 active"><label class="pull-right">有效期：</label></td>
						<td class="width-35">
							<input id="validityTerm" name="validityTerm" type="text" maxlength="20"
								   class="laydate-icon form-control layer-date "
								   value="<fmt:formatDate value="${member.validityTerm}" pattern="yyyy-MM-dd"/>"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right">紧急联系人：</label></td>
						<td class="width-35">
							<input name="emergencyContact" v-model="member.emergencyContact" htmlEscape="false"    class="form-control "/>
						</td>
						<td class="width-15 active"><label class="pull-right">紧急联系人电话：</label></td>
						<td class="width-35">
							<input name="emergencyContactPhone" v-model="member.emergencyContactPhone" htmlEscape="false"    class="form-control "/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right">状态：</label></td>
						<td class="width-35">
							<select name="status" v-model="member.status" class="form-control">
								<option value=""></option>
								<c:forEach items="${fns:getDictList('general_status')}" var="status">
									<option  label="${status.label}" value="${status.value}" htmlEscape="false"/>
								</c:forEach>
							</select>
						</td>
						<td class="width-15 active"><label class="pull-right">注册操作员：</label></td>
						<td class="width-35">
							<input  v-model="member.operator.name" htmlEscape="false"  disabled  class="form-control "/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		<script src="${ctxStatic}/pmsol/js/memberForm.js"></script>
	</div>
</body>
</html>