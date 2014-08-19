package com.perfect.dao;

import com.perfect.entity.DataAttributeInfoEntity;
import com.perfect.entity.DataOperationLogEntity;

import java.util.Collection;
import java.util.List;

/**
 * Created by baizz on 2014-07-04.
 */
public interface LogProcessingDAO extends MongoCrudRepository<DataOperationLogEntity, Long> {

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
}
