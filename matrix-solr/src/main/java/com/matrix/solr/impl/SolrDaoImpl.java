package com.matrix.solr.impl;


import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseClass;
import com.matrix.solr.ISolrDao;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("solrDao")
public class SolrDaoImpl<T> extends BaseClass implements ISolrDao<T> {

    private Logger logger = Logger.getLogger(SolrDaoImpl.class);

    @Resource(name = "solrTemplate")
    private SolrTemplate solrTemplate;

    @Override
    public <T> boolean addSolr(T t) {
        boolean flag = false;
        try {
            // 获取连接服务
            SolrClient solrServer = solrTemplate.getSolrClient();
            // 实例化
            DocumentObjectBinder binder = new DocumentObjectBinder();
            // javabean对象转化为SolrInputDocument对象
            SolrInputDocument doc = binder.toSolrInputDocument(t);
            solrServer.add(doc);
            // 提交事务才能生效
            solrServer.commit();
            flag = true;
        } catch (SolrServerException e) {
            logger.error("SolrServerException:", e);
        } catch (IOException e) {
            logger.error("IOException:", e);
        }
        return flag;
    }

    @Override
    public boolean delteSolrById(String id) {
        boolean flag = false;
        try {
            // 获取连接服务
            SolrClient solrServer = solrTemplate.getSolrClient();
            solrServer.deleteById(id);
            // 优化索引
            // solrServer.optimize();
            // 提交事务才能生效
            solrServer.commit();
            flag = true;
        } catch (SolrServerException e) {
            logger.error("SolrServerException:", e);
        } catch (IOException e) {
            logger.error("IOException:", e);
        }
        return flag;
    }

    @Override
    public boolean delteSolrByIds(List<String> ids) {
        boolean flag = false;
        try {
            // 获取连接服务
            SolrClient solrServer = solrTemplate.getSolrClient();
            solrServer.deleteById(ids);
            // 优化索引
            // solrServer.optimize();
            // 提交事务才能生效
            solrServer.commit();
            flag = true;
        } catch (SolrServerException e) {
            logger.error("SolrServerException:", e);
        } catch (IOException e) {
            logger.error("IOException:", e);
        }
        return flag;
    }

    @Override
    public <T> List<T> querySolr(String query, PageInfo<T> page, Class<T> clazz, String... fields) {
        List<T> list = new ArrayList<T>();
        SolrClient solrServer = solrTemplate.getSolrClient();
        SolrQuery sQuery = new SolrQuery();
        /*
         * if (StringUtils.isEmpty(query)) { query = "*:*"; }
         */
        sQuery.setFields(fields);
        sQuery.setQuery(query);
        sQuery.setStart(page.getPageSize() * (page.getPageNum() - 1));
        sQuery.setRows(page.getPageSize());
        // 排序
        // sQuery.addSort("createDate", SolrQuery.ORDER.desc);
        QueryResponse response = null;
        try {
            response = solrServer.query(sQuery);
            // 获取查询文档
            SolrDocumentList slist = response.getResults();
            // 初始化DocumentObjectBinder对象
            DocumentObjectBinder binder = new DocumentObjectBinder();
            // SolrDocumentList对象转化为 List<T>对象
            list = binder.getBeans(clazz, slist);
            // 总记录数
            Integer counts = (int) slist.getNumFound();
            page.setTotal(counts);

        } catch (SolrServerException e) {
            logger.error("SolrServerException:", e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public int querySolr(String query) {
        SolrClient solrServer = solrTemplate.getSolrClient();
        SolrQuery sQuery = new SolrQuery();
        sQuery.setQuery(query);
        QueryResponse response = null;
        try {
            response = solrServer.query(sQuery);
            // 获取查询文档
            SolrDocumentList slist = response.getResults();
            // 总记录数
            Integer counts = (int) slist.getNumFound();
            logger.info("Solr query size:" + counts + " query:" + sQuery);
            return counts;
        } catch (SolrServerException e) {
            logger.error("SolrServerException:", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public <T> T findById(String id, Class<T> clazz) {

        try {

            SolrClient solrServer = solrTemplate.getSolrClient();

            SolrDocument solrDocument = solrServer.getById(id);

            DocumentObjectBinder binder = new DocumentObjectBinder();

            return binder.getBean(clazz, solrDocument);

        } catch (IOException e) {
            logger.error("IOException:", e);
        } catch (SolrServerException e) {
            logger.error("SolrServerException:", e);
        }
        return null;
    }

    @Override
    public <T> boolean updateSolr(T t) {
        return this.addSolr(t);
    }

    @Override
    public boolean delteSolrAll() {
        boolean flag = false;
        try {
            // 获取连接服务
            SolrClient solrServer = solrTemplate.getSolrClient();
            solrServer.deleteByQuery("*:*");
            // 优化索引
            // solrServer.optimize();
            // 提交事务才能生效
            solrServer.commit();
            flag = true;
        } catch (SolrServerException e) {
            logger.error("SolrServerException:", e);
        } catch (IOException e) {
            logger.error("IOException:", e);
        }
        return flag;
    }
}