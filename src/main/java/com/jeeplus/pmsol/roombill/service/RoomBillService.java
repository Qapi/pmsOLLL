/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.roombill.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.pmsol.roombill.entity.RoomBill;
import com.jeeplus.pmsol.roombill.dao.RoomBillDao;

/**
 * 房单Service
 * @author wangp
 * @version 2017-10-13
 */
@Service
@Transactional(readOnly = true)
public class RoomBillService extends CrudService<RoomBillDao, RoomBill> {

	public RoomBill get(String id) {
		return super.get(id);
	}
	
	public List<RoomBill> findList(RoomBill roomBill) {
		return super.findList(roomBill);
	}
	
	public Page<RoomBill> findPage(Page<RoomBill> page, RoomBill roomBill) {
		return super.findPage(page, roomBill);
	}
	
	@Transactional(readOnly = false)
	public void save(RoomBill roomBill) {
		super.save(roomBill);
	}
	
	@Transactional(readOnly = false)
	public void delete(RoomBill roomBill) {
		super.delete(roomBill);
	}
	
	
	
	
}