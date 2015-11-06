package com.perfect.entity.bidding;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.entity.account.AccountIdEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 * @description 智能竞价规则实体类
 */
@Document(collection = MongoEntityConstants.TBL_BIDDINGRULE)
public class BiddingRuleEntity extends AccountIdEntity {

    // TODO create a price field to save the price when the rule is out of running status

    @Id
    private String id;

    @Indexed(unique = true)
    @Field(MongoEntityConstants.KEYWORD_ID)
    private long keywordId;                             // 关键词ID

    @Field(MongoEntityConstants.NAME)
    private String keyword;                             // 关键词名称

    @Field("stgy")
    private StrategyEntity strategyEntity;              // 出价策略

    @Field("mt")
    private Integer matchType;                          // 匹配类型

    @Field("pt")
    private Integer phraseType;                         // 短语类型

    @Field("cp")
    private BigDecimal currentPrice;                    // 当前出价

    @Field("priority")
    private int priority;                               // 优先级

    @Field("ebl")
    private boolean enabled;                            // 出价规则是否可用

    @Field("r")
    private boolean running;                            // 是否处于运行状态

    @Field("nxt")
    private long next;                                  // 下一次运行时间

    @Field("ct")
    private int currentTimes = -1;                      // 当前运行时间

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
