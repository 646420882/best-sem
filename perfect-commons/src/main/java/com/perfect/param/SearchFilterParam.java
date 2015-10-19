package com.perfect.param;

/**
 * @author xiaowei
 * @title 各层级列表的表头字段筛选条件
 * @package com.perfect.param
 * @description
 * @update 2015年10月19日. 上午11:38
 */
public class SearchFilterParam {
    private String filterType;//筛选条件是哪一个层级

    private String filterFields;//筛选字段

    private String filterValue;//筛选内容

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getFilterFields() {
        return filterFields;
    }

    public void setFilterFields(String filterFields) {
        this.filterFields = filterFields;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }
}
