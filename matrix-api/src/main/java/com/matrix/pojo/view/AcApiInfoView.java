package com.matrix.pojo.view;

import com.matrix.base.BaseView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AcApiInfoView extends BaseView{
	private static final long serialVersionUID = 4084187976311722672L;

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
