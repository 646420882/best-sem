package com.perfect.service.impl;

import com.perfect.api.baidu.PromotionMonitoring;
import com.perfect.autosdk.sms.v3.Folder;
import com.perfect.autosdk.sms.v3.FolderMonitor;
import com.perfect.autosdk.sms.v3.Monitor;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.monitoring.MonitorSynchronizedDAO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.monitor.FolderDTO;
import com.perfect.dto.monitor.FolderMonitorDTO;
import com.perfect.service.MonitorSynchronizedService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SubDong on 2014/9/12.
 */
@Service("synchronizedService")
public class MonitorSynchronizedServiceImpl implements MonitorSynchronizedService {

    @Resource
    private MonitorSynchronizedDAO monitorSynchronizedDAO;

    @Resource
    private AccountManageDAO accountManageDAO;

    @Override
    public int getSynchronized() {
        //获取用户信息
        PromotionMonitoring Monitoring = getUserInfo();
        //获取数据
        List<Folder> folders = Monitoring.getFolder();
        List<FolderMonitor> folderMonitors = Monitoring.getMonitorWordByFolderIdAll();

        List<FolderDTO> folderDTOs = new ArrayList<>();
        List<FolderMonitorDTO> monitorDTOs = new ArrayList<>();

        //监控文件夹数据
        for (Folder folder : folders) {
            FolderDTO folderDTO = new FolderDTO();
            folderDTO.setFolderId(folder.getFolderId());
            folderDTO.setFolderName(folder.getFolderName());
            folderDTO.setAccountId(AppContext.getAccountId());
            folderDTO.setLocalStatus(0);
            folderDTOs.add(folderDTO);
        }
        //监控对象数据
        for (FolderMonitor folder : folderMonitors) {
            for (Monitor monitor : folder.getMonitors()) {
                FolderMonitorDTO folderMonitorDTO = new FolderMonitorDTO();
                folderMonitorDTO.setFolderId(monitor.getFolderId());
                folderMonitorDTO.setAdgroupId(monitor.getAdgroupId());
                folderMonitorDTO.setCampaignId(monitor.getCampaignId());
                folderMonitorDTO.setMonitorId(monitor.getMonitorId());
                folderMonitorDTO.setAclid(monitor.getId());
                folderMonitorDTO.setType(monitor.getType());
                folderMonitorDTO.setAccountId(AppContext.getAccountId());
                folderMonitorDTO.setLocalstatus(0);
                monitorDTOs.add(folderMonitorDTO);
            }
        }

        int JudgeFD = monitorSynchronizedDAO.insterData(folderDTOs);
        int JudgeMT = monitorSynchronizedDAO.insterMoniterData(monitorDTOs);

        if (JudgeFD == 1 && JudgeMT == 1) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public int updateMonitorData() {
        return 0;
    }

    /**
     * 获取当前登陆用户基本信息和对应API
     *
     * @return
     */
    public PromotionMonitoring getUserInfo() {
        Long accid = AppContext.getAccountId();
        BaiduAccountInfoDTO entity = accountManageDAO.findByBaiduUserId(accid);
        PromotionMonitoring Monitoring = new PromotionMonitoring(entity.getBaiduUserName(), entity.getBaiduPassword(), entity.getToken());
        return Monitoring;
    }
}
