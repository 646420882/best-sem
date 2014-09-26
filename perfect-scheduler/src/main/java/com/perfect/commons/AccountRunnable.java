package com.perfect.commons;

import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduApiServiceFactory;
import com.perfect.api.baidu.BaiduSpiderHelper;
import com.perfect.api.baidu.BaiduPreviewHelperFactory;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.constants.BiddingStrategyConstants;
import com.perfect.constants.KeywordStatusEnum;
import com.perfect.core.AppContext;
import com.perfect.dto.CreativeDTO;
import com.perfect.elasticsearch.threads.EsRunnable;
import com.perfect.elasticsearch.threads.EsThreadPoolTaskExecutor;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.bidding.BiddingLogEntity;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.StrategyEntity;
import com.perfect.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/23.
 */
@Deprecated
public class AccountRunnable implements Runnable {

    private Logger logger = LoggerFactory.getLogger(AccountRunnable.class);

    private static final BigDecimal FAST_PRICE = BigDecimal.ONE.divide(BigDecimal.ONE.add(BigDecimal.ONE));

    private static final BigDecimal ECON_PRICE = BigDecimal.valueOf(0.05);

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
                done(biddingRuleEntity, biddingRuleService);
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


                while (true) {

                    if (logger.isDebugEnabled()) {
                        logger.debug("竞价词" + keywordEntity.getKeyword() + " 竞价地域 " + region);
                    }

                    List<BaiduSpiderHelper.PreviewData> datas = baiduSpiderHelper.getPageData(new String[]{
                            keywordEntity.getKeyword()}, region);

                    if (datas.isEmpty()) {
                        sleep(1000);
                        continue;
                    }

                    BaiduSpiderHelper.PreviewData previewData = datas.get(0);

                    if ((previewData.getLeft() == null || previewData.getLeft().isEmpty()) && (previewData.getRight() == null || previewData.getRight().isEmpty())) {
                        sleep(1000);
                        continue;
                    }

                    StrategyEntity strategyEntity = biddingRuleEntity.getStrategyEntity();

                    int pos = strategyEntity.getExpPosition();
                    List<CreativeDTO> leftList = previewData.getLeft();
                    List<CreativeDTO> rightList = previewData.getRight();
                    if (leftList.isEmpty() && rightList.isEmpty()) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("暂未获取到任何排名信息");
                        }
                        continue;
                    } else {
                        //保存到ES数据库
                        List<CreativeDTO> list = new ArrayList<>();
                        list.addAll(leftList);
                        list.addAll(rightList);

                        try {
                            EsRunnable esRunnable = (EsRunnable) context.getBeanByClass(EsRunnable.class);
                            esRunnable.setKeyword(previewData.getKeyword());
                            esRunnable.setRegion(region);
                            esRunnable.setList(list);
                            executors.execute(esRunnable);
                        } catch (Exception e) {
                            if (logger.isErrorEnabled()) {
                                logger.error("ES error", e);
                            }
                        }
                    }

                    int rank = 0;
                    try {
                        URL url = new URL("http", host, "");
                        for (CreativeDTO leftDTO : leftList) {
                            if (url.sameFile(new URL("http", leftDTO.getUrl(), ""))) {
                                rank = leftList.indexOf(leftDTO) + 1;
                                break;
                            }
                        }

                        if (rank == 0) {
                            for (CreativeDTO rightDTO : rightList) {
                                if (url.sameFile(new URL("http", rightDTO.getUrl(), ""))) {
                                    rank = -1 * rightList.indexOf(rightDTO) - 1;
                                    break;
                                }
                            }
                        }
                    } catch (MalformedURLException e) {
                        if (logger.isErrorEnabled()) {
                            logger.error("MalformedURLException", e);
                        }
                    }

                    boolean match = false;
                    if (pos == BiddingStrategyConstants.POS_LEFT_1.value()) {
                        match = (rank == 1);
                    } else if (pos == BiddingStrategyConstants.POS_LEFT_2_3.value()) {
                        match = (rank == 2);
                    } else if (pos == BiddingStrategyConstants.POS_RIGHT_1_3.value()) {
                        match = (rank == -1);
                    } else if (pos == BiddingStrategyConstants.POS_RIGHT_OTHERS.value()) {
                        match = (rank == -1 * strategyEntity.getExpPosition());
                    }

                    // 已经达到排名
                    if (match) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("达到排名..." + region + " " + host + " " + keywordEntity.getKeyword());
                        }
                        break;
                    } else {
                        // 出价策略
                        BigDecimal currentPrice = biddingRuleEntity.getCurrentPrice();

                        if (currentPrice == null) {
                            currentPrice = BigDecimal.ZERO;
                        }
                        // 竞价日志
                        BiddingLogEntity biddingLogEntity = new BiddingLogEntity();
                        biddingLogEntity.setKeywordId(keywordEntity.getKeywordId());
                        biddingLogEntity.setDate(System.currentTimeMillis());

                        if (currentPrice.compareTo(strategyEntity.getMinPrice()) == -1) {
                            currentPrice = strategyEntity.getMinPrice();
                            biddingLogEntity.setBefore(keywordEntity.getPrice());
                            biddingLogEntity.setAfter(currentPrice);
                        } else {
                            biddingLogEntity.setBefore(currentPrice);
                            if (strategyEntity.getMode() == BiddingStrategyConstants.SPD_FAST.value()) {
                                currentPrice = currentPrice.add(FAST_PRICE);
                            } else {
                                currentPrice = currentPrice.add(ECON_PRICE);
                            }
                            biddingLogEntity.setAfter(currentPrice);

                        }

                        if (currentPrice.compareTo(strategyEntity.getMaxPrice()) == 1) {

                            if (logger.isDebugEnabled()) {
                                logger.debug("未达到排名..." + host + " " + keywordEntity.getKeyword() + "\n出价已超过最大价格!");
                            }
                            int failed = strategyEntity.getFailedStrategy();
                            if (failed == BiddingStrategyConstants.FAILED_KEEP.value()) {
                                // 保持当前排名
                                if (logger.isDebugEnabled()) {
                                    logger.debug("根据竞价失败规则..." + host + " " + keywordEntity.getKeyword() + "\n保持当前排名" +
                                            ".");
                                }
                            } else if (failed == BiddingStrategyConstants.FAILED_ROLLBACK.value()) {
                                //竞价失败 恢复之前的原始竞价
                                if (logger.isDebugEnabled()) {
                                    logger.debug("根据竞价失败规则..." + host + " " + keywordEntity.getKeyword() +
                                            "\n恢复原始竞价价格.");
                                }
                                KeywordType keywordType = new KeywordType();
                                keywordType.setKeywordId(keywordEntity.getKeywordId());
                                keywordType.setPrice(keywordEntity.getPrice().doubleValue());
//                                apiService.setKeywordPrice(keywordType);

                                biddingRuleEntity.setCurrentPrice(keywordEntity.getPrice());

                                biddingLogEntity.setAfter(BigDecimal.valueOf(keywordType.getPrice()));
                                biddingLogService.save(biddingLogEntity);

                            }
                            break;
                        } else {
                            // 正常流程
                            KeywordType keywordType = new KeywordType();
                            keywordType.setKeywordId(keywordEntity.getKeywordId());
                            keywordType.setPrice(currentPrice.doubleValue());
//                            apiService.setKeywordPrice(keywordType);

                            biddingRuleEntity.setCurrentPrice(currentPrice);
                            biddingRuleService.updateRule(biddingRuleEntity);

                            biddingLogService.save(biddingLogEntity);

                            if (logger.isDebugEnabled()) {
                                logger.debug("未达到排名..." + host + "\n出价完成,1秒后进行下一次出价.");
                            }
                        }
                    }
                }
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
