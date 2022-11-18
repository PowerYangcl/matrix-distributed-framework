package com.matrix.pojo.entity;

import java.util.Date;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class McUserInfo extends BaseEntity{

	private static final long serialVersionUID = 7497311251212822363L;
	private Long id;
    private String userName;
    private String password;
    private String type;					// leader Leader后台用户|admin 其他平台管理员|user其他平台用户
    private String platform;			// 平台标识码
    private Integer flag;					// 启用状态；1启用 2停用|type=user 则此字段生效
    private String idcard;				// 身份证号
    private Integer sex;					// 性别 1：男 2：女
    private Date birthday;				// 生日
    private String mobile;				// 手机号
    private String email;				// 邮箱
    private String qq;						// qq号码
    private String picUrl;				// 用户头像
    private String pageCss;			// 后台页面css样式
    private String remark;				// 备注
    
}
























