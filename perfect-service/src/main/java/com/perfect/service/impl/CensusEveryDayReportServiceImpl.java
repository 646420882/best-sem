package com.perfect.service.impl;

import com.perfect.dao.report.CensusEveryDayReportDao;
import com.perfect.dto.ViewsDTO;
import com.perfect.dto.count.CensusEveryDayReportDTO;
import com.perfect.service.CensusEveryDayReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by SubDong on 2014/12/15.
 */
@Service("censusEveryDayReportService")
public class CensusEveryDayReportServiceImpl implements CensusEveryDayReportService {

    @Resource
    private CensusEveryDayReportDao censusEveryDayReportDao;

    @Override
    public void insertList(List<CensusEveryDayReportDTO> censusEveryDayReportDTOs) {
        censusEveryDayReportDao.insertList(censusEveryDayReportDTOs);
    }

    @Override
    public List<ViewsDTO> getGroupLastPageByDate(Date date) {
        return censusEveryDayReportDao.getGroupLastPageByDate(date);
    }

    @Override
    public long getCensusCount(Date date, String lastPage, String database_Field) {
        return censusEveryDayReportDao.getCensusCount(date, lastPage, database_Field);
    }
}
