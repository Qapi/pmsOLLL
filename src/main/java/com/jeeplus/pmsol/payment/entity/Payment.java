/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.payment.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 支付信息记录Entity
 * @author wangp
 * @version 2017-10-13
 */
public class Payment extends DataEntity<Payment> {
	
	private static final long serialVersionUID = 1L;
	private String roomBillId;		// 所属房单
	private String hotelId;		// 所属酒店
	private String payMethod;		// 支付方式
	private String payPeriod;		// 支付周期（单位为月或者日）
	private String payAmount;		// 支付金额
	private String payCardNum;		// 支付卡号
	private String payOrderNum;		// 支付订单号
	private String payee;		// 收款人
	private String isArrival;		// 是否到账（默认是）
	private String payContent;		// 费用内容（如每月水电详情）
	
	public Payment() {
		super();
	}

	public Payment(String id){
		super(id);
	}

	@ExcelField(title="所属房单", align=2, sort=6)
	public String getRoomBillId() {
		return roomBillId;
	}

	public void setRoomBillId(String roomBillId) {
		this.roomBillId = roomBillId;
	}
	
	@ExcelField(title="所属酒店", align=2, sort=7)
	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	
	@ExcelField(title="支付方式", align=2, sort=8)
	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	
	@ExcelField(title="支付周期（单位为月或者日）", align=2, sort=9)
	public String getPayPeriod() {
		return payPeriod;
	}

	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}
	
	@ExcelField(title="支付金额", align=2, sort=10)
	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	
	@ExcelField(title="支付卡号", align=2, sort=11)
	public String getPayCardNum() {
		return payCardNum;
	}

	public void setPayCardNum(String payCardNum) {
		this.payCardNum = payCardNum;
	}
	
	@ExcelField(title="支付订单号", align=2, sort=12)
	public String getPayOrderNum() {
		return payOrderNum;
	}

	public void setPayOrderNum(String payOrderNum) {
		this.payOrderNum = payOrderNum;
	}
	
	@ExcelField(title="收款人", align=2, sort=13)
	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}
	
	@ExcelField(title="是否到账（默认是）", align=2, sort=14)
	public String getIsArrival() {
		return isArrival;
	}

	public void setIsArrival(String isArrival) {
		this.isArrival = isArrival;
	}
	
	@ExcelField(title="费用内容（如每月水电详情）", align=2, sort=15)
	public String getPayContent() {
		return payContent;
	}

	public void setPayContent(String payContent) {
		this.payContent = payContent;
	}
	
}