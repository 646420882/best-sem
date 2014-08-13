package com.perfect.utils;

import com.perfect.entity.DataAttributeInfoEntity;
import com.perfect.entity.DataOperationLogEntity;

import java.util.Date;

/**
 * Created by baizz on 2014-08-13.
 */
public class LogUtils {

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
    public static DataOperationLogEntity getLog(Long dataId, Class _class,
                                                DataAttributeInfoEntity attribute, Object instance) {
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
}
