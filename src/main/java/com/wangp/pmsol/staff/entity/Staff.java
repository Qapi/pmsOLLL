/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.wangp.pmsol.staff.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 酒店员工配置Entity
 * @author wangp
 * @version 2017-09-29
 */
public class Staff extends DataEntity<Staff> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String phone;		// 手机号
	private String hotelId;		// 所属酒店
	private String idNum;		// 身份证号
	private String birthday;		// 生日
	private String gender;		// 性别
	private String homeAddress;		// 家庭地址
	private String departmentId;		// 所属部门
	private String postId;		// 所属岗位
	private String contractPeriod;		// 合同期
	private String status;		// 状态
	private String userId;		// 用户id
	private String beginContractPeriod;		// 开始 合同期
	private String endContractPeriod;		// 结束 合同期
	
	public Staff() {
		super();
	}

	public Staff(String id){
		super(id);
	}

	@ExcelField(title="姓名", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="手机号", align=2, sort=8)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ExcelField(title="所属酒店", align=2, sort=9)
	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	
	@ExcelField(title="身份证号", align=2, sort=10)
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
	@ExcelField(title="生日", align=2, sort=11)
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	@ExcelField(title="性别", align=2, sort=12)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@ExcelField(title="家庭地址", align=2, sort=13)
	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	@ExcelField(title="所属部门", fieldType=String.class, value="", align=2, sort=14)
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	@ExcelField(title="所属岗位", align=2, sort=15)
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	@ExcelField(title="合同期", align=2, sort=16)
	public String getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	
	@ExcelField(title="状态", align=2, sort=17)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="用户id", align=2, sort=18)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getBeginContractPeriod() {
		return beginContractPeriod;
	}

	public void setBeginContractPeriod(String beginContractPeriod) {
		this.beginContractPeriod = beginContractPeriod;
	}
	
	public String getEndContractPeriod() {
		return endContractPeriod;
	}

	public void setEndContractPeriod(String endContractPeriod) {
		this.endContractPeriod = endContractPeriod;
	}
		
}