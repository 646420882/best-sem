package com.perfect.mongodb.dao;

import com.perfect.entity.DataAttributeInfoEntity;
import com.perfect.entity.DataOperationLogEntity;

import java.util.Collection;
import java.util.List;

/**
 * Created by baizz on 2014-7-4.
 */
public interface LogProcessingDAO extends CrudRepository<DataOperationLogEntity, Long> {

    /**
     * 按类型查找
     * <br>------------------------------<br>
     *
     * @param type
     * @return
     */
    Collection<DataOperationLogEntity> findByType(String type);

    /**
     * <br>------------------------------<br>
     *
     * @param status
     * @return
     */
    Collection<DataOperationLogEntity> findAll(Integer status);

    /**
     * <br>------------------------------<br>
     *
     * @param logs
     */
    void delete(List<DataOperationLogEntity> logs);

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
    DataOperationLogEntity getLog(Long id, Class _class, DataAttributeInfoEntity attribute, Object instance);
}
