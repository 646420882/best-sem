package com.perfect.service.impl;

import com.mongodb.WriteResult;
import com.perfect.api.baidu.PromotionMonitoring;
import com.perfect.api.baidu.QualityTypeService;
import com.perfect.autosdk.sms.v3.Folder;
import com.perfect.autosdk.sms.v3.QualityType;
import com.perfect.core.AppContext;
import com.perfect.dao.AccountManageDAO;
import com.perfect.dao.MonitoringDao;
import com.perfect.dto.KeywordDTO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.FolderEntity;
import com.perfect.entity.FolderMonitorEntity;
import com.perfect.service.AssistantKeywordService;
import com.perfect.service.MonitoringService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private QualityTypeService qualityTypeService;
    @Resource
    private AccountManageDAO<BaiduAccountInfoEntity> accountManageDAO;

    @Override
    public List<FolderEntity> getFolder() {
        List<FolderEntity> folderEntities = monitoringDao.getForlder();
        for (FolderEntity folderEntity : folderEntities) {
            List<FolderMonitorEntity> monitor = monitoringDao.getMonitorId(folderEntity.getFolderId());
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
        List<FolderEntity> list = monitoringDao.getForlder();
        if(list.size() < 20){
            PromotionMonitoring monitoring = getUserInfo();
            Folder folder = new Folder();
            List<Folder> strings = new ArrayList<>();
            folder.setFolderName(folderName);
            strings.add(folder);

            int  Judge = 0;
            List<Folder> folderList = new ArrayList<>();

            try{
                folderList = monitoring.addFolder(strings);
                Judge = 1;
            } catch (Exception e){
                Judge = 0;
            }
            if(Judge == 1){
                for(Folder folder1 : folderList){
                    FolderEntity folderEntity = new FolderEntity();
                    folderEntity.setAccountId(AppContext.getAccountId());
                    folderEntity.setFolderName(folder1.getFolderName());
                    folderEntity.setFolderId(folder1.getFolderId());
                    monitoringDao.addFolder(folderEntity);
                }
                return 1;
            }else {
                return 0;
            }
        }else{
            return -1;
        }
    }

    @Override
    public boolean deleteFolder(Long folderId) {

        List<FolderMonitorEntity> monitorEntities = monitoringDao.getMonitorId(folderId);
        boolean returnFolder = false;
        int i;
        if(monitorEntities.size()>0){
            WriteResult remove = monitoringDao.deleteMonitor(folderId);
            if(remove.getN() == 1){
                WriteResult folderRemove = monitoringDao.deleteFoder(folderId);
                i = folderRemove.getN();
            }else{
                i = 0;
            }
        }else{
            WriteResult folderRemove = monitoringDao.deleteFoder(folderId);
            i = folderRemove.getN();
        }
        if(i == 1){
            returnFolder = true;
        }else{
            returnFolder = false;
        }

        return returnFolder;
    }

    @Override
    public List<KeywordDTO> getMonitor() {
        List<FolderEntity> folderEntities = monitoringDao.getForlder();
        List<KeywordDTO> returnkeywordDTO = new ArrayList<>();
        for (FolderEntity folderEntity : folderEntities) {

            List<Long> longs = new ArrayList<>();

            List<FolderMonitorEntity> monitor = monitoringDao.getMonitorId(folderEntity.getFolderId());

            for (FolderMonitorEntity monitorEntity : monitor) {
                longs.add(monitorEntity.getAclid());
            }

            List<KeywordDTO> keywordDTOs = assistantKeywordService.getKeywordListByIds(longs);
            List<QualityType> qualityType = qualityTypeService.getQualityType(longs);
            for (FolderMonitorEntity aLong:monitor){
                for(KeywordDTO dto: keywordDTOs){
                    //设置监控文件夹信息
                    if(aLong.getAclid().intValue() == dto.getObject().getKeywordId().intValue()){
                        dto.setMonitorId(aLong.getMonitorId());
                        dto.setFolderId(aLong.getFolderId());
                        for(FolderEntity folderEntity1:folderEntities){
                            if(folderEntity1.getFolderId().intValue() == aLong.getFolderId().intValue()){
                                dto.setFolderName(folderEntity1.getFolderName());
                            }
                        }
                    }
                    //设置质量度信息
                    for (QualityType qualityType1 :qualityType){
                        if(qualityType1.getId().intValue() == dto.getObject().getKeywordId().intValue()){
                            dto.setQuality(qualityType1.getQuality());
                            dto.setMobileQuality(qualityType1.getMobileQuality());
                        }
                    }
                }

            }
            returnkeywordDTO.addAll(keywordDTOs);
        }
        return returnkeywordDTO;
    }

    @Override
    public List<KeywordDTO> getMonitorId(Long folderId) {
        List<FolderEntity> folderEntities = monitoringDao.getForlderId(folderId);
        List<KeywordDTO> keywordDTOs = new ArrayList<>();
        for (FolderEntity folderEntity : folderEntities) {

            List<Long> longs = new ArrayList<>();

            List<FolderMonitorEntity> monitor = monitoringDao.getMonitorId(folderId);

            for (FolderMonitorEntity monitorEntity : monitor) {
                longs.add(monitorEntity.getAclid());
            }

            keywordDTOs = assistantKeywordService.getKeywordListByIds(longs);
            List<QualityType> qualityType = qualityTypeService.getQualityType(longs);
            for (FolderMonitorEntity aLong:monitor){
                for(KeywordDTO dto: keywordDTOs){
                    //设置监控文件夹信息
                    if(aLong.getAclid().intValue() == dto.getObject().getKeywordId().intValue()){
                        dto.setMonitorId(aLong.getMonitorId());
                        dto.setFolderId(aLong.getFolderId());
                        for(FolderEntity folderEntity1:folderEntities){
                            if(folderEntity1.getFolderId().intValue() == aLong.getFolderId().intValue()){
                                dto.setFolderName(folderEntity1.getFolderName());
                            }
                        }
                    }
                    //设置质量度信息
                    for (QualityType qualityType1 :qualityType){
                        if(qualityType1.getId().intValue() == dto.getObject().getKeywordId().intValue()){
                            dto.setQuality(qualityType1.getQuality());
                            dto.setMobileQuality(qualityType1.getMobileQuality());
                        }
                    }
                }

            }
        }
        return keywordDTOs;
    }

    @Override
    public boolean deleteMonitorId(Long MonitorId) {
        boolean returnFolder = false;
        WriteResult folderRemove = monitoringDao.deleteMonitorId(MonitorId);
        if(folderRemove.getN() == 1){
            returnFolder = true;
        }else{
            returnFolder = false;
        }
        return returnFolder;
    }

    @Override
    public int addMonitorId(Long folderID, Long campaignId, Long adgroupId, Long acliId) {

        List<FolderMonitorEntity> entityList = monitoringDao.getMonitor();
        int i = 0;

            if(entityList.size() < 2000){
                FolderMonitorEntity monitorEntity = new FolderMonitorEntity();
                //sid 当前时间的的毫秒数 加上一个一位的随机数   是字符串意义上的加法
                String sid = String.valueOf(new Date().getTime())+String.valueOf((int)(Math.random()*10));
                monitorEntity.setMonitorId(Long.parseLong(sid));
                monitorEntity.setFolderId(folderID);
                monitorEntity.setCampaignId(campaignId);
                monitorEntity.setAdgroupId(adgroupId);
                monitorEntity.setAclid(acliId);
                monitorEntity.setAccountId(AppContext.getAccountId());
                monitorEntity.setType(11);

                try{
                    monitoringDao.addMonitor(monitorEntity);
                    i=1;
                }catch (Exception e){
                    i=0;
                }
            }else{
                i=-1;
            }
        return i;
    }


    /**
     * 获取当前登陆用户基本信息和对应API
     *
     * @return
     */
    public PromotionMonitoring getUserInfo() {
        Long accid = AppContext.getAccountId();
        BaiduAccountInfoEntity entity = accountManageDAO.findByBaiduUserId(accid);
        PromotionMonitoring Monitoring = new PromotionMonitoring(entity);
        return Monitoring;
    }

    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyMMddHHmmssSSS");
        while (true)
            System.out.println(String.valueOf(new Date().getTime())+String.valueOf((int)(Math.random()*10)));
    }
}
