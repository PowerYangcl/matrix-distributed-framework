package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.dao.IMcUserInfoMapper;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.view.McUserInfoView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateMcUserInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 4307560107936755283L;

	@Inject
	private IMcUserInfoMapper mcUserInfoMapper;
	
	private McUserInfoView userCache;
	
	@NotBlank(message = "100020111")		// 100020111=主键丢失
	private Long id;
	
    private String userName;
    private String idcard;
    private Integer sex;
    private String mobile;
    private String email;
    private String qq;
    private String remark;
    private String userNameOld;

	public McUserInfo buildEditSysUser() {
		McUserInfo e = new McUserInfo();
		e.setId(id);
		e.setUserName(userName);
		e.setMobile(mobile);
		e.setEmail(email);
		e.setSex(sex);  
		e.setIdcard(idcard);
		e.setQq(qq);
		e.setRemark(remark);
		e.buildUpdateCommon(userCache);
		return e;
	}
	
	public Result<?> validateEditSysUser() {
		if(StringUtils.isNotBlank(userName) && !userName.equals(userNameOld) ) {	// 开始用户名是否重复
			McUserInfoDto dto = new McUserInfoDto();
			dto.setUserName(userName);
			McUserInfo ishas = mcUserInfoMapper.findEntityByDto(dto);
			if(ishas != null) {		 // 101010029=用户名已存在
				return Result.ERROR(this.getInfo(101010029), ResultCode.INTERNAL_VALIDATION_FAILED);
			}
		}
		return Result.SUCCESS();
	}
}














