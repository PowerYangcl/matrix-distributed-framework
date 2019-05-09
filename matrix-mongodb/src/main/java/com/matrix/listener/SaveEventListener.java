package com.matrix.listener;


import com.matrix.annotation.MongoAutoIncKey;
import com.matrix.system.cache.PropVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;


/**
 *@description:mongdb 保存事件 监听类
 *   在保存对象时，通过反射方式为其生成ID
 *   https://www.jianshu.com/p/3ac71befdc52
 *@author Sjh
 *@date 2019/3/22 11:37
 *@version 1.0.1
 */
@Component
public class SaveEventListener extends AbstractMongoEventListener<Object> {

    private Logger logger = LoggerFactory.getLogger(SaveEventListener.class);
    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 在发起请求到mongo服务器的前置操作
     */
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {

        String isAutoIncKey = PropVisitor.getConfig("matrix-mongodb.is_auto_inc_key");

        /**
         * 生成自增主键
         * 默认不要 no
         * sjh
         */
        if (("yes").equals(isAutoIncKey)) {
            final Object source = event.getSource();
            if (source != null) {
                ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
                    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                        ReflectionUtils.makeAccessible(field);
                        // 如果字段添加了我们自定义的IncKey注解
                        if (field.isAnnotationPresent(MongoAutoIncKey.class)) {
                            // 通过反射设置自增ID
                            field.set(source, getNextId(source.getClass().getSimpleName()));
                            logger.info("=-=-=-=-=-=-=-=-=-=-=new meongdb pkId-=-=-=-=-=-=-=-=" + getNextId(source.getClass().getSimpleName()) + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                        }
                    }
                });
            }
        }
    }

    /**
     * 获取下一个自增ID
     * 这儿有根据表名进行区分
     * @param collName 这里代表数据库表名称
     */
    private Long getNextId(String collName) {
        Query query = new Query(Criteria.where("collName").is(collName));
        Update update = new Update();
        update.inc("seqId", 1);  //每次自增1
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        //操作SeqInfo表,对其seqId加一并且返回最终值
        SeqInfo seq = mongoTemplate.findAndModify(query, update, options, SeqInfo.class);
        return seq.getSeqId();
    }
}
