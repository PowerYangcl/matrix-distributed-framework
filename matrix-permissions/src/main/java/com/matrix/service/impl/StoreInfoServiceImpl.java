package com.matrix.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseServiceImpl;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IStoreInfoMapper;
import com.matrix.pojo.dto.StoreInfoDto;
import com.matrix.pojo.entity.StoreInfo;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.pojo.view.StoreInfoView;
import com.matrix.service.IStoreInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:  门店基本信息服务
 *
 * @author WangJu
 * @date  2018-09-20 15:51
 * @version 1.0.0.1
 */
@Service("storeInfoService")
public class StoreInfoServiceImpl extends BaseServiceImpl<Long,StoreInfo,StoreInfoDto,StoreInfoView> implements IStoreInfoService {

    private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();

    @Resource
    private IStoreInfoMapper storeInfoMapper;

    /**
     * @description: 门店列表分页数据
     *
     * @author WangJu
     * @date 2018-11-21 20:56
     * @version 1.0.0.1
     */
    @Override
    public JSONObject ajaxStoreInfoList(StoreInfoDto dto,HttpServletRequest request) {
        McUserInfoView userCache = dto.getUserCache();
        dto.setCid(userCache.getCid());
        return super.queryPageByDto(dto, request);
    }

    /**
     * @description: 门店列表信息
     *
     * @author WangJu
     * @date 2018-11-20 17:35
     * @version 1.0.0.1
     */
    @Override
    public JSONObject storeInfoList(StoreInfoDto dto) {
        JSONObject result = new JSONObject();
        McUserInfoView userCache = dto.getUserCache();
        dto.setCid(userCache.getCid());
        List<StoreInfo> list = super.findListByDto(dto);
        //加载一下门店缓存,mip-member-service服务需要
        if (CollectionUtils.isNotEmpty(list)){
            for (StoreInfo store: list){
                launch.loadDictCache(DCacheEnum.StoreInfo , "InitStoreInfo").get(store.getCid()+"-"+store.getId());
            }
        }

        if (list==null || list.isEmpty()){
            result.put("status", "error");
            result.put("data", "");
            return result;
        }
        result.put("status", "success");
        result.put("data", list);
        return result;
    }

    /**
     * @description: 门店列表信息
     *
     * @author WangJu
     * @date 2018-11-20 17:35
     * @version 1.0.0.1
     */
    @Override
    public JSONObject findStoreInfo(StoreInfo entity) {
        JSONObject result = new JSONObject();

        StoreInfo storeInfo = super.find(entity.getId());
        result.put("status", "success");
        if (storeInfo !=null){
            result.put("data", storeInfo);
            return result;
        }
        result.put("data", "");
        return result;
    }

    /**
     * @description: 增加门店基本信息
     *
     * @author WangJu
     * @date 2018-11-21 23:21
     * @version 1.0.0.1
     */
    @Override
    public JSONObject addStoreInfo(StoreInfo entity) {
        JSONObject result = new JSONObject();
        Integer count = super.insertSelective(entity);
        if (count==1){
            result.put("status", "success");
            result.put("msg", this.getInfo(400010022));// 添加门店成功
            return result;
        }
        result.put("status", "error");
        result.put("msg", this.getInfo(400010103));	// 保存门店失败
        return result;
    }

    /**
     * @description: 修改门店基本信息
     *
     * @author WangJu
     * @date 2018-11-21 23:21
     * @version 1.0.0.1
     */
    @Override
    public JSONObject editStoreInfo(StoreInfo e) {
        JSONObject result = new JSONObject();
        
        Long orgId = e.getMcOrganizationId();
        StoreInfo find = storeInfoMapper.findByOrgId(orgId);
        if(find == null && e.getType() == 0) {	// 不存在于门店表中，且组织机构为非门店，
        	result.put("status", "success");
            result.put("msg", this.getInfo(400010024));// 400010024=更新成功
            return result;
        }
        
        if(find != null && e.getType() == 0) { // 存在于门店表中，且组织机构改成了非门店，
        	storeInfoMapper.deleteByOrgId(orgId);
        	launch.loadDictCache(DCacheEnum.StoreInfo , null).del(e.getCid() + "-" + find.getId());
        	result.put("status", "success");
            result.put("msg", this.getInfo(400010024));// 400010024=更新成功
            return result;
        }
        
        Integer count = 0;
        if(find == null && e.getType() != 0) { // 不存在于门店表中，且组织机构改成了 门店
        	count = storeInfoMapper.insertSelective(e);
        	if (count == 1){
                result.put("status", "success");
                result.put("msg", this.getInfo(400010022)); // 添加门店成功
                return result;
            }
        	result.put("status", "error");
            result.put("msg", this.getInfo(400010103));	// 保存门店失败
            return result;
        }
        
        if(find != null && e.getType() != 0) { // 存在于门店表中，且组织机构改成了 门店
        	e.setId(find.getId());
        	count = storeInfoMapper.updateSelective(e);
        	if (count == 1){
                //清除门店缓存
                launch.loadDictCache(DCacheEnum.StoreInfo , null).del(e.getCid() + "-" + find.getId());
                result.put("status", "success");
                result.put("msg", this.getInfo(400010024));// 400010024=更新成功
                return result;
            }
            result.put("status", "error");
            result.put("msg", this.getInfo(400010101));	// 保存门店失败
            return result;
        }
        
        result.put("status", "success");
        result.put("msg", this.getInfo(400010024));// 400010024=更新成功
        return result;
    }

    /**
     * @description: 删除门店基本信息
     *
     * @author WangJu
     * @date 2018-11-21 23:21
     * @version 1.0.0.1
     */
    @Override
    public JSONObject deleteStoreInfo(StoreInfo entity) {
        JSONObject result = new JSONObject();
        entity.setDeleteFlag(1);
        Integer count = super.updateSelective(entity);
        if (count==1){
            //清除门店缓存
            launch.loadDictCache(DCacheEnum.StoreInfo , null).del(entity.getCid()+"-"+entity.getId());
            result.put("status", "success");
            result.put("msg", this.getInfo(400010024));// 更新成功
            return result;
        }
        result.put("status", "error");
        result.put("msg", this.getInfo(400010102));	// 删除门店失败
        return result;
    }
}
