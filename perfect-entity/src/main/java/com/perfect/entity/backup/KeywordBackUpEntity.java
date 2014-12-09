package com.perfect.entity.backup;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.entity.keyword.KeywordEntity;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by XiaoWei on 2014/9/9.
 */
@Document(collection = MongoEntityConstants.BAK_KEYWORD)
public class KeywordBackUpEntity extends KeywordEntity {
}
