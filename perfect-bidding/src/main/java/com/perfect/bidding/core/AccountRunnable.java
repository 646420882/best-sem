package com.perfect.bidding.core;

import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduApiServiceFactory;
import com.perfect.api.baidu.BaiduPreviewHelperFactory;
import com.perfect.api.baidu.BaiduSpiderHelper;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.constants.KeywordStatusEnum;
import com.perfect.core.AppContext;
import com.perfect.elasticsearch.threads.EsThreadPoolTaskExecutor;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.StrategyEntity;
import com.perfect.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by vbzer_000 on 2014/9/23.
 */
public class AccountRunnable implements Runnable {

    private Logger logger = LoggerFactory.getLogger(AccountRunnable.class);

    private final String userName;
    private final BaiduAccountInfoEntity entity;

    private ApplicationContextHelper context;


    public AccountRunnable(String userName, BaiduAccountInfoEntity entity) {
        this.userName = userName;
        this.entity = entity;
    }

    @Override
    public void run() {

        AppContext.setUser(userName);

        BiddingRuleService biddingRuleService = (BiddingRuleService) context.getBeanByClass(BiddingRuleService.class);
        SysKeywordService sysKeywordService = (SysKeywordService) context.getBeanByClass(SysKeywordService.class);
        SysCampaignService sysCampaignService = (SysCampaignService) context.getBeanByClass(SysCampaignService.class);
        SysAdgroupService sysAdgroupService = (SysAdgroupService) context.getBeanByClass(SysAdgroupService.class);


        EsThreadPoolTaskExecutor executors = (EsThreadPoolTaskExecutor) context.getBeanByClass(EsThreadPoolTaskExecutor.class);

        CommonService commonService = null;
        try {
            commonService = ServiceFactory.getInstance(entity.getBaiduUserName(), entity.getBaiduPassword(),
                    entity.getToken(), null);
        } catch (ApiException e) {
            e.printStackTrace();
            return;
        }


        BaiduPreviewHelperFactory baiduPreviewHelperFactory = (BaiduPreviewHelperFactory) context.getBeanByClass(BaiduPreviewHelperFactory.class);
        BaiduSpiderHelper baiduSpiderHelper = baiduPreviewHelperFactory.createInstance(commonService);
        if (biddingRuleService == null) {
            return;
        }

        BaiduApiServiceFactory baiduApiServiceFactory = (BaiduApiServiceFactory) context.getBeanByClass(BaiduApiServiceFactory.class);
        BaiduApiService apiService = baiduApiServiceFactory.createService(commonService);
        if (apiService == null) {
            return;
        }

        BiddingLogService biddingLogService = (BiddingLogService) context.getBeanByClass(BiddingLogService.class);

        String host = entity.getRegDomain();

        while (true) {
            BiddingRuleEntity biddingRuleEntity = biddingRuleService.takeOne(userName, entity.getId(),
                    System.currentTimeMillis());
            if (biddingRuleEntity == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("当前暂无可执行的竞价规则.");
                }
                sleep(30000);
                continue;
            }
            KeywordEntity keywordEntity = sysKeywordService.findById(biddingRuleEntity.getKeywordId());

            if (keywordEntity == null) {
                biddingRuleService.removeByKeywordId(biddingRuleEntity.getKeywordId());
                continue;
            }

            //关键词状态为42 45 46时, 不进行竞价
            Integer keywordStatus = keywordEntity.getStatus();  //KeywordStatusEnum
            if (keywordStatus == KeywordStatusEnum.STATUS_PAUSE.getVal()
                    || keywordStatus == KeywordStatusEnum.STATUS_WAIT.getVal()
                    || keywordStatus == KeywordStatusEnum.STATUS_AUDITING.getVal()) {
                done(biddingRuleEntity, biddingRuleService);
                continue;
            }

            //判断该关键词所在的单元或是计划是否处于暂停状态
            boolean isPause = keywordEntity.getPause();
            if (!isPause) {
                isPause = sysAdgroupService.findByAdgroupId(keywordEntity.getAdgroupId()).getPause();
                if (!isPause) {
                    isPause = sysCampaignService.findByKeywordId(keywordEntity.getKeywordId()).getPause();
                }
            }
            if (isPause) {
                done(biddingRuleEntity, biddingRuleService);
                continue;
            }

            //获取竞价区域
            Integer[] regionList = biddingRuleEntity.getStrategyEntity().getRegionTarget();
            if (regionList == null) {
                CampaignEntity campaignEntity = sysCampaignService.findByKeywordId(keywordEntity.getKeywordId());
                if (campaignEntity.getRegionTarget() != null && !campaignEntity.getRegionTarget().isEmpty()) {
                    regionList = campaignEntity.getRegionTarget().toArray(new Integer[]{});
                } else {
                    regionList = entity.getRegionTarget().toArray(new Integer[]{});
                }
            }

            // 根据不同地域进行竞价
            for (Integer region : regionList) {
                if (logger.isDebugEnabled()) {
                    logger.debug("竞价词" + keywordEntity.getKeyword() + " 竞价地域 " + region);
                }
                ParseWorkerExecutor executor = (ParseWorkerExecutor) context.getBeanByClass(ParseWorkerExecutor.class);
                ParseWorker spiderSubTask = new ParseWorker(keywordEntity.getKeyword(), region);
                spiderSubTask.setBiddingRuleEntity(biddingRuleEntity);
                spiderSubTask.setKeywordEntity(keywordEntity);
                spiderSubTask.setApiService(apiService);
                spiderSubTask.setHost(host);

                executor.execute(spiderSubTask);
            }
            done(biddingRuleEntity, biddingRuleService);
        }

    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setContext(ApplicationContextHelper context) {
        this.context = context;
    }

    public ApplicationContextHelper getContext() {
        return context;
    }


    public void done(BiddingRuleEntity biddingRuleEntity, BiddingRuleService biddingRuleService) {

        // 计算下次竞价执行时间
        StrategyEntity strategyEntity = biddingRuleEntity.getStrategyEntity();
        int interval = strategyEntity.getInterval();
        long nextTime = nextTime(strategyEntity.getTimes(), interval);

        biddingRuleEntity.setNext(nextTime);
        int currentTime = biddingRuleEntity.getCurrentTimes();
        if (currentTime != -1)
            biddingRuleEntity.setCurrentTimes(--currentTime);

        biddingRuleEntity.setRunning(false);
        biddingRuleService.updateRule(biddingRuleEntity);
    }

    public void main(String[] args) {

        Calendar calendar = Calendar.getInstance();

        long time = nextTime(new Integer[]{0, 9, 10, 15}, 20);
        calendar.setTimeInMillis(time);
        System.out.println(calendar.getTime());


        time = nextTime(new Integer[]{0, 9, 10, 15, 22, 23}, 60);
        calendar.setTimeInMillis(time);
        System.out.println(calendar.getTime());


        time = nextTime(new Integer[]{0, 9, 10, 15, 18, 19}, 120);
        calendar.setTimeInMillis(time);
        System.out.println(calendar.getTime());

        // 测试单时间段
        time = nextTime(new Integer[]{0, 10}, 20);
        calendar.setTimeInMillis(time);
        System.out.println(calendar.getTime());

        time = nextTime(new Integer[]{0, 10}, 60);
        calendar.setTimeInMillis(time);
        System.out.println(calendar.getTime());

        time = nextTime(new Integer[]{0, 10}, 120);
        calendar.setTimeInMillis(time);
        System.out.println(calendar.getTime());

        // 测试跨越多时间段

        time = nextTime(new Integer[]{3, 9, 18, 18, 20, 20}, 160);
        calendar.setTimeInMillis(time);
        System.out.println(calendar.getTime());
    }

    public static long nextTime(Integer[] times, int interval) {

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

        // 当前时间不在运行区间,取最近的下一个区间
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

        // 上一次寻找无时间段,从头开始查找
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
}
