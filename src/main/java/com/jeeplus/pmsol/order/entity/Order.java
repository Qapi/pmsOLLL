/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.order.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 订单配置Entity
 * @author wangp
 * @version 2017-09-29
 */
public class Order extends DataEntity<Order> {
	
	private static final long serialVersionUID = 1L;
	private String orderNum;		// 订单号
	private String channelId;		// 所属渠道
	private String hotelId;		// 所属酒店
	private String roomTypeId;		// 所属房型
	private String leaseMode;		// 租期
	private String rentMonths;		// 长租月数
	private String bookTime;		// 预订时间
	private String checkInDate;		// 入住日期
	private String contacts;		// 联系人
	private String contactsPhone;		// 联系人电话
	private String memberId;		// 预订人id
	private String bookRoomId;		// 预约房间
	private String status;		// 状态
	private String beginCheckInDate;		// 开始 入住日期
	private String endCheckInDate;		// 结束 入住日期
	
	public Order() {
		super();
	}

	public Order(String id){
		super(id);
	}

	@ExcelField(title="订单号", align=2, sort=7)
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	@ExcelField(title="所属渠道", align=2, sort=8)
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	@ExcelField(title="所属酒店", align=2, sort=9)
	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	
	@ExcelField(title="所属房型", align=2, sort=10)
	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	
	@ExcelField(title="租期", align=2, sort=11)
	public String getLeaseMode() {
		return leaseMode;
	}

	public void setLeaseMode(String leaseMode) {
		this.leaseMode = leaseMode;
	}
	
	@ExcelField(title="长租月数", align=2, sort=12)
	public String getRentMonths() {
		return rentMonths;
	}

	public void setRentMonths(String rentMonths) {
		this.rentMonths = rentMonths;
	}
	
	@ExcelField(title="预订时间", align=2, sort=13)
	public String getBookTime() {
		return bookTime;
	}

	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}
	
	@ExcelField(title="入住日期", align=2, sort=14)
	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}
	
	@ExcelField(title="联系人", align=2, sort=15)
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	@ExcelField(title="联系人电话", align=2, sort=16)
	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}
	
	@ExcelField(title="预订人id", align=2, sort=17)
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@ExcelField(title="预约房间", align=2, sort=18)
	public String getBookRoomId() {
		return bookRoomId;
	}

	public void setBookRoomId(String bookRoomId) {
		this.bookRoomId = bookRoomId;
	}
	
	@ExcelField(title="状态", align=2, sort=19)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getBeginCheckInDate() {
		return beginCheckInDate;
	}

	public void setBeginCheckInDate(String beginCheckInDate) {
		this.beginCheckInDate = beginCheckInDate;
	}
	
	public String getEndCheckInDate() {
		return endCheckInDate;
	}

	public void setEndCheckInDate(String endCheckInDate) {
		this.endCheckInDate = endCheckInDate;
	}
		
}