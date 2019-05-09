package com.matrix.mongodb;


import com.github.pagehelper.PageInfo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;

/**
 *@description:  Mongo DB　 操作 接口
 *
 *@author Sjh
 *@date 2019/3/28 15:07
 *@version 1.0.1
 */

public interface IMongoDao<E>{

    /**
     * 保存
     */
    public void save(E E);

    public void save(E E, String collectionName);

    public void save(List<E> enties);

    /**
     *@description:TODO
     *
     * 条件查询 单字段排序查询
     *
     * @param args 查询条件
     * @param orderField 排序字段
     * @param entityType
     *@author Sjh
     *@date 2019/3/21 10:46
     *@return
     *@version 1.0.1
     */

    public List<E> findObjectByArgs(Map<String, Object> args, String orderField, Class<E> entityType);

    public List<E> findObjectByArgs(Map<String, Object> args, String orderField, Class<E> entityType, String collectionName);

    public List<E> findObjectByArgs(Map<String, Object> args, Class<E> entityType);

    public List<E> findObjectByArgs(Map<String, Object> args, Class<E> entityType, String collectionName);

    public E findOneObjectByArgs(Map<String, Object> args, Class<E> entityType);

	public E findByKey(String _id, Class<E> entityType);
    
    public E findObjectByOrderId(String orderId, Class<E> entityType);

    public List<E> findObjectByArgs(Map<String, Object> args, Map<String, Object> orders, Map<String, Map<String, Object[]>> contains, Class<E> entityType);

    public List<E> findObjectByArgs(Map<String, Object> args, Map<String, Object> orders, Map<String, Map<String, Object[]>> contains, Class<E> entityType, String collectionName);

    /**
     * 查询所有
     */
    public List<E> findAll(Class<E> entityType);

    public List<E> findAll(Class<E> entityType, String collectionName);

    /**
     * 模糊查询
     */
    public List<E> findAll(String regex, String orderField, Class<E> entityType, String fieldName);
    
    public List<E> findAll(String regex, String orderField, Class<E> entityType, String fieldName, String collectionName);

    /**
     * 批量删除
     */
    public void delete(Class<E> entityType);

    public void delete(Class<E> entityType, String collectionName);
    
    public void delete(Query query, Class<E> entityClass);


    /**
     * 更新
     */
    public void updateObjectByMongo(E E);

    public void updateObjectByMongo(E E, String collectionName);

	public void updatePropertityByKey(String key, Map<String, Object> args, Class<E> entityClass);


    /**
     * 批量更新
     */
    public void updateObjectsByMongo(Map<String, Object> args, String fieldName, int custType, Class<E> entityType);

    public void updateObjectsByMongo(Map<String, Object> args, String fieldName, int custType, String collectionName);


    public List<E> findObjectByQuery(Query query, Class<E> entityType);

    /**
     * 通过条件查询,查询分页结果
     */
    public PageInfo<E> getPage(int currentPage, int pageSize, Query query, Class<E> entityClass);

    public MapReduceResults<E> mapReduce(Query query, String colectionName, String mapFunction, String reduceFunction, Class<E> entityClass);

    public long count(Query query, Class<E> entityClass);

    public MongoTemplate getMongoTemplate();


	public void saveOrUpdate(E E, Class<E> entityClass);
}
