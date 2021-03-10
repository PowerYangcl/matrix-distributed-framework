package com.matrix.dubbo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.matrix.base.BaseClass;

/**
 * @description: 泛化调用支持类
 * 		改进自：https://blog.csdn.net/doctor_who2004/article/details/81051226
 *			多线程使用会出现问题，请注意!
 *
 * @author Yangcl
 * @date 2019年1月3日 下午3:35:53 
 * @version 1.0.0.1
 */
public class DubboGeneric  extends BaseClass {
	
	private ReferenceConfig<GenericService> reference = null;
	private ReferenceConfigCache configCache = null;
	
	public DubboGeneric(){
        reference = new ReferenceConfig<GenericService>();	// 引用远程服务|重型实例，里面封装了所有与注册中心及服务提供方连接
        configCache = ReferenceConfigCache.getCache();	// com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用
	}
	
	/**
	 * @description: 获取Dubbo Rpc接口|精确调用，isUseZk设置为false即可
	 *
	 * @param isUseZk 是否通过zk进行泛化调用。true or false; true：directUrl为空，false：directUrl不得为空|
	 * @param directUrl	如：dubbo://127.0.0.1:20881
	 * @param interfaceName  如：com.matrix.rpc.IMatrixRouteRpcService
	 * @param interfaceVersion 如果不传入，则默认为1.0.0
	 * @author Yangcl
	 * @date 2019年1月3日 下午5:19:47 
	 * @version 1.0.0.1
	 */
	public GenericService getDubboRpc(boolean isUseZk , String directUrl, String interfaceName, String interfaceVersion) {
		if(!isUseZk && StringUtils.isAnyBlank(directUrl , interfaceName)) {
			return null;
		}
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName(this.getConfig("matrix-core.dubbo_application_name"));
        applicationConfig.setOwner(this.getConfig("matrix-core.dubbo_application_owner")); 
        applicationConfig.setOrganization("ShuShang-release");
        reference.setApplication(applicationConfig);
        
        if (isUseZk) {
        	List<RegistryConfig> registrieList = new ArrayList<>();
        	String[] zkhost = this.getConfig("matrix-core.zookeeper_host").split(",");
        	for(int i = 0 ; i < zkhost.length ; i ++) {
        		RegistryConfig registry = new RegistryConfig();
                registry.setAddress(zkhost[i]);
                registry.setProtocol("zookeeper");
                registry.setCheck(false);
                registrieList.add(registry);
        	}
            reference.setRegistries(registrieList); 
        } else {
            reference.setUrl(directUrl);  // dubbo直连
        }
		
        reference.setTimeout(60000);
        reference.setCheck(false);
        reference.setGeneric(true);	// 声明为泛化接口
        if (StringUtils.isNotBlank(interfaceVersion)) {
            reference.setVersion(interfaceVersion);
        }else {
        	reference.setVersion("1.0.0");
        }
        reference.setInterface(interfaceName);  // 弱类型接口名
        reference.setId(interfaceName);
        reference.setProtocol("dubbo");
        reference.setRetries(3);		// 尝试三次握手
        
		return configCache.get(reference);
	}
	
	public void destroy() {
		configCache.destroy(reference);
	}
}


























