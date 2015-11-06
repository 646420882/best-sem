package com.perfect.entity.sys;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.entity.account.AccountIdEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "logs")
public class LogEntity extends AccountIdEntity {

    @Id
    private String id;//mongoid

    @Field(MongoEntityConstants.BAIDU_ID)
    private Long bid;//该id表示百度id，如果下面的type为keyword，该值为关键词百度id，如果type为creative，该值为创意的百度id

    @Field(MongoEntityConstants.OBJ_ID)
    private String oid;//该id表示百度id，如果下面的type为keyword，该值为关键词本地mongoid，如果type为creative，该值为创意的mongoid

    private String type;//日志类型

    private int opt;//操作类型，增加，删除，修改

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOpt() {
        return opt;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
