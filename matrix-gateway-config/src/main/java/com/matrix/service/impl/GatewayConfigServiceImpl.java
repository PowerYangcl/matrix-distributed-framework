package com.matrix.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.dao.IGwRouteMapper;
import com.matrix.dao.IGwRoutePredicatesMapper;
import com.matrix.dao.IGwRouteRateFlowKeyWordsMapper;
import com.matrix.pojo.dto.GwRouteDto;
import com.matrix.pojo.entity.GwRoute;
import com.matrix.pojo.entity.GwRoutePredicates;
import com.matrix.pojo.entity.GwRouteRateFlowKeyWords;
import com.matrix.pojo.request.AddGatewayRouteRequest;
import com.matrix.pojo.request.EditGatewayRouteRequest;
import com.matrix.pojo.request.FindGatewayRouteListRequest;
import com.matrix.pojo.response.GwRouteResponse;
import com.matrix.service.IGatewayConfigService;

@Validated
@Service("gatewayConfigService")
public class GatewayConfigServiceImpl extends BaseClass implements IGatewayConfigService {

	@Resource
	private IGwRouteMapper gwRouteMapper;
	
	@Resource
	private IGwRoutePredicatesMapper gwRoutePredicatesMapper;
	
	@Resource
	private IGwRouteRateFlowKeyWordsMapper gwRouteRateFlowKeyWordsMapper;
	
	
	/**
	 * @description: 添加一条网关路由规则，入参包含数组
	 * 
	 * @author Yangcl
	 * @date 2022-8-15 15:34:02
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.4-spring-cloud-gateway
	 */
	@Transactional
	public Result<?> ajaxBtnGatewayRouteAdd(AddGatewayRouteRequest param, HttpServletRequest request) {
		Result<?> validate = param.validate();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			GwRoute gr = param.buildGwRoute();
			gwRouteMapper.insertSelective(gr);
			
			List<GwRoutePredicates> plist = param.buildGwRoutePredicateList();
			for(GwRoutePredicates e : plist) {
				gwRoutePredicatesMapper.insertSelective(e);
			}
			
			List<GwRouteRateFlowKeyWords> rateList = param.buildGwRouteRateFlowKeyWordList();
			for(GwRouteRateFlowKeyWords e : rateList) {
				gwRouteRateFlowKeyWordsMapper.insertSelective(e);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(this.getInfo(100010103) + "：" + ex.getCause().getMessage());		// 100010103=数据添加失败，服务器异常!
		}
		return Result.SUCCESS(this.getInfo(100010102));  		// 100010102=数据添加成功!
	}

	/**
	 * @description: 网关路由规则列表
	 * 
	 * @author Yangcl
	 * @date 2022-8-18 14:35:45
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.4-spring-cloud-gateway
	 */
	public Result<PageInfo<GwRouteResponse>> ajaxGatewayRouteList(FindGatewayRouteListRequest param, HttpServletRequest request) {
		GwRouteDto dto = param.buildGatewayRouteListRequest();
		int pageNum = 1;	// 当前第几页 | 必须大于0
    	int pageSize = 10;	// 当前页所显示记录条数
		try {
			if(StringUtils.isAnyBlank(request.getParameter("pageNum") , request.getParameter("pageSize"))) {
				pageNum = dto.getStartIndex();
				pageSize = dto.getPageSize();
			} else {
				pageNum = Integer.parseInt(request.getParameter("pageNum")); 
				pageSize = Integer.parseInt(request.getParameter("pageSize")); 
			}
			PageHelper.startPage(pageNum , pageSize);
			List<GwRoute> list = gwRouteMapper.queryPageByDto(dto);
			if (CollectionUtils.isNotEmpty(list)) {
				List<GwRoutePredicates> predicateList = gwRoutePredicatesMapper.findList(null);
				Map<String, List<GwRoutePredicates>> pmaps = new HashMap<String, List<GwRoutePredicates>>();
				if(CollectionUtils.isNotEmpty(predicateList)) {
					pmaps = predicateList.stream().collect(Collectors.groupingBy(GwRoutePredicates::getRouteId));
				}
				List<GwRouteRateFlowKeyWords> keywordList = gwRouteRateFlowKeyWordsMapper.findList(null);
				Map<String, List<GwRouteRateFlowKeyWords>> kmaps = new HashMap<String, List<GwRouteRateFlowKeyWords>>();
				if(CollectionUtils.isNotEmpty(keywordList)) {
					kmaps = keywordList.stream().collect(Collectors.groupingBy(GwRouteRateFlowKeyWords::getRouteId));
				}
				
				List<GwRouteResponse> list_ = new ArrayList<GwRouteResponse>();
				for(GwRoute e : list) {
					GwRouteResponse v = new GwRouteResponse();
					v.build(e, pmaps, kmaps); 
					list_.add(v);
				}
				return Result.SUCCESS(this.getInfo(100010114), new PageInfo<GwRouteResponse>(list_));  // 100010114=分页数据返回成功!
			}else {
				return Result.SUCCESS(this.getInfo(100010115), ResultCode.RESULT_NULL);  // 100010115=分页数据返回成功, 但没有查询到可以显示的数据!
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(this.getInfo(100010116), ResultCode.SERVER_EXCEPTION);   // 100010116=分页数据返回失败，服务器异常!
		}
	}

	
	
	/**
	 * @description: 修改一条网关路由规则，入参以对象方式发送(对象包含数组)
	 * 
	 * @return Result<?>
	 * @author Yangcl
	 * @date 2022-11-16 17:55:55
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.4-spring-cloud-gateway
	 */
	@Transactional
	public Result<?> ajaxBtnGatewayRouteEdit(EditGatewayRouteRequest param, HttpServletRequest request) {
		Result<?> validate = param.validate();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		
		String routeId = param.getRouteId();
		try {
			GwRoute gr = param.buildGwRoute();
			gwRouteMapper.updateByRouteId(gr);
			
			GwRoutePredicates grp = new GwRoutePredicates();
			grp.setRouteId(routeId);
			grp.buildUpdateCommon(param.getUserCache());
			gwRoutePredicatesMapper.deleteByRouteId(grp);
			List<GwRoutePredicates> plist = param.buildGwRoutePredicateList();
			for(GwRoutePredicates e : plist) {
				gwRoutePredicatesMapper.insertSelective(e);
			}
			
			GwRouteRateFlowKeyWords grrfkw = new GwRouteRateFlowKeyWords();
			grrfkw.setRouteId(routeId);
			grrfkw.buildUpdateCommon(param.getUserCache());
			gwRouteRateFlowKeyWordsMapper.deleteByRouteId(grrfkw);
			List<GwRouteRateFlowKeyWords> rateList = param.buildGwRouteRateFlowKeyWordList();
			for(GwRouteRateFlowKeyWords e : rateList) {
				gwRouteRateFlowKeyWordsMapper.insertSelective(e);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(this.getInfo(100010105) + "：" + ex.getCause().getMessage());		// 100010105=数据更新失败，服务器异常!
		}
		return Result.SUCCESS(this.getInfo(100010104));  		// 100010104=数据更新成功!
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}





















































