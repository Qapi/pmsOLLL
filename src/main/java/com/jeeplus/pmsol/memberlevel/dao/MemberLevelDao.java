/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.memberlevel.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.pmsol.memberlevel.entity.MemberLevel;

/**
 * 会员等级配置DAO接口
 * @author wangp
 * @version 2017-09-29
 */
@MyBatisDao
public interface MemberLevelDao extends CrudDao<MemberLevel> {

	
}