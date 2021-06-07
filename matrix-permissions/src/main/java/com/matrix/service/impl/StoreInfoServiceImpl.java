package com.matrix.service.impl;

import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseServiceImpl;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IStoreInfoMapper;
import com.matrix.pojo.dto.StoreInfoDto;
import com.matrix.pojo.entity.StoreInfo;
import com.matrix.pojo.request.AddStoreInfoRequest;
import com.matrix.pojo.request.DeleteStoreInfoRequest;
import com.matrix.pojo.request.FindStoreInfoRequest;
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
    public Result<PageInfo<StoreInfo>> ajaxStoreInfoList(FindStoreInfoRequest param, HttpServletRequest request) {
    	StoreInfoDto dto = param.buildAjaxStoreInfoList();
        return super.queryPageByDto(dto, request);
    }

    /**
     * @description: 门店列表信息| TODO StoreInfoInit.java缺失，后续需要补充，2021-06-07
     *
     * @author WangJu
     * @date 2018-11-20 17:35
     * @version 1.0.0.1
     */
    public Result<List<StoreInfo>> storeInfoList(FindStoreInfoRequest param){
        List<StoreInfo> list = super.findListByDto(param.buildStoreInfoList());
        if (CollectionUtils.isEmpty(list)) {
        	return Result.ERROR(this.getInfo(100020108), ResultCode.RESULT_NULL);
        }
        // 加载一下门店缓存，mip-member-service服务需要
        for (StoreInfo store: list){
        	launch.loadDictCache(DCacheEnum.StoreInfo , "StoreInfoInit").get(store.getCid()+"-"+store.getId());
        }
        return Result.SUCCESS(list);
    }

    /**
     * @description: 查找一个门店信息
     *
     * @author WangJu
     * @date 2018-11-20 17:35
     * @version 1.0.0.1
     */
    public Result<StoreInfo> findStoreInfo(FindStoreInfoRequest param){
        StoreInfo storeInfo = super.findEntityByDto(param.buildFindStoreInfo());
        if(storeInfo == null) {
        	return Result.ERROR(this.getInfo(100020107), ResultCode.NOT_FOUND);
        }
        return Result.SUCCESS(storeInfo);
    }

    /**
     * @description: 增加门店基本信息
     *
     * @author WangJu
     * @date 2018-11-21 23:21
     * @version 1.0.0.1
     */
    public Result<?> addStoreInfo(AddStoreInfoRequest param){
    	Result<?> validate = param.validateAddStoreInfo();
    	if(validate.getStatus().equals("error")) {
    		return validate;
    	}
    	StoreInfo entity = param.buildAddStoreInfo();
    	
        Integer count = super.insertSelective(entity);
        if (count != 1){
            return Result.ERROR(this.getInfo(101010060), ResultCode.ERROR_INSERT);	// 101010060=添加门店信息失败
        }
        return Result.SUCCESS(this.getInfo(100010102)); // 100010102=数据添加成功!
    }

    /**
     * @description: 修改门店基本信息|TODO 暂时不实现，需要根据业务逻辑来定
     *
     * @author Yangcl
     * @date 2021-6-7 16:31:37
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Result<?> editStoreInfo(StoreInfo e) {
        
        return Result.SUCCESS();
    }

    /**
     * @description: 删除门店基本信息
     *
     * @author Yangcl
     * @date 2021-6-7 16:44:23
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Result<?> deleteStoreInfo(DeleteStoreInfoRequest param){
    	Result<?> validate = param.validateDeleteStoreInfo();
    	if(validate.getStatus().equals("error")) {
    		return validate;
    	}
    	StoreInfo entity = param.buildDeleteStoreInfo();
        Integer count = super.updateSelective(entity);
        if (count != 1){	// 100010106=数据删除成功!
        	return Result.ERROR(this.getInfo(100010106), ResultCode.ERROR_DELETE);
        }
        
        launch.loadDictCache(DCacheEnum.StoreInfo , null).del(entity.getCid()+"-"+entity.getId());
        return Result.SUCCESS(this.getInfo(100010107));  // 100010107=数据删除失败，服务器异常!
    }
}




























