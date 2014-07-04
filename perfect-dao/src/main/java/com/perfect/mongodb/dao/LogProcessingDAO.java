package com.perfect.mongodb.dao;

import com.perfect.mongodb.entity.DataAttributeInfo;
import com.perfect.mongodb.entity.DataOperationLog;

import java.util.Collection;
import java.util.List;

/**
 * Created by baizz on 2014-7-4.
 */
public interface LogProcessingDAO extends CrudRepository<DataOperationLog, Long> {

    /**
     * 按类型查找
     * <br>------------------------------<br>
     *
     * @param type
     * @return
     */
    List<DataOperationLog> findByType(String type);

    /**
     * <br>------------------------------<br>
     *
     * @param status
     * @return
     */
    Collection<DataOperationLog> findAll(Integer status);

    /**
     * <br>------------------------------<br>
     *
     * @param logs
     */
    void delete(List<DataOperationLog> logs);

    /**
     * 创建日志
     * <br>------------------------------<br>
     *
     * @param id
     * @param _class
     * @param attribute
     * @param instance
     * @return
     */
    DataOperationLog getLog(Long id, Class _class, DataAttributeInfo attribute, Object instance);
}
