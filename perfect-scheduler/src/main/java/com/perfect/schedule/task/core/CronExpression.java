package com.perfect.schedule.task.core;

/**
 * Created by yousheng on 2014/8/1.
 *
 * @author yousheng
 */
public class CronExpression {
    // every day fired once
    public static final String CRON_DAILY_START = "0 0 0 ? * *";

    public static final String CRON_EVERY_HOUR = "0 0 ? * * *";

    public static final String CRON_EVERY_HALF_HOUR = "0 0/30 ? * * *";

    public static final String CRON_EVERY_MINUTE = "0 0/1 * * * ?";

    public static final String CRON_EVERY_HALF_MINUTE = "0/30 ? * * * *";
}
