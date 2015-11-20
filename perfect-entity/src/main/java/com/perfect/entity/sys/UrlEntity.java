package com.perfect.entity.sys;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by vbzer_000 on 2014/9/24.
 */
@Deprecated //涉及的实现类未使用
@Document(collection = "sys_urlpool")
public class UrlEntity {

    @Id
    private String id;

    @Field("r")
    private String request;

    @Field("i")
    private boolean idle;

    @Field("f")
    private long finishTime;

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
