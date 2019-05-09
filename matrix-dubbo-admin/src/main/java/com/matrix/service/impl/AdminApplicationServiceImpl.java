package com.matrix.service.impl;

import com.matrix.pojo.dto.AppProvideDto;
import com.matrix.pojo.dto.ApplicationDto;
import com.matrix.pojo.dto.RpcFunctionDto;
import com.matrix.pojo.view.AppProvideInfo;
import com.matrix.pojo.view.ApplicationView;
import com.matrix.pojo.view.ConsumerView;
import com.matrix.pojo.view.DubboNodeView;
import com.matrix.pojo.view.Node;
import com.matrix.pojo.view.ProviderView;
import com.matrix.pojo.view.RpcFunctionView;
import com.matrix.service.AbstractDubboAdminService;
import com.matrix.service.IAdminApplicationService;
import com.matrix.service.IAdminConsumerService;
import com.matrix.service.IAdminOverrideService;
import com.matrix.service.IAdminProviderService;
import com.matrix.utils.Tool;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.monitor.MonitorService;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

/**
 * @description: dubbo admin application service
 *
 * @author Yangcl
 * @date 2018年8月27日 上午11:44:17 
 * @version 1.0.0.1
 */
@Service("adminApplicationService")
public class AdminApplicationServiceImpl extends AbstractDubboAdminService implements IAdminApplicationService {

	@Resource
	private IAdminProviderService adminProviderService;
	@Resource
	private IAdminConsumerService adminConsumerService;
	@Resource
	private IAdminOverrideService adminOverrideService;
	
	
	
	/**
	 * @description:  查询所有应用列表信息
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年8月27日 下午5:15:30 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindApplicationList(ApplicationDto dto) {
		JSONObject result = new JSONObject();
		
		List<ApplicationView> applicationList = new ArrayList<ApplicationView>();
		
		ConcurrentMap<String, Map<Long, URL>> providers = getServiceByCategory(Constants.PROVIDERS_CATEGORY);			// providers
        if(providers != null){
            for(Map.Entry<String, Map<Long, URL>> oneService:providers.entrySet()){
                Map<Long, URL> urls = oneService.getValue();
                for(Map.Entry<Long,URL> url : urls.entrySet()){
                    ApplicationView view = new ApplicationView();
                    view.setApplication(url.getValue().getParameter(Constants.APPLICATION_KEY));
                    view.setUsername(url.getValue().getParameter("owner"));
                    view.setType(view.PROVIDER);
                    if(!applicationList.contains(view)){
                        applicationList.add(view);
                    }
                    break;
                }
            }
        }
        
        ConcurrentMap<String, Map<Long, URL>> consumers = getServiceByCategory(Constants.CONSUMERS_CATEGORY);		// consumers
        if(consumers != null){
            for(Map.Entry<String, Map<Long, URL>> oneService:consumers.entrySet()){
                //某个服务的所有地址，一个服务可能会被多个应用消费
                Map<Long, URL> urls = oneService.getValue();
                for(Map.Entry<Long,URL> url : urls.entrySet()){
                    if(url.getValue().getParameter(Constants.INTERFACE_KEY).equals( MonitorService.class.getName() )){     // 检索dubbo MonitorService服务信息
                        continue;
                    }
                    
                    ApplicationView view = new ApplicationView();
                    view.setApplication(url.getValue().getParameter(Constants.APPLICATION_KEY));
                    view.setUsername(url.getValue().getParameter("owner"));
                    if(!applicationList.contains(view)){
                        view.setType(view.CONSUMER);   // 标记为消费者
                        applicationList.add(view);
                    }else{
                        view=applicationList.get(applicationList.indexOf(view));
                        if(view.getType()==view.PROVIDER){
                            view.setType(view.PROVIDER_AND_CONSUMER);  // 标记为提供者和消费者
                        }
                    }
                    
                }
            }
        }
		
        result.put("status", "success");
        if(StringUtils.isBlank(dto.getApplication()) && StringUtils.isBlank(dto.getUsername()) && dto.getType() == null) {
        	result.put("list", applicationList);
        	return result;
        }
		
        List<ApplicationView> list = new ArrayList<ApplicationView>();
        for(ApplicationView v : applicationList) {
        	if(StringUtils.isNotBlank(dto.getApplication()) && StringUtils.isNotBlank(dto.getUsername()) && dto.getType() != null) {
        		if( v.getApplication().equals(dto.getApplication()) && v.getUsername().equals(dto.getUsername()) && v.getType() == dto.getType() ) {
        			list.add(v);
        		}
        		continue;
        	}
        	if(StringUtils.isNotBlank(dto.getApplication()) && StringUtils.isNotBlank(dto.getUsername()) && dto.getType() == null) {
        		if( v.getApplication().equals(dto.getApplication()) && v.getUsername().equals(dto.getUsername()) ) {
        			list.add(v);
        		}
        		continue;
        	}
        	if(StringUtils.isNotBlank(dto.getApplication()) && StringUtils.isBlank(dto.getUsername()) && dto.getType() == null) {
        		if(v.getApplication().equals(dto.getApplication())) {
        			list.add(v);
        		}
        		continue;
        	}
        	if(StringUtils.isNotBlank(dto.getApplication()) && StringUtils.isBlank(dto.getUsername()) && dto.getType() != null) {
        		if( v.getApplication().equals(dto.getApplication()) && v.getType() == dto.getType() ) {
        			list.add(v);
        		}
        		continue;
        	}
        	if(StringUtils.isBlank(dto.getApplication()) && StringUtils.isNotBlank(dto.getUsername()) && dto.getType() != null) {
        		if( v.getUsername().equals(dto.getUsername()) && v.getType() == dto.getType() ) {
        			list.add(v);
        		}
        		continue;
        	}
        	if(StringUtils.isBlank(dto.getApplication()) && StringUtils.isNotBlank(dto.getUsername()) && dto.getType() == null) {
        		if(v.getUsername() != null && v.getUsername().equals(dto.getUsername()) ) {
        			list.add(v);
        		}
        		continue;
        	}
        	if(StringUtils.isBlank(dto.getApplication()) && StringUtils.isBlank(dto.getUsername()) && dto.getType() != null) {
        		if( v.getType() == dto.getType() ) {
        			list.add(v);
        		}
        		continue;
        	}
        }
		
        result.put("list", list);
		return result;
	}
	
	
	/**
	 * @description: Dubbo项目部署节点列表数据集合
	 *
	 * @param dto.application 	服务名称
	 * @author Yangcl
	 * @date 2018年8月29日 下午5:08:51 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindDubboProjectIpList(ApplicationDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(dto.getApplication())) {
			result.put("status", "error");
			result.put("msg", "Dubbo服务名称不可为空");
			return result;
		}
		
		List<ProviderView> providerList = adminProviderService.listProviderByApplication(dto.getApplication()); 
		List<ConsumerView> consumerList = adminConsumerService.listConsumerByApplication(dto);
		
		List<Node> nodeList = new ArrayList<Node>();
		for(ProviderView provider : providerList){
            Node node = new Node();
            node.setNodeAddress(provider.getAddress());
            node.setId(provider.getId());
            node.setType("生产者"); 
            if(!nodeList.contains(node)){
                nodeList.add(node);
            }
        }
        for(ConsumerView consumer : consumerList){
            Node node = new Node();
            node.setNodeAddress(consumer.getAddress());
            node.setId(consumer.getId());
            node.setType("消费者"); 
            if(!nodeList.contains(node)){
                nodeList.add(node);
            }
        }
        
        result.put("status", "success");
        if(StringUtils.isBlank( dto.getNodeAddress() )) {
        	result.put("list" , nodeList);
        	return result;
        }
        
        List<Node> list = new ArrayList<Node>();
        for(Node n : nodeList) {
        	if(StringUtils.contains(n.getNodeAddress().toLowerCase() , dto.getNodeAddress().toLowerCase())) {
        		list.add(n);
        	}
        }
        
        result.put("list" , list);
		return result;
	}


	/**
	 * @description:  Dubbo项目指定部署节点下的RPC接口类列表集合|jsp页面数据集合
	 *
	 * @param dto.application 	服务名称					必填
	 * @param dto.nodeAddress 	host ip address		必填
	 * 
	 * @author Yangcl
	 * @date 2018年8月30日 下午3:28:14 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindDubboProjectInterfaceList(ApplicationDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(dto.getApplication()) || StringUtils.isBlank(dto.getNodeAddress())) {
			result.put("status", "error");
			result.put("msg", "Dubbo服务名称和主机地址不可为空");
			return result;
		}
		
		List<ProviderView> providerList = new ArrayList<ProviderView>();  // 过滤后的集合
		
		List<ProviderView> providers = adminProviderService.listProviderByApplication(dto.getApplication()); 
		Iterator<ProviderView> iterator = providers.iterator();
        while(iterator.hasNext()){
        	ProviderView provider = iterator.next();
            if( !provider.getAddress().equals(dto.getNodeAddress()) ){
                iterator.remove();
            }else{
                providerList.add( adminOverrideService.configProvider(provider) );
            }
        }
		
        result.put("status", "success");
		
		if(StringUtils.isBlank(dto.getServiceKey()) && dto.getDynamic() == null && dto.getEnabled() == null) {
			result.put("list", providerList);  
			return result;
		}
		
		List<ProviderView> list = new ArrayList<ProviderView>();
		for(ProviderView v : providerList) {
			if(StringUtils.isNotBlank(dto.getServiceKey()) && dto.getDynamic() != null && dto.getEnabled() != null) {
				if(StringUtils.contains(v.getServiceKey().toLowerCase() , dto.getServiceKey().toLowerCase()) && dto.getDynamic() == v.isDynamic() && dto.getEnabled() == v.isEnabled()) {
					list.add(v);
				}
			}
			if(StringUtils.isNotBlank(dto.getServiceKey()) && dto.getDynamic() != null && dto.getEnabled() == null) {
				if(StringUtils.contains(v.getServiceKey().toLowerCase() , dto.getServiceKey().toLowerCase()) && dto.getDynamic() == v.isDynamic() ) {
					list.add(v);
				}
			}
			if(StringUtils.isNotBlank(dto.getServiceKey()) && dto.getDynamic() == null && dto.getEnabled() == null) {
				if(StringUtils.contains(v.getServiceKey().toLowerCase() , dto.getServiceKey().toLowerCase())) {
					list.add(v);
				}
			}
			if(StringUtils.isNotBlank(dto.getServiceKey()) && dto.getDynamic() == null && dto.getEnabled() != null) {
				if(StringUtils.contains(v.getServiceKey().toLowerCase() , dto.getServiceKey().toLowerCase())  && dto.getEnabled() == v.isEnabled()) {
					list.add(v);
				}
			}
			
			if(StringUtils.isBlank(dto.getServiceKey()) && dto.getDynamic() != null && dto.getEnabled() != null) {
				if( dto.getDynamic() == v.isDynamic() && dto.getEnabled() == v.isEnabled()) {
					list.add(v);
				}
			}
			if(StringUtils.isBlank(dto.getServiceKey()) && dto.getDynamic() != null && dto.getEnabled() == null) {
				if( dto.getDynamic() == v.isDynamic()) {
					list.add(v);
				}
			}
			if(StringUtils.isBlank(dto.getServiceKey()) && dto.getDynamic() == null && dto.getEnabled() != null) {
				if( dto.getEnabled() == v.isEnabled() ) {
					list.add(v);
				}
			}
		}
		
		
		result.put("list", list);  
		return result;
	}


	/**
	 * @description: 发布服务列表数据
	 *
	 * @param dto.application 	服务名称
	 * 
	 * @author Yangcl
	 * @date 2018年9月3日 上午10:38:39 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindDubboProjectServiceList(AppProvideDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(dto.getApplication())) {
			result.put("status", "error");
			result.put("msg", "Dubbo服务名称不可为空");
			return result;
		}
		
		try {
			List<ProviderView>  providers = adminProviderService.listProviderByApplication(dto.getApplication());
	        List<AppProvideInfo> provideInfoList = new ArrayList<AppProvideInfo>();
	        Set<String> containMark = new HashSet<String>();
	        StringBuilder protocolBuffer = new StringBuilder();
	        for(ProviderView provider : providers){
	            if(containMark.contains(provider.getServiceKey())){
	                continue;
	            }
	            containMark.add(provider.getServiceKey());

	            AppProvideInfo provideInfo = new AppProvideInfo();
	            provideInfo.setServiceKey(URLEncoder.encode(URLEncoder.encode(provider.getServiceKey(), "UTF-8"), "UTF-8"));
	            provideInfo.setService(Tool.getInterface(provider.getServiceKey()));
	            provideInfo.setVersion(provider.getVersion());
	            provideInfo.setGroup(provider.getGroup());
	            provideInfo.setId(provider.getId());
	            List<ProviderView> providerList  = adminProviderService.listProviderByConditions(
	            																									Constants.INTERFACE_KEY,
            																										provideInfo.getService(),
            																										Constants.GROUP_KEY,
            																										Tool.getGroup(provider.getServiceKey()),
            																										Constants.VERSION_KEY,
            																										Tool.getVersion(provider.getServiceKey())	);
	            for(ProviderView item : providerList){
	                URL url = URL.valueOf(item.getUrl());
	                protocolBuffer.append(url.getProtocol()).append(":").append(url.getPort()).append(",");
	            }

	            if(protocolBuffer.length()>0){
	                protocolBuffer.setLength(protocolBuffer.length()-1);
	            }
	            provideInfo.setProtocol(protocolBuffer.toString());
	            protocolBuffer.setLength(0);
	            provideInfoList.add(provideInfo);
	        }
			
	        result.put("status", "success");
	        result.put("list", provideInfoList);
			return result;
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		result.put("status", "error");
		result.put("msg", "Unsupported Encoding Exception");
		return result;
	}

	
	/**
	 * @description: 每一个Dubbo服务(生产者)所提供的RPC接口方法所对应的消费者列表
	 *
	 * @param dto.id 具体到Dubbo服务所提供的每一个RPC接口，会对应一个长整型ID.
	 *
	 * @author Yangcl
	 * @date 2018年9月3日 下午3:23:45 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindDubboProjectServiceConsumerList(AppProvideDto dto) {
		JSONObject result = new JSONObject();
		if(dto.getId() == null) {
			result.put("status", "error");
			result.put("msg", "Dubbo服务RPC接口唯一标识不可为空");
			return result;
		}
		
		ProviderView provider = adminProviderService.getProviderById(dto.getId());
		if(provider == null) {
			result.put("status", "error");
			result.put("msg", "生产者解析com.alibaba.dubbo.common.URL为空");
			return result;
		}
		
        List<ConsumerView> clist = adminConsumerService.listConsumerByConditions(
        																						Constants.INTERFACE_KEY,
        																						Tool.getInterface(provider.getServiceKey()),
        																						Constants.VERSION_KEY,
        																						Tool.getVersion(provider.getServiceKey()),
        																						Constants.GROUP_KEY,
        																						Tool.getGroup(provider.getServiceKey())	);
        
        List<ApplicationView> applicationList = new ArrayList<ApplicationView>();
        List<String> containMark = new ArrayList<String>();
        for(ConsumerView consumer : clist) {
            if(containMark.contains(consumer.getAddress())){
                continue;
            }
            containMark.add(consumer.getAddress());
            
            ApplicationView av = new ApplicationView();
            av.setUsername(consumer.getUsername());
            av.setApplication(consumer.getApplication());
            av.setType(ApplicationView.CONSUMER);
            av.setFlag("消费者"); 
            
            List<ProviderView> providers = adminProviderService.listProviderByApplication(consumer.getApplication());
            if(providers.size()>0){
                av.setType(ApplicationView.PROVIDER_AND_CONSUMER);
                av.setFlag("生产者&消费者"); 
            }
            applicationList.add(av);
        }
		
		result.put("status", "success");
		result.put("list", applicationList);
		return result;
	}

	
	/**
	 * @description: Dubbo rpc接口方法列表数据
	 *
	 * @param dto.rpcName  com.scrm.services.aem.rpc.IStaffScoreDetailRpcService
	 * 
	 * @author Yangcl
	 * @date 2018年9月4日 上午10:09:10 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindDubboProjectRpcFunctionList(RpcFunctionDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(dto.getRpcName())) { 
			result.put("status", "error");
			result.put("msg", "Dubbo服务RPC接口类名称不可为空");
			return result;
		}
		
		List<RpcFunctionView> list = new ArrayList<RpcFunctionView>();
		try {
			Class<?> clazz= ClassUtils.getClass(dto.getRpcName());
			Method[] methods = clazz.getMethods();
			for(Method m : methods) {
				Type rtype = m.getGenericReturnType();
				
				RpcFunctionView v = new RpcFunctionView();
				v.setFunctionName(m.getName());
				v.setReturnType(rtype.getTypeName()); 
				v.setGenericString(m.toGenericString());
				
				String params = "";
				Map<String , String> paramMap = new TreeMap<String , String>();
				Type[] types = m.getGenericParameterTypes(); 
				for(int i = 0 ; i < types.length ; i ++) {
					String name = types[i].getTypeName();
					paramMap.put( String.valueOf(i) , name );
					params += name.split("\\.")[ name.split("\\.").length -1 ] + "  arg" + i + ", ";
				}
				
				String returnType = "";		// 方法返回值表意
				if(StringUtils.contains(rtype.getTypeName(),  "<")) {		// 返回值类型包含泛型
					String a = rtype.getTypeName().split("<")[0];
					String b = StringUtils.substringBetween(rtype.getTypeName() , "<" , ">");  // 泛型参数
					returnType = a.split("\\.")[a.split("\\.").length -1] + "<" + b.split("\\.")[b.split("\\.").length -1] + ">";
				}else {
					returnType = rtype.getTypeName().split("\\.")[rtype.getTypeName().split("\\.").length -1];
				}
				
				v.setShowName("public " + returnType + "  " + m.getName() + "(" + params.substring(0, params.length()-2) + ")"); 
				v.setParamMap(paramMap);
				list.add(v);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", "Dubbo服务RPC接口类【" + dto.getRpcName() +"】调用失败! Class Not Found Exception !");
			return result;
		}
		
		
		
		if(StringUtils.isBlank(dto.getFunctionName())) {
			result.put("status", "success");
			result.put("list", list);
			return result;
		}
		
		List<RpcFunctionView> list_ = new ArrayList<RpcFunctionView>();
		for(RpcFunctionView v : list) {
			if(StringUtils.contains(v.getFunctionName().toLowerCase() , dto.getFunctionName().toLowerCase())) {
				list_.add(v);
			}
		}
		result.put("status", "success");
		result.put("list", list_);
		return result;
	}



	/**
	 * @description:Dubbo应用服务部署节点列表 
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2019年1月7日 下午2:39:44 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxDubboNodeList(ApplicationDto dto) {
		JSONObject result = new JSONObject();
		
		List<ApplicationView> applicationList = new ArrayList<ApplicationView>();
		
		ConcurrentMap<String, Map<Long, URL>> providers = getServiceByCategory(Constants.PROVIDERS_CATEGORY);			// providers
        if(providers != null){
            for(Map.Entry<String, Map<Long, URL>> oneService:providers.entrySet()){
                Map<Long, URL> urls = oneService.getValue();
                for(Map.Entry<Long,URL> url : urls.entrySet()){
                    ApplicationView view = new ApplicationView();
                    view.setApplication(url.getValue().getParameter(Constants.APPLICATION_KEY));
                    view.setUsername(url.getValue().getParameter("owner"));
                    view.setType(view.PROVIDER);
                    if(!applicationList.contains(view)){
                        applicationList.add(view);
                    }
                    break;
                }
            }
        }
        
        ConcurrentMap<String, Map<Long, URL>> consumers = getServiceByCategory(Constants.CONSUMERS_CATEGORY);		// consumers
        if(consumers != null){
            for(Map.Entry<String, Map<Long, URL>> oneService:consumers.entrySet()){
                //某个服务的所有地址，一个服务可能会被多个应用消费
                Map<Long, URL> urls = oneService.getValue();
                for(Map.Entry<Long,URL> url : urls.entrySet()){
                    if(url.getValue().getParameter(Constants.INTERFACE_KEY).equals( MonitorService.class.getName() )){     // 检索dubbo MonitorService服务信息
                        continue;
                    }
                    
                    ApplicationView view = new ApplicationView();
                    view.setApplication(url.getValue().getParameter(Constants.APPLICATION_KEY));
                    view.setUsername(url.getValue().getParameter("owner"));
                    if(!applicationList.contains(view)){
                        view.setType(view.CONSUMER);   // 标记为消费者
                        applicationList.add(view);
                    }else{
                        view=applicationList.get(applicationList.indexOf(view));
                        if(view.getType()==view.PROVIDER){
                            view.setType(view.PROVIDER_AND_CONSUMER);  // 标记为提供者和消费者
                        }
                    }
                    
                }
            }
        }
		
        result.put("status", "success");
        if(StringUtils.isBlank(dto.getApplication()) && StringUtils.isBlank(dto.getUsername()) && dto.getType() == null) {
        	result.put("list", this.listNode(applicationList));
        	return result;
        }
		
        List<ApplicationView> list = new ArrayList<ApplicationView>();
        for(ApplicationView v : applicationList) {
        	if(StringUtils.isNotBlank(dto.getApplication()) && StringUtils.isNotBlank(dto.getUsername()) && dto.getType() != null) {
        		if( v.getApplication().equals(dto.getApplication()) && v.getUsername().equals(dto.getUsername()) && v.getType() == dto.getType() ) {
        			list.add(v);
        		}
        		continue;
        	}
        	if(StringUtils.isNotBlank(dto.getApplication()) && StringUtils.isNotBlank(dto.getUsername()) && dto.getType() == null) {
        		if( v.getApplication().equals(dto.getApplication()) && v.getUsername().equals(dto.getUsername()) ) {
        			list.add(v);
        		}
        		continue;
        	}
        	if(StringUtils.isNotBlank(dto.getApplication()) && StringUtils.isBlank(dto.getUsername()) && dto.getType() == null) {
        		if(v.getApplication().equals(dto.getApplication())) {
        			list.add(v);
        		}
        		continue;
        	}
        	if(StringUtils.isNotBlank(dto.getApplication()) && StringUtils.isBlank(dto.getUsername()) && dto.getType() != null) {
        		if( v.getApplication().equals(dto.getApplication()) && v.getType() == dto.getType() ) {
        			list.add(v);
        		}
        		continue;
        	}
        	if(StringUtils.isBlank(dto.getApplication()) && StringUtils.isNotBlank(dto.getUsername()) && dto.getType() != null) {
        		if( v.getUsername().equals(dto.getUsername()) && v.getType() == dto.getType() ) {
        			list.add(v);
        		}
        		continue;
        	}
        	if(StringUtils.isBlank(dto.getApplication()) && StringUtils.isNotBlank(dto.getUsername()) && dto.getType() == null) {
        		if(v.getUsername() != null && v.getUsername().equals(dto.getUsername()) ) {
        			list.add(v);
        		}
        		continue;
        	}
        	if(StringUtils.isBlank(dto.getApplication()) && StringUtils.isBlank(dto.getUsername()) && dto.getType() != null) {
        		if( v.getType() == dto.getType() ) {
        			list.add(v);
        		}
        		continue;
        	}
        }
        
        result.put("list", this.listNode(list));
		return result;
	}

	/**
	 * @description: 返回清洗后的dubbo节点列表 
	 *
	 * @param list
	 * @author Yangcl
	 * @date 2019年1月7日 下午3:24:32 
	 * @version 1.0.0.1
	 */
	private List<DubboNodeView> listNode(List<ApplicationView> list){
		List<DubboNodeView> nodes = new ArrayList<DubboNodeView>();
        for(ApplicationView v : list) {
        	ApplicationDto dto_ = new ApplicationDto();
        	dto_.setApplication(v.getApplication());
        	List<ProviderView> providerList = adminProviderService.listProviderByApplication(dto_.getApplication()); 
    		for(ProviderView provider : providerList){
    			DubboNodeView node = new DubboNodeView();
    			node.setApplication(v.getApplication());
    			node.setUsername(v.getUsername());
    			node.setType(v.getType());
    			node.setFlag(v.getFlag());
                node.setDubboAddr(provider.getAddress());
                if(nodes.contains(node)) {
                	continue;
                }
                nodes.add(node);
            }
        }
        return nodes;
	}
	
	
	
}


































