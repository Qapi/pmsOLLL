/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.wangp.pmsol.member.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 会员配置Entity
 * @author wangp
 * @version 2017-09-29
 */
public class Member extends DataEntity<Member> {
	
	private static final long serialVersionUID = 1L;
	private String nickName;		// 昵称
	private String hotelId;		// 所属酒店
	private String idNum;		// 身份证号
	private String birthday;		// 生日
	private String homeAddress;		// 家庭地址
	private String phone;		// 手机号
	private String emergencyContact;		// 紧急联系人
	private String emergency_contactPhone;		// 紧急联系人电话
	private String memberNum;		// 会员号
	private String menberLevelId;		// 所属会员等级
	private String userId;		// 所属用户id
	private String status;		// 状态
	
	public Member() {
		super();
	}

	public Member(String id){
		super(id);
	}

	@ExcelField(title="昵称", align=2, sort=6)
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@ExcelField(title="所属酒店", align=2, sort=7)
	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	
	@ExcelField(title="身份证号", align=2, sort=8)
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
	@ExcelField(title="生日", align=2, sort=9)
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	@ExcelField(title="家庭地址", align=2, sort=10)
	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	@ExcelField(title="手机号", align=2, sort=11)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ExcelField(title="紧急联系人", align=2, sort=12)
	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	
	@ExcelField(title="紧急联系人电话", align=2, sort=13)
	public String getEmergency_contactPhone() {
		return emergency_contactPhone;
	}

	public void setEmergency_contactPhone(String emergency_contactPhone) {
		this.emergency_contactPhone = emergency_contactPhone;
	}
	
	@ExcelField(title="会员号", align=2, sort=14)
	public String getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(String memberNum) {
		this.memberNum = memberNum;
	}
	
	@ExcelField(title="所属会员等级", align=2, sort=15)
	public String getMenberLevelId() {
		return menberLevelId;
	}

	public void setMenberLevelId(String menberLevelId) {
		this.menberLevelId = menberLevelId;
	}
	
	@ExcelField(title="所属用户id", align=2, sort=16)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ExcelField(title="状态", align=2, sort=17)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}