package com.matrix.pojo.entity;


import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GwRoute extends BaseEntity{
	private static final long serialVersionUID = 2382519132645430551L;

	private Long id;
    private String routeId;
    private String uri;
    private String active;
    private String requestType;
    private String routeType;
    private Integer rateFlowMark;
    private Integer requestSnapshotMark;
    private Integer snapshotCount;
    private String snapshotBeginTime;
    private String snapshotEndTime;
    private Integer status;		// 此条规则是否生效：0-不生效 1-生效；默认不生效，生效需要在列表触发
    private String description;

}