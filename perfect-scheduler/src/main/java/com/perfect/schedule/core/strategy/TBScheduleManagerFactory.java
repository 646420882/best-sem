package com.perfect.schedule.core.strategy;

import com.perfect.schedule.core.ConsoleManager;
import com.perfect.schedule.core.IScheduleTaskDeal;
import com.perfect.schedule.core.ScheduleUtil;
import com.perfect.schedule.core.taskmanager.IScheduleDataManager;
import com.perfect.schedule.core.taskmanager.TBScheduleManagerStatic;
import com.perfect.schedule.core.zk.ScheduleDataManager4ZK;
import com.perfect.schedule.core.zk.ScheduleStrategyDataManager4ZK;
import com.perfect.schedule.core.zk.ZKManager;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TBScheduleManagerFactory implements ApplicationContextAware {

    protected static transient Logger logger = LoggerFactory.getLogger(TBScheduleManagerFactory.class);

    private Map<String, String> zkConfig;

    protected ZKManager zkManager;


    public boolean start = true;
    private int timerInterval = 2000;

    private IScheduleDataManager scheduleDataManager;
    private ScheduleStrategyDataManager4ZK scheduleStrategyManager;

    private Map<String, List<IStrategyTask>> managerMap = new ConcurrentHashMap<String, List<IStrategyTask>>();

    private ApplicationContext applicationcontext;
    private String uuid;
    private String ip;
    private String hostName;

    private Timer timer;
    private ManagerFactoryTimerTask timerTask;
    protected Lock lock = new ReentrantLock();

    volatile String errorMessage = "No config Zookeeper connect infomation";
    private InitialThread initialThread;

    public TBScheduleManagerFactory() {
        this.ip = ScheduleUtil.getLocalIP();
        this.hostName = ScheduleUtil.getLocalHostName();
    }

    public void init() throws Exception {
        Properties properties = new Properties();
        for (Map.Entry<String, String> e : this.zkConfig.entrySet()) {
            properties.put(e.getKey(), e.getValue());
        }
        this.init(properties);
    }

    public void reInit(Properties p) throws Exception {
        if (this.start == true || this.timer != null || this.managerMap.size() > 0) {
            throw new Exception("");
        }
        this.init(p);
    }

    public void init(Properties p) throws Exception {
        if (this.initialThread != null) {
            this.initialThread.stopThread();
        }
        this.lock.lock();
        try {
            this.scheduleDataManager = null;
            this.scheduleStrategyManager = null;
            ConsoleManager.setScheduleManagerFactory(this);
            if (this.zkManager != null) {
                this.zkManager.close();
            }
            this.zkManager = new ZKManager(p);
            this.errorMessage = "Zookeeper connecting ......" + this.zkManager.getConnectStr();
            initialThread = new InitialThread(this);
            initialThread.setName("TBScheduleManagerFactory-initialThread");
            initialThread.start();
        } finally {
            this.lock.unlock();
        }
    }

    public void initialData() throws Exception {
        this.zkManager.initial();
        this.scheduleDataManager = new ScheduleDataManager4ZK(this.zkManager);
        this.scheduleStrategyManager = new ScheduleStrategyDataManager4ZK(this.zkManager);
        if (this.start == true) {
            this.scheduleStrategyManager.registerManagerFactory(this);
            if (timer == null) {
                timer = new Timer("TBScheduleManagerFactory-Timer");
            }
            if (timerTask == null) {
                timerTask = new ManagerFactoryTimerTask(this);
                timer.schedule(timerTask, 2000, this.timerInterval);
            }
        }
    }

    /**
     *
     * @param baseTaskType
     * @param ownSign
     * @return
     * @throws Exception
     */
    public IStrategyTask createStrategyTask(ScheduleStrategy strategy)
            throws Exception {
        IStrategyTask result = null;
        if (ScheduleStrategy.Kind.Schedule == strategy.getKind()) {
            String baseTaskType = ScheduleUtil.splitBaseTaskTypeFromTaskType(strategy.getTaskName());
            String ownSign = ScheduleUtil.splitOwnsignFromTaskType(strategy.getTaskName());
            result = new TBScheduleManagerStatic(this, baseTaskType, ownSign, scheduleDataManager);
        } else if (ScheduleStrategy.Kind.Java == strategy.getKind()) {
            result = (IStrategyTask) Class.forName(strategy.getTaskName()).newInstance();
            result.initialTaskParameter(strategy.getStrategyName(), strategy.getTaskParameter());
        } else if (ScheduleStrategy.Kind.Bean == strategy.getKind()) {
            result = (IStrategyTask) this.getBean(strategy.getTaskName());
            result.initialTaskParameter(strategy.getStrategyName(), strategy.getTaskParameter());
        }
        return result;
    }

    public void refresh() throws Exception {
        this.lock.lock();
        try {
            ManagerFactoryInfo stsInfo = null;
            boolean isException = false;
            try {
                stsInfo = this.getScheduleStrategyManager().loadManagerFactoryInfo(this.getUuid());
            } catch (Exception e) {
                isException = true;
                logger.error(e.getMessage(), e);
            }
            if (isException == true) {
                try {
                    stopServer(null);
                    this.getScheduleStrategyManager().unRregisterManagerFactory(this);
                } finally {
                    reRegisterManagerFactory();
                }
            } else if (stsInfo.isStart() == false) {
                stopServer(null);
                this.getScheduleStrategyManager().unRregisterManagerFactory(
                        this);
            } else {
                reRegisterManagerFactory();
            }
        } finally {
            this.lock.unlock();
        }
    }

    public void reRegisterManagerFactory() throws Exception {
        List<String> stopList = this.getScheduleStrategyManager().registerManagerFactory(this);
        for (String strategyName : stopList) {
            this.stopServer(strategyName);
        }
        this.assignScheduleServer();
        this.reRunScheduleServer();
    }

    /**
     *
     * @throws Exception
     */
    public void assignScheduleServer() throws Exception {
        for (ScheduleStrategyRunntime run : this.scheduleStrategyManager.loadAllScheduleStrategyRunntimeByUUID(this.uuid)) {
            List<ScheduleStrategyRunntime> factoryList = this.scheduleStrategyManager.loadAllScheduleStrategyRunntimeByTaskType(run.getStrategyName());
            if (factoryList.size() == 0 || this.isLeader(this.uuid, factoryList) == false) {
                continue;
            }
            ScheduleStrategy scheduleStrategy = this.scheduleStrategyManager.loadStrategy(run.getStrategyName());

            int[] nums = ScheduleUtil.assignTaskNumber(factoryList.size(), scheduleStrategy.getAssignNum(), scheduleStrategy.getNumOfSingleServer());
            for (int i = 0; i < factoryList.size(); i++) {
                ScheduleStrategyRunntime factory = factoryList.get(i);
                this.scheduleStrategyManager.updateStrategyRunntimeReqestNum(run.getStrategyName(),
                        factory.getUuid(), nums[i]);
            }
        }
    }

    public boolean isLeader(String uuid, List<ScheduleStrategyRunntime> factoryList) {
        try {
            long no = Long.parseLong(uuid.substring(uuid.lastIndexOf("$") + 1));
            for (ScheduleStrategyRunntime server : factoryList) {
                if (no > Long.parseLong(server.getUuid().substring(
                        server.getUuid().lastIndexOf("$") + 1))) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return true;
        }
    }

    public void reRunScheduleServer() throws Exception {
        for (ScheduleStrategyRunntime run : this.scheduleStrategyManager.loadAllScheduleStrategyRunntimeByUUID(this.uuid)) {
            List<IStrategyTask> list = this.managerMap.get(run.getStrategyName());
            if (list == null) {
                list = new ArrayList<IStrategyTask>();
                this.managerMap.put(run.getStrategyName(), list);
            }
            while (list.size() > run.getRequestNum() && list.size() > 0) {
                IStrategyTask task = list.remove(list.size() - 1);
                try {
                    task.stop(run.getStrategyName());
                } catch (Throwable e) {
                    logger.error("" + e.getMessage(), e);
                }
            }
            ScheduleStrategy strategy = this.scheduleStrategyManager.loadStrategy(run.getStrategyName());
            while (list.size() < run.getRequestNum()) {
                IStrategyTask result = this.createStrategyTask(strategy);
                list.add(result);
            }
        }
    }

    /**
     *
     * @param taskType
     * @throws Exception
     */
    public void stopServer(String strategyName) throws Exception {
        if (strategyName == null) {
            String[] nameList = (String[]) this.managerMap.keySet().toArray(new String[0]);
            for (String name : nameList) {
                for (IStrategyTask task : this.managerMap.get(name)) {
                    try {
                        task.stop(strategyName);
                    } catch (Throwable e) {
                        logger.error("" + e.getMessage(), e);
                    }
                }
                this.managerMap.remove(name);
            }
        } else {
            List<IStrategyTask> list = this.managerMap.get(strategyName);
            if (list != null) {
                for (IStrategyTask task : list) {
                    try {
                        task.stop(strategyName);
                    } catch (Throwable e) {
                        logger.error("" + e.getMessage(), e);
                    }
                }
                this.managerMap.remove(strategyName);
            }

        }
    }

    /**
     */
    public void stopAll() throws Exception {
        try {
            lock.lock();
            this.start = false;
            if (this.initialThread != null) {
                this.initialThread.stopThread();
            }
            if (this.timer != null) {
                if (this.timerTask != null) {
                    this.timerTask.cancel();
                    this.timerTask = null;
                }
                this.timer.cancel();
                this.timer = null;
            }
            this.stopServer(null);
            if (this.zkManager != null) {
                this.zkManager.close();
            }
            if (this.scheduleStrategyManager != null) {
                try {
                    ZooKeeper zk = this.scheduleStrategyManager.getZooKeeper();
                    if (zk != null) {
                        zk.close();
                    }
                } catch (Exception e) {
                }
            }
            this.uuid = null;
            logger.warn("");
        } catch (Throwable e) {
            logger.error("" + e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }

    /**
     *
     * @throws Exception
     */
    public void reStart() throws Exception {
        try {
            if (this.timer != null) {
                if (this.timerTask != null) {
                    this.timerTask.cancel();
                    this.timerTask = null;
                }
                this.timer.purge();
            }
            this.stopServer(null);
            if (this.zkManager != null) {
                this.zkManager.close();
            }
            this.uuid = null;
            this.init();
        } catch (Throwable e) {
            logger.error("" + e.getMessage(), e);
        }
    }

    public boolean isZookeeperInitialSucess() throws Exception {
        return this.zkManager.checkZookeeperState();
    }

    public String[] getScheduleTaskDealList() {
        return applicationcontext.getBeanNamesForType(IScheduleTaskDeal.class);

    }

    public IScheduleDataManager getScheduleDataManager() {
        if (this.scheduleDataManager == null) {
            throw new RuntimeException(this.errorMessage);
        }
        return scheduleDataManager;
    }

    public ScheduleStrategyDataManager4ZK getScheduleStrategyManager() {
        if (this.scheduleStrategyManager == null) {
            throw new RuntimeException(this.errorMessage);
        }
        return scheduleStrategyManager;
    }

    public void setApplicationContext(ApplicationContext aApplicationcontext) throws BeansException {
        applicationcontext = aApplicationcontext;
    }

    public Object getBean(String beanName) {
        return applicationcontext.getBean(beanName);
    }

    public String getUuid() {
        return uuid;
    }

    public String getIp() {
        return ip;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHostName() {
        return hostName;
    }

    public void setStart(boolean isStart) {
        this.start = isStart;
    }

    public void setTimerInterval(int timerInterval) {
        this.timerInterval = timerInterval;
    }

    public void setZkConfig(Map<String, String> zkConfig) {
        this.zkConfig = zkConfig;
    }

    public ZKManager getZkManager() {
        return this.zkManager;
    }

    public Map<String, String> getZkConfig() {
        return zkConfig;
    }
}


