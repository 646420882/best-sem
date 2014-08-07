package com.perfect.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
@Document(collection = "bid_rule")
public class BiddingRuleEntity {

    private String id;

    @Field("ts")
    private long timestamp;

    @DBRef
    @Field("kw")
    private KeywordEntity keyword;

    // 1 = economics
    // 2 = fast
    @Field("bdtype")
    private int strategy;

    //竞价规则 PC or 移动
    @Field("type")
    private int type;

    @Field("max")
    private double maxPrice;

    @Field("min")
    private double minPrice;

    @Field("step")
    private double priceStep;

    // 1 = slow 60
    // 2 = medium 30
    // 3 = fast 15
    @Field("intval")
    private int interval;


    // 1 = left 1
    // 2 = left 2-3
    // 3 = right 1-3
    // 4 = right + postion
    @Field("pstra")
    private int positionStrategy;

    // 1 = keep current postion
    // 2 = rollback
    @Field("failed")
    private int failedStrategy;

    @Field("pos")
    private int position;


    @Field("times")
    private List<TimeRange> times;

    @Field("start")
    private long startTime;

    @Field("end")
    private long endTime;

    @Field("priority")
    private int priority;


    class TimeRange {
        private short startTime;
        private short endTime;

        public short getStartTime() {
            return startTime;
        }

        public void setStartTime(short startTime) {
            this.startTime = startTime;
        }

        public short getEndTime() {
            return endTime;
        }

        public void setEndTime(short endTime) {
            this.endTime = endTime;
        }
    }

    public void addRange(short start, short end) {
        TimeRange tr = new TimeRange();
        tr.startTime = start;
        tr.endTime = end;

        if (times == null) {
            times = new ArrayList<TimeRange>(1);
        }
        times.add(tr);
    }

    public void clearTimes() {
        if (times != null) {
            times.clear();
        }
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public KeywordEntity getKeyword() {
        return keyword;
    }

    public void setKeyword(KeywordEntity keyword) {
        this.keyword = keyword;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getPriceStep() {
        return priceStep;
    }

    public void setPriceStep(double priceStep) {
        this.priceStep = priceStep;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPositionStrategy() {
        return positionStrategy;
    }

    public void setPositionStrategy(int positionStrategy) {
        this.positionStrategy = positionStrategy;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
