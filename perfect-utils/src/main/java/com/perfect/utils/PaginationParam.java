package com.perfect.utils;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by vbzer_000 on 14-11-25.
 */
public class PaginationParam {

    private int start;

    private int limit;

    private String orderBy;

    private boolean asc;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public Query withParam(Query query) {
        return query.skip(getStart()).limit(getLimit()).with(new Sort((isAsc()) ? Sort.Direction.ASC : Sort.Direction.DESC, getOrderBy()));
    }
}
