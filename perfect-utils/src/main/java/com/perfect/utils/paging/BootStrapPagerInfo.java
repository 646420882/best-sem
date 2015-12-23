package com.perfect.utils.paging;

import java.util.List;

/**
 * @author xiaowei
 * @title 用于返回BootStrapTable 所需数据格式
 * @package com.perfect.admin.commons
 * @description
 * @update 2015年12月23日. 下午4:53
 */
public class BootStrapPagerInfo {
    public BootStrapPagerInfo(int total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public BootStrapPagerInfo() {

    }

    private int total;
    private List<?> rows;


    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    //        int totalPage = totalCount / pageSize;
//        if (totalPage == 0 || totalCount % pageSize != 0) {
//            totalPage++;
//        }
    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
