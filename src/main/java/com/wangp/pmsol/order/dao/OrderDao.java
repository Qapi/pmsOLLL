/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.wangp.pmsol.order.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.wangp.pmsol.order.entity.Order;

/**
 * 订单配置DAO接口
 * @author wangp
 * @version 2017-09-29
 */
@MyBatisDao
public interface OrderDao extends CrudDao<Order> {

	
}