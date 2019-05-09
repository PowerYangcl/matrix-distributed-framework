package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

public class SysTableOpreateRecordDto extends BaseDto {
	private static final long serialVersionUID = -5596518829097490799L;
	private Long id;
    private Long cid;
    private String mdvalue;
    private Long zid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public String getMdvalue() {
		return mdvalue;
	}
	public void setMdvalue(String mdvalue) {
		this.mdvalue = mdvalue;
	}
	public Long getZid() {
		return zid;
	}
	public void setZid(Long zid) {
		this.zid = zid;
	}
}
