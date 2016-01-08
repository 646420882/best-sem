package com.perfect.bidding.redis;

/**
 * Created by baizz on 2014-12-25.
 */
public interface Constants {

    public static final String BIDDING_MASTER_ID = "bidding_master_id";

    public static final String HEARTBEAT_VALUE = "running";

//    public static final String BIDDING_WORKER_LIST = "bidding_workers";

//    public static final String BIDDING_WORKER_PREFIX = "-worker";

    public static final String BIDDING_WORKER_SUFFIX = "-worker";


    public static final String BIDDING_WORKER_INIT_VALUE = "workerInit";

    public static final String BIDDING_EXPIRE_WORKER_CHANNEL = "bidding_expire_worker_channel";

    public static final String BIDDING_WORKER_REGEX = "*-worker";

    public static final String BIDDING_WORKER_STATUS_REGEX = "*-workerStatus";

//    public static final String BIDDING_WORKER_STATUS_PREFIX = "workerStatus-";

    public static final String BIDDING_WORKER_STATUS_SUFFIX = "-workerStatus";

    public static final String JOB_SEP = ":";


    default boolean isFinishedChannel(String channel) {
        return channel.length() == 40;       // worker-6afd76367f124982be96033fd40aa05a$(length: 40)
    }

}
