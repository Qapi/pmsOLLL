/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.payment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.pmsol.payment.entity.Payment;
import com.jeeplus.pmsol.payment.dao.PaymentDao;

/**
 * 支付信息记录Service
 * @author wangp
 * @version 2017-10-13
 */
@Service
@Transactional(readOnly = true)
public class PaymentService extends CrudService<PaymentDao, Payment> {

	public Payment get(String id) {
		return super.get(id);
	}
	
	public List<Payment> findList(Payment payment) {
		return super.findList(payment);
	}
	
	public Page<Payment> findPage(Page<Payment> page, Payment payment) {
		return super.findPage(page, payment);
	}
	
	@Transactional(readOnly = false)
	public void save(Payment payment) {
		super.save(payment);
	}
	
	@Transactional(readOnly = false)
	public void delete(Payment payment) {
		super.delete(payment);
	}
	
	
	
	
}