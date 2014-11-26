package com.perfect.dto;

/**
 * Created by XiaoWei on 2014/11/26.
 */
public class CustomGroupDTO {
    private String id;
    private String groupName;
    private Long accountId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "CustomGroupDTO{" +
                "id='" + id + '\'' +
                ", groupName='" + groupName + '\'' +
                ", accountId=" + accountId +
                '}';
    }
}
