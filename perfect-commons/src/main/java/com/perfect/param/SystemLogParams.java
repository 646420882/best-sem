package com.perfect.param;

import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 15/12/16.
 */
public class SystemLogParams {

    /**
     * 开始时间
     */
     Long start;

    /**
     * 结束时间
     */
     Long end;

    /**
     * 用户名
     */
     String user;

    Integer level;

    /**
     * 层级id列表
     */
     List<Long> oids;

    List<String> property;
     Integer pageNo;
     Integer pageSize;

    public Long getStart() {
        return start;
    }

    public SystemLogParams setStart(Long start) {
        this.start = start;
        return this;
    }

    public Long getEnd() {
        return end;
    }

    public SystemLogParams setEnd(Long end) {
        this.end = end;
        return this;
    }

    public String getUser() {
        return user;
    }

    public SystemLogParams setUser(String user) {
        this.user = user;
        return this;
    }


    public List<Long> getOids() {
        return oids;
    }

    public void setOids(List<Long> oids) {
        this.oids = oids;
    }


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getProperty() {
        return property;
    }

    public void setProperty(List<String> property) {
        this.property = property;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
