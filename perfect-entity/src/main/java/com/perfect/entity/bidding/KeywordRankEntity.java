package com.perfect.entity.bidding;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.entity.account.AccountIdEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

/**
 * Created by vbzer_000 on 2014/8/29.
 */
@Document(collection = "kw_rank")
public class KeywordRankEntity extends AccountIdEntity {

    @Id
    private String id;

    @Field(MongoEntityConstants.KEYWORD_ID)
    private String kwid;

    @Field("name")
    private String name;

    @Field("d")
    private int device;

    @Field
    private long time;

    @Field("tr")
    private Map<Integer, Integer> targetRank;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Map<Integer, Integer> getTargetRank() {
        return targetRank;
    }

    public void setTargetRank(Map<Integer, Integer> targetRank) {
        this.targetRank = targetRank;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKwid() {
        return kwid;
    }

    public void setKwid(String kwid) {
        this.kwid = kwid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
