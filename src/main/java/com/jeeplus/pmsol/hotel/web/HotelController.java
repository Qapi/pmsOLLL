/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.hotel.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.utils.UserUtils;
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
import com.jeeplus.pmsol.hotel.entity.Hotel;
import com.jeeplus.pmsol.hotel.service.HotelService;

/**
 * 酒店物业配置Controller
 * @author wangp
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/hotel/hotel")
public class HotelController extends BaseController {

	@Autowired
	private HotelService hotelService;
	@Autowired
	private OfficeService officeService;

	@ModelAttribute
	public Hotel get(@RequestParam(required=false) String id) {
		Hotel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hotelService.get(id);
		}
		if (entity == null){
			entity = new Hotel();
		}
		return entity;
	}
	
	/**
	 * 酒店物业列表页面
	 */
	@RequiresPermissions("hotel:hotel:list")
	@RequestMapping(value = {"list", ""})
	public String list(Hotel hotel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Hotel> page = hotelService.findPage(new Page<Hotel>(request, response), hotel); 
		model.addAttribute("page", page);
		return "pmsol/hotel/hotelList";
	}

	/**
	 * 查看，增加，编辑酒店物业表单页面
	 */
	@RequiresPermissions(value={"hotel:hotel:view","hotel:hotel:add","hotel:hotel:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Hotel hotel, Model model) {
		model.addAttribute("hotel", hotel);
		return "pmsol/hotel/hotelForm";
	}

	/**
	 * 获取所有酒店——ajax
	 */
	@RequiresPermissions(value="hotel:hotel:view")
	@RequestMapping(value = "getList")
	@ResponseBody
	public ResponseEntity<List<Hotel>> getList(Hotel hotel) throws Exception{
		// 若非管理员admin ，则默认只显示操作员所在酒店
		// TODO 应调整为单独方法，配置相关权限，以拓展到报表数据等模块
		List<Hotel> list = new ArrayList<>();
		if(UserUtils.getUser().isAdmin()){
			list = hotelService.findList(hotel);
		}else{
			Office company = UserUtils.getUser().getCompany();
			hotel = hotelService.findUniqueByProperty("office_id",company.getId());
			list.add(hotel);
		}
		return new ResponseEntity(list, HttpStatus.OK);
	}

	/**
	 * 保存酒店物业
	 */
	@RequiresPermissions(value={"hotel:hotel:add","hotel:hotel:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Hotel hotel, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, hotel)){
			return form(hotel, model);
		}
		if(!hotel.getIsNewRecord()){//编辑表单保存
			Hotel t = hotelService.get(hotel.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(hotel, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			hotelService.save(t);//保存
		}else{//新增表单保存
			hotelService.save(hotel);//保存
		}
		addMessage(redirectAttributes, "保存酒店物业成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/hotel/?repage";
	}
	
	/**
	 * 删除酒店物业
	 */
	@RequiresPermissions("hotel:hotel:del")
	@RequestMapping(value = "delete")
	public String delete(Hotel hotel, RedirectAttributes redirectAttributes) {
		hotelService.delete(hotel);
		addMessage(redirectAttributes, "删除酒店物业成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/hotel/?repage";
	}
	
	/**
	 * 批量删除酒店物业
	 */
	@RequiresPermissions("hotel:hotel:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hotelService.delete(hotelService.get(id));
		}
		addMessage(redirectAttributes, "删除酒店物业成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/hotel/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("hotel:hotel:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Hotel hotel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "酒店物业"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Hotel> page = hotelService.findPage(new Page<Hotel>(request, response, -1), hotel);
    		new ExportExcel("酒店物业", Hotel.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出酒店物业记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/hotel/hotel/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("hotel:hotel:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Hotel> list = ei.getDataList(Hotel.class);
			for (Hotel hotel : list){
				try{
					hotelService.save(hotel);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条酒店物业记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条酒店物业记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入酒店物业失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/hotel/hotel/?repage";
    }
	
	/**
	 * 下载导入酒店物业数据模板
	 */
	@RequiresPermissions("hotel:hotel:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "酒店物业数据导入模板.xlsx";
    		List<Hotel> list = Lists.newArrayList(); 
    		new ExportExcel("酒店物业数据", Hotel.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/hotel/hotel/?repage";
    }
	
	
	

}