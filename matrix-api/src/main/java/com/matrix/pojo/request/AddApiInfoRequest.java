package com.matrix.pojo.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.cache.enums.CachePrefix;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.pojo.entity.AcApiInfo;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddApiInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 115604822483772880L;

	private McUserInfoView userCache;
	private IBaseLaunch<ICacheFactory> launch;
	
    private String name;  // 接口中文描述   树展示使用
    private String target; // 系统接口名称 比如：TEST-PUBLIC-PROCESSOR，访问标识
    private String dtoInfo; // 接口请求参数描述
    private String atype;
    private String module;
    private String processor;
    private Integer domain;
    private Long parentId;
    private Integer seqnum;
    private Integer discard;		// 这个api是否废弃|0:废弃 1:使用中
    private Integer login;  // 当前接口是否需要登录后访问：1 需要登录后访问 0不需要
    private String remark;
    
    private String domainList; // ac_include_domain 列表
	
	public Result<AcApiInfo> validate(IBaseLaunch<ICacheFactory> launch){
		if(StringUtils.isAnyBlank(name , target , processor , module , remark)) {
			return Result.ERROR(this.getInfo(600010071), ResultCode.MISSING_ARGUMENT);  // 600010071=API关键信息不得为空!请全部填写.
		}
		if(domain == 1 && StringUtils.isBlank(domainList)) {
			return Result.ERROR(this.getInfo(600010072), ResultCode.MISSING_ARGUMENT);  // 600010072=请勾选API可用跨域列表
		}
		if(!StringUtils.startsWithAny(processor , atype , "common")) { 
			// 600010074=【业务处理实现】路径输入错误!应该以{0}起始
			return Result.ERROR(this.getInfo(600010074 , atype), ResultCode.INVALID_ARGUMENT);
		}
		String isRecord = launch.loadDictCache(CachePrefix.ApiInfo , "ApiInfoInit").get(target);
		if(StringUtils.isNotBlank(isRecord)) { // 600010075=系统接口名称：{0} 已经在数据库中存在,请修改.
			return Result.ERROR(this.getInfo(600010075 , target), ResultCode.ALREADY_EXISTS);
		}
		return Result.SUCCESS();
	}
	
	
	public AcApiInfo buildAjaxApiInfoAdd() {
		AcApiInfo e = new AcApiInfo();
		e.setName(name);
		e.setTarget(target);
		e.setDtoInfo(dtoInfo);
		e.setAtype(atype);
		e.setModule(module);
		e.setProcessor(processor);
		e.setDomain(domain);
		e.setParentId(parentId);
		e.setSeqnum(seqnum);
		e.setDiscard(1);
		e.setLogin(login);
		e.setRemark(remark);
		e.buildAddCommon(userCache);
		return e;
	}
}




















