package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McUserOrgInfo  extends BaseEntity{
	private static final long serialVersionUID = 316190254481819232L;

	/**员工归属信息表主键|归属EHR系统*/
    private Long id;

    /**乡镇/村/棚区编号|生产岗位/大棚编号|岗位点*/
    private String name;

    /**town:乡镇名称;village:村名称;area:棚区编号/生产岗位，比如：A区/游乐场;num:大棚编号 1,2,3等*/
    private String level;

    /**org_type=num时记录完整名称，比如：漷县镇-草厂村-A棚区-1号棚/漷县镇-草厂村-餐厅名-后厨*/
    private String fullName;

    /**父节点。root为0*/
    private Long parentId;

    /**顺序码,同一层次对应的显示顺序*/
    private Integer seqnum;

    /**所属省市区备注，比如：北京市-市辖区-通州区*/
    private String regions;

    /**备注*/
    private String remark;

}




