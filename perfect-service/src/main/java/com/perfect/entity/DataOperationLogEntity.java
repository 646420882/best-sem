package com.perfect.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Created by baizz on 2014-07-02.
 */
@Document(collection = "logs")
public class DataOperationLogEntity {

    private Long dataId;    //数据的ID

    private String type;    //数据类型: 计划、单元、关键词、创意

    private Date time;      //日志写入时间

    @Field("attr")
    private DataAttributeInfoEntity attribute;     //用于修改(obj为null)---要修改的属性

    @Field("obj")
    private Object instance;      //用于新增(attr为null)---一个type实例

    private Integer status;     //0:未操作, 1:操作失败(操作成功后会删除该条日志)

    @Field("msg")
    private String message;     //操作失败后返回失败信息

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public DataAttributeInfoEntity getAttribute() {
        return attribute;
    }

    public void setAttribute(DataAttributeInfoEntity attribute) {
        this.attribute = attribute;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
