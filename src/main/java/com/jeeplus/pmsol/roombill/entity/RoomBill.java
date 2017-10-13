/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.roombill.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 房单Entity
 * @author wangp
 * @version 2017-10-13
 */
public class RoomBill extends DataEntity<RoomBill> {
	
	private static final long serialVersionUID = 1L;
	private String order;		// 订单id
	private String actualResident;		// 实际入住人（集合）
	private String room;		// 入住房间
	private String waterPrice;		// 水费单价（每吨）
	private String electricityPrice;		// 电费单价（每度）
	private String waterFirstReading;		// 水初始读数
	private String electricityFirstReading;		// 电初始读数
	private String waterLastReading;		// 水最终读数
	private String electricityLastReading;		// 电最终读数
	private String managementFee;		// 物业管理费（每月）
	private String checkInTime;		// 入住时间
	private String checkOutTime;		// 离店时间
	private String paymentRecord;		// 支付记录（集合）
	private String depositAmount;		// 押金金额
	private String receivableAmount;		// 应收金额
	private String netreceiptsAmount;		// 实收金额
	private String unreceivedAmount;		// 未收金额
	private String returnAmount;		// 退还金额
	private String hotel;		// 所属酒店
	private String beginCheckInTime;		// 开始 入住时间
	private String endCheckInTime;		// 结束 入住时间
	private String beginCheckOutTime;		// 开始 离店时间
	private String endCheckOutTime;		// 结束 离店时间
	
	public RoomBill() {
		super();
	}

	public RoomBill(String id){
		super(id);
	}

	@ExcelField(title="订单id", align=2, sort=7)
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	@ExcelField(title="实际入住人（集合）", align=2, sort=8)
	public String getActualResident() {
		return actualResident;
	}

	public void setActualResident(String actualResident) {
		this.actualResident = actualResident;
	}
	
	@ExcelField(title="入住房间", align=2, sort=9)
	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	
	@ExcelField(title="水费单价（每吨）", align=2, sort=10)
	public String getWaterPrice() {
		return waterPrice;
	}

	public void setWaterPrice(String waterPrice) {
		this.waterPrice = waterPrice;
	}
	
	@ExcelField(title="电费单价（每度）", align=2, sort=11)
	public String getElectricityPrice() {
		return electricityPrice;
	}

	public void setElectricityPrice(String electricityPrice) {
		this.electricityPrice = electricityPrice;
	}
	
	@ExcelField(title="水初始读数", align=2, sort=12)
	public String getWaterFirstReading() {
		return waterFirstReading;
	}

	public void setWaterFirstReading(String waterFirstReading) {
		this.waterFirstReading = waterFirstReading;
	}
	
	@ExcelField(title="电初始读数", align=2, sort=13)
	public String getElectricityFirstReading() {
		return electricityFirstReading;
	}

	public void setElectricityFirstReading(String electricityFirstReading) {
		this.electricityFirstReading = electricityFirstReading;
	}
	
	@ExcelField(title="水最终读数", align=2, sort=14)
	public String getWaterLastReading() {
		return waterLastReading;
	}

	public void setWaterLastReading(String waterLastReading) {
		this.waterLastReading = waterLastReading;
	}
	
	@ExcelField(title="电最终读数", align=2, sort=15)
	public String getElectricityLastReading() {
		return electricityLastReading;
	}

	public void setElectricityLastReading(String electricityLastReading) {
		this.electricityLastReading = electricityLastReading;
	}
	
	@ExcelField(title="物业管理费（每月）", align=2, sort=16)
	public String getManagementFee() {
		return managementFee;
	}

	public void setManagementFee(String managementFee) {
		this.managementFee = managementFee;
	}
	
	@ExcelField(title="入住时间", align=2, sort=17)
	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}
	
	@ExcelField(title="离店时间", align=2, sort=18)
	public String getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	
	@ExcelField(title="支付记录（集合）", align=2, sort=19)
	public String getPaymentRecord() {
		return paymentRecord;
	}

	public void setPaymentRecord(String paymentRecord) {
		this.paymentRecord = paymentRecord;
	}
	
	@ExcelField(title="押金金额", align=2, sort=20)
	public String getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}
	
	@ExcelField(title="应收金额", align=2, sort=21)
	public String getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(String receivableAmount) {
		this.receivableAmount = receivableAmount;
	}
	
	@ExcelField(title="实收金额", align=2, sort=22)
	public String getNetreceiptsAmount() {
		return netreceiptsAmount;
	}

	public void setNetreceiptsAmount(String netreceiptsAmount) {
		this.netreceiptsAmount = netreceiptsAmount;
	}
	
	@ExcelField(title="未收金额", align=2, sort=23)
	public String getUnreceivedAmount() {
		return unreceivedAmount;
	}

	public void setUnreceivedAmount(String unreceivedAmount) {
		this.unreceivedAmount = unreceivedAmount;
	}
	
	@ExcelField(title="退还金额", align=2, sort=24)
	public String getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(String returnAmount) {
		this.returnAmount = returnAmount;
	}
	
	@ExcelField(title="所属酒店", align=2, sort=25)
	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}
	
	public String getBeginCheckInTime() {
		return beginCheckInTime;
	}

	public void setBeginCheckInTime(String beginCheckInTime) {
		this.beginCheckInTime = beginCheckInTime;
	}
	
	public String getEndCheckInTime() {
		return endCheckInTime;
	}

	public void setEndCheckInTime(String endCheckInTime) {
		this.endCheckInTime = endCheckInTime;
	}
		
	public String getBeginCheckOutTime() {
		return beginCheckOutTime;
	}

	public void setBeginCheckOutTime(String beginCheckOutTime) {
		this.beginCheckOutTime = beginCheckOutTime;
	}
	
	public String getEndCheckOutTime() {
		return endCheckOutTime;
	}

	public void setEndCheckOutTime(String endCheckOutTime) {
		this.endCheckOutTime = endCheckOutTime;
	}
		
}