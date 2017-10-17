/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.order.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.pmsol.hotel.entity.Hotel;
import com.jeeplus.pmsol.hotel.service.HotelService;
import com.jeeplus.pmsol.order.util.OrderUtil;
import com.jeeplus.pmsol.room.entity.Room;
import com.jeeplus.pmsol.room.service.RoomService;
import com.jeeplus.pmsol.roomtype.entity.RoomType;
import com.jeeplus.pmsol.roomtype.service.RoomTypeService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.pmsol.order.entity.Order;
import com.jeeplus.pmsol.order.service.OrderService;

/**
 * 订单配置Controller
 *
 * @author wangp
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/order/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomTypeService roomTypeService;
    @Autowired
    private RoomService roomService;

    @ModelAttribute
    public Order get(@RequestParam(required = false) String id) {
        Order entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = orderService.get(id);
        }
        if (entity == null) {
            entity = new Order();
        }
        return entity;
    }

    /**
     * 订单列表页面
     */
    @RequiresPermissions("order:order:list")
    @RequestMapping(value = {"list", ""})
    public String list(Order order, Hotel hotel, Room room, RoomType roomType, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Order> page = orderService.findPage(new Page<Order>(request, response), order);
        List<Hotel> hotelList = hotelService.findList(hotel);
        List<Room> roomList = roomService.findList(room);
        List<RoomType> roomTypeList = roomTypeService.findList(roomType);
        model.addAttribute("page", page);
        model.addAttribute("hotels", hotelList);
        model.addAttribute("rooms", roomList);
        model.addAttribute("roomTypes", roomTypeList);
        return "pmsol/order/orderList";
    }

    /**
     * 获取本日预达
     */
    @RequiresPermissions("order:order:view")
    @RequestMapping(value = "arriveAtToday")
    public String arriveAtToday(Order order, Hotel hotel, RoomType roomType, HttpServletRequest request, HttpServletResponse response, Model model) {
        // 默认取当天入住数据
        if (order.getCheckInDate() == null) {
            order.setCheckInDate(DateUtils.parseDate(DateUtils.formatDate(new Date())));
        }
        // 默认取所在酒店数据
        if(order.getHotel() == null && !UserUtils.getUser().isAdmin()){
            Hotel hotel2 = hotelService.findUniqueByProperty("office_id",UserUtils.getUser().getCompany().getId());
            order.setHotel(hotel2);
        }
        order.setStatus("0");
        Page<Order> page = orderService.findPage(new Page<Order>(request, response), order);
        List<Hotel> hotelList = hotelService.findList(hotel);
        List<RoomType> roomTypeList = roomTypeService.findList(roomType);
        model.addAttribute("page", page);
        model.addAttribute("hotels", hotelList);
        model.addAttribute("roomTypes", roomTypeList);
        return "pmsol/order/arriveAtToday";
    }

    /**
     * 查看，增加，编辑订单表单页面
     */
    @RequiresPermissions(value = {"order:order:view", "order:order:add", "order:order:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(Order order, Model model) {
        model.addAttribute("order", order);
        return "pmsol/order/orderForm";
    }

    /**
     * 获取订单——ajax
     */
    @RequiresPermissions(value = {"order:order:add", "order:order:edit"}, logical = Logical.OR)
    @RequestMapping(value = "getModel/{id}")
    @ResponseBody
    public ResponseEntity<Order> getModel(@PathVariable String id) throws Exception {
        if (StringUtils.isBlank(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Order t = orderService.get(id);
        if (t == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(t, HttpStatus.OK);
    }


    /**
     * 保存订单
     */
    @RequiresPermissions(value = {"order:order:add", "order:order:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(Order order, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, order)) {
            return form(order, model);
        }
        // TODO 需要对订单金额作校验，防止前台数据被强行破坏
        if (!order.getIsNewRecord()) {//编辑表单保存
            Order t = orderService.get(order.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(order, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            orderService.save(t);//保存
        } else {//新增表单保存
            order.setOrderNum(OrderUtil.createOrderNum()); // 设定一定规律的订单号
            orderService.save(order);//保存
        }
        addMessage(redirectAttributes, "保存订单成功");
        return "redirect:" + Global.getAdminPath() + "/order/order/?repage";
    }

    /**
     * 删除订单
     */
    @RequiresPermissions("order:order:del")
    @RequestMapping(value = "delete")
    public String delete(Order order, RedirectAttributes redirectAttributes) {
        orderService.delete(order);
        addMessage(redirectAttributes, "删除订单成功");
        return "redirect:" + Global.getAdminPath() + "/order/order/?repage";
    }

    /**
     * 批量删除订单
     */
    @RequiresPermissions("order:order:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            orderService.delete(orderService.get(id));
        }
        addMessage(redirectAttributes, "删除订单成功");
        return "redirect:" + Global.getAdminPath() + "/order/order/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("order:order:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(Order order, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "订单" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<Order> page = orderService.findPage(new Page<Order>(request, response, -1), order);
            new ExportExcel("订单", Order.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出订单记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/order/order/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("order:order:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<Order> list = ei.getDataList(Order.class);
            for (Order order : list) {
                try {
                    orderService.save(order);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条订单记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条订单记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入订单失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/order/order/?repage";
    }

    /**
     * 下载导入订单数据模板
     */
    @RequiresPermissions("order:order:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "订单数据导入模板.xlsx";
            List<Order> list = Lists.newArrayList();
            new ExportExcel("订单数据", Order.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/order/order/?repage";
    }


}