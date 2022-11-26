package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McWorkflowTemplate  extends BaseEntity{
	private static final long serialVersionUID = 8375091629088595826L;

	/**工作流转模板表(树型结构数据记录)|归属EHR系统*/
    private Long id;

    /**
     * 	流转渠道：
     * 		A-运营系统 
     * 		B-EHR系统 
     * 		C-生产管理系统
     * */
    private String channel;

    /**所属业务线的流转表名，比如：mc_workflow_*****|mc_workflow_purchase*/
    private String serviceName;

    /**
     * 	工作流模板名称(leve=1)
     * 	工作流过程描述(level=2)
     * */
    private String name;

    /**父节点ID*/
    private Long parentId;

    /**节点等级 1-模板名称 2-工作流过程描述*/
    private String level;

    /**当前工作流模板编码，方便获取数据使用*/
    private String templateCode;

    /**同层节点顺序码，流转顺序以此为依据*/
    private Integer seqnum;

    /**流转处理人id*/
    private Long mcUserId;

    /**流转处理人真实姓名*/
    private String processUserName;
}