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

    private String msg;
    private int code;


    public BootStrapPagerInfo(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public BootStrapPagerInfo() {

    }

    private long total;
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
    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getMsg() {
        return msg;
    }

    public BootStrapPagerInfo setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getCode() {
        return code;
    }

    public BootStrapPagerInfo setCode(int code) {
        this.code = code;
        return this;
    }


    public static BootStrapPagerInfo buildErrorInfo(int code, String msg) {
        return new BootStrapPagerInfo().setMsg(msg).setCode(code);
    }
}
