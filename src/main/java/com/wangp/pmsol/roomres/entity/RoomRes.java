/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.wangp.pmsol.roomres.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 房间资源配置Entity
 * @author wangp
 * @version 2017-09-29
 */
public class RoomRes extends DataEntity<RoomRes> {
	
	private static final long serialVersionUID = 1L;
	private String roomNum;		// 房间号
	private String roomId;		// 房间id
	private String hotelId;		// 所属酒店
	private String roomTypeId;		// 所属房型
	private String resDate;		// 日期
	private String status;		// 状态
	private String beginResDate;		// 开始 日期
	private String endResDate;		// 结束 日期
	
	public RoomRes() {
		super();
	}

	public RoomRes(String id){
		super(id);
	}

	@ExcelField(title="房间号", align=2, sort=7)
	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}
	
	@ExcelField(title="房间id", align=2, sort=8)
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
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
	
	@ExcelField(title="日期", align=2, sort=11)
	public String getResDate() {
		return resDate;
	}

	public void setResDate(String resDate) {
		this.resDate = resDate;
	}
	
	@ExcelField(title="状态", align=2, sort=12)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getBeginResDate() {
		return beginResDate;
	}

	public void setBeginResDate(String beginResDate) {
		this.beginResDate = beginResDate;
	}
	
	public String getEndResDate() {
		return endResDate;
	}

	public void setEndResDate(String endResDate) {
		this.endResDate = endResDate;
	}
		
}