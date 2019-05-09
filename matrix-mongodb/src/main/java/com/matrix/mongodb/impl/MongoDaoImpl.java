package com.matrix.mongodb.impl;


import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseClass;
import com.matrix.mongodb.IMongoDao;
import com.matrix.util.AnnotationUtility;
import com.mongodb.BasicDBList;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 *@description: Mongo DB　数据库操作dao
 *
 *@author Sjh
 *@date 2019/3/22 13:53
 *@version 1.0.1
 */

public class MongoDaoImpl<E> extends BaseClass implements IMongoDao<E> {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 保存
     */
    public void save(E E) {
        this.mongoTemplate.insert(E);
    }

    public void save(E E, String collectionName) {
        this.mongoTemplate.insert(E, collectionName);
    }

    public void save(List<E> enties) {
        this.mongoTemplate.insertAll(enties);
    }

    public List<E> findObjectByArgs(Map<String, Object> args, String orderField, Class<E> entityType) {
        Query query = this.findQueryObjectByArgs(args, orderField);
        //logger.info(query);
        return this.mongoTemplate.find(query, entityType);
    }

    public List<E> findObjectByArgs(Map<String, Object> args, String orderField, Class<E> entityType, String collectionName) {
        Query query = this.findQueryObjectByArgs(args, orderField);
        //logger.info(query);
        return this.mongoTemplate.find(query, entityType, collectionName);
    }

    public List<E> findObjectByArgs(Map<String, Object> args, Class<E> entityType) {
        Query query = this.findQueryObjectByArgs(args);
        //logger.info(query);
        return this.mongoTemplate.find(query, entityType);
    }

    public List<E> findObjectByArgs(Map<String, Object> args, Class<E> entityType, String collectionName) {
        Query query = this.findQueryObjectByArgs(args);
        //logger.info(query);
        return this.mongoTemplate.find(query, entityType, collectionName);
    }

    public E findOneObjectByArgs(Map<String, Object> args, Class<E> entityType) {
        Query query = this.findQueryObjectByArgs(args);
        return this.mongoTemplate.findOne(query, entityType);
    }

    @Override
    public E findByKey(String _id, Class<E> entityType) {

        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("_id").is(new ObjectId(_id));
        query.addCriteria(criteria);

        return this.mongoTemplate.findOne(query, entityType);
    }

    public E findObjectByOrderId(String orderId, Class<E> entityType) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("orderId").is(orderId);
        query.addCriteria(criteria);
        return this.mongoTemplate.findOne(query, entityType);
    }

    /**
     * 条件查询 多字段排序查询
     *
     * @param args 查询条件
     * @param orders  排序集合
     * @param contains   是否包含 表示或的关系，只要有就符合条件 map的value值包含为in，不包含为nin
     * @param entityType
     * @return
     */
    public List<E> findObjectByArgs(Map<String, Object> args, Map<String, Object> orders, Map<String, Map<String, Object[]>> contains, Class<E> entityType) {
        Query query = this.findQueryObjectByArgs(args, orders, contains);
        //logger.info(query);
        return this.mongoTemplate.find(query, entityType);
    }

    public List<E> findObjectByArgs(Map<String, Object> args, Map<String, Object> orders, Map<String, Map<String, Object[]>> contains, Class<E> entityType, String collectionName) {
        Query query = this.findQueryObjectByArgs(args, orders, contains);
        //logger.info(query);
        return this.mongoTemplate.find(query, entityType, collectionName);
    }

    /**
     * 查询所有
     */
    public List<E> findAll(Class<E> entityType) {
        return this.mongoTemplate.find(new Query(), entityType);
    }

    public List<E> findAll(Class<E> entityType, String collectionName) {
        return this.mongoTemplate.find(new Query(), entityType, collectionName);
    }

    /**
     * 模糊查询
     */
    public List<E> findAll(String regex, String orderField, Class<E> entityType, String fieldName) {
        Pattern pattern = Pattern.compile("^.*" + regex + ".*$", Pattern.CASE_INSENSITIVE);
        Criteria criteria = new Criteria(fieldName).regex(pattern.toString());
        return this.mongoTemplate.find(new Query(criteria).with(new Sort(new Sort.Order(Sort.Direction.DESC, orderField))), entityType);
    }

    public List<E> findAll(String regex, String orderField, Class<E> entityType, String fieldName, String collectionName) {
        Pattern pattern = Pattern.compile("^.*" + regex + ".*$", Pattern.CASE_INSENSITIVE);
        Criteria criteria = new Criteria(fieldName).regex(pattern.toString());
        return this.mongoTemplate.find(new Query(criteria).with(new Sort(new Sort.Order(Sort.Direction.DESC, orderField))), entityType, collectionName);
    }

    /**
     * 批量删除
     */
    public void delete(Class<E> entityType) {
        List<E> entities = this.mongoTemplate.findAll(entityType);
        for (E E : entities) {
            this.mongoTemplate.remove(E);
        }
    }

    public void delete(Class<E> entityType, String collectionName) {
        List<E> entities = this.mongoTemplate.findAll(entityType, collectionName);
        for (E E : entities) {
            this.mongoTemplate.remove(E, collectionName);
        }
    }

    public void delete(Query query, Class<E> entityClass) {
        this.mongoTemplate.remove(query, entityClass);
    }

    /**
     * 更新
     */
    public void updateObjectByMongo(E E) {
        this.mongoTemplate.save(E);
    }

    public void updateObjectByMongo(E E, String collectionName) {
        this.mongoTemplate.save(E, collectionName);
    }

    @Override
    public void updatePropertityByKey(String key, Map<String, Object> args, Class<E> entityClass) {
        Update update = this.updateQueryObjectByArgs(args);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(key)));
        this.getMongoTemplate().updateMulti(query, update, entityClass);
    }

    /**
     * 批量更新
     */
    public void updateObjectsByMongo(Map<String, Object> args, String fieldName, int custType, Class<E> entityType) {
        Update update = this.updateQueryObjectByArgs(args).set(fieldName, custType);
        Query query = this.findQueryObjectByArgs(args);
        //logger.info(query);
        this.mongoTemplate.updateMulti(query, update, entityType);
    }

    public void updateObjectsByMongo(Map<String, Object> args, String fieldName, int custType, String collectionName) {
        Update update = this.updateQueryObjectByArgs(args).set(fieldName, custType);
        Query query = this.findQueryObjectByArgs(args);
        //logger.info(query);
        this.mongoTemplate.updateMulti(query, update, collectionName);
    }


    public List<E> findObjectByQuery(Query query, Class<E> entityType) {
        return this.mongoTemplate.find(query, entityType);
    }

    /**
     * *****************************私有方法****************************************
     * ***
     *
     * @throws ParseException
     */
    // 查询私有方法 单字段排序
    // 查询1：不支持排序
    @SuppressWarnings("null")
    private Query findQueryObjectByArgs(Map<String, Object> args) {
        Query query = null;
        Criteria criteria = null;
        if (null != args && args.size() > 0) {
            Set<String> set = args.keySet();
            int m = 0;
            for (String key : set) {
                if (args.get(key) instanceof Date) {
                    criteria = Criteria.where(key).lt(args.get(key));
                } else {
                    if (null != criteria) {
                        if (m == 0) {
                            criteria = Criteria.where(key).is(args.get(key));
                        } else {
                            criteria = criteria.and(key).is(args.get(key));
                        }
                    } else {
                        if (m == 0) {
                            criteria = Criteria.where(key).is(args.get(key));
                        } else {
                            criteria = criteria.and(key).is(args.get(key));
                        }
                    }
                }
                m++;
            }
        }
        if (null != criteria) {
            query = new Query(criteria);
        } else {
            query = new Query();
        }
        //logger.info(query);
        return query;
    }

    // 查询2：支持排序--降序 单字段排序
    @SuppressWarnings("null")
    private Query findQueryObjectByArgs(Map<String, Object> args, String fieldName) {
        Criteria criteria = null;
        Query query = null;
        if (null != args && args.size() > 0) {
            Set<String> set = args.keySet();
            int m = 0;
            for (String key : set) {
                if (args.get(key) instanceof Date) {
                    criteria = Criteria.where(key).lt(args.get(key));
                } else {
                    if (null != criteria) {
                        if (m == 0) {
                            criteria = Criteria.where(key).is(args.get(key));
                        } else {
                            criteria = criteria.and(key).is(args.get(key));
                        }
                    } else {
                        if (m == 0) {
                            criteria = Criteria.where(key).is(args.get(key));
                        } else {
                            criteria = criteria.and(key).is(args.get(key));
                        }
                    }
                }
                m++;
            }
        }
        if (null != criteria) {
            query = new Query(criteria);
        } else {
            query = new Query();
        }
        if (null != fieldName || !"".equals(fieldName)) {
            query.with(new Sort(new Sort.Order(Sort.Direction.DESC, fieldName)));
        }
        //logger.info(query);
        return query;
    }

    // 查询3：支持排序--升序 单字段排序
    @SuppressWarnings({"unused", "static-access", "null"})
    private Query findQueryObjectByArgAsc(Map<String, Object> args, String fieldName) {
        Criteria criteria = new Criteria();
        Query query = null;
        if (null != args && args.size() > 0) {
            Set<String> set = args.keySet();
            int m = 0;
            for (String key : set) {
                if (args.get(key) instanceof Date) {
                    criteria = Criteria.where(key).lt(args.get(key));
                } else {
                    if (null != criteria) {
                        if (m == 0) {
                            criteria = criteria.where(key).is(args.get(key));
                        } else {
                            criteria = criteria.and(key).is(args.get(key));
                        }
                    } else {
                        if (m == 0) {
                            criteria = Criteria.where(key).is(args.get(key));
                        } else {
                            criteria = criteria.and(key).is(args.get(key));
                        }
                    }
                }
                m++;
            }
        }
        if (null != criteria) {
            query = new Query(criteria);
        } else {
            query = new Query();
        }
        if (null != fieldName || !"".equals(fieldName)) {
            query.with(new Sort(new Sort.Order(Sort.Direction.ASC, fieldName)));
        }
        //logger.info(query);
        return query;
    }

    /**
     * 查询4：多字段排序
     *
     * @param args 查询参数
     * @param orders 排序字段集合
     * @param contains 是否包含 包含为in，不包含为nin,大小写无所谓
     * @return
     */
    @SuppressWarnings({"static-access", "null"})
    private Query findQueryObjectByArgs(Map<String, Object> args, Map<String, Object> orders, Map<String, Map<String, Object[]>> contains) {
        Query query = null;
        Criteria criteria = null;
        if (null != args && args.size() > 0) {
            Set<String> set = args.keySet();
            int m = 0;
            for (String key : set) {
                if (args.get(key) instanceof Date) {
                    criteria = Criteria.where(key).lt(args.get(key));
                } else {
                    if (null != criteria) {
                        if (m == 0) {
                            criteria = criteria.where(key).is(args.get(key));
                        } else {
                            criteria = criteria.and(key).is(args.get(key));
                        }
                    } else {
                        if (m == 0) {
                            criteria = Criteria.where(key).is(args.get(key));
                        } else {
                            criteria = criteria.and(key).is(args.get(key));
                        }
                    }
                }
                m++;
            }
        }
        if (null != contains && contains.size() > 0) {
            Set<String> keys = contains.keySet();// 只包含两种key，in或nin
            for (String key : keys) {
                if ("in".equalsIgnoreCase(key)) {
                    Map<String, Object[]> maps = contains.get(key);
                    Set<String> columnsKeys = maps.keySet();
                    for (String columnsKey : columnsKeys) {
                        if (criteria == null) {
                            criteria = Criteria.where(columnsKey).in((Object[]) maps.get(columnsKey));
                        } else {
                            criteria.and(columnsKey).in((Object[]) maps.get(columnsKey));
                        }

                    }
                }
                if ("nin".equalsIgnoreCase(key)) {
                    Map<String, Object[]> maps = contains.get(key);
                    Set<String> columnsKeys = maps.keySet();
                    for (String columnsKey : columnsKeys) {
                        if (criteria == null) {
                            criteria = Criteria.where(columnsKey).in((Object[]) maps.get(columnsKey));
                        } else {
                            criteria.and(columnsKey).in((Object[]) maps.get(columnsKey));
                        }
                    }
                }
            }
        }
        if (null != criteria) {
            query = new Query(criteria);
        } else {
            query = new Query();
        }
        if (null != orders && orders.size() > 0) {
            Set<String> keys = orders.keySet();
            for (String key : keys) {
                if ("desc".equalsIgnoreCase(orders.get(key).toString())) {
                    query.with(new Sort(new Sort.Order(Sort.Direction.DESC, key)));
                }
                if ("asc".equalsIgnoreCase(orders.get(key).toString())) {
                    query.with(new Sort(new Sort.Order(Sort.Direction.ASC, key)));
                }
            }
        }
        //logger.info(query);
        return query;
    }

    // 批量更新私有方法
    private Update updateQueryObjectByArgs(Map<String, Object> args) {
        Set<String> set = args.keySet();
        Update update = null;
        for (String key : set) {
            update = Update.update(key, args.get(key));
        }
        return update;
    }

    /**
     * 通过条件查询,查询分页结果
     */
    public PageInfo<E> getPage(int currentPage, int pageSize, Query query, Class<E> entityClass) {

        List<E> datas = this.findObjectByQuery(query, entityClass);

        PageInfo<E> page = new PageInfo<E>(datas);

        return page;
    }

    public MapReduceResults<E> mapReduce(Query query, String colectionName, String mapFunction, String reduceFunction, Class<E> entityClass) {
        MapReduceResults<E> rs = mongoTemplate.mapReduce(query, colectionName, mapFunction, reduceFunction, entityClass);
        return rs;
    }

    public long count(Query query, Class<E> entityClass) {
        return mongoTemplate.count(query, entityClass);
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public BasicDBList group(Criteria criteria, String collectionName, GroupBy groupBy, Class<E> entityClass) {
        GroupByResults<E> r = mongoTemplate.group(criteria, collectionName, groupBy, entityClass);
        BasicDBList list = (BasicDBList) r.getRawResults().get("retval");
        return list;
    }

    @SuppressWarnings("unused")
    @Override
    public void saveOrUpdate(E E, Class<E> entityClass) {

        List<Field> fields = AnnotationUtility.getAnnotatedFields(entityClass, org.springframework.data.mongodb.core.mapping.Field.class);

        Object id = null;
        Map<String, Object> args = new HashMap<String, Object>();
        try {
            for (Field field : fields) {

                org.springframework.data.mongodb.core.mapping.Field annotation = field.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class);

                if ("id".equals(annotation.value())) {
                    Method getId = ClassUtils.getMethod(entityClass, "getId");
                    Method setId = ClassUtils.getMethod(entityClass, "setId", field.getType());
                    if (getId != null) {
                        id = getId.invoke(E);
                        if (id == null) {
                            id = ObjectId.get();
                            continue;
                        }
                        if (field.getType() == String.class) {
                            id = new ObjectId((String) id);
                            continue;
                        }
                        if (field.getType() == ObjectId.class) {
                            id = new ObjectId((String) id);
                            continue;
                        }
                        id = new ObjectId(id.toString());
                    }
                    continue;
                }

                Method getMethod = ClassUtils.getMethod(entityClass, "get" + StringUtils.capitalize(field.getName()));
                Object value = getMethod.invoke(E);
                if (value != null) {
                    args.put(annotation.value(), value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //logger.info(args);
        Query query = Query.query(Criteria.where("_id").is(id));

        Update update = new Update();
        Set<String> set = args.keySet();
        for (String key : set) {
            update.set(key, args.get(key));
        }

        this.mongoTemplate.upsert(query, update, entityClass);
    }

}
