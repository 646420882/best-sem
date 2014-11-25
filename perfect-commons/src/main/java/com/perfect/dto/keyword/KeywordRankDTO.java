package com.perfect.dto.keyword;

import java.util.HashMap;

/**
 * Created by yousheng on 14/11/24.
 */
public class KeywordRankDTO {
    private String kwid;
    private String name;
    private HashMap<Integer, Integer> targetRank;
    private int device;
    private long time;

    public void setKwid(String kwid) {
        this.kwid = kwid;
    }

    public String getKwid() {
        return kwid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTargetRank(HashMap<Integer, Integer> targetRank) {
        this.targetRank = targetRank;
    }

    public HashMap<Integer, Integer> getTargetRank() {
        return targetRank;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public int getDevice() {
        return device;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
