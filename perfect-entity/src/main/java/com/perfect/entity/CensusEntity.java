package com.perfect.entity;

import com.perfect.commons.constants.MongoEntityConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Created by XiaoWei on 2014/11/11.
 */
@Document(collection = MongoEntityConstants.SYS_CENSUS)
public class CensusEntity {
    @Id
    private String id;
    @Field(value = "uid")
    private String uuid;
    @Field(value = "sys")
    private String system;//操作系统
    @Field(value = "brw")
    private String browser;//浏览器
    @Field(value = "res")
    private String resolution;//分辨率
    @Field(value = "sc")
    private boolean supportCookie;//是否支持cookie
    @Field(value = "sj")
    private boolean supportJava;//是否支持java
    @Field(value = "bit")
    private String bit;//位操作数
    @Field(value = "fl")
    private String flash;//flash版本
    @Field(value = "dat")
    private Date date;//访问日期
    @Field(value = "ti")
    private String time;//访问时间
    @Field(value = "tp")
    private String intoPage;//目标页面
    @Field(value = "lp")
    private String  lastPage;//停留页面
    @Field(value = "ip")
    private String ip;//ip地址
    @Field(value = "are")
    private String area;//地区
    @Field(value = "ope")
    private Integer operate;//使用设备
    @Field(value = "up")
    private Integer userType;//新老客户标识


    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIntoPage() {
        return intoPage;
    }

    public void setIntoPage(String intoPage) {
        this.intoPage = intoPage;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "CensusEntity{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
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
                '}';
    }
}
