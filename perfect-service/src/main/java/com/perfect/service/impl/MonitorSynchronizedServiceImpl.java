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
import com.perfect.entity.sys.BaiduAccountInfoEntity;
import com.perfect.service.MonitorSynchronizedService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SubDong on 2014/9/12.
 */
@Repository("synchronizedService")
public class MonitorSynchronizedServiceImpl implements MonitorSynchronizedService {

    @Resource
    private MonitorSynchronizedDAO monitorSynchronizedDAO;

    @Resource
    private AccountManageDAO<BaiduAccountInfoEntity> accountManageDAO;

    @Override
    public int getSynchronized() {
        //获取用户信息
        PromotionMonitoring Monitoring = getUserInfo();
        //获取数据
        List<Folder> folders = Monitoring.getFolder();
        List<FolderMonitor> folderMonitors = Monitoring.getMonitorWordByFolderIdAll();

        List<FolderDTO> forlderEntities = new ArrayList<>();
        List<FolderMonitorDTO> monitorEntities = new ArrayList<>();

        //监控文件夹数据
        for (Folder folder : folders) {
            FolderDTO forlderEntity = new FolderDTO();
            forlderEntity.setFolderId(folder.getFolderId());
            forlderEntity.setFolderName(folder.getFolderName());
            forlderEntity.setAccountId(AppContext.getAccountId());
            forlderEntities.add(forlderEntity);
        }
        //监控对象数据
        for (FolderMonitor folder : folderMonitors) {
            for (Monitor monitor : folder.getMonitors()) {
                FolderMonitorDTO folderMonitorEntity = new FolderMonitorDTO();
                folderMonitorEntity.setFolderId(monitor.getFolderId());
                folderMonitorEntity.setAdgroupId(monitor.getAdgroupId());
                folderMonitorEntity.setCampaignId(monitor.getCampaignId());
                folderMonitorEntity.setMonitorId(monitor.getMonitorId());
                folderMonitorEntity.setAclid(monitor.getId());
                folderMonitorEntity.setType(monitor.getType());
                folderMonitorEntity.setAccountId(AppContext.getAccountId());
                monitorEntities.add(folderMonitorEntity);
            }
        }

        int JudgeFD = monitorSynchronizedDAO.insterData(forlderEntities);
        int JudgeMT = monitorSynchronizedDAO.insterMoniterData(monitorEntities);

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
