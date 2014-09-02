package com.perfect.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.perfect.mongodb.utils.EntityConstants.BAIDU_ID;
import static com.perfect.mongodb.utils.EntityConstants.OBJ_ID;

@Document(collection = "logs")
public class LogEntity extends AccountIdEntity {

    @Id
    private String id;

    @Field(BAIDU_ID)
    private Long bid;

    @Field(OBJ_ID)
    private String oid;

    private String type;

    private int opt;

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
