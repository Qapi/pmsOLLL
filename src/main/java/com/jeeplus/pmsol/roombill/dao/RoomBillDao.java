/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.roombill.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.pmsol.roombill.entity.RoomBill;

/**
 * 房单DAO接口
 * @author wangp
 * @version 2017-10-13
 */
@MyBatisDao
public interface RoomBillDao extends CrudDao<RoomBill> {

	
}