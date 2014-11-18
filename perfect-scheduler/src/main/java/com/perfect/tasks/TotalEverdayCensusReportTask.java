package com.perfect.tasks;

import com.perfect.dao.CensusEveryDayReportDao;
import com.perfect.dto.ViewsDTO;
import com.perfect.entity.CensusEveryDayReportEntity;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;
import java.util.*;
import java.util.Calendar;

/**
 * Created by john on 2014/11/17.
 * 统计每天的站点PV,UV,IP
 * 每天凌晨0:30执行
 */
public class TotalEverdayCensusReportTask {

    private static final String LP = "lp";//数据库中的停留页面字段
    private static final String UID = "uid";//数据库中的访客id字段
    private static final String IP = "ip";//数据库中的ip字段


    @Resource
    private CensusEveryDayReportDao censusEveryDayReportDao;


    public boolean excute(List<CensusEveryDayReportEntity> entities){
        censusEveryDayReportDao.insertList(entities);
        return true;
    }


    public List<CensusEveryDayReportEntity> selectTask(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE,-1);
//        cal.set(2014,10,13);
        List<ViewsDTO> lastPagelist = censusEveryDayReportDao.getGroupLastPageByDate(cal.getTime());

        List<CensusEveryDayReportEntity> censusRoports = new ArrayList<>();
        for(ViewsDTO lp : lastPagelist){
            long pvs = censusEveryDayReportDao.getCensusCount(cal.getTime(),lp.getField(),LP);
            long uvs = censusEveryDayReportDao.getCensusCount(cal.getTime(),lp.getField(),UID);
            long ips = censusEveryDayReportDao.getCensusCount(cal.getTime(),lp.getField(),IP);

            CensusEveryDayReportEntity entity = new CensusEveryDayReportEntity();
            entity.setIpCount(ips);
            entity.setPvCount(pvs);
            entity.setUvCount(uvs);
            entity.setLp(lp.getField());
            entity.setTotalDate(cal.getTime());
            censusRoports.add(entity);
        }
        return censusRoports.size()==0?null:censusRoports;
    }


    /**
     * 开始执行每天的统计任务
     * @throws JobExecutionException
     */
    public void startTotalCensusReportTask() throws JobExecutionException {
        try {
            List<CensusEveryDayReportEntity> list = selectTask();
            excute(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
