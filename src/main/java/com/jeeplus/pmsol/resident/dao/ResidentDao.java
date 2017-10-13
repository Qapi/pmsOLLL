/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.resident.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.pmsol.resident.entity.Resident;

/**
 * 入住人DAO接口
 * @author wangp
 * @version 2017-10-13
 */
@MyBatisDao
public interface ResidentDao extends CrudDao<Resident> {

	
}