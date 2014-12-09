package com.perfect.dto.monitor;

import com.perfect.dto.keyword.KeywordInfoDTO;

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
    private List<MonitorDTO> monitors;

    //文件夹具体数据
    private List<KeywordInfoDTO> keywordByIds;


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

    public List<MonitorDTO> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<MonitorDTO> monitors) {
        this.monitors = monitors;
    }

    public List<KeywordInfoDTO> getKeywordByIds() {
        return keywordByIds;
    }

    public void setKeywordByIds(List<KeywordInfoDTO> keywordByIds) {
        this.keywordByIds = keywordByIds;
    }
}
