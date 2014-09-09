package com.perfect.entity.backup;

import com.perfect.entity.KeywordEntity;
import com.perfect.mongodb.utils.EntityConstants;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by XiaoWei on 2014/9/9.
 */
@Document(collection = EntityConstants.BAK_KEYWORD)
public class KeyWordBackUpEntity extends KeywordEntity {
}
