/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.staff.web;

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
import com.jeeplus.pmsol.staff.entity.Staff;
import com.jeeplus.pmsol.staff.service.StaffService;

/**
 * 酒店员工配置Controller
 * @author wangp
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/staff/staff")
public class StaffController extends BaseController {

	@Autowired
	private StaffService staffService;
	
	@ModelAttribute
	public Staff get(@RequestParam(required=false) String id) {
		Staff entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = staffService.get(id);
		}
		if (entity == null){
			entity = new Staff();
		}
		return entity;
	}
	
	/**
	 * 酒店员工列表页面
	 */
	@RequiresPermissions("staff:staff:list")
	@RequestMapping(value = {"list", ""})
	public String list(Staff staff, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Staff> page = staffService.findPage(new Page<Staff>(request, response), staff); 
		model.addAttribute("page", page);
		return "pmsol/staff/staffList";
	}

	/**
	 * 查看，增加，编辑酒店员工表单页面
	 */
	@RequiresPermissions(value={"staff:staff:view","staff:staff:add","staff:staff:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Staff staff, Model model) {
		model.addAttribute("staff", staff);
		return "pmsol/staff/staffForm";
	}

	/**
	 * 保存酒店员工
	 */
	@RequiresPermissions(value={"staff:staff:add","staff:staff:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Staff staff, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, staff)){
			return form(staff, model);
		}
		if(!staff.getIsNewRecord()){//编辑表单保存
			Staff t = staffService.get(staff.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(staff, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			staffService.save(t);//保存
		}else{//新增表单保存
			staffService.save(staff);//保存
		}
		addMessage(redirectAttributes, "保存酒店员工成功");
		return "redirect:"+Global.getAdminPath()+"/staff/staff/?repage";
	}
	
	/**
	 * 删除酒店员工
	 */
	@RequiresPermissions("staff:staff:del")
	@RequestMapping(value = "delete")
	public String delete(Staff staff, RedirectAttributes redirectAttributes) {
		staffService.delete(staff);
		addMessage(redirectAttributes, "删除酒店员工成功");
		return "redirect:"+Global.getAdminPath()+"/staff/staff/?repage";
	}
	
	/**
	 * 批量删除酒店员工
	 */
	@RequiresPermissions("staff:staff:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			staffService.delete(staffService.get(id));
		}
		addMessage(redirectAttributes, "删除酒店员工成功");
		return "redirect:"+Global.getAdminPath()+"/staff/staff/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("staff:staff:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Staff staff, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "酒店员工"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Staff> page = staffService.findPage(new Page<Staff>(request, response, -1), staff);
    		new ExportExcel("酒店员工", Staff.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出酒店员工记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/staff/staff/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("staff:staff:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Staff> list = ei.getDataList(Staff.class);
			for (Staff staff : list){
				try{
					staffService.save(staff);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条酒店员工记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条酒店员工记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入酒店员工失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/staff/staff/?repage";
    }
	
	/**
	 * 下载导入酒店员工数据模板
	 */
	@RequiresPermissions("staff:staff:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "酒店员工数据导入模板.xlsx";
    		List<Staff> list = Lists.newArrayList(); 
    		new ExportExcel("酒店员工数据", Staff.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/staff/staff/?repage";
    }
	
	
	

}