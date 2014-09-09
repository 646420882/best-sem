package com.perfect.schedule.core.taskmanager;

import com.perfect.schedule.core.TaskItemDefine;
import com.perfect.schedule.core.strategy.TBScheduleManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TBScheduleManagerStatic extends TBScheduleManager {
    private static transient Logger log = LoggerFactory.getLogger(TBScheduleManagerStatic.class);
    protected int taskItemCount = 0;

    protected long lastFetchVersion = -1;

    private final Object NeedReloadTaskItemLock = new Object();

    public TBScheduleManagerStatic(TBScheduleManagerFactory aFactory,
                                   String baseTaskType, String ownSign, IScheduleDataManager aScheduleCenter) throws Exception {
        super(aFactory, baseTaskType, ownSign, aScheduleCenter);
    }

    public void initialRunningInfo() throws Exception {
        scheduleCenter.clearExpireScheduleServer(this.currenScheduleServer.getTaskType(), this.taskTypeInfo.getJudgeDeadInterval());
        List<String> list = scheduleCenter.loadScheduleServerNames(this.currenScheduleServer.getTaskType());
        if (scheduleCenter.isLeader(this.currenScheduleServer.getUuid(), list)) {

            log.debug(this.currenScheduleServer.getUuid() + ":" + list.size());
            this.scheduleCenter.initialRunningInfo4Static(this.currenScheduleServer.getBaseTaskType(), this.currenScheduleServer.getOwnSign(), this.currenScheduleServer.getUuid());
        }
    }

    public void initial() throws Exception {
        new Thread(this.currenScheduleServer.getTaskType() + "-" + this.currentSerialNumber + "-StartProcess") {
            @SuppressWarnings("static-access")
            public void run() {
                try {
                    log.info("...... of " + currenScheduleServer.getUuid());
                    while (isRuntimeInfoInitial == false) {
                        if (isStopSchedule == true) {
                            log.debug("" + currenScheduleServer.getUuid());
                            return;
                        }

                        try {
                            initialRunningInfo();
                            isRuntimeInfoInitial = scheduleCenter.isInitialRunningInfoSucuss(
                                    currenScheduleServer.getBaseTaskType(),
                                    currenScheduleServer.getOwnSign());
                        } catch (Throwable e) {

                            log.error(e.getMessage(), e);
                        }
                        if (isRuntimeInfoInitial == false) {
                            Thread.currentThread().sleep(1000);
                        }
                    }
                    int count = 0;
                    lastReloadTaskItemListTime = scheduleCenter.getSystemTime();
                    while (getCurrentScheduleTaskItemListNow().size() <= 0) {
                        if (isStopSchedule == true) {
                            log.debug("" + currenScheduleServer.getUuid());
                            return;
                        }
                        Thread.currentThread().sleep(1000);
                        count = count + 1;

                    }
                    String tmpStr = "";
                    for (int i = 0; i < currentTaskItemList.size(); i++) {
                        if (i > 0) {
                            tmpStr = tmpStr + ",";
                        }
                        tmpStr = tmpStr + currentTaskItemList.get(i);
                    }
                    log.info("" + tmpStr + "  of  " + currenScheduleServer.getUuid());


                    taskItemCount = scheduleCenter.loadAllTaskItem(currenScheduleServer.getTaskType()).size();

                    computerStart();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    String str = e.getMessage();
                    if (str.length() > 300) {
                        str = str.substring(0, 300);
                    }
                    startErrorInfo = "" + str;
                }
            }
        }.start();
    }

    /**
     * @throws Exception
     */
    public void refreshScheduleServerInfo() throws Exception {
        try {
            rewriteScheduleInfo();

            if (this.isRuntimeInfoInitial == false) {
                return;
            }


            this.assignScheduleTask();


            boolean tmpBoolean = this.isNeedReLoadTaskItemList();
            if (tmpBoolean != this.isNeedReloadTaskItem) {

                synchronized (NeedReloadTaskItemLock) {
                    this.isNeedReloadTaskItem = true;
                }
                rewriteScheduleInfo();
            }

            if (this.isPauseSchedule == true || this.processor != null && processor.isSleeping() == true) {


                this.getCurrentScheduleTaskItemListNow();
            }
        } catch (Throwable e) {

            this.clearMemoInfo();
            if (e instanceof Exception) {
                throw (Exception) e;
            } else {
                throw new Exception(e.getMessage(), e);
            }
        }
    }

    /**
     * @return
     * @throws Exception
     */
    public boolean isNeedReLoadTaskItemList() throws Exception {
        return this.lastFetchVersion < this.scheduleCenter.getReloadTaskItemFlag(this.currenScheduleServer.getTaskType());
    }

    /**
     * @throws Exception
     */
    public void assignScheduleTask() throws Exception {
        scheduleCenter.clearExpireScheduleServer(this.currenScheduleServer.getTaskType(), this.taskTypeInfo.getJudgeDeadInterval());
        List<String> serverList = scheduleCenter
                .loadScheduleServerNames(this.currenScheduleServer.getTaskType());

        if (scheduleCenter.isLeader(this.currenScheduleServer.getUuid(), serverList) == false) {
            if (log.isDebugEnabled()) {
                log.debug(this.currenScheduleServer.getUuid() + "");
            }
            return;
        }

        scheduleCenter.setInitialRunningInfoSucuss(this.currenScheduleServer.getBaseTaskType(), this.currenScheduleServer.getTaskType(), this.currenScheduleServer.getUuid());
        scheduleCenter.clearTaskItem(this.currenScheduleServer.getTaskType(), serverList);
        scheduleCenter.assignTaskItem(this.currenScheduleServer.getTaskType(), this.currenScheduleServer.getUuid(), this.taskTypeInfo.getMaxTaskItemsOfOneThreadGroup(), serverList);
    }

    /**








     */

    public List<TaskItemDefine> getCurrentScheduleTaskItemList() {
        try {
            if (this.isNeedReloadTaskItem == true) {


                if (this.processor != null) {
                    while (this.processor.isDealFinishAllData() == false) {
                        Thread.sleep(50);
                    }
                }

                synchronized (NeedReloadTaskItemLock) {
                    this.getCurrentScheduleTaskItemListNow();
                    this.isNeedReloadTaskItem = false;
                }
            }
            this.lastReloadTaskItemListTime = this.scheduleCenter.getSystemTime();
            return this.currentTaskItemList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected List<TaskItemDefine> getCurrentScheduleTaskItemListNow() throws Exception {

        this.lastFetchVersion = this.scheduleCenter.getReloadTaskItemFlag(this.currenScheduleServer.getTaskType());
        try {

            this.scheduleCenter.releaseDealTaskItem(this.currenScheduleServer.getTaskType(), this.currenScheduleServer.getUuid());


            this.currentTaskItemList.clear();
            this.currentTaskItemList = this.scheduleCenter.reloadDealTaskItem(
                    this.currenScheduleServer.getTaskType(), this.currenScheduleServer.getUuid());


            if (this.currentTaskItemList.size() == 0 &&
                    scheduleCenter.getSystemTime() - this.lastReloadTaskItemListTime
                            > this.taskTypeInfo.getHeartBeatRate() * 10) {
                String message = "" + this.currenScheduleServer.getUuid() + "[TASK_TYPE=" + this.currenScheduleServer.getTaskType() + "";
                log.warn(message);
            }

            if (this.currentTaskItemList.size() > 0) {

                this.lastReloadTaskItemListTime = scheduleCenter.getSystemTime();
            }

            return this.currentTaskItemList;
        } catch (Throwable e) {
            this.lastFetchVersion = -1;
            if (e instanceof Exception) {
                throw (Exception) e;
            } else {
                throw new Exception(e);
            }
        }
    }

    public int getTaskItemCount() {
        return this.taskItemCount;
    }

}
