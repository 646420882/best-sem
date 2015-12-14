package com.perfect.entity.admin;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by yousheng on 15/12/14.
 */
@Document(collection = "admin_op_logs")
public class AdminOperationLogEntity {
    private String userId;

    private String adminUserId;

    private String operationType;

    private long time;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(String adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
