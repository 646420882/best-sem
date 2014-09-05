package com.perfect.entity.backup;

import com.perfect.entity.AdgroupEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.perfect.mongodb.utils.EntityConstants.BAK_ADGROUP;

/**
 * Created by XiaoWei on 2014/9/4.
 */
@Document(collection=BAK_ADGROUP)
public class AdgroupBackUpEntity extends com.perfect.entity.AdgroupEntity {
}
