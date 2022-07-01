package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.util.DateUtil;
import com.matrix.validate.Validation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddMcSysFunctionRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = -4703428007044146964L;

	private McUserInfoView userCache;
	
    private Long mcSellerCompanyId;
    
    @NotBlank(message = "101010059")	   // 101010059=功能名称 | 父节点不能为空 ! 
    private String name;
    
    @NotBlank(message = "101010059")	   // 101010059=功能名称 | 父节点不能为空 ! 
    private String parentId;
    
    private Integer seqnum;
    
    // 101010062=nav type 类型错误
    @Range(min=0, max=5, message="101010062")
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
	
	public McSysFunction buildAddMcSysFunction() {
		McSysFunction e = new McSysFunction();
		e.setMcSellerCompanyId(mcSellerCompanyId);
		e.setName(name);
		e.setParentId(parentId);
		e.setSeqnum(seqnum);
		e.setNavType(navType);
		e.setAuthorize(authorize);
		e.setPlatform(platform);
		e.setStyleClass(styleClass);
		e.setStyleKey(styleKey);
		e.setFuncUrl(funcUrl);
		e.setAjaxBtnUrl(ajaxBtnUrl);
		e.setRemark(remark);
		e.setBtnArea(btnArea);
		e.setEleValue(eleValue);
		e.buildAddCommon(userCache);
		e.setUserCache(userCache);
		
		switch(navType){
			case 0 :		// 平台默认标识码|nav_type=0，此处为系统生成默认值
				DateUtil dateUtil = new DateUtil();
				e.setPlatform(dateUtil.getDateLongHex("yyyyMMdd").toUpperCase() + dateUtil.getDateLongHex("HHmmss").toUpperCase());     
				break;  
			case 1 :			// 1 横向导航栏
				break; 
			case 2 :		 	// 1 级菜单栏
				break; 
			case 3 :			// 2级菜单栏
				e.setStyleKey(null);	 // 系统只允许横导航和1级菜单栏有自己的特殊样式
				break; 
			case 4 :			// 页面按钮
				e.setStyleKey(null);	 // 系统只允许横导航和1级菜单栏有自己的特殊样式
				break; 
			case 5 :			//  按钮内包含跳转页面(dialog或新页面)
				e.setStyleKey(null);	 // 系统只允许横导航和1级菜单栏有自己的特殊样式
				break; 
		}
		return e;
	}
	
	public Result<McSysFunction> validate() {
		switch(navType){
			case 3 :			// 2级菜单栏
				if(StringUtils.isBlank(funcUrl)) {	// 101010049=系统功能添加失败! 【页面跳转地址】不得为空
					return Result.ERROR(this.getInfo(101010049), ResultCode.MISSING_ARGUMENT);
				}
				break; 
			case 4 :			// 页面按钮
				if(StringUtils.isBlank(eleValue)) {		// 101010048=系统功能添加失败! 【页面按钮标识】不得为空
					return Result.ERROR(this.getInfo(101010048), ResultCode.MISSING_ARGUMENT);
				}
				if(StringUtils.isBlank(ajaxBtnUrl)) {		// 101010051=系统功能添加失败! 【按钮请求路径】不得为空
					return Result.ERROR(this.getInfo(101010051), ResultCode.MISSING_ARGUMENT);
				}
				break; 
			case 5 :			//  按钮内包含跳转页面(dialog或新页面)
				if(StringUtils.isBlank(eleValue)) {		// 101010048=系统功能添加失败! 【页面按钮标识】不得为空
					return Result.ERROR(this.getInfo(101010048), ResultCode.MISSING_ARGUMENT);
				}
				if(StringUtils.isBlank(funcUrl)) {  	// 101010050=系统功能添加失败! 【按钮跳转地址】不得为空
					return Result.ERROR(this.getInfo(101010050), ResultCode.MISSING_ARGUMENT);
				}
				break; 
		}
		return Result.SUCCESS();
	}
}















































