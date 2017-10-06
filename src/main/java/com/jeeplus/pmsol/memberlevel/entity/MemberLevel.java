/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.memberlevel.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.pmsol.hotel.entity.Hotel;

import java.util.Date;

/**
 * 会员等级配置Entity
 * @author wangp
 * @version 2017-09-29
 */
public class MemberLevel extends DataEntity<MemberLevel> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String flag;		// 等级标识
	private Hotel hotel;		// 所属酒店
	private String salePercent;		// 折扣优惠
	private Date validityTerm;		// 有效期
	private String status;		// 状态
	private String beginSalePercent;		// 开始 折扣优惠
	private String endSalePercent;		// 结束 折扣优惠
	private Date beginValidityTerm;		// 开始 有效期
	private Date endValidityTerm;		// 结束 有效期
	
	public MemberLevel() {
		super();
	}

	public MemberLevel(String id){
		super(id);
	}

	@ExcelField(title="名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="等级标识", align=2, sort=8)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@ExcelField(title="所属酒店", align=2, sort=9)
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	@ExcelField(title="折扣优惠", align=2, sort=10)
	public String getSalePercent() {
		return salePercent;
	}

	public void setSalePercent(String salePercent) {
		this.salePercent = salePercent;
	}
	
	@ExcelField(title="有效期", align=2, sort=11)
	public Date getValidityTerm() {
		return validityTerm;
	}

	public void setValidityTerm(Date validityTerm) {
		this.validityTerm = validityTerm;
	}
	
	@ExcelField(title="状态", align=2, sort=12)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getBeginSalePercent() {
		return beginSalePercent;
	}

	public void setBeginSalePercent(String beginSalePercent) {
		this.beginSalePercent = beginSalePercent;
	}
	
	public String getEndSalePercent() {
		return endSalePercent;
	}

	public void setEndSalePercent(String endSalePercent) {
		this.endSalePercent = endSalePercent;
	}
		
	public Date getBeginValidityTerm() {
		return beginValidityTerm;
	}

	public void setBeginValidityTerm(Date beginValidityTerm) {
		this.beginValidityTerm = beginValidityTerm;
	}
	
	public Date getEndValidityTerm() {
		return endValidityTerm;
	}

	public void setEndValidityTerm(Date endValidityTerm) {
		this.endValidityTerm = endValidityTerm;
	}
		
}