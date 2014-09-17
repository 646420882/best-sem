package com.perfect.dto;

import com.perfect.autosdk.sms.v3.Monitor;
import com.perfect.entity.KeywordEntity;

import java.util.List;

/**
 * Created by SubDong on 2014/9/12.
 */
public class FolderDataAllDTO {
    //监控文件夹ID
    private Long folderId;

    //监控文件夹名字
    private String name;

    //文件夹内的数据
    private List<Monitor> monitors;

    //文件夹具体数据
    private List<KeywordEntity> keywordByIds;


    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Monitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<Monitor> monitors) {
        this.monitors = monitors;
    }

    public List<KeywordEntity> getKeywordByIds() {
        return keywordByIds;
    }

    public void setKeywordByIds(List<KeywordEntity> keywordByIds) {
        this.keywordByIds = keywordByIds;
    }
}
