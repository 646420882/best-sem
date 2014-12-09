package com.perfect.entity.bidding;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.entity.account.AccountIdEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
@Document(collection = MongoEntityConstants.TBL_BIDDINGRULE)
public class BiddingRuleEntity extends AccountIdEntity {

    // TODO create a price field to save the price when the rule is out of running status

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @Field(MongoEntityConstants.KEYWORD_ID)
    private long keywordId;

    @Field(MongoEntityConstants.NAME)
    private String keyword;

    @Field("stgy")
    private StrategyEntity strategyEntity;

    @Field("mt")
    private Integer matchType;

    @Field("pt")
    private Integer phraseType;

    @Field("cp")
    private BigDecimal currentPrice;

    @Field("priority")
    private int priority;

    @Field("ebl")
    private boolean enabled;

    @Field("r")
    private boolean running;

    @Field("nxt")
    private long next;

    @Field("ct")
    private int currentTimes = -1;

    public Integer getMatchType() {
        return matchType;
    }

    public void setMatchType(Integer matchType) {
        this.matchType = matchType;
    }

    public Integer getPhraseType() {
        return phraseType;
    }

    public void setPhraseType(Integer phraseType) {
        this.phraseType = phraseType;
    }

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

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setNext(long next) {
        this.next = next;
    }

    public long getNext() {
        return next;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setCurrentTimes(int currentTimes) {
        this.currentTimes = currentTimes;
    }

    public int getCurrentTimes() {
        return currentTimes;
    }
}
