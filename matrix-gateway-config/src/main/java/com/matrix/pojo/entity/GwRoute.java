package com.matrix.pojo.entity;

import java.util.Date;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GwRoute  extends BaseEntity{
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
    private Date snapshotBeginTime;
    private Date snapshotEndTime;
    private Integer status;
    private String description;

}