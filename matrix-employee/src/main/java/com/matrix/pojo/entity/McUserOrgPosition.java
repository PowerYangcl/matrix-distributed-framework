package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McUserOrgPosition  extends BaseEntity{
	private static final long serialVersionUID = 5147446386806996717L;

	/**员工扩展信息关联表,mc_user_info.type=user时生效|归属EHR系统*/
    private Long id;

    /**position|org*/
    private String extType;

    /**用户id*/
    private Long mcUserId;

    /**mc_user_position表 或 mc_user_org_info表主键*/
    private Long extensionId;

}