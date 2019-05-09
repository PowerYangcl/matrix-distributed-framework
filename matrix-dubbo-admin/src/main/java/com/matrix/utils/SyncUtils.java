package com.matrix.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.cluster.Configurator;
import com.alibaba.dubbo.rpc.cluster.ConfiguratorFactory;

import com.matrix.pojo.view.ConsumerView;
import com.matrix.pojo.view.OverrideView;
import com.matrix.pojo.view.ProviderView;
import com.matrix.pojo.view.Route;


public class SyncUtils {
	public static final String SERVICE_FILTER_KEY = ".service";

    public static final String ADDRESS_FILTER_KEY = ".address";
    
    public static final String ID_FILTER_KEY = ".id";



    private static final ConfiguratorFactory configuratorFactory = ExtensionLoader.getExtensionLoader(ConfiguratorFactory.class).getAdaptiveExtension();

    public static String generateServiceKey(URL url){
        String inf = url.getServiceInterface();
        if (inf == null) return null;
        StringBuilder buf = new StringBuilder();
        String group = url.getParameter(Constants.GROUP_KEY);
        if (group != null&& group.length() > 0) {
            buf.append(group).append("/");
        }
        buf.append(inf);
        String version = url.getParameter(Constants.VERSION_KEY);
        if (version != null&& version.length() > 0) {
            buf.append(":").append(version);
        }
        return buf.toString();
    }

    public static URL provider2URL(ProviderView provider){
        URL url = URL.valueOf(provider.getUrl());
        url=url.addParameterString(provider.getParameters());
        url = url.addParameter(Constants.WEIGHT_KEY,provider.getWeight());
        url=url.addParameter(Constants.ENABLED_KEY,provider.isEnabled());
        url=url.addParameter(Constants.DYNAMIC_KEY,provider.isDynamic());
        return url;
    }

    public static Configurator toConfigurators(URL url){
        if (Constants.EMPTY_PROTOCOL.equals(url.getProtocol())) {
            return null;
        }
        Map<String,String> override = new HashMap<String, String>(url.getParameters());
        // override 上的anyhost可能是自动添加的，不能影响改变url判断
        override.remove(Constants.ANYHOST_KEY);
        if (override.size() == 0){
            return null;
        }
        return configuratorFactory.getConfigurator(url);

    }

    public static ProviderView url2Provider(Pair<Long, URL> pair) {
    	if (pair == null) {
    		return null;
    	}
    	
        Long id = pair.getKey();
        URL url = pair.getValue();

        if (url == null)
            return null;

        ProviderView p = new ProviderView();
        p.setId(id);
        p.setServiceKey(generateServiceKey(url));
        p.setAddress(url.getAddress());
        p.setApplication(url.getParameter(Constants.APPLICATION_KEY));
        p.setUrl(url.toIdentityString());
        p.setParameters(url.toParameterString());

        p.setDynamic(url.getParameter("dynamic", true));
        p.setEnabled(url.getParameter(Constants.ENABLED_KEY, true));
        if(!url.getParameters().containsKey(Constants.WEIGHT_KEY)){
            p.setWeight(Constants.DEFAULT_WEIGHT);
        }else{
            p.setWeight("null".equals(url.getParameter(Constants.WEIGHT_KEY))?Constants.DEFAULT_WEIGHT:Integer.parseInt(url.getParameter(Constants.WEIGHT_KEY)));
        }
        p.setUsername(url.getParameter("owner"));
        p.setGroup(url.getParameter(Constants.GROUP_KEY));
        p.setVersion(url.getParameter(Constants.VERSION_KEY));
        return p;
    }
    
    public static List<ProviderView> url2ProviderList(Map<Long, URL> ps) {
        List<ProviderView> ret = new ArrayList<ProviderView>();
        for(Map.Entry<Long, URL> entry : ps.entrySet()) {
            ret.add(url2Provider(new Pair<Long, URL>(entry.getKey(), entry.getValue())));
        }
        return ret;
    }

    public static ConsumerView url2Consumer(Pair<Long, URL> pair) {
    	if (pair == null) {
    		return null;
    	}
    	
        Long id = pair.getKey();
        URL url = pair.getValue();

        if (null == url)
            return null;

        ConsumerView c = new ConsumerView();
        c.setId(id);
        c.setServiceKey(generateServiceKey(url));
        c.setAddress(url.getHost());
        c.setApplication(url.getParameter(Constants.APPLICATION_KEY));
        c.setParameters(url.toParameterString());
        c.setUsername(url.getParameter("owner"));
        c.setGroup(url.getParameter(Constants.GROUP_KEY));
        c.setVersion(url.getParameter(Constants.VERSION_KEY));
        return c;
    }
    
    public static List<ConsumerView> url2ConsumerList(Map<Long, URL> cs) {
        List<ConsumerView> list = new ArrayList<ConsumerView>();
        if(cs == null) return list;
        for(Map.Entry<Long, URL> entry : cs.entrySet()) {
            list.add(url2Consumer(new Pair<Long, URL>(entry.getKey(), entry.getValue())));
        }
        return list;
    }

    public static Route url2Route(Pair<Long, URL> pair) {
    	if (pair == null) {
    		return null;
    	}
    	
        Long id = pair.getKey();
        URL url = pair.getValue();

        if (null == url)
            return null;

        Route r = new Route();
        r.setId(id);
        r.setName(url.getParameter("name"));
        r.setService(generateServiceKey(url));
        r.setPriority(url.getParameter(Constants.PRIORITY_KEY, 0));
        r.setEnabled(url.getParameter(Constants.ENABLED_KEY, true));
        r.setForce(url.getParameter(Constants.FORCE_KEY, false));
        r.setType(url.getParameter(Constants.ROUTER_KEY));
        r.setRule(url.getParameterAndDecoded(Constants.RULE_KEY));
        r.setScriptType(url.getParameter(Constants.TYPE_KEY,Constants.DEFAULT_SCRIPT_TYPE_KEY));
        return r;
    }
    
    public static List<Route> url2RouteList(Map<Long, URL> cs) {
        List<Route> list = new ArrayList<Route>();
        if(cs == null) return list;
        for(Map.Entry<Long, URL> entry : cs.entrySet()) {
            list.add(url2Route(new Pair<Long, URL>(entry.getKey(), entry.getValue())));
        }
        return list;
    }

    public static OverrideView url2Override(Pair<Long, URL> pair) {
    	if (pair == null) {
    		return null;
    	}
    	
        Long id = pair.getKey();
        URL url = pair.getValue();

        if (null == url)
            return null;

        OverrideView o = new OverrideView();
        o.setId(id);

        Map<String, String> parameters = new HashMap<String, String>(url.getParameters());

        o.setService(generateServiceKey(url));
        parameters.remove(Constants.INTERFACE_KEY);
        parameters.remove(Constants.GROUP_KEY);
        parameters.remove(Constants.VERSION_KEY);
        parameters.remove(Constants.APPLICATION_KEY);
        parameters.remove(Constants.CATEGORY_KEY);
        parameters.remove(Constants.DYNAMIC_KEY);
        parameters.remove(Constants.ENABLED_KEY);

        o.setEnabled(url.getParameter(Constants.ENABLED_KEY, true));

        String host = url.getHost();
        boolean anyhost = url.getParameter(Constants.ANYHOST_VALUE, false);
        if(!anyhost || !"0.0.0.0".equals(host)) {
            o.setAddress(url.getAddress());
        }

        o.setApplication(url.getParameter(Constants.APPLICATION_KEY, url.getUsername()));
        parameters.remove(Constants.VERSION_KEY);

        o.setParams(StringUtils.toQueryString(parameters));

        return o;
    }
}
