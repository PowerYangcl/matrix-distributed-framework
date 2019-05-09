package com.matrix.pojo.view;


import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ConsumerView extends BaseDubboModel implements Serializable{
	
	
	private static final long serialVersionUID = -1140894843784583237L;

    private String serviceKey; /* 消费者所引用的服务名称 */

    private String parameters;
    
    private String result;    /*路由结果*/

    private String address; /* 消费者地址 */
    
	private String registry; /* 消费者连接的注册中心地址 */
    
    private String application; /* 应用名 */

    private String username;      /* 消费者用户名 */
    
    private String statistics;    /* 服务调用统计信息 */
    
    private Date collected;  /* 服务调用统计时间 */

    private String group;

    private String version;


	private OverrideView override;

	private List<OverrideView> overrides;

    private List<Route> routes;
    
    private List<ProviderView> providers;
    
	private Date expired;   /*过期时间*/
    
    private long alived;    /*存活时间，单位秒*/

    public ConsumerView() {
    }

    public ConsumerView(Long id) {
        super(id); 
    }


	public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatistics() {
        return statistics;
    }

    public void setStatistics(String statistics) {
        this.statistics = statistics;
    }

    public Date getCollected() {
        return collected;
    }

    public void setCollected(Date collected) {
        this.collected = collected;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public Date getExpired() {
        return expired;
    }

    
    public void setExpired(Date expired) {
        this.expired = expired;
    }

    
    public long getAlived() {
        return alived;
    }

    
    public void setAlived(long alived) {
        this.alived = alived;
    }

	public OverrideView getOverride() {
		return override;
	}

	public void setOverride(OverrideView override) {
		this.override = override;
	}

    public List<OverrideView> getOverrides() {
		return overrides;
	}

	public void setOverrides(List<OverrideView> overrides) {
		this.overrides = overrides;
	}

    public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	public List<ProviderView> getProviders() {
		return providers;
	}

	public void setProviders(List<ProviderView> providers) {
		this.providers = providers;
	}

    public String toString() {
        return "Consumer [serviceKey=" + serviceKey + ", parameters=" + parameters + ", result=" + result
                + ", address=" + address + ", registry=" + registry + ", application="
                + application + ", username=" + username + ", statistics=" + statistics
                + ", collected=" + collected + ", routes=" + routes + ", overrides=" + overrides
                + ", expired=" + expired + ", alived=" + alived + "]";
    }
    
    public URL toUrl() {
        String group = null;
        String version = null;
        String path = serviceKey;
        int i = path.indexOf("/");
        if (i > 0) {
            group = path.substring(0, i);
            path = path.substring(i + 1);
        }
        i = path.lastIndexOf(":");
        if (i > 0) {
            version = path.substring(i + 1);
            path = path.substring(0, i);
        }
        Map<String, String> param = StringUtils.parseQueryString(parameters);
        param.put(Constants.CATEGORY_KEY, Constants.CONSUMERS_CATEGORY);
        if (group != null) {
            param.put(Constants.GROUP_KEY, group);
        }
        if (version != null) {
            param.put(Constants.VERSION_KEY, version);
        }
        return URL.valueOf(Constants.CONSUMER_PROTOCOL + "://" + address + "/" + path 
                + "?" + StringUtils.toQueryString(param));
    }
}
