package com.matrix.service;

import com.github.pagehelper.PageInfo;
import com.matrix.base.Result;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.StoreInfoDto;
import com.matrix.pojo.entity.StoreInfo;
import com.matrix.pojo.request.AddStoreInfoRequest;
import com.matrix.pojo.request.DeleteStoreInfoRequest;
import com.matrix.pojo.request.FindStoreInfoRequest;
import com.matrix.pojo.view.StoreInfoView;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
	public Result<PageInfo<StoreInfo>> ajaxStoreInfoList(FindStoreInfoRequest param, HttpServletRequest request);

    /**
     * @description:  门店列表信息
     *
     * @author WangJu
     * @date  2018-11-20 17:35
     * @version 1.0.0.1
     */
	public Result<List<StoreInfo>> storeInfoList(@Valid FindStoreInfoRequest param);

    /**
     * @description:  门店列表信息
     *
     * @author WangJu
     * @date  2018-11-20 17:35
     * @version 1.0.0.1
     */
	public Result<StoreInfo> findStoreInfo(FindStoreInfoRequest param);

    /**
     * @description:  增加门店基本信息
     *
     * @author WangJu
     * @date  2018-11-21 23:21
     * @version 1.0.0.1
     */
	public Result<?> addStoreInfo(AddStoreInfoRequest param);

    /**
     * @description: 修改门店基本信息|TODO 暂时不实现，需要根据业务逻辑来定
     *
     * @author Yangcl
     * @date 2021-6-7 16:31:37
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Result<?> editStoreInfo(StoreInfo entity) ;

    /**
     * @description: 删除门店基本信息
     *
     * @author Yangcl
     * @date 2021-6-7 16:44:23
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Result<?> deleteStoreInfo(DeleteStoreInfoRequest param);

}
