package com.matrix.pojo.view;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;

import com.matrix.utils.Tool;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProviderView extends BaseDubboModel implements Serializable{

    private static final long serialVersionUID = 5981342400350878171L;

    private String serviceKey;/* 提供者所提供的服务名称 */
    private String url; /* 提供者提供服务的地址 */
    private String parameters; /* 提供者提供服务的参数 */
    private String address; /* 提供者地址 */
    private String registry;/* 提供者连接的注册中心地址 */
    private boolean dynamic;          /* 是否为动态注册服务 */
    private boolean enabled;          /* 是否启用 */
    private int weight;          /* 权重 */
    private String application; /* 应用名 */
    private String username;      /* 提供者用户名 */
    private Date expired;   /*过期时间*/
    private long alived;    /*存活时间，单位秒*/
    private String group;
    private String version;
    private OverrideView override;
    private List<OverrideView> overrides;


    public ProviderView() {
    }

    public ProviderView(Long id) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public void setAlived(long aliveSeconds) {
        this.alived = aliveSeconds;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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

    public URL toUrl() {
        Map<String, String> serviceName2Map = Tool.serviceName2Map(getServiceKey());

        String u = this.getUrl();
        URL url = URL.valueOf(u + "?" + getParameters());

        url = url.addParameters(serviceName2Map);

        boolean dynamic = isDynamic();
        if (!dynamic) {
            url = url.addParameter(Constants.DYNAMIC_KEY, false);
        }
        boolean enabled = isEnabled();
        if (enabled != url.getParameter("enabled", true)) {
            if (enabled) {
                url = url.removeParameter("enabled");
            } else {
                url = url.addParameter("enabled", false);
            }
        }

        return url;
    }
}
