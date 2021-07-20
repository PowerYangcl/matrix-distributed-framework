package com.matrix.pojo.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.dao.IMcSysFunctionMapper;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateMcSysFunctionRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 8407464708831978095L;
	
	@Inject
	private IMcSysFunctionMapper mcSysFunctionMapper;

	private McUserInfoView userCache;
	
	private Long id;
    private String name;
    private String btnArea; 
    private Integer navType;		// -1 根节点 0 平台标记 1 横向导航栏|2 为1级菜单栏|3 2级菜单栏 |4 页面按钮|5 按钮内包含跳转页面(dialog或新页面)
    private String eleValue;
    private String remark;
    private String funcUrl;			// nav_type=3或5; 标识为一个跳转页面 如：example/page_form_example.do
    private String platform; 			// 平台默认标识码|nav_type=0，此处为系统生成默认值
    private String ajaxBtnUrl; 	// 按钮请求路径|nav_type=4时保存所请求的接口与本条记录中ele_value的值一一对应|虽然都是按钮但nav_type=5通常此处为空
    
    private Integer authorize;      // 用户与角色是否委托Leader创建。0:委托 1:不委托|nav_type=0此字段生效。

    
	public McSysFunction buildEditMcSysFunction() {
		McSysFunction e = new McSysFunction();
		e.setId(id);
		e.setName(name);
		e.setBtnArea(btnArea);
		e.setNavType(navType);
		e.setEleValue(eleValue);
		e.setRemark(remark);
		e.setFuncUrl(funcUrl);
		e.setPlatform(platform);
		e.setAjaxBtnUrl(ajaxBtnUrl);
		e.buildUpdateCommon(userCache);
		e.setUserCache(userCache);
		
		switch(navType){	// validateEditMcSysFunction()验证后赋值，故此处永不为空。
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
	
	public Result<McSysFunction> validateEditMcSysFunction() {
		if(id == null) {		// 100020111=主键丢失
			return Result.ERROR(this.getInfo(100020111), ResultCode.MISSING_ARGUMENT);
		}
		if(StringUtils.isBlank(name)) {	// 100010125=请求参数：{0}不允许为空
			return Result.ERROR(this.getInfo(100010125 , "name"), ResultCode.MISSING_ARGUMENT);
		}
		if(navType == null) {		// navTpye = 4 or 5会由前端JS传入，其他则需要查询数据库来判断
			McSysFunction find = mcSysFunctionMapper.find(id);
			if(find == null) {		// 100020114=找不到对象，资源信息：{0}
				return Result.ERROR(this.getInfo(100020114, id.toString()), ResultCode.NOT_FOUND);
			}
			navType = find.getNavType();
		}
		
		switch(navType){
			case 3 :			// 2级菜单栏
				if(StringUtils.isBlank(funcUrl)) { 		// 101010053=系统功能更新失败! 【页面跳转地址】不得为空
					return Result.ERROR(this.getInfo(101010053), ResultCode.MISSING_ARGUMENT);
				}
				break; 
			case 4 :			// 页面按钮
				if(StringUtils.isBlank(eleValue)) { 		// 101010052=系统功能更新失败! 【页面按钮标识】不得为空
					return Result.ERROR(this.getInfo(101010052), ResultCode.MISSING_ARGUMENT);
				}
				if(StringUtils.isBlank(ajaxBtnUrl)) { 		// 101010055=系统功能更新失败! 【按钮请求路径】不得为空
					return Result.ERROR(this.getInfo(101010055), ResultCode.MISSING_ARGUMENT);
				}
				break; 
			case 5 :			//  按钮内包含跳转页面(dialog或新页面)
				if(StringUtils.isBlank(eleValue)) { 		// 101010052=系统功能更新失败! 【页面按钮标识】不得为空
					return Result.ERROR(this.getInfo(101010052), ResultCode.MISSING_ARGUMENT);
				}
				if(StringUtils.isBlank(funcUrl)) {			// 101010054=系统功能更新失败! 【按钮跳转地址】不得为空
					return Result.ERROR(this.getInfo(101010054), ResultCode.MISSING_ARGUMENT);
				}
				break; 
		}
		return Result.SUCCESS();
	}
	
	
	private String ustring;     // 系统功能同层节点拖拽更新| id@seqnum,id@seqnum
	
	public List<McSysFunction> buildUpdateTreeNodes(){
		List<McSysFunction> list = new ArrayList<McSysFunction>();
		if(StringUtils.isBlank(ustring)) {
			return list;
		}
		String [] arr = ustring.split(",");
		for(int i = 0 ; i < arr.length ; i ++) {
			McSysFunction e = new McSysFunction();
			e.setId( Long.valueOf(arr[i].split("@")[0]) );
			e.setSeqnum( Integer.valueOf(arr[i].split("@")[1]) );
			e.buildUpdateCommon(userCache);
			list.add(e);
		}
		return list;
	}
	
	public Result<?> validateUpdateTreeNodes(){
		if(StringUtils.isBlank(ustring)) {			// 100010125=请求参数：{0}不允许为空
			return Result.ERROR(this.getInfo(100010125 , "ustring"), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
}















































