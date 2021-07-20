package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.dto.StoreInfoDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class FindStoreInfoRequest implements Serializable{

	private static final long serialVersionUID = 2514542612578002868L;

	private McUserInfoView userCache;
	
	private Long id;
	
    /** 客户id*/
    private Long cid;

    /** 门店名称*/
    private String name;
    
    /** 门店省份id*/
    private Long provinceId;

    /** 门店城市id*/
    private Long cityId;

    /** 门店区县id*/
    private Long areaId;
    
    /** 门店联系电话*/
    private String phone;

    /** 门店星级*/
    private Short starLevel;

    /** 1=加盟店 2直营店 3代理商 4连锁店 0其他*/
    private Short type;

    /** 0 暂停营业 1正常运营*/
    private Short status;
	
	public StoreInfoDto buildAjaxStoreInfoList() {
		StoreInfoDto dto = new StoreInfoDto();
		dto.setCid(userCache.getCid());
		dto.setName(name);
		return dto;
	}
	
	public StoreInfoDto buildStoreInfoList() {
		StoreInfoDto dto = new StoreInfoDto();
		dto.setCid(userCache.getCid());
		dto.setName(name);
		return dto;
	}
	
	public StoreInfoDto buildFindStoreInfo() {
		StoreInfoDto dto = new StoreInfoDto();
		dto.setCid(userCache.getCid());
		dto.setId(id);
		return dto;
	}
}














