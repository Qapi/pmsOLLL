/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.wangp.pmsol.hotel.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.wangp.pmsol.hotel.entity.Hotel;

/**
 * 酒店物业配置DAO接口
 * @author wangp
 * @version 2017-09-29
 */
@MyBatisDao
public interface HotelDao extends CrudDao<Hotel> {

	
}