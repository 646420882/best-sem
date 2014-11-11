package com.perfect.entity;

import com.perfect.mongodb.utils.EntityConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by XiaoWei on 2014/11/11.
 */
@Document(collection = EntityConstants.SYS_CENSUS)
public class CensusEntity {
    @Id
    private String id;
    @Field(value = "uid")
    private String uuid;
    @Field(value = "sys")
    private String system;
    @Field(value = "brw")
    private String browser;
    @Field(value = "res")
    private String resolution;
    @Field(value = "sc")
    private boolean supportCookie;
    @Field(value = "sj")
    private boolean supportJava;
    @Field(value = "bit")
    private String bit;
    @Field(value = "fl")
    private String flash;
    @Field(value = "ti")
    private String time;
    @Field(value = "lp")
    private String  lastPage;
    @Field(value = "ip")
    private String ip;
    @Field(value = "are")
    private String area;
    @Field(value = "ope")
    private Integer operate;

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
}
