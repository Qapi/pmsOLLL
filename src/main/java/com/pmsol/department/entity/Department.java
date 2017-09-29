/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pmsol.department.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 部门配置Entity
 * @author wangp
 * @version 2017-09-29
 */
public class Department extends DataEntity<Department> {
	
	private static final long serialVersionUID = 1L;
	private String flag;		// 等级标识
	private String hotelId;		// 所属酒店
	private String status;		// 状态
	private String officeId;		// 系统officeId
	
	public Department() {
		super();
	}

	public Department(String id){
		super(id);
	}

	@ExcelField(title="等级标识", align=2, sort=6)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@ExcelField(title="所属酒店", align=2, sort=7)
	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	
	@ExcelField(title="状态", align=2, sort=8)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="系统officeId", fieldType=String.class, value="", align=2, sort=9)
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
}