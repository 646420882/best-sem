package com.perfect.entity.backup;

import com.perfect.entity.CreativeEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.perfect.mongodb.utils.EntityConstants.*;
/**
 * Created by XiaoWei on 2014/9/4.
 */
@Document(collection=BAK_CREATIVE)
public class CreativeBackUpEntity extends CreativeEntity {
}
