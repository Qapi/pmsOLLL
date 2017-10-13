/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.resident.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.pmsol.resident.entity.Resident;
import com.jeeplus.pmsol.resident.dao.ResidentDao;

/**
 * 入住人Service
 * @author wangp
 * @version 2017-10-13
 */
@Service
@Transactional(readOnly = true)
public class ResidentService extends CrudService<ResidentDao, Resident> {

	public Resident get(String id) {
		return super.get(id);
	}
	
	public List<Resident> findList(Resident resident) {
		return super.findList(resident);
	}
	
	public Page<Resident> findPage(Page<Resident> page, Resident resident) {
		return super.findPage(page, resident);
	}
	
	@Transactional(readOnly = false)
	public void save(Resident resident) {
		super.save(resident);
	}
	
	@Transactional(readOnly = false)
	public void delete(Resident resident) {
		super.delete(resident);
	}
	
	
	
	
}