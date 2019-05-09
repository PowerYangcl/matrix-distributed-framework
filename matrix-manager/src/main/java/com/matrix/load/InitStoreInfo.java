package com.matrix.load;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IStoreInfoMapper;
import com.matrix.pojo.dto.StoreInfoDto;
import com.matrix.pojo.entity.StoreInfo;

/**
 * @description:  门店缓存
 *
 * @author WangJu
 * @date  2018-11-26 21:32
 * @version 1.0.0.1
 */
public class InitStoreInfo extends BaseClass implements ILoadCache<String> {

    private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();

    @Inject
    private IStoreInfoMapper storeInfoMapper;

    /**
     * @description:  加载门店缓存
     *
     * @author WangJu
     * @date  2018-11-26 21:33
     * @version 1.0.0.1
     */
    @Override
    public String load(String key, String field) {
        StoreInfoDto dto=new StoreInfoDto();
        dto.setCid(Long.parseLong( key.split("-")[0]));
        dto.setId(Long.parseLong( key.split("-")[1]));
        StoreInfo storeInfo = storeInfoMapper.findEntityByDto(dto);
        String value="";
        if (storeInfo!=null){
            value = JSONObject.toJSONString(storeInfo);
            launch.loadDictCache(DCacheEnum.StoreInfo , null).set(key , value , 30*24*60*60);
        }
        return value;
    }
}
