package com.perfect.service.impl;

import com.perfect.api.baidu.PromotionMonitoring;
import com.perfect.autosdk.sms.v3.Folder;
import com.perfect.autosdk.sms.v3.FolderMonitor;
import com.perfect.autosdk.sms.v3.Monitor;
import com.perfect.core.AppContext;
import com.perfect.dao.AccountManageDAO;
import com.perfect.dao.MonitoringDao;
import com.perfect.dto.FolderDataAllDTO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.FolderEntity;
import com.perfect.entity.FolderMonitorEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.service.AssistantKeywordService;
import com.perfect.service.MonitoringService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SubDong on 2014/9/10.
 */
@Service("monitoringService")
public class MonitoringServiceImpl implements MonitoringService {

    @Resource
    private MonitoringDao monitoringDao;

    @Override
    public List<FolderEntity> getFolder() {
        List<FolderEntity> folderEntities = monitoringDao.getForlder();
        for (FolderEntity folderEntity : folderEntities){
            List<FolderMonitorEntity> monitor = monitoringDao.getMonitor(folderEntity.getFolderId());
            folderEntity.setCountNumber(monitor.size());
        }
        return folderEntities;
    }
}
