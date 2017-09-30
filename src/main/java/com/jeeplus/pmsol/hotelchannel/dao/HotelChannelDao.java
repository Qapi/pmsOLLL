/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.hotelchannel.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.pmsol.hotelchannel.entity.HotelChannel;

/**
 * 酒店销售渠道配置DAO接口
 * @author wangp
 * @version 2017-09-29
 */
@MyBatisDao
public interface HotelChannelDao extends CrudDao<HotelChannel> {

	
}