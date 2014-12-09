package com.perfect.dto.count;

import com.perfect.vo.BasedDataVO;

import java.util.List;

/**
 * Created by SubDong on 2014/12/5.
 */
public class SourcesAllDTO {
    //外部连接分组数据
    private List<BasedDataVO> intoPageData;
    //搜索引擎分组数据
    private List<BasedDataVO> searchEngine;
    //查询总数
    private Integer findCount;
    //浏览量
    private Integer pageviews;
    //访客数
    private Integer Visitors;

    public List<BasedDataVO> getIntoPageData() {
        return intoPageData;
    }

    public void setIntoPageData(List<BasedDataVO> intoPageData) {
        this.intoPageData = intoPageData;
    }

    public List<BasedDataVO> getSearchEngine() {
        return searchEngine;
    }

    public void setSearchEngine(List<BasedDataVO> searchEngine) {
        this.searchEngine = searchEngine;
    }

    public Integer getFindCount() {
        return findCount;
    }

    public void setFindCount(Integer findCount) {
        this.findCount = findCount;
    }

    public Integer getPageviews() {
        return pageviews;
    }

    public void setPageviews(Integer pageviews) {
        this.pageviews = pageviews;
    }

    public Integer getVisitors() {
        return Visitors;
    }

    public void setVisitors(Integer visitors) {
        Visitors = visitors;
    }
}
