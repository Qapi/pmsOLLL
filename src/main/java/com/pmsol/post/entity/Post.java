/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pmsol.post.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 岗位配置Entity
 * @author wangp
 * @version 2017-09-29
 */
public class Post extends DataEntity<Post> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String flag;		// 等级标识
	private String hotelId;		// 所属酒店
	private String status;		// 状态
	
	public Post() {
		super();
	}

	public Post(String id){
		super(id);
	}

	@ExcelField(title="姓名", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="等级标识", align=2, sort=8)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@ExcelField(title="所属酒店", align=2, sort=9)
	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	
	@ExcelField(title="状态", align=2, sort=10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}