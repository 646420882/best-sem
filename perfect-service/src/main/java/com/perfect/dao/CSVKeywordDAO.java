package com.perfect.dao;

import com.perfect.entity.CSVEntity;

import java.util.List;

/**
 * Created by XiaoWei on 2014/8/11.
 */
public interface CSVKeywordDAO {
    public void insertAll(List<CSVEntity> csvEntityList);
}
