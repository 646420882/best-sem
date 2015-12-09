package com.perfect.service;

import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.log.model.OperationRecordModel;
import com.perfect.utils.OperationRecordModelBuilder;

/**
 * @author xiaowei
 * @title LogSaveService
 * @package com.perfect.service
 * @description
 * @update 2015年12月08日. 下午6:44
 */
public interface LogSaveService {
    void log(long cid, long adgroupod, long objid, String objName, int optType, int optConent, String optObj, long userId, String planName, String unitName, String oldValue, String newValue, int optLevel);

    void log(KeywordDTO oldObj, KeywordDTO newObj);

    void log(CampaignDTO oldObj, CampaignDTO newObj);

    void log(AdgroupDTO oldObj, AdgroupDTO newObj);

    void log(CreativeDTO oldObj, CreativeDTO newObj);


    void ormByKeyword(KeywordDTO keywordDTO, OperationRecordModelBuilder builder);


}
