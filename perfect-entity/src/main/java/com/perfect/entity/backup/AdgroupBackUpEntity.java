package com.perfect.entity.backup;

import com.perfect.commons.constants.MongoEntityConstants;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by XiaoWei on 2014/9/4.
 */
@Document(collection = MongoEntityConstants.BAK_ADGROUP)
public class AdgroupBackUpEntity extends com.perfect.entity.AdgroupEntity {
}
