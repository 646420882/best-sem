package com.perfect.entity;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by SubDong on 2014/9/12.
 */
public class FolderEntity {
    //监控文件夹ID
    @Field("fdId")
    private Long folderId;
    //监控文件夹名称
    @Field("fdna")
    private String folderName;

    //用户ID
    @Field("acid")
    private Long accountId;

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "FolderEntity{" +
                "folderId=" + folderId +
                ", folderName='" + folderName + '\'' +
                ", accountId=" + accountId +
                '}';
    }
}
