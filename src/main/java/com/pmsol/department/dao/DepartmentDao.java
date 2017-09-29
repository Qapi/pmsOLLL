/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pmsol.department.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.pmsol.department.entity.Department;

/**
 * 部门配置DAO接口
 * @author wangp
 * @version 2017-09-29
 */
@MyBatisDao
public interface DepartmentDao extends CrudDao<Department> {

	
}