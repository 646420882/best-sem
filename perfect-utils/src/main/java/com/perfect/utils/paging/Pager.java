package com.perfect.utils.paging;

import java.util.List;

/**
 * Created by XiaoWei on 2014/7/28.
 * 分页对象父类
 */
public class Pager {
    //总条数
    private int total;
    //开始页数
    private int start;
    //每页数
    private int pageSize;
    //最大分页数
    private int maxPage;
    //当前页数
    private int currentPage;
    //数据行
    private List rows;

    /**
     * 构造器
     *
     * @param start 开始页
     * @param pageSize 每页数
     */
    public Pager(int start, int pageSize){
        this.start=start;
        this.pageSize=pageSize;
        this.currentPage=start/pageSize+1;
    }

    public Pager() {
    }

    /**
     * 获取当页
     * @return
     */
    public int getCurrentPage(){
        return currentPage;
    }

    /**
     * 获取最大页数
     * @return
     */
    public int getPageSize(){
        return pageSize;
    }

    /**
     * 获取开始页数
     * @return
     */
    public int getStart(){
        return start;
    }

    /**
     * 获取行数据
     * @return
     */
    public List getRows(){
        return rows;
    }

    /**
     * 获取总共条目
     * @return
     */
    public int getTotal(){
        this.total=getRows().size();
        return total;
    }

    /**
     * 设置行数据
     * @param rows
     */
    public void setRows(List rows){
        this.rows=rows;
    }

    /**
     * 获取最大页数
     * @return
     */
    public int getMaxPage(){
        return maxPage;
    }

    /**
     * 设置最大页数
     * @param maxPage
     */
    public  void setMaxPage(int maxPage){
        this.maxPage=maxPage;
    }
}
