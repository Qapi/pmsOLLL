/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.wangp.pmsol.roomtype.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.wangp.pmsol.roomtype.entity.RoomType;
import com.wangp.pmsol.roomtype.dao.RoomTypeDao;

/**
 * 房型配置Service
 * @author wangp
 * @version 2017-09-29
 */
@Service
@Transactional(readOnly = true)
public class RoomTypeService extends CrudService<RoomTypeDao, RoomType> {

	public RoomType get(String id) {
		return super.get(id);
	}
	
	public List<RoomType> findList(RoomType roomType) {
		return super.findList(roomType);
	}
	
	public Page<RoomType> findPage(Page<RoomType> page, RoomType roomType) {
		return super.findPage(page, roomType);
	}
	
	@Transactional(readOnly = false)
	public void save(RoomType roomType) {
		super.save(roomType);
	}
	
	@Transactional(readOnly = false)
	public void delete(RoomType roomType) {
		super.delete(roomType);
	}
	
	
	
	
}