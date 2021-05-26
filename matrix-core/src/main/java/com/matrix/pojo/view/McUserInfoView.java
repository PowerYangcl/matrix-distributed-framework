package com.matrix.pojo.view;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;


/**
 * @description: 多表联合视图
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年11月25日 下午3:05:01 
 * @version 1.0.0
 */
@Data
public class McUserInfoView implements Serializable {
	
	private static final long serialVersionUID = 3770893646228836140L;
	
	//	McUserInfo实体数据
	private Long id; 
	private Long cid;
	private Long tenantInfoId;
    private String userName;
    private String userCode;
    private Long mcOrganizationId;
    private String password;
    private String type;	// leader Leader后台用户|admin 其他平台管理员|user其他平台用户
    private Integer flag;
    private String idcard;
    private Integer sex;
    private Date birthday;
    private String mobile;
    private String email;
    private String qq;
    private String remark;
    private Date createTime;
    private Integer deleteFlag;
    private String picUrl;
    private String pageCss;
    private String platform;
    
    // type=user|则此字段生效，为mc_user_info_organization表的mc_organization_id记录集合
    private List<Long> orgidList; 
    
    private JSONObject mcOrg;  // mc_organization表对象，代表当前用户的归属组织，mcOrganizationId=0时此处为null
    
    /**
     *  店铺列表(cid != 0等情况)：用户可能为多个店铺。
     *  key: "key-" + cid
     */
    private Map<String , JSONObject> shopInfoMap = new HashMap<String , JSONObject>(); 
    
    
}



























