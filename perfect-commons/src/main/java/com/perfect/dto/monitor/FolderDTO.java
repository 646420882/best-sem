package com.perfect.dto.monitor;

import com.perfect.dto.BaseDTO;

/**
 * Created by SubDong on 2014/11/26.
 */
public class FolderDTO extends BaseDTO {
    //监控文件夹ID
    private Long folderId;
    //监控文件夹名称
    private String folderName;
    //用户ID
    private Long accountId;

    private int countNumber;

    //修改状态 0/百度数据  1/本地添加  2/本地修改  3/本地删除 4/远程删除 5/远程修改
    private Integer localStatus;

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

    public int getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(int countNumber) {
        this.countNumber = countNumber;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getLocalStatus() {
        return localStatus;
    }

    public void setLocalStatus(Integer localStatus) {
        this.localStatus = localStatus;
    }

    @Override
    public String toString() {
        return "FolderDTO{" +
                "folderId=" + folderId +
                ", folderName='" + folderName + '\'' +
                ", accountId=" + accountId +
                ", countNumber=" + countNumber +
                ", localStatus=" + localStatus +
                '}';
    }
}
