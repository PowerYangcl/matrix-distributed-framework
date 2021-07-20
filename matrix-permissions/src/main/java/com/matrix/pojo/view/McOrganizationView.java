package com.matrix.pojo.view;

import com.matrix.base.BaseView;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class McOrganizationView extends BaseView{
	private static final long serialVersionUID = -1348515735738183334L;
	private Long id;
    private Long cid;
    private String name;
    private Long parentId;
    private Integer type;		// 1:总部/总公司/集团|2:区域/子集团/分公司|3:门店|4:部门
    private String platform;
    private Long managerId;
    private String managerName;
    private Integer storeType;
    private Integer seqnum;
    private String mobile;
    private String address;
    private String remark;
    private List<McOrganizationView> orginList;

}
