package com.perfect.schedule.core.taskmanager;

/**
 * �����������
 *
 * @author xuannan
 */
public class ScheduleTaskItem {
    public enum TaskItemSts {
        ACTIVTE, FINISH, HALT
    }

    /**
     * ������������
     */
    private String taskType;

    /**
     * ԭʼ��������
     */
    private String baseTaskType;

    /**
     * ���״̬
     */
    private TaskItemSts sts = TaskItemSts.ACTIVTE;

    /**
     * ��������Ҫ�Ĳ���
     */
    private String dealParameter = "";

    /**
     * ���������,��������������дһЩ��Ϣ
     */
    private String dealDesc = "";


    public String getBaseTaskType() {
        return baseTaskType;
    }

    public void setBaseTaskType(String baseTaskType) {
        this.baseTaskType = baseTaskType;
    }

    /**
     * ���еĻ�����ʶ
     */
    private String ownSign;

    /**
     * �������ID
     */
    private String taskItem;
    /**
     * ���е�ǰ������е���������
     */
    private String currentScheduleServer;
    /**
     * ���������������е���������
     */
    private String requestScheduleServer;

    /**
     * ��ݰ汾��
     */
    private long version;

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskItem() {
        return taskItem;
    }

    public void setTaskItem(String aTaskItem) {
        this.taskItem = aTaskItem;
    }

    public String getCurrentScheduleServer() {
        return currentScheduleServer;
    }

    public void setCurrentScheduleServer(String currentScheduleServer) {
        this.currentScheduleServer = currentScheduleServer;
    }

    public String getRequestScheduleServer() {
        return requestScheduleServer;
    }

    public void setRequestScheduleServer(String requestScheduleServer) {
        this.requestScheduleServer = requestScheduleServer;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getOwnSign() {
        return ownSign;
    }

    public void setOwnSign(String ownSign) {
        this.ownSign = ownSign;
    }

    public String toString() {
        return "TASK_TYPE=" + this.taskType + ":TASK_ITEM=" + this.taskItem
                + ":CUR_SERVER=" + this.currentScheduleServer + ":REQ_SERVER=" + this.requestScheduleServer + ":DEAL_PARAMETER=" + this.dealParameter;
    }

    public void setDealDesc(String dealDesc) {
        this.dealDesc = dealDesc;
    }

    public String getDealDesc() {
        return dealDesc;
    }

    public void setSts(TaskItemSts sts) {
        this.sts = sts;
    }

    public TaskItemSts getSts() {
        return sts;
    }

    public void setDealParameter(String dealParameter) {
        this.dealParameter = dealParameter;
    }

    public String getDealParameter() {
        return dealParameter;
    }

}
