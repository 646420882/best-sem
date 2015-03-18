package com.perfect.service;

import com.perfect.dto.ViewsDTO;
import com.perfect.dto.count.CensusEveryDayReportDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by SubDong on 2014/12/15.
 */
public interface CensusEveryDayReportService {

    public void insertList(List<CensusEveryDayReportDTO> censusEveryDayReportDTOs);

    public List<ViewsDTO> getGroupLastPageByDate(Date date);

    public long getCensusCount(Date date, String lastPage, String database_Field);
}
