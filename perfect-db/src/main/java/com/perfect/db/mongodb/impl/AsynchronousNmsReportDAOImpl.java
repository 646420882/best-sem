package com.perfect.db.mongodb.impl;

import com.perfect.dao.report.AsynchronousNmsReportDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.account.NmsAccountReportDTO;
import com.perfect.dto.account.NmsAdReportDTO;
import com.perfect.dto.account.NmsCampaignReportDTO;
import com.perfect.dto.account.NmsGroupReportDTO;
import com.perfect.entity.report.NmsAccountReportEntity;
import com.perfect.entity.report.NmsAdReportEntity;
import com.perfect.entity.report.NmsCampaignReportEntity;
import com.perfect.entity.report.NmsGroupReportEntity;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.mongodb.DBNameUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dolphineor on 2015-7-21.
 */
@Repository("asynchronousNmsReportDAO")
public class AsynchronousNmsReportDAOImpl extends AbstractUserBaseDAOImpl<NmsAccountReportDTO, Long> implements AsynchronousNmsReportDAO {
    @Override
    public void getNmsAccountReportData(List<NmsAccountReportDTO> nmsAccountReportDtos, SystemUserDTO systemUser, String dateStr, String baiduUserName) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<NmsAccountReportEntity> accountReportEntities = ObjectUtils.convert(nmsAccountReportDtos, NmsAccountReportEntity.class);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = df.format(date);

        List<NmsAccountReportEntity> entities = null;
        if (!dateStr.equals(dateString)) {
            try {
                entities = mongoTemplate.find(Query.query(Criteria.where("date").is(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr)).and("acna").is(baiduUserName)), NmsAccountReportEntity.class);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (entities == null || entities.size() == 0) {
                mongoTemplate.insert(accountReportEntities, TBL_NMS_ACCOUNT_REPORT);
            }
        }
    }

    @Override
    public void getNmsCampaignReportData(List<NmsCampaignReportDTO> nmsCampaignReportDTOs, SystemUserDTO systemUser, String dateStr) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<NmsCampaignReportEntity> campaignReportEntities = new ArrayList<>(ObjectUtils.convert(nmsCampaignReportDTOs, NmsCampaignReportEntity.class));
        List<NmsCampaignReportEntity> campaignReportEntities1;
        if (mongoTemplate.collectionExists(dateStr + "-nms-campaign")) {
            campaignReportEntities1 = new ArrayList<>(mongoTemplate.find(new Query(), NmsCampaignReportEntity.class, dateStr + "-nms-campaign"));
            //campaignReportEntities.addAll(campaignReportEntities1);
            mongoTemplate.dropCollection(dateStr + "-nms-campaign");
        }

        mongoTemplate.insert(campaignReportEntities, dateStr + "-nms-campaign");

    }

    @Override
    public void getNmsGroupReportData(List<NmsGroupReportDTO> nmsGroupReportDtos, SystemUserDTO systemUser, String dateStr) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<NmsGroupReportEntity> adgroupReportEntities = new ArrayList<>(ObjectUtils.convert(nmsGroupReportDtos, NmsGroupReportEntity.class));
        List<NmsGroupReportEntity> adgroupReportEntities1;
        if (mongoTemplate.collectionExists(dateStr + "-nms-adgroup")) {
            adgroupReportEntities1 = new ArrayList<>(mongoTemplate.find(new Query(), NmsGroupReportEntity.class, dateStr + "-nms-adgroup"));
            //adgroupReportEntities.addAll(adgroupReportEntities1);
            mongoTemplate.dropCollection(dateStr + "-nms-adgroup");
        }

        mongoTemplate.insert(adgroupReportEntities, dateStr + "-nms-adgroup");
    }

    @Override
    public void getNmsAdReportData(List<NmsAdReportDTO> nmsAdReportDTOs, SystemUserDTO systemUser, String dateStr) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<NmsAdReportEntity> adReportEntities = new ArrayList<>(ObjectUtils.convert(nmsAdReportDTOs, NmsAdReportEntity.class));
        List<NmsAdReportEntity> adReportEntities1;
        if (mongoTemplate.collectionExists(dateStr + "-nms-creative")) {
            adReportEntities1 = new ArrayList<>(mongoTemplate.find(new Query(), NmsAdReportEntity.class, dateStr + "-nms-creative"));
            //adReportEntities.addAll(adReportEntities1);
            mongoTemplate.dropCollection(dateStr + "-nms-creative");
        }

        mongoTemplate.insert(adReportEntities, dateStr + "-nms-creative");
    }

    @Override
    public Class<NmsAccountReportEntity> getEntityClass() {
        return NmsAccountReportEntity.class;
    }

    @Override
    public Class<NmsAccountReportDTO> getDTOClass() {
        return NmsAccountReportDTO.class;
    }
}
