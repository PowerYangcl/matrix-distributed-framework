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
    private Long cid;
    private String userName;
    private String userCode;
    private Long mcOrganizationId;
    private String password;
    private String type;
    private Integer flag;
    private String idcard;
    private Integer sex;
    private Date birthday;
    private String mobile;
    private String email;
    private String qq;
    private String remark;
    private String picUrl;
    private String pageCss;
    private String platform;
    
}
























