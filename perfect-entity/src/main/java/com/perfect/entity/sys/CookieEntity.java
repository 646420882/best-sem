package com.perfect.entity.sys;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by baizz on 2014-11-10.
 * 2014-12-23 refactor
 */
@Document(collection = "sys_cookie")
public class CookieEntity {

    @Id
    private String id;

    @Field("c")
    private String cookie;

    @Field("k")
    private String castk;

    @Field("i")
    private boolean idle;

    @Field("f")
    private long finishTime;    //下次什么时候可以使用该cookie

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCastk() {
        return castk;
    }

    public void setCastk(String castk) {
        this.castk = castk;
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
