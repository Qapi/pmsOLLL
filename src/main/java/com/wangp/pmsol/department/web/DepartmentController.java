/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.wangp.pmsol.department.web;

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
import com.wangp.pmsol.department.entity.Department;
import com.wangp.pmsol.department.service.DepartmentService;

/**
 * 部门配置Controller
 * @author wangp
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/department/department")
public class DepartmentController extends BaseController {

	@Autowired
	private DepartmentService departmentService;
	
	@ModelAttribute
	public Department get(@RequestParam(required=false) String id) {
		Department entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = departmentService.get(id);
		}
		if (entity == null){
			entity = new Department();
		}
		return entity;
	}
	
	/**
	 * 部门列表页面
	 */
	@RequiresPermissions("department:department:list")
	@RequestMapping(value = {"list", ""})
	public String list(Department department, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Department> page = departmentService.findPage(new Page<Department>(request, response), department); 
		model.addAttribute("page", page);
		return "pmsol/department/departmentList";
	}

	/**
	 * 查看，增加，编辑部门表单页面
	 */
	@RequiresPermissions(value={"department:department:view","department:department:add","department:department:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Department department, Model model) {
		model.addAttribute("department", department);
		return "pmsol/department/departmentForm";
	}

	/**
	 * 保存部门
	 */
	@RequiresPermissions(value={"department:department:add","department:department:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Department department, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, department)){
			return form(department, model);
		}
		if(!department.getIsNewRecord()){//编辑表单保存
			Department t = departmentService.get(department.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(department, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			departmentService.save(t);//保存
		}else{//新增表单保存
			departmentService.save(department);//保存
		}
		addMessage(redirectAttributes, "保存部门成功");
		return "redirect:"+Global.getAdminPath()+"/department/department/?repage";
	}
	
	/**
	 * 删除部门
	 */
	@RequiresPermissions("department:department:del")
	@RequestMapping(value = "delete")
	public String delete(Department department, RedirectAttributes redirectAttributes) {
		departmentService.delete(department);
		addMessage(redirectAttributes, "删除部门成功");
		return "redirect:"+Global.getAdminPath()+"/department/department/?repage";
	}
	
	/**
	 * 批量删除部门
	 */
	@RequiresPermissions("department:department:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			departmentService.delete(departmentService.get(id));
		}
		addMessage(redirectAttributes, "删除部门成功");
		return "redirect:"+Global.getAdminPath()+"/department/department/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("department:department:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Department department, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "部门"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Department> page = departmentService.findPage(new Page<Department>(request, response, -1), department);
    		new ExportExcel("部门", Department.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出部门记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/department/department/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("department:department:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Department> list = ei.getDataList(Department.class);
			for (Department department : list){
				try{
					departmentService.save(department);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条部门记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条部门记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入部门失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/department/department/?repage";
    }
	
	/**
	 * 下载导入部门数据模板
	 */
	@RequiresPermissions("department:department:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "部门数据导入模板.xlsx";
    		List<Department> list = Lists.newArrayList(); 
    		new ExportExcel("部门数据", Department.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/department/department/?repage";
    }
	
	
	

}