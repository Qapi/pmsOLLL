/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.order.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.pmsol.hotel.entity.Hotel;
import com.jeeplus.pmsol.hotelchannel.entity.HotelChannel;
import com.jeeplus.pmsol.member.entity.Member;
import com.jeeplus.pmsol.room.entity.Room;
import com.jeeplus.pmsol.roomtype.entity.RoomType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单配置Entity
 *
 * @author wangp
 * @version 2017-09-29
 */
public class Order extends DataEntity<Order> {

	private static final long serialVersionUID = 1L;
	private String orderNum;        // 订单号
	private String chlOrderNum;        //外部渠道订单号
	private HotelChannel channel;        // 所属渠道
	private Hotel hotel;        // 所属酒店
	private RoomType roomType;        // 所属房型
	private String leaseMode;        // 租赁类型  	日租/钟点/月租
	private String rentMonths;        // 长租月数
	private Date checkInDate;        // 入住日期
	private Date checkOutDate;        // 预离日期
	private String liveHours;        // 入住小时数
	private String liveDays;        // 入住天数
	private BigDecimal dailyPrice;    // 每天单价
	private BigDecimal monthlyRent;    // 每月单价
	private BigDecimal hourPrice;        // 每小时单价
	private BigDecimal totalAmount;        // 订单总额
	private String contacts;        // 入住人
	private String contactsPhone;        // 入住人电话
	private Member booker;        // 预订人id
	private Room bookRoom;        // 预约房间
	private String status;        // 状态
	private Date beginCheckInDate;        // 开始 入住日期
	private Date endCheckInDate;        // 结束 入住日期
	private Date beginCheckOutDate;        // 开始 预离日期
	private Date endCheckOutDate;        // 结束 预离日期

	public Order() {
		super();
	}

	public Order(String id) {
		super(id);
	}

	@ExcelField(title = "订单号", align = 2, sort = 7)
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	@ExcelField(title = "所属渠道", align = 2, sort = 8)
	public HotelChannel getChannel() {
		return channel;
	}

	public void setChannel(HotelChannel channel) {
		this.channel = channel;
	}

	@ExcelField(title = "所属酒店", align = 2, sort = 9)
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	@ExcelField(title = "所属房型", align = 2, sort = 10)
	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	@ExcelField(title = "租期", align = 2, sort = 11)
	public String getLeaseMode() {
		return leaseMode;
	}

	public void setLeaseMode(String leaseMode) {
		this.leaseMode = leaseMode;
	}

	@ExcelField(title = "长租月数", align = 2, sort = 12)
	public String getRentMonths() {
		return rentMonths;
	}

	public void setRentMonths(String rentMonths) {
		this.rentMonths = rentMonths;
	}

	@ExcelField(title = "入住日期", align = 2, sort = 14)
	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	@ExcelField(title = "联系人", align = 2, sort = 15)
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@ExcelField(title = "联系人电话", align = 2, sort = 16)
	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	@ExcelField(title = "预订人", align = 2, sort = 17)
	public Member getBooker() {
		return booker;
	}

	public void setBooker(Member booker) {
		this.booker = booker;
	}

	@ExcelField(title = "预约房间", align = 2, sort = 18)
	public Room getBookRoom() {
		return bookRoom;
	}

	public void setBookRoom(Room bookRoom) {
		this.bookRoom = bookRoom;
	}

	@ExcelField(title = "状态", align = 2, sort = 19)
	public String getStatus() {
		// 防止有扫描器罢工或者设计纰漏所造成的漏网之鱼,过滤预约日期次日6点前的预约中订单
		if (StringUtils.isNotBlank(status) && "0".equals(status)) {
			Long bookTime = this.checkInDate.getTime() + 30 * 60 * 60 * 1000;
			Long checkTime = new Date().getTime();
			if (bookTime.compareTo(checkTime) < 0) {
				setStatus("1");
			}
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getBeginCheckInDate() {
		return beginCheckInDate;
	}

	public void setBeginCheckInDate(Date beginCheckInDate) {
		this.beginCheckInDate = beginCheckInDate;
	}

	public Date getEndCheckInDate() {
		return endCheckInDate;
	}

	public void setEndCheckInDate(Date endCheckInDate) {
		this.endCheckInDate = endCheckInDate;
	}

	public String getLiveDays() {
		return liveDays;
	}

	public void setLiveDays(String liveDays) {
		this.liveDays = liveDays;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Date getBeginCheckOutDate() {
		return beginCheckOutDate;
	}

	public void setBeginCheckOutDate(Date beginCheckOutDate) {
		this.beginCheckOutDate = beginCheckOutDate;
	}

	public Date getEndCheckOutDate() {
		return endCheckOutDate;
	}

	public void setEndCheckOutDate(Date endCheckOutDate) {
		this.endCheckOutDate = endCheckOutDate;
	}

	public BigDecimal getDailyPrice() {
		return dailyPrice;
	}

	public void setDailyPrice(BigDecimal dailyPrice) {
		this.dailyPrice = dailyPrice;
	}

	public BigDecimal getMonthlyRent() {
		return monthlyRent;
	}

	public void setMonthlyRent(BigDecimal monthlyRent) {
		this.monthlyRent = monthlyRent;
	}

	public BigDecimal getHourPrice() {
		return hourPrice;
	}

	public void setHourPrice(BigDecimal hourPrice) {
		this.hourPrice = hourPrice;
	}

	public String getLiveHours() {
		return liveHours;
	}

	public void setLiveHours(String liveHours) {
		this.liveHours = liveHours;
	}

	public String getChlOrderNum() {
		return chlOrderNum;
	}

	public void setChlOrderNum(String chlOrderNum) {
		this.chlOrderNum = chlOrderNum;
	}
}