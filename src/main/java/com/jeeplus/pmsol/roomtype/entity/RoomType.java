/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.roomtype.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.pmsol.hotel.entity.Hotel;

import java.math.BigDecimal;

/**
 * 房型配置Entity
 * @author wangp
 * @version 2017-09-29
 */
public class RoomType extends DataEntity<RoomType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 房型名称
	private String capacity;		// 可入住人数
	private BigDecimal dailyPrice;		// 平日价
	private BigDecimal weekendPrice;		// 周末价
	private BigDecimal holidayPrice;		// 节日价
	private BigDecimal hourPrice;		// 钟点价
	private String hourNum; 			// 钟点房小时数
	private BigDecimal monthlyRent;		// 月租价
	private String bedNum;		// 床位数
	private String bedType;		// 床型
	private Hotel hotel;		// 所属酒店
	private String beginDailyPrice;		// 开始 平日价
	private String endDailyPrice;		// 结束 平日价
	private String beginHolidayPrice;		// 开始 节假日价
	private String endHolidayPrice;		// 结束 节假日价
	private String beginHourPrice;		// 开始 钟点房价
	private String endHourPrice;		// 结束 钟点房价
	private String beginMonthlyRent;		// 开始 月租价
	private String endMonthlyRent;		// 结束 月租价
	
	public RoomType() {
		super();
	}

	public RoomType(String id){
		super(id);
	}

	@ExcelField(title="房型名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="可入住人数", align=2, sort=8)
	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	
	@ExcelField(title="平日价", align=2, sort=9)
	public BigDecimal getDailyPrice() {
		return dailyPrice;
	}

	public void setDailyPrice(BigDecimal dailyPrice) {
		this.dailyPrice = dailyPrice;
	}
	
	@ExcelField(title="节假日价", align=2, sort=10)
	public BigDecimal getHolidayPrice() {
		return holidayPrice;
	}

	public void setHolidayPrice(BigDecimal holidayPrice) {
		this.holidayPrice = holidayPrice;
	}
	
	@ExcelField(title="钟点房价", align=2, sort=11)
	public BigDecimal getHourPrice() {
		return hourPrice;
	}

	public void setHourPrice(BigDecimal hourPrice) {
		this.hourPrice = hourPrice;
	}
	
	@ExcelField(title="月租价", align=2, sort=12)
	public BigDecimal getMonthlyRent() {
		return monthlyRent;
	}

	public void setMonthlyRent(BigDecimal monthlyRent) {
		this.monthlyRent = monthlyRent;
	}
	
	@ExcelField(title="床位数", align=2, sort=13)
	public String getBedNum() {
		return bedNum;
	}

	public void setBedNum(String bedNum) {
		this.bedNum = bedNum;
	}

	public String getBeginDailyPrice() {
		return beginDailyPrice;
	}

	public void setBeginDailyPrice(String beginDailyPrice) {
		this.beginDailyPrice = beginDailyPrice;
	}
	
	public String getEndDailyPrice() {
		return endDailyPrice;
	}

	public void setEndDailyPrice(String endDailyPrice) {
		this.endDailyPrice = endDailyPrice;
	}
		
	public String getBeginHolidayPrice() {
		return beginHolidayPrice;
	}

	public void setBeginHolidayPrice(String beginHolidayPrice) {
		this.beginHolidayPrice = beginHolidayPrice;
	}
	
	public String getEndHolidayPrice() {
		return endHolidayPrice;
	}

	public void setEndHolidayPrice(String endHolidayPrice) {
		this.endHolidayPrice = endHolidayPrice;
	}
		
	public String getBeginHourPrice() {
		return beginHourPrice;
	}

	public void setBeginHourPrice(String beginHourPrice) {
		this.beginHourPrice = beginHourPrice;
	}
	
	public String getEndHourPrice() {
		return endHourPrice;
	}

	public void setEndHourPrice(String endHourPrice) {
		this.endHourPrice = endHourPrice;
	}
		
	public String getBeginMonthlyRent() {
		return beginMonthlyRent;
	}

	public void setBeginMonthlyRent(String beginMonthlyRent) {
		this.beginMonthlyRent = beginMonthlyRent;
	}
	
	public String getEndMonthlyRent() {
		return endMonthlyRent;
	}

	public void setEndMonthlyRent(String endMonthlyRent) {
		this.endMonthlyRent = endMonthlyRent;
	}

	@ExcelField(title="钟点房小时数", align=2, sort=15)
	public String getHourNum() {
		return hourNum;
	}

	public void setHourNum(String hourNum) {
		this.hourNum = hourNum;
	}

	@ExcelField(title="所属酒店Id", align=2, sort=14)
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public BigDecimal getWeekendPrice() {
		return weekendPrice;
	}

	public void setWeekendPrice(BigDecimal weekendPrice) {
		this.weekendPrice = weekendPrice;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}
}