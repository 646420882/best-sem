package com.perfect.entity.backup;

import com.perfect.entity.CampaignEntity;
import com.perfect.mongodb.utils.EntityConstants;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by john on 2014/9/16.
 */
@Document(collection = EntityConstants.BAK_CAMPAIGN)
public class CampaignBackUpEntity extends CampaignEntity{
}
