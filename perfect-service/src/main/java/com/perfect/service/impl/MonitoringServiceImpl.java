package com.perfect.service.impl;

import com.google.common.collect.Maps;
import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.api.baidu.PromotionMonitoring;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.sms.v3.Folder;
import com.perfect.autosdk.sms.v3.Monitor;
import com.perfect.autosdk.sms.v3.QualityType;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.monitoring.MonitoringDao;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.keyword.KeywordInfoDTO;
import com.perfect.dto.monitor.FolderDTO;
import com.perfect.dto.monitor.FolderMonitorDTO;
import com.perfect.service.AssistantKeywordService;
import com.perfect.service.MonitoringService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Created by SubDong on 2014/9/10.
 */
@Service("monitoringService")
public class MonitoringServiceImpl implements MonitoringService {

    @Resource
    private MonitoringDao monitoringDao;

    @Resource
    private AssistantKeywordService assistantKeywordService;

    @Resource
    private AccountManageDAO accountManageDAO;

    @Override
    public List<FolderDTO> getFolder() {
        List<FolderDTO> folderEntities = monitoringDao.getForlder();
        for (FolderDTO folderEntity : folderEntities) {
            List<FolderMonitorDTO> monitor = monitoringDao.getMonitorId(folderEntity.getFolderId());
            folderEntity.setCountNumber(monitor.size());
        }
        return folderEntities;
    }

    @Override
    public boolean updateFolderName(Long folderId, String folderName) {
        boolean writeResult = monitoringDao.updataForlderId(folderId, folderName);
        return writeResult;
    }

    @Override
    public int addFolder(String folderName) {
        PromotionMonitoring monitoring = getUserInfo();
        List<FolderDTO> list = monitoringDao.getForlder();
        if (list.size() < 20) {
            Folder folder = new Folder();
            List<Folder> strings = new ArrayList<>();
            folder.setFolderName(folderName);
            strings.add(folder);

            //int Judge = 0;
            //List<Folder> folderList = new ArrayList<>();

//            try {
//                folderList = monitoring.addFolder(strings);
//                Judge = 1;
//            } catch (Exception e) {
//                Judge = 0;
//            }
//            if (Judge == 1) {
            //for (Folder folder1 : folderList) {
            //sid 当前时间的的毫秒数 加上一个一位的随机数   是字符串意义上的加法
            String sid = String.valueOf(new Date().getTime()) + String.valueOf((int) (Math.random() * 10));
            FolderDTO folderEntity = new FolderDTO();
            folderEntity.setAccountId(AppContext.getAccountId());
            folderEntity.setFolderName(folderName);
            folderEntity.setFolderId(Long.valueOf(sid));
            folderEntity.setLocalStatus(1);
            monitoringDao.addFolder(folderEntity);
            //}
            return 1;
//            } else {
//                return 0;
//            }
        } else {
            return -1;
        }
    }

    @Override
    public boolean deleteFolder(Long folderId) {

        List<FolderMonitorDTO> monitorEntities = monitoringDao.getMonitorId(folderId);
        boolean returnFolder;
        int i;
        if (monitorEntities.size() > 0) {
            int remove = monitoringDao.deleteMonitor(folderId);
            if (remove > 0) {
                int folderRemove = monitoringDao.deleteFoder(folderId);
                i = folderRemove;
            } else {
                i = 0;
            }
        } else {
            int folderRemove = monitoringDao.deleteFoder(folderId);
            i = folderRemove;
        }
        if (i > 0) {
            returnFolder = true;
        } else {
            returnFolder = false;
        }

        return returnFolder;
    }

    @Override
    public List<KeywordInfoDTO> getMonitor() {
        List<FolderDTO> folderEntities = monitoringDao.getForlder();
        List<KeywordInfoDTO> returnkeywordInfoDTO = new ArrayList<>();
        for (FolderDTO folderEntity : folderEntities) {

            List<Long> longs = new ArrayList<>();

            List<FolderMonitorDTO> monitor = monitoringDao.getMonitorId(folderEntity.getFolderId());

            for (FolderMonitorDTO monitorEntity : monitor) {
                longs.add(monitorEntity.getAclid());
            }

            List<KeywordInfoDTO> keywordInfoDTOs = assistantKeywordService.getKeywordListByIds(longs);
            BaiduAccountInfoDTO baiduAccountInfoDTO = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
            CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoDTO.getBaiduUserName(), baiduAccountInfoDTO.getBaiduPassword(), baiduAccountInfoDTO.getToken());
            BaiduApiService apiService = new BaiduApiService(commonService);
            List<QualityType> qualityType = apiService.getKeywordQuality(longs);
            for (FolderMonitorDTO aLong : monitor) {
                for (KeywordInfoDTO dto : keywordInfoDTOs) {
                    //设置监控文件夹信息
                    if (aLong.getAclid().intValue() == dto.getObject().getKeywordId().intValue()) {
                        dto.setMonitorId(aLong.getMonitorId());
                        dto.setFolderId(aLong.getFolderId());
                        for (FolderDTO folderEntity1 : folderEntities) {
                            if (folderEntity1.getFolderId().intValue() == aLong.getFolderId().intValue()) {
                                dto.setFolderName(folderEntity1.getFolderName());
                            }
                        }
                    }
                    //设置质量度信息
                    for (QualityType qualityType1 : qualityType) {
                        if (qualityType1.getId().intValue() == dto.getObject().getKeywordId().intValue()) {
                            dto.setQuality(qualityType1.getQuality());
                            dto.setMobileQuality(qualityType1.getMobileQuality());
                        }
                    }
                }

            }
            returnkeywordInfoDTO.addAll(keywordInfoDTOs);
        }
        return returnkeywordInfoDTO;
    }

    @Override
    public List<KeywordInfoDTO> getMonitorId(Long folderId) {
        List<FolderDTO> folderEntities = monitoringDao.getForlderId(folderId);
        List<KeywordInfoDTO> keywordInfoDTOs = new ArrayList<>();
        for (FolderDTO folderEntity : folderEntities) {

            List<Long> longs = new ArrayList<>();

            List<FolderMonitorDTO> monitor = monitoringDao.getMonitorId(folderEntity.getFolderId());

            for (FolderMonitorDTO monitorEntity : monitor) {
                longs.add(monitorEntity.getAclid());
            }

            keywordInfoDTOs = assistantKeywordService.getKeywordListByIds(longs);
            BaiduAccountInfoDTO baiduAccountInfoDTO = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
            CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoDTO.getBaiduUserName(), baiduAccountInfoDTO.getBaiduPassword(), baiduAccountInfoDTO.getToken());
            BaiduApiService apiService = new BaiduApiService(commonService);
            List<QualityType> qualityType = apiService.getKeywordQuality(longs);
            for (FolderMonitorDTO aLong : monitor) {
                for (KeywordInfoDTO dto : keywordInfoDTOs) {
                    //设置监控文件夹信息
                    if (aLong.getAclid().intValue() == dto.getObject().getKeywordId().intValue()) {
                        dto.setMonitorId(aLong.getMonitorId());
                        dto.setFolderId(aLong.getFolderId());
                        for (FolderDTO folderEntity1 : folderEntities) {
                            if (folderEntity1.getFolderId().intValue() == aLong.getFolderId().intValue()) {
                                dto.setFolderName(folderEntity1.getFolderName());
                            }
                        }
                    }
                    //设置质量度信息
                    for (QualityType qualityType1 : qualityType) {
                        if (qualityType1.getId().intValue() == dto.getObject().getKeywordId().intValue()) {
                            dto.setQuality(qualityType1.getQuality());
                            dto.setMobileQuality(qualityType1.getMobileQuality());
                        }
                    }
                }

            }
        }
        return keywordInfoDTOs;
    }

    @Override
    public boolean deleteMonitorId(Long MonitorId) {
        boolean returnFolder = false;
        int folderRemove = monitoringDao.deleteMonitorId(MonitorId);
        if (folderRemove > 0) {
            returnFolder = true;
        } else {
            returnFolder = false;
        }
        return returnFolder;
    }

    @Override
    public int addMonitorId(Long folderID, Long campaignId, Long adgroupId, Long acliId) {

        List<FolderMonitorDTO> entityList = monitoringDao.getMonitor();
        int i = 0;
        List<FolderMonitorDTO> monitorEntities = monitoringDao.getMonitorId(folderID);
        for (FolderMonitorDTO entity : monitorEntities) {
            if (entity.getAclid().intValue() == acliId.intValue()) {
                i = -2;
            }
        }
        if (i == 0 && entityList.size() < 2000) {
            FolderMonitorDTO monitorEntity = new FolderMonitorDTO();
            //sid 当前时间的的毫秒数 加上一个一位的随机数   是字符串意义上的加法
            String sid = String.valueOf(new Date().getTime()) + String.valueOf((int) (Math.random() * 10));
            monitorEntity.setMonitorId(Long.parseLong(sid));
            monitorEntity.setFolderId(folderID);
            monitorEntity.setCampaignId(campaignId);
            monitorEntity.setAdgroupId(adgroupId);
            monitorEntity.setAclid(acliId);
            monitorEntity.setAccountId(AppContext.getAccountId());
            monitorEntity.setType(11);
            monitorEntity.setLocalstatus(1);

            try {
                monitoringDao.addMonitor(monitorEntity);
                i = 1;
            } catch (Exception e) {
                i = 0;
            }
        } else if (i == 0) {
            i = -1;
        }
        return i;
    }

    @Override
    public int upMonitor() {
        PromotionMonitoring monitoring = getUserInfo();
        List<FolderDTO> list = monitoringDao.getForlder();
        List<FolderMonitorDTO> entityList = monitoringDao.getMonitor();
        List<Long> folders = new ArrayList<>();
        List<Long> monitors = new ArrayList<>();
        List<Folder> folderList = new ArrayList<>();
        list.forEach(e -> {
            if (e.getLocalStatus() == 0 || e.getLocalStatus() == 4 || e.getLocalStatus() == 5)
                folders.add(e.getFolderId());
            if (e.getLocalStatus() == 0 || e.getLocalStatus() == 5 || e.getLocalStatus() == 1 || e.getLocalStatus() == 2) {
                Folder folder = new Folder();
                folder.setFolderName(e.getFolderName());
                folderList.add(folder);
            }
        });

        monitoring.deleteFolderAPI(folders);

        List<Folder> returnFolderList = monitoring.addFolder(folderList);
        if (returnFolderList != null) {
            final Map<String, Long> longMap = returnFolderList.stream().filter(Objects::nonNull).collect(Collectors.toMap(Folder::getFolderName, Folder::getFolderId));
            Map<Long, Long> map = list.stream().map(o -> Maps.immutableEntry(o.getFolderId(), longMap.get(o.getFolderName()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            List<Monitor> monitorList = new ArrayList<>();
            entityList.forEach(e -> {
                if (e.getLocalstatus() == 0 || e.getLocalstatus() == 4 || e.getLocalstatus() == 5)
                    monitors.add(e.getMonitorId());
                if (e.getLocalstatus() == 0 || e.getLocalstatus() == 1 || e.getLocalstatus() == 2 || e.getLocalstatus() == 4) {
                    Monitor monitor = new Monitor();
                    monitor.setFolderId(map.get(e.getFolderId()));
                    monitor.setAdgroupId(e.getAdgroupId());
                    monitor.setCampaignId(e.getCampaignId());
                    monitor.setId(e.getAclid());
                    monitorList.add(monitor);
                }
            });
            monitoring.deleteMonitorWordAPI(monitors);
            List<Monitor> monitorList1 = monitoring.addMonitorWordAPI(monitorList);
            if (monitorList1 == null && returnFolderList == null) return -1;
            else return 0;
        }else{
            return 0;
        }
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

    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyMMddHHmmssSSS");
        while (true)
            System.out.println(String.valueOf(new Date().getTime()) + String.valueOf((int) (Math.random() * 10)));
    }
}
