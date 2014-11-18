package com.perfect.dao;

import com.perfect.dto.ViewsDTO;
import com.perfect.entity.CensusEveryDayReportEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.WarningRuleEntity;
import com.perfect.mongodb.base.AbstractSysBaseDAOImpl;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by john on 2014/11/17.
 */
public interface CensusEveryDayReportDao{


    /**
     * 根据日期得到分组过后的停留页面List
     * @param date
     * @return
     */
    public List<ViewsDTO> getGroupLastPageByDate(Date date);

    /**
     * 根据date得到该天的浏览量
     *
     * @param date
     * @return
     */
    public long getCensusCount(Date date,String lastPage,String database_Field);

    /**
     * 添加
     * @param list
     */
    public void insertList(List<CensusEveryDayReportEntity> list);
}