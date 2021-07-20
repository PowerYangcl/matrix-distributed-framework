package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AcApiInfo extends BaseEntity{
	
	private static final long serialVersionUID = 7388908004393425485L;
	
	private Long id;
    private String name;  // 接口中文描述   树展示使用
    private String target; // 系统接口名称 比如：TEST-PUBLIC-PROCESSOR，访问标识
    private String dtoInfo; // 接口请求参数描述
    private String atype;
    private String module;
    private String processor;
    private Integer domain;
    private Long parentId;
    private Integer seqnum;
    private Integer discard;
    private Integer login;  // 当前接口是否需要登录后访问：1 需要登录后访问 0不需要
    private String remark;
    
    
}

















