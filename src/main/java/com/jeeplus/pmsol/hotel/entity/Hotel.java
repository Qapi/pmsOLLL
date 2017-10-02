/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.hotel.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.Office;

/**
 * 酒店物业配置Entity
 * @author wangp
 * @version 2017-09-29
 */
public class Hotel extends DataEntity<Hotel> {
	
	private static final long serialVersionUID = 1L;
	private String address;		// 酒店地址
	private String landline;		// 前台座机
	private String contacts;		// 联系人
	private String contactPhone;		// 联系电话
	private String propertyOwner;		// 物业持有人
	private String propertyPhone;		// 物业持有人联系电话
	private String contractPeriod;		// 有效合同期
	private String status;		// 物业状态
	private Office office;		// 系统office
	
	public Hotel() {
		super();
	}

	public Hotel(String id){
		super(id);
	}

	@ExcelField(title="酒店地址", align=2, sort=6)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="前台座机", align=2, sort=7)
	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}
	
	@ExcelField(title="联系人", align=2, sort=8)
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	@ExcelField(title="联系电话", align=2, sort=9)
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	@ExcelField(title="物业持有人", align=2, sort=10)
	public String getPropertyOwner() {
		return propertyOwner;
	}

	public void setPropertyOwner(String propertyOwner) {
		this.propertyOwner = propertyOwner;
	}
	
	@ExcelField(title="物业持有人联系电话", align=2, sort=11)
	public String getPropertyPhone() {
		return propertyPhone;
	}

	public void setPropertyPhone(String propertyPhone) {
		this.propertyPhone = propertyPhone;
	}
	
	@ExcelField(title="有效合同期", align=2, sort=12)
	public String getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	
	@ExcelField(title="物业状态", align=2, sort=13)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="系统office", align=2, sort=14)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}