package com.perfect.dto.bidding;

import com.perfect.dto.account.AccountIdDTO;

import java.math.BigDecimal;

/**
 * Created by yousheng on 14/12/1.
 */
public class BiddingRuleDTO extends AccountIdDTO {

    // TODO create a price field to save the price when the rule is out of running status

    private long keywordId;

    private String keyword;

    private StrategyDTO strategyDTO;

    private Integer matchType;

    private Integer phraseType;

    private BigDecimal currentPrice;

    private int priority;

    private boolean enabled;

    private boolean running;

    private long next;

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


    public long getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(long keywordId) {
        this.keywordId = keywordId;
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

    public StrategyDTO getStrategyDTO() {
        return strategyDTO;
    }

    public void setStrategyDTO(StrategyDTO strategyDTO) {
        this.strategyDTO = strategyDTO;
    }
}
