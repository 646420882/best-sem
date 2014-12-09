package com.perfect.dto.count;

/**
 * Created by XiaoWei on 2014/12/3.
 */
public class CensusCfgDTO {
    private String id;
    private String url;
    private int status;//设置的Url状态，0为监控状态的Url
    private String ip;
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
        return "CensusCfgDTO{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", ip='" + ip + '\'' +
                ", urlDesc='" + urlDesc + '\'' +
                '}';
    }
}
