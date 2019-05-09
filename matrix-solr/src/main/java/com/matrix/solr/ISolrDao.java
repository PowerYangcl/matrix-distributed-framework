package com.matrix.solr;


import com.github.pagehelper.PageInfo;
import java.util.List;

/**
 *@description: solr 操作类接口
 *
 *@author Sjh
 *@date 2019/3/29 12:40
 *@version 1.0.1
 */

public interface ISolrDao<T> {

    /**
     * 添加索引
     * 
     * @param <T>
     */
    public <T> boolean addSolr(T t);

    /**
     * 
     * @方法描述：根据条件查询索引-分页
     * @作者：guoqiang@300.cn
     * @日期：2015-12-31 下午4:49:11
     * @param query 查询条件字符串
     * @param page 分页对象
     * @param clazz
     * @param fields 需要输出的solr字段
     * @return
     * List<T>
     */
    public <T> List<T> querySolr(String query, PageInfo<T> page, Class<T> clazz, String... fields);

    /**
     * 
     * @方法描述：根据查询语句，返回查询总数
     * @作者：guoqiang@300.cn
     * @日期：2018年11月26日 下午1:56:40
     * @param query
     * @return
     * @返回值:List<T>
     */
    public int querySolr(String query);

    /**
     * 删除单个索引
     * 
     */
    public boolean delteSolrById(String id);

    /**
     * 删除多个索引
     * 
     */
    public boolean delteSolrByIds(List<String> ids);

    /**
     * 查询唯一
     * @param <T>
     * @param <T>
     */
    public <T> T findById(String id, Class<T> clazz);

    /**
     * 更新索引
     * 
     */
    public <T> boolean updateSolr(T t);

    /**
     * 清空所有索引
     * 
     */
    public boolean delteSolrAll();

}
