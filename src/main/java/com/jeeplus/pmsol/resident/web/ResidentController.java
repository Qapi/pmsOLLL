/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.resident.web;

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
import com.jeeplus.pmsol.resident.entity.Resident;
import com.jeeplus.pmsol.resident.service.ResidentService;

/**
 * 入住人Controller
 * @author wangp
 * @version 2017-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/resident/resident")
public class ResidentController extends BaseController {

	@Autowired
	private ResidentService residentService;
	
	@ModelAttribute
	public Resident get(@RequestParam(required=false) String id) {
		Resident entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = residentService.get(id);
		}
		if (entity == null){
			entity = new Resident();
		}
		return entity;
	}
	
	/**
	 * 入住人列表页面
	 */
	@RequiresPermissions("resident:resident:list")
	@RequestMapping(value = {"list", ""})
	public String list(Resident resident, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Resident> page = residentService.findPage(new Page<Resident>(request, response), resident); 
		model.addAttribute("page", page);
		return "pmsol/resident/residentList";
	}

	/**
	 * 查看，增加，编辑入住人表单页面
	 */
	@RequiresPermissions(value={"resident:resident:view","resident:resident:add","resident:resident:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Resident resident, Model model) {
		model.addAttribute("resident", resident);
		return "pmsol/resident/residentForm";
	}

	/**
	 * 保存入住人
	 */
	@RequiresPermissions(value={"resident:resident:add","resident:resident:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Resident resident, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resident)){
			return form(resident, model);
		}
		if(!resident.getIsNewRecord()){//编辑表单保存
			Resident t = residentService.get(resident.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(resident, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			residentService.save(t);//保存
		}else{//新增表单保存
			residentService.save(resident);//保存
		}
		addMessage(redirectAttributes, "保存入住人成功");
		return "redirect:"+Global.getAdminPath()+"/resident/resident/?repage";
	}
	
	/**
	 * 删除入住人
	 */
	@RequiresPermissions("resident:resident:del")
	@RequestMapping(value = "delete")
	public String delete(Resident resident, RedirectAttributes redirectAttributes) {
		residentService.delete(resident);
		addMessage(redirectAttributes, "删除入住人成功");
		return "redirect:"+Global.getAdminPath()+"/resident/resident/?repage";
	}
	
	/**
	 * 批量删除入住人
	 */
	@RequiresPermissions("resident:resident:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			residentService.delete(residentService.get(id));
		}
		addMessage(redirectAttributes, "删除入住人成功");
		return "redirect:"+Global.getAdminPath()+"/resident/resident/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("resident:resident:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Resident resident, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "入住人"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Resident> page = residentService.findPage(new Page<Resident>(request, response, -1), resident);
    		new ExportExcel("入住人", Resident.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出入住人记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resident/resident/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("resident:resident:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Resident> list = ei.getDataList(Resident.class);
			for (Resident resident : list){
				try{
					residentService.save(resident);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条入住人记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条入住人记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入入住人失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resident/resident/?repage";
    }
	
	/**
	 * 下载导入入住人数据模板
	 */
	@RequiresPermissions("resident:resident:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "入住人数据导入模板.xlsx";
    		List<Resident> list = Lists.newArrayList(); 
    		new ExportExcel("入住人数据", Resident.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resident/resident/?repage";
    }
	
	
	

}