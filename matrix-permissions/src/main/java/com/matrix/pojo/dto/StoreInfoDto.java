package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreInfoDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = 2500640178558323082L;

	/** 注册门店表id*/
    private Long id;

    /** 客户id*/
    private Long cid;

    /** 门店名称*/
    private String name;
    
    private Long mcOrganizationId;
    
    /** 门店省份id*/
    private Long provinceId;

    /** 门店城市id*/
    private Long cityId;

    /** 门店区县id*/
    private Long areaId;

    /** 门店详细地址*/
    private String address;

    /** 门店联系电话*/
    private String phone;

    /** 门店星级*/
    private Short starLevel;

    /** 1=加盟店 2直营店 3代理商 4连锁店 0其他*/
    private Short type;

    /** 0 暂停营业 1正常运营*/
    private Short status;

    /** 暂停营业原因*/
    private String remark;

    /** 门店所属行业id*/
    private Long dictVocationInfoId;

    /** 地理位置:经度*/
    private String longitude;

    /** 地理位置:纬度*/
    private String latitude;

    /** 负责人id*/
    private Long managerId;

}