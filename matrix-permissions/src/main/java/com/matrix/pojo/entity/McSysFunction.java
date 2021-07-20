package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McSysFunction extends BaseEntity{
	
	private static final long serialVersionUID = 5229443632033099730L;
	
	private Long id;
    private Long mcSellerCompanyId;
    private String name;
    private String parentId;
    private Integer seqnum;
    private Integer navType;		// -1 根节点 0 平台标记 1 横向导航栏|2 为1级菜单栏|3 2级菜单栏 |4 页面按钮|5 按钮内包含跳转页面(dialog或新页面)
    private Integer authorize;      // 用户与角色是否委托Leader创建。0:委托 1:不委托|nav_type=0此字段生效。
    private String platform; 			// 平台默认标识码|nav_type=0，此处为系统生成默认值
    private String styleClass;
    private String styleKey;
    private String funcUrl;			// nav_type=3或5; 标识为一个跳转页面 如：example/page_form_example.do
    private String ajaxBtnUrl; 	// 按钮请求路径|nav_type=4时保存所请求的接口与本条记录中ele_value的值一一对应|虽然都是按钮但nav_type=5通常此处为空
    private String remark;
    /**
     * 按钮节点所在页面的位置，只有只有导航树的最后一层：按钮节点才会有。
     * 	10001：功能区域(在查询区域上部。如：添加按钮、导出按钮等等)；
     * 	10002：查询区域(查询、重置等等)；
     * 	10003：数据区域 (即：页面列表所在区域。如：编辑、修改、删除、授权、弹出层等等，多数为a标签。)
     */
    private String btnArea; 
    /**
     * 按钮权限唯一标识
     */
    private String eleValue;
    
    
}







































