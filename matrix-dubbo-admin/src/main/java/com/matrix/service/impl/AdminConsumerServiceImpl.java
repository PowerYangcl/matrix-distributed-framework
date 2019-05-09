package com.matrix.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;

import com.matrix.pojo.dto.ApplicationDto;
import com.matrix.pojo.view.ConsumerView;
import com.matrix.service.AbstractDubboAdminService;
import com.matrix.service.IAdminConsumerService;
import com.matrix.utils.Pair;
import com.matrix.utils.SyncUtils;



/**
 * @description: dubbo admin consumer service
 *
 * @author Yangcl
 * @date 2018年8月27日 上午11:44:17 
 * @version 1.0.0.1
 */
@Service("adminConsumerService")
public class AdminConsumerServiceImpl extends AbstractDubboAdminService implements IAdminConsumerService {

	/**
	 * @description: 查询某个应用提供的所有消费信息
	 *
	 * @param dto.application 	服务名称
	 * @author Yangcl
	 * @date 2018年8月29日 下午5:15:52 
	 * @version 1.0.0.1
	 */
	public List<ConsumerView> listConsumerByApplication(ApplicationDto dto) {
		
		return super.filterCategoryData(
				new ConvertURL2Entity<ConsumerView>() {
		            public ConsumerView convert(Pair<Long, URL> pair) {
		            	
		                return SyncUtils.url2Consumer(pair);   
		            }
				} ,
				Constants.CONSUMERS_CATEGORY  , 	// consumers
				Constants.APPLICATION_KEY  , 			// application
				dto.getApplication()
			);
	}

	/**
	 * @description: as the function name...
	 *
	 * @param conditions 成对出现，你懂的
	 * 
	 * @author Yangcl
	 * @date 2018年9月3日 下午4:14:07 
	 * @version 1.0.0.1
	 */
	public List<ConsumerView> listConsumerByConditions(String... conditions) {
		return filterCategoryData(
				new ConvertURL2Entity<ConsumerView>() {
		            @Override
		            public ConsumerView convert(Pair<Long, URL> pair) {
		                return SyncUtils.url2Consumer(pair);
		            }
		        },
			   Constants.CONSUMERS_CATEGORY,				// consumers
			   conditions
		   );
	}

}





















































