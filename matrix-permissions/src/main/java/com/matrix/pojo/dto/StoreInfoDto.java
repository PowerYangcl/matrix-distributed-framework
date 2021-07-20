package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreInfoDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = 2500640178558323082L;

    private Long id;

    private Long cid;

    /** 门店名称*/
    private String name;
    

}