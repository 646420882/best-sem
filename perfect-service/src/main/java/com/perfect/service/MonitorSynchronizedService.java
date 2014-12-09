package com.perfect.service;

/**
 * Created by SubDong on 2014/9/12.
 */
public interface MonitorSynchronizedService {

    /**
     * 同步百度监控数据到本地
     *
     * @return
     */
    public int getSynchronized();


    /**
     * 本地数据上传更新到百度服务器
     *
     * @return
     */
    public int updateMonitorData();

}
