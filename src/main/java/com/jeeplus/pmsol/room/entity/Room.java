/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.room.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.pmsol.hotel.entity.Hotel;
import com.jeeplus.pmsol.roomtype.entity.RoomType;

/**
 * 房间配置Entity
 * @author wangp
 * @version 2017-09-29
 */
public class Room extends DataEntity<Room> {
	
	private static final long serialVersionUID = 1L;
	private String roomNum;		// 房间号
	private String topicName;		// 主题名称/房间别名
	private Hotel hotel;		// 所属酒店
	private String floorNum;		// 楼层
	private String layout;		// 户型
	private RoomType roomType;		// 所属房型
	private String bedType;		// 床型
	private String status;		// 状态
	
	public Room() {
		super();
	}

	public Room(String id){
		super(id);
	}

	@ExcelField(title="房间号", align=2, sort=7)
	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}
	
	@ExcelField(title="主题名称/房间别名", align=2, sort=8)
	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
	@ExcelField(title="所属酒店", align=2, sort=9)
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	@ExcelField(title="楼层", align=2, sort=10)
	public String getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(String floorNum) {
		this.floorNum = floorNum;
	}
	
	@ExcelField(title="户型", align=2, sort=11)
	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}
	
	@ExcelField(title="所属房型", align=2, sort=12)
	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	
	@ExcelField(title="床型", align=2, sort=13)
	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}
	
	@ExcelField(title="状态", align=2, sort=14)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}