package com.perfect.schedule.task.execute;

import com.perfect.autosdk.core.CommonService;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.service.SysCampaignService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by vbzer_000 on 2014/9/1.
 */
public class BiddingSubTaskFactory {

    private SysCampaignService sysCampaignService;


    public BiddingSubTask getObject(String userName, CommonService service, BaiduAccountInfoEntity accountInfoEntity,
                                    BiddingRuleEntity biddingRuleEntity, KeywordEntity keywordEntity) throws Exception {
//        BiddingSubTask biddingSubTask = new BiddingSubTask(AppContext.getUser(), service,
//                sysCampaignService, accountInfoEntity, biddingRuleEntity, keywordEntity);

        return null;
    }

}
