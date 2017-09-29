/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pmsol.memberlevel.web;

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
import com.pmsol.memberlevel.entity.MemberLevel;
import com.pmsol.memberlevel.service.MemberLevelService;

/**
 * 会员等级配置Controller
 * @author wangp
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/memberlevel/memberLevel")
public class MemberLevelController extends BaseController {

	@Autowired
	private MemberLevelService memberLevelService;
	
	@ModelAttribute
	public MemberLevel get(@RequestParam(required=false) String id) {
		MemberLevel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberLevelService.get(id);
		}
		if (entity == null){
			entity = new MemberLevel();
		}
		return entity;
	}
	
	/**
	 * 会员等级列表页面
	 */
	@RequiresPermissions("memberlevel:memberLevel:list")
	@RequestMapping(value = {"list", ""})
	public String list(MemberLevel memberLevel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberLevel> page = memberLevelService.findPage(new Page<MemberLevel>(request, response), memberLevel); 
		model.addAttribute("page", page);
		return "pmsol/memberlevel/memberLevelList";
	}

	/**
	 * 查看，增加，编辑会员等级表单页面
	 */
	@RequiresPermissions(value={"memberlevel:memberLevel:view","memberlevel:memberLevel:add","memberlevel:memberLevel:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MemberLevel memberLevel, Model model) {
		model.addAttribute("memberLevel", memberLevel);
		return "pmsol/memberlevel/memberLevelForm";
	}

	/**
	 * 保存会员等级
	 */
	@RequiresPermissions(value={"memberlevel:memberLevel:add","memberlevel:memberLevel:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MemberLevel memberLevel, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, memberLevel)){
			return form(memberLevel, model);
		}
		if(!memberLevel.getIsNewRecord()){//编辑表单保存
			MemberLevel t = memberLevelService.get(memberLevel.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(memberLevel, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			memberLevelService.save(t);//保存
		}else{//新增表单保存
			memberLevelService.save(memberLevel);//保存
		}
		addMessage(redirectAttributes, "保存会员等级成功");
		return "redirect:"+Global.getAdminPath()+"/memberlevel/memberLevel/?repage";
	}
	
	/**
	 * 删除会员等级
	 */
	@RequiresPermissions("memberlevel:memberLevel:del")
	@RequestMapping(value = "delete")
	public String delete(MemberLevel memberLevel, RedirectAttributes redirectAttributes) {
		memberLevelService.delete(memberLevel);
		addMessage(redirectAttributes, "删除会员等级成功");
		return "redirect:"+Global.getAdminPath()+"/memberlevel/memberLevel/?repage";
	}
	
	/**
	 * 批量删除会员等级
	 */
	@RequiresPermissions("memberlevel:memberLevel:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			memberLevelService.delete(memberLevelService.get(id));
		}
		addMessage(redirectAttributes, "删除会员等级成功");
		return "redirect:"+Global.getAdminPath()+"/memberlevel/memberLevel/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("memberlevel:memberLevel:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(MemberLevel memberLevel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "会员等级"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MemberLevel> page = memberLevelService.findPage(new Page<MemberLevel>(request, response, -1), memberLevel);
    		new ExportExcel("会员等级", MemberLevel.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出会员等级记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/memberlevel/memberLevel/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("memberlevel:memberLevel:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MemberLevel> list = ei.getDataList(MemberLevel.class);
			for (MemberLevel memberLevel : list){
				try{
					memberLevelService.save(memberLevel);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条会员等级记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条会员等级记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入会员等级失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/memberlevel/memberLevel/?repage";
    }
	
	/**
	 * 下载导入会员等级数据模板
	 */
	@RequiresPermissions("memberlevel:memberLevel:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "会员等级数据导入模板.xlsx";
    		List<MemberLevel> list = Lists.newArrayList(); 
    		new ExportExcel("会员等级数据", MemberLevel.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/memberlevel/memberLevel/?repage";
    }
	
	
	

}