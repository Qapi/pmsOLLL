/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pmsol.staff.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.pmsol.staff.entity.Staff;

/**
 * 酒店员工配置DAO接口
 * @author wangp
 * @version 2017-09-29
 */
@MyBatisDao
public interface StaffDao extends CrudDao<Staff> {

	
}