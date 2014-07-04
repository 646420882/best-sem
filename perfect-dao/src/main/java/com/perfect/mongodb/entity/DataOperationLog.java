package com.perfect.mongodb.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by baizz on 2014-7-2.
 */
@Document(collection = "DataOperationLog")
public class DataOperationLog {

    private Long dataId;    //操作数据的ID

    private String type;    //操作数据的类型:1.计划、2.单元、3.关键词、4.创意

    private Date time;      //日志写入时间

    @Field("attr")
    private Set<DataAttributeInfo> attributeInfos = new HashSet<DataAttributeInfo>(1);     //要操作的属性

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

    public Set<DataAttributeInfo> getAttributeInfos() {
        return attributeInfos;
    }

    public void setAttributeInfos(Set<DataAttributeInfo> attributeInfos) {
        this.attributeInfos = attributeInfos;
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
