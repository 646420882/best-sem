package com.perfect.dao;

import com.mongodb.WriteResult;
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
     * 通过id获取监控文件夹
     * @return
     */
    public boolean updataForlderId(Long folderId, String folderName);

    /**
     * 添加监控文件夹
     * @param folderEntity
     */
    public void addFolder(FolderEntity folderEntity);

    /**
     * 通过监控文件夹删除对应的文件夹
     * @param folderId
     * @return
     */
    public WriteResult deleteFoder(Long folderId);

    /**
     * 通过监控文件夹ID删除对应的监控对象
     * @param folderId
     * @return
     */
    public WriteResult deleteMonitor(Long folderId);

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

    /**
     * 通过监控对象ID 删除监控对象
     * @param MonitorId
     * @return
     */
    public WriteResult deleteMonitorId(Long MonitorId);

    /**
     * 添加监控对象
     * @param folderMonitorEntity
     * @return
     */
    public void addMonitor(FolderMonitorEntity folderMonitorEntity);
}
