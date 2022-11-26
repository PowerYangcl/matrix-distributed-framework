package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McWorkflowPurchase  extends BaseEntity{
	private static final long serialVersionUID = 8592217358146781718L;

	/**mc_goods_purchase表对应的采购记录流转表|归属EHR系统*/
    private Long id;

    /**采购类型，如：大棚生产物资|员工福利商品等，即：mc_goods_purchase.purchase_type,区分流转过程的不同*/
    private String action;

    /**采购批次流水号*/
    private String serialNumber;

    /**流转记录名称，如：一级审批、二级审批、入库等等*/
    private String name;

    /**流程处理人员ID*/
    private Long mcUserId;

    /**流程处理人员真实名称*/
    private String processUserName;

    /**处理结果：0-处理中 1-通过 2-驳回 3-完成*/
    private Integer status;

    /**是否显示当前记录 0-隐藏 1-显示*/
    private Integer flag;

}