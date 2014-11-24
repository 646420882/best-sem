package com.perfect.service.impl;

import com.perfect.dao.CensusDAO;
import com.perfect.dto.ConstantsDTO;
import com.perfect.entity.CensusEntity;
import com.perfect.dao.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dao.mongodb.utils.Pager;
import com.perfect.service.CensusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by XiaoWei on 2014/11/11.
 */
@Service("censusService")
public class CensusServiceImpl extends AbstractUserBaseDAOImpl<CensusEntity,Long> implements CensusService {
    @Resource private CensusDAO censusDAO;
    private SimpleDateFormat allFormat=new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss z", Locale.ENGLISH);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);

    @Override
    public Class<CensusEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public String saveParams(String[] osAnBrowser) {
        String uuid=osAnBrowser[0];
        String system=osAnBrowser[1];
        String browser=osAnBrowser[2];
        String resolution=osAnBrowser[3];
        boolean supportCookie= Boolean.parseBoolean(osAnBrowser[4]);
        boolean supportJava= Boolean.parseBoolean(osAnBrowser[5]);
        String bit=osAnBrowser[6];
        String flash=((osAnBrowser[7].equals(""))?"deny":((osAnBrowser[7]==null?"deny":osAnBrowser[7])));
        Date d=null;
        try {
            d=allFormat.parse(osAnBrowser[8]);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        String intoPage=((osAnBrowser[9].equals(""))?"direct":((osAnBrowser[9]==null?"":osAnBrowser[9])));
        String ip=osAnBrowser[10];
        String area=osAnBrowser[11];
        Integer operate= Integer.valueOf(osAnBrowser[12]);
        String lastPage=osAnBrowser[13];
        CensusEntity censusEntity=new CensusEntity();
        censusEntity.setUuid(uuid);
        censusEntity.setSystem(system);
        censusEntity.setBrowser(browser);
        censusEntity.setResolution(resolution);
        censusEntity.setSupportCookie(supportCookie);
        censusEntity.setSupportJava(supportJava);
        censusEntity.setBit(bit);
        censusEntity.setFlash(flash);
        censusEntity.setDate(d);
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC+08:00"));
        censusEntity.setTime(timeFormat.format(d));
        censusEntity.setIntoPage(intoPage);
        censusEntity.setLastPage(lastPage);
        censusEntity.setIp(ip);
        censusEntity.setArea(area);
        censusEntity.setOperate(operate);
        censusDAO.saveParams(censusEntity);
        return censusEntity.getId();
    }

    @Override
    public ConstantsDTO getTodayTotal(String url) {
        return censusDAO.getTodayTotal(url);
    }

    @Override
    public ConstantsDTO getLastDayTotal(String url) {
        return censusDAO.getLastDayTotal(url);
    }

    @Override
    public ConstantsDTO getLastWeekTotal(String url) {
        return censusDAO.getLastWeekTotal(url);
    }

    @Override
    public ConstantsDTO getLastMonthTotal(String url) {
        return censusDAO.getLastMonthTotal(url);
    }

}
