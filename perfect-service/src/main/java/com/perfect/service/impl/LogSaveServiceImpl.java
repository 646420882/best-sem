package com.perfect.service.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.KeywordBackUpDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.log.filters.field.enums.KeyWordEnum;
import com.perfect.log.filters.field.enums.OptContentEnum;
import com.perfect.log.model.OperationRecordModel;
import com.perfect.log.util.LogOptUtil;
import com.perfect.service.AdgroupService;
import com.perfect.service.LogSaveService;
import com.perfect.utils.OperationRecordModelBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xiaowei
 * @title LogSaveServiceImpl
 * @package com.perfect.service.impl
 * @description
 * @update 2015年12月08日. 下午6:46
 */
@Service
public class LogSaveServiceImpl implements LogSaveService {

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private KeywordDAO keywordDAO;

    @Override
    public void log(long cid, long adgroupid, long objid, String objName, int optType, int optConent, String optObj, long userId, String planName, String unitName, String oldValue, String newValue, int optLevel) {
        OperationRecordModel model = new OperationRecordModel();
        model.setPlanId(cid);
        model.setUnitId(adgroupid);
        model.setUserId(userId);
        model.setOptType(optType);
        model.setOptObj(optObj);
        model.setPlanName(planName);
        model.setUnitName(unitName);
        model.setOptTime(System.currentTimeMillis());
        model.setOldValue(oldValue);
        model.setNewValue(newValue);
        model.setOptLevel(optLevel);

        // send log
    }

    @Override
    public void log(KeywordDTO oldObj, KeywordDTO newObj) {

    }


    @Override
    public void ormByKeyword(KeywordDTO keywordDTO, OperationRecordModelBuilder builder) {
        AdgroupDTO adgroupDTO = adgroupDAO.findOne(keywordDTO.getAdgroupId());
        CampaignDTO campaignDTO = campaignDAO.findOne(adgroupDTO.getCampaignId());
        fillCommonData(builder, campaignDTO, adgroupDTO);
    }


    private OperationRecordModelBuilder fillCommonData(OperationRecordModelBuilder builder, CampaignDTO campaignDTO, AdgroupDTO adgroupDTO) {
        return builder.setUserId(AppContext.getAccountId()).setUnitId(adgroupDTO.getAdgroupId()).setUnitName(adgroupDTO.getAdgroupName())
                .setPlanId(campaignDTO.getCampaignId()).setPlanName(campaignDTO.getCampaignName());

    }



    @Override
    public void log(CampaignDTO oldObj, CampaignDTO newObj) {

    }

    @Override
    public void log(AdgroupDTO oldObj, AdgroupDTO newObj) {

    }

    @Override
    public void log(CreativeDTO oldObj, CreativeDTO newObj) {

    }


    private void save(OperationRecordModel model) {
        LogOptUtil.saveLogs(model);
    }
}
