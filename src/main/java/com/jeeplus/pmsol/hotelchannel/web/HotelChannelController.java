/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.hotelchannel.web;

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
import com.jeeplus.pmsol.hotelchannel.entity.HotelChannel;
import com.jeeplus.pmsol.hotelchannel.service.HotelChannelService;

/**
 * 酒店销售渠道配置Controller
 * @author wangp
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/hotelchannel/hotelChannel")
public class HotelChannelController extends BaseController {

	@Autowired
	private HotelChannelService hotelChannelService;
	
	@ModelAttribute
	public HotelChannel get(@RequestParam(required=false) String id) {
		HotelChannel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hotelChannelService.get(id);
		}
		if (entity == null){
			entity = new HotelChannel();
		}
		return entity;
	}
	
	/**
	 * 酒店销售渠道列表页面
	 */
	@RequiresPermissions("hotelchannel:hotelChannel:list")
	@RequestMapping(value = {"list", ""})
	public String list(HotelChannel hotelChannel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HotelChannel> page = hotelChannelService.findPage(new Page<HotelChannel>(request, response), hotelChannel); 
		model.addAttribute("page", page);
		return "pmsol/hotelchannel/hotelChannelList";
	}

	/**
	 * 查看，增加，编辑酒店销售渠道表单页面
	 */
	@RequiresPermissions(value={"hotelchannel:hotelChannel:view","hotelchannel:hotelChannel:add","hotelchannel:hotelChannel:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(HotelChannel hotelChannel, Model model) {
		model.addAttribute("hotelChannel", hotelChannel);
		return "pmsol/hotelchannel/hotelChannelForm";
	}

	/**
	 * 保存酒店销售渠道
	 */
	@RequiresPermissions(value={"hotelchannel:hotelChannel:add","hotelchannel:hotelChannel:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(HotelChannel hotelChannel, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, hotelChannel)){
			return form(hotelChannel, model);
		}
		if(!hotelChannel.getIsNewRecord()){//编辑表单保存
			HotelChannel t = hotelChannelService.get(hotelChannel.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(hotelChannel, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			hotelChannelService.save(t);//保存
		}else{//新增表单保存
			hotelChannelService.save(hotelChannel);//保存
		}
		addMessage(redirectAttributes, "保存酒店销售渠道成功");
		return "redirect:"+Global.getAdminPath()+"/hotelchannel/hotelChannel/?repage";
	}
	
	/**
	 * 删除酒店销售渠道
	 */
	@RequiresPermissions("hotelchannel:hotelChannel:del")
	@RequestMapping(value = "delete")
	public String delete(HotelChannel hotelChannel, RedirectAttributes redirectAttributes) {
		hotelChannelService.delete(hotelChannel);
		addMessage(redirectAttributes, "删除酒店销售渠道成功");
		return "redirect:"+Global.getAdminPath()+"/hotelchannel/hotelChannel/?repage";
	}
	
	/**
	 * 批量删除酒店销售渠道
	 */
	@RequiresPermissions("hotelchannel:hotelChannel:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hotelChannelService.delete(hotelChannelService.get(id));
		}
		addMessage(redirectAttributes, "删除酒店销售渠道成功");
		return "redirect:"+Global.getAdminPath()+"/hotelchannel/hotelChannel/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("hotelchannel:hotelChannel:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(HotelChannel hotelChannel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "酒店销售渠道"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HotelChannel> page = hotelChannelService.findPage(new Page<HotelChannel>(request, response, -1), hotelChannel);
    		new ExportExcel("酒店销售渠道", HotelChannel.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出酒店销售渠道记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/hotelchannel/hotelChannel/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("hotelchannel:hotelChannel:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HotelChannel> list = ei.getDataList(HotelChannel.class);
			for (HotelChannel hotelChannel : list){
				try{
					hotelChannelService.save(hotelChannel);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条酒店销售渠道记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条酒店销售渠道记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入酒店销售渠道失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/hotelchannel/hotelChannel/?repage";
    }
	
	/**
	 * 下载导入酒店销售渠道数据模板
	 */
	@RequiresPermissions("hotelchannel:hotelChannel:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "酒店销售渠道数据导入模板.xlsx";
    		List<HotelChannel> list = Lists.newArrayList(); 
    		new ExportExcel("酒店销售渠道数据", HotelChannel.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/hotelchannel/hotelChannel/?repage";
    }
	
	
	

}