/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.hotelchannel.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.pmsol.hotel.entity.Hotel;

/**
 * 酒店销售渠道配置Entity
 * @author wangp
 * @version 2017-09-29
 */
public class HotelChannel extends DataEntity<HotelChannel> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private Hotel hotel;		// 所属酒店
	private String adminUrl;	// 后台管理网址
	private String userName;	// 后台管理用户名
	private String password;	// 后台管理用户名
	private String contacts;		// 联系人
	private String contactsPhone;		// 联系人电话
	private String contractPeriod;		// 合同期
	private String status;		// 状态
	private String beginContractPeriod;		// 开始 合同期
	private String endContractPeriod;		// 结束 合同期
	
	public HotelChannel() {
		super();
	}

	public HotelChannel(String id){
		super(id);
	}

	@ExcelField(title="名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="所属酒店", fieldType=String.class, value="", align=2, sort=8)
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	@ExcelField(title="联系人", align=2, sort=9)
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	@ExcelField(title="联系人电话", align=2, sort=10)
	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}
	
	@ExcelField(title="合同期", align=2, sort=11)
	public String getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	
	@ExcelField(title="状态", align=2, sort=12)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getAdminUrl() {
		return adminUrl;
	}

	public void setAdminUrl(String adminUrl) {
		this.adminUrl = adminUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}