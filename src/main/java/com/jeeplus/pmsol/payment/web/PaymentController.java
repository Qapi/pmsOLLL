/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.payment.web;

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
import com.jeeplus.pmsol.payment.entity.Payment;
import com.jeeplus.pmsol.payment.service.PaymentService;

/**
 * 支付信息记录Controller
 * @author wangp
 * @version 2017-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/payment/payment")
public class PaymentController extends BaseController {

	@Autowired
	private PaymentService paymentService;
	
	@ModelAttribute
	public Payment get(@RequestParam(required=false) String id) {
		Payment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = paymentService.get(id);
		}
		if (entity == null){
			entity = new Payment();
		}
		return entity;
	}
	
	/**
	 * 支付信息记录列表页面
	 */
	@RequiresPermissions("payment:payment:list")
	@RequestMapping(value = {"list", ""})
	public String list(Payment payment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Payment> page = paymentService.findPage(new Page<Payment>(request, response), payment); 
		model.addAttribute("page", page);
		return "pmsol/payment/paymentList";
	}

	/**
	 * 查看，增加，编辑支付信息记录表单页面
	 */
	@RequiresPermissions(value={"payment:payment:view","payment:payment:add","payment:payment:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Payment payment, Model model) {
		model.addAttribute("payment", payment);
		return "pmsol/payment/paymentForm";
	}

	/**
	 * 保存支付信息记录
	 */
	@RequiresPermissions(value={"payment:payment:add","payment:payment:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Payment payment, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, payment)){
			return form(payment, model);
		}
		if(!payment.getIsNewRecord()){//编辑表单保存
			Payment t = paymentService.get(payment.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(payment, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			paymentService.save(t);//保存
		}else{//新增表单保存
			paymentService.save(payment);//保存
		}
		addMessage(redirectAttributes, "保存支付信息记录成功");
		return "redirect:"+Global.getAdminPath()+"/payment/payment/?repage";
	}
	
	/**
	 * 删除支付信息记录
	 */
	@RequiresPermissions("payment:payment:del")
	@RequestMapping(value = "delete")
	public String delete(Payment payment, RedirectAttributes redirectAttributes) {
		paymentService.delete(payment);
		addMessage(redirectAttributes, "删除支付信息记录成功");
		return "redirect:"+Global.getAdminPath()+"/payment/payment/?repage";
	}
	
	/**
	 * 批量删除支付信息记录
	 */
	@RequiresPermissions("payment:payment:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			paymentService.delete(paymentService.get(id));
		}
		addMessage(redirectAttributes, "删除支付信息记录成功");
		return "redirect:"+Global.getAdminPath()+"/payment/payment/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("payment:payment:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Payment payment, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "支付信息记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Payment> page = paymentService.findPage(new Page<Payment>(request, response, -1), payment);
    		new ExportExcel("支付信息记录", Payment.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出支付信息记录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/payment/payment/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("payment:payment:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Payment> list = ei.getDataList(Payment.class);
			for (Payment payment : list){
				try{
					paymentService.save(payment);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条支付信息记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条支付信息记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入支付信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/payment/payment/?repage";
    }
	
	/**
	 * 下载导入支付信息记录数据模板
	 */
	@RequiresPermissions("payment:payment:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "支付信息记录数据导入模板.xlsx";
    		List<Payment> list = Lists.newArrayList(); 
    		new ExportExcel("支付信息记录数据", Payment.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/payment/payment/?repage";
    }
	
	
	

}