package com.matrix.base;

import java.io.Serializable;
import java.util.Date;

import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

/**
 * @description: 数据返回视图模型基类 | 提供dubbo分布式序列化支持
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年7月24日 上午2:44:55 
 * @version 1.0.0.1
 */
@Data
public class BaseView  implements Serializable {

	private static final long serialVersionUID = 2698724150737191453L;
	
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
