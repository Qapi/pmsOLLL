/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.department.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.pmsol.department.entity.Department;
import com.jeeplus.pmsol.department.dao.DepartmentDao;

/**
 * 部门配置Service
 * @author wangp
 * @version 2017-09-29
 */
@Service
@Transactional(readOnly = true)
public class DepartmentService extends CrudService<DepartmentDao, Department> {

	public Department get(String id) {
		return super.get(id);
	}
	
	public List<Department> findList(Department department) {
		return super.findList(department);
	}
	
	public Page<Department> findPage(Page<Department> page, Department department) {
		return super.findPage(page, department);
	}
	
	@Transactional(readOnly = false)
	public void save(Department department) {
		super.save(department);
	}
	
	@Transactional(readOnly = false)
	public void delete(Department department) {
		super.delete(department);
	}
	
	
	
	
}