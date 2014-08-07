package com.perfect.schedule.task.conf.impl;

import com.perfect.schedule.core.strategy.ScheduleStrategy;
import com.perfect.schedule.core.strategy.TBScheduleManagerFactory;
import com.perfect.schedule.core.taskmanager.ScheduleTaskType;
import com.perfect.schedule.task.conf.TaskConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
public class UserUpdateTaskConfig implements TaskConfig {

    protected static transient Logger log = LoggerFactory
            .getLogger(UserUpdateTaskConfig.class);

    @Resource
    TBScheduleManagerFactory scheduleManagerFactory;

    public void setScheduleManagerFactory(
            TBScheduleManagerFactory tbScheduleManagerFactory) {
        this.scheduleManagerFactory = tbScheduleManagerFactory;
    }

    public void createTask() {
        String baseTaskTypeName = "UserUpdateTask";
        try {
            while (this.scheduleManagerFactory.isZookeeperInitialSucess() == false) {
                Thread.sleep(1000);
            }
            scheduleManagerFactory.stopServer(null);
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            this.scheduleManagerFactory.getScheduleDataManager()
                    .deleteTaskType(baseTaskTypeName);
        } catch (Exception e) {

        }
        ScheduleTaskType baseTaskType = new ScheduleTaskType();
        baseTaskType.setBaseTaskType(baseTaskTypeName);
        baseTaskType.setDealBeanName("userUpdateTask");
        baseTaskType.setHeartBeatRate(2000);
        baseTaskType.setJudgeDeadInterval(10000);
        baseTaskType.setPermitRunStartTime("0 * * * * ?");
        baseTaskType.setTaskItems(ScheduleTaskType.splitTaskItem(
                "0:{TYPE=A,KIND=1},1:{TYPE=A,KIND=2},2:{TYPE=A,KIND=3},3:{TYPE=A,KIND=4}," +
                        "4:{TYPE=A,KIND=5},5:{TYPE=A,KIND=6},6:{TYPE=A,KIND=7},7:{TYPE=A,KIND=8}," +
                        "8:{TYPE=A,KIND=9},9:{TYPE=A,KIND=10}"));
        try {
            this.scheduleManagerFactory.getScheduleDataManager()
                    .createBaseTaskType(baseTaskType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("创建调度任务成功:" + baseTaskType.toString());

        // 创建任务DemoTask的调度策略
        String taskName = baseTaskTypeName + "$PERFECT";
        String strategyName = baseTaskTypeName + "-Strategy";
        try {
            this.scheduleManagerFactory.getScheduleStrategyManager()
                    .deleteMachineStrategy(strategyName, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ScheduleStrategy strategy = new ScheduleStrategy();
        strategy.setStrategyName(strategyName);
        strategy.setKind(ScheduleStrategy.Kind.Schedule);
        strategy.setTaskName(taskName);

        strategy.setNumOfSingleServer(1);
        strategy.setAssignNum(10);
        strategy.setIPList("127.0.0.1".split(","));
        try {
            this.scheduleManagerFactory.getScheduleStrategyManager()
                    .createScheduleStrategy(strategy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("创建调度策略成功:" + strategy.toString());
    }
}
