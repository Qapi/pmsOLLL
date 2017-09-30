/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.hotelchannel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.pmsol.hotelchannel.entity.HotelChannel;
import com.jeeplus.pmsol.hotelchannel.dao.HotelChannelDao;

/**
 * 酒店销售渠道配置Service
 * @author wangp
 * @version 2017-09-29
 */
@Service
@Transactional(readOnly = true)
public class HotelChannelService extends CrudService<HotelChannelDao, HotelChannel> {

	public HotelChannel get(String id) {
		return super.get(id);
	}
	
	public List<HotelChannel> findList(HotelChannel hotelChannel) {
		return super.findList(hotelChannel);
	}
	
	public Page<HotelChannel> findPage(Page<HotelChannel> page, HotelChannel hotelChannel) {
		return super.findPage(page, hotelChannel);
	}
	
	@Transactional(readOnly = false)
	public void save(HotelChannel hotelChannel) {
		super.save(hotelChannel);
	}
	
	@Transactional(readOnly = false)
	public void delete(HotelChannel hotelChannel) {
		super.delete(hotelChannel);
	}
	
	
	
	
}