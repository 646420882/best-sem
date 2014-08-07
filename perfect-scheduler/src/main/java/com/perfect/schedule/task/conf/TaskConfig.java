package com.perfect.schedule.task.conf;

/**
 * Created by yousheng on 2014/7/29.
 *
 * @author yousheng
 */
public interface TaskConfig {

    public final String TASK_DOMAIN = "perfect";

    public final String[] ALL_MACHINES = new String[]{"127.0.0.1"};

    public final String SUFFIX_STRATEGY = "-Strategy";

    public final String SEPARATOR = "$";

    public void createTask();
}
