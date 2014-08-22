package com.perfect.dataPull;

import com.perfect.dao.AsynchronousReportDAO;
import com.perfect.mongodb.dao.impl.AsynchronousReportDAOImpl;
import com.perfect.mongodb.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by john on 2014/8/22.
 */
public class DataPull {

    public void getFullAccountDataPull(){
        AsynchronousReportDAO dao = new AsynchronousReportDAOImpl();
        Calendar cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
        String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());

        List<String> list = DateUtil.getPeriod(yesterday, yesterday);
        for (String dateStr : list) {
            dao.getAccountReportData(dateStr);
            dao.getCampaignReportData(dateStr);
            dao.getAdgroupReportData(dateStr);
            dao.getCreativeReportData(dateStr);
            dao.getKeywordReportData(dateStr);
            dao.getRegionReportData(dateStr);
        }
    }
}
