package com.matrix.pojo.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.dao.IAcApiInfoMapper;
import com.matrix.pojo.cache.AcApiInfoCache;
import com.matrix.pojo.entity.AcApiInfo;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateApiInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 115604822483772880L;

	private McUserInfoView userCache;

	private IAcApiInfoMapper acApiInfoMapper;
	
	private Long id;
	private String dtoInfo; // 接口请求参数描述
	private String processor;
	private String module;
	private Integer domain;
	private Integer seqnum;
	private Integer login;  // 当前接口是否需要登录后访问：1 需要登录后访问 0不需要
	private String remark;
	
	private String target; // 系统接口名称 比如：TEST-PUBLIC-PROCESSOR，访问标识
	private String atype;
	
    private String domainList; // ac_include_domain 列表
    
	public Result<AcApiInfoCache> validate(IAcApiInfoMapper acApiInfoMapper){
		if(id == null){ 	// 100020111=主键丢失
			return Result.ERROR(this.getInfo(100020111), ResultCode.MISSING_ARGUMENT);
		}
		if(!StringUtils.startsWithAny(processor , atype , "common")) { 
			// 600010074=【业务处理实现】路径输入错误!应该以{0}起始
			return Result.ERROR(this.getInfo(600010074 , atype), ResultCode.INVALID_ARGUMENT);
		}
		if(domain == 1 && StringUtils.isBlank(domainList)) {
			return Result.ERROR(this.getInfo(600010072), ResultCode.MISSING_ARGUMENT);  // 600010072=请勾选API可用跨域列表
		}
		if(StringUtils.isBlank(dtoInfo)) {// 600010085=请求数据Json结构体不得为空!
			return Result.ERROR(this.getInfo(600010085), ResultCode.MISSING_ARGUMENT);
		}
		try {
			JSONObject.parse(dtoInfo);
		} catch (Exception e) {		// 600010086=非法的请求数据Json结构体!请输入一个Json字符串
			return Result.ERROR(this.getInfo(600010086), ResultCode.INVALID_ARGUMENT);
		}
		AcApiInfo api = acApiInfoMapper.find(id);
		if(api == null) { 		// 600010078=目标接口: {0} 不存在!数据库无此记录,修改失败!
			return Result.ERROR(this.getInfo(600010078 , target), ResultCode.NOT_FOUND);
		}
		if(!api.getTarget().equals(target)) {		// 600010079=目标接口: {0} 与数据库记录不符, 请勿修改【系统接口名称】
			return Result.ERROR(this.getInfo(600010079 , target), ResultCode.MISMATCH_ARGUMENT);
		}
		
		return Result.SUCCESS();
	}
	
	
	public AcApiInfo buildAjaxApiInfoEdit() {
		AcApiInfo e = new AcApiInfo();
		e.setId(id);
		e.setDtoInfo(dtoInfo);
		e.setProcessor(processor);
		e.setModule(module);
		e.setDomain(domain);
		e.setSeqnum(seqnum);
		e.setLogin(login);
		e.setRemark(remark);
		e.buildUpdateCommon(userCache);
		return e;
	}
}




















