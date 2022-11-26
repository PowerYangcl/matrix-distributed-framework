package com.matrix.pojo.entity;

import java.math.BigDecimal;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McUserInfoExt  extends BaseEntity{
	private static final long serialVersionUID = 8098659638019194963L;

	/**员工扩展信息表,mc_user_info.type=user时生效，用户信息主体属性补充|归属EHR系统*/
    private Long id;

    /**用户id*/
    private Long mcUserId;

    /**员工真实姓名*/
    private String name;

    /**员工最终薪资=合同薪资+绩效薪资*/
    private BigDecimal salary;

    /**开户行名称*/
    private String bankName;

    /**银行卡号/薪资卡*/
    private String bankCard;

    /**员工基础薪资*/
    private BigDecimal salaryBasic;

    /**员工绩效薪资*/
    private BigDecimal salaryMerit;

    /**员工实际成本：当年实际工作月数*薪资(含企业负担五险一金/可平均计算) + 绩效奖金|四舍五入后的结果*/
    private BigDecimal actualCost;

    /**
     * 	员工当前年度绩效(即总评，不同于季度绩效)：
     * 		A+(突出贡献)
     * 		A(无客诉或重要责任事故)
     * 		B(客诉5次以内)
     * 		B-(客诉10次以内)
     * 		C(客诉10次以上)
     * 		D(无法胜任工作或效率明显低下)
     * */
    private String merit;

    /**五险一金缴纳比例，0~100代表百分比*/
    private Integer premium;

    /**过往疾病史，此处为描述文本*/
    private String anyPastHistoryOfIllness;

    /**公司是否为员工子女上了重疾险，上了的话每年额度多少*/
    private BigDecimal criticalIllnessInsurance;

    /**籍贯*/
    private String nativePlace;

    /**当前居住地址*/
    private String presentAddress;

    /**身份证正面图片信息*/
    private String idcardPicFront;

    /**身份证反面图片信息*/
    private String idcardPicBack;

    /**英语水平*/
    private String enProficiencyLevel;

    /**第一学历(最高学历)，比如：哈佛大学/计算机学院/软件工程系/计算机学士*/
    private String firstDegree;

    /**第一学历学位证书图片*/
    private String firstDegreeUrl;

    /**第一学历毕业证书图片*/
    private String firstDegreeDiplomaUrl;

    /**第二学历*/
    private String secondDegree;

    /**第二学历学位证书图片*/
    private String secondDegreeUrl;

    /**第三学历*/
    private String thirdDegree;

    /**第三学历学位证书图片*/
    private String thirdDegreeUrl;

}




















































