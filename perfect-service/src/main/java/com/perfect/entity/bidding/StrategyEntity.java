package com.perfect.entity.bidding;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Created by yousheng on 2014/8/14.
 *
 * @author yousheng
 */
public class StrategyEntity {

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

    @Field("spd")
    private int spd;

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

    @Field("t")
    private List<Integer> time;

    @Field("pos")
    private int position;

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getPositionStrategy() {
        return positionStrategy;
    }

    public void setPositionStrategy(int positionStrategy) {
        this.positionStrategy = positionStrategy;
    }

    public int getFailedStrategy() {
        return failedStrategy;
    }

    public void setFailedStrategy(int failedStrategy) {
        this.failedStrategy = failedStrategy;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<Integer> getTime() {
        return time;
    }

    public void setTime(List<Integer> time) {
        this.time = time;
    }
}
