package com.perfect.schedule.task.conf;

/**
 * Created by yousheng on 2014/8/1.
 *
 * @author yousheng
 */
public class StrategyConfig {

    private int assignNum;

    private int numOfSingleServer;

    private String[] ipList;

    public int getAssignNum() {
        return assignNum;
    }

    public void setAssignNum(int assignNum) {
        this.assignNum = assignNum;
    }

    public int getNumOfSingleServer() {
        return numOfSingleServer;
    }

    public void setNumOfSingleServer(int numOfSingleServer) {
        this.numOfSingleServer = numOfSingleServer;
    }

    public String[] getIpList() {
        return ipList;
    }

    public void setIpList(String[] ipList) {
        this.ipList = ipList;
    }
}
