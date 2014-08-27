package com.perfect.entity.bidding;

import com.perfect.entity.AccountIdEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.perfect.mongodb.utils.EntityConstants.TBL_BIDDINGRULE;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
@Document(collection = TBL_BIDDINGRULE)
public class BiddingRuleEntity extends AccountIdEntity {

    @Id
    private ObjectId id;

    @Field("cid")
    private long cid;

    @Field("agid")
    private long agid;

    @Field("kwid")
    private long keywordId;

    @Field("kw")
    private String keyword;

    @Field("stgy")
    private StrategyEntity strategyEntity;

    @Field("cp")
    private double currentPrice;

    @Field("cpos")
    private int currentPos;

    @Field("next")
    private long nextTime;

    @Field("priority")
    private int priority;

    @Field("ebl")
    private boolean enabled;


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public long getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(long keywordId) {
        this.keywordId = keywordId;
    }

    public StrategyEntity getStrategyEntity() {
        return strategyEntity;
    }

    public void setStrategyEntity(StrategyEntity strategyEntity) {
        this.strategyEntity = strategyEntity;
    }

    public long getNextTime() {
        return nextTime;
    }

    public void setNextTime(long nextTime) {
        this.nextTime = nextTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public long getAgid() {
        return agid;
    }

    public void setAgid(long agid) {
        this.agid = agid;
    }
}
