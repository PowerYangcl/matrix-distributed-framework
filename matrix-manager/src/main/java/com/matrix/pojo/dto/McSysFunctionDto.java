package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

public class McSysFunctionDto extends BaseDto {

	private static final long serialVersionUID = 6945367056254282346L;

	private Long id;
	private String type;
	private String platform; 			// 平台默认标识码|nav_type=0，此处为系统生成默认值
	private String ustring;     // 系统功能同层节点拖拽更新| id@seqnum,id@seqnum 
	private String ids;             // 删除一个系统功能节点及其子节点，用于保存节点主键|节点树，查找指定id范围内的数据列表，没有这个ID的功能，不显示
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUstring() {
		return ustring;
	}
	public void setUstring(String ustring) {
		this.ustring = ustring;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
}
















































