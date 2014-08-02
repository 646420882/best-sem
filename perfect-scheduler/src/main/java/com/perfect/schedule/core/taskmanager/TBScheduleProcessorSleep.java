package com.perfect.schedule.core.taskmanager;

import com.perfect.schedule.core.IScheduleTaskDeal;
import com.perfect.schedule.core.IScheduleTaskDealMulti;
import com.perfect.schedule.core.IScheduleTaskDealSingle;
import com.perfect.schedule.core.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ?????????????TBScheduleManager????????????????????
 *
 * @param <T>
 * @author xuannan
 */
class TBScheduleProcessorSleep<T> implements IScheduleProcessor, Runnable {

    private static transient Logger logger = LoggerFactory.getLogger(TBScheduleProcessorSleep.class);
    final LockObject m_lockObject = new LockObject();
    List<Thread> threadList = Collections.synchronizedList(new ArrayList<Thread>());
    /**
     * ?????????
     */
    protected TBScheduleManager scheduleManager;
    /**
     * ????????
     */
    ScheduleTaskType taskTypeInfo;

    /**
     * ???????????
     */
    protected IScheduleTaskDeal<T> taskDealBean;

    /**
     * ?????????快?煆??
     */
    protected long taskListVersion = 0;
    final Object lockVersionObject = new Object();
    final Object lockRunningList = new Object();

    protected List<T> taskList = Collections.synchronizedList(new ArrayList<T>());

    /**
     * ??????????
     */
    boolean isMutilTask = false;

    /**
     * ???????????????????
     */
    boolean isStopSchedule = false;// ????????快???
    boolean isSleeping = false;

    StatisticsInfo statisticsInfo;

    /**
     * ????????????????
     *
     * @param aManager
     * @param aTaskDealBean
     * @param aStatisticsInfo
     * @throws Exception
     */
    public TBScheduleProcessorSleep(TBScheduleManager aManager,
                                    IScheduleTaskDeal<T> aTaskDealBean, StatisticsInfo aStatisticsInfo) throws Exception {
        this.scheduleManager = aManager;
        this.statisticsInfo = aStatisticsInfo;
        this.taskTypeInfo = this.scheduleManager.getTaskTypeInfo();
        this.taskDealBean = aTaskDealBean;
        if (this.taskDealBean instanceof IScheduleTaskDealSingle<?>) {
            if (taskTypeInfo.getExecuteNumber() > 1) {
                taskTypeInfo.setExecuteNumber(1);
            }
            isMutilTask = false;
        } else {
            isMutilTask = true;
        }
        if (taskTypeInfo.getFetchDataNumber() < taskTypeInfo.getThreadNumber() * 10) {
            logger.warn("???????辰???????????????????汛?????????????fetchnum?? >= ?????????threadnum?? *?????????????10?? ");
        }
        for (int i = 0; i < taskTypeInfo.getThreadNumber(); i++) {
            this.startThread(i);
        }
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     *
     * @throws Exception
     */
    public void stopSchedule() throws Exception {
        // ?????????????,?????????????????????????????????????
        this.isStopSchedule = true;
        //???????汛????????,?????????????快????????????
        this.taskList.clear();
    }

    private void startThread(int index) {
        Thread thread = new Thread(this);
        threadList.add(thread);
        String threadName = this.scheduleManager.getScheduleServer().getTaskType() + "-"
                + this.scheduleManager.getCurrentSerialNumber() + "-exe"
                + index;
        thread.setName(threadName);
        thread.start();
    }

    public synchronized Object getScheduleTaskId() {
        if (this.taskList.size() > 0)
            return this.taskList.remove(0);  // ????????
        return null;
    }

    public synchronized Object[] getScheduleTaskIdMulti() {
        if (this.taskList.size() == 0) {
            return null;
        }
        int size = taskList.size() > taskTypeInfo.getExecuteNumber() ? taskTypeInfo.getExecuteNumber()
                : taskList.size();

        Object[] result = null;
        if (size > 0) {
            result = (Object[]) Array.newInstance(this.taskList.get(0).getClass(), size);
        }
        for (int i = 0; i < size; i++) {
            result[i] = this.taskList.remove(0);  // ????????
        }
        return result;
    }

    public void clearAllHasFetchData() {
        this.taskList.clear();
    }

    public boolean isDealFinishAllData() {
        return this.taskList.size() == 0;
    }

    public boolean isSleeping() {
        return this.isSleeping;
    }

    protected int loadScheduleData() {
        try {
            //??????????????????????????
            if (this.taskTypeInfo.getSleepTimeInterval() > 0) {
                if (logger.isTraceEnabled()) {
                    logger.trace("??????????????????" + this.taskTypeInfo.getSleepTimeInterval());
                }
                this.isSleeping = true;
                Thread.sleep(taskTypeInfo.getSleepTimeInterval());
                this.isSleeping = false;

                if (logger.isTraceEnabled()) {
                    logger.trace("????????????????????");
                }
            }

            List<TaskItemDefine> taskItems = this.scheduleManager.getCurrentScheduleTaskItemList();
            // ?????????????????????????????????????忌???
            if (taskItems.size() > 0) {
                List<T> tmpList = this.taskDealBean.selectTasks(
                        taskTypeInfo.getTaskParameter(),
                        scheduleManager.getScheduleServer().getOwnSign(),
                        this.scheduleManager.getTaskItemCount(), taskItems,
                        taskTypeInfo.getFetchDataNumber());
                scheduleManager.getScheduleServer().setLastFetchDataTime(new Timestamp(scheduleManager.scheduleCenter.getSystemTime()));
                if (tmpList != null) {
                    this.taskList.addAll(tmpList);
                }
            } else {
                if (logger.isTraceEnabled()) {
                    logger.trace("??抖??????????????????");
                }
            }
            addFetchNum(taskList.size(), "TBScheduleProcessor.loadScheduleData");
            return this.taskList.size();
        } catch (Throwable ex) {
            logger.error("Get tasks error.", ex);
        }
        return 0;
    }

    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    public void run() {
        try {
            long startTime = 0;
            while (true) {
                this.m_lockObject.addThread();
                Object executeTask;
                while (true) {
                    if (this.isStopSchedule == true) {//?????快???
                        this.m_lockObject.realseThread();
                        this.m_lockObject.notifyOtherThread();//?????快????????
                        synchronized (this.threadList) {
                            this.threadList.remove(Thread.currentThread());
                            if (this.threadList.size() == 0) {
                                this.scheduleManager.unRegisterScheduleServer();
                            }
                        }
                        return;
                    }

                    //???????????
                    if (this.isMutilTask == false) {
                        executeTask = this.getScheduleTaskId();
                    } else {
                        executeTask = this.getScheduleTaskIdMulti();
                    }

                    if (executeTask == null) {
                        break;
                    }

                    try {//???????????
                        startTime = scheduleManager.scheduleCenter.getSystemTime();
                        if (this.isMutilTask == false) {
                            if (((IScheduleTaskDealSingle) this.taskDealBean).execute(executeTask, scheduleManager.getScheduleServer().getOwnSign()) == true) {
                                addSuccessNum(1, scheduleManager.scheduleCenter.getSystemTime()
                                                - startTime,
                                        "com.taobao.pamirs.com.perfect.schedule.TBScheduleProcessorSleep.run");
                            } else {
                                addFailNum(1, scheduleManager.scheduleCenter.getSystemTime()
                                                - startTime,
                                        "com.taobao.pamirs.com.perfect.schedule.TBScheduleProcessorSleep.run");
                            }
                        } else {
                            if (((IScheduleTaskDealMulti) this.taskDealBean)
                                    .execute((Object[]) executeTask, scheduleManager.getScheduleServer().getOwnSign()) == true) {
                                addSuccessNum(((Object[]) executeTask).length, scheduleManager.scheduleCenter.getSystemTime()
                                                - startTime,
                                        "com.taobao.pamirs.com.perfect.schedule.TBScheduleProcessorSleep.run");
                            } else {
                                addFailNum(((Object[]) executeTask).length, scheduleManager.scheduleCenter.getSystemTime()
                                                - startTime,
                                        "com.taobao.pamirs.com.perfect.schedule.TBScheduleProcessorSleep.run");
                            }
                        }
                    } catch (Throwable ex) {
                        if (this.isMutilTask == false) {
                            addFailNum(1, scheduleManager.scheduleCenter.getSystemTime() - startTime,
                                    "TBScheduleProcessor.run");
                        } else {
                            addFailNum(((Object[]) executeTask).length, scheduleManager.scheduleCenter.getSystemTime()
                                            - startTime,
                                    "TBScheduleProcessor.run");
                        }
                        logger.warn("Task :" + executeTask + " ???????", ex);
                    }
                }
                //????????????快??????????????
                if (logger.isTraceEnabled()) {
                    logger.trace(Thread.currentThread().getName() + "????????????????:" + this.m_lockObject.count());
                }
                if (this.m_lockObject.realseThreadButNotLast() == false) {
                    int size = 0;
                    Thread.currentThread().sleep(100);
                    startTime = scheduleManager.scheduleCenter.getSystemTime();
                    // ??????
                    size = this.loadScheduleData();
                    if (size > 0) {
                        this.m_lockObject.notifyOtherThread();
                    } else {
                        //?忪?????????????????????????
                        if (this.isStopSchedule == false && this.scheduleManager.isContinueWhenData() == true) {
                            if (logger.isTraceEnabled()) {
                                logger.trace("???????????start sleep");
                            }
                            this.isSleeping = true;
                            Thread.currentThread().sleep(this.scheduleManager.getTaskTypeInfo().getSleepTimeNoData());
                            this.isSleeping = false;

                            if (logger.isTraceEnabled()) {
                                logger.trace("Sleep end");
                            }
                        } else {
                            //??????????????????????忍?????
                            this.m_lockObject.notifyOtherThread();
                        }
                    }
                    this.m_lockObject.realseThread();
                } else {// ???????????????????妊?????????????????????????
                    if (logger.isTraceEnabled()) {
                        logger.trace("??????????????sleep");
                    }
                    this.m_lockObject.waitCurrentThread();
                }
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void addFetchNum(long num, String addr) {

        this.statisticsInfo.addFetchDataCount(1);
        this.statisticsInfo.addFetchDataNum(num);
    }

    public void addSuccessNum(long num, long spendTime, String addr) {
        this.statisticsInfo.addDealDataSucess(num);
        this.statisticsInfo.addDealSpendTime(spendTime);
    }

    public void addFailNum(long num, long spendTime, String addr) {
        this.statisticsInfo.addDealDataFail(num);
        this.statisticsInfo.addDealSpendTime(spendTime);
    }
}
