package com.perfect.service.impl;

import com.perfect.dao.MonitoringDao;
import com.perfect.entity.FolderEntity;
import com.perfect.entity.FolderMonitorEntity;
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
        for (FolderEntity folderEntity : folderEntities) {
            List<FolderMonitorEntity> monitor = monitoringDao.getMonitor(folderEntity.getFolderId());
            folderEntity.setCountNumber(monitor.size());
        }
        return folderEntities;
    }

    @Override
    public List<FolderMonitorEntity> getMonitor() {
        List<FolderEntity> folderEntities = monitoringDao.getForlder();
        List<Long> longs = new ArrayList<>();

        for (FolderEntity folderEntity : folderEntities) {
            List<FolderMonitorEntity> monitor = monitoringDao.getMonitor(folderEntity.getFolderId());
            for (FolderMonitorEntity monitorEntity : monitor) {
                longs.add(monitorEntity.getAclid());
            }
        }
        return null;
    }
}
