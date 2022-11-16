package com.matrix.pojo.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.matrix.pojo.entity.GwRoute;
import com.matrix.pojo.entity.GwRoutePredicates;
import com.matrix.pojo.entity.GwRouteRateFlowKeyWords;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GwRouteResponse {
	private static final long serialVersionUID = 2682519132645430551L;

	private Long id;
    private String routeId;
    private String uri;
    private String active;
    private String requestType;
    private String routeType;
    private String rateFlowMark;
    private String requestSnapshotMark;
    private String snapshotCount;
    private String snapshotBeginTime;
    private String snapshotEndTime;
    private String status;		// 此条规则是否生效：0-不生效 1-生效；默认不生效，生效需要在列表触发
    private String description;
    
	private Date createTime;
	private Long createUserId;
	private String createUserName;
	private Date updateTime;
	private Long updateUserId;
	private String updateUserName;
	private Integer deleteFlag;               // 0 删除 | 1 未删除 数据库记录默认未删除
	
	private List<GwRoutePredicateResponse> predicateList = new ArrayList<GwRoutePredicateResponse>();
	
	private List<GwRouteRateFlowKeyWordResponse> rfmList = new ArrayList<GwRouteRateFlowKeyWordResponse>();
    
    public void build(GwRoute e , Map<String, List<GwRoutePredicates>> pmaps, Map<String, List<GwRouteRateFlowKeyWords>> kmaps) {
    	id = e.getId();
    	routeId = e.getRouteId();
    	uri = e.getUri();
    	active = e.getActive();		// 环境：dev|test|pre|master or prod
    	requestType = e.getRequestType();		// 请求方式：get|post|put|delete
    	routeType = e.getRouteType();		// 路由规则类型：project-项目 url-单接口
    	rateFlowMark = e.getRateFlowMark() == 0 ? "不标记" : "标记";		// 是否开启流量标记：0-不标记 1-标记
    	requestSnapshotMark = e.getRequestSnapshotMark() == 0 ? "不保存" : "保存";		// 是否保存流量快照：0-不保存 1-保存(用于模拟压测使用)
    	snapshotCount = e.getSnapshotCount() == null ? "" : e.getSnapshotCount().toString();
    	snapshotBeginTime = e.getSnapshotBeginTime();
    	snapshotEndTime = e.getSnapshotEndTime();
    	status = e.getStatus() == 0 ? "不生效" : "生效";			// 此条规则是否生效：0-不生效 1-生效
    	description = e.getDescription();
    	createTime = e.getCreateTime();
    	createUserId = e.getCreateUserId();
    	createUserName = e.getCreateUserName();
    	updateTime = e.getUpdateTime();
    	updateUserId = e.getUpdateUserId();
    	updateUserName = e.getUpdateUserName();
    	deleteFlag = e.getDeleteFlag();
    	
    	List<GwRoutePredicates> plist = pmaps.get(routeId);
    	for(GwRoutePredicates r : plist) {
    		GwRoutePredicateResponse p = new GwRoutePredicateResponse();
    		p.setId(r.getId());
    		p.setPredicate(r.getPredicate());
    		p.setPredicateValue(r.getPredicateValue());
    		predicateList.add(p);
    	}
    	
    	if(e.getRateFlowMark() == 1) {
    		List<GwRouteRateFlowKeyWords> klist = kmaps.get(routeId);
    		for(GwRouteRateFlowKeyWords r : klist) {
    			GwRouteRateFlowKeyWordResponse k = new GwRouteRateFlowKeyWordResponse();
    			k.setId(r.getId());
    			k.setName(r.getName());
    			k.setType(r.getType());
    			k.setTarget(r.getTarget());
    			k.setParam(r.getParam());
    			k.setValue(r.getValue());
    			k.setStatisticalDimension(r.getStatisticalDimension());
    			rfmList.add(k);
    		}
    	}
    	
    	
    }
}
















































