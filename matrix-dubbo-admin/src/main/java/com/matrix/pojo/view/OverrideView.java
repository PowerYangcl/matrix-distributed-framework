package com.matrix.pojo.view;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;

import java.io.Serializable;
import java.util.Map;


public class OverrideView extends BaseDubboModel implements Serializable{

	private static final long serialVersionUID = 114828505391757846L;

    private String service;
    
    private String params;
    
    private String application;
    
    private String address;
    
    private String username;
    
    private boolean enabled;
    
    
    
    public OverrideView(){
    }

    public OverrideView(long id){
    	 super(id);
    }
    
	public String getService() {
        return service;
    }

    
    public void setService(String service) {
        this.service = service;
    }

    
    public String getParams() {
        return params;
    }

    
    public void setParams(String params) {
        if(StringUtils.isEmpty(this.params)){
            this.params = params;
        }else{
            this.params +="&"+params;
        }
    }

    
    public String getApplication() {
        return application;
    }

    
    public void setApplication(String application) {
        this.application = application;
    }

    
    public String getAddress() {
        return address;
    }

    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
    public boolean isEnabled() {
        return enabled;
    }

    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String toString() {
        return "Override [service=" + service + ", params=" + params + ", application="
                + application + ", address=" + address + ", username=" + username + ", enabled=" + enabled + "]";
    }
    
    public boolean isDefault() {
    	return (getAddress() == null || getAddress().length() == 0 || Constants.ANY_VALUE.equals(getAddress()) || Constants.ANYHOST_VALUE.equals(getAddress()))
				&& (getApplication() == null || getApplication().length() == 0 || Constants.ANY_VALUE.equals(getApplication()));
    }
    
    public boolean isMatch(String service, String address, String application) {
    	return isEnabled() && getParams() != null && getParams().length() > 0
    			&& service.equals(getService())
    			&& (address == null || getAddress() == null || getAddress().length() == 0 || getAddress().equals(Constants.ANY_VALUE) || getAddress().equals(Constants.ANYHOST_VALUE) || getAddress().equals(address))
    			&& (application == null || getApplication() == null || getApplication().length() == 0 || getApplication().equals(Constants.ANY_VALUE) || getApplication().equals(application));
    }
    
    public boolean isUniqueMatch(ProviderView provider) {
    	return isEnabled() && getParams() != null && getParams().length() > 0
    			&& provider.getServiceKey().equals(getService())
    			&& provider.getAddress().equals(getAddress());
    }
    
    public boolean isMatch(ProviderView provider) {
    	return isEnabled() && getParams() != null && getParams().length() > 0
    			&& provider.getServiceKey().equals(getService())
    			&& (getAddress() == null || getAddress().length() == 0 || getAddress().equals(Constants.ANY_VALUE) || getAddress().equals(Constants.ANYHOST_VALUE) || getAddress().equals(provider.getAddress()))
    			&& (getApplication() == null || getApplication().length() == 0 || getApplication().equals(Constants.ANY_VALUE) || getApplication().equals(provider.getApplication()));
    }

    public boolean isUniqueMatch(ConsumerView consumer) {
    	return isEnabled() && getParams() != null && getParams().length() > 0
    			&& consumer.getServiceKey().equals(getService())
    			&& consumer.getAddress().equals(getAddress());
    }
    
    public boolean isMatch(ConsumerView consumer) {
    	return isEnabled() && getParams() != null && getParams().length() > 0
    			&& consumer.getServiceKey().equals(getService())
    			&& (getAddress() == null || getAddress().length() == 0 || getAddress().equals(Constants.ANY_VALUE) || getAddress().equals(Constants.ANYHOST_VALUE) || getAddress().equals(consumer.getAddress()))
    			&& (getApplication() == null || getApplication().length() == 0 || getApplication().equals(Constants.ANY_VALUE) || getApplication().equals(consumer.getApplication()));
    }
    
    public Map<String, String> toParametersMap() {
    	Map<String, String> map = StringUtils.parseQueryString(getParams());
    	map.remove(Constants.INTERFACE_KEY);
    	map.remove(Constants.GROUP_KEY);
    	map.remove(Constants.VERSION_KEY);
    	map.remove(Constants.APPLICATION_KEY);
    	map.remove(Constants.CATEGORY_KEY);
    	map.remove(Constants.DYNAMIC_KEY);
    	map.remove(Constants.ENABLED_KEY);
    	return map;
    }

    public URL toUrl() {
        String group = null;
        String version = null;
        String path = service;
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
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.OVERRIDE_PROTOCOL);
        sb.append("://");
        if(! StringUtils.isBlank(address) && ! Constants.ANY_VALUE.equals(address)) {
            sb.append(address);
        } else {
            sb.append(Constants.ANYHOST_VALUE);
        }
        sb.append("/");
        sb.append(path);
        sb.append("?");
        Map<String, String> param = StringUtils.parseQueryString(params);
        param.put(Constants.CATEGORY_KEY, Constants.CONFIGURATORS_CATEGORY);
        param.put(Constants.ENABLED_KEY, String.valueOf(isEnabled()));
        param.put(Constants.DYNAMIC_KEY, "false");
        if(! StringUtils.isBlank(application) && ! Constants.ANY_VALUE.equals(application)) {
            param.put(Constants.APPLICATION_KEY, application);
        }
        if (group != null) {
            param.put(Constants.GROUP_KEY, group);
        }
        if (version != null) {
            param.put(Constants.VERSION_KEY, version);
        }
        sb.append(StringUtils.toQueryString(param));
        return URL.valueOf(sb.toString());
    }
}
