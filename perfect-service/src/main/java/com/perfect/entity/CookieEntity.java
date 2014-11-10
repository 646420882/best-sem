package com.perfect.entity;

import org.apache.http.client.CookieStore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by baizz on 2014-11-10.
 */
@Document(collection = "sys_cookie")
public class CookieEntity {

    @Id
    private String id;

    @Field("c")
    private CookieStore cookie;

    @Field("i")
    private boolean idle;

    @Field("f")
    private long finishTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CookieStore getCookie() {
        return cookie;
    }

    public void setCookie(CookieStore cookie) {
        this.cookie = cookie;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }
}
