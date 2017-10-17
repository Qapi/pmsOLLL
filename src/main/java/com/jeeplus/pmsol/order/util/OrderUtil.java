package com.jeeplus.pmsol.order.util;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.pmsol.order.entity.Order;
import com.jeeplus.pmsol.order.service.OrderService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by tingting on 2017/10/7.
 */
public class OrderUtil {

	private static OrderService orderService = SpringContextHolder.getBean(OrderService.class);

	// 生成固定格式：OxMMddHHmmssxxxx（x代表随机字符）的订单号
	public static String createOrderNum() {
		return "O" + StringUtils.upperCase(IdGen.randomBase62(1)) + DateUtils.getDate("MMddHHmmss") + StringUtils.upperCase(IdGen.randomBase62(2));
	}

	// 扫描预约中的订单，将已超时的订单设为已过期(过期时间暂时设为次日六时)
	public static int checkExpiredOrder() {
		Order o1 = new Order();
		o1.setCheckInDate(DateUtils.parseDate(DateUtils.formatDate(new Date())));
		List<Order> orderList = orderService.findExpiredList(o1);
		for (Order o2 : orderList) {
			o2.setStatus("1");
			orderService.save(o2);
		}
		return orderList.size();
	}

	// 执行定时器扫描过期订单
	public static void checkOrderStatus() {
		Runnable command = new Runnable() {
			public void run() {
				System.out.println(DateUtils.formatDateTime(new Date()) + "共捕获并修正<" + checkExpiredOrder() + ">个已过期订单");
			}
		};
		long oneDay = 24 * 60 * 60 * 1000; // 一天的毫秒数
		long checkTime = DateUtils.parseDate(DateUtils.formatDate(new Date())).getTime() + 30 * 60 * 60 * 1000; // 次日凌晨六时毫秒数
		long initDelay = checkTime - new Date().getTime(); // 初始化扫描间隔
//		long oneDay = 5 * 1000;
//		long initDelay = 5000;
		// 假如已过当天检查时间，则执行一次检查
		if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > 6) {
			checkExpiredOrder();
		}
		ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		service.scheduleAtFixedRate(command, initDelay, oneDay, TimeUnit.MILLISECONDS);
		System.out.println("订单定时扫描系统已启动！");

	}

}
