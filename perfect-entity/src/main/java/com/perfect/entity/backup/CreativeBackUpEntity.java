package com.perfect.entity.backup;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.entity.CreativeEntity;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * Created by XiaoWei on 2014/9/4.
 */
@Document(collection= MongoEntityConstants.BAK_CREATIVE)
public class CreativeBackUpEntity extends CreativeEntity {
}
