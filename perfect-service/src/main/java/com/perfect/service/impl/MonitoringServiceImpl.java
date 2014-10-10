package com.perfect.service.impl;

import com.perfect.api.baidu.QualityTypeService;
import com.perfect.autosdk.sms.v3.QualityType;
import com.perfect.dao.MonitoringDao;
import com.perfect.dto.KeywordDTO;
import com.perfect.entity.FolderEntity;
import com.perfect.entity.FolderMonitorEntity;
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
    @Resource
    private AssistantKeywordService assistantKeywordService;
    @Resource
    private QualityTypeService qualityTypeService;

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
    public List<KeywordDTO> getMonitor() {
        List<FolderEntity> folderEntities = monitoringDao.getForlder();
        List<KeywordDTO> keywordDTOs = new ArrayList<>();
        for (FolderEntity folderEntity : folderEntities) {

            List<Long> longs = new ArrayList<>();

            List<FolderMonitorEntity> monitor = monitoringDao.getMonitorId(folderEntity.getFolderId());

            for (FolderMonitorEntity monitorEntity : monitor) {
                longs.add(monitorEntity.getAclid());
            }

            keywordDTOs = assistantKeywordService.getKeywordListByIds(longs);
            List<QualityType> qualityType = qualityTypeService.getQualityType(longs);
            for (FolderMonitorEntity aLong:monitor){
                for(KeywordDTO dto: keywordDTOs){
                    //设置监控文件夹信息
                    if(aLong.getAclid().intValue() == dto.getObject().getKeywordId().intValue()){
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
}
