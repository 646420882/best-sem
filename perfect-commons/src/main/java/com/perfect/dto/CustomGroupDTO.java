package com.perfect.dto;

/**
 * Created by XiaoWei on 2014/11/26.
 */
public class CustomGroupDTO extends BaseDTO {
    private String groupName;
    private Long accountId;


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
                ", groupName='" + groupName + '\'' +
                ", accountId=" + accountId +
                '}';
    }
}
