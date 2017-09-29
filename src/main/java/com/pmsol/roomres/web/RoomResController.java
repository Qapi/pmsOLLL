/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pmsol.roomres.web;

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
import com.pmsol.roomres.entity.RoomRes;
import com.pmsol.roomres.service.RoomResService;

/**
 * 房间资源配置Controller
 * @author wangp
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/roomres/roomRes")
public class RoomResController extends BaseController {

	@Autowired
	private RoomResService roomResService;
	
	@ModelAttribute
	public RoomRes get(@RequestParam(required=false) String id) {
		RoomRes entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = roomResService.get(id);
		}
		if (entity == null){
			entity = new RoomRes();
		}
		return entity;
	}
	
	/**
	 * 房间资源列表页面
	 */
	@RequiresPermissions("roomres:roomRes:list")
	@RequestMapping(value = {"list", ""})
	public String list(RoomRes roomRes, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RoomRes> page = roomResService.findPage(new Page<RoomRes>(request, response), roomRes); 
		model.addAttribute("page", page);
		return "pmsol/roomres/roomResList";
	}

	/**
	 * 查看，增加，编辑房间资源表单页面
	 */
	@RequiresPermissions(value={"roomres:roomRes:view","roomres:roomRes:add","roomres:roomRes:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(RoomRes roomRes, Model model) {
		model.addAttribute("roomRes", roomRes);
		return "pmsol/roomres/roomResForm";
	}

	/**
	 * 保存房间资源
	 */
	@RequiresPermissions(value={"roomres:roomRes:add","roomres:roomRes:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(RoomRes roomRes, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, roomRes)){
			return form(roomRes, model);
		}
		if(!roomRes.getIsNewRecord()){//编辑表单保存
			RoomRes t = roomResService.get(roomRes.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(roomRes, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			roomResService.save(t);//保存
		}else{//新增表单保存
			roomResService.save(roomRes);//保存
		}
		addMessage(redirectAttributes, "保存房间资源成功");
		return "redirect:"+Global.getAdminPath()+"/roomres/roomRes/?repage";
	}
	
	/**
	 * 删除房间资源
	 */
	@RequiresPermissions("roomres:roomRes:del")
	@RequestMapping(value = "delete")
	public String delete(RoomRes roomRes, RedirectAttributes redirectAttributes) {
		roomResService.delete(roomRes);
		addMessage(redirectAttributes, "删除房间资源成功");
		return "redirect:"+Global.getAdminPath()+"/roomres/roomRes/?repage";
	}
	
	/**
	 * 批量删除房间资源
	 */
	@RequiresPermissions("roomres:roomRes:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			roomResService.delete(roomResService.get(id));
		}
		addMessage(redirectAttributes, "删除房间资源成功");
		return "redirect:"+Global.getAdminPath()+"/roomres/roomRes/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("roomres:roomRes:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(RoomRes roomRes, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "房间资源"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<RoomRes> page = roomResService.findPage(new Page<RoomRes>(request, response, -1), roomRes);
    		new ExportExcel("房间资源", RoomRes.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出房间资源记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/roomres/roomRes/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("roomres:roomRes:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<RoomRes> list = ei.getDataList(RoomRes.class);
			for (RoomRes roomRes : list){
				try{
					roomResService.save(roomRes);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条房间资源记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条房间资源记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入房间资源失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/roomres/roomRes/?repage";
    }
	
	/**
	 * 下载导入房间资源数据模板
	 */
	@RequiresPermissions("roomres:roomRes:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "房间资源数据导入模板.xlsx";
    		List<RoomRes> list = Lists.newArrayList(); 
    		new ExportExcel("房间资源数据", RoomRes.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/roomres/roomRes/?repage";
    }
	
	
	

}