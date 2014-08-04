package com.perfect.schedule.core.taskmanager;

import com.perfect.schedule.core.CronExpression;
import com.perfect.schedule.core.IScheduleTaskDeal;
import com.perfect.schedule.core.ScheduleUtil;
import com.perfect.schedule.core.TaskItemDefine;
import com.perfect.schedule.core.strategy.IStrategyTask;
import com.perfect.schedule.core.strategy.TBScheduleManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



@SuppressWarnings({"rawtypes", "unchecked"})
abstract class TBScheduleManager implements IStrategyTask {
    private static transient Logger log = LoggerFactory.getLogger(TBScheduleManager.class);
    private static int nextSerialNumber = 0;

    protected int currentSerialNumber = 0;
    protected ScheduleTaskType taskTypeInfo;
    protected ScheduleServer currenScheduleServer;
    IScheduleTaskDeal taskDealBean;

    IScheduleProcessor processor;
    StatisticsInfo statisticsInfo = new StatisticsInfo();

    boolean isPauseSchedule = true;
    String pauseMessage = "";
    protected List<TaskItemDefine> currentTaskItemList = new ArrayList<TaskItemDefine>();
    protected long lastReloadTaskItemListTime = 0;
    protected boolean isNeedReloadTaskItem = true;


    private String mBeanName;
    private Timer heartBeatTimer;

    protected IScheduleDataManager scheduleCenter;

    protected String startErrorInfo = null;

    protected boolean isStopSchedule = false;
    protected Lock registerLock = new ReentrantLock();

    protected boolean isRuntimeInfoInitial = false;

    TBScheduleManagerFactory factory;

    TBScheduleManager(TBScheduleManagerFactory aFactory, String baseTaskType, String ownSign, IScheduleDataManager aScheduleCenter) throws Exception {
        this.factory = aFactory;
        this.currentSerialNumber = serialNumber();
        this.scheduleCenter = aScheduleCenter;
        this.taskTypeInfo = this.scheduleCenter.loadTaskTypeBaseInfo(baseTaskType);

        this.scheduleCenter.clearExpireTaskTypeRunningInfo(baseTaskType, ScheduleUtil.getLocalIP() + "??????OWN_SIGN???", this.taskTypeInfo.getExpireOwnSignInterval());

        Object dealBean = aFactory.getBean(this.taskTypeInfo.getDealBeanName());
        if (dealBean == null) {
            throw new Exception("SpringBean " + this.taskTypeInfo.getDealBeanName());
        }
        if (dealBean instanceof IScheduleTaskDeal == false) {
            throw new Exception("SpringBean " + this.taskTypeInfo.getDealBeanName());
        }
        this.taskDealBean = (IScheduleTaskDeal) dealBean;

        if (this.taskTypeInfo.getJudgeDeadInterval() < this.taskTypeInfo.getHeartBeatRate() * 5) {
            throw new Exception(this.taskTypeInfo.getJudgeDeadInterval()
                    + ",HeartBeatRate = " + this.taskTypeInfo.getHeartBeatRate());
        }
        this.currenScheduleServer = ScheduleServer.createScheduleServer(this.scheduleCenter, baseTaskType, ownSign, this.taskTypeInfo.getThreadNumber());
        this.currenScheduleServer.setManagerFactoryUUID(this.factory.getUuid());
        scheduleCenter.registerScheduleServer(this.currenScheduleServer);
        this.mBeanName = "pamirs:name=" + "com.perfect.schedule.ServerMananger." + this.currenScheduleServer.getUuid();
        this.heartBeatTimer = new Timer(this.currenScheduleServer.getTaskType() + "-" + this.currentSerialNumber + "-HeartBeat");
        this.heartBeatTimer.schedule(new HeartBeatTimerTask(this),
                new java.util.Date(System.currentTimeMillis() + 500),
                this.taskTypeInfo.getHeartBeatRate());
        initial();
    }

    public abstract void initial() throws Exception;

    public abstract void refreshScheduleServerInfo() throws Exception;

    public abstract void assignScheduleTask() throws Exception;

    public abstract List<TaskItemDefine> getCurrentScheduleTaskItemList();

    public abstract int getTaskItemCount();

    public String getTaskType() {
        return this.currenScheduleServer.getTaskType();
    }

    public void initialTaskParameter(String strategyName, String taskParameter) {
    }

    private static synchronized int serialNumber() {
        return nextSerialNumber++;
    }

    public int getCurrentSerialNumber() {
        return this.currentSerialNumber;
    }

    public void clearMemoInfo() {
        try {
            this.currentTaskItemList.clear();
            if (this.processor != null) {
                this.processor.clearAllHasFetchData();
            }
        } finally {
            this.isNeedReloadTaskItem = true;
        }

    }

    public void rewriteScheduleInfo() throws Exception {
        registerLock.lock();
        try {
            if (this.isStopSchedule == true) {
                if (log.isDebugEnabled()) {
                    log.debug(currenScheduleServer.getUuid());
                }
                return;
            }
            if (startErrorInfo == null) {
                this.currenScheduleServer.setDealInfoDesc(this.pauseMessage + ":" + this.statisticsInfo.getDealDescription());
            } else {
                this.currenScheduleServer.setDealInfoDesc(startErrorInfo);
            }
            if (this.scheduleCenter.refreshScheduleServer(this.currenScheduleServer) == false) {
                this.clearMemoInfo();
                this.scheduleCenter.registerScheduleServer(this.currenScheduleServer);
            }
        } finally {
            registerLock.unlock();
        }
    }


    public void computerStart() throws Exception {

        boolean isRunNow = false;
        if (this.taskTypeInfo.getPermitRunStartTime() == null) {
            isRunNow = true;
        } else {
            String tmpStr = this.taskTypeInfo.getPermitRunStartTime();
            if (tmpStr.toLowerCase().startsWith("startrun:")) {
                isRunNow = true;
                tmpStr = tmpStr.substring("startrun:".length());
            }
            CronExpression cexpStart = new CronExpression(tmpStr);
            Date current = new Date(this.scheduleCenter.getSystemTime());
            Date firstStartTime = cexpStart.getNextValidTimeAfter(current);
            this.heartBeatTimer.schedule(
                    new PauseOrResumeScheduleTask(this, this.heartBeatTimer,
                            PauseOrResumeScheduleTask.TYPE_RESUME, tmpStr),
                    firstStartTime);
            this.currenScheduleServer.setNextRunStartTime(ScheduleUtil.transferDataToString(firstStartTime));
            if (this.taskTypeInfo.getPermitRunEndTime() == null
                    || this.taskTypeInfo.getPermitRunEndTime().equals("-1")) {
                this.currenScheduleServer.setNextRunEndTime("?????????????????pause");
            } else {
                try {
                    String tmpEndStr = this.taskTypeInfo.getPermitRunEndTime();
                    CronExpression cexpEnd = new CronExpression(tmpEndStr);
                    Date firstEndTime = cexpEnd.getNextValidTimeAfter(firstStartTime);
                    Date nowEndTime = cexpEnd.getNextValidTimeAfter(current);
                    if (!nowEndTime.equals(firstEndTime) && current.before(nowEndTime)) {
                        isRunNow = true;
                        firstEndTime = nowEndTime;
                    }
                    this.heartBeatTimer.schedule(
                            new PauseOrResumeScheduleTask(this, this.heartBeatTimer,
                                    PauseOrResumeScheduleTask.TYPE_PAUSE, tmpEndStr),
                            firstEndTime);
                    this.currenScheduleServer.setNextRunEndTime(ScheduleUtil.transferDataToString(firstEndTime));
                } catch (Exception e) {
                    log.error(currenScheduleServer.getUuid(), e);
                    throw new Exception(currenScheduleServer.getUuid(), e);
                }
            }
        }
        if (isRunNow == true) {
            this.resume("");
        }
        this.rewriteScheduleInfo();

    }

    public boolean isContinueWhenData() throws Exception {
        if (isPauseWhenNoData() == true) {
            this.pause("");
            return false;
        } else {
            return true;
        }
    }

    public boolean isPauseWhenNoData() {
        if (this.currentTaskItemList.size() > 0 && this.taskTypeInfo.getPermitRunStartTime() != null) {
            if (this.taskTypeInfo.getPermitRunEndTime() == null
                    || this.taskTypeInfo.getPermitRunEndTime().equals("-1")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void pause(String message) throws Exception {
        if (this.isPauseSchedule == false) {
            this.isPauseSchedule = true;
            this.pauseMessage = message;
            if (log.isDebugEnabled()) {
                log.debug(this.currenScheduleServer.getUuid() + ":" + this.statisticsInfo.getDealDescription());
            }
            if (this.processor != null) {
                this.processor.stopSchedule();
            }
            rewriteScheduleInfo();
        }
    }

    public void resume(String message) throws Exception {
        if (this.isPauseSchedule == true) {
            if (log.isDebugEnabled()) {
                log.debug(this.currenScheduleServer.getUuid());
            }
            this.isPauseSchedule = false;
            this.pauseMessage = message;
            if (this.taskDealBean != null) {
                if (this.taskTypeInfo.getProcessorType() != null &&
                        this.taskTypeInfo.getProcessorType().equalsIgnoreCase("NOTSLEEP") == true) {
                    this.taskTypeInfo.setProcessorType("NOTSLEEP");
                    this.processor = new TBScheduleProcessorNotSleep(this,
                            taskDealBean, this.statisticsInfo);
                } else {
                    this.processor = new TBScheduleProcessorSleep(this,
                            taskDealBean, this.statisticsInfo);
                    this.taskTypeInfo.setProcessorType("SLEEP");
                }
            }
            rewriteScheduleInfo();
        }
    }

    public void stop(String strategyName) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug( this.currenScheduleServer.getUuid());
        }
        this.isPauseSchedule = false;
        if (this.processor != null) {
            this.processor.stopSchedule();
        } else {
            this.unRegisterScheduleServer();
        }
    }

    protected void unRegisterScheduleServer() throws Exception {
        registerLock.lock();
        try {
            if (this.processor != null) {
                this.processor = null;
            }
            if (this.isPauseSchedule == true) {
                return;
            }
            if (log.isDebugEnabled()) {
                log.debug("" + this.currenScheduleServer.getUuid());
            }
            this.isStopSchedule = true;
            this.heartBeatTimer.cancel();
            this.scheduleCenter.unRegisterScheduleServer(
                    this.currenScheduleServer.getTaskType(),
                    this.currenScheduleServer.getUuid());
        } finally {
            registerLock.unlock();
        }
    }

    public ScheduleTaskType getTaskTypeInfo() {
        return taskTypeInfo;
    }


    public StatisticsInfo getStatisticsInfo() {
        return statisticsInfo;
    }

    public void printScheduleServerInfo(String taskType) {

    }

    public ScheduleServer getScheduleServer() {
        return this.currenScheduleServer;
    }

    public String getmBeanName() {
        return mBeanName;
    }
}

class HeartBeatTimerTask extends java.util.TimerTask {
    private static transient Logger log = LoggerFactory
            .getLogger(HeartBeatTimerTask.class);
    TBScheduleManager manager;

    public HeartBeatTimerTask(TBScheduleManager aManager) {
        manager = aManager;
    }

    public void run() {
        try {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            manager.refreshScheduleServerInfo();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}

class PauseOrResumeScheduleTask extends java.util.TimerTask {
    private static transient Logger log = LoggerFactory
            .getLogger(HeartBeatTimerTask.class);
    public static int TYPE_PAUSE = 1;
    public static int TYPE_RESUME = 2;
    TBScheduleManager manager;
    Timer timer;
    int type;
    String cronTabExpress;

    public PauseOrResumeScheduleTask(TBScheduleManager aManager, Timer aTimer, int aType, String aCronTabExpress) {
        this.manager = aManager;
        this.timer = aTimer;
        this.type = aType;
        this.cronTabExpress = aCronTabExpress;
    }

    public void run() {
        try {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            this.cancel();
            Date current = new Date(System.currentTimeMillis());
            CronExpression cexp = new CronExpression(this.cronTabExpress);
            Date nextTime = cexp.getNextValidTimeAfter(current);
            if (this.type == TYPE_PAUSE) {
                manager.pause("??????????,pause????");
                this.manager.getScheduleServer().setNextRunEndTime(ScheduleUtil.transferDataToString(nextTime));
            } else {
                manager.resume("????????,resume????");
                this.manager.getScheduleServer().setNextRunStartTime(ScheduleUtil.transferDataToString(nextTime));
            }
            this.timer.schedule(new PauseOrResumeScheduleTask(this.manager, this.timer, this.type, this.cronTabExpress), nextTime);
        } catch (Throwable ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}

class StatisticsInfo {
    private AtomicLong fetchDataNum = new AtomicLong(0);
    private AtomicLong fetchDataCount = new AtomicLong(0);
    private AtomicLong dealDataSucess = new AtomicLong(0);
    private AtomicLong dealDataFail = new AtomicLong(0);
    private AtomicLong dealSpendTime = new AtomicLong(0);
    private AtomicLong otherCompareCount = new AtomicLong(0);

    public void addFetchDataNum(long value) {
        this.fetchDataNum.addAndGet(value);
    }

    public void addFetchDataCount(long value) {
        this.fetchDataCount.addAndGet(value);
    }

    public void addDealDataSucess(long value) {
        this.dealDataSucess.addAndGet(value);
    }

    public void addDealDataFail(long value) {
        this.dealDataFail.addAndGet(value);
    }

    public void addDealSpendTime(long value) {
        this.dealSpendTime.addAndGet(value);
    }

    public void addOtherCompareCount(long value) {
        this.otherCompareCount.addAndGet(value);
    }

    public String getDealDescription() {
        return "FetchDataCount=" + this.fetchDataCount
                + ",FetchDataNum=" + this.fetchDataNum
                + ",DealDataSucess=" + this.dealDataSucess
                + ",DealDataFail=" + this.dealDataFail
                + ",DealSpendTime=" + this.dealSpendTime
                + ",otherCompareCount=" + this.otherCompareCount;
    }

}