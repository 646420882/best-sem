package com.perfect.dto.count;

import com.perfect.dto.BaseDTO;

import java.util.Date;

/**
 * Created by XiaoWei on 2014/11/26.
 */
public class CensusDTO extends BaseDTO {
    private String uuid;
    private String system;//操作系统
    private String browser;//浏览器
    private String resolution;//分辨率
    private boolean supportCookie;//是否支持cookie
    private boolean supportJava;//是否支持java
    private String bit;//位操作数
    private String flash;//flash版本
    private Date date;//访问日期
    private String time;//访问时间
    private String intoPage;//目标页面
    private String lastPage;//停留页面
    private String ip;//ip地址
    private String area;//地区
    private Integer operate;//使用设备
    private Integer userType;//新老客户标识
    private String searchEngine;//搜索引擎

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public boolean isSupportCookie() {
        return supportCookie;
    }

    public void setSupportCookie(boolean supportCookie) {
        this.supportCookie = supportCookie;
    }

    public boolean isSupportJava() {
        return supportJava;
    }

    public void setSupportJava(boolean supportJava) {
        this.supportJava = supportJava;
    }

    public String getBit() {
        return bit;
    }

    public void setBit(String bit) {
        this.bit = bit;
    }

    public String getFlash() {
        return flash;
    }

    public void setFlash(String flash) {
        this.flash = flash;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIntoPage() {
        return intoPage;
    }

    public void setIntoPage(String intoPage) {
        this.intoPage = intoPage;
    }

    public String getLastPage() {
        return lastPage;
    }

    public void setLastPage(String lastPage) {
        this.lastPage = lastPage;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getOperate() {
        return operate;
    }

    public void setOperate(Integer operate) {
        this.operate = operate;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getSearchEngine() {
        return searchEngine;
    }

    public void setSearchEngine(String searchEngine) {
        this.searchEngine = searchEngine;
    }

    @Override
    public String toString() {
        return "CensusDTO{" +
                "uuid='" + uuid + '\'' +
                ", system='" + system + '\'' +
                ", browser='" + browser + '\'' +
                ", resolution='" + resolution + '\'' +
                ", supportCookie=" + supportCookie +
                ", supportJava=" + supportJava +
                ", bit='" + bit + '\'' +
                ", flash='" + flash + '\'' +
                ", date=" + date +
                ", time='" + time + '\'' +
                ", intoPage='" + intoPage + '\'' +
                ", lastPage='" + lastPage + '\'' +
                ", ip='" + ip + '\'' +
                ", area='" + area + '\'' +
                ", operate=" + operate +
                ", userType=" + userType +
                ", searchEngine='" + searchEngine + '\'' +
                '}';
    }
}
