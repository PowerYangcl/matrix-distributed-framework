package com.matrix.pojo.entity;

import java.math.BigDecimal;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McUserPosition  extends BaseEntity{
	private static final long serialVersionUID = 2561685678740255251L;

	/**员工岗位表主键|归属EHR系统*/
    private Long id;

    /**
     * 	岗位类型：
     * 		1-管理/财务/HR岗 
     * 		2-运营岗 
     * 		3-IT技术岗 
     * 		4-生产岗(自营) 
     * 		5-生产岗(外采) 
     * 		6-生产岗(自营餐厅) 
     * 		7-生产岗(娱乐项目) 
     * 		8-服务岗
     * */
    private Integer positionType;

    /**岗位名称*/
    private String position;

    /**
     * 	职级：
     * 		管理序列职级：职员/经理/总经理/副总/CEO/董事长
     * 		生产序列职级：学员/初级培育员/中级培育员/高级培育员/技术专家
     * 		IT技术序列职级：中级/高级/资深/架构
     * */
    private String positionLevel;

    /**岗位技能要求*/
    private String skillRequirement;

    /**薪资上限*/
    private BigDecimal salaryCap;

    /**薪资下限*/
    private BigDecimal salaryFloor;

    /**绩效薪资*/
    private BigDecimal meritPay;

    /**年终奖上限月数，仅限在职人员，标准14薪对应此基数为2*/
    private Integer annualBonus;

    /**岗位晋升条件*/
    private String promotion;

    /**1-在职岗位 2-外包岗位*/
    private Integer activeStaff;

    /**职能描述*/
    private String remark;
}