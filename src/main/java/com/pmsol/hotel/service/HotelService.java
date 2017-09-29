/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pmsol.hotel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.pmsol.hotel.entity.Hotel;
import com.pmsol.hotel.dao.HotelDao;

/**
 * 酒店物业配置Service
 * @author wangp
 * @version 2017-09-29
 */
@Service
@Transactional(readOnly = true)
public class HotelService extends CrudService<HotelDao, Hotel> {

	public Hotel get(String id) {
		return super.get(id);
	}
	
	public List<Hotel> findList(Hotel hotel) {
		return super.findList(hotel);
	}
	
	public Page<Hotel> findPage(Page<Hotel> page, Hotel hotel) {
		return super.findPage(page, hotel);
	}
	
	@Transactional(readOnly = false)
	public void save(Hotel hotel) {
		super.save(hotel);
	}
	
	@Transactional(readOnly = false)
	public void delete(Hotel hotel) {
		super.delete(hotel);
	}
	
	
	
	
}