/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.room.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.pmsol.hotel.entity.Hotel;
import com.jeeplus.pmsol.hotel.service.HotelService;
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
import com.jeeplus.pmsol.room.entity.Room;
import com.jeeplus.pmsol.room.service.RoomService;

/**
 * 房间配置Controller
 * @author wangp
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/room/room")
public class RoomController extends BaseController {

	@Autowired
	private RoomService roomService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private RoomTypeService roomTypeService;

	@ModelAttribute
	public Room get(@RequestParam(required=false) String id) {
		Room entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = roomService.get(id);
		}
		if (entity == null){
			entity = new Room();
		}
		return entity;
	}
	
	/**
	 * 房间列表页面
	 */
	@RequiresPermissions("room:room:list")
	@RequestMapping(value = {"list", ""})
	public String list(Room room, Hotel hotel, RoomType roomType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Room> page = roomService.findPage(new Page<Room>(request, response), room);
		List<Hotel> hotelList = hotelService.findList(hotel);
		List<RoomType> roomTypeList = roomTypeService.findList(roomType);
		model.addAttribute("page", page);
		model.addAttribute("hotels", hotelList);
		model.addAttribute("roomTypes", roomTypeList);
		return "pmsol/room/roomList";
	}

	/**
	 * 查看，增加，编辑房间表单页面
	 */
	@RequiresPermissions(value={"room:room:view","room:room:add","room:room:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Room room, Model model) {
		model.addAttribute("room", room);
		return "pmsol/room/roomForm";
	}

	/**
	 * 获取房间——ajax
	 */
	@RequiresPermissions(value = {"room:room:add", "room:room:edit"}, logical = Logical.OR)
	@RequestMapping(value = "getModel/{id}")
	@ResponseBody
	public ResponseEntity<Room> getModel(@PathVariable String id) throws Exception {
		if (StringUtils.isBlank(id)) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		Room t = roomService.get(id);
		if (t == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(t, HttpStatus.OK);
	}

	/**
	 * 获取所有或根据房型选择房间——ajax
	 */
	@RequiresPermissions(value="room:room:view")
	@RequestMapping(value = "getList")
	@ResponseBody
	public ResponseEntity<List<Room>> getList(Room room , String roomTypeId) throws Exception{
		if(StringUtils.isNotBlank(roomTypeId)){
			// 动态切换房型进行房间选择
			RoomType roomType = new RoomType();
			roomType.setId(roomTypeId);
			room.setRoomType(roomType);
		}
		List<Room> list = roomService.findList(room);
		return new ResponseEntity(list, HttpStatus.OK);
	}


	/**
	 * 保存房间
	 */
	@RequiresPermissions(value={"room:room:add","room:room:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Room room, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, room)){
			return form(room, model);
		}
		if(!room.getIsNewRecord()){//编辑表单保存
			Room t = roomService.get(room.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(room, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			roomService.save(t);//保存
		}else{//新增表单保存
			roomService.save(room);//保存
		}
		addMessage(redirectAttributes, "保存房间成功");
		return "redirect:"+Global.getAdminPath()+"/room/room/?repage";
	}
	
	/**
	 * 删除房间
	 */
	@RequiresPermissions("room:room:del")
	@RequestMapping(value = "delete")
	public String delete(Room room, RedirectAttributes redirectAttributes) {
		roomService.delete(room);
		addMessage(redirectAttributes, "删除房间成功");
		return "redirect:"+Global.getAdminPath()+"/room/room/?repage";
	}
	
	/**
	 * 批量删除房间
	 */
	@RequiresPermissions("room:room:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			roomService.delete(roomService.get(id));
		}
		addMessage(redirectAttributes, "删除房间成功");
		return "redirect:"+Global.getAdminPath()+"/room/room/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("room:room:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Room room, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "房间"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Room> page = roomService.findPage(new Page<Room>(request, response, -1), room);
    		new ExportExcel("房间", Room.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出房间记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/room/room/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("room:room:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Room> list = ei.getDataList(Room.class);
			for (Room room : list){
				try{
					roomService.save(room);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条房间记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条房间记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入房间失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/room/room/?repage";
    }
	
	/**
	 * 下载导入房间数据模板
	 */
	@RequiresPermissions("room:room:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "房间数据导入模板.xlsx";
    		List<Room> list = Lists.newArrayList(); 
    		new ExportExcel("房间数据", Room.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/room/room/?repage";
    }
	
	
	

}