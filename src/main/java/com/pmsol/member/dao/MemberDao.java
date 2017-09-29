/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pmsol.member.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.pmsol.member.entity.Member;

/**
 * 会员配置DAO接口
 * @author wangp
 * @version 2017-09-29
 */
@MyBatisDao
public interface MemberDao extends CrudDao<Member> {

	
}