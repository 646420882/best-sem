package com.perfect.db.mongodb.impl;

import com.perfect.dao.report.AsynchronousReportDAO;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.account.*;
import com.perfect.dto.keyword.KeywordReportDTO;
import com.perfect.entity.account.AccountReportEntity;
import com.perfect.entity.report.*;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.mongodb.DBNameUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.perfect.commons.constants.MongoEntityConstants.TBL_ACCOUNT_REPORT;

/**
 * Created by baizz on 2014-08-07.
 * 2014-11-26 refactor
 */
@Repository("asynchronousReportDAO")
public class AsynchronousReportDAOImpl implements AsynchronousReportDAO {

    @Override
    public void getAccountReportData(List<AccountReportDTO> accountReportDTOs, SystemUserDTO systemUser, String dateStr) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<AccountReportEntity> reportDTOs = ObjectUtils.convert(accountReportDTOs, AccountReportEntity.class);
        mongoTemplate.insert(reportDTOs, TBL_ACCOUNT_REPORT);
    }

    @Override
    public void getCampaignReportData(List<CampaignReportDTO> campaignReportDTOs, SystemUserDTO systemUser, String dateStr) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<CampaignReportEntity> reportDTOs = ObjectUtils.convert(campaignReportDTOs, CampaignReportEntity.class);
        mongoTemplate.insert(reportDTOs, dateStr + "-campaign");
    }

    @Override
    public void getAdgroupReportData(List<AdgroupReportDTO> adgroupReportDTOs, SystemUserDTO systemUser, String dateStr) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<AdgroupReportEntity> reportDTOs = ObjectUtils.convert(adgroupReportDTOs, AdgroupReportEntity.class);
        mongoTemplate.insert(reportDTOs, dateStr + "-adgroup");
    }

    @Override
    public void getCreativeReportData(List<CreativeReportDTO> creativeReportDTOs, SystemUserDTO systemUser, String dateStr) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<CreativeReportEntity> reportDTOs = ObjectUtils.convert(creativeReportDTOs, CreativeReportEntity.class);
        mongoTemplate.insert(reportDTOs, dateStr + "-creative");
    }

    @Override
    public void getKeywordReportData(List<KeywordReportDTO> keywordReportDTOs, SystemUserDTO systemUser, String dateStr) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<KeywordReportEntity> reportDTOs = ObjectUtils.convert(keywordReportDTOs, KeywordReportEntity.class);
        mongoTemplate.insert(reportDTOs, dateStr + "-keyword");
    }

    @Override
    public void getRegionReportData(List<RegionReportDTO> regionReportDTOs, SystemUserDTO systemUser, String dateStr) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<RegionReportEntity> reportDTOs = ObjectUtils.convert(regionReportDTOs, RegionReportEntity.class);
        mongoTemplate.insert(reportDTOs, dateStr + "-region");
    }
}