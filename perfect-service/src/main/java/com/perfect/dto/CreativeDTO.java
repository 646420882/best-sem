package com.perfect.dto;

import com.perfect.autosdk.sms.v3.SublinkInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by baizz on 2014-08-18.
 */
public class CreativeDTO implements Serializable {


    private String title;

    private String description;

    private String descSource;

    private String url;

    private List<SublinkInfo> sublinkInfos;
    private String time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<SublinkInfo> getSublinkInfos() {
        return sublinkInfos;
    }

    public void setSublinkInfos(List<SublinkInfo> sublinkInfos) {
        this.sublinkInfos = sublinkInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreativeDTO that = (CreativeDTO) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (sublinkInfos != null ? !sublinkInfos.equals(that.sublinkInfos) : that.sublinkInfos != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (sublinkInfos != null ? sublinkInfos.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CreativeVOEntity{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", sublinkInfos=" + sublinkInfos +
                ", html =" + descSource +
                '}';
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getDescSource() {
        return descSource;
    }

    public void setDescSource(String descSource) {
        this.descSource = descSource;
    }
}
