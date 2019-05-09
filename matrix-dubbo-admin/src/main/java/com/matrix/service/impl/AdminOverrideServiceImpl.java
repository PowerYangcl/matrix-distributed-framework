package com.matrix.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.cluster.Configurator;

import com.matrix.pojo.view.OverrideView;
import com.matrix.pojo.view.ProviderView;
import com.matrix.service.AbstractDubboAdminService;
import com.matrix.service.IAdminOverrideService;
import com.matrix.utils.Pair;
import com.matrix.utils.SyncUtils;
import com.matrix.utils.Tool;


/**
 * @description: dubbo admin override service	| dubbo项目中的RPC接口信息概览
 *
 * @author Yangcl
 * @date 2018年8月30日 下午3:39:35 
 * @version 1.0.0.1
 */
@Service("adminOverrideService")
public class AdminOverrideServiceImpl extends AbstractDubboAdminService implements IAdminOverrideService {

	
	
	
	public ProviderView configProvider(ProviderView provider) {
		List<OverrideView> overrides = this.listByProvider(provider);
		
        URL url = URL.valueOf(provider.getUrl()).addParameterString(provider.getParameters());
        
        for (OverrideView override : overrides) {
            URL overrideUrl = override.toUrl();
            Configurator configurator = SyncUtils.toConfigurators(overrideUrl);
            if (configurator != null) {
                url = configurator.configure(url);
            }
            if(overrideUrl.getParameters().containsKey(Constants.DISABLED_KEY)){		// disabled
                url = url.addParameter(Constants.ENABLED_KEY , !Boolean.parseBoolean( overrideUrl.getParameter(Constants.DISABLED_KEY) ) );  // enabled
            }
        }
        
		return SyncUtils.url2Provider(new Pair<Long, URL>(provider.getId(), url));
	}

	
	/**
	 * @description: 获取dubbo提供者的 RPC接口信息概览
	 *
	 * @param provider
	 * @author Yangcl
	 * @date 2018年8月30日 下午4:00:25 
	 * @version 1.0.0.1
	 */
	public List<OverrideView> listByProvider(ProviderView provider) {
        List<OverrideView> list = new ArrayList<OverrideView>();
        ConcurrentMap<String, Map<Long, URL>> serviceUrls = getServiceByCategory(Constants.CONFIGURATORS_CATEGORY);  // configurators
        if (serviceUrls == null || serviceUrls.size() <= 0) {
            return list;
        }
        
        Collection<Map<Long, URL>> urlMaps = serviceUrls.values();
        URL providerUrl = SyncUtils.provider2URL(provider);
        for (Map<Long, URL> urlMap : urlMaps) {
            for (Map.Entry<Long, URL> urlEntry : urlMap.entrySet()) {
                URL url = urlEntry.getValue();
                if (Constants.ANYHOST_VALUE.equals(url.getHost()) || providerUrl.getHost().equals(url.getHost())) {				// ANYHOST_VALUE = 0.0.0.0
                    String configApplication = url.getParameter(Constants.APPLICATION_KEY, url.getUsername());    // application
                    String currentApplication = StringUtils.isEmpty(provider.getApplication()) ? provider.getUsername() : provider.getApplication();
                    if (configApplication == null || Constants.ANY_VALUE.equals(configApplication) || configApplication.equals(currentApplication)) {			// ANY_VALUE = *
                        if ( url.getPort() == 0   ||   URL.valueOf(provider.getUrl()).getPort() == url.getPort() ) {
                            if (url.getPath().equals(Tool.getInterface(provider.getServiceKey())) 
                            		&& StringUtils.isEquals(url.getParameter(Constants.GROUP_KEY), providerUrl.getParameter(Constants.GROUP_KEY))		// group
                            		&& StringUtils.isEquals(url.getParameter(Constants.VERSION_KEY), providerUrl.getParameter(Constants.VERSION_KEY))  ) {			// version
                            	
                                list.add(SyncUtils.url2Override(new Pair<Long, URL>(urlEntry.getKey(), url)));
                            }
                        }
                    }
                }
            }
        }
        return list;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
}























































