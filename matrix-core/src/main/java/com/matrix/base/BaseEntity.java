package com.matrix.base;

import java.io.Serializable;
import java.util.Date;

import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

/**
 * @description: 实体类基础类
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年7月25日 下午2:16:57 
 * @version 1.0.0.1
 */
@Data
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -7383391444275138625L;

	/** 
	 * 起始页数 - 微服务化项目需要的字段
	 */
	private Integer startIndex = 1;
	
	/**
	 *  分页记录数 - 微服务化项目需要的字段
	 */
	private Integer pageSize = 10;
	
	
	/**
	 *  动态列表排序依据，比如：dto.setOrder("create_time");
	 */
	private String orderBy = null;
	
	private Date createTime;
	private Long createUserId;
	private String createUserName;
	private Date updateTime;
	private Long updateUserId;
	private String updateUserName;
	private Integer deleteFlag;               // 0 删除 | 1 未删除 数据库记录默认未删除
	
	private McUserInfoView userCache ;

	private String eleValue; // 按钮权限标识

}
