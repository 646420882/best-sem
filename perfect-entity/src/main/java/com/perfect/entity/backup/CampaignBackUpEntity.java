package com.perfect.entity.backup;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.entity.campaign.CampaignEntity;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by john on 2014/9/16.
 */
@Document(collection = MongoEntityConstants.BAK_CAMPAIGN)
public class CampaignBackUpEntity extends CampaignEntity{
}
