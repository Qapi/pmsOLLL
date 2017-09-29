/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.wangp.pmsol.room.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.wangp.pmsol.room.entity.Room;
import com.wangp.pmsol.room.dao.RoomDao;

/**
 * 房间配置Service
 * @author wangp
 * @version 2017-09-29
 */
@Service
@Transactional(readOnly = true)
public class RoomService extends CrudService<RoomDao, Room> {

	public Room get(String id) {
		return super.get(id);
	}
	
	public List<Room> findList(Room room) {
		return super.findList(room);
	}
	
	public Page<Room> findPage(Page<Room> page, Room room) {
		return super.findPage(page, room);
	}
	
	@Transactional(readOnly = false)
	public void save(Room room) {
		super.save(room);
	}
	
	@Transactional(readOnly = false)
	public void delete(Room room) {
		super.delete(room);
	}
	
	
	
	
}