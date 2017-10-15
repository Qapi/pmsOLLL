/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.roombill.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.pmsol.hotel.entity.Hotel;
import com.jeeplus.pmsol.hotel.service.HotelService;
import com.jeeplus.pmsol.order.entity.Order;
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
import com.jeeplus.pmsol.roombill.entity.RoomBill;
import com.jeeplus.pmsol.roombill.service.RoomBillService;

/**
 * 房单Controller
 *
 * @author wangp
 * @version 2017-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/roombill/roomBill")
public class RoomBillController extends BaseController {

    @Autowired
    private RoomBillService roomBillService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomTypeService roomTypeService;

    @ModelAttribute
    public RoomBill get(@RequestParam(required = false) String id) {
        RoomBill entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = roomBillService.get(id);
        }
        if (entity == null) {
            entity = new RoomBill();
        }
        return entity;
    }

    /**
     * 房单列表页面
     */
    @RequiresPermissions("roombill:roomBill:list")
    @RequestMapping(value = {"list", ""})
    public String list(RoomBill roomBill, Hotel hotel, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<RoomBill> page = roomBillService.findPage(new Page<RoomBill>(request, response), roomBill);
        List<Hotel> hotelList = hotelService.findList(hotel);
        model.addAttribute("page", page);
        return "pmsol/roombill/roomBillList";
    }

    /**
     * 获取本日预达
     */
    @RequiresPermissions("roombill:roomBill:leaveAtToday")
    @RequestMapping(value = "leaveAtToday")
    public String leaveAtToday(RoomBill roomBill, Hotel hotel, Order order, RoomType roomType, HttpServletRequest request, HttpServletResponse response, Model model) {
        // 默认取当天离店数据
        if (roomBill.getOrder() == null) {
            order.setCheckOutDate(new Date());
            roomBill.setOrder(order);
        }
        // 默认取所在酒店数据
        if (roomBill.getHotel() == null && !UserUtils.getUser().isAdmin()) {
            Hotel hotel2 = hotelService.findUniqueByProperty("office_id", UserUtils.getUser().getCompany().getId());
            roomBill.setHotel(hotel2);
        }
        Page<RoomBill> page = roomBillService.findPage(new Page<RoomBill>(request, response), roomBill);
        List<Hotel> hotelList = hotelService.findList(hotel);
        List<RoomType> roomTypeList = roomTypeService.findList(roomType);
        model.addAttribute("page", page);
        model.addAttribute("hotels", hotelList);
        model.addAttribute("roomTypes", roomTypeList);
        return "pmsol/order/arriveAtToday";
    }

    /**
     * 查看，增加，编辑房单表单页面
     */
    @RequiresPermissions(value = {"roombill:roomBill:view", "roombill:roomBill:add", "roombill:roomBill:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(RoomBill roomBill, Model model) {
        model.addAttribute("roomBill", roomBill);
        return "pmsol/roombill/roomBillForm";
    }

    /**
     * 获取订单——ajax
     */
    @RequiresPermissions(value = {"roombill:roomBill:add", "roombill:roomBill:edit"}, logical = Logical.OR)
    @RequestMapping(value = "getModel/{id}")
    @ResponseBody
    public ResponseEntity<RoomBill> getModel(@PathVariable String id) throws Exception {
        if (StringUtils.isBlank(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        RoomBill t = roomBillService.get(id);
        if (t == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(t, HttpStatus.OK);
    }


    /**
     * 保存房单
     */
    @RequiresPermissions(value = {"roombill:roomBill:add", "roombill:roomBill:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(RoomBill roomBill, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, roomBill)) {
            return form(roomBill, model);
        }
        if (!roomBill.getIsNewRecord()) {//编辑表单保存
            RoomBill t = roomBillService.get(roomBill.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(roomBill, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            roomBillService.save(t);//保存
        } else {//新增表单保存
            roomBillService.save(roomBill);//保存
        }
        addMessage(redirectAttributes, "保存房单成功");
        return "redirect:" + Global.getAdminPath() + "/roombill/roomBill/?repage";
    }

    /**
     * 删除房单
     */
    @RequiresPermissions("roombill:roomBill:del")
    @RequestMapping(value = "delete")
    public String delete(RoomBill roomBill, RedirectAttributes redirectAttributes) {
        roomBillService.delete(roomBill);
        addMessage(redirectAttributes, "删除房单成功");
        return "redirect:" + Global.getAdminPath() + "/roombill/roomBill/?repage";
    }

    /**
     * 批量删除房单
     */
    @RequiresPermissions("roombill:roomBill:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            roomBillService.delete(roomBillService.get(id));
        }
        addMessage(redirectAttributes, "删除房单成功");
        return "redirect:" + Global.getAdminPath() + "/roombill/roomBill/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("roombill:roomBill:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(RoomBill roomBill, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "房单" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<RoomBill> page = roomBillService.findPage(new Page<RoomBill>(request, response, -1), roomBill);
            new ExportExcel("房单", RoomBill.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出房单记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/roombill/roomBill/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("roombill:roomBill:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<RoomBill> list = ei.getDataList(RoomBill.class);
            for (RoomBill roomBill : list) {
                try {
                    roomBillService.save(roomBill);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条房单记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条房单记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入房单失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/roombill/roomBill/?repage";
    }

    /**
     * 下载导入房单数据模板
     */
    @RequiresPermissions("roombill:roomBill:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "房单数据导入模板.xlsx";
            List<RoomBill> list = Lists.newArrayList();
            new ExportExcel("房单数据", RoomBill.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/roombill/roomBill/?repage";
    }


}