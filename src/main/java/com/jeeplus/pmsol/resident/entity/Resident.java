/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.resident.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 入住人Entity
 * @author wangp
 * @version 2017-10-13
 */
public class Resident extends DataEntity<Resident> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String credentialType; //证件类型
	private String idNum;		// 证件号码
	private String phone;		// 手机号
	private String gender;		// 性别
	private String homeAddress;		// 家庭地址/居住地址
	private String emergencyContact;		// 紧急联系人
	private String emergencyContactPhone;		// 紧急联系人电话
	private String residentType;		// 入住类型  1、入住  2、来访  3、长租
	private String leaveTime;		// 来访离开时间
	private String hotel;		// 入住酒店
	
	public Resident() {
		super();
	}

	public Resident(String id){
		super(id);
	}

	@ExcelField(title="姓名", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="身份证号", align=2, sort=8)
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
	@ExcelField(title="手机号", align=2, sort=9)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ExcelField(title="性别", align=2, sort=10)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@ExcelField(title="家庭地址/居住地址", align=2, sort=11)
	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	@ExcelField(title="紧急联系人", align=2, sort=12)
	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	
	@ExcelField(title="紧急联系人电话", align=2, sort=13)
	public String getEmergencyContactPhone() {
		return emergencyContactPhone;
	}

	public void setEmergencyContactPhone(String emergencyContactPhone) {
		this.emergencyContactPhone = emergencyContactPhone;
	}
	
	@ExcelField(title="入住类型  1、入住  2、来访  3、长租", align=2, sort=14)
	public String getResidentType() {
		return residentType;
	}

	public void setResidentType(String residentType) {
		this.residentType = residentType;
	}
	
	@ExcelField(title="来访离开时间", align=2, sort=15)
	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}
	
	@ExcelField(title="入住酒店", align=2, sort=16)
	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public String getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}
}