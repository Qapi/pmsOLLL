/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.wangp.pmsol.memberlevel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.wangp.pmsol.memberlevel.entity.MemberLevel;
import com.wangp.pmsol.memberlevel.dao.MemberLevelDao;

/**
 * 会员等级配置Service
 * @author wangp
 * @version 2017-09-29
 */
@Service
@Transactional(readOnly = true)
public class MemberLevelService extends CrudService<MemberLevelDao, MemberLevel> {

	public MemberLevel get(String id) {
		return super.get(id);
	}
	
	public List<MemberLevel> findList(MemberLevel memberLevel) {
		return super.findList(memberLevel);
	}
	
	public Page<MemberLevel> findPage(Page<MemberLevel> page, MemberLevel memberLevel) {
		return super.findPage(page, memberLevel);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberLevel memberLevel) {
		super.save(memberLevel);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberLevel memberLevel) {
		super.delete(memberLevel);
	}
	
	
	
	
}