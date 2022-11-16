package com.matrix.pojo.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.matrix.aspectj.IdempotentRequest;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.entity.GwRoute;
import com.matrix.pojo.entity.GwRoutePredicates;
import com.matrix.pojo.entity.GwRouteRateFlowKeyWords;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.util.DateUtil;
import com.matrix.validate.Validation;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @description: edit by routeId，不以自增id为依据
 * 
 * @author Yangcl
 * @date 2022-11-16 17:38:30
 * @home https://github.com/PowerYangcl
 * @path matrix-gateway-config / com.matrix.pojo.request.EditGatewayRouteRequest.java
 * @version 1.6.1.4-spring-cloud-gateway
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class EditGatewayRouteRequest extends IdempotentRequest implements Serializable{

	private static final long serialVersionUID = 1690857983380892365L;
	
	private McUserInfoView userCache;
	
	// GwRoute
	@NotBlank(message = "106010002")		// 106010002=请求参数：路由规则ID(routeId)不得为空
    private String routeId;
	
	@NotBlank(message = "106010003")			// 106010003=路由转发路径不得为空
    private String uri;
	
	@NotBlank(message = "106010004")			// 106010004=生产环境不得为空
	@Pattern(regexp=Validation.GW_ACTIVE, message="106010005")		// 106010005=生产环境仅限于：dev|test|pre|master|prod
    private String active;
    
	@NotBlank(message = "106010006")			// 106010006=请求方式不得为空
	@Pattern(regexp=Validation.GW_REQUEST_TYPE, message="106010007")			// 106010007=请求方式仅限于：get|post|put|delete
    private String requestType;
    
	@NotBlank(message = "106010008")			// 106010008=路由规则类型不得为空
	@Pattern(regexp=Validation.GW_ROUTE_TYPE, message="106010009")		// 106010009=路由规则类型仅限于：project|url
    private String routeType;
	
	@NotNull(message = "106010010")		// 106010010=是否开启流量标记不得为空
	private Integer rateFlowMark;		// 是否开启流量标记：0-不标记 1-标记
	
	@NotNull(message = "106010011")		// 106010011=是否保存流量快照不得为空
	private Integer requestSnapshotMark;		// 是否保存流量快照：0-不保存 1-保存(用于模拟压测使用)
	
	@Length(max = 7, message = "106010012")		// 106010012=保存流量快照不建议超过9999999(千万条记录以下)
	@Pattern(regexp=Validation.REGEX_NUMBER, message="100020119")		// 100020119=请输入数字
	private String snapshotCount;
	
	private String snapshotBeginTime;
	
    private String snapshotEndTime;
    
    @NotBlank(message = "106010013")			// 106010013=路由转发规则描述不得为空
    private String description;
    
    @Valid
    @NotNull(message = "106010014")		// 106010014=断言规则不得为空
    private List<GwRoutePredicates> predicateList;
    
    @Valid
    private List<GwRouteRateFlowKeyWords> rfmList;		//  不一定有值
    
    public Result<?> validate() {
    	if(this.requestSnapshotMark != 0 && this.requestSnapshotMark != 1) {	// 0-不保存 1-保存(用于模拟压测使用)
    		return Result.ERROR(this.getInfo(106010015), ResultCode.INVALID_ARGUMENT);		// 106010015=流量快照取值错误
    	}
    	if(this.rateFlowMark != 0 && this.rateFlowMark != 1) {	// 是否开启流量标记：0-不标记 1-标记
    		return Result.ERROR(this.getInfo(106010016), ResultCode.INVALID_ARGUMENT);		// 106010016=流量标记取值错误
    	}
    	
    	if(StringUtils.isNotBlank(this.snapshotBeginTime) && StringUtils.isNotBlank(this.snapshotEndTime)) {
    		DateUtil du = new DateUtil();
    		if(!du.compareDate(this.snapshotBeginTime, this.snapshotEndTime)) {
    			return Result.ERROR(this.getInfo(106010017), ResultCode.INVALID_ARGUMENT);		// 106010017=流量快照开始时间不得大于流量快照结束时间
    		}
    	}
    	return Result.SUCCESS();
    }
    
    
    public GwRoute buildGwRoute() {
    	GwRoute e = new GwRoute();
    	e.setRouteId(this.routeId);
    	e.setUri(uri);
    	e.setActive(active);
    	e.setRequestType(requestType);
    	e.setRouteType(routeType);
    	e.setRateFlowMark(rateFlowMark);
    	e.setRequestSnapshotMark(requestSnapshotMark);
    	if(StringUtils.isNotBlank(this.snapshotCount)) {
    		e.setSnapshotCount(Integer.valueOf(snapshotCount));
    	}
    	e.setSnapshotBeginTime(StringUtils.isBlank(snapshotBeginTime) ? null : snapshotBeginTime);
    	e.setSnapshotEndTime(StringUtils.isBlank(snapshotEndTime) ? null : snapshotEndTime);
    	e.setDescription(description);
    	e.setStatus(0);
    	e.buildUpdateCommon(userCache);
    	return e;
    }
    
    public List<GwRoutePredicates> buildGwRoutePredicateList(){
    	List<GwRoutePredicates> list = new ArrayList<GwRoutePredicates>();
    	for(GwRoutePredicates e : this.predicateList) {
    		GwRoutePredicates v = new GwRoutePredicates();
    		v.setRouteId(routeId);
    		v.setPredicate(e.getPredicate());
    		v.setPredicateValue(e.getPredicateValue());
    		v.buildAddCommon(userCache);
    		list.add(v);
    	}
    	return list;
    }
    
    public List<GwRouteRateFlowKeyWords> buildGwRouteRateFlowKeyWordList(){
    	if(CollectionUtils.isEmpty(this.rfmList)) {
    		return new ArrayList<GwRouteRateFlowKeyWords>();
    	}
    	List<GwRouteRateFlowKeyWords> list = new ArrayList<GwRouteRateFlowKeyWords>();
    	for(GwRouteRateFlowKeyWords e : rfmList) {
    		GwRouteRateFlowKeyWords v = new GwRouteRateFlowKeyWords();
    		v.setRouteId(routeId);
    		v.setName(e.getName());
    		v.setType(e.getType());
    		v.setTarget(e.getTarget());
    		v.setParam(e.getParam());
    		v.setValue(e.getValue());
    		v.setStatisticalDimension(e.getStatisticalDimension());
    		v.buildAddCommon(userCache);
    		list.add(v);
    	}
    	return list;
    }
}





















































