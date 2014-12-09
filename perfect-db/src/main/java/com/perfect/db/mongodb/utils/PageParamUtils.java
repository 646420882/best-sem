package com.perfect.db.mongodb.utils;

import com.perfect.utils.paging.PaginationParam;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by baizz on 14-12-2.
 */
public class PageParamUtils {


    public static Query withParam(PaginationParam param, Query query) {

        query.skip(param.getStart()).limit(param.getLimit()).with(new Sort((param.isAsc()) ? Sort.Direction.ASC : Sort.Direction.DESC, param.getOrderBy()));

        return query;
    }
}
