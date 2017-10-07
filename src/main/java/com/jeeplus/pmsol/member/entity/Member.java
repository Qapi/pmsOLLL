/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.member.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.pmsol.hotel.entity.Hotel;
import com.jeeplus.pmsol.memberlevel.entity.MemberLevel;

import java.util.Date;

/**
 * 会员配置Entity
 * @author wangp
 * @version 2017-09-29
 */
public class Member extends DataEntity<Member> {
	
	private static final long serialVersionUID = 1L;
	private String name; 		// 姓名
	private String nickName;		// 昵称
	private Hotel hotel;		// 所属酒店
	private String idNum;		// 身份证号
	private Date birthday;		// 生日
	private String gender;		// 性别
	private String homeAddress;		// 家庭地址
	private String phone;		// 手机号
	private String email;		// 邮箱
	private String emergencyContact;		// 紧急联系人
	private String emergencyContactPhone;		// 紧急联系人电话
	private String memberNum;		// 会员号
	private MemberLevel memberLevel;		// 所属会员等级
	private User operator;		// 操作注册用户
	private int integral;		// 积分
	private String userId;		// 所属用户id
	private Date validityTerm;		// 有效期
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
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	@ExcelField(title="身份证号", align=2, sort=8)
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
	@ExcelField(title="生日", align=2, sort=9)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
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
	public String getEmergencyContactPhone() {
		return emergencyContactPhone;
	}

	public void setEmergencyContactPhone(String emergencyContactPhone) {
		this.emergencyContactPhone = emergencyContactPhone;
	}
	
	@ExcelField(title="会员号", align=2, sort=14)
	public String getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(String memberNum) {
		this.memberNum = memberNum;
	}
	
	@ExcelField(title="所属会员等级", align=2, sort=15)
	public MemberLevel getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(MemberLevel memberLevel) {
		this.memberLevel = memberLevel;
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

	public Date getValidityTerm() {
		return this.validityTerm;
	}

	public void setValidityTerm(Date validityTerm) {
		this.validityTerm = validityTerm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}