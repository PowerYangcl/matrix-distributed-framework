package com.matrix.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;

import com.matrix.pojo.view.ProviderView;
import com.matrix.service.AbstractDubboAdminService;
import com.matrix.service.IAdminOverrideService;
import com.matrix.service.IAdminProviderService;
import com.matrix.utils.Pair;
import com.matrix.utils.SyncUtils;


/**
 * @description: dubbo admin provider service
 *
 * @author Yangcl
 * @date 2018年8月27日 上午11:44:17 
 * @version 1.0.0.1
 */
@Service("adminProviderService")
public class AdminProviderServiceImpl extends AbstractDubboAdminService implements IAdminProviderService {

	@Resource
	private IAdminOverrideService adminOverrideService;
	
	
	/**
	 * @description: 查询某个应用提供的所有服务信息
	 *
	 * @param dto.application 	服务名称
	 * @author Yangcl
	 * @date 2018年8月29日 下午5:15:52 
	 * @version 1.0.0.1
	 */
	public List<ProviderView> listProviderByApplication(String application) {
		
		return super.filterCategoryData(
				new ConvertURL2Entity<ProviderView>() {
		            public ProviderView convert(Pair<Long, URL> pair) {
		            	
		                return SyncUtils.url2Provider(pair);
		            }
				} ,
				Constants.PROVIDERS_CATEGORY  , 	// providers
				Constants.APPLICATION_KEY  , 			// application
				application
			);
	}
	
	
	public List<ProviderView> listProviderByConditions(String... conditions) {
        return filterCategoryData(new ConvertURL2Entity<ProviderView>() {
            public ProviderView convert(Pair<Long, URL> pair) {
            	ProviderView provider = SyncUtils.url2Provider(pair);
                if(provider.isDynamic()){
                    return adminOverrideService.configProvider(provider);
                }
                return provider;
            }
        },Constants.PROVIDERS_CATEGORY,conditions);
    }


    /**
     * @description: find provider by the type of long id
     *
     * @param id
     * @author Yangcl
     * @date 2018年9月3日 下午4:00:26 
     * @version 1.0.0.1
     */
	public ProviderView getProviderById(Long id) {
		URL url = super.getOneById(Constants.PROVIDERS_CATEGORY , id);		// providers
        if(url != null){
        	ProviderView provider = SyncUtils.url2Provider(new Pair<Long, URL>(id, url));
            if(provider.isDynamic()){
                return adminOverrideService.configProvider(provider);
            }else{
                return provider;
            }
        }
        
        return null;
	}

}






































