package com.perfect.entity;

import com.perfect.commons.constants.MongoEntityConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by XiaoWei on 2014/12/2.
 */
@Document(collection = MongoEntityConstants.SYS_CENSUS_CONFIG)
public class CensusCfgEntity  {
    @Id
    private String id;
    private String url;
    @Field(value = "s")
    private int status;//设置的Url状态，0为监控状态的Url
    private String ip;
    @Field(value = "ud")
    private String urlDesc;//Url地址的中文描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrlDesc() {
        return urlDesc;
    }

    public void setUrlDesc(String urlDesc) {
        this.urlDesc = urlDesc;
    }

    @Override
    public String toString() {
        return "CensusCfgEntity{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", ip='" + ip + '\'' +
                ", urlDesc='" + urlDesc + '\'' +
                '}';
    }
}
