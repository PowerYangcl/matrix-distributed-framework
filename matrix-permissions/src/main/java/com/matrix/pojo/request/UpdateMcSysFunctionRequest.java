package com.matrix.pojo.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class UpdateMcSysFunctionRequest implements Serializable{

	private static final long serialVersionUID = 8407464708831978095L;

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
		return e;
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
}















































