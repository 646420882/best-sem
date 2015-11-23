package com.perfect.param;

/**
 * @author xiaowei
 * @title 各层级列表的表头字段筛选条件
 * @package com.perfect.param
 * @description
 * @update 2015年10月19日. 上午11:38
 */
public class SearchFilterParam {
    private String cid;//计划id
    private String aid;//单元id
    private String oid;//用于层级数据id载体
    private String filterType;//筛选条件是哪一个层级

    private String filterField;//筛选字段

    private String filterValue;//筛选内容

    private Integer selected;//选择的筛选模式

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getFilterField() {
        return filterField;
    }

    public void setFilterField(String filterField) {
        this.filterField = filterField;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @Override
    public String toString() {
        return "SearchFilterParam{" +
                "cid='" + cid + '\'' +
                ", aid='" + aid + '\'' +
                ", oid='" + oid + '\'' +
                ", filterType='" + filterType + '\'' +
                ", filterField='" + filterField + '\'' +
                ", filterValue='" + filterValue + '\'' +
                ", selected=" + selected +
                '}';
    }
}
