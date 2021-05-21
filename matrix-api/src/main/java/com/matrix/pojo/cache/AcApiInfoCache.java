package com.matrix.pojo.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class AcApiInfoCache implements Serializable{
	private static final long serialVersionUID = 4084187976311722688L;

	private Long id;
    private String name;  // 接口中文描述   树展示使用
    private String target; // 系统接口名称 比如：TEST-PUBLIC-PROCESSOR，访问标识
    private String dtoInfo; // 接口请求参数描述
    private String atype;	//	接口类型 private:私有 即公司内部使用的接口| public:公开，即开放给第三方的接口
    private String module;		//
    private String processor;		//
    private Integer domain;		// 接口是否拥有跨域行为 0 不允许  1 允许跨域访问|ac_api_domain表作为关联
    private Long parentId;		//	所属内部项目id,用于树形结构展示|ac_api_project表id
    private Integer seqnum;		//	顺序码 同一层次显示顺序
    private Integer discard;		// 这个api是否废弃|0:废弃 1:使用中
    private Integer login;  // 当前接口是否需要登录后访问：1 需要登录后访问 0不需要
    private String remark;
    
    private List<String> list = new ArrayList<String>();
    
    private JSONObject param = new JSONObject();	// 保存请求体信息
}
