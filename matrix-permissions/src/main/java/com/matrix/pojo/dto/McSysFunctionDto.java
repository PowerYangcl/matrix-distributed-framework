package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McSysFunctionDto extends BaseDto {

	private static final long serialVersionUID = 6945367056254282346L;

	private Long roleId;
	private String type;
	private String platform; 			// 平台默认标识码|nav_type=0，此处为系统生成默认值
	private String ids;             // 删除一个系统功能节点及其子节点，用于保存节点主键|节点树，查找指定id范围内的数据列表，没有这个ID的功能，不显示

	private Integer navType;
	private String platformList;
	
}
















































