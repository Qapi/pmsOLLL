/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.department.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.pmsol.hotel.entity.Hotel;

/**
 * 部门配置Entity
 * @author wangp
 * @version 2017-09-29
 */
public class Department extends DataEntity<Department> {
	
	private static final long serialVersionUID = 1L;
	private String flag;		// 等级标识
	private Hotel hotel;		// 所属酒店
	private String status;		// 状态
	private Office office;		// 系统office
	
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
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	@ExcelField(title="状态", align=2, sort=8)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="系统officeId", fieldType=String.class, value="", align=2, sort=9)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}