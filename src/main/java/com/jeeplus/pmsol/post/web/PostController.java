/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.pmsol.post.web;

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
import com.jeeplus.pmsol.post.entity.Post;
import com.jeeplus.pmsol.post.service.PostService;

/**
 * 岗位配置Controller
 * @author wangp
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/post/post")
public class PostController extends BaseController {

	@Autowired
	private PostService postService;
	
	@ModelAttribute
	public Post get(@RequestParam(required=false) String id) {
		Post entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = postService.get(id);
		}
		if (entity == null){
			entity = new Post();
		}
		return entity;
	}
	
	/**
	 * 岗位列表页面
	 */
	@RequiresPermissions("post:post:list")
	@RequestMapping(value = {"list", ""})
	public String list(Post post, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Post> page = postService.findPage(new Page<Post>(request, response), post); 
		model.addAttribute("page", page);
		return "pmsol/post/postList";
	}

	/**
	 * 查看，增加，编辑岗位表单页面
	 */
	@RequiresPermissions(value={"post:post:view","post:post:add","post:post:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Post post, Model model) {
		model.addAttribute("post", post);
		return "pmsol/post/postForm";
	}

	/**
	 * 保存岗位
	 */
	@RequiresPermissions(value={"post:post:add","post:post:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Post post, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, post)){
			return form(post, model);
		}
		if(!post.getIsNewRecord()){//编辑表单保存
			Post t = postService.get(post.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(post, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			postService.save(t);//保存
		}else{//新增表单保存
			postService.save(post);//保存
		}
		addMessage(redirectAttributes, "保存岗位成功");
		return "redirect:"+Global.getAdminPath()+"/post/post/?repage";
	}
	
	/**
	 * 删除岗位
	 */
	@RequiresPermissions("post:post:del")
	@RequestMapping(value = "delete")
	public String delete(Post post, RedirectAttributes redirectAttributes) {
		postService.delete(post);
		addMessage(redirectAttributes, "删除岗位成功");
		return "redirect:"+Global.getAdminPath()+"/post/post/?repage";
	}
	
	/**
	 * 批量删除岗位
	 */
	@RequiresPermissions("post:post:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			postService.delete(postService.get(id));
		}
		addMessage(redirectAttributes, "删除岗位成功");
		return "redirect:"+Global.getAdminPath()+"/post/post/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("post:post:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Post post, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "岗位"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Post> page = postService.findPage(new Page<Post>(request, response, -1), post);
    		new ExportExcel("岗位", Post.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出岗位记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/post/post/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("post:post:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Post> list = ei.getDataList(Post.class);
			for (Post post : list){
				try{
					postService.save(post);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条岗位记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条岗位记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入岗位失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/post/post/?repage";
    }
	
	/**
	 * 下载导入岗位数据模板
	 */
	@RequiresPermissions("post:post:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "岗位数据导入模板.xlsx";
    		List<Post> list = Lists.newArrayList(); 
    		new ExportExcel("岗位数据", Post.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/post/post/?repage";
    }
	
	
	

}