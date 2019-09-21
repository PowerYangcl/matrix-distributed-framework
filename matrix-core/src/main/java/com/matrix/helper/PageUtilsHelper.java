package com.matrix.helper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseClass;
import com.matrix.base.BaseDto;
import com.matrix.base.RpcResult;

import java.util.List;

/**
 *@description:分页信息封装
 *
 *@param
 *@author liwt
 *@date 2019/7/23 16:00
 *@return
 *@version 1.0.1
 */
public class PageUtilsHelper extends BaseClass {
    //分页数据
    private BaseDto dto;

    //排序方式
    private String orderBy;

    /**
     *@description:初始化分页信息
     *
     *@param
     *@author liwt
     *@date 2019/7/24 20:02
     *@return
     *@version 1.0.1
    */
    public PageUtilsHelper(BaseDto dto, String orderBy) {
        this.dto = dto;
        this.orderBy = orderBy;
        int pageNum = dto.getStartIndex();
        int pageSize = dto.getPageSize();
        PageHelper.startPage(pageNum, pageSize, "update_time " + orderBy);
    }

    /**
     *@description:分页信息
     *
     *@param
     *@author liwt
     *@date 2019/7/23 16:14
     *@return
     *@version 1.0.1
     * 调用实例
     * RpcResult<?> result = new PageUtilsHelper(dto, "desc").page(myFileService.pageList(dto));
     */
    public <T> RpcResult<?> page(List<T> t) {
        RpcResult<Object> result = RpcResult.SUCCESS();
        if (t != null && t.size() > 0) {
            result.setStatus("success");
        } else {
            result.setStatus("success");
            result.setMsg(this.getInfo(100090002L, new Object[0]));
        }

        PageInfo<T> pageInfo = new PageInfo<T>(t);
        result.setResult(pageInfo);
        return result;
    }
}
