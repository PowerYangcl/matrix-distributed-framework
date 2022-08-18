package com.matrix.pojo.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.matrix.base.BaseEntity;
import com.matrix.validate.Validation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GwRouteRateFlowKeyWords extends BaseEntity{
	
	private static final long serialVersionUID = 1677899529277610082L;

	private Long id;
    private String routeId;
    
    @NotBlank(message = "106010018")			// 106010018=统计规则描述不得为空
    private String name;
    
    private String type;
    private String target;
    private String param;
    private String value;
    
    @NotBlank(message = "106010019")			// 106010019=统计维度不得为空
    @Pattern(regexp=Validation.GW_STATISTICAL_DIMENSION, message="106010020")		// 106010020=统计维度仅限于：month|day|hour|minute|second
    private String statisticalDimension;

}