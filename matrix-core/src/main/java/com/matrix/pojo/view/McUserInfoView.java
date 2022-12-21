package com.matrix.pojo.view;

import java.io.Serializable;
import java.util.Date;
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
    private String userName;
    private String password;
    private String type;				// leader Leader后台用户|admin 其他平台管理员|user其他平台用户
    private String platform;		// 平台标识码集合，半角逗号分隔
    private String webcode;		// 保存当前登录的系统所属的平台标识码platform的子集；用户登录后进行赋值操作 v1.6.1.6-multiple-jspweb
    private Integer flag;				// 启用状态；1启用 2停用|type=user 则此字段生效
    private String idcard;			// 身份证号
    private Integer sex;				// 性别 1：男 2：女
    private Date birthday;			// 生日
    private String mobile;			// 手机号
    private String email;			// 邮箱
    private String qq;					// qq号码
    private String picUrl;			// 用户头像
    private String pageCss;		// 后台页面css样式
    private String remark;			// 备注
    private Date createTime;
    private Integer deleteFlag;
    
    // type=user|则此字段生效，为mc_user_info_organization表的mc_organization_id记录集合
//    private List<Long> orgidList; 
    
//    private JSONObject mcOrg;  // mc_organization表对象，代表当前用户的归属组织，mcOrganizationId=0时此处为null
    
    /**
     *  店铺列表(cid != 0等情况)：用户可能为多个店铺。
     *  key: "key-" + cid
     */
//    private Map<String , JSONObject> shopInfoMap = new HashMap<String , JSONObject>(); 
    
    
}



























