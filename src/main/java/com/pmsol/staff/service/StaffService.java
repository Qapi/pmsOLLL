/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pmsol.staff.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.pmsol.staff.entity.Staff;
import com.pmsol.staff.dao.StaffDao;

/**
 * 酒店员工配置Service
 * @author wangp
 * @version 2017-09-29
 */
@Service
@Transactional(readOnly = true)
public class StaffService extends CrudService<StaffDao, Staff> {

	public Staff get(String id) {
		return super.get(id);
	}
	
	public List<Staff> findList(Staff staff) {
		return super.findList(staff);
	}
	
	public Page<Staff> findPage(Page<Staff> page, Staff staff) {
		return super.findPage(page, staff);
	}
	
	@Transactional(readOnly = false)
	public void save(Staff staff) {
		super.save(staff);
	}
	
	@Transactional(readOnly = false)
	public void delete(Staff staff) {
		super.delete(staff);
	}
	
	
	
	
}