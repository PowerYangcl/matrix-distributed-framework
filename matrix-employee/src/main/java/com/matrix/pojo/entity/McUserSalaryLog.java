package com.matrix.pojo.entity;

import java.math.BigDecimal;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McUserSalaryLog  extends BaseEntity{
	private static final long serialVersionUID = 1789443674427643710L;

	/**员工薪资发放记录表|归属EHR系统*/
    private Long id;

    /**员工ID*/
    private Long mcUserId;

    /**薪资发放年份*/
    private String year;

    /**薪资发放月份*/
    private String month;

    /**实发薪资*/
    private BigDecimal salaryPay;

    /**员工基础薪资*/
    private BigDecimal salaryBasic;

    /**应发绩效薪资=合同绩效薪资*绩效得分，即：mc_user_info_ext.salary_merit * this.merit_point*/
    private BigDecimal salaryMerit;

    /**
     * 	绩效得分：
     * 		A+ = 1.5
     * 		A = 1.2
     * 		B = 1
     * 		B- = 0.5
     * 		C = 0.1
     * 		D = 0
     * */
    private BigDecimal meritPoint;

    /**社保扣款-个人*/
    private BigDecimal socialSecurityPersonal;

    /**社保扣款-公司*/
    private BigDecimal socialSecurityCompany;

    /**个人所得税*/
    private BigDecimal individualIncomeTax;

    /**住房公积金扣款-个人*/
    private BigDecimal housingFundPersonal;

    /**住房公积金扣款-公司*/
    private BigDecimal housingFundCompany;

    /**特殊贡献奖金*/
    private BigDecimal bonus;

    /**特殊贡献奖金发放原因*/
    private String bonusReason;

    /**产生罚款金额*/
    private BigDecimal penalty;

    /**罚款金额原因*/
    private String penaltyReason;

    /**备注*/
    private String remark;
}