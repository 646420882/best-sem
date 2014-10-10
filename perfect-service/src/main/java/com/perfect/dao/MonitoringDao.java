package com.perfect.dao;

import com.perfect.entity.FolderEntity;
import com.perfect.entity.FolderMonitorEntity;

import java.util.List;

/**
 * Created by SubDong on 2014/9/15.
 */
public interface MonitoringDao {

    /**
     * 获取所有监控文件夹信息
     * @return
     */
    public List<FolderEntity> getForlder();

    /**
     * 通过id获取监控文件夹
     * @return
     */
    public List<FolderEntity> getForlderId(Long folderId);

    /**
     * 获取所有监控对象
     * @return
     */
    public List<FolderMonitorEntity> getMonitor();

    /**
     * 通过监控文件夹ID 获取对应的监控对象
     * @return
     */
    public List<FolderMonitorEntity> getMonitorId(Long folderId);
}
