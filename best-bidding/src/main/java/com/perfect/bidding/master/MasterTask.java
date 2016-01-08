package com.perfect.bidding.master;

import com.perfect.bidding.redis.BiddingMessage;
import com.perfect.bidding.redis.Constants;
import com.perfect.bidding.redis.JRedisPools;
import com.perfect.commons.constants.KeywordStatusEnum;
import com.perfect.commons.constants.SystemNameConstant;
import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.core.AppContext;
import com.perfect.dto.bidding.BiddingRuleDTO;
import com.perfect.dto.bidding.StrategyDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.service.*;
import com.perfect.utils.json.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by baizz on 2014-12-29.
 */
public class MasterTask implements Runnable, Constants {

    private static final Logger LOGGER = LoggerFactory.getLogger(MasterTask.class);

    private static final long TIME_PERIOD = 5 * 60 * 1_000;     // five minutes


    private SysKeywordService sysKeywordService;

    private SysCampaignService sysCampaignService;

    private SysAdgroupService sysAdgroupService;


    @Override
    public void run() {

        sysKeywordService = (SysKeywordService) ApplicationContextHelper
                .getBeanByName("sysKeywordService");
        sysCampaignService = (SysCampaignService) ApplicationContextHelper
                .getBeanByName("sysCampaignService");
        sysAdgroupService = (SysAdgroupService) ApplicationContextHelper
                .getBeanByName("sysAdgroupService");

        SystemUserService systemUserService = (SystemUserService) ApplicationContextHelper
                .getBeanByName("systemUserService");
        List<SystemUserDTO> userDTOList = systemUserService.getAllValidUser();
        for (SystemUserDTO user : userDTOList) {
            String username = user.getUserName();
            user.getSystemUserModules().stream()
                    .filter(systemUserModuleDTO -> Objects.equals(SystemNameConstant.SOUKE_SYSTEM_NAME, systemUserModuleDTO.getModuleName()))
                    .filter(systemUserModuleDTO -> systemUserModuleDTO.getAccounts().size() > 0)
                    .forEach(systemUserModuleDTO -> publishJobs(username));
        }

    }

    private void publishJobs(String username) {
        AppContext.setUser(username);
        BiddingRuleService biddingRuleService = (BiddingRuleService) ApplicationContextHelper
                .getBeanByName("biddingRuleService");

        Jedis jedis = null;
        try {
            jedis = JRedisPools.getConnection();
            while (true) {
                if (WorkerList.len() == 0) {
                    sleep(1);
                } else {
                    break;
                }
            }

            long time = System.currentTimeMillis() - TIME_PERIOD;
            List<BiddingRuleDTO> biddingRuleList = new ArrayList<>(biddingRuleService.getAvailableRules(username, time));
            if (biddingRuleList.size() > 0) {
                for (BiddingRuleDTO biddingRule : biddingRuleList) {
//                    if (biddingRule == null) {
//                        if (LOGGER.isDebugEnabled()) {
//                            LOGGER.debug("no executable bidding rule at present.");
//                        }
//                        // 该用户当前无可执行的竞价策略
//                        continue;
//                    }

                    // 检查关键词是否存在
                    KeywordDTO keywordDTO = sysKeywordService.findById(biddingRule.getKeywordId());

                    if (keywordDTO == null) {
                        biddingRuleService.removeByKeywordId(biddingRule.getKeywordId());
                        continue;
                    }

                    // 关键词状态为42 45 46时, 不进行竞价
                    Integer keywordStatus = keywordDTO.getStatus();  //KeywordStatusEnum
                    if (keywordStatus == KeywordStatusEnum.STATUS_PAUSE.getVal()
                            || keywordStatus == KeywordStatusEnum.STATUS_WAIT.getVal()
                            || keywordStatus == KeywordStatusEnum.STATUS_AUDITING.getVal()) {
                        done(biddingRule, biddingRuleService);
                        continue;
                    }

                    // 判断该关键词所在的单元或是计划是否处于暂停状态
                    boolean isPause = keywordDTO.getPause();
                    if (!isPause) {
                        isPause = sysAdgroupService.findByAdgroupId(keywordDTO.getAdgroupId()).getPause();
                        if (!isPause) {
                            isPause = sysCampaignService.findByKeywordId(keywordDTO.getKeywordId()).getPause();
                        }
                    }
                    if (isPause) {
                        done(biddingRule, biddingRuleService);
                        continue;
                    }


                    // publish Jobs
//                    String jobstr = username + JOB_SEP + biddingRuleDTO.getAccountId() + JOB_SEP + biddingRuleDTO.getId();
                    String jobstr = new BiddingMessage(username, biddingRule.getAccountId(), biddingRule.getId()).toString();

                    jedis = JRedisPools.getConnection();
                    while (true) {
                        if (WorkerList.len() == 0) {
                            sleep(1);
                            continue;
                        }

//                        WorkerObject workerObject = WorkerList.take();
                        String workerId = WorkerList.take();

                        if (Master.isExpired(workerId)) {
                            jedis.publish(BIDDING_EXPIRE_WORKER_CHANNEL, workerId);
                            continue;
                        }
                        jedis.publish(workerId, jobstr);


//                        workerObject.inc();
                        WorkerList.offer(workerId);
                        break;
                    }


//                    done(biddingRule, biddingRuleService);
                }
            }
        } finally {
            if (jedis != null) {
                JRedisPools.returnJedis(jedis);
            }
        }
    }

    private void sleep(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void done(BiddingRuleDTO biddingRuleDTO, BiddingRuleService biddingRuleService) {

        // 计算下次竞价执行时间
        StrategyDTO strategyDTO = biddingRuleDTO.getStrategyDTO();
        int interval = strategyDTO.getInterval();
        long nextTime = nextTime(strategyDTO.getTimes(), interval);

        biddingRuleDTO.setNext(nextTime);
        int currentTime = biddingRuleDTO.getCurrentTimes();
        if (currentTime != -1)
            biddingRuleDTO.setCurrentTimes(--currentTime);

        biddingRuleDTO.setRunning(false);
        biddingRuleService.save(biddingRuleDTO);

        //publish message
//        Jedis jedis = null;
//        try {
//            jedis = JRedisPools.getConnection();
//            String message = getMessage(biddingRuleDTO);
//            Objects.requireNonNull(message);
//            jedis.publish(BIDDING_FINISHED_CHANNEL, message);
//        } finally {
//            if (jedis != null) {
//                JRedisPools.returnJedis(jedis);
//            }
//        }

        biddingRuleDTO.setId(null);
    }

    private static long nextTime(Integer[] times, int interval) {

        long nextRunTime = System.currentTimeMillis() + interval * 60 * 1000;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(nextRunTime);

        int nextHour = cal.get(Calendar.HOUR_OF_DAY);
        long nextTime = -1;

        int[] dayOfHous = new int[24];
        Arrays.fill(dayOfHous, 0);
        // 获取运行区间
        for (int i = 0, len = times.length; i < len; ) {
            int start = times[i++];
            int end = times[i++];
            Arrays.fill(dayOfHous, start, end + 1, 1);
            if (start <= nextHour && nextHour <= end) {
                nextTime = nextRunTime;
                break;
            }
        }

        // 当前时间不在运行区间, 取最近的下一个区间
        if (nextTime == -1) {
            for (int i = nextHour; i < dayOfHous.length; i++) {
                if (dayOfHous[i] == 1) {
                    cal.set(Calendar.HOUR_OF_DAY, i);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    nextTime = cal.getTimeInMillis();
                    break;
                }
            }
        }

        // 上一次寻找无时间段, 从头开始查找
        if (nextTime == -1) {
            for (int i = 0; i < dayOfHous.length; i++) {
                if (dayOfHous[i] == 1) {
                    cal.add(Calendar.DAY_OF_YEAR, 1);
                    cal.set(Calendar.HOUR_OF_DAY, i);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    nextTime = cal.getTimeInMillis();
                    break;
                }
            }
        }

        return nextTime;
    }

    private String getMessage(Object o) {
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ObjectOutputStream oos = new ObjectOutputStream(baos);
//            oos.writeObject(o);
//            return new String(baos.toByteArray());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
        return JSONUtils.getJsonString(o);
    }

}
