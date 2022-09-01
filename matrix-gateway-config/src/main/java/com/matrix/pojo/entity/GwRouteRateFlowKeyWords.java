package com.matrix.pojo.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.matrix.base.BaseEntity;
import com.matrix.validate.Validation;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 
 * 		1、统计整个接口项目流量 type=1 or 2 or 3 or 4
 * 		2、统计当前接口项目下某个接口流量
 * 		3、统计当前接口项目下指定接口的某个或某几个参数产生的流量
 * 		4、统计当前接口项目下指定接口的某个参数对应的值所产生的流量
					{
						"type": "1"
					},
					{
						"type": "2",
						"target": "MANAGER-API-901"
					},
					{
						"type": "3",
						"target": "MANAGER-API-901",
						"param": "productName"
					},{
						"type": "4",
						"target": "MANAGER-API-901",
						"param": "productName",
						"value": "时间简史"
					}
 * 
 * @author Yangcl
 * @date 2022-9-1 18:31:22
 * @home https://github.com/PowerYangcl
 * @path matrix-gateway-config / com.matrix.pojo.entity.GwRouteSnapshot.java
 * @version 1.6.1.4-spring-cloud-gateway
 */
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