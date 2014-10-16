package com.perfect.service;

import com.mongodb.WriteResult;
import com.perfect.autosdk.sms.v3.Folder;
import com.perfect.autosdk.sms.v3.FolderMonitor;
import com.perfect.autosdk.sms.v3.Monitor;
import com.perfect.dto.KeywordDTO;
import com.perfect.entity.FolderEntity;
import com.perfect.entity.FolderMonitorEntity;

import java.util.List;

/**
 * Created by SubDong on 2014/9/10.
 */
public interface MonitoringService {
    /**
     * 获取监控文件夹
     * @return
     */
    public List<FolderEntity> getFolder();

    /**
     * 修改监控文件夹名称
     * @return
     */
    public boolean updateFolderName(Long folderId,String folderName);

    /**
     * 添加监控文件夹
     * @return
     */
    public int addFolder(String folderName);

    /**
     * 删除监控文件夹
     * @param folderId
     * @return
     */
    public boolean deleteFolder(Long folderId);

    /**
     * 获取监控文件夹下的所有内容
     * @return
     */
    public List<KeywordDTO> getMonitor();

    /**
     * 通过监控文件夹ID获取所有监控对象内容
     * @return
     */
    public List<KeywordDTO> getMonitorId(Long folderId);

    /**
     * 删除监控对象
     * @param monitorId
     * @return
     */
    public boolean deleteMonitorId(Long monitorId);

    /**
     * 添加监控对象
     * @param folderID 监控文件夹ID
     * @param campaignId 计划ID
     * @param adgroupId 单元ID
     * @param acliId 实际ID
     * @return
     */
    public int addMonitorId(Long folderID,Long campaignId,Long adgroupId,Long acliId);
}
