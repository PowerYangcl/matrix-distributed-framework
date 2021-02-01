package com.matrix.service;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.StoreInfoDto;
import com.matrix.pojo.entity.StoreInfo;
import com.matrix.pojo.view.StoreInfoView;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:  门店基本信息服务接口
 *
 * @author WangJu
 * @date  2018-09-20 15:51
 * @version 1.0.0.1
 */
public interface IStoreInfoService extends IBaseService<Long,StoreInfo,StoreInfoDto,StoreInfoView> {

    /**
     * @description:  门店列表分页数据
     *
     * @author WangJu
     * @date  2018-11-21 20:56
     * @version 1.0.0.1
     */
    JSONObject ajaxStoreInfoList(StoreInfoDto dto,HttpServletRequest request);

    /**
     * @description:  门店列表信息
     *
     * @author WangJu
     * @date  2018-11-20 17:35
     * @version 1.0.0.1
     */
    JSONObject storeInfoList(StoreInfoDto dto);

    /**
     * @description:  门店列表信息
     *
     * @author WangJu
     * @date  2018-11-20 17:35
     * @version 1.0.0.1
     */
    JSONObject findStoreInfo(StoreInfo entity);

    /**
     * @description:  增加门店基本信息
     *
     * @author WangJu
     * @date  2018-11-21 23:21
     * @version 1.0.0.1
     */
    JSONObject addStoreInfo(StoreInfo entity) ;

    /**
     * @description:  修改门店基本信息
     *
     * @author WangJu
     * @date  2018-11-21 23:21
     * @version 1.0.0.1
     */
    JSONObject editStoreInfo(StoreInfo entity) ;

   /**
    * @description:  删除门店基本信息
    *
    * @author WangJu
    * @date  2018-11-21 23:21
    * @version 1.0.0.1
    */
    JSONObject deleteStoreInfo(StoreInfo entity) ;

}
