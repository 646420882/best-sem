package com.perfect.dao.monitoring;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.monitor.FolderDTO;
import com.perfect.dto.monitor.FolderMonitorDTO;

import java.util.List;

/**
 * Created by SubDong on 2014/9/15.
 */
public interface MonitoringDao extends HeyCrudRepository<FolderDTO,Long> {

    /**
     * 获取所有监控文件夹信息
     *
     * @return
     */
    public List<FolderDTO> getForlder();

    /**
     * 通过id获取监控文件夹
     *
     * @return
     */
    public List<FolderDTO> getForlderId(Long folderId);

    /**
     * 通过id获取监控文件夹
     *
     * @return
     */
    public boolean updataForlderId(Long folderId, String folderName);

    /**
     * 添加监控文件夹
     *
     * @param folderDTO
     */
    public void addFolder(FolderDTO folderDTO);

    /**
     * 通过监控文件夹删除对应的文件夹
     *
     * @param folderId
     * @return
     */
    public int deleteFoder(Long folderId);

    /**
     * 通过监控文件夹ID删除对应的监控对象
     *
     * @param folderId
     * @return
     */
    public int deleteMonitor(Long folderId);

    /**
     * 获取所有监控对象
     *
     * @return
     */
    public List<FolderMonitorDTO> getMonitor();

    /**
     * 通过监控文件夹ID 获取对应的监控对象
     *
     * @return
     */
    public List<FolderMonitorDTO> getMonitorId(Long folderId);

    /**
     * 通过监控对象ID 删除监控对象
     *
     * @param MonitorId
     * @return
     */
    public int deleteMonitorId(Long MonitorId);

    /**
     * 添加监控对象
     *
     * @param folderMonitorDTO
     * @return
     */
    public void addMonitor(FolderMonitorDTO folderMonitorDTO);

    /**
     * 根据关键词的long id得到该关键词所属监控文件夹数
     *
     * @return
     */
    Long getForlderCountByKwid(long kwid);
}
