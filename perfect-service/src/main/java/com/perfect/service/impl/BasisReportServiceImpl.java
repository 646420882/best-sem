package com.perfect.service.impl;

import com.perfect.dao.BasisReportDAO;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import com.perfect.service.BasisReportService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2014/8/6.
 */
public class BasisReportServiceImpl implements BasisReportService{
    @Resource
    private BasisReportDAO basisReportDAO;
    /**
     * 获取单元报告
     * @param terminal 推广设备 0、全部  1、PC端 2、移动端
     * @param date 时间
     * @param categoryTime 分类时间  0、默认 1、分日 2、分周 3、分月
     * @return
     */
    public List<Object> getUnitReportDate(String terminal,String[] date,int categoryTime){
        List<Object> objectsList = new ArrayList<>();

        switch (categoryTime){
            case 0:
                for (int i=0; i<=date.length;i++){
                    List<Object> object = basisReportDAO.getUnitReportDate(terminal,date[i]+"Unit");
                    objectsList.addAll(object);
                }
                return objectsList;
            case 1:
                for (int i=0; i<=date.length;i++){
                    List<Object> object = basisReportDAO.getUnitReportDate(terminal,date[i]+"Unit");
                    objectsList.addAll(object);
                }
                return objectsList;
            case 2:
                for (int i=0; i<=date.length;i++){
                    List<Object> object = basisReportDAO.getUnitReportDate(terminal,date[i]+"Unit");
                    objectsList.addAll(object);
                }
                return objectsList;
            case 3:
                for (int i=0; i<=date.length;i++){
                    List<Object> object = basisReportDAO.getUnitReportDate(terminal,date[i]+"Unit");
                    objectsList.addAll(object);
                }
                return objectsList;
        }


        return null;
    }


}
