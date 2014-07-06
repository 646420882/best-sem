package com.perfect.mongodb.dao.impl;

import com.perfect.entity.DataAttributeInfoEntity;
import com.perfect.entity.DataOperationLogEntity;
import com.perfect.dao.LogProcessingDAO;
import com.perfect.mongodb.utils.Log4MongoTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by baizz on 2014-7-4.
 */
@Repository(value = "logProcessingDAO")
public class LogProcessingDAOImpl implements LogProcessingDAO {

    @Resource(name = "log4MongoTemplate")
    private Log4MongoTemplate log4MongoTemplate;

    /**
     * 创建日志
     * =======
     * # 新增数据
     * attribute = null
     * # 修改数据
     * instance = null
     * # 删除数据
     * attribute = null, instance = null
     *
     * @param dataId
     * @param _class
     * @param attribute
     * @param instance
     * @return
     */
    public DataOperationLogEntity getLog(Long dataId, Class _class, DataAttributeInfoEntity attribute, Object instance) {
        DataOperationLogEntity log = new DataOperationLogEntity();
        log.setDataId(dataId);
        log.setType(_class.getSimpleName());
        log.setTime(new Date());
        log.setStatus(0);   //默认为0, 还未进行数据同步
        if (attribute != null && instance == null)
            //更新
            log.setAttribute(attribute);
        else if (instance != null && attribute == null)
            //新增
            log.setInstance(instance);
        return log;
    }

    /**
     * 按类型查找
     *
     * @param type
     * @return
     */
    public List<DataOperationLogEntity> findByType(String type) {
        List<DataOperationLogEntity> list = log4MongoTemplate.find(
                new Query(Criteria.where("type").is(type)).with(new Sort(Sort.Direction.ASC, "time")),
                DataOperationLogEntity.class,
                "DataOperationLogEntity");
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
    public Collection<DataOperationLogEntity> findAll(Integer status) {
        if (status == -1) {
            Query query = new Query();
            query.with(new Sort(Sort.Direction.ASC, "time"));
            List<DataOperationLogEntity> list = log4MongoTemplate.find(query, DataOperationLogEntity.class, "DataOperationLogEntity");
            return list;
        } else if (status == 0) {
            List<DataOperationLogEntity> list1 = log4MongoTemplate.find(
                    new Query(Criteria.where("status").is(0)).with(new Sort(Sort.Direction.ASC, "time")),
                    DataOperationLogEntity.class,
                    "DataOperationLogEntity");
            return list1;
        } else if (status == 1) {
            List<DataOperationLogEntity> list2 = log4MongoTemplate.find(
                    new Query(Criteria.where("status").is(1)).with(new Sort(Sort.Direction.ASC, "time")),
                    DataOperationLogEntity.class,
                    "DataOperationLogEntity");
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
        List<DataOperationLogEntity> list = log4MongoTemplate.find(
                Query.query(Criteria.where("dataId").is(dataId)).with(new Sort(Sort.Direction.DESC, "time")),
                DataOperationLogEntity.class,
                "DataOperationLogEntity");
        if (list != null && list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    /**
     * @param criteria
     * @param skip
     * @param limit
     * @return
     */
    public Collection<DataOperationLogEntity> find(DataOperationLogEntity criteria, int skip, int limit) {
        Query query = getQuery(criteria);
        query.skip(skip).limit(limit);
        return log4MongoTemplate.find(query, DataOperationLogEntity.class, "DataOperationLogEntity");
    }

    /**
     * 查询全部
     *
     * @return
     */
    public Collection<DataOperationLogEntity> findAll() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "time"));
        List<DataOperationLogEntity> list = log4MongoTemplate
                .find(query, DataOperationLogEntity.class, "DataOperationLogEntity");
        return list;
    }

    /**
     * 对于在修改数据插入日志时, 若存在相同记录(日志的数据id和其属性唯一确定一条日志), 则对其进行更新;
     * 没有就插入一条新的日志.
     *
     * @param log
     */
    public void insert(DataOperationLogEntity log) {
        Long dataId = log.getDataId();
        if (!isExists(log)) {
            if (log.getAttribute() == null && log.getInstance() == null) {
                List<DataOperationLogEntity> list = log4MongoTemplate.find(
                        new Query(Criteria.where("dataId").is(dataId).and("obj").ne(null)),
                        DataOperationLogEntity.class,
                        "DataOperationLogEntity");
                if (list.size() == 1) {
                    //新增的数据还未同步, 则删除数据库中所有dataId为log.getDataId()的日志记录
                    log4MongoTemplate.remove(new Query(Criteria.where("dataId").is(dataId)),
                            DataOperationLogEntity.class, "DataOperationLogEntity");
                } else {
                    /*
                     * 新增的数据已经同步, 若要删除数据需要同步到凤巢.
                     * 只保留删除数据的那条日志记录.
                     */
                    log4MongoTemplate.remove(new Query(Criteria.where("dataId").is(dataId)),
                            DataOperationLogEntity.class, "DataOperationLogEntity");
                    log4MongoTemplate.insert(log, "DataOperationLogEntity");
                }
            } else {
                log4MongoTemplate.insert(log, "DataOperationLogEntity");
            }
        } else {
            if (log.getAttribute() != null) {
                Update update = new Update();
                update.set("time", log.getTime());
                update.set("attr", log.getAttribute());
                log4MongoTemplate.updateFirst(
                        new Query(Criteria.where("dataId").is(dataId)),
                        update,
                        "DataOperationLogEntity");
            }
        }
    }

    /**
     * 批量插入日志
     *
     * @param logs
     */
    public void insertAll(Collection<? extends DataOperationLogEntity> logs) {
        for (DataOperationLogEntity log : logs)
            insert(log);
    }

    /**
     * 数据同步失败后, 会将log的status属性置为1, msg会填上相应的失败信息
     *
     * @param log
     */
    public void update(DataOperationLogEntity log) {
        Query query = new Query();
        query.addCriteria(Criteria.where("dataId").is(log.getDataId()));
        Update update = new Update();
        update.set("time", log.getTime());
        update.set("status", 1);
        update.set("msg", log.getMessage());
        log4MongoTemplate.updateFirst(query, update, "DataOperationLogEntity");
    }

    /**
     * 批量更新日志
     *
     * @param logs
     */
    public void update(Collection<DataOperationLogEntity> logs) {
        for (DataOperationLogEntity log : logs)
            update(log);
    }

    public void updateMulti(String key, Object value1, Object value2) {

    }

    /**
     * 数据的新增和删除同步成功后, 根据id删除日志
     *
     * @param dataId
     */
    public void deleteById(Long dataId) {
        log4MongoTemplate.remove(
                new Query(Criteria.where("dataId").is(dataId)),
                DataOperationLogEntity.class,
                "DataOperationLogEntity");
    }

    /**
     * 数据的新增和删除同步成功后, 将该条日志删除
     *
     * @param dataIds
     */
    public void deleteByIds(Collection<Long> dataIds) {
        for (Long dataId : dataIds)
            log4MongoTemplate.remove(
                    new Query(Criteria.where("dataId").is(dataId)),
                    DataOperationLogEntity.class,
                    "DataOperationLogEntity");
    }

    /**
     * 用于数据更新操作同步成功后删除日志
     *
     * @param log
     */
    public void delete(DataOperationLogEntity log) {
        log4MongoTemplate.remove(log, "DataOperationLogEntity");
    }

    /**
     * 用于数据更新操作同步成功后删除日志
     *
     * @param logs
     */
    public void delete(List<DataOperationLogEntity> logs) {
        for (DataOperationLogEntity log : logs)
            log4MongoTemplate.remove(log, "DataOperationLogEntity");
    }

    public void deleteAll() {

    }

    /**
     * 修改数据时, 日志的数据id和其属性唯一确定一条日志
     *
     * @param log
     * @return
     */
    private boolean isExists(DataOperationLogEntity log) {
        List<DataOperationLogEntity> list = new ArrayList<DataOperationLogEntity>();
        if (log.getAttribute() != null)
            list = log4MongoTemplate.find(
                    new Query(Criteria.where("dataId").is(log.getDataId())
                            .and("attr.name").is(log.getAttribute().getName())
                            .and("obj").is(null)),
                    DataOperationLogEntity.class,
                    "DataOperationLogEntity");
        else if (log.getInstance() != null)
            list = log4MongoTemplate.find(
                    new Query(Criteria.where("dataId").is(log.getDataId())
                            .and("attr").is(null)
                            .and("obj").ne(null)),
                    DataOperationLogEntity.class,
                    "DataOperationLogEntity");
        else if (log.getAttribute() == null && log.getInstance() == null)
            list = log4MongoTemplate.find(
                    new Query(Criteria.where("dataId").is(log.getDataId())
                            .and("attr").is(null)
                            .and("obj").is(null)),
                    DataOperationLogEntity.class,
                    "DataOperationLogEntity");

        if (list.size() == 1) return true;
        else return false;
    }

    private Query getQuery(DataOperationLogEntity log) {
        Query query = new Query();

        if (log == null)
            return query;

        if (log.getDataId() != null)
            query.addCriteria(Criteria.where("dataId").is(log.getDataId()));

        return query;
    }

}
