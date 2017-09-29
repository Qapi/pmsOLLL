/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pmsol.roomres.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.pmsol.roomres.entity.RoomRes;
import com.pmsol.roomres.dao.RoomResDao;

/**
 * 房间资源配置Service
 * @author wangp
 * @version 2017-09-29
 */
@Service
@Transactional(readOnly = true)
public class RoomResService extends CrudService<RoomResDao, RoomRes> {

	public RoomRes get(String id) {
		return super.get(id);
	}
	
	public List<RoomRes> findList(RoomRes roomRes) {
		return super.findList(roomRes);
	}
	
	public Page<RoomRes> findPage(Page<RoomRes> page, RoomRes roomRes) {
		return super.findPage(page, roomRes);
	}
	
	@Transactional(readOnly = false)
	public void save(RoomRes roomRes) {
		super.save(roomRes);
	}
	
	@Transactional(readOnly = false)
	public void delete(RoomRes roomRes) {
		super.delete(roomRes);
	}
	
	
	
	
}