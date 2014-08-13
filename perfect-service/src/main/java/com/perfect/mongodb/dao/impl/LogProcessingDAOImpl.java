package com.perfect.mongodb.dao.impl;

import com.perfect.dao.LogProcessingDAO;
import com.perfect.entity.DataOperationLogEntity;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-07-04.
 */
@Repository("logProcessingDAO")
public class LogProcessingDAOImpl implements LogProcessingDAO {

    /**
     * 按类型查找
     *
     * @param type
     * @return
     */
    public List<DataOperationLogEntity> findByType(String type) {
        MongoTemplate log4Mongo = BaseMongoTemplate.getUserMongo();
        List<DataOperationLogEntity> list = log4Mongo.find(
                new Query(Criteria.where("type").is(type)).with(new Sort(Sort.Direction.ASC, "time")),
                DataOperationLogEntity.class,
                "logs");
        return list;
    }

    /**
     * status:
     * -1: 查询所有日志
     * 0: 查询还未同步的日志
     * 1: 查询同步失败的日志
     *
     * @param status
     * @return
     */
    public List<DataOperationLogEntity> findAll(Integer status) {
        MongoTemplate log4Mongo = BaseMongoTemplate.getUserMongo();
        if (status == -1) {
            Query query = new Query();
            query.with(new Sort(Sort.Direction.ASC, "time"));
            List<DataOperationLogEntity> list = log4Mongo.find(query, DataOperationLogEntity.class, "logs");
            return list;
        } else if (status == 0) {
            List<DataOperationLogEntity> list1 = log4Mongo.find(
                    new Query(Criteria.where("status").is(0)).with(new Sort(Sort.Direction.ASC, "time")),
                    DataOperationLogEntity.class,
                    "logs");
            return list1;
        } else if (status == 1) {
            List<DataOperationLogEntity> list2 = log4Mongo.find(
                    new Query(Criteria.where("status").is(1)).with(new Sort(Sort.Direction.ASC, "time")),
                    DataOperationLogEntity.class,
                    "logs");
            return list2;
        }
        return null;
    }

    /**
     * 根据数据id查询该条数据最近的操作日志
     *
     * @param dataId
     * @return
     */
    public DataOperationLogEntity findOne(Long dataId) {
        MongoTemplate log4Mongo = BaseMongoTemplate.getUserMongo();
        List<DataOperationLogEntity> list = log4Mongo.find(
                Query.query(Criteria.where("dataId").is(dataId)).with(new Sort(Sort.Direction.DESC, "time")),
                DataOperationLogEntity.class,
                "logs");
        if (list != null && list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    /**
     * @param params
     * @param skip
     * @param limit
     * @return
     */
    public List<DataOperationLogEntity> find(Map<String, Object> params, int skip, int limit) {
        return null;
    }

    /**
     * 查询全部
     *
     * @return
     */
    public List<DataOperationLogEntity> findAll() {
        MongoTemplate log4Mongo = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "time"));
        List<DataOperationLogEntity> list = log4Mongo
                .find(query, DataOperationLogEntity.class, "logs");
        return list;
    }

    /**
     * 对于在修改数据插入日志时, 若存在相同记录(日志的数据id和其属性唯一确定一条日志), 则对其进行更新;
     * 没有就插入一条新的日志.
     *
     * @param log
     */
    public void insert(DataOperationLogEntity log) {
        MongoTemplate log4Mongo = BaseMongoTemplate.getUserMongo();
        Long dataId = log.getDataId();
        List<DataOperationLogEntity> list = log4Mongo.find(
                new Query(Criteria.where("dataId").is(dataId).and("obj").ne(null)),
                DataOperationLogEntity.class,
                "logs");
        if (!isExists(log)) {
            if (log.getAttribute() == null && log.getInstance() == null) {
                if (list.size() == 1) {
                    //新增的数据还未同步, 则删除数据库中所有dataId为log.getDataId()的日志记录
                    log4Mongo.remove(new Query(Criteria.where("dataId").is(dataId)),
                            DataOperationLogEntity.class, "logs");
                } else {
                    /*
                     * 新增的数据已经同步, 若要删除数据需要同步到凤巢.
                     * 只保留删除数据的那条日志记录.
                     */
                    log4Mongo.remove(new Query(Criteria.where("dataId").is(dataId)),
                            DataOperationLogEntity.class, "logs");
                    log4Mongo.insert(log, "logs");
                }
            } else if (log.getAttribute() != null && log.getInstance() == null) {
                //数据新增之后还未进行同步, 又需要对其进行修改
                Update update = new Update();
                update.set("time", log.getTime());
                update.set("obj." + log.getAttribute().getName(), log.getAttribute().getAfter());
                log4Mongo.updateFirst(
                        new Query(Criteria.where("dataId").is(dataId)),
                        update,
                        "logs");
            } else {
                log4Mongo.insert(log, "logs");
            }
        } else {
            if (log.getAttribute() != null) {
                Update update = new Update();
                update.set("time", log.getTime());
                update.set("attr", log.getAttribute());
                log4Mongo.updateFirst(
                        new Query(Criteria.where("dataId").is(dataId)),
                        update,
                        "logs");
            }
        }
    }

    /**
     * 批量插入日志
     *
     * @param logs
     */
    public void insertAll(List<DataOperationLogEntity> logs) {
        for (DataOperationLogEntity log : logs)
            insert(log);
    }

    /**
     * 数据同步失败后, 会将log的status属性置为1, msg会填上相应的失败信息
     *
     * @param log
     */
    public void update(DataOperationLogEntity log) {
        MongoTemplate log4Mongo = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        query.addCriteria(Criteria.where("dataId").is(log.getDataId()));
        Update update = new Update();
        update.set("time", log.getTime());
        update.set("status", 1);
        update.set("msg", log.getMessage());
        log4Mongo.updateFirst(query, update, "logs");
    }

    /**
     * 批量更新日志
     *
     * @param logs
     */
    public void update(List<DataOperationLogEntity> logs) {
        for (DataOperationLogEntity log : logs)
            update(log);
    }

    /**
     * 数据的新增和删除同步成功后, 根据id删除日志
     *
     * @param dataId
     */
    public void deleteById(Long dataId) {
        MongoTemplate log4Mongo = BaseMongoTemplate.getUserMongo();
        log4Mongo.remove(
                new Query(Criteria.where("dataId").is(dataId)),
                DataOperationLogEntity.class,
                "logs");
    }

    /**
     * 数据的新增和删除同步成功后, 将该条日志删除
     *
     * @param dataIds
     */
    public void deleteByIds(List<Long> dataIds) {
        MongoTemplate log4Mongo = BaseMongoTemplate.getUserMongo();
        for (Long dataId : dataIds)
            log4Mongo.remove(
                    new Query(Criteria.where("dataId").is(dataId)),
                    DataOperationLogEntity.class,
                    "logs");
    }

    /**
     * 用于数据更新操作同步成功后删除日志
     *
     * @param log
     */
    public void delete(DataOperationLogEntity log) {
        MongoTemplate log4Mongo = BaseMongoTemplate.getUserMongo();
        log4Mongo.remove(log, "logs");
    }

    /**
     * 用于数据更新操作同步成功后删除日志
     *
     * @param logs
     */
    public void delete(List<DataOperationLogEntity> logs) {
        MongoTemplate log4Mongo = BaseMongoTemplate.getUserMongo();
        for (DataOperationLogEntity log : logs)
            log4Mongo.remove(log, "logs");
    }

    public void deleteAll() {

    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    /**
     * 修改数据时, 日志的数据id和其属性唯一确定一条日志
     *
     * @param log
     * @return
     */
    private boolean isExists(DataOperationLogEntity log) {
        MongoTemplate log4Mongo = BaseMongoTemplate.getUserMongo();
        List<DataOperationLogEntity> list;
        if (log.getAttribute() != null)
            list = log4Mongo.find(
                    new Query(Criteria.where("dataId").is(log.getDataId())
                            .and("attr.name").is(log.getAttribute().getName())
                            .and("obj").is(null)),
                    DataOperationLogEntity.class,
                    "logs");
        else if (log.getInstance() != null)
            list = log4Mongo.find(
                    new Query(Criteria.where("dataId").is(log.getDataId())
                            .and("attr").is(null)
                            .and("obj").ne(null)),
                    DataOperationLogEntity.class,
                    "logs");
        else
            list = log4Mongo.find(
                    new Query(Criteria.where("dataId").is(log.getDataId())
                            .and("attr").is(null)
                            .and("obj").is(null)),
                    DataOperationLogEntity.class,
                    "logs");

        return list.size() == 1;
    }

}
