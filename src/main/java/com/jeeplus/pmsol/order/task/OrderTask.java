package com.jeeplus.pmsol.order.task;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.pmsol.order.entity.Order;
import com.jeeplus.pmsol.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by tingting on 2017/12/5.
 */
@Service
@Lazy(false)
public class OrderTask {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static OrderService orderService = SpringContextHolder.getBean(OrderService.class);


	// 执行定时器扫描预约中的订单，将已超时的订单设为已过期(过期时间暂时设为次日六时)
	@Scheduled(cron="6 6 6 * * ?")
	public void checkOrderStatus() {
		logger.info("定时器开始扫描已超时订单");
		Order o1 = new Order();
		o1.setCheckInDate(DateUtils.parseDate(DateUtils.formatDate(new Date())));
		List<Order> orderList = orderService.findExpiredList(o1);
		orderList.forEach(o2->{
			o2.setStatus("1");
			orderService.save(o2);
		});
		logger.info("定时器共捕获并修正<{}>个已过时订单",orderList.size());
	}

}
