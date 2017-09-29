/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pmsol.roomtype.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.pmsol.roomtype.entity.RoomType;
import com.pmsol.roomtype.service.RoomTypeService;

/**
 * 房型配置Controller
 * @author wangp
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/roomtype/roomType")
public class RoomTypeController extends BaseController {

	@Autowired
	private RoomTypeService roomTypeService;
	
	@ModelAttribute
	public RoomType get(@RequestParam(required=false) String id) {
		RoomType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = roomTypeService.get(id);
		}
		if (entity == null){
			entity = new RoomType();
		}
		return entity;
	}
	
	/**
	 * 房型列表页面
	 */
	@RequiresPermissions("roomtype:roomType:list")
	@RequestMapping(value = {"list", ""})
	public String list(RoomType roomType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RoomType> page = roomTypeService.findPage(new Page<RoomType>(request, response), roomType); 
		model.addAttribute("page", page);
		return "pmsol/roomtype/roomTypeList";
	}

	/**
	 * 查看，增加，编辑房型表单页面
	 */
	@RequiresPermissions(value={"roomtype:roomType:view","roomtype:roomType:add","roomtype:roomType:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(RoomType roomType, Model model) {
		model.addAttribute("roomType", roomType);
		return "pmsol/roomtype/roomTypeForm";
	}

	/**
	 * 保存房型
	 */
	@RequiresPermissions(value={"roomtype:roomType:add","roomtype:roomType:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(RoomType roomType, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, roomType)){
			return form(roomType, model);
		}
		if(!roomType.getIsNewRecord()){//编辑表单保存
			RoomType t = roomTypeService.get(roomType.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(roomType, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			roomTypeService.save(t);//保存
		}else{//新增表单保存
			roomTypeService.save(roomType);//保存
		}
		addMessage(redirectAttributes, "保存房型成功");
		return "redirect:"+Global.getAdminPath()+"/roomtype/roomType/?repage";
	}
	
	/**
	 * 删除房型
	 */
	@RequiresPermissions("roomtype:roomType:del")
	@RequestMapping(value = "delete")
	public String delete(RoomType roomType, RedirectAttributes redirectAttributes) {
		roomTypeService.delete(roomType);
		addMessage(redirectAttributes, "删除房型成功");
		return "redirect:"+Global.getAdminPath()+"/roomtype/roomType/?repage";
	}
	
	/**
	 * 批量删除房型
	 */
	@RequiresPermissions("roomtype:roomType:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			roomTypeService.delete(roomTypeService.get(id));
		}
		addMessage(redirectAttributes, "删除房型成功");
		return "redirect:"+Global.getAdminPath()+"/roomtype/roomType/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("roomtype:roomType:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(RoomType roomType, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "房型"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<RoomType> page = roomTypeService.findPage(new Page<RoomType>(request, response, -1), roomType);
    		new ExportExcel("房型", RoomType.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出房型记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/roomtype/roomType/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("roomtype:roomType:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<RoomType> list = ei.getDataList(RoomType.class);
			for (RoomType roomType : list){
				try{
					roomTypeService.save(roomType);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条房型记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条房型记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入房型失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/roomtype/roomType/?repage";
    }
	
	/**
	 * 下载导入房型数据模板
	 */
	@RequiresPermissions("roomtype:roomType:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "房型数据导入模板.xlsx";
    		List<RoomType> list = Lists.newArrayList(); 
    		new ExportExcel("房型数据", RoomType.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/roomtype/roomType/?repage";
    }
	
	
	

}