/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.payment.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.pmsol.payment.entity.Payment;

/**
 * 支付信息记录DAO接口
 * @author wangp
 * @version 2017-10-13
 */
@MyBatisDao
public interface PaymentDao extends CrudDao<Payment> {

	
}