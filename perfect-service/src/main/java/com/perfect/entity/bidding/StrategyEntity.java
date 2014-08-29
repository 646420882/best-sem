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
    private int device;

    @Field("max")
    private double maxPrice;

    @Field("min")
    private double minPrice;

    // 竞价策略
    @Field("m")
    private int mode;

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
    private int[] times;

    @Field("pos")
    private int position;

    @Field("rt")
    private int regionTarget;

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
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

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
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

    public int[] getTimes() {
        return times;
    }

    public void setTimes(int[] times) {
        this.times = times;
    }

    public int getRegionTarget() {
        return regionTarget;
    }

    public void setRegionTarget(int regionTarget) {
        this.regionTarget = regionTarget;
    }
}
