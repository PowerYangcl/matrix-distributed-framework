package com.matrix.pojo.entity;


import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McUserMerit  extends BaseEntity{
	private static final long serialVersionUID = 8009248214176345082L;

	/**员工绩效日志表|绩效评定人以create_user_name为准|归属EHR系统*/
    private Long id;

    /**用户id*/
    private Long mcUserId;

    /**年度，比如2025*/
    private String year;

    /**
     * 	绩效阶段：
     * 		1-第一季度
     * 		2-第二季度
     * 		3-第三季度
     * 		4-第四季度
     * */
    private Integer flag;

    /**
     * 	季度绩效得分：
     * 		A+(突出贡献)
     * 		A(无客诉或重要责任事故)
     * 		B(客诉5次以内)
     * 		B-(客诉10次以内)
     * 		C(客诉10次以上)
     * 		D(无法胜任工作或效率明显低下)
     * */
    private String merit;

    /**绩效评语，阐述绩效得分原因*/
    private String remark;

}

















